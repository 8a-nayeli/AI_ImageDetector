from pathlib import Path
from fastapi import UploadFile
import numpy as np
from config.settings import Settings
from core.image_io import load_image_from_upload, save_image_array
from core.visualization import save_gradient_image


class FileStorage:
    """Filesystem-based storage for images and gradient artifacts."""

    def __init__(self, settings: Settings):
        self.settings = settings

    def _url_for(self, path: Path) -> str:
        relative = path.relative_to(self.settings.data_dir)
        # Return absolute URL so the frontend (on a different origin/port) can load assets.
        base = str(self.settings.public_base_url).rstrip("/")
        return f"{base}/static/{relative.as_posix()}"

    def save_real_image(self, upload: UploadFile, pair_id: str) -> tuple[str, str, Path]:
        image = load_image_from_upload(upload)
        image_id = f"{pair_id}_real"
        path = self.settings.images_real_dir / f"{image_id}.png"
        save_image_array(path, image)
        return image_id, self._url_for(path), path

    def save_fake_image(self, upload: UploadFile, pair_id: str) -> tuple[str, str, Path]:
        image = load_image_from_upload(upload)
        image_id = f"{pair_id}_fake"
        path = self.settings.images_fake_dir / f"{image_id}.png"
        save_image_array(path, image)
        return image_id, self._url_for(path), path

    def save_gradient(self, gradient: np.ndarray, pair_id: str, kind: str) -> tuple[str, str, Path]:
        path = self.settings.gradients_dir / pair_id / f"{kind}.png"
        save_gradient_image(gradient, path)
        return kind, self._url_for(path), path


