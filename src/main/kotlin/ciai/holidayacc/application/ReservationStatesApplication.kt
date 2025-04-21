package ciai.holidayacc.application

import ciai.holidayacc.application.utils.DomainConverter
import ciai.holidayacc.application.utils.Result
import ciai.holidayacc.domain.ReservationStateDAO
import ciai.holidayacc.domain.ReservationStateId
import ciai.holidayacc.presentation.dto.*
import ciai.holidayacc.service.ReservationStatesService
import ciai.holidayacc.service.ReservationsService
import ciai.holidayacc.service.UsersService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service


@Service
class ReservationStatesApplication(val reservationStatesService: ReservationStatesService,
                                   val reservationsService: ReservationsService,
                                   val usersService: UsersService,
                                   val reservatiosnApp: ReservationsApplication,
                                   val domainConverter: DomainConverter) {

    fun updateState(reservationId: String, state: ReservationStateUpdateDTO): Result<ReservationStateUpdateDTO> {
        val result = reservatiosnApp.getReservationId(reservationId)
        if (!result.isOK())
            return Result.error(result.error())
        val reservationID = result.value()

        val reservationDAO = reservationsService.getReservation(reservationID)?: return Result.error(HttpStatus.NOT_FOUND)
        val currentState = reservationDAO.states.last()
        if(currentState.reservationStateId.state.nextState().name != state.nextState.name.name)
            return Result.error(HttpStatus.BAD_REQUEST)

        currentState.endDate = state.endDate

        val nextStateCreateDTO = state.nextState
        val reservationStateId = ReservationStateId(nextStateCreateDTO.name, reservationDAO.reservationId)
        val nextStateDAO = ReservationStateDAO(reservationStateId, nextStateCreateDTO.startDate, null, reservationDAO)

        val updatedReservationState = reservationStatesService.updateReservationState(currentState, nextStateDAO, reservationDAO)

        return Result.ok(domainConverter.convertToReservationStateUpdate(updatedReservationState, nextStateCreateDTO))
    }

    fun listReservationStateHistory(reservationId: String): Result<List<ReservationStateDTO>> {
        val result = reservatiosnApp.getReservationId(reservationId)
        if (!result.isOK())
            return Result.error(result.error())
        val reservationID = result.value()

        val reservationDAO = reservationsService.getReservation(reservationID)?: return Result.error(HttpStatus.NOT_FOUND)

        return Result.ok(reservationStatesService.listReservationStates(reservationID).map { domainConverter.convertToReservationState(it) })
    }


}