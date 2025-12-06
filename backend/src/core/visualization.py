from pathlib import Path
import cv2
import numpy as np
from .image_io import save_image_array


def gradient_to_uint8(gradient: np.ndarray) -> np.ndarray:
    """Normalize a gradient magnitude map to 0-255 uint8."""
    if gradient.size == 0:
        return np.zeros_like(gradient, dtype=np.uint8)
    normalized = cv2.normalize(gradient, None, alpha=0, beta=255, norm_type=cv2.NORM_MINMAX)
    return normalized.astype(np.uint8)


def save_gradient_image(gradient: np.ndarray, path: Path) -> Path:
    img = gradient_to_uint8(gradient)
    return save_image_array(path, img)


def compute_diff_gradient(real_grad: np.ndarray, fake_grad: np.ndarray) -> np.ndarray:
    """Absolute difference between two gradient maps."""
    return np.abs(real_grad - fake_grad)


