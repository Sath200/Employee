package com.example.Employee.services

import com.example.Employee.models.Holiday
import com.example.Employee.repositories.HolidaysRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
public class HolidaysService (private val holidaysRepository: HolidaysRepository){
    fun createHoliday(holiday: Holiday){
        holidaysRepository.save(holiday)
    }

    fun getHolidays( ): List<Holiday> {
        return holidaysRepository.findAll().toList()
    }

    fun getHoliday(id: Int): Holiday {
        return holidaysRepository.findById(id).get()
    }

    fun updateHoliday(id: Int, holiday: Holiday){
        holiday.id=id
        holidaysRepository.save(holiday)
    }

    fun deleteHoliday(id: Int){
        holidaysRepository.deleteById(id)
    }
}