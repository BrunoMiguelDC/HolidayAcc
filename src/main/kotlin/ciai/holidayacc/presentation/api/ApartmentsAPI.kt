package ciai.holidayacc.presentation.api

import ciai.holidayacc.domain.State
import ciai.holidayacc.presentation.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal
import java.time.LocalDate


@Validated
@Tag(name = "Apartments", description = "Apartments API")
@RequestMapping("/api/apartments")
interface ApartmentsAPI {

    @Operation(
        summary = "Add a new apartment",
        operationId = "addApartment",
        description = "Add a new apartment",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = Long::class))]),
            ApiResponse(responseCode = "400", description = "Invalid request"),
            ApiResponse(responseCode = "500", description = "Internal server error")
                    ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = [""],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun addApartment(
        @Valid @RequestBody apartment: ApartmentCreateDTO
    ) : Long

    @Operation(
        summary = "Get an apartment",
        operationId = "getApartment",
        description = "Find an apartment by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = ApartmentDTO::class))]),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "404", description = "Apartment not found") ,
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/{apartmentId}"],
        produces = ["application/json"]
    )
    fun getApartment(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long
    ) : ApartmentDTO

    @Operation(
        summary = "Update an existing apartment",
        operationId = "updateApartment",
        description = "Update an existing apartment by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = ApartmentUpdateDTO::class))]),
            ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            ApiResponse(responseCode = "404", description = "Apartment not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/{apartmentId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    @PreAuthorize("hasRole({'MANAGER'}) or @SecurityService.canEditApartment(principal, #apartmentId)")
    fun updateApartment(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
        @Valid @RequestBody apartment: ApartmentUpdateDTO,
        principal: Principal
    ) : ApartmentUpdateDTO

    @Operation(
        summary = "Delete an apartment",
        operationId = "deleteApartment",
        description = "Delete an apartment",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = ApartmentDeleteDTO::class))]),
            ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            ApiResponse(responseCode = "404", description = "Apartment not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/{apartmentId}"],
        produces = ["application/json"]
    )
    @PreAuthorize("hasRole({'MANAGER'}) or @SecurityService.canEditApartment(principal, #apartmentId)")
    fun deleteApartment(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
        principal: Principal
    ) : ApartmentDeleteDTO

    @Operation(
        summary = "Search apartments",
        operationId = "searchApartments",
        description = "Search apartments according to search parameters",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "400", description = "Invalid request"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = [""],
        produces = ["application/json"]
    )
    fun searchApartments(
        @Valid @RequestParam(value = "name", required = false) name : String?,
        @Valid @RequestParam(value = "location", required = false) location : String?,
        @Valid @RequestParam(value = "amenities", required = false) amenities : List<String>?,
        @Valid @RequestParam(value = "price", required = false) price : Float?,
        @Valid @RequestParam(value = "startDate", required = false) startDate : LocalDate?,
        @Valid @RequestParam(value = "endDate", required = false) endDate : LocalDate?,
        @Valid @RequestParam(value = "isAvailable", required = false) isAvailable : Boolean?,
        @Valid @RequestParam(value = "owner", required = false) owner : String?,
        @Valid @RequestParam(value = "pg", required = true) pg : Int,
        @Valid @RequestParam(value = "len", required = true) len : Int
    ) : PageableListDTO<ApartmentListDTO>

    @Operation(
            summary = "Returns a list of the reservations of an apartment",
            operationId = "searchApartmentReservations",
            description = "Search reservations according to search parameters",
            responses = [
                ApiResponse(responseCode = "200", description = "Successful operation"),
                ApiResponse(responseCode = "400", description = "Invalid request"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ],
            //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/{apartmentId}/reservations"],
            produces = ["application/json"]
    )
    fun searchApartmentReservations(
            @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
            @Valid @RequestParam(value = "startDate", required = false) startDate : LocalDate?,
            @Valid @RequestParam(value = "endDate", required = false) endDate : LocalDate?,
            @Valid @RequestParam(value = "client", required = false) client : String?,
            @Valid @RequestParam(value = "states", required = false) states : List<State>?,
            @Valid @RequestParam(value = "pg", required = true) pg : Int,
            @Valid @RequestParam(value = "len", required = true) len : Int
    ) : PageableListDTO<ReservationApartmentsListDTO>

    @Operation(
        summary = "Returns a list of reviews of an apartment",
        operationId = "searchApartmentReviews",
        description = "Search reviews according to search parameters",
        responses = [
            ApiResponse(responseCode = "200", description = "successful operation"),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "404", description = "Apartment not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/{apartmentId}/reviews"],
        produces = ["application/json", "application/xml"]
    )
    fun searchApartmentsReviews(@PathVariable("apartmentId") apartmentId: Long,
                                @Valid @RequestParam(value = "rating", required = false) rating: Int?,
                                @Valid @RequestParam(value = "author", required = false) authorUsername: String?,
                                @Valid @RequestParam(value = "pg", required = true) pg : Int,
                                @Valid @RequestParam(value = "len", required = true) len : Int
    ):List<ReviewApartmentsListDTO>

}