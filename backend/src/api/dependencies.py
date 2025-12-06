from config.settings import get_settings, Settings
from data_access.file_storage import FileStorage
from data_access.analysis_repository import AnalysisRepository
from data_access.pair_repository import PairRepository
from detection.gradient_stats_detector import GradientStatsDetector
from detection.histogram_chi2_detector import HistogramChi2Detector
from detection.std_ratio_detector import StdRatioDetector


settings: Settings = get_settings()
file_storage = FileStorage(settings)
analysis_repository = AnalysisRepository(settings)
pair_repository = PairRepository(settings)
detectors = [
    GradientStatsDetector(),
    HistogramChi2Detector(),
    StdRatioDetector(),
]


def get_settings_dep() -> Settings:
    return settings


def get_storage() -> FileStorage:
    return file_storage


def get_analysis_repo() -> AnalysisRepository:
    return analysis_repository


def get_pair_repo() -> PairRepository:
    return pair_repository


def get_detectors() -> list:
    return detectors


