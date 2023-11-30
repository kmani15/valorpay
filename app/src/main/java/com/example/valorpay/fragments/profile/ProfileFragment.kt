package com.example.valorpay.fragments.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.valorpay.R
import com.example.valorpay.activity.MainActivity
import com.example.valorpay.databinding.FragmentProfileBinding
import com.example.valorpay.factory.ProfileViewModelFactory
import com.example.valorpay.fragments.base.BaseFragment
import com.example.valorpay.repository.CommonRepository
import com.example.valorpay.utils.CommonUtils
import com.example.valorpay.utils.SharedPreferencesHelper
import com.example.valorpay.utils.shortToast
import com.example.valorpay.viewmodel.ProfileViewModel
import java.util.Calendar

class ProfileFragment : BaseFragment() {

    lateinit var mViewModel: ProfileViewModel
    lateinit var mBinding: FragmentProfileBinding
    var mSharedPref: SharedPreferencesHelper?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentProfileBinding.inflate(inflater,container,false)

        mSharedPref = SharedPreferencesHelper(requireActivity())
        val id = mSharedPref?.getLongValue(CommonUtils.id)!!

        val repository = CommonRepository(dataBase)
        val mFactory = ProfileViewModelFactory(repository,requireActivity().application)
        mViewModel = ViewModelProvider(requireActivity(),mFactory).get(ProfileViewModel::class.java)
        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this
        mViewModel.getProfileData(id)

        mBinding.etDob.setOnClickListener { callDatePicker() }

        mViewModel.showToast.observe(viewLifecycleOwner, Observer {
            shortToast(it)
        })

        mViewModel.isUpdated.observe(viewLifecycleOwner, Observer {
            if (it!=null) {
                if (it) {
                    mSharedPref?.save(CommonUtils.name,mViewModel.mReg.name)
                    mSharedPref?.save(CommonUtils.mail,mViewModel.mReg.email)
                    mSharedPref?.save(CommonUtils.phone,mViewModel.mReg.phoneNumber)
                    shortToast(resources.getString(R.string.success))

                    try{
                        val obj = (activity as? MainActivity)
                        obj?.let {
                            it.setProfileInfo()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                }else {
                    shortToast(resources.getString(R.string.failure))
                }
            }
        })

        return mBinding.root
    }

    private fun callDatePicker() {

        val calendar = Calendar.getInstance()
        val mYear: Int = calendar.get(Calendar.YEAR) // current year
        val mMonth: Int = calendar.get(Calendar.MONTH) // current month
        val mDay: Int = calendar.get(Calendar.DAY_OF_MONTH) // current day

        val dialog = DatePickerDialog(requireActivity(), { _, year, month, day_of_month ->

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