package com.example.valorpay.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.valorpay.R
import com.example.valorpay.databinding.LayoutSettingsBinding
import com.example.valorpay.utils.CommonUtils
import com.example.valorpay.utils.SharedPreferencesHelper
import com.example.valorpay.utils.longToast
import com.example.valorpay.utils.shortToast

class SettingsActivity : AppCompatActivity() {

    lateinit var mBinding: LayoutSettingsBinding
    var mSharedPref: SharedPreferencesHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LayoutSettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mSharedPref = SharedPreferencesHelper(this@SettingsActivity)
        mBinding.imgBack.setOnClickListener { finish() }

        mBinding.save.setOnClickListener {

            val sTax = mBinding.etTax.text.toString()
            val sFee = mBinding.etFee.text.toString()

            if (sTax.trim().isEmpty() ||
                sTax.trim().toInt()>100) {
                longToast(resources.getString(R.string.tax_error))
            }else if (sFee.trim().isEmpty() ||
                sFee.trim().toInt()>100) {
                longToast(resources.getString(R.string.fee_error))
            }else {
                mSharedPref?.saveInt(CommonUtils.stateTax,sTax.toInt())
                mSharedPref?.saveInt(CommonUtils.customFee,sFee.toInt())
                shortToast(resources.getString(R.string.success))
                finish()
            }
        }

    }
}