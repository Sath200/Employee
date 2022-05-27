package com.example.Employee.controllers

import com.example.Employee.controllers.request.BankAccountRequest
import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.models.BankAccount
import com.example.Employee.models.Employee
import com.example.Employee.services.BankAccountService
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
@WebMvcTest(controllers = [BankAccountController::class])
@ContextConfiguration
internal class BankAccountControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var bankAccountService: BankAccountService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should add bank account of an employee`(){
        every { bankAccountService.addAccount(fakeEmployee.employeeId, fakeBankAccountRequest) }returns fakeBankAccount

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/employees/${fakeEmployee.employeeId}/bank_accounts")
                .content(objectMapper.writeValueAsBytes(fakeBankAccountRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fakeBankAccount)))
    }

    @Test
    fun `should get 404 while trying to add bank account to non existent employee`(){
        every { bankAccountService.addAccount(fakeEmployee.employeeId, fakeBankAccountRequest) }throws EntityNotFoundException("employee not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/employees/${fakeEmployee.employeeId}/bank_accounts")
                .content(objectMapper.writeValueAsBytes(fakeBankAccountRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("employee not found"))
    }

    @Test
    fun `should get all bank accounts of an employee`(){
        every { bankAccountService.getAccounts(fakeEmployee.employeeId) }returns listOf(fakeBankAccount)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/employees/${fakeEmployee.employeeId}/bank_accounts")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listOf(fakeBankAccount))))
    }

    @Test
    fun `should get 404 while trying to find bank accounts of non existent employee`(){
        every { bankAccountService.getAccounts(fakeEmployee.employeeId) } throws EntityNotFoundException("employee not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/employees/${fakeEmployee.employeeId}/bank_accounts")
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("employee not found"))
    }

    @Test
    fun `should get a particular bank account of an employee`(){
        every { bankAccountService.getAccount(fakeEmployee.employeeId, fakeBankAccount.id) }returns fakeBankAccount

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fakeBankAccount)))
    }

    @Test
    fun `should get 404 while trying to find bank account of non existent employee`(){
        every { bankAccountService.getAccount(fakeEmployee.employeeId, fakeBankAccount.id) } throws EntityNotFoundException("employee not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("employee not found"))
    }

    @Test
    fun `should get 404 while trying to find non existent bank account of employee`(){
        every { bankAccountService.getAccount(fakeEmployee.employeeId, fakeBankAccount.id) } throws EntityNotFoundException("bank account not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("bank account not found"))
    }

    @Test
    fun `should update a bank account of employee`(){
        every { bankAccountService.updateAccount(fakeEmployee.employeeId, fakeBankAccount.id, fakeBankAccountRequest.copy(bankName = "HDFC")) } returns fakeBankAccount

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
                .content(objectMapper.writeValueAsBytes(fakeBankAccountRequest.copy(bankName = "HDFC")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent)

        verify { bankAccountService.updateAccount(fakeEmployee.employeeId, fakeBankAccount.id, fakeBankAccountRequest.copy(bankName = "HDFC")) }
    }

    @Test
    fun `should get 404 while trying to update a bank account of non existent employee`(){
        every { bankAccountService.updateAccount(fakeEmployee.employeeId, fakeBankAccount.id, fakeBankAccountRequest.copy(bankName = "HDFC")) } throws EntityNotFoundException("employee not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
                .content(objectMapper.writeValueAsBytes(fakeBankAccountRequest.copy(bankName = "HDFC")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("employee not found"))
    }

    @Test
    fun `should get 404 while trying to update a non existent bank account of employee`(){
        every { bankAccountService.updateAccount(fakeEmployee.employeeId, fakeBankAccount.id, fakeBankAccountRequest.copy(bankName = "HDFC")) } throws EntityNotFoundException("bank account not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
                .content(objectMapper.writeValueAsBytes(fakeBankAccountRequest.copy(bankName = "HDFC")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("bank account not found"))
    }

    @Test
    fun `should delete a bank account of an employee`(){
        every { bankAccountService.deleteAccount(fakeEmployee.employeeId, fakeBankAccount.id) }returns Unit

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
                .content(objectMapper.writeValueAsBytes(fakeBankAccountRequest))
        ).andExpect(MockMvcResultMatchers.status().isNoContent)

        verify { bankAccountService.deleteAccount(fakeEmployee.employeeId, fakeBankAccount.id) }
    }

    @Test
    fun `should get 404 while trying to delete non existent employee`(){
        every { bankAccountService.deleteAccount(fakeEmployee.employeeId, fakeBankAccount.id) }throws EntityNotFoundException("employee not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
                .content(objectMapper.writeValueAsBytes(fakeBankAccountRequest))
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("employee not found"))
    }

    @Test
    fun `should get 404 while trying to delete non existent bank account of employee`(){
        every { bankAccountService.deleteAccount(fakeEmployee.employeeId, fakeBankAccount.id) }throws EntityNotFoundException("bank account not found")

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/employees/${fakeEmployee.employeeId}/bank_accounts/${fakeBankAccount.id}")
                .content(objectMapper.writeValueAsBytes(fakeBankAccountRequest))
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("bank account not found"))
    }


}
private val fakeEmployee= Employee(employeeId=0,name= "Vikram",age=25,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")
private val fakeBankAccountRequest= BankAccountRequest(id=0,bankName = "ICICI", accountNumber = "2345678", ifscCode = "ICICI2345")
private val fakeBankAccount= BankAccount(id = 0, bankName="ICICI", accountNumber = "2345678",ifscCode= "ICICI2345", employee = fakeEmployee)