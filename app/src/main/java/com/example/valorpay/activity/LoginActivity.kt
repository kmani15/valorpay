package com.example.valorpay.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.valorpay.databinding.ActivityLoginBinding
import com.example.valorpay.factory.LoginViewModelFactory
import com.example.valorpay.repository.CommonRepository
import com.example.valorpay.utils.*
import com.example.valorpay.viewmodel.LoginViewModel

class LoginActivity : BaseActivity() {

    lateinit var mBinding: ActivityLoginBinding
    lateinit var mViewModel: LoginViewModel

    var mSharedPref: SharedPreferencesHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        val repository = CommonRepository(dataBase)
        val mFactory = LoginViewModelFactory(repository,application)
        mViewModel = ViewModelProvider(this,mFactory).get(LoginViewModel::class.java)

        mBinding.vm = mViewModel
        setContentView(mBinding.root)

        mSharedPref = SharedPreferencesHelper(this@LoginActivity)

        mBinding.txtRegister.setOnClickListener {
            callRegister()
        }

        if (::mViewModel.isInitialized) {

            mViewModel.showToast.observe(this, Observer{
                shortToast(it)
            })

            mViewModel.checkLogin.observe(this, Observer {

                if (it!=null) {
                    if (it) {
                        mSharedPref?.save(CommonUtils.isLogin,CommonUtils.True)
                        callMainActivity()
                    }else {
                        shortToast("user not registered")
                    }
                }
            })

            mViewModel.currentUser.observe(this, Observer {
                if (it!=null && it.isNotEmpty()) {
                    //After Login Success...
                    mSharedPref?.save(CommonUtils.isLogin,CommonUtils.True)

                    mSharedPref?.save(CommonUtils.name,it[0].name)
                    mSharedPref?.save(CommonUtils.mail,it[0].email)
                    mSharedPref?.save(CommonUtils.phone,it[0].phoneNumber)
                    mSharedPref?.saveLong(CommonUtils.id,it[0].id)
                    callMainActivity()
                }else {
                    shortToast("user not registered")
                }
            })

        }

    }
}