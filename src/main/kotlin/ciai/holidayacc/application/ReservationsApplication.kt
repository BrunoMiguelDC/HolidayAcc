package ciai.holidayacc.application

import ciai.holidayacc.application.utils.DomainConverter
import ciai.holidayacc.application.utils.Result
import ciai.holidayacc.domain.*
import ciai.holidayacc.presentation.dto.*
import ciai.holidayacc.service.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate

const val RESERVATION_ID_DELIMITER = "-"
@Service
class ReservationsApplication(private val reservationsService: ReservationsService,
                              val usersService: UsersService,
                              val apartmentsService: ApartmentsService,
                              val periodsService: PeriodsService,
                              val reservationStatesService: ReservationStatesService,
                              val domainConverter: DomainConverter) {


    fun addReservation(apartmentId: Long, periodNum: Int, reservation: ReservationCreateDTO) : Result<String> {
        val apartmentDAO = apartmentsService.getApartment(apartmentId)?: return Result.error(HttpStatus.NOT_FOUND)

        val periodId = PeriodId(periodNum, apartmentDAO.apartmentId)
        val periodDAO = periodsService.getPeriod(periodId)?: return Result.error(HttpStatus.NOT_FOUND)
        if(!periodsService.isAvailable(periodDAO) || !periodsService.canRentPeriod(periodDAO, reservation.startDate, reservation.endDate))
            return Result.error(HttpStatus.FORBIDDEN)

        val clientUsername = reservation.clientId
        val clientDAO = usersService.getUser(clientUsername)?: return Result.error(HttpStatus.NOT_FOUND)

        val reservationDAO = domainConverter.convertFromReservationCreate(periodDAO.reservations.size, reservation,
               clientDAO, periodDAO)
        val savedReservationId = reservationsService.addReservation(reservationDAO, clientDAO, periodDAO)

        val reservationStateId = ReservationStateId(State.UNDER_CONSIDERATION, savedReservationId)
        val reservationStateDAO = ReservationStateDAO(reservationStateId, reservation.startDate, null, reservationDAO)
        reservationStatesService.addReservationState(reservationStateDAO)

        reservationDAO.states.add(reservationStateDAO)
        reservationsService.updateReservation(reservationDAO)

        val reservationId = savedReservationId.reservationNum.toString() + RESERVATION_ID_DELIMITER +
                clientUsername + RESERVATION_ID_DELIMITER +
                periodNum.toString() + RESERVATION_ID_DELIMITER +
                apartmentId.toString()

        return Result.ok(reservationId)
    }

    fun getReservation(reservationId: String): Result<ReservationDTO> {
        val result = this.getReservationId(reservationId)
        if (!result.isOK())
            return Result.error(result.error())
        val reservationID = result.value()
        val reservationDAO = reservationsService.getReservation(reservationID) ?: return Result.error(HttpStatus.NOT_FOUND)

        return Result.ok(domainConverter.convertToReservation(reservationDAO))
    }

    fun deleteReservation(reservationId: String): Result<ReservationLimitedDTO> {
        val result = this.getReservationId(reservationId)
        if (!result.isOK())
            return Result.error(result.error())
        val reservationID = result.value()
        val reservationDAO = reservationsService.getReservation(reservationID) ?: return Result.error(HttpStatus.NOT_FOUND)

        val currentState = reservationDAO.states.last()
        val endDate = LocalDate.now()
        currentState.endDate = endDate
        val reservationStateId = ReservationStateId(State.CANCELED, reservationDAO.reservationId)
        val nextState = ReservationStateDAO(reservationStateId, endDate, endDate, reservationDAO)

        reservationStatesService.updateReservationState(currentState, nextState, reservationDAO)
        return Result.ok(domainConverter.convertToReservationLimited(reservationDAO))
    }

    fun searchApartmentReservations(apartmentId : Long,
                                    startDate: LocalDate?,
                                    endDate: LocalDate?,
                                    clientId: String?,
                                    states: List<State>?,
                                    pg: Int,
                                    len: Int) : Result<PageableListDTO<ReservationApartmentsListDTO>>{
        val reservationsPage = reservationsService.searchApartmentReservations(apartmentId, startDate, endDate, clientId, states, pg, len)
        val totalPages = reservationsPage.totalPages

        return Result.ok(PageableListDTO(reservationsPage
            .map{this.domainConverter.convertToReservationApartmentsList(it)}.toList(), totalPages))
    }

    fun searchUserReservations(clientId : String,
                               startDate: LocalDate?,
                               endDate: LocalDate?,
                               apartment: Long?,
                               states: List<State>?,
                               pg: Int,
                               len: Int) : Result<PageableListDTO<ReservationUsersListDTO>>{
        val reservationsPage = reservationsService.searchUserReservations(clientId, startDate, endDate, apartment, states, pg, len)
        val totalPages = reservationsPage.totalPages

        return Result.ok(PageableListDTO(reservationsPage
            .map{this.domainConverter.convertToReservationUsersList(it)}.toList(), totalPages))
    }

    fun getReservationId(id: String) : Result<ReservationId> {
        val tokens = id.split(RESERVATION_ID_DELIMITER)
        if( tokens.size != 4)
            return Result.error(HttpStatus.NOT_FOUND)
        val reservationNum = tokens[0].toInt()
        val client = usersService.getUser(tokens[1]) ?: return Result.error(HttpStatus.NOT_FOUND)
        val periodNum = tokens[2].toInt()
        val apartment = apartmentsService.getApartment(tokens[3].toLong())?: return Result.error(HttpStatus.NOT_FOUND)
        val period = periodsService.getPeriod(PeriodId(periodNum, apartment.apartmentId))?: return Result.error(HttpStatus.NOT_FOUND)

        return Result.ok(ReservationId(reservationNum, client.username, period.periodId))
    }


}