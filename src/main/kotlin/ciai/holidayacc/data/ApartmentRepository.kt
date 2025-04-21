package ciai.holidayacc.data

import ciai.holidayacc.domain.ApartmentDAO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface ApartmentRepository : CrudRepository<ApartmentDAO, Long> {

    @Query("SELECT a " +
            "FROM ApartmentDAO a " +
            "WHERE (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT(:name, '%'))) " +
            "AND (:location IS NULL OR LOWER(a.location) LIKE LOWER(CONCAT(:location, '%'))) " +
            "AND (:amenities IS NULL OR EXISTS(SELECT am FROM a.amenities am WHERE am IN :amenities)) " +
            "AND (:price IS NULL OR a.price <= :price) " +
            "AND (:isAvailable IS NULL OR a.isAvailable = :isAvailable) " +
            "AND (:owner IS NULL OR a.owner.username = :owner) " +
            "AND (:startDate IS NULL OR EXISTS(SELECT p FROM a.periods p WHERE p.startDate >= :startDate)) " +
            "AND (:endDate IS NULL OR EXISTS(SELECT p FROM a.periods p WHERE p.endDate <= :endDate))"
    )
    fun searchApartmentsBy(
        name : String?,
        location : String?,
        amenities : List<String>?,
        price : Float?,
        startDate : LocalDate?,
        endDate : LocalDate?,
        isAvailable : Boolean?,
        owner : String?,
        page: Pageable
    ) : Page<ApartmentDAO>
}
