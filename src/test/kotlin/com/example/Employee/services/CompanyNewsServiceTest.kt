package com.example.Employee.services


import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.models.CompanyNews
import com.example.Employee.repositories.CompanyNewsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.*
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow

internal class CompanyNewsServiceTest {
    private val companyNewsRepository = mockk<CompanyNewsRepository>()
    private val companyNewsService = CompanyNewsService(companyNewsRepository)

    @Test
    fun `should create news`(){
        every { companyNewsRepository.save(fakeCompanyNews) } returns fakeCompanyNews

        val createdNews=companyNewsService.addNews(fakeCompanyNews)

        createdNews `should be equal to` fakeCompanyNews
    }

    @Test
    fun `should get news`(){
        every { companyNewsRepository.findAll() } returns listOf(fakeCompanyNews)

        val fetchedNews = companyNewsService.getNews()

        fetchedNews `should be equal to` listOf(fakeCompanyNews)

    }

    @Test
    fun `should get news by id`(){
        every { companyNewsRepository.findById(1) }returns Optional.of(fakeCompanyNews.copy(id=1))

        val fetchedNews=companyNewsService.getNewsById(1)

        fetchedNews `should be equal to` fakeCompanyNews.copy(id=1)
    }

    @Test
    fun `should throw exception while trying to access non existent news`(){
        every { companyNewsRepository.findById(1) }returns Optional.empty()

        invoking { companyNewsService.getNewsById(1) }shouldThrow EntityNotFoundException("news with given Id does not exist")
    }

    @Test
    fun `should delete news with given id`(){
        every { companyNewsRepository.findById(1) }returns Optional.of(fakeCompanyNews.copy(id=1))
        every { companyNewsRepository.deleteById(1) } returns Unit

        companyNewsRepository.deleteById(1)

        verify { companyNewsRepository.deleteById(1) }
    }

    @Test
    fun `should throw exception while trying to delete non existent news`(){
        every {companyNewsRepository.findById(fakeCompanyNews.id)} returns Optional.empty()

        invoking {companyNewsService.deleteNews(fakeCompanyNews.id) } shouldThrow EntityNotFoundException("news with given Id does not exist")
    }

    @Test
    fun `should update news`(){
        every { companyNewsRepository.findById(fakeCompanyNews.id) }returns Optional.of(fakeCompanyNews)
        every{companyNewsRepository.save(fakeCompanyNews)}returns fakeCompanyNews.copy(news="Welcome Interns")

        val updatedNews=companyNewsService.updateNews(fakeCompanyNews.id, fakeCompanyNews)

        updatedNews `should be equal to` fakeCompanyNews.copy(news="Welcome Interns")
    }

    @Test
    fun `should throw exception while trying to update non existent news`(){
        every { companyNewsRepository.findById(fakeCompanyNews.id) }returns Optional.empty()

        invoking { companyNewsService.updateNews(fakeCompanyNews.id, fakeCompanyNews) } shouldThrow EntityNotFoundException("news with given Id does not exist")

    }
}
private val fakeCompanyNews= CompanyNews(id=0,news = "Welcome New Joiners!!")