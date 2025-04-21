package ciai.holidayacc.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class ReviewDTO(
    @Schema(example = "Some review about some apartment", description = "")
    @field:JsonProperty("text") val text: String?,

    @Schema(example = "5.0", description = "")
    @field:JsonProperty("rating") val rating: Float?

)

data class ReviewCreateDTO(
    @Schema(example = "Some review about some apartment", description = "")
    @field:JsonProperty("text") val text: String,

    @Schema(example = "5.0", description = "")
    @field:JsonProperty("rating") val rating: Float,

    @Schema(example = "date", description = "")
    @field:JsonProperty("date") val date: LocalDate,


    )

data class ReviewApartmentsListDTO(

    @Schema(example = "Some review about some apartment", description = "")
    @field:JsonProperty("text") val text: String,

    @Schema(example = "5.0", description = "")
    @field:JsonProperty("rating") val rating: Float,

    @Schema(example = "author", description = "")
    @field:JsonProperty("author") val author: UserShortDTO

)
data class ReviewUsersListDTO(

    @Schema(example = "Some review about some apartment", description = "")
    @field:JsonProperty("text") val text: String,

    @Schema(example = "5.0", description = "")
    @field:JsonProperty("rating") val rating: Float,

    @Schema(example = "apartment", description = "")
    @field:JsonProperty("apartment") val apartment: ApartmentShortInfoDTO

)
