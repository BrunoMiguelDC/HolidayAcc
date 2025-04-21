package ciai.holidayacc.presentation.api

import ciai.holidayacc.presentation.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Validated
@Tag(name = "Reservations", description = "Reservations API")
@RequestMapping("/api/reservations")
interface ReservationsAPI {

    @Operation(
            summary = "Get a reservation",
            operationId = "getReservation",
            description = "Find a reservation by id",
            responses = [
                ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = ReservationDTO::class))]),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "404", description = "Reservation not found") ,
                ApiResponse(responseCode = "500", description = "Internal server error")
            ],
            //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/{reservationId}"],
            produces = ["application/json"]
    )
    fun getReservation(
            @Valid @PathVariable(name = "reservationId", required = true) reservationId : String
    ) : ReservationDTO


    @Operation(
            summary = "Cancel a reservation",
            operationId = "deleteReservation",
            description = "Cancel a reservation",
            responses = [
                ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = ReservationLimitedDTO::class))]),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "404", description = "Reservation not found"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ],
            //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
            method = [RequestMethod.DELETE],
            value = ["/{reservationId}"],
            produces = ["application/json"]
    )
    @PreAuthorize("hasRole({'MANAGER'}) or @SecurityService.canDeleteReservation(principal, #reservationId)")
    fun deleteReservation(
            @Valid @PathVariable(name = "reservationId", required = true) reservationId : String,
            principal: Principal
    ) : ReservationLimitedDTO

    @Operation(
        summary = "Create a review for a reservation",
        operationId = "addReview",
        description = "Create a review for a reservation",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "404", description = "Reservation not found") ,
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/{reservationId}"],
        consumes = ["application/json"]
    )
    fun addReview(
        @Valid @PathVariable(name = "reservationId", required = true) reservationId : String,
        @Valid @RequestBody reviewDTO: ReviewCreateDTO
    )

}