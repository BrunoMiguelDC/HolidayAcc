package ciai.holidayacc.application

import ciai.holidayacc.application.utils.DomainConverter
import ciai.holidayacc.application.utils.Result
import ciai.holidayacc.domain.*
import ciai.holidayacc.presentation.dto.*
import ciai.holidayacc.service.UsersService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UsersApplication(val usersService: UsersService, val domainConverter: DomainConverter) {

    fun addUser(user: UserDTO
    ): Result<String> {
        val userDAO = UserDAO(user.username, user.password, user.name, user.email, user.picture, user.phone, mutableListOf(), mutableListOf())
        return Result.ok(usersService.addUser(userDAO))
    }

    fun getUser(username: String
    ): Result<UserPasswordlessDTO> {
        val user = usersService.getUser(username) ?: return Result.error(HttpStatus.NOT_FOUND)

        return Result.ok(domainConverter.convertToPasswordless(user))
    }

    fun updateUser(username: String, updatedUser: UserUpdateDTO
    ): Result<UserPasswordlessDTO> {
        val user = usersService.getUser(username) ?: return Result.error(HttpStatus.NOT_FOUND)

        if(updatedUser.password != null)
            user.password = updatedUser.password
        else if(updatedUser.name != null)
            user.name = updatedUser.name
        else if(updatedUser.email != null)
            user.email = updatedUser.email
        else if(updatedUser.picture != null)
            user.picture = updatedUser.picture
        else if(updatedUser.phone != null)
            user.phone = updatedUser.phone

        usersService.updateUser(user)

        return Result.ok(domainConverter.convertToPasswordless(user))
    }

    fun deleteUser(username: String
    ): Result<UserDeleteDTO>{
        val user = usersService.getUser(username) ?: return Result.error(HttpStatus.NOT_FOUND)

        usersService.deleteUser(username)
        return Result.ok(this.domainConverter.convertUserToUserDelete(user))
    }

    fun searchUsers(name: String?,
                    email: String?,
                    phone: String?,
                    pg: Int,
                    len: Int
    ): Result<PageableListDTO<UserListDTO>> {
        val usersPage = usersService.searchUsers(name, email, phone, pg, len)
        val totalPages = usersPage.totalPages
        return Result.ok(PageableListDTO(usersPage
            .map{this.domainConverter.convertToUserList(it)}.toList(), totalPages))
    }

}