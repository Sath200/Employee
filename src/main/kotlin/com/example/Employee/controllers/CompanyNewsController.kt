package com.example.Employee.controllers

import com.example.Employee.models.CompanyNews
import com.example.Employee.services.CompanyNewsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/companyNews")
public class CompanyNewsController (private var companyNewsService: CompanyNewsService){

    @PostMapping
    fun addNews(@RequestBody companyNews: CompanyNews): ResponseEntity<CompanyNews>{

        companyNewsService.addNews(companyNews)
        return ResponseEntity.ok(companyNews)
    }

    @GetMapping
    fun getNews(): MutableIterable<CompanyNews> {
         return companyNewsService.getNews()
    }

    @GetMapping("/{id}")
    fun getNewsById(@PathVariable id: Int): Optional<CompanyNews> {
        return companyNewsService.getNewsById(id)
    }

    @PutMapping("/{id}")
    fun updateNews(@PathVariable id: Int, @RequestBody companyNews: CompanyNews): ResponseEntity<CompanyNews>
    {
        companyNewsService.updateNews(id,companyNews)
        return ResponseEntity.ok(companyNews)
    }

    @DeleteMapping("/{id}")
    fun deleteNews(@PathVariable id: Int){
        companyNewsService.deleteNews(id)
    }



}