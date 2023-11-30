package com.example.valorpay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorpay.model.Product

import com.example.valorpay.repository.ProductRepository

class ProductViewModel(): ViewModel() {

    private var productRepository = ProductRepository()
    var result = MutableLiveData<ArrayList<Product>>()

    fun callAPI() {
        result = productRepository.getProducts()
    }


}

