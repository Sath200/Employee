package com.example.Employee.controllers

import com.example.Employee.controllers.request.BankAccountRequest
import com.example.Employee.models.BankAccount
import com.example.Employee.services.BankAccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/employees")
class BankAccountController (private val bankAccountservice: BankAccountService){

    @PostMapping("/{employeeId}/bank_accounts")
    fun addAccount(@PathVariable employeeId: Int, @RequestBody bankAccountrequest: BankAccountRequest): ResponseEntity<BankAccount> {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountservice.addAccount(employeeId,bankAccountrequest))
    }

    @GetMapping("/{employeeId}/bank_accounts")
    fun getAccounts(@PathVariable employeeId: Int): ResponseEntity<List<BankAccount>> {
        return ResponseEntity.ok(bankAccountservice.getAccounts(employeeId))
    }


    @GetMapping("/{employeeId}/bank_accounts/{id}")
    fun getAccount(@PathVariable employeeId: Int, @PathVariable id: Int): ResponseEntity<BankAccount> {
        return ResponseEntity.ok(bankAccountservice.getAccount(employeeId,id))
    }

    @PutMapping("/{employeeId}/bank_accounts/{id}")
    fun updateAccount(@PathVariable employeeId: Int, @PathVariable id: Int, @RequestBody bankAccountrequest: BankAccountRequest): ResponseEntity<BankAccount> {
        bankAccountservice.updateAccount(employeeId,id,bankAccountrequest)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{employeeId}/bank_accounts/{id}")
    fun deleteAccount(@PathVariable employeeId: Int, @PathVariable id: Int):ResponseEntity<BankAccount>{
        bankAccountservice.deleteAccount(employeeId,id)
        return ResponseEntity.noContent().build()
    }

}