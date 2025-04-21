package ciai.holidayacc.service

import ciai.holidayacc.data.ApartmentRepository
import ciai.holidayacc.domain.ApartmentDAO
import ciai.holidayacc.domain.UserDAO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
class ApartmentsService(val apartmentsRepo: ApartmentRepository, val usersService: UsersService) {

    fun addApartment(apartment: ApartmentDAO, owner: UserDAO) : Long {
        val savedApartmentDAO = apartmentsRepo.save(apartment)
        owner.apartments.add(savedApartmentDAO)
        usersService.updateUser(owner)
        return savedApartmentDAO.apartmentId
    }
    fun hasApartment(apartmentId : Long) : Boolean {
        return apartmentsRepo.existsById(apartmentId)
    }

    fun getApartment(apartmentId: Long) : ApartmentDAO? {
        return apartmentsRepo.findById(apartmentId).getOrNull()
    }

    fun updateApartment(apartment: ApartmentDAO) : ApartmentDAO {
        return apartmentsRepo.save(apartment)
    }

    fun deleteApartment(apartmentId: Long) {
        return apartmentsRepo.deleteById(apartmentId)
    }

    fun searchApartments(
        name : String?,
        location : String?,
        amenities : List<String>?,
        price : Float?,
        startDate : LocalDate?,
        endDate : LocalDate?,
        isAvailable : Boolean?,
        owner : String?,
        pg: Int,
        len: Int
    ) : Page<ApartmentDAO> {
        return apartmentsRepo.searchApartmentsBy(name, location, amenities, price, startDate, endDate, isAvailable, owner, PageRequest.of(pg, len))
    }

}