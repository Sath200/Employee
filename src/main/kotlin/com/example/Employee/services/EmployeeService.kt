package com.example.Employee.services

import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.exceptions.InvalidRequestException
import com.example.Employee.models.Employee

import com.example.Employee.repositories.EmployeeRepository
import org.springframework.stereotype.Service


@Service
class EmployeeService (private val employeeRepository: EmployeeRepository){
    fun addDetails(employee: Employee): Employee{
        if(employee.age<=18){
            throw InvalidRequestException("Age should be above 18")
        }
        return employeeRepository.save(employee)
    }

    fun get(): List<Employee> {
        return employeeRepository.findAll().toList()
    }

    fun getEmployee(employeeId: Int): Employee {
        return employeeRepository.findById(employeeId)
            .orElseThrow{EntityNotFoundException("employee not found")}
    }

    fun deleteEmployee(employeeId: Int){
        val employee = employeeRepository.findById(employeeId)
        if(employee.isEmpty){
            throw EntityNotFoundException("employee not found")
        }
        employeeRepository.deleteById(employeeId)
    }

    fun updateEmployee(employeeId: Int, employee: Employee) :Employee{
        val existingEmployee = employeeRepository.findById(employeeId)
        if(existingEmployee.isEmpty){
            throw EntityNotFoundException("employee not found")
        }
        if(employee.age<=18){
            throw InvalidRequestException("Age should be above 18")
        }
        employee.employeeId = employeeId
        return employeeRepository.save(employee)
    }


}

