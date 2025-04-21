package ciai.holidayacc.domain

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDate

@Embeddable
data class PeriodId (val periodNum: Int, val apartmentPK: Long) : Serializable

/**
 * Data class that represents a period of an apartment as stored in the database
 * @param periodNum number of the period
 * @param startDate start date of the period
 * @param endDate end date of the period
 * @param apartment apartment of the period
 * @param reservations list of reservations made for the period
 */
@Entity
data class PeriodDAO(

    @EmbeddedId
    @Schema(example = "7", description = "")
    @field:JsonProperty("periodId") val periodId: PeriodId,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1991-12-16", description = "")
    @field:JsonProperty("startDate") var startDate: LocalDate,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1997-08-29", description = "")
    @field:JsonProperty("endDate") var endDate: LocalDate,

    @Schema(example = "7", description = "")
    @field:JsonProperty("availableDays") var availableDays: Int,

    @ManyToOne
    @MapsId("apartmentPK")
    @JoinColumn(name= "apartmentId")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @Schema(example = "apartment", description = "")
    @field:JsonProperty("apartment") val apartment: ApartmentDAO,

    @OneToMany(mappedBy = "period", cascade = [CascadeType.ALL])
    @Schema(example = "reservations", description = "")
    @field:JsonProperty("reservations") val reservations: MutableList<ReservationDAO>

)
