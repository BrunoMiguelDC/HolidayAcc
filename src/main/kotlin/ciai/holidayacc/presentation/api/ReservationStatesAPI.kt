package ciai.holidayacc.presentation.api

import ciai.holidayacc.presentation.dto.ReservationStateDTO
import ciai.holidayacc.presentation.dto.ReservationStateUpdateDTO
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
import java.security.Principal

@Validated
@Tag(name = "Reservations", description = "Reservations API")
@RequestMapping("/api/reservations/{reservationId}/states")
interface ReservationStatesAPI {

    @Operation(
            summary = "Update the state of a reservation",
            operationId = "updateState",
            description = "Updates the state of a reservation whose id is reservationId.",
            responses = [
                ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = ReservationStateUpdateDTO::class))]),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "404", description = "Reservation not found"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ],
            //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
            method = [RequestMethod.PUT],
            value = [""],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    @PreAuthorize("hasRole({'MANAGER'}) or @SecurityService.canChangeState(principal, #reservationId, #state)")
    fun updateState(
            @Valid @PathVariable(name = "reservationId", required = true) reservationId : String,
            @Valid @RequestBody state: ReservationStateUpdateDTO,
            principal: Principal
    ) : ReservationStateUpdateDTO

    @Operation(
            summary = "List state history of a reservation",
            operationId = "getReservationStateHistory",
            description = "Returns a list of the states of a reservation whose id is reservationId.",
            responses = [
                ApiResponse(responseCode = "200", description = "Successful operation"),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "404", description = "Reservation not found"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ],
            //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = [""],
            produces = ["application/json"]
    )
    fun listReservationStateHistory(
            @Valid @PathVariable(name = "reservationId", required = true) reservationId : String
    ) : List<ReservationStateDTO>

}