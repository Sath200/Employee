package com.example.Employee.repositories

import com.example.Employee.models.Employee

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
internal class EmployeeRepositoryTest {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @Test
    fun `should create employee`(){
        val savedEmployee = employeeRepository.save(fakeEmployee)

        savedEmployee.employeeId `should not be` 0
        savedEmployee `should be equal to` fakeEmployee.copy(employeeId = savedEmployee.employeeId)
    }


    @Test
    fun `should get details of all the employees`(){
        val savedEmployee=employeeRepository.save(fakeEmployee)
        val fetchedEmployee = employeeRepository.findAll().toList()

        fetchedEmployee `should be equal to` listOf(savedEmployee)
    }

    @Test
    fun `should get details of employee using id`(){
        val savedEmployee = employeeRepository.save(fakeEmployee)
        val fetchedEmployee=employeeRepository.findById(savedEmployee.employeeId).get()

        fetchedEmployee `should be equal to` savedEmployee
    }

    @Test
    fun `should update employee`(){
        val savedEmployee=employeeRepository.save(fakeEmployee)
        employeeRepository.save(savedEmployee.copy(name="aishwarya"))
        val fetchUpdatedEmployee=employeeRepository.findById(savedEmployee.employeeId)

        fetchUpdatedEmployee.get() `should be equal to` savedEmployee.copy(name="aishwarya")
    }

    @Test
    fun `should delete employee`(){
        val savedEmployee=employeeRepository.save(fakeEmployee)
        employeeRepository.deleteById(savedEmployee.employeeId)
        val fetchUpdatedEmployee=employeeRepository.findById(savedEmployee.employeeId)

        fetchUpdatedEmployee `should be equal to` Optional.empty()
    }
}

private val fakeEmployee= Employee(employeeId = 0, name= "Vikram",age=25,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")
