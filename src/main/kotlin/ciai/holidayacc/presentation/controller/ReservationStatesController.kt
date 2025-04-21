package ciai.holidayacc.presentation.controller

import ciai.holidayacc.application.ReservationStatesApplication
import ciai.holidayacc.presentation.api.ReservationStatesAPI
import ciai.holidayacc.presentation.dto.ReservationStateDTO
import ciai.holidayacc.presentation.dto.ReservationStateUpdateDTO
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@RestController
class ReservationStatesController(val reservationStatesApplication: ReservationStatesApplication) : ReservationStatesAPI {

    override fun updateState(reservationId: String, state: ReservationStateUpdateDTO, principal: Principal): ReservationStateUpdateDTO {
        val result = reservationStatesApplication.updateState(reservationId, state)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun listReservationStateHistory(reservationId: String): List<ReservationStateDTO> {
        val result = reservationStatesApplication.listReservationStateHistory(reservationId)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }
}