package ciai.holidayacc.application

import ciai.holidayacc.application.utils.DomainConverter
import ciai.holidayacc.application.utils.Result
import ciai.holidayacc.domain.PeriodId
import ciai.holidayacc.presentation.dto.*
import ciai.holidayacc.service.ApartmentsService
import ciai.holidayacc.service.PeriodsService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PeriodsApplication(val periodsService: PeriodsService, val apartmentsService: ApartmentsService, val domainConverter: DomainConverter) {

    fun addPeriod(apartmentId: Long, period: PeriodCreateDTO) : Result<Int> {
        val apartmentDAO = apartmentsService.getApartment(apartmentId)?: return Result.error(HttpStatus.NOT_FOUND)

        val periodDAO = domainConverter.convertFromPeriodCreate(apartmentDAO.periods.size, period, apartmentDAO)
        val periodNum = periodsService.addPeriod(periodDAO, apartmentDAO)
        return Result.ok(periodNum)
    }

    fun hasPeriod(apartmentId: Long, periodNum: Int) : Boolean {
        val apartmentDAO = apartmentsService.getApartment(apartmentId)?: return false
        return periodsService.hasPeriod(PeriodId(periodNum, apartmentDAO.apartmentId))
    }

    fun getPeriod(apartmentId: Long, periodNum: Int) : Result<PeriodDTO> {
        val apartmentDAO = apartmentsService.getApartment(apartmentId)?: return Result.error(HttpStatus.NOT_FOUND)
        val periodDAO = periodsService.getPeriod(PeriodId(periodNum, apartmentDAO.apartmentId)) ?: return Result.error(HttpStatus.NOT_FOUND)
        TODO("Check using security service if user is owner or client and return adequate PeriodDTO")
    }

    fun updatePeriod(apartmentId: Long, periodNum: Int, period: PeriodUpdateDTO) : Result<PeriodUpdateDTO> {
        val apartmentDAO = apartmentsService.getApartment(apartmentId)?: return Result.error(HttpStatus.NOT_FOUND)
        val periodDAO = periodsService.getPeriod(PeriodId(periodNum, apartmentDAO.apartmentId)) ?: return Result.error(HttpStatus.NOT_FOUND)

        periodDAO.startDate = period.startDate ?: periodDAO.startDate
        periodDAO.endDate = period.endDate ?: periodDAO.endDate
        periodDAO.availableDays = period.availableDays ?: periodDAO.availableDays
        val updatedDAO = periodsService.updatePeriod(periodDAO)

        return Result.ok(domainConverter.convertToPeriodUpdate(updatedDAO))
    }

    fun deletePeriod(apartmentId: Long, periodNum: Int) : Result<PeriodShortDTO> {
        val apartmentDAO = apartmentsService.getApartment(apartmentId)?: return Result.error(HttpStatus.NOT_FOUND)
        val periodDAO = periodsService.getPeriod(PeriodId(periodNum, apartmentDAO.apartmentId)) ?: return Result.error(HttpStatus.NOT_FOUND)

        periodsService.deletePeriod(periodDAO, apartmentDAO)
        return Result.ok(domainConverter.convertToPeriodShort(periodDAO))
    }

    fun searchPeriods(
        apartmentId : Long,
        startDate : LocalDate?,
        endDate : LocalDate?,
        isAvailable : Boolean?,
        pg: Int,
        len: Int
    ) : Result<List<PeriodShortDTO>> {
        if(!apartmentsService.hasApartment(apartmentId))
            return Result.error(HttpStatus.NOT_FOUND)
        return Result.ok(periodsService.searchPeriods(
                                            apartmentId,
                                            startDate,
                                            endDate,
                                            isAvailable,
                                            pg,
                                            len).map { domainConverter.convertToPeriodShort(it) })
    }

}