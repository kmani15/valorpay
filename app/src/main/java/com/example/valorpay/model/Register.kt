package com.example.valorpay.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "user_table")
data class Register(

    @ColumnInfo(name ="name")
    var name:String="",
    @ColumnInfo(name = "email")
    var email:String="",
    @ColumnInfo(name = "phoneNumber")
    var phoneNumber:String="",
    @ColumnInfo(name = "dob")
    var dob:String="",
    @ColumnInfo(name = "password")
    var password:String="",
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

)
