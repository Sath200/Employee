package com.example.Employee.services

import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.models.Holiday
import com.example.Employee.repositories.HolidaysRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*



internal class HolidaysServiceTest{
    private val holidaysRepository=mockk<HolidaysRepository>()
    private val holidaysService=HolidaysService(holidaysRepository)


    @Test
    fun `should add holiday`(){
        every { holidaysRepository.save(fakeHoliday) } returns fakeHoliday

        holidaysService.createHoliday(fakeHoliday)

        verify { holidaysRepository.save(fakeHoliday) }
    }

    @Test
    fun `should get list of all holidays`(){
        every { holidaysRepository.findAll() }returns listOf(fakeHoliday)

        val fetchedHolidays=holidaysService.getHolidays()

        fetchedHolidays `should be equal to` listOf(fakeHoliday)
    }

    @Test
    fun `should get holiday by id`(){
        every { holidaysRepository.findById(fakeHoliday.id) } returns Optional.of(fakeHoliday)

        val  fetchedHoliday=holidaysService.getHoliday(fakeHoliday.id)

        fetchedHoliday `should be equal to` fakeHoliday
    }

    @Test
    fun `should give an error message while trying to get non existent holiday`(){
        every { holidaysRepository.findById(fakeHoliday.id) }returns Optional.empty()

        invoking { holidaysService.getHoliday(fakeHoliday.id) }shouldThrow EntityNotFoundException("holiday with given id does not exist")
    }

    @Test
    fun `should update holiday`(){
        every { holidaysRepository.findById(fakeHoliday.id) } returns Optional.of(fakeHoliday)
        every { holidaysRepository.save(fakeHoliday) }returns fakeHoliday.copy(holiday="75th Independence Day")

        holidaysService.updateHoliday(fakeHoliday.id, fakeHoliday)

        verify { holidaysRepository.save(fakeHoliday) }
    }

    @Test
    fun `should give an error message while trying to update non existent holiday`(){
        every { holidaysRepository.findById(fakeHoliday.id) }returns Optional.empty()

        invoking { holidaysService.updateHoliday(fakeHoliday.id, fakeHoliday.copy(holiday ="75th Independence Day" )) }shouldThrow EntityNotFoundException("holiday with given id does not exist")
    }

    @Test
    fun `should delete holiday`(){
        every { holidaysRepository.findById(fakeHoliday.id) } returns Optional.of(fakeHoliday)
        every { holidaysRepository.deleteById(fakeHoliday.id) }returns Unit

        holidaysService.deleteHoliday(fakeHoliday.id)

        verify { holidaysRepository.deleteById(fakeHoliday.id)}
    }

    @Test
    fun `should give an error message while trying to delete a non existent holiday`(){
        every { holidaysRepository.findById(fakeHoliday.id) } returns Optional.empty()

        invoking { holidaysService.deleteHoliday(fakeHoliday.id) } shouldThrow EntityNotFoundException("holiday with given id does not exist")
    }





}
private val fakeHoliday=Holiday(id=0,date= Date(2022,7,15), holiday = "Independence Day")