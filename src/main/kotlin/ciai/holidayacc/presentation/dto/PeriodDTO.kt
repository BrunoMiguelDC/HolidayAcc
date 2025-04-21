package ciai.holidayacc.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

interface PeriodDTO {
    val startDate: LocalDate
    val endDate: LocalDate
    val isAvailable: Boolean
}
data class PeriodOwnerDTO (

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1991-12-16", description = "")
    @field:JsonProperty("startDate") override val startDate: LocalDate,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1997-08-29", description = "")
    @field:JsonProperty("endDate") override val endDate: LocalDate,

    @Schema(example = "true", description = "")
    @field:JsonProperty("available") override val isAvailable: Boolean,

    @Schema(example = "reservations", description = "")
    @field:JsonProperty("reservations") val reservations: List<ReservationShortDTO>

) : PeriodDTO

data class PeriodClientDTO (

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1991-12-16", description = "")
    @field:JsonProperty("startDate") override var startDate: LocalDate,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1997-08-29", description = "")
    @field:JsonProperty("endDate") override var endDate: LocalDate,

    @Schema(example = "true", description = "")
    @field:JsonProperty("available") override var isAvailable: Boolean,

    @Schema(example = "reservations", description = "")
    @field:JsonProperty("reservations") val reservations: List<ReservationLimitedDTO>

) : PeriodDTO

data class PeriodShortDTO(

    @Schema(example = "7", description = "")
    @field:JsonProperty("periodNum") val periodNum: Int,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1991-12-16", description = "")
    @field:JsonProperty("startDate") var startDate: LocalDate,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1997-08-29", description = "")
    @field:JsonProperty("endDate") var endDate: LocalDate,

    @Schema(example = "7", description = "")
    @field:JsonProperty("availableDays") var availableDays: Int,

    )

data class PeriodCreateDTO(

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1991-12-16", description = "")
    @field:JsonProperty("startDate") var startDate: LocalDate,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1997-08-29", description = "")
    @field:JsonProperty("endDate") var endDate: LocalDate

)

data class PeriodUpdateDTO(

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1991-12-16", description = "")
    @field:JsonProperty("startDate") var startDate: LocalDate?,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1997-08-29", description = "")
    @field:JsonProperty("endDate") var endDate: LocalDate?,

    @Schema(example = "1991-12-20", description = "")
    @field:JsonProperty("availableFrom") var availableDays: Int?

)
