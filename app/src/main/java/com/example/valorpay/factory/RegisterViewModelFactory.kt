package com.example.valorpay.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorpay.repository.CommonRepository
import com.example.valorpay.viewmodel.RegisterViewModel

class RegisterViewModelFactory(private val repository: CommonRepository,private val context:Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository,context) as T
    }

}
