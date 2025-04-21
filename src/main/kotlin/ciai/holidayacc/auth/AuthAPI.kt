package ciai.holidayacc.auth

import ciai.holidayacc.presentation.dto.ApartmentCreateDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Validated
@Tag(name = "Auth", description = "Auth API")
interface AuthAPI {

    @Operation(
        summary = "Login user",
        operationId = "login",
        description = "Login user",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "400", description = "Invalid request"),
            ApiResponse(responseCode = "401", description = "Login failed"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ],
        //security = [ SecurityRequirement(name = "petstore_auth", scopes = [ "write:pets", "read:pets" ]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/login"],
        consumes = ["application/json"]
    )
    fun login(
        @Valid @RequestBody loginDTO: LoginDTO
    )

}