package ciai.holidayacc.presentation.api

import ciai.holidayacc.domain.State
import ciai.holidayacc.presentation.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@Validated
@Tag(name = "Users", description = "Users API")
@RequestMapping("/api/users")
interface UsersAPI {
    @Operation(
            summary = "Adds a user",
            operationId = "addUser",
            description = "Adds a user",
            responses = [
                ApiResponse(responseCode = "200", description = "Successful operation"),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ]
    )
    @RequestMapping(
            method = [RequestMethod.POST],
            value = [""],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun addUser(@Valid @RequestBody user: UserDTO
    ): String

    @Operation(
            summary = "Returns the user by its username",
            operationId = "getUser",
            description = "Returns the user by its username",
            responses = [
                ApiResponse(responseCode = "200", description = "successful operation"),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "404", description = "User not found"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/{username}"],
            produces = ["application/json"]
    )
    fun getUser(@PathVariable("username") username: String
    ): UserPasswordlessDTO

    @Operation(
            summary = "Update an existing user",
            operationId = "updateUser",
            description = "Updates an existing user",
            responses = [
                ApiResponse(responseCode = "200", description = "Successful operation"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ]
    )
    @RequestMapping(
            method = [RequestMethod.PUT],
            value = ["/{username}"],
            consumes = ["application/json"]
    )
    fun updateUser(@PathVariable("username") username: String, @Valid @RequestBody user: UserUpdateDTO
    ): UserPasswordlessDTO

    @Operation(
            summary = "Delete user",
            operationId = "deleteUser",
            description = "Deletes a user",
            responses = [
                ApiResponse(responseCode = "200", description = "successful operation"),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "404", description = "User not found"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ]
    )
    @RequestMapping(
            method = [RequestMethod.DELETE],
            value = ["/{username}"]
    )
    fun deleteUser(@PathVariable("username") username: String
    ): UserDeleteDTO

    @Operation(
            summary = "Returns a list of users in the application system",
            operationId = "searchUsers",
            description = "Returns a list of users in the application system",
            responses = [
                ApiResponse(responseCode = "200", description = "successful operation")
            ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = [""],
            produces = ["application/json"]
    )
    fun searchUsers(@Valid @RequestParam(value = "name", required = false) name: String?,
                    @Valid @RequestParam(value = "email", required = false) email: String?,
                    @Valid @RequestParam(value = "phone", required = false) phone: String?,
                    @Valid @RequestParam(value = "pg", required = true) pg : Int,
                    @Valid @RequestParam(value = "len", required = true) len : Int
    ):PageableListDTO<UserListDTO>

    @Operation(
            summary = "Returns a list of reservations of a user",
            operationId = "searchUserReservations",
            description = "Search reservations according to search parameters",
            responses = [
                ApiResponse(responseCode = "200", description = "successful operation"),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "404", description = "User not found"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/{username}/reservations"],
            produces = ["application/json"]
    )
    fun searchUserReservations(@PathVariable("username") username: String,
                               @Valid @RequestParam(value = "startDate", required = false) startDate: LocalDate?,
                               @Valid @RequestParam(value = "endDate", required = false) endDate: LocalDate?,
                               @Valid @RequestParam(value = "apartment", required = false) apartment: Long?,
                               @Valid @RequestParam(value = "states", required = false) states: List<State>?,
                               @Valid @RequestParam(value = "pg", required = true) pg : Int,
                               @Valid @RequestParam(value = "len", required = true) len : Int
    ):PageableListDTO<ReservationUsersListDTO>

    @Operation(
            summary = "Returns a list of reviews of a user",
            operationId = "searchUserReviews",
            description = "Search reviews according to search parameters",
            responses = [
                ApiResponse(responseCode = "200", description = "successful operation"),
                ApiResponse(responseCode = "400", description = "Bad request"),
                ApiResponse(responseCode = "404", description = "User not found"),
                ApiResponse(responseCode = "500", description = "Internal server error")
            ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/{username}/reviews"],
            produces = ["application/json"]
    )
    fun searchUserReviews(@PathVariable("username") username: String,
                          @Valid @RequestParam(value = "rating", required = false) rating: Int?,
                          @Valid @RequestParam(value = "apartment", required = false) apartmentId: Long?,
                          @Valid @RequestParam(value = "pg", required = true) pg : Int,
                          @Valid @RequestParam(value = "len", required = true) len : Int
    ):List<ReviewUsersListDTO>


}