package com.example.Employee.models


import javax.persistence.*

@Entity
public class BankAccount(
    @Id
    private var id: Int,
    private var bankName: String,
    private var accountNumber: String,
    private var ifscCode: String,
    @ManyToOne(cascade=[CascadeType.ALL],optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="empId", referencedColumnName = "employeeId")
    private var employee: Employee


) {
    fun getId(): Int {
        return id
    }

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
        println("hello")
        this.employee=employee
    }
    fun getEmp(employee: Employee): Employee{
        return employee
    }

    /*fun from(request: Bank_accountRequest): Bank_account{
        var bank_account: Bank_account
        bank_account.setId(request.getid())
        return bank_account

    }
    */
}
@Entity
public class BankAccountRequest (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     var id:Int,
     var bankName: String,
     var accountNumber: String,
    var ifscCode: String
){


}





