package ciai.holidayacc.data

import ciai.holidayacc.domain.ReservationDAO
import ciai.holidayacc.domain.ReservationId
import ciai.holidayacc.domain.State
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface ReservationsRepository : CrudRepository<ReservationDAO, ReservationId> {


    @Query("Select res " +
            "FROM ReservationDAO res " +
            "WHERE res.period.apartment.apartmentId = :apartment " +
            "AND (:startDate IS NULL OR  res.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR  res.startDate <= :endDate) " +
            "AND (:clientId IS NULL OR res.client.username = :clientId) " +
            "AND (:states IS NULL OR EXISTS(SELECT s FROM res.states s WHERE s.reservationStateId.state IN :states))")
    fun searchApartmentReservations(apartment : Long,
                                    startDate: LocalDate?,
                                    endDate: LocalDate?,
                                    clientId: String?,
                                    states: List<State>?,
                                    pageable: Pageable) : Page<ReservationDAO>

    @Query("Select res " +
            "FROM ReservationDAO res " +
            "WHERE res.client.username = :clientId " +
            "AND (:startDate IS NULL OR  res.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR  res.startDate <= :endDate) " +
            "AND (:apartment IS NULL OR res.period.apartment.apartmentId = :apartment) " +
            "AND (:states IS NULL OR EXISTS(SELECT s FROM res.states s WHERE s.reservationStateId.state IN :states))")
    fun searchUserReservations(clientId : String,
                               startDate: LocalDate?,
                               endDate: LocalDate?,
                               apartment: Long?,
                               states: List<State>?,
                               pageable: Pageable) : Page<ReservationDAO>

}