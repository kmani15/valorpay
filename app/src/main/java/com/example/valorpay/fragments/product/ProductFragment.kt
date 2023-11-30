package com.example.valorpay.fragments.product

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.valorpay.adapter.ProductAdapter
import com.example.valorpay.databinding.FragmentProductBinding
import com.example.valorpay.fragments.base.BaseFragment
import com.example.valorpay.model.Product
import com.example.valorpay.viewmodel.ProductViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class ProductFragment : BaseFragment(), ProductAdapter.ProductInterface {

    var productList = ArrayList<Product>()
    lateinit var adapter: ProductAdapter
    lateinit var mBinding: FragmentProductBinding
    var list = ArrayList<Product>()

    lateinit var mViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        mBinding = FragmentProductBinding.inflate(inflater, container,false)

        mViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            dataBase.mDataBaseDao().getCartItems().collect {//get cart item...
                if (it!=null && it.size>0) {
                    list = it.toCollection(ArrayList<Product>())
                }
            }
        }

        showProgressDialog()
        mViewModel.callAPI()

        mViewModel.result.observe(viewLifecycleOwner,Observer {
            dismissProgressDialog()
            if (it!=null) {
                if (it.size>0) {
                    productList = it
                    setList() //update result from API...
                } else {
                    callLocalData() //If API fails load from local json...
                }
            }
        })

        return mBinding.root
    }

    private fun callLocalData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val jsonFileString = getJsonDataFromAsset(requireActivity(), "product.json")
            jsonFileString?.let {
                Log.i("data", jsonFileString.toString())

                val gson = Gson()
                val listPersonType = object : TypeToken<ArrayList<Product>>() {}.type
                productList = gson.fromJson(jsonFileString, listPersonType)
                Log.d("List",gson.toJson(productList))

                lifecycleScope.launch(Dispatchers.Main) { setList() }
            }
        }
    }

    private fun setList() {

        if (list.size>0) {

            loopa@
            for (i in 0 until list.size) {
                loopb@
                for (j in 0 until productList.size) {
                    if (list[i].itemId == productList[j].itemId) {
                        productList[j].isCart = true
                        break@loopb
                    }
                }
            }

            callAdapter()

        }else {
            callAdapter()
        }
    }

    fun callAdapter() {
        adapter = ProductAdapter(requireActivity(),this,productList)
        mBinding.recyclerView.adapter = adapter
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    override fun selectedItem(product: Product) {

        lifecycleScope.launch(Dispatchers.IO) {

            if (product.isCart) {
                dataBase.mDataBaseDao().addToCart(product)
            }else {
                dataBase.mDataBaseDao().removeFromCart(product.itemId)
            }

        }

    }

}