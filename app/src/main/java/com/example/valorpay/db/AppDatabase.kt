package com.example.valorpay.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.valorpay.dao.DatabaseDao
import com.example.valorpay.model.Product
import com.example.valorpay.model.Register
import com.example.valorpay.model.Tax

@Database(entities = [Register::class,Tax::class,Product::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun mDataBaseDao(): DatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase").build()
        }
    }

}