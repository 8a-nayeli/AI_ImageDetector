from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.staticfiles import StaticFiles
from api.routes import api_router
from api.dependencies import settings
from utils.logging import configure_logging


configure_logging(settings.log_level)

app = FastAPI(
    title="Image Analysis Backend",
    version="0.1.0",
    description="Uploads image pairs, computes gradients/features, runs detectors.",
)

# CORS for frontend
app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.allowed_origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Serve stored artifacts (images, gradients, JSON) via /static
app.mount("/static", StaticFiles(directory=settings.data_dir, html=True), name="static")

app.include_router(api_router)


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok"}


