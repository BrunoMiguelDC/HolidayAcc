package ciai.holidayacc.config

import ciai.holidayacc.application.RESERVATION_ID_DELIMITER
import ciai.holidayacc.application.utils.Result
import ciai.holidayacc.data.ApartmentRepository
import ciai.holidayacc.data.ReservationsRepository
import ciai.holidayacc.domain.PeriodId
import ciai.holidayacc.domain.ReservationId
import ciai.holidayacc.domain.State
import ciai.holidayacc.presentation.dto.ReservationStateUpdateDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component("SecurityService")
class SecurityService {

    @Autowired
    lateinit var apartments: ApartmentRepository

    @Autowired
    lateinit var reservations: ReservationsRepository

    fun canEditApartment(authToken: UserAuthToken, apartmentId : Long):Boolean {
        val apartment = apartments.findById(apartmentId).getOrNull()
        return apartment?.owner != null && apartment.owner.username == authToken.name
    }

    fun canChangeState(authToken: UserAuthToken, reservationId: String, state: ReservationStateUpdateDTO) : Boolean{
        val id = getReservationId(reservationId)
        val reservation = reservations.findById(id).getOrNull()

        val canOwnerChange = reservation != null
                && authToken.name == reservation.period.apartment.owner.username
                && (state.nextState.name == State.BOOKED || state.nextState.name == State.CANCELED)

        val canUserChange = reservation != null
                && authToken.name == reservation.client.username
                && (state.nextState.name == State.OCCUPIED || state.nextState.name == State.AWAITING_REVIEW)
        return canOwnerChange || canUserChange
    }

    fun canDeleteReservation(authToken: UserAuthToken, reservationId: String) : Boolean{
        val id = getReservationId(reservationId)
        val reservation = reservations.findById(id).getOrNull()


        return reservation != null &&
                ((authToken.name == reservation.period.apartment.owner.username && reservation.states.last().reservationStateId.state == State.UNDER_CONSIDERATION) ||
                        ( authToken.name == reservation.client.username && reservation.states.last().reservationStateId.state == State.UNDER_CONSIDERATION))


    }

    fun getReservationId(id: String) : ReservationId {
        val tokens = id.split(RESERVATION_ID_DELIMITER)
        val reservationNum = tokens[0].toInt()
        val clientId = tokens[1]
        val periodNum = tokens[2].toInt()
        val apartmentId = tokens[3].toLong()
        return ReservationId(reservationNum, clientId, PeriodId(periodNum, apartmentId))
    }

}