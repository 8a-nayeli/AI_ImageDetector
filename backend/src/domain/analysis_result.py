from typing import Dict
from pydantic import BaseModel, ConfigDict, Field
from .image_pair import ImageInfo


class AnalysisImages(BaseModel):
    real_image: ImageInfo = Field(
        serialization_alias="realImage", validation_alias="realImage"
    )
    fake_image: ImageInfo = Field(
        serialization_alias="fakeImage", validation_alias="fakeImage"
    )

    model_config = ConfigDict(populate_by_name=True)


class GradientArtifacts(BaseModel):
    real_gradient_url: str = Field(
        serialization_alias="realGradientUrl", validation_alias="realGradientUrl"
    )
    fake_gradient_url: str = Field(
        serialization_alias="fakeGradientUrl", validation_alias="fakeGradientUrl"
    )
    diff_gradient_url: str | None = Field(
        default=None,
        serialization_alias="diffGradientUrl",
        validation_alias="diffGradientUrl",
    )

    model_config = ConfigDict(populate_by_name=True)


class FeatureVector(BaseModel):
    mean_gradient_real: float = Field(
        serialization_alias="meanGradientReal", validation_alias="meanGradientReal"
    )
    mean_gradient_fake: float = Field(
        serialization_alias="meanGradientFake", validation_alias="meanGradientFake"
    )
    std_gradient_real: float = Field(
        serialization_alias="stdGradientReal", validation_alias="stdGradientReal"
    )
    std_gradient_fake: float = Field(
        serialization_alias="stdGradientFake", validation_alias="stdGradientFake"
    )
    histogram_real: list[float] = Field(
        serialization_alias="histogramReal", validation_alias="histogramReal"
    )
    histogram_fake: list[float] = Field(
        serialization_alias="histogramFake", validation_alias="histogramFake"
    )

    model_config = ConfigDict(populate_by_name=True)


class DetectorResult(BaseModel):
    score: float
    explanation: str | None = None

    model_config = ConfigDict(populate_by_name=True)


class AnalysisResult(BaseModel):
    pair_id: str = Field(serialization_alias="pairId", validation_alias="pairId")
    images: AnalysisImages
    gradients: GradientArtifacts
    features: FeatureVector
    detectors: Dict[str, DetectorResult]

    model_config = ConfigDict(populate_by_name=True)


