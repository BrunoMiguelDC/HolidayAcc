package ciai.holidayacc.presentation.controller

import ciai.holidayacc.application.ApartmentsApplication
import ciai.holidayacc.application.ReservationsApplication
import ciai.holidayacc.application.ReviewsApplication
import ciai.holidayacc.domain.State
import ciai.holidayacc.presentation.api.ApartmentsAPI
import ciai.holidayacc.presentation.dto.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.security.Principal
import java.time.LocalDate

@RestController
class ApartmentsController(val apartmentsApp: ApartmentsApplication,
                           val reservationsApp: ReservationsApplication,
                           val reviewsApp: ReviewsApplication) : ApartmentsAPI {
    override fun addApartment(apartment: ApartmentCreateDTO): Long {
        val result = apartmentsApp.addApartment(apartment)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun getApartment(apartmentId: Long): ApartmentDTO {
        val result = apartmentsApp.getApartment(apartmentId)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun updateApartment(apartmentId: Long, apartment: ApartmentUpdateDTO, principal: Principal): ApartmentUpdateDTO {
        val result = apartmentsApp.updateApartment(apartmentId, apartment)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun deleteApartment(apartmentId: Long, principal: Principal): ApartmentDeleteDTO {
        val result = apartmentsApp.deleteApartment(apartmentId)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun searchApartments(
        name: String?,
        location: String?,
        amenities: List<String>?,
        price: Float?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        isAvailable: Boolean?,
        owner: String?,
        pg: Int,
        len: Int
    ): PageableListDTO<ApartmentListDTO> {
        val result = apartmentsApp.searchApartments(name, location, amenities, price, startDate, endDate, isAvailable, owner, pg, len)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun searchApartmentReservations(apartmentId: Long,
                                             startDate: LocalDate?,
                                             endDate: LocalDate?,
                                             client: String?,
                                             states: List<State>?,
                                             pg: Int,
                                             len: Int
    ): PageableListDTO<ReservationApartmentsListDTO> {
        val result = reservationsApp.searchApartmentReservations(apartmentId, startDate, endDate, client, states, pg, len)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun searchApartmentsReviews(
        apartmentId: Long,
        rating: Int?,
        authorUsername: String?,
        pg: Int,
        len: Int
    ): List<ReviewApartmentsListDTO> {
        val result = reviewsApp.searchApartmentsReviews(apartmentId, rating, authorUsername, pg, len)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }
}