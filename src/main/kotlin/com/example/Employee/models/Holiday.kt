package com.example.Employee.models

import java.util.Date
import javax.persistence.*


@Entity
data class Holiday(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Temporal(TemporalType.DATE)
    val date: Date,
    val holiday: String


) {


}