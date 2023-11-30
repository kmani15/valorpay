package com.example.valorpay.activity

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.valorpay.R
import com.example.valorpay.databinding.ActivityRegisterBinding
import com.example.valorpay.repository.CommonRepository
import com.example.valorpay.utils.shortToast
import com.example.valorpay.viewmodel.RegisterViewModel
import com.example.valorpay.factory.RegisterViewModelFactory
import java.util.Calendar

class RegisterActivity : BaseActivity() {

    lateinit var mViewModel: RegisterViewModel
    lateinit var mBinding: ActivityRegisterBinding
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        mContext = this@RegisterActivity
        val repository = CommonRepository(dataBase)
        val mFactory = RegisterViewModelFactory(repository,application)
        mViewModel = ViewModelProvider(this,mFactory).get(RegisterViewModel::class.java)

        mBinding.vm = mViewModel
        setContentView(mBinding.root)

        mBinding.imgBack.setOnClickListener { finish() }

        if (::mViewModel.isInitialized) {

            mViewModel.showToast.observe(this, Observer{
                shortToast(it)
            })

            mViewModel.isRegister.observe(this, Observer {
                if (it!=null) {
                    if (it) {
                        shortToast(resources.getString(R.string.success))
                        finish()
                    }else {
                        shortToast(resources.getString(R.string.failure))
                    }
                }
            })

        }

        mBinding.layoutDob.setOnClickListener { callDatePicker() }
        mBinding.etDob.setOnClickListener { callDatePicker() }

    }

    private fun callDatePicker() {

        val calendar = Calendar.getInstance()
        val mYear: Int = calendar.get(Calendar.YEAR) // current year
        val mMonth: Int = calendar.get(Calendar.MONTH) // current month
        val mDay: Int = calendar.get(Calendar.DAY_OF_MONTH) // current day

        val dialog = DatePickerDialog(this, { _, year, month, day_of_month ->
            val cMonth = month + 1
            val date = "$day_of_month/$cMonth/$year"
            Log.d("date",date)
            mViewModel.setDob(date)
            mBinding.etDob.setText(date)

        }, mYear, mMonth, mDay)

        dialog.datePicker.maxDate = calendar.timeInMillis
        dialog.datePicker.fitsSystemWindows = true
        dialog.show()

    }

}