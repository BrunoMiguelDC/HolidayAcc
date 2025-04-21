package ciai.holidayacc.boot

import ciai.holidayacc.data.*
import ciai.holidayacc.domain.*
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.Period

@Profile("!test")
@Component
@Order(1)
    class Init(val usersRepo:UserRepository, val apartmentsRep: ApartmentRepository, val periodsRep: PeriodsRepository,
               val reservationsRep: ReservationsRepository, val reservationStatesRep: ReservationStatesRepository) : CommandLineRunner {
    @Transactional
    override fun run(vararg args: String?) {
        val lorem:Lorem = LoremIpsum.getInstance()
        val range = (1..11)

        val manager = UserDAO("admin", "0", "manager", "manager@gmail.com",
            "/pic0.jpg", "917777770", mutableListOf(), mutableListOf())
        val user1 = UserDAO("user1", "1", "Name1", "name1@gmail.com",
            "/pic1.jpg", "917777771", mutableListOf(), mutableListOf())
        val user2 = UserDAO("user2", "2", "Name2", "name2@gmail.com",
            "/pic2.jpg", "927777772", mutableListOf(), mutableListOf())
        val user3 = UserDAO("user3", "3", "Name3", "name3@gmail.com",
            "/pic3.jpg", "937777773", mutableListOf(), mutableListOf())
        val user4 = UserDAO("user4", "4", "Name4", "name4@gmail.com",
            "/pic4.jpg", "947777774", mutableListOf(), mutableListOf())

        usersRepo.saveAll(listOf(manager, user1, user2, user3, user4))

        val amenities = mutableSetOf<String>("good", "spacious", "cool")
        val amenities1 = mutableSetOf<String>("pool", "kitchen", "A/C")
        val pictures = mutableSetOf<String>("https://a0.muscache.com/im/pictures/81d71177-10f1-4c17-878c-82d2e51ef643.jpg?im_w=1200", "https://a0.muscache.com/im/pictures/6ddd0b9f-51a4-45a3-b59b-235e67f48e35.jpg?im_w=1200", "https://a0.muscache.com/im/pictures/9457df67-cb8f-480e-b3bb-98b269ace9bf.jpg?im_w=1200")
        val pictures1 = mutableSetOf<String>("https://mygate.com/wp-content/uploads/2023/07/110.jpg")
        val pictures2 = mutableSetOf<String>("https://www.rocketmortgage.com/resources-cmsassets/RocketMortgage.com/Article_Images/Large_Images/Types%20Of%20Homes/Stock-Gray-Ranch-Style-Home-AdobeStock_279953994-copy.jpeg")
        val pictures3 = mutableSetOf<String>("https://i.pinimg.com/564x/fe/29/8a/fe298a70a49d93f50c62ae40c5ecce3a.jpg")
        val pictures4 = mutableSetOf<String>("https://images.adsttc.com/media/images/5e1d/02c3/3312/fd58/9c00/06e9/large_jpg/NewHouse_SA_Photo_01.jpg?1578959519")
        val pictures5 = mutableSetOf<String>("https://www.travelandleisure.com/thmb/5KH1_3dKLruIyfz8XLdgLtuMp6I=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/TAL-hidden-haven-villa-south-africa-airbnb-NEWBNBCAT0123-0e3c6ef5dce0490aac7dd5cd92b27f33.jpg")
        val pictures6 = mutableSetOf<String>("https://empire-s3-production.bobvila.com/slides/45183/original/iStock-1312034472.-old-homes-airbnb.jpg?1666061562")

        val apartment1 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities, pictures,
            320f, true, range.random(), mutableListOf(), user2)
        val apartment2 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities1, pictures1,
            231f, true, range.random(), mutableListOf(), user1)
        val apartment3 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(),amenities, pictures2,
            3124f, true, range.random(), mutableListOf(), user1)
        val apartment4 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities1, pictures3,
            323f, true, range.random(), mutableListOf(), user2)
        val apartment5 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities, pictures4,
            234f, true, range.random(), mutableListOf(), user2)
        val apartment6 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities1, pictures5,
            3124f, true, range.random(), mutableListOf(), user2)
        val apartment7 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities, pictures6,
            320f, true, range.random(), mutableListOf(), user3)
        val apartment8 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities1, pictures,
            421412f, true, range.random(), mutableListOf(), user1)
        val apartment9 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities, pictures1,
            312324f, true, range.random(), mutableListOf(), user1)
        val apartment10 = ApartmentDAO(0,lorem.getTitle(2, 4), lorem.getWords(200, 250), lorem.getCity(), amenities1, pictures2,
            320f, true, range.random(), mutableListOf(), user1)

        apartmentsRep.saveAll(listOf(apartment1, apartment2, apartment3, apartment4, apartment5, apartment6, apartment7))

        user1.apartments.addAll(listOf(apartment2, apartment3, apartment8, apartment9, apartment10))
        user2.apartments.addAll(listOf(apartment1, apartment4,  apartment5,  apartment6))
        user3.apartments.add(apartment7)

        usersRepo.saveAll(listOf(user1, user2, user3, user4))

        val periodId1 = PeriodId(0, 1)
        val periodId2 = PeriodId(1, 4)
        val periodId3 = PeriodId(2, 4)

        val period1 = PeriodDAO(periodId1,LocalDate.now(),LocalDate.now().plus(Period.ofDays(15)), 15, apartment1, mutableListOf())
        val period2 = PeriodDAO(periodId2,LocalDate.now(),LocalDate.now().plus(Period.ofDays(10)), 10, apartment3, mutableListOf())
        val period3 = PeriodDAO(periodId3,LocalDate.now().plus(Period.ofDays(15)),LocalDate.now().plus(Period.ofDays(25)), 25, apartment3, mutableListOf())
        periodsRep.saveAll(listOf(period1, period2, period3))

        val reservationId1 = ReservationId(0, "user1", periodId1)
        val reservationId2 = ReservationId(1, "user1", periodId2)
        val reservationId3 = ReservationId(2, "user1", periodId3)

        val reservation1 = ReservationDAO(reservationId1, LocalDate.now(), LocalDate.now().plus(Period.ofDays(5)),  range.random(), user1, period1, mutableListOf(), null)
        val reservation2 = ReservationDAO(reservationId2, LocalDate.now(), LocalDate.now().plus(Period.ofDays(4)),  range.random(), user1, period2, mutableListOf(), null)
        val reservation3 = ReservationDAO(reservationId3, LocalDate.now().plus(Period.ofDays(16)), LocalDate.now().plus(Period.ofDays(18)),  range.random(), user1, period3, mutableListOf(), null)
        reservationsRep.saveAll(listOf(reservation1, reservation2, reservation3))

        val reservationStateId1 = ReservationStateId(State.BOOKED, reservationId1)
        val reservationStateId2 = ReservationStateId(State.AWAITING_REVIEW, reservationId2)
        val reservationStateId3 = ReservationStateId(State.CANCELED, reservationId3)

        val reservationState1 = ReservationStateDAO(reservationStateId1, LocalDate.now(), null, reservation1)
        val reservationState2 = ReservationStateDAO(reservationStateId2, LocalDate.now(), null, reservation2)
        val reservationState3 = ReservationStateDAO(reservationStateId3, LocalDate.now(), null, reservation3)
        reservationStatesRep.saveAll(listOf(reservationState1, reservationState2, reservationState3))

        reservation1.states.add(reservationState1)
        reservation2.states.add(reservationState2)
        reservation3.states.add(reservationState3)

        reservationsRep.saveAll(listOf(reservation1, reservation2, reservation2))

        user1.reservations.addAll(listOf(reservation1, reservation2, reservation3))

        usersRepo.save(user1)

        period1.reservations.add(reservation1)
        period2.reservations.add(reservation2)
        period3.reservations.add(reservation3)

        periodsRep.saveAll(listOf(period1, period2, period3))

        apartment1.periods.add(period1)
        apartment3.periods.add(period2)
        apartment3.periods.add(period3)

        apartmentsRep.saveAll(listOf(apartment1, apartment3, apartment3))
    }
}
