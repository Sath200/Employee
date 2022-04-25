package com.example.Employee.repositories

import com.example.Employee.models.Employee
import org.apache.tomcat.jni.Address
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
public interface EmployeeRepository: CrudRepository<Employee, Int >{


}