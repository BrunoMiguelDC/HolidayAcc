package ciai.holidayacc.presentation.dto

import ciai.holidayacc.domain.State
import java.time.LocalDate

data class ReservationStateDTO(
        val name: State,
        val startDate: LocalDate,
        val endDate: LocalDate?
)

data class ReservationStateShortDTO(
        val name: State,
)

data class ReservationStateCreateDTO(
        val name: State,
        val startDate: LocalDate,
)

data class ReservationStateUpdateDTO(
        val endDate: LocalDate?,
        val nextState: ReservationStateCreateDTO
)