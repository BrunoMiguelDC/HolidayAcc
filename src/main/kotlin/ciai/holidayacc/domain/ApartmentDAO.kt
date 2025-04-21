package ciai.holidayacc.domain

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.apache.commons.lang3.builder.ToStringExclude
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType

/**
 * Data class that represents an apartment as stored in the database
 * @param apartmentId id of the apartment
 * @param name name of the apartment
 * @param description description of the apartment
 * @param location location of the apartment
 * @param amenities list of amenities of the apartment
 * @param pictures list of urls of the pictures of the apartment
 * @param price price per night of the apartment
 * @param isAvailable availability of the apartment
 * @param periods list of periods of the pictures of the apartment
 * @param owner owner of the apartment
 */
@Entity
data class ApartmentDAO(

    @Id
    @GeneratedValue
    @Schema(example = "52", description = "")
    @field:JsonProperty("apartmentId") val apartmentId: Long,

    @Schema(example = "The BatCave", description = "")
    @field:JsonProperty("name") var name: String,

    @Lob
    @Schema(example = "dark", description = "")
    @field:JsonProperty("description") var description: String,

    @Schema(example = "unknown", description = "")
    @field:JsonProperty("location") val location: String,

    @ElementCollection
    @Schema(example = "[\"Batcomputer\", \"Batmobile\", \"JL Teleporter\"]", description = "")
    @field:JsonProperty("amenities") var amenities: MutableSet<String>,

    @ElementCollection
    @Schema(example = "[\"somepicatsomeurl1.jpg\", \"somepicatsomeurl2.jpg\"]", description = "")
    @field:JsonProperty("pictures") var pictures: MutableSet<String>,

    @Schema(example = "52", description = "")
    @field:JsonProperty("price") var price: Float,

    @Schema(example = "true", description = "")
    @field:JsonProperty("available") var isAvailable: Boolean,

    @Schema(example = "7", description = "")
    @field:JsonProperty("maxGuests") var maxGuests: Int,

    @OneToMany(mappedBy = "apartment", cascade = [jakarta.persistence.CascadeType.ALL])
    @Schema(example = "periods", description = "")
    @field:JsonProperty("periods") val periods: MutableList<PeriodDAO>,

    @ManyToOne
    @ToStringExclude
    @Cascade(CascadeType.SAVE_UPDATE)
    @Schema(example = "owner", description = "")
    @field:JsonProperty("owner") val owner: UserDAO

)
