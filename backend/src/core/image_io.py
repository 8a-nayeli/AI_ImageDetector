from pathlib import Path
import cv2
import numpy as np
from fastapi import UploadFile
from utils.errors import ValidationError


def load_image_from_upload(upload: UploadFile) -> np.ndarray:
    """Read an uploaded image file into a BGR numpy array."""
    content = upload.file.read()
    if not content:
        raise ValidationError("Empty image upload.")
    array = np.frombuffer(content, dtype=np.uint8)
    image = cv2.imdecode(array, cv2.IMREAD_COLOR)
    if image is None:
        raise ValidationError("Failed to decode image.")
    return image


def save_image_array(path: Path, image: np.ndarray) -> Path:
    """Save an image array to disk, ensuring parent directories exist."""
    path.parent.mkdir(parents=True, exist_ok=True)
    if not cv2.imwrite(str(path), image):
        raise ValidationError(f"Could not write image to {path}")
    return path


