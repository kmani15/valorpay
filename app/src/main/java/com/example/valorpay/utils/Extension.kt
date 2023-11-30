package com.example.valorpay.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.valorpay.activity.LoginActivity
import com.example.valorpay.activity.MainActivity
import com.example.valorpay.activity.RegisterActivity
import com.example.valorpay.activity.SettingsActivity

fun Activity.shortToast(msg:String) {
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}
fun Fragment.shortToast(msg:String) {
    Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
}

fun Activity.longToast(msg:String) {
    Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
}
fun Fragment.longToast(msg:String) {
    Toast.makeText(activity,msg, Toast.LENGTH_LONG).show()
}

fun Activity.callLogin() {
    val intent = Intent(this,LoginActivity::class.java)
    startActivity(intent)
    finish()
}

fun Activity.callRegister() {
    val intent = Intent(this,RegisterActivity::class.java)
    startActivity(intent)
}

fun Activity.callMainActivity() {
    val intent = Intent(this,MainActivity::class.java)
    startActivity(intent)
    finish()
}

fun Activity.callSettings() {
    val intent = Intent(this, SettingsActivity::class.java)
    startActivity(intent)
}

fun Activity.clearAllCallMainPage() {
    val i = Intent(this, LoginActivity::class.java)
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(i)
}
