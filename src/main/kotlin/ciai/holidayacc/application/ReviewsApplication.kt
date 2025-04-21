package ciai.holidayacc.application

import ciai.holidayacc.application.utils.DomainConverter
import ciai.holidayacc.application.utils.Result
import ciai.holidayacc.domain.*
import ciai.holidayacc.presentation.dto.ReviewApartmentsListDTO
import ciai.holidayacc.presentation.dto.ReviewCreateDTO
import ciai.holidayacc.presentation.dto.ReviewUsersListDTO
import ciai.holidayacc.service.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ReviewsApplication(val reviewsService: ReviewsService,
                         val reservationsService: ReservationsService,
                         val apartmentsService: ApartmentsService,
                         val usersService: UsersService,
                         val reservationStatesService: ReservationStatesService,
                         val reservationsApp: ReservationsApplication,
                         val domainConverter: DomainConverter) {

    fun addReview(reservationId: String, reviewDTO: ReviewCreateDTO) : Result<Unit> {
        val result = reservationsApp.getReservationId(reservationId)
        if (!result.isOK())
            return Result.error(result.error())
        val reservationID = result.value()
        val reservationDAO = reservationsService.getReservation(reservationID) ?: return Result.error(HttpStatus.NOT_FOUND)
        val reviewDAO = ReviewDAO(ReviewId(1, reservationDAO.reservationId), reviewDTO.text, reviewDTO.rating, reservationDAO)

        val currentState = reservationDAO.states.last()
        currentState.endDate = reviewDTO.date

        val reservationStateId = ReservationStateId(State.CLOSED, reservationDAO.reservationId)
        val nextStateDAO = ReservationStateDAO(reservationStateId, reviewDTO.date, reviewDTO.date, reservationDAO)

        reservationStatesService.updateReservationState(currentState, nextStateDAO, reservationDAO)

        reviewsService.addReview(reviewDAO, reservationDAO)
        return Result.ok()
    }

    fun searchApartmentsReviews(apartmentId: Long, rating: Int?, authorUsername: String?, pg: Int, len: Int): Result<List<ReviewApartmentsListDTO>> {
        if(!apartmentsService.hasApartment(apartmentId))
            return Result.error(HttpStatus.NOT_FOUND)
        return Result.ok(reviewsService.searchApartmentReviews(apartmentId, rating, authorUsername, pg, len).map {
            domainConverter.convertToReviewApartmentsList(it)
        })
    }

    fun searchUserReviews(username: String, rating: Int?, apartmentId: Long?, pg: Int, len: Int): Result<List<ReviewUsersListDTO>> {
        if(!usersService.hasUser(username))
            return Result.error(HttpStatus.NOT_FOUND)
        return Result.ok(reviewsService.searchUserReviews(username, rating, apartmentId, pg, len).map {
            domainConverter.convertToReviewUsersList(it)
        })
    }

}