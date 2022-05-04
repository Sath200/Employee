package com.example.Employee.services

import com.example.Employee.exceptions.EntityNotFoundException
import com.example.Employee.exceptions.InvalidRequestException
import com.example.Employee.models.Employee

import com.example.Employee.repositories.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
public class EmployeeService (private val employeeRepository: EmployeeRepository){
    fun addDetails(employee: Employee){
        if(employee.age<18){
            throw InvalidRequestException("Age should be above 18")
        }
        else {
            employeeRepository.save(employee)
        }
    }

    fun get(): List<Employee> {
        return employeeRepository.findAll().toList()
    }

    fun getEmployee(employeeId: Int): Employee {
        return employeeRepository.findById(employeeId).orElseThrow{EntityNotFoundException("employee not found")}
    }

    fun deleteEmployee(employeeId: Int){
        val employee = employeeRepository.findById(employeeId)
        if(employee.isPresent) {
            employeeRepository.deleteById(employeeId)
        } else{
            throw EntityNotFoundException("employee not found")
        }
    }

    fun updateEmployee(employeeId: Int, employee: Employee) {
        val employeeExists = employeeRepository.findById(employeeId)
        if(employeeExists.isPresent) {
            employee.employeeId = employeeId
            if(employee.age>18) {
                employeeRepository.save(employee)
            }
            else{
                throw InvalidRequestException("Age should be above 18")
            }
        } else{
            throw EntityNotFoundException("employee not found")
        }
    }


}

