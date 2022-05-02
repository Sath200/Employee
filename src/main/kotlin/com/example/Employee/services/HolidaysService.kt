package com.example.Employee.services

import com.example.Employee.models.Holiday
import com.example.Employee.repositories.HolidaysRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
public class HolidaysService (private var holidaysRepository: HolidaysRepository){
    fun createHoliday(holiday: Holiday){
        holidaysRepository.save(holiday)
    }

    fun getHolidays( ): MutableIterable<Holiday> {
        return holidaysRepository.findAll()
    }

    fun getHoliday(id: Int): Optional<Holiday> {
        return holidaysRepository.findById(id)
    }

    fun updateHoliday(id: Int, holiday: Holiday){
        holiday.setId(id)
        holidaysRepository.save(holiday)
    }

    fun deleteHoliday(id: Int){
        holidaysRepository.deleteById(id)
    }
}