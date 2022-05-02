package com.example.Employee.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class CompanyNews (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int,
    private var news: String
){
    fun getId():Int{
        return this.id
    }
    fun getNews(): String{
        return this.news
    }
    fun setid(id:Int){
        this.id=id
    }
    fun setNews(news: String){
        this.news=news
    }
}