package com.example.valorpay.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.valorpay.model.Product
import com.example.valorpay.model.Register
import com.example.valorpay.model.Tax
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userReg:Register): Long

    @Query("SELECT * FROM user_table WHERE phoneNumber LIKE :phone AND password LIKE :password")
    suspend fun readUser(phone: String, password: String): List<Register>

    @Insert
    suspend fun insertTax(tax: Tax): Long

    @Query("DELETE FROM tax_table")
    suspend fun deleteTable()

    @Insert
    suspend fun addToCart(product: Product)

    @Query("DELETE FROM cart_table WHERE itemId LIKE :product")
    suspend fun removeFromCart(product: Int): Int

    @Query("SELECT * FROM cart_table")
    fun getCartItems(): Flow<List<Product>>

    @Query("SELECT * FROM tax_table")
    fun getTax(): Flow<List<Tax>>

    @Query("UPDATE cart_table SET itemCount = :count, itemTotalPrice = :total  WHERE itemId LIKE :id")
    suspend fun updateCarts(count: Int,total: Double, id: Int)

    @Query("DELETE FROM cart_table")
    suspend fun deleteCartTable()

    @Query("SELECT * FROM user_table WHERE id LIKE :uId")
    suspend fun getUser(uId: Long): List<Register>

    @Query("UPDATE user_table SET name = :sName, email = :sMail, phoneNumber = :sPhone, dob = :sDob WHERE id LIKE :id")
    suspend fun updateUserInfo(id: Long,sName:String,sPhone:String,
                               sMail:String,sDob:String): Int

}