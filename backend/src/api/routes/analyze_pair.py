import cv2
from uuid import uuid4
from fastapi import APIRouter, Depends, File, Form, UploadFile, HTTPException, status
from api.dependencies import (
    get_storage,
    get_analysis_repo,
    get_pair_repo,
    get_detectors,
)
from data_access.file_storage import FileStorage
from data_access.analysis_repository import AnalysisRepository
from data_access.pair_repository import PairRepository
from detection.gradient_stats_detector import GradientStatsDetector
from domain.image_pair import ImageInfo, ImagePair
from domain.analysis_result import (
    AnalysisImages,
    AnalysisResult,
    GradientArtifacts,
)
from core.gradients import compute_gradient_magnitude
from core.features import extract_features
from core.visualization import compute_diff_gradient
from utils.errors import ValidationError
from utils.logging import get_logger

logger = get_logger(__name__)

router = APIRouter(prefix="/api/analyze", tags=["analyze"])


@router.post(
    "/pair",
    response_model=AnalysisResult,
    status_code=status.HTTP_201_CREATED,
)
async def analyze_pair(
    real_image: UploadFile = File(..., description="Real/reference image"),
    fake_image: UploadFile = File(..., description="Generated image"),
    generatorType: str | None = Form(None),
    prompt: str | None = Form(None),
    storage: FileStorage = Depends(get_storage),
    analysis_repo: AnalysisRepository = Depends(get_analysis_repo),
    pair_repo: PairRepository = Depends(get_pair_repo),
    detectors: list[GradientStatsDetector] = Depends(get_detectors),
):
    pair_id = uuid4().hex
    try:
        logger.info("Analyze pair start", extra={"pair_id": pair_id})
        real_id, real_url, real_path = storage.save_real_image(real_image, pair_id)
        fake_id, fake_url, fake_path = storage.save_fake_image(fake_image, pair_id)

        real_img = cv2.imread(str(real_path))
        fake_img = cv2.imread(str(fake_path))
        if real_img is None or fake_img is None:
            raise ValidationError("Unable to read saved images from disk.")

        logger.info(
            "Images saved and loaded",
            extra={
                "pair_id": pair_id,
                "real_shape": None if real_img is None else real_img.shape,
                "fake_shape": None if fake_img is None else fake_img.shape,
            },
        )

        # Ensure both images share the same spatial dimensions for downstream ops
        real_h, real_w = real_img.shape[:2]
        fake_h, fake_w = fake_img.shape[:2]
        if (real_h, real_w) != (fake_h, fake_w):
            fake_img = cv2.resize(fake_img, (real_w, real_h), interpolation=cv2.INTER_AREA)
            logger.info(
                "Resized fake image to match real image",
                extra={
                    "pair_id": pair_id,
                    "real_shape": (real_h, real_w),
                    "fake_shape_before": (fake_h, fake_w),
                    "fake_shape_after": (real_h, real_w),
                },
            )

        real_grad = compute_gradient_magnitude(real_img)
        fake_grad = compute_gradient_magnitude(fake_img)
        logger.info(
            "Gradients computed",
            extra={
                "pair_id": pair_id,
                "real_grad_shape": real_grad.shape,
                "fake_grad_shape": fake_grad.shape,
            },
        )
        features = extract_features(real_grad, fake_grad)
        logger.info(
            "Features extracted",
            extra={
                "pair_id": pair_id,
                "mean_real": features.mean_gradient_real,
                "mean_fake": features.mean_gradient_fake,
            },
        )

        _, real_grad_url, _ = storage.save_gradient(real_grad, pair_id, "real")
        _, fake_grad_url, _ = storage.save_gradient(fake_grad, pair_id, "fake")
        diff_grad = compute_diff_gradient(real_grad, fake_grad)
        _, diff_grad_url, _ = storage.save_gradient(diff_grad, pair_id, "diff")

        real_info = ImageInfo(id=real_id, url=real_url)
        fake_info = ImageInfo(
            id=fake_id,
            url=fake_url,
            generator_type=generatorType,
            prompt=prompt,
        )

        pair = ImagePair(pair_id=pair_id, real_image=real_info, fake_image=fake_info)
        pair_repo.save(pair)

        detector_results = {
            det.descriptor.name: det.predict(features) for det in detectors
        }

        result = AnalysisResult(
            pair_id=pair_id,
            images=AnalysisImages(real_image=real_info, fake_image=fake_info),
            gradients=GradientArtifacts(
                real_gradient_url=real_grad_url,
                fake_gradient_url=fake_grad_url,
                diff_gradient_url=diff_grad_url,
            ),
            features=features,
            detectors=detector_results,
        )
        analysis_repo.save(result)
        logger.info("Analysis persisted", extra={"pair_id": pair_id})
        return result
    except ValidationError as ve:
        raise HTTPException(status_code=400, detail=str(ve)) from ve
    except Exception as exc:  # pragma: no cover - broad catch for API surface
        logger.exception("Analyze pair failed", extra={"pair_id": pair_id})
        raise HTTPException(status_code=500, detail=str(exc)) from exc


