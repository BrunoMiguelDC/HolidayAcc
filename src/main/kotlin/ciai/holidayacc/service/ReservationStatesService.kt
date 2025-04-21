package ciai.holidayacc.service

import ciai.holidayacc.data.ReservationStatesRepository
import ciai.holidayacc.domain.*
import org.springframework.stereotype.Service

@Service
class ReservationStatesService(val reservationStatesRepo: ReservationStatesRepository,
                               val reservationsService: ReservationsService) {

    fun addReservationState(reservationState: ReservationStateDAO) : ReservationStateDAO {
        return reservationStatesRepo.save(reservationState)
    }

    fun hasReservationState(reservationStateId : ReservationStateId) : Boolean {
        return reservationStatesRepo.existsById(reservationStateId)
    }

    fun updateReservationState(currentState: ReservationStateDAO, nextState: ReservationStateDAO,
                               reservation: ReservationDAO) : ReservationStateDAO {
        reservationStatesRepo.save(currentState)
        val nextReservationState = reservationStatesRepo.save(nextState)
        reservation.states.add(nextState)
        reservationsService.updateReservation(reservation)
        return nextReservationState
    }

    fun listReservationStates(reservationId: ReservationId): List<ReservationStateDAO> {
        return reservationStatesRepo.getListReservationStates(reservationId.reservationNum, reservationId.clientUsername,
                reservationId.periodId.periodNum, reservationId.periodId.apartmentPK)
    }


}