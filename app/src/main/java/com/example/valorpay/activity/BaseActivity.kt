package com.example.valorpay.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.valorpay.R
import com.example.valorpay.db.AppDatabase

abstract class BaseActivity : AppCompatActivity() {

    public val dataBase by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}