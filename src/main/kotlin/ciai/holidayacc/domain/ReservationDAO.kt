package ciai.holidayacc.domain

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDate

@Embeddable
data class ReservationId(val reservationNum: Int, val clientUsername: String, val periodId: PeriodId) : Serializable

/**
 * Data class that represents a reservation made by a user for an apartment as stored in the database
 * @param reservationNum number of the reservation
 * @param startDate start date of the reservation
 * @param endDate end date of the reservation
 * @param client user that made the reservation
 * @param period period for which the reservation was made
 * @param states history of states of the reservation
 * @param review review of the reservation
 */
@Entity
data class ReservationDAO(

    @EmbeddedId
    @Schema(example = "7", description = "")
    @field:JsonProperty("reservationId") val reservationId: ReservationId,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1991-12-16", description = "")
    @field:JsonProperty("startDate") var startDate: LocalDate,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1997-08-29", description = "")
    @field:JsonProperty("endDate") var endDate: LocalDate,

    @Schema(example = "7", description = "")
    @field:JsonProperty("numGuests") var numGuests: Int,

    @MapsId("clientUsername")
    @ManyToOne
    @JoinColumn(name="username")
    @Schema(example = "client", description = "")
    @field:JsonProperty("client") val client: UserDAO,

    @MapsId("periodId")
    @ManyToOne
    @JoinColumns(
            JoinColumn(name = "apartment"),
            JoinColumn(name = "periodNum")
    )
    @Schema(example = "period", description = "")
    @field:JsonProperty("period") val period: PeriodDAO,

    @OneToMany(mappedBy = "reservation", cascade = [CascadeType.ALL])
    @Schema(example = "states", description = "")
    @field:JsonProperty("states") val states: MutableList<ReservationStateDAO>,

    @OneToOne(mappedBy = "reservation", cascade = [CascadeType.ALL])
    @JoinColumn(name = "reservation")
    @Schema(example = "review", description = "")
    @field:JsonProperty("review") var review: ReviewDAO?

)
