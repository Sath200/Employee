package com.example.Employee.repositories


import com.example.Employee.models.BankAccount
//import com.example.Employee.models.BankAccountRequest
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
public interface BankAccountRepository: CrudRepository<BankAccount, Int> {

        fun findAllByEmployeeEmployeeId(employeeId: Int):List<BankAccount>
        fun findByEmployeeEmployeeIdAndId(employeeId: Int, id: Int):Optional<BankAccount>
        fun existsByEmployeeEmployeeIdAndId(employeeId: Int,id: Int): Boolean

}

