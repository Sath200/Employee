package com.example.Employee.services



import com.example.Employee.controllers.request.BankAccountRequest
import com.example.Employee.models.BankAccount
//import com.example.Employee.models.BankAccountRequest
import com.example.Employee.repositories.BankAccountRepository
//import com.example.Employee.repositories.BankAccountRequestRepository
import com.example.Employee.repositories.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
public class BankAccountService(private val employeeRepository: EmployeeRepository,
                                private val bankAccountRepository: BankAccountRepository){
    public fun addAccount(employeeId: Int, account: BankAccountRequest) {
        var employee = employeeRepository.findById(employeeId)
        if (employee.isPresent) {

            bankAccountRepository.save(
                BankAccount(
                    id = account.id,
                    bankName = account.bankName,
                    accountNumber = account.accountNumber,
                    ifscCode = account.ifscCode,
                    employee = employee.get()
                )
            )
        } else {
            throw EntityNotFoundException("emp not found")
        }
    }
    public fun getAccounts(employeeId: Int): List<BankAccount>{
        return bankAccountRepository.findAllByEmployeeEmployeeId(employeeId)
    }

    public fun getAccount(employeeId: Int,id: Int): BankAccount {
        return bankAccountRepository.findById(id).get()
    }

    public fun updateAccount(employeeId: Int,id: Int, account: BankAccountRequest){
        account.id=id
        val employee = employeeRepository.findById(employeeId)
        if (employee.isPresent) {
            bankAccountRepository.save(
                BankAccount(
                    id = account.id,
                    bankName = account.bankName,
                    accountNumber = account.accountNumber,
                    ifscCode = account.ifscCode,
                    employee = employee.get()
                )
            )
        } else {
            throw EntityNotFoundException("emp not found")
        }
    }

}





