package com.example.Employee.repositories

import com.example.Employee.models.Holiday
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class HolidaysRepositoryTest {

    @Autowired
    private lateinit var holidaysRepository: HolidaysRepository

    @Test
    fun `should create holiday`(){
        val savedHoliday= holidaysRepository.save(fakeHoliday)

        savedHoliday.id `should not be` 0
        savedHoliday `should be equal to` fakeHoliday.copy(id = savedHoliday.id)
    }

    @Test
    fun `should get all holidays`(){
        val savedHoliday=holidaysRepository.save(fakeHoliday)
        val fetchedHoliday = holidaysRepository.findAll().toList()

        fetchedHoliday `should be equal to` listOf(savedHoliday)
    }

    @Test
    fun `should get details of holiday using id`(){
        val savedHoliday=holidaysRepository.save(fakeHoliday)
        val fetchedHoliday=holidaysRepository.findById(savedHoliday.id)

        fetchedHoliday.get() `should be equal to` savedHoliday

    }

    @Test
    fun `should update holiday`(){
        val savedHoliday=holidaysRepository.save(fakeHoliday)
        holidaysRepository.save(savedHoliday.copy(holiday = "75th Independence Day"))
        val fetchedHoliday=holidaysRepository.findById(savedHoliday.id)

        fetchedHoliday.get() `should be equal to` savedHoliday.copy(holiday = "75th Independence Day")
    }

    @Test
    fun `should delete holiday`(){
        val savedHoliday=holidaysRepository.save(fakeHoliday)
        holidaysRepository.deleteById(savedHoliday.id)
        val fetchedHoliday=holidaysRepository.findById(savedHoliday.id)

        fetchedHoliday `should be equal to` Optional.empty()
    }
}
private val fakeHoliday= Holiday(id=0,date= Date(2022,7,15), holiday = "Independence Day")