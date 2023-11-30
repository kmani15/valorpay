package com.example.valorpay.activity

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.valorpay.R
import com.example.valorpay.model.Tax
import com.example.valorpay.repository.CommonRepository
import com.example.valorpay.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : BaseActivity() {

    var bottomNavigationView: BottomNavigationView? = null
    var navController: NavController? = null

    var navHost: NavHostFragment? = null
    var logoutLayout: RelativeLayout? = null
    var settings_lay: RelativeLayout? = null
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var graph: NavGraph? = null
    var mSharedPref: SharedPreferencesHelper?=null

    lateinit var mUserName: TextView
    lateinit var mail: TextView
    lateinit var phone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSharedPref = SharedPreferencesHelper(this)
        logoutLayout = findViewById(R.id.logout_lay)
        settings_lay = findViewById(R.id.settings_lay)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        mUserName = findViewById(R.id.user_name)
        mail = findViewById(R.id.mail)
        phone = findViewById(R.id.phone)

        //New
        navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment?
        navController = navHost!!.navController
        val navInflater = navController!!.navInflater
        graph = navInflater.inflate(R.navigation.nav_graph)
        graph!!.setStartDestination(R.id.productFragment)
        navController!!.graph = graph!!
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationViewDrawer)

        NavigationUI.setupWithNavController(bottomNavigationView!!, navController!!)
        NavigationUI.setupWithNavController(navigationView!!, navController!!)

        logoutLayout!!.setOnClickListener(View.OnClickListener {
            drawerLayout!!.closeDrawers()
            openLogoutDialog()
        })
        settings_lay!!.setOnClickListener(View.OnClickListener {
            drawerLayout!!.closeDrawers()
            callSettings()
            //callDialog()

        })

        setProfileInfo()

    }

    private fun openLogoutDialog() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(resources.getString(R.string.logout_msg))
        builder.setTitle(resources.getString(R.string.logout_title))
        builder.setCancelable(false)

        builder.setPositiveButton(resources.getString(R.string.yes))
        { dialog: DialogInterface?, which: Int ->
            clearAllData(dialog)
        }

        builder.setNegativeButton(resources.getString(R.string.no))
        { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun clearAllData(dialog: DialogInterface?) {
        mSharedPref?.let {
            it.clearAll()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            dataBase.mDataBaseDao().deleteCartTable()
        }

        dialog?.dismiss()
        clearAllCallMainPage()
    }

    fun setProfileInfo() {
        mSharedPref?.let {

            try {
                mUserName.text = it.getValueString(CommonUtils.name)
                mail.text = it.getValueString(CommonUtils.mail)
                phone.text = it.getValueString(CommonUtils.phone)

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun callDialog() {
        drawerLayout!!.closeDrawers()
        val dialog = showAlertDialog(R.layout.layout_settings)

        val edtTax = dialog.findViewById(R.id.etTax) as TextInputEditText
        val edtFee = dialog.findViewById(R.id.etFee) as TextInputEditText
        val btnSave = dialog.findViewById(R.id.save) as Button

        btnSave.setOnClickListener {
            if (edtTax.text.toString().trim().isEmpty() ||
                edtTax.text.toString().trim().toInt()>100) {
                longToast(resources.getString(R.string.tax_error))
            }else if (edtFee.text.toString().trim().isEmpty() ||
                edtFee.text.toString().trim().toInt()>100) {
                longToast(resources.getString(R.string.fee_error))
            }else {

                lifecycleScope.launch(Dispatchers.IO) {

                    val repository = CommonRepository(dataBase)

                    val mTax = Tax()
                    mTax.stateTax = edtTax.text.toString()
                    mTax.customFee = edtFee.text.toString()

                    val id = repository.insertOrUpdate(mTax)
                    Log.d("TAX",id.toString())

                    launch(Dispatchers.Main) {
                        if (id>0) {
                            shortToast(resources.getString(R.string.success))
                            dialog.dismiss()
                        }else {
                            shortToast(resources.getString(R.string.failure))
                        }
                    }

                }

            }
        }

        dialog.show()
    }

    private fun showAlertDialog(dialogLayout: Int): Dialog {

        val mDialog = Dialog(this)
        mDialog.setContentView(dialogLayout)
        mDialog.setCanceledOnTouchOutside(true)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        mDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        //mDialog.window?.setBackgroundDrawableResource(R.drawable.white_dialog_bg)

        return mDialog
    }

}