package ciai.holidayacc.service

import ciai.holidayacc.data.ReservationsRepository
import ciai.holidayacc.domain.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import kotlin.jvm.optionals.getOrNull

@Service
class ReservationsService(val reservationsRepository: ReservationsRepository,
                          val usersService: UsersService, val periodsService: PeriodsService) {

    fun addReservation(reservation: ReservationDAO, client: UserDAO, period: PeriodDAO) : ReservationId {
        val savedReservationDAO = reservationsRepository.save(reservation)

        client.reservations.add(0, savedReservationDAO)
        usersService.updateUser(client)

        period.reservations.add(savedReservationDAO)
        period.availableDays -= Period.between(reservation.startDate, reservation.endDate).days
        periodsService.updatePeriod(period)

        val reservationId = ReservationId(reservation.reservationId.reservationNum, reservation.client.username, period.periodId)
        return reservationId
    }

    fun getReservation(reservationId: ReservationId) : ReservationDAO? {
        return reservationsRepository.findById(reservationId).getOrNull()
    }

    fun updateReservation(reservationDAO: ReservationDAO) : ReservationDAO{
        return reservationsRepository.save(reservationDAO)
    }

    fun deleteReservation(reservationDAO: ReservationDAO, client: UserDAO, period: PeriodDAO) {
        client.reservations.remove(reservationDAO)
        period.reservations.remove(reservationDAO)

        reservationsRepository.delete(reservationDAO)
    }

    fun searchApartmentReservations(apartmentId : Long,
                                    startDate: LocalDate?,
                                    endDate: LocalDate?,
                                    clientId: String?,
                                    states: List<State>?,
                                    pg: Int,
                                    len: Int) : Page<ReservationDAO> {
        return reservationsRepository.searchApartmentReservations(apartmentId, startDate, endDate, clientId, states, PageRequest.of(pg, len))
    }

    fun searchUserReservations(clientId : String,
                               startDate: LocalDate?,
                               endDate: LocalDate?,
                               apartment: Long?,
                               states: List<State>?,
                               pg: Int,
                               len: Int) : Page<ReservationDAO>{
        return reservationsRepository.searchUserReservations(clientId, startDate, endDate, apartment, states, PageRequest.of(pg, len))
    }

}