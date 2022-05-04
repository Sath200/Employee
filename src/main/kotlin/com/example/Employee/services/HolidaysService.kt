package com.example.Employee.services

import com.example.Employee.exceptions.EntityNotFoundException
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
        return holidaysRepository.findById(id).orElseThrow { throw EntityNotFoundException("holiday with given id does not exist") }
    }

    fun updateHoliday(id: Int, holiday: Holiday){
        val holidayExists=holidaysRepository.findById(id)
        if(holidayExists.isPresent){
            holiday.id=id
            holidaysRepository.save(holiday)
        } else{
            throw EntityNotFoundException("holiday with given id does not exist")
        }
    }

    fun deleteHoliday(id: Int){
        val holidayExists=holidaysRepository.findById(id)
        if(holidayExists.isPresent) {
            holidaysRepository.deleteById(id)
        } else{
            throw EntityNotFoundException("holiday with given id does not exist")
        }
    }
}