package com.example.Employee.controllers

import com.example.Employee.models.Holiday
import com.example.Employee.services.HolidaysService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/holidays")
public class HolidaysController (private val holidaysService: HolidaysService){

    @PostMapping
    fun createHoliday(@RequestBody holiday: Holiday):ResponseEntity<Holiday> {
        holidaysService.createHoliday(holiday)
        return ResponseEntity.ok(holiday)
    }

    @GetMapping
    fun getHolidays(): ResponseEntity<List<Holiday>> {
        return ResponseEntity.ok(holidaysService.getHolidays())
    }

    @GetMapping("/{id}")
    fun getHoliday(@PathVariable id: Int): ResponseEntity<Holiday> {
        return ResponseEntity.ok(holidaysService.getHoliday(id))
    }

    @PutMapping("/{id}")
    fun updateHoliday(@PathVariable id: Int, @RequestBody holiday: Holiday): ResponseEntity<Holiday> {
        holidaysService.updateHoliday(id, holiday)
        return ResponseEntity.ok(holiday)

    }

    @DeleteMapping("/{id}")
    fun deleteHoliday(@PathVariable id: Int){
        holidaysService.deleteHoliday(id)
    }


}