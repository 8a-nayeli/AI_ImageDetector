import math
from domain.analysis_result import FeatureVector, DetectorResult
from .base import BaseDetector


class StdRatioDetector(BaseDetector):
    """
    Measures deviation between std of real and generated gradients.
    Larger std gap -> higher score.
    """

    def __init__(self):
        super().__init__(
            name="std_ratio",
            description="Std deviation gap between real and generated gradients.",
        )

    def predict(self, features: FeatureVector) -> DetectorResult:
        gap = abs(features.std_gradient_fake - features.std_gradient_real)
        norm = features.std_gradient_real + 1e-6
        raw = gap / norm
        score = 1.0 - math.exp(-raw)
        score = min(1.0, max(0.0, score))
        explanation = "Higher score when variance of generated gradients deviates from real."
        return DetectorResult(score=score, explanation=explanation)

