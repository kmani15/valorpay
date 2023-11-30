package com.example.valorpay.fragments.base

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import com.example.valorpay.db.AppDatabase

open class BaseFragment : Fragment() {

    public val dataBase by lazy { AppDatabase.getDatabase(requireActivity()) }
    var mProgressDialog: ProgressDialog? = null

    fun showProgressDialog() {
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog!!.setMessage("Loading...")
        mProgressDialog!!.show()
    }

    fun dismissProgressDialog() {
        mProgressDialog?.dismiss()
    }

}