package ciai.holidayacc.presentation.controller

import ciai.holidayacc.application.ReservationStatesApplication
import ciai.holidayacc.application.ReservationsApplication
import ciai.holidayacc.application.ReviewsApplication
import ciai.holidayacc.presentation.api.ReservationsAPI
import ciai.holidayacc.presentation.dto.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@RestController
class ReservationsController(val reservationsApp: ReservationsApplication,
                             val reservationStateApp: ReservationStatesApplication,
                             val reviewsApp: ReviewsApplication) : ReservationsAPI {

    override fun getReservation(reservationId: String): ReservationDTO {
        val result = reservationsApp.getReservation(reservationId)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun deleteReservation(reservationId: String, principal: Principal): ReservationLimitedDTO {
        val result = reservationsApp.deleteReservation(reservationId)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun addReview(reservationId: String, reviewDTO: ReviewCreateDTO) {
        val result = reviewsApp.addReview(reservationId, reviewDTO)
        if (result.isOK())
            return
        else
            throw ResponseStatusException(result.error())
    }

}