package com.example.Employee.services



import com.example.Employee.models.BankAccount
import com.example.Employee.models.BankAccountRequest
import com.example.Employee.repositories.BankAccountRepository
import com.example.Employee.repositories.BankAccountRequestRepository
import com.example.Employee.repositories.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
public class BankAccountService(private val employeeRepository: EmployeeRepository,
                                private val bankAccountRepository: BankAccountRepository,
                                private val bankAccountRequestRepository: BankAccountRequestRepository) {

    public fun addAccount(employeeId: Int, account: BankAccountRequest) {
        var x = employeeRepository.findById(employeeId)
        bankAccountRequestRepository.save(account)
        if (x.isPresent) {
            var a = x.get()
            bankAccountRepository.save(
                BankAccount(
                    id = account.id,
                    bankName = account.bankName,
                    accountNumber = account.accountNumber,
                    ifscCode = account.ifscCode,
                    employee = a
                )
            )

        } else {
            throw EntityNotFoundException("emp not found")
        }
        //bankAccountrepository.save(Bank_account(id = account.id, bank_name = account.bank_name, account_number = account.account_number, IFSC_Code = account.IFSC_Code, employee = ))
    }
    public fun getAccounts(employeeId: Int): List<BankAccount>{
        return bankAccountRepository.findAllByEmployeeEmployeeId(employeeId)
    }

    public fun getAccount(employeeId: Int,id: Int): Optional<BankAccount> {
        return bankAccountRepository.findByEmployeeEmployeeIdAndId(employeeId,id)
    }

    public fun updateAccount(employeeId: Int,id: Int, account: BankAccountRequest){
        account.id=id
        var x = employeeRepository.findById(employeeId)
        bankAccountRequestRepository.save(account)
        if (x.isPresent) {
            println("exists")
            var a = x.get()
            bankAccountRepository.save(
                BankAccount(
                    id = account.id,
                    bankName = account.bankName,
                    accountNumber = account.accountNumber,
                    ifscCode = account.ifscCode,
                    employee = a
                )
            )

        } else {
            throw EntityNotFoundException("emp not found")
        }
    }

}



