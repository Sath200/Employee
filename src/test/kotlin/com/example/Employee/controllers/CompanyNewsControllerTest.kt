package com.example.Employee.controllers

import com.example.Employee.models.CompanyNews
import com.example.Employee.services.CompanyNewsService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.amshove.kluent.`should be equal to`
import org.springframework.http.HttpStatus

internal class CompanyNewsControllerTest{
    private val companyNewsService= mockk<CompanyNewsService>()
    private val companyNewsController=CompanyNewsController(companyNewsService)

    @Test
    fun `should add news`(){
        every { companyNewsService.addNews(fakeCompanyNews) }returns Unit

        val news=companyNewsController.addNews(fakeCompanyNews)

        news.body `should be equal to` fakeCompanyNews
        news.statusCode `should be equal to` HttpStatus.OK
    }

    @Test
    fun `should fetch news`(){
        every { companyNewsService.getNews() }returns listOf(fakeCompanyNews)

        val fetchedNews =companyNewsController.getNews()

        fetchedNews.body `should be equal to` listOf(fakeCompanyNews)
        fetchedNews.statusCode `should be equal to` HttpStatus.OK
    }

    @Test
    fun `should fetch news by id`(){
        every { companyNewsService.getNewsById(1) }returns fakeCompanyNews

        val fetchedNews =companyNewsController.getNewsById(1)

        fetchedNews.body `should be equal to` fakeCompanyNews
        fetchedNews.statusCode `should be equal to` HttpStatus.OK
    }

    @Test
    fun `should update news`(){
        every { companyNewsService.updateNews(1, fakeCompanyNews.copy(news = "welcome interns")) }returns Unit

        val updatedNews=companyNewsController.updateNews(1, fakeCompanyNews.copy(news = "welcome interns"))

        updatedNews.body `should be equal to` fakeCompanyNews.copy(news = "welcome interns")
        updatedNews.statusCode `should be equal to` HttpStatus.OK
    }

    @Test
    fun `should delete news`(){
        every { companyNewsService.deleteNews(1) }returns Unit

        companyNewsController.deleteNews(1)

        verify { companyNewsService.deleteNews(1) }
    }
}
private val fakeCompanyNews= CompanyNews(id=1,news = "Welcome New Joiners!!")