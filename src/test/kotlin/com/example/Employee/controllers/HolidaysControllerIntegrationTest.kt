package com.example.Employee.controllers

import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.models.Holiday
import com.example.Employee.services.HolidaysService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*


@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [HolidaysController::class])
@ContextConfiguration
internal class HolidaysControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var holidaysService: HolidaysService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should add new holiday`(){
        every { holidaysService.createHoliday(fakeHoliday) }returns fakeHoliday

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/holidays")
                .content(objectMapper.writeValueAsBytes(fakeHoliday))
                .contentType(MediaType.APPLICATION_JSON)
        )  .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fakeHoliday)))
    }

    @Test
    fun `should get all the holidays`(){
        every { holidaysService.getHolidays() }returns listOf(fakeHoliday)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/holidays")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listOf(fakeHoliday))))
    }

    @Test
    fun `should get 404 while trying to get non existent holiday`(){
       every { holidaysService.getHoliday(fakeHoliday.id) }throws EntityNotFoundException("holiday with given id does not exist")

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/holidays/${fakeHoliday.id}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("holiday with given id does not exist"))

    }

    @Test
    fun `should get holiday with id`(){
        every { holidaysService.getHoliday(fakeHoliday.id) } returns fakeHoliday

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/holidays/${fakeHoliday.id}")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fakeHoliday)))
    }

    @Test
    fun `should update holiday`(){
        every { holidaysService.updateHoliday(fakeHoliday.id, fakeHoliday.copy(holiday = "75th Independence Day")) }returns fakeHoliday

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/holidays/${fakeHoliday.id}")
                .content(objectMapper.writeValueAsBytes(fakeHoliday.copy(holiday = "75th Independence Day")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent)

        verify { holidaysService.updateHoliday(fakeHoliday.id, fakeHoliday.copy(holiday="75th Independence Day")) }
    }

    @Test
    fun `should get 404 while trying to update non existent holiday`(){
        every { holidaysService.updateHoliday(fakeHoliday.id, fakeHoliday.copy(holiday = "75th Independence Day")) }throws EntityNotFoundException("holiday with given id does not exist")

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/holidays/${fakeHoliday.id}")
                .content(objectMapper.writeValueAsBytes(fakeHoliday.copy(holiday = "75th Independence Day")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("holiday with given id does not exist"))

    }

    @Test
    fun `should delete holiday`(){
        every { holidaysService.deleteHoliday(fakeHoliday.id) } returns Unit

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/holidays/${fakeHoliday.id}")
        ).andExpect(MockMvcResultMatchers.status().isNoContent)

        verify { holidaysService.deleteHoliday(fakeHoliday.id) }
    }

    @Test
    fun `should get 404 while trying to delete non existent holiday`(){
        every { holidaysService.deleteHoliday(fakeHoliday.id) }throws EntityNotFoundException("holiday with given id does not exist")

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/holidays/${fakeHoliday.id}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("holiday with given id does not exist"))
    }
}
private val fakeHoliday= Holiday(id=1,date= Date(2022,7,15), holiday = "Independence Day")