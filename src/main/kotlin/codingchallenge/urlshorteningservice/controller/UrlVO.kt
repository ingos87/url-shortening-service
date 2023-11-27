package codingchallenge.urlshorteningservice.controller

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UrlVO(

    @Schema(
        description = "A URL string to be shortened",
        example = "https://i-see-you.com",
        )
    @field:Size(max = 2048, message = "URL length must not exceed 2048 characters") // not working...
    @field:NotNull
    val url: String,
)