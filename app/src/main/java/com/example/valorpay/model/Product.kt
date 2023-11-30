package com.example.valorpay.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name ="name")
    var name:String="",
    @ColumnInfo(name = "price")
    var price:String="",
    @ColumnInfo(name = "currency")
    var currency:String="",
    @ColumnInfo(name = "itemCount")
    var itemCount:Int=0,
    @ColumnInfo(name = "itemTotalPrice")
    var itemTotalPrice:Double=0.0,
    @ColumnInfo(name = "imagesrc")
    var imagesrc:String="",
    @ColumnInfo(name = "itemId")
    var itemId: Int = 0,

    var isCart: Boolean
)