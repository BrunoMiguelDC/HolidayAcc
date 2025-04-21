package ciai.holidayacc.domain

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDate


enum class State(){
    UNDER_CONSIDERATION{
        override fun nextState() = BOOKED
    },
    BOOKED{
        override fun nextState() = OCCUPIED
    },
    OCCUPIED{
        override fun nextState() = AWAITING_REVIEW
    },
    AWAITING_REVIEW{
        override fun nextState() = CLOSED
    },
    CLOSED{
        override fun nextState() = CLOSED
    },
    CANCELED{
        override fun nextState() = CANCELED
    };
    abstract fun nextState(): State

}

@Embeddable
data class ReservationStateId(val state: State, val reservationId: ReservationId) : Serializable

/**
 * Data class that represents a state of reservation as stored in the database
 * @param name name of the reservation state
 * @param startDate start date of the reservation state
 * @param endDate end date of the reservation state
 * @param reservation reservation for which the state was given
 */
@Entity
data class ReservationStateDAO(
    @EmbeddedId
    @Schema(example = "under_consideration", description = "")
    @field:JsonProperty("reservationStateId") val reservationStateId: ReservationStateId,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1991-12-16", description = "")
    @field:JsonProperty("startDate") val startDate: LocalDate,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(example = "1997-08-29", description = "")
    @field:JsonProperty("endDate") var endDate: LocalDate?,

    @MapsId("reservationId")
    @ManyToOne
    @JoinColumns(
            JoinColumn(name = "client"),
            JoinColumn(name = "apartment"),
            JoinColumn(name="period"),
            JoinColumn(name="reservationNum")
    )
    @Schema(example = "reservation", description = "")
    @field:JsonProperty("reservation") val reservation: ReservationDAO

)
