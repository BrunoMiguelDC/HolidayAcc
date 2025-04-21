package ciai.holidayacc

import ciai.holidayacc.data.UserRepository
import ciai.holidayacc.domain.UserDAO
import ciai.holidayacc.presentation.dto.UserDTO
import ciai.holidayacc.presentation.dto.UserPasswordlessDTO
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.get

@ActiveProfiles("test")
@SpringBootTest(
    classes = [HolidayAcc::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HolidayAccTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var users: UserRepository

    @Test
    fun `basic Rest Test` (){
        val user = Optional.of(UserDAO("usr1", "1", "name1", "name1@gmail.com", "/pic1.jpg",
            "937777771", mutableListOf(), mutableListOf()
        ))

        Mockito.`when`(users.findById("usr1")).thenReturn(user)

        mvc.get("/api/users/usr1").andExpect { status { isOk() } }

    }
}