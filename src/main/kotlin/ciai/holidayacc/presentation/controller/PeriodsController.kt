package ciai.holidayacc.presentation.controller

import ciai.holidayacc.application.PeriodsApplication
import ciai.holidayacc.application.ReservationsApplication
import ciai.holidayacc.presentation.api.PeriodsAPI
import ciai.holidayacc.presentation.dto.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.security.Principal
import java.time.LocalDate

@RestController
class PeriodsController(val periodsApplication: PeriodsApplication,
                        val reservationsApplication: ReservationsApplication) : PeriodsAPI {
    override fun addPeriod(apartmentId: Long, period: PeriodCreateDTO, principal: Principal): Int {
        val result = periodsApplication.addPeriod(apartmentId, period)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun getPeriod(apartmentId: Long, periodNum: Int): PeriodDTO {
        val result = periodsApplication.getPeriod(apartmentId, periodNum)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun updatePeriod(apartmentId: Long, periodNum: Int, period: PeriodUpdateDTO, principal: Principal): PeriodUpdateDTO {
        val result = periodsApplication.updatePeriod(apartmentId, periodNum, period)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun deletePeriod(apartmentId: Long, periodNum: Int, principal: Principal): PeriodShortDTO {
        val result = periodsApplication.deletePeriod(apartmentId, periodNum)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun searchPeriods(
        apartmentId: Long,
        startDate: LocalDate?,
        endDate: LocalDate?,
        isAvailable: Boolean?,
        pg: Int,
        len: Int
    ): List<PeriodShortDTO> {
        val result = periodsApplication.searchPeriods(apartmentId, startDate, endDate, isAvailable, pg, len)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

    override fun addReservation(apartmentId: Long, periodNum: Int, period: ReservationCreateDTO): String {
        val result = reservationsApplication.addReservation(apartmentId, periodNum, period)
        if (result.isOK())
            return result.value()
        else
            throw ResponseStatusException(result.error())
    }

}