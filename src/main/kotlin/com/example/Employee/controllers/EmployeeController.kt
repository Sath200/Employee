package com.example.Employee.controllers

import com.example.Employee.controllers.request.BankAccountRequest
import com.example.Employee.models.BankAccount
//import com.example.Employee.models.BankAccountRequest
import com.example.Employee.models.Employee
import com.example.Employee.services.BankAccountService
import com.example.Employee.services.EmployeeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/employees")
public class EmployeeController (private val employeeService: EmployeeService){


    @PostMapping
    fun addEmployee(@RequestBody employee: Employee ): ResponseEntity<Employee>{
        employeeService.addDetails(employee)
        return ResponseEntity.ok(employee)

    }

    @GetMapping
    fun get(): ResponseEntity<List<Employee>> {
        return ResponseEntity.ok(employeeService.get())
    }

    @GetMapping("/{employeeId}")
    fun getEmployee(@PathVariable employeeId: Int): ResponseEntity<Employee>  {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId))
    }

    @DeleteMapping("/{employeeId}")
    fun deleteEmployee(@PathVariable employeeId: Int){
        employeeService.deleteEmployee(employeeId)
    }

    @PutMapping("/{employeeId}")
    fun updateEmployee(@PathVariable employeeId: Int, @RequestBody employee: Employee): ResponseEntity<Employee> {
        employeeService.updateEmployee(employeeId,employee)
        return ResponseEntity.ok((employee))
    }

}







