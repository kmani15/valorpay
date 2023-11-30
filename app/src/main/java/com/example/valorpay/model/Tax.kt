package com.example.valorpay.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tax_table")
data class Tax(

    @ColumnInfo(name ="stateTax")
    var stateTax:String="",
    @ColumnInfo(name = "customFee")
    var customFee:String="",
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

)
