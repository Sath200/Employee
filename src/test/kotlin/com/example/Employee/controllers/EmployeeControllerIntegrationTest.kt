package com.example.Employee.controllers

import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.exceptions.InvalidRequestException
import com.example.Employee.models.Employee
import com.example.Employee.services.EmployeeService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [EmployeeController::class])
@ContextConfiguration
internal class EmployeeControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var employeeService: EmployeeService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should create employee`(){
        every { employeeService.addDetails(fakeEmployee) }returns Unit

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/employees")
                .content(objectMapper.writeValueAsBytes(fakeEmployee))
                .contentType(MediaType.APPLICATION_JSON)
        )  .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fakeEmployee)))

        verify { employeeService.addDetails(fakeEmployee) }
    }


    @Test
    fun `should get 400 while trying to create employee with age less than 18`(){
        every { employeeService.addDetails(fakeEmployee.copy(age=16)) } throws InvalidRequestException("age should be above 18")

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/employees")
                .content(objectMapper.writeValueAsBytes(fakeEmployee.copy(age=16)))
                .contentType(MediaType.APPLICATION_JSON)
        )  .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("age should be above 18"))
    }

    @Test
    fun `should get all employees`(){
        every { employeeService.get() } returns listOf(fakeEmployee)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/employees")

        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listOf(fakeEmployee))))
    }

    @Test
    fun `should get an employee with given id`(){
        every { employeeService.getEmployee(fakeEmployee.employeeId) }returns fakeEmployee

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/employees/${fakeEmployee.employeeId}")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fakeEmployee)))
    }

    @Test
    fun `should get 404 while trying to find non existent employee`(){
        every { employeeService.getEmployee(fakeEmployee.employeeId) }throws EntityNotFoundException("employee not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/employees/${fakeEmployee.employeeId}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("employee not found"))

    }
    @Test
    fun `should delete an employee`(){
        every { employeeService.deleteEmployee(fakeEmployee.employeeId) } returns Unit

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/employees/${fakeEmployee.employeeId}")
        )

        verify { employeeService.deleteEmployee(fakeEmployee.employeeId) }
    }

    @Test
    fun `should get 404 while trying to delete a non existent employee`(){
        every { employeeService.deleteEmployee(fakeEmployee.employeeId) } throws EntityNotFoundException("employee not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/employees/${fakeEmployee.employeeId}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("employee not found"))
    }

    @Test
    fun `should update an employee`(){
        every { employeeService.updateEmployee(fakeEmployee.employeeId, fakeEmployee) }returns Unit

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/employees/${fakeEmployee.employeeId}")
                .content(objectMapper.writeValueAsBytes(fakeEmployee))
                .contentType(MediaType.APPLICATION_JSON)
        )  .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fakeEmployee)))

        verify { employeeService.updateEmployee(fakeEmployee.employeeId, fakeEmployee) }
    }

    @Test
    fun `should get 404 while trying to update non existent employee`(){
        every { employeeService.updateEmployee(fakeEmployee.employeeId, fakeEmployee) }throws EntityNotFoundException("employee not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/employees/${fakeEmployee.employeeId}")
                .content(objectMapper.writeValueAsBytes(fakeEmployee))
                .contentType(MediaType.APPLICATION_JSON)
        )  .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("employee not found"))
    }
}

private val fakeEmployee= Employee(employeeId=0,name= "Vikram",age=25,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")