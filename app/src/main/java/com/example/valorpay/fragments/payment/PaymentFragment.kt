package com.example.valorpay.fragments.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.valorpay.R
import com.example.valorpay.activity.MainActivity
import com.example.valorpay.adapter.PaymentCardAdapter
import com.example.valorpay.adapter.ViewCartAdapter
import com.example.valorpay.databinding.FragmentPaymentBinding
import com.example.valorpay.fragments.base.BaseFragment
import com.example.valorpay.model.PaymentCard
import com.example.valorpay.model.Product
import com.example.valorpay.utils.CommonUtils
import com.example.valorpay.utils.SharedPreferencesHelper
import com.example.valorpay.utils.longToast
import com.example.valorpay.utils.shortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentFragment : BaseFragment() {

    lateinit var mBinding: FragmentPaymentBinding
    var mCardList = ArrayList<PaymentCard>()
    lateinit var mAdapter: PaymentCardAdapter
    lateinit var mCartAdapter: ViewCartAdapter
    var list = ArrayList<Product>()

    //calc...
    var mNetPrice = 0.00
    var stateTaxPercent: Int = 0
    var customTaxPercent: Int = 0
    var mSharedPref: SharedPreferencesHelper?=null
    var stateTaxValue: Double = 0.00
    var customTaxValue: Double = 0.00

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentPaymentBinding.inflate(inflater,container,false)

        mSharedPref = SharedPreferencesHelper(requireActivity())
        stateTaxPercent = mSharedPref?.getIntValue(CommonUtils.stateTax)!!
        customTaxPercent = mSharedPref?.getIntValue(CommonUtils.customFee)!!


        val card1 = PaymentCard("4111111123423400","Abc","12/23")
        val card2 = PaymentCard("4111156123428900","Xyz","05/24")
        val card3 = PaymentCard("4111111111111100","Skm","08/25")

        mCardList.add(card1);mCardList.add(card2);mCardList.add(card3);
        mAdapter = PaymentCardAdapter(requireActivity(),mCardList)
        mBinding.cardRecyclerview.adapter = mAdapter

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

        mBinding.pay.setOnClickListener {

            if (mAdapter.pos>=0) {

                lifecycleScope.launch(Dispatchers.IO) {
                    dataBase.mDataBaseDao().deleteCartTable()
                }

                lifecycleScope.launch (Dispatchers.Main) {
                    try{
                        shortToast(resources.getString(R.string.success))
                        val obj = (activity as? MainActivity)
                        obj?.let {
                            it.bottomNavigationView?.selectedItemId = R.id.productFragment
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

            }else {
                longToast(resources.getString(R.string.choose_card))
            }
        }

        return mBinding.root
    }

    private fun callAdapter() {

        if (list.size>0){
            showViews()
            val currency = list[0].currency
            lifecycleScope.launch(Dispatchers.Default) {

                //Amount Calculations...
                mNetPrice = CommonUtils.amountCalc(list)

                //Tax Calculations...
                if (stateTaxPercent>0) {
                    stateTaxValue = (mNetPrice/100)*stateTaxPercent
                    Log.d("stateTaxValue",stateTaxValue.toString())
                }

                if (customTaxPercent>0) {
                    customTaxValue = (mNetPrice/100)*customTaxPercent
                    Log.d("customTaxValue",customTaxValue.toString())
                }

                val toPay = mNetPrice+stateTaxValue+customTaxValue

                lifecycleScope.launch(Dispatchers.Main) {//Update the UI so with Dispatcher.Main
                    mBinding.valNetPrice.text = currency+String.format("%.2f", mNetPrice)
                    mBinding.valStateTax.text = currency+String.format("%.2f", stateTaxValue)
                    mBinding.valCustomFee.text = currency+String.format("%.2f", customTaxValue)
                    mBinding.valToPay.text = currency+String.format("%.2f", toPay)
                }

            }

        }else{
            hideViews()
        }

        if (::mCartAdapter.isInitialized) {
            mCartAdapter.notifyDataSetChanged()
        }else {
            mCartAdapter = ViewCartAdapter(requireActivity(),list)
            mBinding.recyclerview.adapter = mCartAdapter
        }

    }

    private fun hideViews() {
        mBinding.priceCard.visibility = View.GONE
        mBinding.cardRecyclerview.visibility = View.GONE
        mBinding.pay.visibility = View.GONE

    }

    private fun showViews() {
        mBinding.priceCard.visibility = View.VISIBLE
        mBinding.cardRecyclerview.visibility = View.VISIBLE
        mBinding.pay.visibility = View.VISIBLE
    }

}