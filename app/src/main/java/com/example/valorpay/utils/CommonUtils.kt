package com.example.valorpay.utils

import com.example.valorpay.model.Product

object CommonUtils {

    const val isLogin = "isLogin"
    const val True = "True"
    const val False = "False"
    const val stateTax = "stateTax"
    const val customFee = "customFee"
    const val id = "id"
    const val name = "name"
    const val mail = "mail"
    const val phone = "phone"

    fun amountCalc(list: ArrayList<Product>):Double {
        try {
            //lifecycleScope.launch(Dispatchers.Default) {
            if (list.size>0) {
                var total = 0.0
                for (item in list) {
                    total += item.itemTotalPrice
                }
                return total
            }else {
                return 0.00
            }
            //}
        }catch (e:Exception){
            e.printStackTrace()
            return 0.00
        }
    }

}