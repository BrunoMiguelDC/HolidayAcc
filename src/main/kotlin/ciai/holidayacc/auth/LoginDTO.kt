package ciai.holidayacc.auth

data class LoginDTO(val username: String, val password: String){
    constructor() : this("a", "b") {}
}
