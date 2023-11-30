package com.example.valorpay.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorpay.repository.CommonRepository
import com.example.valorpay.viewmodel.LoginViewModel

class LoginViewModelFactory(private val repository: CommonRepository, private val context:Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository,context) as T
    }

}
