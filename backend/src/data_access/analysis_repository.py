import json
from pathlib import Path
from typing import Iterable, List
from config.settings import Settings
from domain.analysis_result import AnalysisResult
from utils.errors import NotFoundError


class AnalysisRepository:
    """Persists AnalysisResult objects as JSON files."""

    def __init__(self, settings: Settings):
        self.settings = settings

    def _path_for(self, pair_id: str) -> Path:
        return self.settings.analyses_dir / f"{pair_id}.json"

    def save(self, result: AnalysisResult) -> Path:
        path = self._path_for(result.pair_id)
        path.parent.mkdir(parents=True, exist_ok=True)
        with path.open("w", encoding="utf-8") as f:
            json.dump(result.model_dump(by_alias=True), f, ensure_ascii=False, indent=2)
        return path

    def get(self, pair_id: str) -> AnalysisResult:
        path = self._path_for(pair_id)
        if not path.exists():
            raise NotFoundError(f"Analysis for pair {pair_id} not found")
        with path.open("r", encoding="utf-8") as f:
            data = json.load(f)
        return AnalysisResult.model_validate(data)

    def list_all(self) -> List[AnalysisResult]:
        results: List[AnalysisResult] = []
        if not self.settings.analyses_dir.exists():
            return results
        for file in sorted(self.settings.analyses_dir.glob("*.json")):
            try:
                with file.open("r", encoding="utf-8") as f:
                    data = json.load(f)
                results.append(AnalysisResult.model_validate(data))
            except Exception:
                continue
        return results


