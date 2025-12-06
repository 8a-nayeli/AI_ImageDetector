from functools import lru_cache
from pathlib import Path
from pydantic import Field, HttpUrl
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """Application settings loaded from environment or .env."""

    data_dir: Path = Field(default=Path("data"), validation_alias="DATA_DIR")
    log_level: str = Field(default="INFO", validation_alias="LOG_LEVEL")
    public_base_url: HttpUrl = Field(
        default="http://localhost:8000",
        validation_alias="PUBLIC_BASE_URL",
    )
    allowed_origins: list[str] = Field(
        default_factory=lambda: ["http://localhost:3000"],
        validation_alias="ALLOWED_ORIGINS",
        description="List of origins allowed for CORS.",
    )

    @property
    def images_real_dir(self) -> Path:
        return self.data_dir / "images" / "real"

    @property
    def images_fake_dir(self) -> Path:
        return self.data_dir / "images" / "fake"

    @property
    def gradients_dir(self) -> Path:
        return self.data_dir / "gradients"

    @property
    def analyses_dir(self) -> Path:
        return self.data_dir / "analyses"

    @property
    def examples_dir(self) -> Path:
        return self.data_dir / "examples"

    model_config = {
        "env_file": ".env",
        "env_file_encoding": "utf-8",
        "json_schema_extra": {
            "ALLOWED_ORIGINS": ["http://localhost:3000"],
        },
    }


@lru_cache(maxsize=1)
def get_settings() -> Settings:
    settings = Settings()
    settings.data_dir.mkdir(parents=True, exist_ok=True)
    settings.images_real_dir.mkdir(parents=True, exist_ok=True)
    settings.images_fake_dir.mkdir(parents=True, exist_ok=True)
    settings.gradients_dir.mkdir(parents=True, exist_ok=True)
    settings.analyses_dir.mkdir(parents=True, exist_ok=True)
    settings.examples_dir.mkdir(parents=True, exist_ok=True)
    return settings


