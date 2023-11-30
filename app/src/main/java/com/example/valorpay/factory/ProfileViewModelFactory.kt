package com.example.valorpay.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorpay.repository.CommonRepository
import com.example.valorpay.viewmodel.ProfileViewModel

class ProfileViewModelFactory(private val repository: CommonRepository,
                              private val context:Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository,context) as T
    }

}
