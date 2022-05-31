package com.example.Employee.services



import com.example.Employee.controllers.request.BankAccountRequest
import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.models.BankAccount
//import com.example.Employee.models.BankAccountRequest
import com.example.Employee.repositories.BankAccountRepository
//import com.example.Employee.repositories.BankAccountRequestRepository
import com.example.Employee.repositories.EmployeeRepository
import org.springframework.stereotype.Service



@Service
class BankAccountService(private val employeeRepository: EmployeeRepository,
                                private val bankAccountRepository: BankAccountRepository){
    fun addAccount(employeeId: Int, account: BankAccountRequest): BankAccount {
        val employee = employeeRepository.findById(employeeId)
        if (employee.isEmpty) {
            throw EntityNotFoundException("employee not found")
        }
        return bankAccountRepository.save(
            BankAccount(
                id = account.id,
                bankName = account.bankName,
                accountNumber = account.accountNumber,
                ifscCode = account.ifscCode,
                employee = employee.get()
            )
        )
    }
    fun getAccounts(employeeId: Int): List<BankAccount>{
        val employee = employeeRepository.findById(employeeId)
        if(employee.isEmpty){
            throw EntityNotFoundException("employee not found")
        }
        return bankAccountRepository.findAllByEmployeeEmployeeId(employeeId).toList()

    }

    fun getAccount(employeeId: Int,id: Int): BankAccount {
        val employee = employeeRepository.findById(employeeId)
        if(employee.isEmpty){
            throw EntityNotFoundException("employee not found")
        }
        return bankAccountRepository.findByEmployeeEmployeeIdAndId(employeeId,id)
            .orElseThrow { EntityNotFoundException("bank account not found") }

    }

    fun updateAccount(employeeId: Int,id: Int, account: BankAccountRequest): BankAccount{
        account.id=id
        val employee = employeeRepository.findById(employeeId)
        val bankAccount=bankAccountRepository.findByEmployeeEmployeeIdAndId(employeeId, id)
        if(employee.isEmpty){
            throw EntityNotFoundException("employee not found")
        }
        if(bankAccount.isEmpty){
            throw EntityNotFoundException("bank account not found")
        }
        return bankAccountRepository.save(
            BankAccount(
                id = account.id,
                bankName = account.bankName,
                accountNumber = account.accountNumber,
                ifscCode = account.ifscCode,
                employee = employee.get()
            )
        )
    }

    fun deleteAccount(employeeId: Int,id: Int){
        if(!employeeRepository.existsById(employeeId)){
            throw EntityNotFoundException("employee not found")
        }
        if(!bankAccountRepository.existsByEmployeeEmployeeIdAndId(employeeId, id)){
            throw EntityNotFoundException("bank account not found")
        }
        bankAccountRepository.deleteById(id)

    }

}





