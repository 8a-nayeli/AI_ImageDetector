from pydantic import BaseModel, ConfigDict, Field
from domain.image_pair import ImageInfo


class PairSummary(BaseModel):
    pair_id: str = Field(serialization_alias="pairId", validation_alias="pairId")
    real_image: ImageInfo = Field(
        serialization_alias="realImage", validation_alias="realImage"
    )
    fake_image: ImageInfo = Field(
        serialization_alias="fakeImage", validation_alias="fakeImage"
    )

    model_config = ConfigDict(populate_by_name=True)


