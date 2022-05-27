package com.example.Employee.controllers

import com.example.Employee.models.Employee
import com.example.Employee.services.EmployeeService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.amshove.kluent.`should be equal to`
import org.springframework.http.HttpStatus

internal class EmployeeControllerTest{
    private val employeeService=mockk<EmployeeService>()
    private val employeeController=EmployeeController(employeeService)

    @Test
    fun `should create employee`() {
        every { employeeService.addDetails(fakeEmployee) }returns Unit

        val createdEmployee=employeeController.addEmployee(fakeEmployee)

        createdEmployee.body `should be equal to` fakeEmployee
        createdEmployee.statusCode `should be equal to` HttpStatus.OK
    }

    @Test
    fun `should get details of all the employees`(){
        every { employeeService.get() }returns listOf(fakeEmployee)

        val employees=employeeController.get()

        employees.body `should be equal to` listOf(fakeEmployee)
        employees.statusCode `should be equal to` HttpStatus.OK
    }

    @Test
    fun `should get details of an employee`(){
        every { employeeService.getEmployee(1) } returns fakeEmployee

        val employee=employeeController.getEmployee(fakeEmployee.employeeId)

        employee.body `should be equal to` fakeEmployee
        employee.statusCode `should be equal to` HttpStatus.OK

    }

    @Test
    fun `should update the employee`(){
        every {employeeService.updateEmployee(1, fakeEmployee.copy(name="Sathwika"))} returns Unit

        val updatedEmployee=employeeController.updateEmployee(1, fakeEmployee.copy(name="Sathwika"))

        updatedEmployee.body `should be equal to` fakeEmployee.copy(name="Sathwika")
        updatedEmployee.statusCode `should be equal to` HttpStatus.OK
    }

    @Test
    fun `should delete the employee`(){
        every { employeeService.deleteEmployee(1) }returns Unit

        employeeController.deleteEmployee(1)

        verify { employeeService.deleteEmployee(1) }
    }
}
private val fakeEmployee= Employee(employeeId=1,name= "Vikram",age=25,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")