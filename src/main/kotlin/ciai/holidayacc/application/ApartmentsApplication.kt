package ciai.holidayacc.application

import ciai.holidayacc.application.utils.DomainConverter
import ciai.holidayacc.presentation.dto.*
import ciai.holidayacc.service.ApartmentsService
import org.springframework.http.HttpStatus
import java.time.LocalDate
import ciai.holidayacc.application.utils.Result
import ciai.holidayacc.service.UsersService
import org.springframework.stereotype.Service


@Service
class ApartmentsApplication(private val apartmentsService: ApartmentsService, val usersService: UsersService, val domainConverter: DomainConverter) {


    fun addApartment(apartment : ApartmentCreateDTO) : Result<Long> {
        val ownerDAO = usersService.getUser(apartment.ownerUsername)?: return Result.error(HttpStatus.BAD_REQUEST)
        val apartmentDAO = domainConverter.convertFromApartmentCreate(apartment, ownerDAO)
        val apartmentId = apartmentsService.addApartment(apartmentDAO, ownerDAO)
        return Result.ok(apartmentId)
    }

    fun hasApartment(apartmentId : Long) : Boolean {
        return apartmentsService.hasApartment(apartmentId)
    }

    fun getApartment(apartmentId: Long) : Result<ApartmentDTO> {
        val apartmentDAO = apartmentsService.getApartment(apartmentId) ?: return Result.error(HttpStatus.NOT_FOUND)
        return Result.ok(domainConverter.convertToApartment(apartmentDAO))
    }

    fun updateApartment(apartmentId: Long, apartment: ApartmentUpdateDTO) : Result<ApartmentUpdateDTO> {
        val apartmentDAO = apartmentsService.getApartment(apartmentId) ?: return Result.error(HttpStatus.NOT_FOUND)
        apartmentDAO.name = apartment.name ?: apartmentDAO.name
        apartmentDAO.description = apartment.description ?: apartmentDAO.description
        apartmentDAO.amenities = apartment.amenities ?: apartmentDAO.amenities
        apartmentDAO.pictures = apartment.pictures ?: apartmentDAO.pictures
        apartmentDAO.price = apartment.price ?: apartmentDAO.price
        apartmentDAO.isAvailable = apartment.isAvailable ?: apartmentDAO.isAvailable
        apartmentDAO.maxGuests = apartment.maxGuests ?: apartmentDAO.maxGuests

        val updatedDAO = apartmentsService.updateApartment(apartmentDAO)
        return Result.ok(domainConverter.convertToApartmentUpdate(updatedDAO))
    }

    fun deleteApartment(apartmentId: Long) : Result<ApartmentDeleteDTO> {
        val apartmentDAO = apartmentsService.getApartment(apartmentId) ?: return Result.error(HttpStatus.NOT_FOUND)
        apartmentsService.deleteApartment(apartmentId)
        return Result.ok(domainConverter.convertToApartmentDelete(apartmentDAO))
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
    ) : Result<PageableListDTO<ApartmentListDTO>> {
        val apartmentsPage = apartmentsService.searchApartments(name, location, amenities, price, startDate, endDate, isAvailable,
            owner,
            pg,
            len)

        val totalPages = apartmentsPage.totalPages
        return Result.ok(
            PageableListDTO(apartmentsPage.map{domainConverter.convertToApartmentList(it)}.toList(), totalPages)
        )
    }

}