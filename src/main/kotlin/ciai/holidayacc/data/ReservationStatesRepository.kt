package ciai.holidayacc.data

import ciai.holidayacc.domain.ReservationId
import ciai.holidayacc.domain.ReservationStateDAO
import ciai.holidayacc.domain.ReservationStateId
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ReservationStatesRepository : CrudRepository<ReservationStateDAO, ReservationStateId> {

    @Query("SELECT s " +
            "FROM ReservationStateDAO s " +
            "WHERE s.reservation.reservationId.reservationNum = :reservationNum " +
            "AND s.reservation.client.username = :clientUsername " +
            "AND s.reservation.period.periodId.periodNum = :periodNum " +
            "AND s.reservation.period.apartment.apartmentId = :apartmentId"
    )
    fun getListReservationStates(reservationNum: Int, clientUsername: String, periodNum: Int, apartmentId: Long
    ): List<ReservationStateDAO>

}