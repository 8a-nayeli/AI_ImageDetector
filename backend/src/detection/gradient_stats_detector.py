from domain.analysis_result import FeatureVector, DetectorResult
from .base import BaseDetector


class GradientStatsDetector(BaseDetector):
    """
    Simple baseline detector: compares mean gradient magnitudes between
    real and fake images. Larger differences yield higher scores.
    """

    def __init__(self):
        super().__init__(
            name="gradient_stats",
            description="Baseline detector using gradient magnitude statistics.",
        )

    def predict(self, features: FeatureVector) -> DetectorResult:
        diff = abs(features.mean_gradient_fake - features.mean_gradient_real)
        denom = features.std_gradient_real + features.std_gradient_fake + 1e-6
        raw_score = diff / denom
        # smoother mapping to spread low values
        score = 1.0 - (1.0 / (1.0 + raw_score * 5.0))
        score = min(1.0, max(0.0, score))
        explanation = (
            "Higher score when fake gradients deviate from real gradients in mean magnitude."
        )
        return DetectorResult(score=score, explanation=explanation)


