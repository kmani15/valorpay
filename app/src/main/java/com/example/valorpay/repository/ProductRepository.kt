package com.example.valorpay.repository

import androidx.lifecycle.MutableLiveData
import com.example.valorpay.api.APIInterface
import com.example.valorpay.api.ApiClient
import com.example.valorpay.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {

    private var apiInterface:APIInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(APIInterface::class.java)
    }

    fun getProducts():MutableLiveData<ArrayList<Product>> {

        val data = MutableLiveData<ArrayList<Product>>()

        apiInterface?.fetchAllProducts()?.enqueue(object : Callback<ArrayList<Product>> {

            override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                data.value = ArrayList<Product>()
            }

            override fun onResponse(
                call: Call<ArrayList<Product>>,
                response: Response<ArrayList<Product>>) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res!!
                }else{
                    data.value = ArrayList<Product>()
                }

            }
        })

        return data

    }

}