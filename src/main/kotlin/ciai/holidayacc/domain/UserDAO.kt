package ciai.holidayacc.domain

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

/**
 * Data class that represents a user as stored in the database
 * @param username username of the user
 * @param password password of the user
 * @param name name of the user
 * @param email email of the user
 * @param picture url of the picture of the user
 * @param phone phone number of the user
 * @param apartments list of apartments of user
 * @param reservations list of reservations of user
 */
@Entity
data class UserDAO(
    @Id
    @Schema(example = "noobmaster69", description = "")
    @field:JsonProperty("username") val username: String,

    @Schema(example = "thestrongestavenger", description = "")
    @field:JsonProperty("password") var password: String,

    @Schema(example = "Aaron", description = "")
    @field:JsonProperty("name") var name: String,

    @Schema(example = "aaron@email.com", description = "")
    @field:JsonProperty("email") var email: String,

    @Schema(example = "somepicatsomeurl.jpg", description = "")
    @field:JsonProperty("picture") var picture: String,

    @Schema(example = "636-555-3226", description = "")
    @field:JsonProperty("phone") var phone: String,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    @Schema(example = "apartments", description = "")
    @field:JsonProperty("apartments") val apartments: MutableList<ApartmentDAO>,

    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL])
    @Schema(example = "reservations", description = "")
    @field:JsonProperty("reservations") var reservations: MutableList<ReservationDAO>

)
