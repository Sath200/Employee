package com.example.Employee.repositories

import com.example.Employee.models.Holiday
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HolidaysRepository : CrudRepository<Holiday,Int> {
}