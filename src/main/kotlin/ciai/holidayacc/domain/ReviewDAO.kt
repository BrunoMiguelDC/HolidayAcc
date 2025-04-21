package ciai.holidayacc.domain

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.io.Serializable

@Embeddable
data class ReviewId(val reviewNum: Int, val reservationId: ReservationId) : Serializable

/**
 * Data class that represents a review made by a user for a reservation as stored in the database
 * @param text text of the review
 * @param rating rating of the review
 * @param reservation reservation for which the review was written
 */
@Entity
data class ReviewDAO(

    @EmbeddedId
    @Schema(example = "Some review about some apartment", description = "")
    @field:JsonProperty("reviewId") val reviewId: ReviewId,

    @Schema(example = "Some review about some apartment", description = "")
    @field:JsonProperty("text") val text: String,

    @Schema(example = "5.0", description = "")
    @field:JsonProperty("rating") val rating: Float,

    @MapsId("reservationId")
    @OneToOne
    @Schema(example = "reservation", description = "")
    @field:JsonProperty("reservation") val reservation: ReservationDAO

)
