package ciai.holidayacc.auth

import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController : AuthAPI {
    override fun login(loginDTO: LoginDTO) {}
}