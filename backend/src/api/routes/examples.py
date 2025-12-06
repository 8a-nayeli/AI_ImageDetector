from fastapi import APIRouter, Depends
from api.dependencies import get_analysis_repo
from data_access.analysis_repository import AnalysisRepository
from domain.analysis_result import AnalysisResult

router = APIRouter(prefix="/api/examples", tags=["examples"])


@router.get("", response_model=list[AnalysisResult])
def list_examples(
    analysis_repo: AnalysisRepository = Depends(get_analysis_repo),
):
    return analysis_repo.list_all()[:5]


