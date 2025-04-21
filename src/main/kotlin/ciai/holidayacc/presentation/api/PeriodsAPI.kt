package ciai.holidayacc.presentation.api

import ciai.holidayacc.domain.ReservationId
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
import java.time.LocalDate

@Validated
@Tag(name = "Apartments", description = "Apartments API")
@RequestMapping("/api/apartments/{apartmentId}/periods")
interface PeriodsAPI {

    @Operation(
        summary = "Add a new period",
        operationId = "addPeriod",
        description = "Add a new period",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = Int::class))]),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "403", description = "User is not owner"),
            ApiResponse(responseCode = "404", description = "Apartment not found"),
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
    @PreAuthorize("hasRole({'MANAGER'}) or @SecurityService.canEditApartment(principal, #apartmentId)")
    fun addPeriod(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
        @Valid @RequestBody period: PeriodCreateDTO,
        principal: Principal
    ) : Int

    @Operation(
        summary = "Get an apartment period",
        operationId = "getPeriod",
        description = "Find an apartment period by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = PeriodDTO::class))]),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "404", description = "Apartment or period not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/{periodNum}"],
        produces = ["application/json"]
    )
    fun getPeriod(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
        @Valid @PathVariable(name = "periodNum", required = true) periodNum : Int
    ) : PeriodDTO

    @Operation(
        summary = "Update a existing period",
        operationId = "updatePeriod",
        description = "Update a existing period by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = PeriodUpdateDTO::class))]),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "403", description = "User is not owner"),
            ApiResponse(responseCode = "404", description = "Apartment or period not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/{periodNum}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    @PreAuthorize("hasRole({'MANAGER'}) or @SecurityService.canEditApartment(principal, #apartmentId)")
    fun updatePeriod(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
        @Valid @PathVariable(name = "periodNum", required = true) periodNum : Int,
        @Valid @RequestBody period: PeriodUpdateDTO,
        principal: Principal
    ) : PeriodUpdateDTO

    @Operation(
        summary = "Delete an apartment period",
        operationId = "deletePeriod",
        description = "Delete an apartment period",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = PeriodShortDTO::class))]),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "403", description = "User is not owner"),
            ApiResponse(responseCode = "404", description = "Apartment or period not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/{periodNum}"],
        produces = ["application/json"]
    )
    @PreAuthorize("hasRole({'MANAGER'}) or @SecurityService.canEditApartment(principal, #apartmentId)")
    fun deletePeriod(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
        @Valid @PathVariable(name = "periodNum", required = true) periodNum : Int,
        principal: Principal
    ) : PeriodShortDTO

    @Operation(
        summary = "Search periods",
        operationId = "searchPeriods",
        description = "Search periods according to search parameters",
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
    fun searchPeriods(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
        @Valid @RequestParam(value = "startDate", required = false) startDate : LocalDate?,
        @Valid @RequestParam(value = "endDate", required = false) endDate : LocalDate?,
        @Valid @RequestParam(value = "isAvailable", required = false) isAvailable : Boolean?,
        @Valid @RequestParam(value = "pg", required = true) pg : Int,
        @Valid @RequestParam(value = "len", required = true) len : Int
    ) : List<PeriodShortDTO>

    @Operation(
        summary = "Add a new reservation to the period",
        operationId = "addReservation",
        description = "Add a new reservation to the period",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = [Content(schema = Schema(implementation = String::class))]),
            ApiResponse(responseCode = "400", description = "Bad request"),
            ApiResponse(responseCode = "403", description = "Period not available"),
            ApiResponse(responseCode = "404", description = "Apartment or period not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/{periodNum}"],
        produces = ["text/plain"],
        consumes = ["application/json"]
    )
    fun addReservation(
        @Valid @PathVariable(name = "apartmentId", required = true) apartmentId : Long,
        @Valid @PathVariable(name = "periodNum", required = true) periodNum : Int,
        @Valid @RequestBody period: ReservationCreateDTO
    ) : String

}