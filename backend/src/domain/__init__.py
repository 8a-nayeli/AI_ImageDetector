"""
Domain layer: core data structures shared across layers.
"""

from .image_pair import ImageInfo, ImagePair
from .analysis_result import (
    AnalysisImages,
    AnalysisResult,
    DetectorResult,
    FeatureVector,
    GradientArtifacts,
)
from .detector import DetectorDescriptor

__all__ = [
    "ImageInfo",
    "ImagePair",
    "AnalysisImages",
    "AnalysisResult",
    "GradientArtifacts",
    "FeatureVector",
    "DetectorResult",
    "DetectorDescriptor",
]

