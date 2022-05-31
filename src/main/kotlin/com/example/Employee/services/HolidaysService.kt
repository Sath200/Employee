package com.example.Employee.services

import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.models.Holiday
import com.example.Employee.repositories.HolidaysRepository
import org.springframework.stereotype.Service

@Service
class HolidaysService (private val holidaysRepository: HolidaysRepository){
    fun createHoliday(holiday: Holiday): Holiday {
        return holidaysRepository.save(holiday)
    }

    fun getHolidays( ): List<Holiday> {
        return holidaysRepository.findAll().toList()
    }

    fun getHoliday(id: Int): Holiday {
        return holidaysRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException("holiday with given id does not exist") }
    }

    fun updateHoliday(id: Int, holiday: Holiday): Holiday {
        val existingHoliday=holidaysRepository.findById(id)
        if(existingHoliday.isEmpty){
            throw EntityNotFoundException("holiday with given id does not exist")
        }
        holiday.id=id
        return holidaysRepository.save(holiday)

    }

    fun deleteHoliday(id: Int){
        val existingHoliday=holidaysRepository.findById(id)
        if(existingHoliday.isEmpty){
            throw EntityNotFoundException("holiday with given id does not exist")
        }
        holidaysRepository.deleteById(id)

    }
}