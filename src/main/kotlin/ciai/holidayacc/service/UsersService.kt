package ciai.holidayacc.service

import ciai.holidayacc.data.UserRepository
import ciai.holidayacc.domain.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UsersService(val usersRepository: UserRepository) {

    fun addUser(user:UserDAO):String{
        usersRepository.save(user)

        return user.username
    }

    fun hasUser(username: String):Boolean{
        return usersRepository.existsById(username)
    }

    fun getUser(username: String): UserDAO? {
        return usersRepository.findById(username).getOrNull()
    }

    fun updateUser(updatedUser: UserDAO):UserDAO{
        return usersRepository.save(updatedUser)
    }

    fun deleteUser(username: String) {
        usersRepository.deleteById(username)
    }

    fun searchUsers(name: String?,
                    email: String?,
                    phone: String?,
                    pg: Int,
                    len: Int
    ): Page<UserDAO> {
        return usersRepository.searchUsers(name, email, phone, PageRequest.of(pg, len))
    }

}