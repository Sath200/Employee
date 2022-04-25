package com.example.Employee.services

import com.example.Employee.models.Holidays
import com.example.Employee.repositories.HolidaysRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
public class HolidaysService (private var holidaysRepository: HolidaysRepository){
    fun createHoliday(holiday: Holidays){
        holidaysRepository.save(holiday)
    }

    fun getHolidays( ): MutableIterable<Holidays> {
        return holidaysRepository.findAll()
    }

    fun getHoliday(id: Int): Optional<Holidays> {
        return holidaysRepository.findById(id)
    }

    fun updateHoliday(id: Int, holiday: Holidays){
        holiday.setId(id)
        holidaysRepository.save(holiday)
    }

    fun deleteHoliday(id: Int){
        holidaysRepository.deleteById(id)
    }
}