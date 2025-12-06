from fastapi import APIRouter, Depends, HTTPException, status
from api.dependencies import get_analysis_repo
from data_access.analysis_repository import AnalysisRepository
from api.schemas.pairs import PairSummary
from domain.analysis_result import AnalysisResult

router = APIRouter(prefix="/api/pairs", tags=["pairs"])


@router.get("", response_model=list[PairSummary])
def list_pairs(
    analysis_repo: AnalysisRepository = Depends(get_analysis_repo),
):
    analyses = analysis_repo.list_all()
    summaries = [
        PairSummary(
            pair_id=item.pair_id,
            real_image=item.images.real_image,
            fake_image=item.images.fake_image,
        )
        for item in analyses
    ]
    return summaries


@router.get("/{pair_id}", response_model=AnalysisResult)
def get_pair(
    pair_id: str,
    analysis_repo: AnalysisRepository = Depends(get_analysis_repo),
):
    try:
        return analysis_repo.get(pair_id)
    except Exception as exc:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"pair {pair_id} not found",
        ) from exc


