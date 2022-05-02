package com.example.Employee.models


import javax.persistence.*

@Entity
data class BankAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int= 0,
    private var bankName: String,
    private var accountNumber: String,
    private var ifscCode: String,
    @ManyToOne(cascade=[CascadeType.ALL],optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="empId", referencedColumnName = "employeeId")
    private var employee: Employee


) {


    fun getBankName(): String {
        return bankName
    }
    fun setBankName(bank_name: String) {
        this.bankName = bank_name
    }
    fun getAccountNumber(): String{
        return accountNumber
    }
    fun setAccountNumber(account_number: String){
        this.accountNumber=account_number
    }
    fun getIFSCCode(): String{
        return ifscCode
    }
    fun setIFSCCode(IFSC_Code: String){
        this.ifscCode= IFSC_Code
    }

    fun setEmp(employee: Employee){
        this.employee=employee
    }
    fun getEmp(employee: Employee): Employee{
        return employee
    }


}







