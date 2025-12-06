import math
from domain.analysis_result import FeatureVector, DetectorResult
from .base import BaseDetector


class HistogramChi2Detector(BaseDetector):
    """
    Compares histogram distributions of gradients between real and generated images
    using a Chi-square distance; larger distance -> higher score.
    """

    def __init__(self):
        super().__init__(
            name="histogram_chi2",
            description="Histogram chi-square distance between real and generated gradients.",
        )

    def predict(self, features: FeatureVector) -> DetectorResult:
        real = features.histogram_real
        fake = features.histogram_fake
        chi2 = 0.0
        for r, f in zip(real, fake):
            denom = r + f + 1e-8
            chi2 += ((r - f) ** 2) / denom
        chi2 *= 0.5  # standard chi2 scaling

        # Map to 0..1 with a smooth curve to spread small differences
        score = 1.0 - math.exp(-chi2 * 2.0)
        score = min(1.0, max(0.0, score))

        explanation = "Higher score when gradient histograms differ more (Chi-square distance)."
        return DetectorResult(score=score, explanation=explanation)

