from abc import ABC, abstractmethod
from domain.analysis_result import FeatureVector, DetectorResult
from domain.detector import DetectorDescriptor


class BaseDetector(ABC):
    """Interface for detectors operating on extracted features."""

    def __init__(self, name: str, description: str | None = None):
        self.descriptor = DetectorDescriptor(
            name=name,
            description=description,
            input_type="features",
            output_score_range=(0.0, 1.0),
        )

    @abstractmethod
    def predict(self, features: FeatureVector) -> DetectorResult:
        raise NotImplementedError


