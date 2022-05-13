package com.example.Employee.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class CompanyNews (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    val news: String
){

}