package ciai.holidayacc.service

import ciai.holidayacc.data.ReviewsRepository
import ciai.holidayacc.domain.ReservationDAO
import ciai.holidayacc.domain.ReviewDAO
import ciai.holidayacc.domain.ReviewId
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ReviewsService(val reviewRepository: ReviewsRepository, val reservationsService: ReservationsService) {
    
    fun addReview(review: ReviewDAO, reservation: ReservationDAO) {
        val savedReview = reviewRepository.save(review)
        reservation.review = savedReview
        reservationsService.updateReservation(reservation)
    }
    fun hasReview(reviewId : ReviewId) : Boolean {
        return reviewRepository.existsById(reviewId)
    }

    fun searchApartmentReviews(apartmentId : Long, rating: Int?, clientId : String?, pg: Int, len: Int) : List<ReviewDAO>{
        return reviewRepository.searchApartmentReviews(apartmentId, rating, clientId, PageRequest.of(pg, len))
    }

    fun searchUserReviews(clientId : String, rating: Int?, apartment: Long?, pg: Int, len: Int) : List<ReviewDAO> {
        return reviewRepository.searchUserReviews(clientId, rating, apartment, PageRequest.of(pg, len))
    }

}