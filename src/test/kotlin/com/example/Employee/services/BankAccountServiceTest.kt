package com.example.Employee.services

import com.example.Employee.controllers.request.BankAccountRequest
import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.models.BankAccount
import com.example.Employee.models.Employee
import com.example.Employee.repositories.BankAccountRepository
import com.example.Employee.repositories.EmployeeRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.*
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow


internal class BankAccountServiceTest {
    private val employeeRepository= mockk<EmployeeRepository>()
    private val bankAccountRepository=mockk<BankAccountRepository>()
    private val bankAccountService=BankAccountService(employeeRepository,bankAccountRepository)

    @Test
    fun `should create bank account for employee`(){
        every { employeeRepository.findById(fakeEmployee.employeeId) } returns Optional.of(fakeEmployee)
        every { bankAccountRepository.save(fakeBankAccount) } returns fakeBankAccount

        bankAccountService.addAccount(fakeEmployee.employeeId, fakeBankAccountRequest)

        verify { bankAccountRepository.save(fakeBankAccount) }
    }

    @Test
    fun `should give an error message while trying to create bankAccount for non existent employee`(){
        every { employeeRepository.findById(fakeEmployee.employeeId) }returns Optional.empty()

        invoking {bankAccountService.addAccount(fakeEmployee.employeeId, fakeBankAccountRequest)} shouldThrow EntityNotFoundException("employee not found")
    }

    @Test
    fun `should get all bank accounts of an employee`(){
        every { employeeRepository.findById(fakeEmployee.employeeId) } returns Optional.of(fakeEmployee)
        every { bankAccountRepository.findAllByEmployeeEmployeeId(fakeEmployee.employeeId) }returns listOf(
            fakeBankAccount)

        val fetchedBankAccounts= bankAccountService.getAccounts(fakeEmployee.employeeId)

        fetchedBankAccounts `should be equal to` listOf(fakeBankAccount)
        verify { bankAccountRepository.findAllByEmployeeEmployeeId(fakeEmployee.employeeId) }
    }

    @Test
    fun `should give an error message while trying to get bank accounts of non existent employee`(){
        every { employeeRepository.findById(fakeEmployee.employeeId) } returns Optional.empty()

        invoking { bankAccountService.getAccounts(fakeEmployee.employeeId) } shouldThrow EntityNotFoundException("employee not found")
    }

    @Test
    fun `should get a particular bank account of an employee`(){
        every { employeeRepository.findById(1) } returns Optional.of(fakeEmployee.copy(employeeId = 1))
        every {bankAccountRepository.findByEmployeeEmployeeIdAndId(1, fakeBankAccount.id)} returns Optional.of(
            fakeBankAccount)

        val fetchedBankAccount = bankAccountService.getAccount(1, fakeBankAccount.id)

        fetchedBankAccount `should be equal to` fakeBankAccount
        verify { bankAccountRepository.findByEmployeeEmployeeIdAndId(1, fakeBankAccount.id) }
    }

    @Test
    fun `should give an error message while trying to get bank account of non existent employee`(){
        every {employeeRepository.findById(fakeEmployee.employeeId)} returns Optional.empty()

        invoking { bankAccountService.getAccount(fakeEmployee.employeeId, fakeBankAccount.id) } shouldThrow EntityNotFoundException("employee not found")
    }

    @Test
    fun `should give an error message while trying to get non existent bank account of an employee`(){
        every { employeeRepository.findById(fakeEmployee.employeeId) } returns Optional.of(fakeEmployee.copy(employeeId = 1))
        every {bankAccountRepository.findByEmployeeEmployeeIdAndId(fakeEmployee.employeeId, fakeBankAccount.id)} returns Optional.empty()

        invoking {bankAccountService.getAccount(fakeEmployee.employeeId, fakeBankAccount.id)} shouldThrow EntityNotFoundException("bank account not found")
    }

    @Test
    fun `should update a bank account`(){
        every { employeeRepository.findById(fakeEmployee.employeeId) } returns Optional.of(fakeEmployee)
        every { bankAccountRepository.findByEmployeeEmployeeIdAndId(fakeEmployee.employeeId, fakeBankAccount.id) }returns Optional.of(
            fakeBankAccount)
        every { bankAccountRepository.save(fakeBankAccount.copy(bankName = "HDFC")) }returns fakeBankAccount.copy(bankName = "HDFC")

        bankAccountService.updateAccount(fakeEmployee.employeeId, fakeBankAccount.id, fakeBankAccountRequest.copy(bankName = "HDFC"))

        verify { bankAccountRepository.save(fakeBankAccount.copy(bankName = "HDFC")) }
    }

    @Test
    fun `should give an error message while trying to update bank account of non existent employee`(){
        every {employeeRepository.findById(fakeEmployee.employeeId)} returns Optional.empty()
        every { bankAccountRepository.findByEmployeeEmployeeIdAndId(fakeEmployee.employeeId, fakeBankAccount.id) } returns Optional.of(
            fakeBankAccount)
        invoking { bankAccountService.updateAccount(fakeEmployee.employeeId, fakeBankAccount.id, fakeBankAccountRequest)}shouldThrow EntityNotFoundException("employee not found")
    }

    @Test
    fun `should give an error message while trying to update non existent bank account`(){
        every {employeeRepository.findById(fakeEmployee.employeeId)} returns Optional.of(fakeEmployee)
        every { bankAccountRepository.findByEmployeeEmployeeIdAndId(fakeEmployee.employeeId, fakeBankAccount.id) } returns Optional.empty()

        invoking { bankAccountService.updateAccount(fakeEmployee.employeeId, fakeBankAccount.id, fakeBankAccountRequest) } shouldThrow EntityNotFoundException("bank account not found")

    }

    @Test
    fun `should delete a bank account`(){
        every {employeeRepository.existsById(fakeEmployee.employeeId)} returns true
        every { bankAccountRepository.existsByEmployeeEmployeeIdAndId(fakeEmployee.employeeId, fakeBankAccount.id) }returns true
        every {bankAccountRepository.deleteById(fakeBankAccount.id)}returns Unit

        bankAccountService.deleteAccount(fakeEmployee.employeeId, fakeBankAccount.id)

        verify { bankAccountRepository.deleteById(fakeBankAccount.id) }
    }

    @Test
    fun `should give an error message while trying to delete bank account for non existent employee`(){
        every {employeeRepository.existsById(fakeEmployee.employeeId)} returns false
        //every { bankAccountRepository.existsByEmployeeEmployeeIdAndId(fakeEmployee.employeeId, fakeBankAccount.id) }returns true

        invoking { bankAccountService.deleteAccount(fakeEmployee.employeeId, fakeBankAccount.id) }shouldThrow EntityNotFoundException("employee not found")
    }

    @Test
    fun `should give an error message while trying to delete a non existent bank account`(){
        every {employeeRepository.existsById(fakeEmployee.employeeId)} returns true
        every { bankAccountRepository.existsByEmployeeEmployeeIdAndId(fakeEmployee.employeeId, fakeBankAccount.id) }returns false

        invoking { bankAccountService.deleteAccount(fakeEmployee.employeeId, fakeBankAccount.id) }shouldThrow EntityNotFoundException("bank account not found")

    }
}
private val fakeEmployee= Employee(employeeId=0,name= "Vikram",age=25,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")
private val fakeBankAccountRequest= BankAccountRequest(bankName = "ICICI", accountNumber = "2345678", ifscCode = "ICICI2345")
private val fakeBankAccount= BankAccount(bankName="ICICI", accountNumber = "2345678",ifscCode= "ICICI2345", employee = fakeEmployee)