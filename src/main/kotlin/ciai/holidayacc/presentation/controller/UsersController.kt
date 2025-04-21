package ciai.holidayacc.presentation.controller

import ciai.holidayacc.application.ReservationsApplication
import ciai.holidayacc.application.ReviewsApplication
import ciai.holidayacc.application.UsersApplication
import ciai.holidayacc.domain.State
import ciai.holidayacc.presentation.api.UsersAPI
import ciai.holidayacc.presentation.dto.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate


@RestController
class UsersController(val usersApp: UsersApplication,
                      val reservationsApp: ReservationsApplication,
                      val reviewsApp: ReviewsApplication) : UsersAPI {

    override fun addUser(user: UserDTO
    ): String {
        val result = usersApp.addUser(user)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun getUser(username: String
    ): UserPasswordlessDTO {
        val result = usersApp.getUser(username)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun updateUser(username: String, user: UserUpdateDTO
    ): UserPasswordlessDTO {
        val result = usersApp.updateUser(username, user)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun deleteUser(username: String
    ): UserDeleteDTO {
        val result = usersApp.deleteUser(username)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun searchUsers(name: String?, email: String?, phone: String?, pg: Int, len: Int
    ): PageableListDTO<UserListDTO> {
        val result = usersApp.searchUsers(name, email, phone, pg, len)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun searchUserReservations(username: String,
                                        startDate: LocalDate?,
                                        endDate: LocalDate?,
                                        apartment: Long?,
                                        states: List<State>?,
                                        pg: Int,
                                        len: Int
    ): PageableListDTO<ReservationUsersListDTO> {
        val result = reservationsApp.searchUserReservations(username, startDate, endDate, apartment, states, pg, len)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun searchUserReviews(username: String,
                                   rating: Int?,
                                   apartmentId: Long?,
                                   pg: Int,
                                   len: Int
    ): List<ReviewUsersListDTO> {
        val result = reviewsApp.searchUserReviews(username, rating, apartmentId, pg, len)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

}