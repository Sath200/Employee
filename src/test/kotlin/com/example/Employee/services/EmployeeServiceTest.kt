package com.example.Employee.services


import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.exceptions.InvalidRequestException
import com.example.Employee.models.Employee
import com.example.Employee.repositories.EmployeeRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.*
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Assertions.*

internal class EmployeeServiceTest {
    private val employeeRepository= mockk<EmployeeRepository>()
    private val employeeService= EmployeeService(employeeRepository)

    @Test
    fun `should create employee`() {
        every {employeeRepository.save(fakeEmployee)} returns fakeEmployee.copy(employeeId=1)

        employeeService.addDetails(fakeEmployee)

        verify { employeeRepository.save(fakeEmployee)}
    }

    @Test
    fun `should throw exception while trying to create employee when age is less than 18`(){
        //val employeeToCreate = Employee(employeeId = 0, name= "Vikram",age=16,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")
        val employeeToCreate = fakeEmployee.copy(age=16)
        invoking { employeeService.addDetails(employeeToCreate) } shouldThrow InvalidRequestException("Age should be above 18")

        verify(exactly = 0){employeeRepository.save(any())}
    }

    @Test
    fun `should get all the employees`(){
        every {employeeRepository.findAll()} returns listOf(fakeEmployee)

        val fetchedEmployees = employeeService.get()

        fetchedEmployees `should be equal to` listOf(fakeEmployee)
        verify { employeeRepository.findAll() }
    }

    @Test
    fun `should get the employee`(){
        every {employeeRepository.findById(fakeEmployee.employeeId)} returns Optional.of(fakeEmployee)

        val fetchedEmployee= employeeService.getEmployee(employeeId = fakeEmployee.employeeId)

        fetchedEmployee `should be equal to` fakeEmployee
        verify { employeeRepository.findById(fakeEmployee.employeeId) }
    }

    @Test
    fun `should throw exception while trying to find non existent employee` (){
        every { employeeRepository.findById(fakeEmployee.employeeId) } returns Optional.empty()

        invoking { employeeService.getEmployee(fakeEmployee.employeeId) } shouldThrow EntityNotFoundException("employee not found")
    }

    @Test
    fun `should delete employee` (){
        every { employeeRepository.findById(fakeEmployee.employeeId) } returns Optional.of(fakeEmployee)
        every { employeeRepository.deleteById(fakeEmployee.employeeId)} returns Unit

        employeeService.deleteEmployee(fakeEmployee.employeeId)

        verify { employeeRepository.deleteById(fakeEmployee.employeeId) }
    }

    @Test
    fun `should throw exception while trying to delete non existent employee` (){
        every { employeeRepository.findById(fakeEmployee.employeeId) } returns Optional.empty()

        invoking { employeeService.deleteEmployee(fakeEmployee.employeeId)} shouldThrow EntityNotFoundException("employee not found")
    }

    @Test
    fun `should update employee`(){
        every{employeeRepository.findById(1)} returns Optional.of(fakeEmployee.copy(employeeId = 1))
        every{ employeeRepository.save(fakeEmployee.copy(employeeId = 1,location="Hyd"))} returns fakeEmployee.copy(employeeId = 1,location="Hyd")

        employeeService.updateEmployee(1, fakeEmployee.copy(location="Hyd"))

        verify {employeeRepository.save(fakeEmployee.copy(employeeId = 1,location="Hyd"))}

    }

    @Test
    fun `should throw exception while trying to update non existent employee`(){
        every { employeeRepository.findById(1) } returns Optional.empty()

        invoking {employeeService.updateEmployee(1, fakeEmployee.copy(location="Hyd"))} shouldThrow  EntityNotFoundException("employee not found")
    }
}

private val fakeEmployee= Employee(employeeId=0,name= "Vikram",age=25,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")