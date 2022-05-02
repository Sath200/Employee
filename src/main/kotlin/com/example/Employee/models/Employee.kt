package com.example.Employee.models
import javax.persistence.*


@Entity
data class Employee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var employeeId: Int,
    private var name: String,
    private var age: Int,
    private var address: String,
    private var designation: String,
    private var department: String,
    private var location: String,
    private var aadharNumber: String,
    private var pancard: String,
    @OneToMany(cascade = [CascadeType.REMOVE],mappedBy="employee", fetch = FetchType.LAZY)
    val bankAccounts: List<BankAccount> =emptyList()

 ){
    fun getId(): Int {
        return employeeId
    }
    fun setId(id: Int) {
        this.employeeId = id
    }
    fun getName(): String {
        return name
    }
    fun setName(name: String) {
        this.name = name
    }
    fun getAge(): Int {
        return age
    }
    fun setAge(age: Int) {
        this.age = age
    }
    fun getAddress(): String{
        return address
    }
    fun setAddress(address: String){
        this.address=address
    }
    fun getDesignation() : String{
        return designation
    }
    fun setDesignation(designation: String){
        this.designation=designation
    }
    fun getDepartment(): String{
        return department
    }
    fun setDepartment(department: String){
        this.department=department
    }
    fun getLocation(): String{
        return location
    }
    fun setLocation(location: String){
        this.location=location
    }
    fun getAadhar_number(): String{
        return aadharNumber
    }
    fun setAadhar_number(aadhar_number: String){
        this.aadharNumber=aadhar_number
    }
    fun getPancard(): String{
        return pancard
    }
    fun setPancard(pancard: String){
        this.pancard=pancard
    }

}