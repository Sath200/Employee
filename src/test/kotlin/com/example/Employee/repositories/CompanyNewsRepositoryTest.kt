package com.example.Employee.repositories

import com.example.Employee.models.CompanyNews
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class CompanyNewsRepositoryTest {
    @Autowired
    private lateinit var companyNewsRepository:CompanyNewsRepository

    @Test
    fun `should add news`(){
        val savedNews=companyNewsRepository.save(fakeCompanyNews)

        savedNews.id `should not be` 0
        savedNews `should be equal to` fakeCompanyNews.copy(id=savedNews.id)
    }

    @Test
    fun `should get news`(){
        val savedNews=companyNewsRepository.save(fakeCompanyNews)
        val fetchedNews=companyNewsRepository.findAll().toList()

        fetchedNews `should be equal to` listOf(savedNews)
    }

    @Test
    fun `should get news by id`(){
        val savedNews=companyNewsRepository.save(fakeCompanyNews)
        val fetchedNews=companyNewsRepository.findById(savedNews.id)

        fetchedNews.get() `should be equal to` savedNews
    }

    @Test
    fun `should update news`(){
        val savedNews=companyNewsRepository.save(fakeCompanyNews)
        companyNewsRepository.save(savedNews.copy(news="Welcome Interns!!"))
        val fetchedNews=companyNewsRepository.findById(savedNews.id)

        fetchedNews.get() `should be equal to` fakeCompanyNews.copy(id=savedNews.id, news = "Welcome Interns!!")
    }

    @Test
    fun `should delete news`(){
        val savedNews=companyNewsRepository.save(fakeCompanyNews)
        companyNewsRepository.deleteById(savedNews.id)
        val fetchedNews=companyNewsRepository.findById(savedNews.id)

        fetchedNews `should be equal to` Optional.empty()
    }

}
private val fakeCompanyNews= CompanyNews(id=0,news = "Welcome New Joiners!!")