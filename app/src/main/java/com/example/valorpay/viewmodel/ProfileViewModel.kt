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

class ProfileViewModel(private val cRepository: CommonRepository,
                       private val application: Application): AndroidViewModel(application) {

    var mReg = Register()
    val showToast = MutableLiveData<String>()
    val isUpdated = MutableLiveData<Boolean>()
    var reg = MutableLiveData<Register>()

    var userId: Long = 0

    fun callReg() {
        if (isValidation()){
            viewModelScope.launch {

                if (userId>0){

                    val result = cRepository.updateProfileData(userId,mReg.name, mReg.phoneNumber,mReg.email,mReg.dob)
                    Log.d("result",result.toString())

                    isUpdated.value = result>0

                }

            }
        }
    }

    private fun isValidation(): Boolean {
        return if (mReg.name.trim().isEmpty()) {
            showToast.value = application.resources.getString(R.string.name_error)
            false
        }else if (!Validation.isValidEmail(mReg.email.trim())) {
            showToast.value = application.resources.getString(R.string.email_error)
            false
        }else if (!Validation.isValidMobile(mReg.phoneNumber.trim())) {
            showToast.value = application.resources.getString(R.string.mobile_error)
            false
        }else if (mReg.dob.trim().isEmpty()) {
            showToast.value = application.resources.getString(R.string.dob_error)
            false
        }else {
            true
        }
    }

    fun setDob(date: String) {
        Log.d("DOB",date)
        reg.value?.dob = date
        mReg.dob = date
    }

    fun getProfileData(id: Long) {
        userId = id
        viewModelScope.launch {
            val result = cRepository.getProfileData(id)
            Log.d("Register",result.toString())

            if (result.isNotEmpty()) {
                mReg.name = result[0].name
                mReg.email = result[0].email
                mReg.phoneNumber = result[0].phoneNumber
                mReg.id = result[0].id
                mReg.dob = result[0].dob

                reg.value = mReg

                Log.d("Name" ,reg.value!!.name.toString())
                Log.d("Name result" ,result[0].name)
            }

        }
    }

}

