package com.example.valorpay.utils

import android.text.TextUtils
import android.util.Patterns

object Validation {

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidMobile(phone: String): Boolean {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone)
            .matches() && phone.length >= 8
    }

}