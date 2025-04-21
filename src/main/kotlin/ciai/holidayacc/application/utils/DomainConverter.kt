package ciai.holidayacc.application.utils

import ciai.holidayacc.domain.*

import ciai.holidayacc.presentation.dto.*
import org.springframework.stereotype.Service
import java.time.Period

@Service
class DomainConverter {

     fun convertToPasswordless(user: UserDAO): UserPasswordlessDTO =
             UserPasswordlessDTO(user.username,user.name,user.email,user.picture, user.phone,
                     user.apartments.map { this.convertToApartmentShort(it) })

     fun convertToUserShort(userDAO: UserDAO) : UserShortDTO = UserShortDTO(userDAO.username, userDAO.name, userDAO.email, userDAO.phone)

     fun convertUserToUserDelete(user: UserDAO): UserDeleteDTO = UserDeleteDTO(user.username, user.name, user.email)

     fun convertToUserList(user: UserDAO): UserListDTO = UserListDTO(user.username, user.name, user.email, user.picture, user.phone)

     fun convertFromApartmentCreate(apartment: ApartmentCreateDTO, ownerDAO: UserDAO) : ApartmentDAO =
             ApartmentDAO(0, apartment.name, apartment.description, apartment.location, apartment.amenities,
                     apartment.pictures, apartment.price, apartment.isAvailable, apartment.maxGuests, mutableListOf(), ownerDAO
             )

     fun convertToApartment(apartmentDAO: ApartmentDAO) : ApartmentDTO = ApartmentDTO(
             apartmentDAO.name, apartmentDAO.description, apartmentDAO.location,
             apartmentDAO.amenities, apartmentDAO.pictures, apartmentDAO.price,
             apartmentDAO.isAvailable, apartmentDAO.maxGuests, this.convertToUserShort(apartmentDAO.owner)
     )

     fun convertToApartmentShort(apartmentDAO: ApartmentDAO) : ApartmentShortDTO = ApartmentShortDTO(
             apartmentDAO.apartmentId, apartmentDAO.name, apartmentDAO.location, apartmentDAO.pictures.first(),
             apartmentDAO.price, apartmentDAO.isAvailable, apartmentDAO.maxGuests
     )

     fun convertToApartmentShortInfo(apartmentDAO: ApartmentDAO) : ApartmentShortInfoDTO = ApartmentShortInfoDTO(
             apartmentDAO.apartmentId, apartmentDAO.name, apartmentDAO.location, apartmentDAO.price
     )

     fun convertToApartmentUpdate(apartmentDAO: ApartmentDAO) : ApartmentUpdateDTO = ApartmentUpdateDTO(
             apartmentDAO.name, apartmentDAO.description, apartmentDAO.amenities,
             apartmentDAO.pictures, apartmentDAO.price, apartmentDAO.isAvailable,  apartmentDAO.maxGuests
     )

     fun convertToApartmentDelete(apartmentDAO: ApartmentDAO) : ApartmentDeleteDTO = ApartmentDeleteDTO(
             apartmentDAO.apartmentId, apartmentDAO.name, apartmentDAO.location, this.convertToUserShort(apartmentDAO.owner)
     )

     fun convertToApartmentList(apartmentDAO: ApartmentDAO) : ApartmentListDTO = ApartmentListDTO(
             apartmentDAO.apartmentId, apartmentDAO.name, apartmentDAO.location,
             apartmentDAO.description, apartmentDAO.pictures.first(), apartmentDAO.price,
             apartmentDAO.isAvailable, apartmentDAO.maxGuests, this.convertToUserShort(apartmentDAO.owner)
     )

    fun convertFromPeriodCreate(periodNum: Int, period: PeriodCreateDTO, apartment: ApartmentDAO) : PeriodDAO = PeriodDAO(
        PeriodId(periodNum, apartment.apartmentId), period.startDate, period.endDate,
        Period.between(period.startDate, period.endDate).days, apartment, mutableListOf()
    )

    fun convertToPeriodOwner(period: PeriodDAO) : PeriodDTO = PeriodOwnerDTO(
        period.startDate, period.endDate, period.availableDays > 0, period.reservations.map { convertToReservationShort(it) }
    )

    fun convertToPeriodClient(period: PeriodDAO) : PeriodDTO = PeriodClientDTO(
        period.startDate, period.endDate, period.availableDays > 0, period.reservations.map { convertToReservationLimited(it) }
    )

    fun convertToPeriodShort(period: PeriodDAO) : PeriodShortDTO = PeriodShortDTO(
        period.periodId.periodNum, period.startDate, period.endDate, period.availableDays
    )

    fun convertToPeriodUpdate(period: PeriodDAO) : PeriodUpdateDTO = PeriodUpdateDTO(
        period.startDate, period.endDate, period.availableDays
    )

    fun convertFromReservationCreate(reservationNum: Int, reservation: ReservationCreateDTO,
                                     client: UserDAO, period: PeriodDAO) : ReservationDAO =
            ReservationDAO(ReservationId(reservationNum, client.username,
                    PeriodId(period.periodId.periodNum, period.periodId.apartmentPK)), reservation.startDate,
                    reservation.endDate, reservation.numGuests, client, period,
                    mutableListOf(), null)

    fun convertToReservation(res:ReservationDAO): ReservationDTO = ReservationDTO(
        res.reservationId.reservationNum, res.startDate, res.endDate, convertToReservationState(res.states.last()),
        res.numGuests, convertToUserShort(res.client), convertToPeriodShort(res.period),
        convertToApartmentShort(res.period.apartment), convertToReview(res.review)
    )

    fun convertToReservationShort(res: ReservationDAO): ReservationShortDTO = ReservationShortDTO(
        res.reservationId.reservationNum, res.startDate, res.endDate, res.numGuests, convertToUserShort(res.client),
        convertToReservationStateShort(res.states.last())
    )

    fun convertToReservationLimited(res: ReservationDAO): ReservationLimitedDTO =
             ReservationLimitedDTO(res.reservationId.reservationNum, res.startDate, res.endDate, res.numGuests,)

    fun convertToReservationUsersList(res: ReservationDAO): ReservationUsersListDTO =
        ReservationUsersListDTO(res.reservationId.reservationNum, res.startDate, res.endDate,
            convertToReservationStateShort(res.states.last()), res.numGuests, res.reservationId.periodId.periodNum,
            convertToApartmentShort(res.period.apartment))

    fun convertToReservationApartmentsList(res: ReservationDAO): ReservationApartmentsListDTO =
        ReservationApartmentsListDTO(res.reservationId.reservationNum, res.startDate, res.endDate,
            convertToReservationStateShort(res.states.last()), res.numGuests, res.reservationId.periodId.periodNum,
            convertToUserShort(res.client))

    fun convertToReservationCreateReview(res: ReservationDAO): ReservationCreateReviewDTO =
             ReservationCreateReviewDTO(res.reservationId.reservationNum, convertToReview(res.review))

     fun convertToReservationState(state: ReservationStateDAO): ReservationStateDTO =
             ReservationStateDTO(state.reservationStateId.state, state.startDate, state.endDate)

     fun convertToReservationStateShort(state: ReservationStateDAO): ReservationStateShortDTO =
             ReservationStateShortDTO(state.reservationStateId.state)

     fun convertToReservationStateUpdate(state: ReservationStateDAO, nextState: ReservationStateCreateDTO): ReservationStateUpdateDTO =
             ReservationStateUpdateDTO(state.endDate, nextState)

    fun convertToReview(review: ReviewDAO?): ReviewDTO = ReviewDTO(review?.text, review?.rating)

    fun convertToReviewApartmentsList(review: ReviewDAO): ReviewApartmentsListDTO = ReviewApartmentsListDTO(
        review.text, review.rating, convertToUserShort(review.reservation.client)
    )
    fun convertToReviewUsersList(review: ReviewDAO): ReviewUsersListDTO = ReviewUsersListDTO(
        review.text, review.rating, convertToApartmentShortInfo(review.reservation.period.apartment)
    )

}
