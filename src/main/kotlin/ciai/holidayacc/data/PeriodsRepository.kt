package ciai.holidayacc.data

import ciai.holidayacc.domain.PeriodDAO
import ciai.holidayacc.domain.PeriodId
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface PeriodsRepository : CrudRepository<PeriodDAO, PeriodId> {

    @Query("SELECT p " +
            "FROM PeriodDAO p " +
            "WHERE p.apartment.apartmentId = :apartmentId " +
            "AND (:startDate IS NULL OR p.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR p.endDate <= :endDate) " +
            "AND (:isAvailable IS NULL OR p.availableDays > 0)"

    )
    fun searchPeriodsBy(
        apartmentId : Long,
        startDate : LocalDate?,
        endDate : LocalDate?,
        isAvailable : Boolean?,
        pageable: Pageable
    ) : List<PeriodDAO>

}