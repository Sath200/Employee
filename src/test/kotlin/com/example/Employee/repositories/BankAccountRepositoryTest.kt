package com.example.Employee.repositories

import com.example.Employee.models.BankAccount
import com.example.Employee.models.Employee
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class BankAccountRepositoryTest {
    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @Autowired
    private lateinit var bankAccountRepository: BankAccountRepository

    @Test
    fun `should add bank account`(){
        val savedEmployee=employeeRepository.save(fakeEmployee)
        val savedBankAccount=bankAccountRepository.save(fakeBankAccount.copy(employee = savedEmployee))

        savedEmployee `should be equal to` fakeEmployee.copy(employeeId = savedEmployee.employeeId)
        savedBankAccount.employee `should be equal to` savedEmployee
        savedBankAccount `should be equal to` fakeBankAccount.copy(id = savedBankAccount.id)
    }

    @Test
    fun `should get bank account of an employee`(){
        val savedEmployee=employeeRepository.save(fakeEmployee)
        val savedBankAccount=bankAccountRepository.save(fakeBankAccount.copy(employee = savedEmployee))
        val fetchedBankAccount=bankAccountRepository.findByEmployeeEmployeeIdAndId(savedEmployee.employeeId,savedBankAccount.id)

        fetchedBankAccount.get() `should be equal to` savedBankAccount
    }

    @Test
    fun `should update bank account`(){
        val savedEmployee=employeeRepository.save(fakeEmployee)
        val savedBankAccount=bankAccountRepository.save(fakeBankAccount.copy(employee = savedEmployee))
        bankAccountRepository.save(savedBankAccount.copy(bankName = "HDFC"))
        val fetchedBankAccount=bankAccountRepository.findById(savedBankAccount.id)

        fetchedBankAccount.get() `should be equal to` fakeBankAccount.copy(id=savedBankAccount.id,bankName = "HDFC", employee = savedEmployee)
    }

    @Test
    fun `should delete bank account`(){
        val savedEmployee=employeeRepository.save(fakeEmployee)
        val savedBankAccount=bankAccountRepository.save(fakeBankAccount.copy(employee = savedEmployee))
        bankAccountRepository.deleteById(savedBankAccount.id)
        val fetchedBankAccount=bankAccountRepository.findById(savedBankAccount.id)

        fetchedBankAccount `should be equal to` Optional.empty()
    }
}
private val fakeEmployee= Employee(employeeId=0,name= "Vikram",age=25,address="Hyd",designation="Intern",department="LPD", location = "Bangalore", aadharNumber = "5678678678", pancard = "KYP678")
private val fakeBankAccount= BankAccount(bankName="ICICI", accountNumber = "2345678",ifscCode= "ICICI2345", employee = fakeEmployee)