package ciai.holidayacc.presentation.dto

import java.time.LocalDate

data class ReservationDTO(
        val reservationNum: Int,
        var startDate: LocalDate,
        var endDate: LocalDate,
        val state: ReservationStateDTO,
        val numGuests: Int,
        val client: UserShortDTO,
        val period: PeriodShortDTO,
        val apartment: ApartmentShortDTO,
        var review: ReviewDTO?,
)

data class ReservationShortDTO(
        val reservationNum: Int,
        var startDate: LocalDate,
        var endDate: LocalDate,
        val numGuests: Int,
        val client: UserShortDTO,
        val state: ReservationStateShortDTO,
)

data class ReservationCreateDTO(
        var startDate: LocalDate,
        var endDate: LocalDate,
        val numGuests: Int,
        val clientId: String
)

data class ReservationLimitedDTO(
        val reservationNum: Int,
        var startDate: LocalDate,
        var endDate: LocalDate,
        val numGuests: Int,
)

data class ReservationCreateReviewDTO(
        val reservationNum: Int,
        val review: ReviewDTO
)

data class ReservationUsersListDTO(
        val reservationNum: Int,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val state: ReservationStateShortDTO,
        val numGuests: Int,
        val periodNum: Int,
        val apartment: ApartmentShortDTO,
)

data class ReservationApartmentsListDTO(
        val reservationNum: Int,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val state: ReservationStateShortDTO,
        val numGuests: Int,
        val periodNum: Int,
        val client: UserShortDTO
)