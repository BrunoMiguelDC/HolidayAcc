package ciai.holidayacc.presentation.dto

data class UserDTO(
        val username:String,
        val password:String,
        val name: String,
        val email: String,
        val picture: String,
        val phone: String,
        val apartments: List<ApartmentShortDTO>
)

data class UserPasswordlessDTO(
        val username:String,
        val name: String,
        val email: String,
        val picture: String,
        val phone: String,
        val apartments: List<ApartmentShortDTO>
)

data class UserUpdateDTO(
        val password:String?,
        val name: String?,
        val email: String?,
        val picture: String?,
        val phone: String?,
)

data class UserShortDTO(
        val username:String,
        val name: String,
        val email: String,
        val phone: String,
)

data class UserDeleteDTO(
        val username: String,
        val name: String,
        val email: String
)

data class UserListDTO(
        val username:String,
        val name: String,
        val email: String,
        val picture: String,
        val phone: String,
)
