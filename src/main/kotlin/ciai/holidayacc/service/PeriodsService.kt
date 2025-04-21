package ciai.holidayacc.service

import ciai.holidayacc.data.PeriodsRepository
import ciai.holidayacc.domain.ApartmentDAO
import ciai.holidayacc.domain.PeriodDAO
import ciai.holidayacc.domain.PeriodId
import ciai.holidayacc.domain.ReservationDAO
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
class PeriodsService(val periodsRepo: PeriodsRepository, val apartmentsService: ApartmentsService) {
    fun addPeriod(period: PeriodDAO, apartment: ApartmentDAO) : Int {
        val savedPeriodDAO = periodsRepo.save(period)
        apartment.periods.add(savedPeriodDAO)
        apartmentsService.updateApartment(apartment)
        return savedPeriodDAO.periodId.periodNum
    }

    fun hasPeriod(periodId : PeriodId) : Boolean {
        return periodsRepo.existsById(periodId)
    }

    fun isAvailable(periodId : PeriodId) : Boolean {
        val period = this.getPeriod(periodId)
        return period != null && period.availableDays > 0
    }

    fun isAvailable(period : PeriodDAO) : Boolean {
        return period.availableDays > 0
    }

    fun canRentPeriod(period: PeriodDAO, startDate: LocalDate, endDate: LocalDate) : Boolean {
        var intersectsReservation = false
        for (reservation:ReservationDAO in period.reservations){
            val inside = reservation.startDate.isBefore(startDate) && reservation.endDate.isAfter(endDate)
            val startDateInside = reservation.startDate.isBefore(startDate) && reservation.endDate.isAfter(startDate)
            val endDateInside = reservation.startDate.isBefore(endDate) && reservation.endDate.isAfter(endDate)
            val same = reservation.startDate.isEqual(startDate) && reservation.endDate.isEqual(endDate)
            if (inside || startDateInside || endDateInside || same){
                intersectsReservation = true
                break
            }
        }
        return period.startDate.isBefore(startDate) && period.endDate.isAfter(endDate) && !intersectsReservation
    }

    fun getPeriod(periodId: PeriodId) : PeriodDAO? {
        return periodsRepo.findById(periodId).getOrNull()
    }

    fun updatePeriod(period: PeriodDAO) : PeriodDAO {
        return periodsRepo.save(period)
    }

    fun deletePeriod(periodDAO: PeriodDAO, apartment: ApartmentDAO) {
        apartment.periods.remove(periodDAO)
        apartmentsService.updateApartment(apartment)
        return periodsRepo.delete(periodDAO)
    }

    fun searchPeriods(
        apartmentId : Long,
        startDate : LocalDate?,
        endDate : LocalDate?,
        isAvailable : Boolean?,
        pg: Int,
        len: Int
    ) : List<PeriodDAO> {
        return periodsRepo.searchPeriodsBy(apartmentId, startDate, endDate, isAvailable, PageRequest.of(pg, len))
    }

}