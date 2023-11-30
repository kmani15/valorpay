package com.example.valorpay.api

import com.example.valorpay.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {

    @GET("v3/9f1f9a33-4cae-49cc-8dc7-264e3b6aaf1d")
    fun fetchAllProducts(): Call<ArrayList<Product>>

}