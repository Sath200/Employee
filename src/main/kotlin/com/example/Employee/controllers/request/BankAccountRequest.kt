package com.example.Employee.controllers.request

import javax.persistence.*


data class BankAccountRequest(
                              //@Id
                              //@GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int = 0,
    val bankName: String,
    val accountNumber: String,
    val ifscCode: String)

