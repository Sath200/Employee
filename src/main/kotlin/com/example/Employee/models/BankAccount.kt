package com.example.Employee.models


import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class BankAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int= 0,
    val bankName: String,
    val accountNumber: String,
    var ifscCode: String,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="empId", referencedColumnName = "employeeId")
    @JsonIgnore
    val employee: Employee
    ) {



}







