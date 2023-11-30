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

class LoginViewModel(private val commonRepository: CommonRepository,
                     private val application: Application): AndroidViewModel(application) {

    var reg = Register()
    val showToast = MutableLiveData<String>()
    val checkLogin = MutableLiveData<Boolean>()
    val currentUser = MutableLiveData<List<Register>>()

    fun callReg() {
        if (isValidation()){

            viewModelScope.launch {
                //Check with entered credentials...
                val listReg = commonRepository.checkLogin(reg.phoneNumber,reg.password)
                if (listReg!=null && listReg.isNotEmpty()){
                    Log.d("Login: ","yes")
                    currentUser.value = listReg
                    //checkLogin.value = true
                }else {
                    Log.d("Login: ","no")
                    checkLogin.value = false
                }
                //loginLiveData.postValue(listReg)
            }

        }
    }

    private fun isValidation(): Boolean {
        return if (!Validation.isValidMobile(reg.phoneNumber.trim())) {
            showToast.value = application.resources.getString(R.string.mobile_error)
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

}

