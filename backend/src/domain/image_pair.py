from pydantic import BaseModel, ConfigDict, Field


class ImageInfo(BaseModel):
    id: str
    url: str
    source: str | None = None
    generator_type: str | None = Field(
        default=None,
        serialization_alias="generatorType",
        validation_alias="generatorType",
    )
    prompt: str | None = None

    model_config = ConfigDict(populate_by_name=True)


class ImagePair(BaseModel):
    pair_id: str = Field(serialization_alias="pairId", validation_alias="pairId")
    real_image: ImageInfo = Field(
        serialization_alias="realImage", validation_alias="realImage"
    )
    fake_image: ImageInfo = Field(
        serialization_alias="fakeImage", validation_alias="fakeImage"
    )

    model_config = ConfigDict(populate_by_name=True)


