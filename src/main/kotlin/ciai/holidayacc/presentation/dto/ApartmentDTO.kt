package ciai.holidayacc.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class ApartmentDTO(

    @Schema(example = "The BatCave", description = "")
    @field:JsonProperty("name") val name: String,

    @Schema(example = "dark", description = "")
    @field:JsonProperty("description") val description: String,

    @Schema(example = "unknown", description = "")
    @field:JsonProperty("location") val location: String,

    @Schema(example = "[\"Batcomputer\", \"Batmobile\", \"JL Teleporter\"]", description = "")
    @field:JsonProperty("amenities") val amenities: MutableSet<String>,

    @Schema(example = "[\"somepicatsomeurl1.jpg\", \"somepicatsomeurl2.jpg\"]", description = "")
    @field:JsonProperty("pictures") val pictures: MutableSet<String>,

    @Schema(example = "52", description = "")
    @field:JsonProperty("price") val price: Float,

    @Schema(example = "true", description = "")
    @field:JsonProperty("available") val isAvailable: Boolean,

    @Schema(example = "7", description = "")
    @field:JsonProperty("maxGuests") var maxGuests: Int,

    @Schema(example = "owner", description = "")
    @field:JsonProperty("owner") val owner: UserShortDTO
)

data class ApartmentShortDTO(

    @Schema(example = "52", description = "")
    @field:JsonProperty("apartmentId") val apartmentId: Long,

    @Schema(example = "The BatCave", description = "")
    @field:JsonProperty("name") val name: String,

    @Schema(example = "unknown", description = "")
    @field:JsonProperty("location") val location: String,

    @Schema(example = "somepicatsomeurl1.jpg", description = "")
    @field:JsonProperty("picture") val picture: String,

    @Schema(example = "52", description = "")
    @field:JsonProperty("price") val price: Float,

    @Schema(example = "true", description = "")
    @field:JsonProperty("available") val isAvailable: Boolean,

    @Schema(example = "7", description = "")
    @field:JsonProperty("maxGuests") var maxGuests: Int,
)

data class ApartmentShortInfoDTO(

    @Schema(example = "52", description = "")
    @field:JsonProperty("apartmentId") val apartmentId: Long,

    @Schema(example = "The BatCave", description = "")
    @field:JsonProperty("name") val name: String,

    @Schema(example = "unknown", description = "")
    @field:JsonProperty("location") val location: String,

    @Schema(example = "52", description = "")
    @field:JsonProperty("price") val price: Float,

)

data class ApartmentCreateDTO(
    @Schema(example = "The BatCave", description = "")
    @field:JsonProperty("name") val name: String,

    @Schema(example = "dark", description = "")
    @field:JsonProperty("description") val description: String,

    @Schema(example = "unknown", description = "")
    @field:JsonProperty("location") val location: String,

    @Schema(example = "[\"Batcomputer\", \"Batmobile\", \"JL Teleporter\"]", description = "")
    @field:JsonProperty("amenities") val amenities: MutableSet<String>,

    @Schema(example = "[\"somepicatsomeurl1.jpg\", \"somepicatsomeurl2.jpg\"]", description = "")
    @field:JsonProperty("pictures") val pictures: MutableSet<String>,

    @Schema(example = "52", description = "")
    @field:JsonProperty("price") val price: Float,

    @Schema(example = "true", description = "")
    @field:JsonProperty("available") val isAvailable: Boolean,

    @Schema(example = "7", description = "")
    @field:JsonProperty("maxGuests") var maxGuests: Int,

    @Schema(example = "masternoob69", description = "")
    @field:JsonProperty("ownerUsername") val ownerUsername: String
)

data class ApartmentUpdateDTO (
    @Schema(example = "The BatCave", description = "")
    @field:JsonProperty("name") val name: String?,

    @Schema(example = "dark", description = "")
    @field:JsonProperty("description") val description: String?,

    @Schema(example = "[\"Batcomputer\", \"Batmobile\", \"JL Teleporter\"]", description = "")
    @field:JsonProperty("amenities") val amenities: MutableSet<String>?,

    @Schema(example = "[\"somepicatsomeurl1.jpg\", \"somepicatsomeurl2.jpg\"]", description = "")
    @field:JsonProperty("pictures") val pictures: MutableSet<String>?,

    @Schema(example = "52", description = "")
    @field:JsonProperty("price") val price: Float?,

    @Schema(example = "true", description = "")
    @field:JsonProperty("available") val isAvailable: Boolean?,

    @Schema(example = "7", description = "")
    @field:JsonProperty("maxGuests") var maxGuests: Int?,

)

data class ApartmentDeleteDTO(
    @Schema(example = "52", description = "")
    @field:JsonProperty("apartmentId") val apartmentId: Long,

    @Schema(example = "The BatCave", description = "")
    @field:JsonProperty("name") val name: String,

    @Schema(example = "unknown", description = "")
    @field:JsonProperty("location") val location: String,

    @Schema(example = "owner", description = "")
    @field:JsonProperty("owner") val owner: UserShortDTO
)

data class ApartmentListDTO(
    @Schema(example = "52", description = "")
    @field:JsonProperty("apartmentId") val apartmentId: Long,

    @Schema(example = "The BatCave", description = "")
    @field:JsonProperty("name") val name: String,

    @Schema(example = "unknown", description = "")
    @field:JsonProperty("location") val location: String,

    @Schema(example = "really dark cave with lots of bats", description = "")
    @field:JsonProperty("description") val description: String,

    @Schema(example = "somepicatsomeurl1.jpg", description = "")
    @field:JsonProperty("picture") val picture: String,

    @Schema(example = "52", description = "")
    @field:JsonProperty("price") val price: Float,

    @Schema(example = "true", description = "")
    @field:JsonProperty("available") val isAvailable: Boolean,

    @Schema(example = "7", description = "")
    @field:JsonProperty("maxGuests") var maxGuests: Int,

    @Schema(example = "owner", description = "")
    @field:JsonProperty("owner") val owner: UserShortDTO

)