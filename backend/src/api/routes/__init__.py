from fastapi import APIRouter
from .analyze_pair import router as analyze_router
from .pairs import router as pairs_router
from .examples import router as examples_router


api_router = APIRouter()
api_router.include_router(analyze_router)
api_router.include_router(pairs_router)
api_router.include_router(examples_router)


