package com.example.Employee.controllers

import com.example.Employee.models.Holiday
import com.example.Employee.services.HolidaysService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.amshove.kluent.`should be equal to`
import org.springframework.http.HttpStatus
import java.util.*
import kotlin.math.hypot

internal class HolidaysControllerTest {
     private val holidaysService=mockk<HolidaysService>()
     private val holidaysController=HolidaysController(holidaysService)

     @Test
     fun `should create new holiday`(){
         every { holidaysService.createHoliday(fakeHoliday) }returns Unit

         val createdHoliday = holidaysController.createHoliday(fakeHoliday)

         createdHoliday.body `should be equal to` fakeHoliday
         createdHoliday.statusCode `should be equal to` HttpStatus.OK
         verify { holidaysService.createHoliday(fakeHoliday) }
     }

    @Test
    fun `should get all holidays`(){
        every { holidaysService.getHolidays() }returns listOf(fakeHoliday)

        val holidays=holidaysController.getHolidays()

        holidays.body `should be equal to` listOf(fakeHoliday)
        holidays.statusCode `should be equal to` HttpStatus.OK
    }

    @Test
    fun `should fetch a holiday with id`(){
        every { holidaysService.getHoliday(1) }returns fakeHoliday

        val holiday =holidaysController.getHoliday(1)

        holiday.body `should be equal to` fakeHoliday
        holiday.statusCode `should be equal to`  HttpStatus.OK
    }

    @Test
    fun `should update holiday`(){
        every { holidaysService.updateHoliday(1, fakeHoliday.copy(holiday = "75th Independence Day")) }returns Unit

        val updatedHoliday=holidaysController.updateHoliday(1, fakeHoliday.copy(holiday = "75th Independence Day"))

        updatedHoliday.body `should be equal to` fakeHoliday.copy(holiday = "75th Independence Day")
        updatedHoliday.statusCode `should be equal to` HttpStatus.OK
        verify { holidaysService.updateHoliday(1, fakeHoliday.copy(holiday = "75th Independence Day")) }
    }

    @Test
    fun `should delete a holiday`(){
        every { holidaysService.deleteHoliday(1) }returns Unit

        holidaysController.deleteHoliday(1)

        verify { holidaysService.deleteHoliday(1) }
    }
}
private val fakeHoliday= Holiday(id=1,date= Date(2022,7,15), holiday = "Independence Day")