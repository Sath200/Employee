package com.example.Employee.controllers

import com.example.Employee.models.Holidays
import com.example.Employee.services.HolidaysService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/holidays")
public class HolidaysController (private var holidaysService: HolidaysService){

    @PostMapping
    fun createHoliday(@RequestBody holiday: Holidays):ResponseEntity<Holidays> {
        holidaysService.createHoliday(holiday)
        return ResponseEntity.ok(holiday)
    }

    @GetMapping
    fun getHolidays(): MutableIterable<Holidays> {
        return holidaysService.getHolidays()
    }

    @GetMapping("/{id}")
    fun getHoliday(@PathVariable id: Int): Optional<Holidays> {
        return holidaysService.getHoliday(id)
    }

    @PutMapping("/{id}")
    fun updateHoliday(@PathVariable id: Int, @RequestBody holiday: Holidays): ResponseEntity<Holidays> {
        holidaysService.updateHoliday(id, holiday)
        return ResponseEntity.ok(holiday)

    }

    @DeleteMapping("/{id}")
    fun deleteHoliday(@PathVariable id: Int){
        holidaysService.deleteHoliday(id)
    }


}