package com.example.Employee.services

import com.example.Employee.models.Employee

import com.example.Employee.repositories.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
public class EmployeeService (private val employeeRepository: EmployeeRepository){
    fun addDetails(employee: Employee){
        employeeRepository.save(employee)
    }

    fun get(): List<Employee> {
        return employeeRepository.findAll().toList()
    }

    fun getEmployee(employeeId: Int): Employee {
        return employeeRepository.findById(employeeId).get()
    }

    fun deleteEmployee(employeeId: Int){
        employeeRepository.deleteById(employeeId)
    }

    fun updateEmployee(employeeId: Int, employee: Employee) {
        employee.employeeId=employeeId
        employeeRepository.save(employee)
    }


}

