package com.example.Employee.services

import com.example.Employee.models.CompanyNews
import com.example.Employee.repositories.CompanyNewsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
public class CompanyNewsService(private val companyNewsRepository: CompanyNewsRepository) {

    fun addNews(news: CompanyNews){
        companyNewsRepository.save(news)
    }

    fun getNews(): List<CompanyNews> {
        return companyNewsRepository.findAll().toList()
    }

    fun getNewsById(id: Int): CompanyNews {
        return companyNewsRepository.findById(id).get()
    }

    fun updateNews(id: Int, news: CompanyNews){
        news.id=id
        companyNewsRepository.save(news)
    }

    fun deleteNews(id: Int){
        companyNewsRepository.deleteById(id)
    }
}