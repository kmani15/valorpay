package com.example.valorpay.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.valorpay.R
import com.example.valorpay.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    var mSharedPref: SharedPreferencesHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mSharedPref = SharedPreferencesHelper(this@SplashActivity)

        lifecycleScope.launch {
            delay(1500)

            if (mSharedPref!=null && mSharedPref?.getValueString(CommonUtils.isLogin)!=null &&
                    mSharedPref?.getValueString(CommonUtils.isLogin).equals(CommonUtils.True,true)){
                callMainActivity()
            }else {
                callLogin()
            }
        }

    }
}