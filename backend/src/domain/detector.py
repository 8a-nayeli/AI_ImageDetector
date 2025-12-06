from pydantic import BaseModel, ConfigDict, Field


class DetectorDescriptor(BaseModel):
    name: str
    description: str | None = None
    input_type: str = Field(
        default="features",
        description="Expected input type, e.g., 'features' or 'gradients'.",
    )
    output_score_range: tuple[float, float] | None = Field(
        default=None, serialization_alias="outputScoreRange", validation_alias="outputScoreRange"
    )

    model_config = ConfigDict(populate_by_name=True)


