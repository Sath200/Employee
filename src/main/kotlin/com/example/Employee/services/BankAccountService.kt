package com.example.Employee.services



import com.example.Employee.controllers.request.BankAccountRequest
import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.models.BankAccount
//import com.example.Employee.models.BankAccountRequest
import com.example.Employee.repositories.BankAccountRepository
//import com.example.Employee.repositories.BankAccountRequestRepository
import com.example.Employee.repositories.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
public class BankAccountService(private val employeeRepository: EmployeeRepository,
                                private val bankAccountRepository: BankAccountRepository){
    public fun addAccount(employeeId: Int, account: BankAccountRequest) {
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
            throw EntityNotFoundException("employee not found")
        }
    }
    public fun getAccounts(employeeId: Int): List<BankAccount>{
        val employee = employeeRepository.findById(employeeId)
        if(employee.isPresent) {
            return bankAccountRepository.findAllByEmployeeEmployeeId(employeeId).toList()
        } else{
            throw EntityNotFoundException("employee not found")
        }
    }

    public fun getAccount(employeeId: Int,id: Int): BankAccount {
        //return bankAccountRepository.findById(id).get()
        val employee = employeeRepository.findById(employeeId)
        if(employee.isPresent) {
            return bankAccountRepository.findById(id)
                .orElseThrow { EntityNotFoundException("bank account not found") }
        } else{
            throw EntityNotFoundException("employee not found")
        }
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
            throw EntityNotFoundException("employee not found")
        }
    }

}





