package com.example.valorpay.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.valorpay.R
import com.example.valorpay.model.Register
import com.example.valorpay.repository.CommonRepository
import com.example.valorpay.utils.Validation
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerRepository: CommonRepository,
                        private val application: Application): AndroidViewModel(application) {

    var reg = Register()
    val showToast = MutableLiveData<String>()
    val isRegister = MutableLiveData<Boolean>()

    fun callReg() {
        if (isValidation()){

            viewModelScope.launch {
                val result = registerRepository.insert(reg)
                Log.d("Register",result.toString())

                //check is register success or not...
                isRegister.value = result>0

            }

        }
    }

    private fun isValidation(): Boolean {
        return if (reg.name.trim().isEmpty()) {
            showToast.value = application.resources.getString(R.string.name_error)
            false
        }else if (!Validation.isValidEmail(reg.email.trim())) {
            showToast.value = application.resources.getString(R.string.email_error)
            false
        }else if (!Validation.isValidMobile(reg.phoneNumber.trim())) {
            showToast.value = application.resources.getString(R.string.mobile_error)
            false
        }else if (reg.dob.trim().isEmpty()) {
            showToast.value = application.resources.getString(R.string.dob_error)
            false
        }else if (reg.password.trim().isEmpty()) {
            showToast.value = application.resources.getString(R.string.password_error)
            false
        }else if (reg.password.trim().length<8) {
            showToast.value = application.resources.getString(R.string.password_length_error)
            false
        }else {
            true
        }
    }

    fun setDob(date: String) {
        Log.d("DOB",date)
        reg.dob = date
    }

}

