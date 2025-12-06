from pathlib import Path
import json
from typing import List, Dict, Any
from config.settings import Settings


class ExperimentRepository:
    """Lightweight storage for experiment metadata (optional)."""

    def __init__(self, settings: Settings):
        self.settings = settings
        self.path = self.settings.data_dir / "experiments.json"
        self.path.parent.mkdir(parents=True, exist_ok=True)

    def _load(self) -> List[Dict[str, Any]]:
        if not self.path.exists():
            return []
        with self.path.open("r", encoding="utf-8") as f:
            try:
                return json.load(f)
            except json.JSONDecodeError:
                return []

    def _write(self, payload: List[Dict[str, Any]]) -> None:
        with self.path.open("w", encoding="utf-8") as f:
            json.dump(payload, f, ensure_ascii=False, indent=2)

    def add(self, experiment: Dict[str, Any]) -> None:
        data = self._load()
        data.append(experiment)
        self._write(data)

    def list_all(self) -> List[Dict[str, Any]]:
        return self._load()


