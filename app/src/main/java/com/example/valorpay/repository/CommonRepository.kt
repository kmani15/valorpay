package com.example.valorpay.repository

import com.example.valorpay.db.AppDatabase
import com.example.valorpay.model.Register
import com.example.valorpay.model.Tax
import kotlinx.coroutines.delay

class CommonRepository(private val db: AppDatabase) {

    suspend fun insert(regUser:Register):Long {
        return db.mDataBaseDao().insert(regUser)
    }

    suspend fun checkLogin(phoneNumber: String, password: String): List<Register> {
        return db.mDataBaseDao().readUser(phoneNumber,password)
    }

    suspend fun insertOrUpdate(model: Tax): Long {

        db.mDataBaseDao().deleteTable()
        delay(100)
        return db.mDataBaseDao().insertTax(model)
    }

    suspend fun getProfileData(id: Long): List<Register> {
        return db.mDataBaseDao().getUser(id)
    }

    suspend fun updateProfileData(id: Long,name:String,phone:String,
    mail:String,dob:String): Int {
        return db.mDataBaseDao().updateUserInfo(id,name,phone,mail,dob)
    }

}