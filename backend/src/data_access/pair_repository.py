import json
from pathlib import Path
from typing import List
from config.settings import Settings
from domain.image_pair import ImagePair
from utils.errors import NotFoundError


class PairRepository:
    """Stores metadata about image pairs."""

    def __init__(self, settings: Settings):
        self.settings = settings
        self.path = self.settings.data_dir / "pairs.json"
        self.path.parent.mkdir(parents=True, exist_ok=True)

    def _load_all(self) -> List[dict]:
        if not self.path.exists():
            return []
        with self.path.open("r", encoding="utf-8") as f:
            try:
                return json.load(f)
            except json.JSONDecodeError:
                return []

    def _write_all(self, payload: List[dict]) -> None:
        with self.path.open("w", encoding="utf-8") as f:
            json.dump(payload, f, ensure_ascii=False, indent=2)

    def save(self, pair: ImagePair) -> None:
        data = self._load_all()
        data = [p for p in data if p.get("pairId") != pair.pair_id]
        data.append(pair.model_dump(by_alias=True))
        self._write_all(data)

    def get(self, pair_id: str) -> ImagePair:
        data = self._load_all()
        for item in data:
            if item.get("pairId") == pair_id:
                return ImagePair.model_validate(item)
        raise NotFoundError(f"Pair {pair_id} not found")

    def list_all(self) -> List[ImagePair]:
        return [ImagePair.model_validate(item) for item in self._load_all()]


