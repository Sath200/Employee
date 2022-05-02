package com.example.Employee.controllers.request

import javax.persistence.*


data class BankAccountRequest(
                              //@Id
                              //@GeneratedValue(strategy = GenerationType.IDENTITY)
                              var id:Int = 0,
                              var bankName: String,
                              var accountNumber: String,
                              var ifscCode: String)

