package com.example.Employee.services

import com.example.Employee.models.CompanyNews
import com.example.Employee.repositories.CompanyNewsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
public class CompanyNewsService(private var companyNewsRepository: CompanyNewsRepository) {

    fun addNews(news: CompanyNews){
        companyNewsRepository.save(news)
    }

    fun getNews(): MutableIterable<CompanyNews> {
        return companyNewsRepository.findAll()
    }

    fun getNewsById(id: Int): Optional<CompanyNews> {
        return companyNewsRepository.findById(id)
    }

    fun updateNews(id: Int, news: CompanyNews){
        news.setid(id)
        companyNewsRepository.save(news)
    }

    fun deleteNews(id: Int){
        companyNewsRepository.deleteById(id)
    }
}