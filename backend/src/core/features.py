import numpy as np
from domain.analysis_result import FeatureVector


def _normalized_histogram(values: np.ndarray, bins: int = 16) -> list[float]:
    max_val = float(values.max()) if values.size else 0.0
    upper = max(max_val, 1e-6)
    hist, _ = np.histogram(values, bins=bins, range=(0.0, upper))
    total = hist.sum()
    if total == 0:
        return [0.0] * bins
    return (hist / total).astype(float).tolist()


def extract_features(real_grad: np.ndarray, fake_grad: np.ndarray, bins: int = 16) -> FeatureVector:
    """Compute simple statistical features from gradient magnitudes."""
    return FeatureVector(
        mean_gradient_real=float(np.mean(real_grad)),
        mean_gradient_fake=float(np.mean(fake_grad)),
        std_gradient_real=float(np.std(real_grad)),
        std_gradient_fake=float(np.std(fake_grad)),
        histogram_real=_normalized_histogram(real_grad, bins=bins),
        histogram_fake=_normalized_histogram(fake_grad, bins=bins),
    )


