package com.example.Employee.repositories

import com.example.Employee.models.CompanyNews
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
public interface CompanyNewsRepository : CrudRepository<CompanyNews,Int> {
}