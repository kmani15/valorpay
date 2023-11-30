package com.example.valorpay.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.valorpay.R
import com.example.valorpay.activity.MainActivity
import com.example.valorpay.adapter.CartAdapter
import com.example.valorpay.databinding.FragmentCartBinding
import com.example.valorpay.fragments.base.BaseFragment
import com.example.valorpay.model.Product
import com.example.valorpay.utils.CommonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartFragment: BaseFragment(), CartAdapter.CartInterface {

    lateinit var mBinding: FragmentCartBinding
    var list = ArrayList<Product>()
    lateinit var mAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        mBinding = FragmentCartBinding.inflate(inflater,container,false)

        lifecycleScope.launch(Dispatchers.IO) {
            dataBase.mDataBaseDao().getCartItems().collect {
                if (it!=null && it.size>0) {
                    list = it.toCollection(ArrayList<Product>())
                    lifecycleScope.launch(Dispatchers.Main) { callAdapter() }
                }else {
                    lifecycleScope.launch(Dispatchers.Main) { hideViews() }
                }
            }
        }

        mBinding.btnCheckout.setOnClickListener {
            val obj = (activity as? MainActivity)
            obj?.let {
                it.bottomNavigationView?.selectedItemId = R.id.paymentFragment
            }
        }

        return mBinding.root
    }

    private fun hideViews() {
        mBinding.totalAmt.visibility = View.INVISIBLE
        mBinding.btnCheckout.visibility = View.INVISIBLE
        mBinding.titleTotalAmount.visibility = View.INVISIBLE
        mBinding.cartRecyclerview.visibility = View.INVISIBLE
    }

    private fun showViews() {
        mBinding.totalAmt.visibility = View.VISIBLE
        mBinding.btnCheckout.visibility = View.VISIBLE
        mBinding.titleTotalAmount.visibility = View.VISIBLE
        mBinding.cartRecyclerview.visibility = View.VISIBLE
    }

    private fun callAdapter() {

        if (list.size>0){
            showViews()
            val currency = list[0].currency
            mBinding.totalAmt.text = currency + String.format("%.2f", CommonUtils.amountCalc(list))
        }else{
            hideViews()
        }

        if (::mAdapter.isInitialized) {
            mAdapter.notifyDataSetChanged()
        }else {
            mAdapter = CartAdapter(requireActivity(),this,list)
            mBinding.cartRecyclerview.adapter = mAdapter
        }

    }

    override fun removeFromCart(cart: Product,position: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val isDel = dataBase.mDataBaseDao().removeFromCart(cart.itemId)
        }
    }

    override fun incDecFunction(list: Product, position: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            //dataBase.mDataBaseDao().updateCart(list,list.itemId)
            dataBase.mDataBaseDao().updateCarts(list.itemCount,list.itemTotalPrice,list.itemId)
        }
    }

}