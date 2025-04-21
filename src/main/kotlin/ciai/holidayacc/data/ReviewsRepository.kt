package ciai.holidayacc.data

import ciai.holidayacc.domain.ReviewDAO
import ciai.holidayacc.domain.ReviewId
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ReviewsRepository : CrudRepository<ReviewDAO, ReviewId> {

    @Query("SELECT r " +
            "FROM ReviewDAO r " +
            "WHERE r.reservation.period.apartment.apartmentId = :apartmentId " +
            "AND (:rating IS NULL OR ROUND(r.rating, 0) = :rating) " +
            "AND (:clientId IS NULL OR r.reservation.client.username = :clientId) ")
    fun searchApartmentReviews(apartmentId : Long, rating: Int?, clientId : String?, pageable: Pageable) : List<ReviewDAO>

    @Query("SELECT r " +
            "FROM ReviewDAO r " +
            "WHERE r.reservation.client.username = :clientId " +
            "AND (:rating IS NULL OR ROUND(r.rating, 0) = :rating) " +
            "AND (:apartment IS NULL OR r.reservation.period.apartment.apartmentId = :apartment)")
    fun searchUserReviews(clientId : String, rating: Int?, apartment: Long?, pageable: Pageable) : List<ReviewDAO>

}