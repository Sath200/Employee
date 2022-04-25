package com.example.Employee.models

import java.util.Date
import javax.persistence.*


@Entity
public class Holidays(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int,

    @Temporal(TemporalType.DATE)
    private var date: Date,
    private var holiday: String


) {
    fun getId():Int{
        return this.id
    }
    fun setId(id: Int){
        this.id= id
    }
    fun getDate(): Date{
        return this.date
    }
    fun setDate(date: Date){
        this.date=date
    }
    fun getHoliday(): String{
        return this.holiday
    }

}