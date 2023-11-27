package codingchallenge.urlshorteningservice.controller

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UrlIdentifierVO(

    @Schema(
        description = "An identifier for URL saved in this service",
        example = "420d",
        )
    @field:Size(max = 32)
    @field:NotNull
    val urlIdentifier: String,
)