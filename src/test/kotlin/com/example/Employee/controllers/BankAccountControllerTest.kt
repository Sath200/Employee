package com.example.Employee.controllers

import com.example.Employee.controllers.request.BankAccountRequest
import com.example.Employee.models.BankAccount
import com.example.Employee.models.Employee
import com.example.Employee.services.BankAccountService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.amshove.kluent.`should be equal to`

internal class BankAccountControllerTest {
    private val bankAccountService=mockk<BankAccountService>()
    private val bankAccountController=BankAccountController(bankAccountService)

    @Test
    fun `should add bank details of employee`(){
        every { bankAccountService.addAccount(1, fakeBankAccountRequest) }returns Unit

        val addedAccount=bankAccountController.addAccount(1, fakeBankAccountRequest)
        println(addedAccount)

        addedAccount.body `should be equal to` fakeBankAccountRequest
    }

    @Test
    fun `should get all bank accounts of an employee`(){
        every { bankAccountService.getAccounts(1) }returns listOf(fakeBankAccount)

        val accountsFetched=bankAccountController.getAccounts(1)

        accountsFetched.body `should be equal to` listOf(fakeBankAccount)
    }

    @Test
    fun `should get a particular bank account of an employee`(){
        every { bankAccountService.getAccount(1,1) }returns fakeBankAccount

        val accountFetched= bankAccountController.getAccount(1,1)

        accountFetched.body `should be equal to` fakeBankAccount
    }

    @Test
    fun `should update a bank account`(){
        every { bankAccountService.updateAccount(1,1, fakeBankAccountRequest.copy(bankName = "HDFC")) }returns Unit

        val updatedAccount = bankAccountController.updateAccount(1,1, fakeBankAccountRequest.copy(bankName = "HDFC"))

        updatedAccount.body `should be equal to` fakeBankAccountRequest.copy(bankName = "HDFC")
    }

    @Test
    fun `should delete a bank account`(){
        every { bankAccountService.deleteAccount(1,1) }returns Unit

        bankAccountController.deleteAccount(1,1)

        verify { bankAccountService.deleteAccount(1,1) }
    }



}
private val fakeEmployee= Employee(employeeId=1,name= "Vikram",age=25,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")
private val fakeBankAccountRequest= BankAccountRequest(id=1,bankName = "ICICI", accountNumber = "2345678", ifscCode = "ICICI2345")
private val fakeBankAccount= BankAccount(id = 1, bankName="ICICI", accountNumber = "2345678",ifscCode= "ICICI2345", employee = fakeEmployee)