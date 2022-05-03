package com.example.Employee.models
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


@Entity
data class Employee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var employeeId: Int,
    val name: String,
    val age: Int,
    val address: String,
    val designation: String,
    val department: String,
    val location: String,
    val aadharNumber: String,
    val pancard: String,
    @OneToMany(cascade = [CascadeType.REMOVE],mappedBy="employee", fetch = FetchType.LAZY)
    @JsonIgnore
    val bankAccounts: List<BankAccount> =emptyList()

 ){



}