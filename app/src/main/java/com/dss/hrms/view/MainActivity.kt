package com.dss.hrms.view

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.model.Employee
import com.dss.hrms.model.LoginInfo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.CustomVisibility
import com.dss.hrms.view.`interface`.OnNetworkStateChangeListener
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.activity.LoginActivity
import com.dss.hrms.view.adapter.employeeInfo.CustomeView
import com.dss.hrms.view.receiver.NetworkChangeReceiver
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.LoginViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_header.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_menu_layout.*

class MainActivity : BaseActivity(), OnNetworkStateChangeListener {
    lateinit var preparence: MySharedPreparence
    lateinit var employeeViewModel: EmployeeViewModel
    var mNetworkReceiver: NetworkChangeReceiver? = null
    var loginInfo: LoginInfo? = null
    var utilViewModel: UtilViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preparence = MySharedPreparence(this)
        setLocalLanguage(preparence.getLanguage())
        setContentView(R.layout.activity_main)
        appContext = application
        context = this
        init()

        drawerMenu()
        getEmployeeInfo()
        utilData()
    }

    fun init() {
        preparence = MySharedPreparence(this)
        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel::class.java)
        mNetworkReceiver = NetworkChangeReceiver(this)
        loginInfo = Gson().fromJson(preparence.getLoginInfo(), LoginInfo::class.java)
        registerNetworkBroadcast()
    }

    fun getEmployeeInfo() {
        var dialog = CustomLoadingDialog().createLoadingDialog(this)
        employeeViewModel?.getEmployeeInfo(loginInfo?.employee_id)
            ?.observe(this, Observer { any ->
                dialog?.dismiss()
                //   Log.e("LoginActivity", "response : " + Gson().toJson(any))
                if (any is Employee) {
                    employee = any
                    //   Log.e("MainActivity", "response : " + any)
                } else if (any is ApiError) {

                } else if (any is Throwable) {
                    toast(this, any.toString())
                }
            })
    }

    fun utilData() {
        llContent.addView(CustomeView().getEditextText(this, "first edittext"))

//        utilViewModel = ViewModelProviders.of(this).get(UtilViewModel::class.java)
//        utilViewModel?.getDivision()
//            ?.observe(this, Observer { any ->
//                Log.e("division", "division " + Gson().toJson(any))
//            })
//        utilViewModel?.getDistrict(1)
//            ?.observe(this, Observer { any ->
//                Log.e("district", "district " + Gson().toJson(any))
//            })
//        utilViewModel?.getUpozila(1)
//            ?.observe(this, Observer { any ->
//                Log.e("upazila", "upazila " + Gson().toJson(any))
//            })


    }

    fun drawerMenu() {
        menu_profile_lay_expandableLayout.collapse()
        menu_dashboard_lay_head.setOnClickListener {
            drawer_menu.closeDrawer(GravityCompat.START)
        }
        menu_profile_lay_head.setOnClickListener {
            menu_profile_lay_expandableLayout.toggle()
            if (!menu_profile_lay_expandableLayout.isExpanded) {
                menu_profile_lay_head.setBackgroundResource(
                    R.drawable.selected
                )
                menu_profile.setTextColor(ContextCompat.getColor(this, R.color.black))
              //  more_profile.setImageDrawable(getDrawable(R.drawable.ic_baseline_expand_more_white_24))
                more_profile.animate().setDuration(300).rotation(180.0f).start()
            } else {
                menu_profile_lay_head.setBackgroundResource(
                    R.drawable.focused
                )
                menu_profile.setTextColor(ContextCompat.getColor(this, R.color.black))
                more_profile.setImageDrawable(getDrawable(R.drawable.ic_baseline_expand_more_24))
                more_profile.animate().setDuration(300).rotation(360.0f).start()
            }
        }
        menu_icon.setOnClickListener { drawer_menu.openDrawer(GravityCompat.START) }
        //menu_back.setOnClickListener { drawer_menu.closeDrawer(GravityCompat.START) }

        menu_profile_personal_info.setOnClickListener({ view ->
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 0))
            selectedPosition = 0
        })
        menu_profile_spouse.setOnClickListener({ view ->
            selectedPosition = 1
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 1))
        })

        menu_profile_child_information.setOnClickListener({ view ->
            selectedPosition = 2
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 2))
        })
        menu_profile_present_address.setOnClickListener({ view ->
            selectedPosition = 3
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 3))
        })
        menu_profile_permanent_address.setOnClickListener({ view ->
            selectedPosition = 4
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 4))
        })
        menu_profile_educational_qualification.setOnClickListener({ view ->
            selectedPosition = 5
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 5))
        })
        menu_profile_language_information.setOnClickListener({ view ->
            selectedPosition = 6
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 6))
        })
        menu_profile_job_joining_information.setOnClickListener({ view ->
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 7))
            selectedPosition = 7
        })
        menu_profile_quata_information.setOnClickListener({ view ->
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 8))
            selectedPosition = 8
        })
        menu_profile_local_training.setOnClickListener({ view ->
            selectedPosition = 9
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 9))
        })
        menu_profile_foreign_training.setOnClickListener({ view ->
            selectedPosition = 10
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 10))
        })
        menu_profile_Official_residential_information.setOnClickListener({ view ->
            selectedPosition = 11
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 11))
        })
        menu_profile_foreign_travel.setOnClickListener({ view ->
            selectedPosition = 12
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 12))
        })
        menu_profile_additional_professional_qualification.setOnClickListener({ view ->
            selectedPosition = 13
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 13))
        })
        menu_profile_publication.setOnClickListener({ view ->
            selectedPosition = 14
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 14))
        })
        menu_profile_honours_award.setOnClickListener({ view ->
            selectedPosition = 15
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 15))
        })
//        menu_profile_posting_record.setOnClickListener({ view ->
//            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 16))
//        })
        menu_profile_disciplinary_action.setOnClickListener({ view ->
            selectedPosition = 16
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 16))
        })


        menu_profile_promotion.setOnClickListener({ view ->
            selectedPosition = 17
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 17))
        })
        menu_profile_reference.setOnClickListener({ view ->
            selectedPosition = 18
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 18))
        })

        this.menu_dashboard_signout.setOnClickListener {
//            var mainActivityViewModel_logout =
////                ViewModelProvider(this)[MainActivityViewModel_logout::class.java]
////            mainActivityViewModel_logout.logout(this,this).observe(this, Observer<Any> { any ->
////            })

            preparence?.setLanguage(null)
            preparence.setLoginStatus(false)
            finish()
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }


    private fun registerNetworkBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(
                mNetworkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    private fun unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkChanges()
    }


    override fun onChange(isConnected: Boolean) {
        if (isConnected) {
            noInternetTV.setBackgroundColor(resources.getColor(R.color.green))
            noInternetTV.setText(resources.getString(R.string.back_online))
            Handler().postDelayed({
                CustomVisibility.collapse(
                    noInternetTV,
                    500
                )
            }, 2000)
        } else {
            noInternetTV.setBackgroundColor(resources.getColor(R.color.red))
            noInternetTV.setText(resources.getString(R.string.no_internet_connection))
            CustomVisibility.expand(noInternetTV, 500)
        }
    }

    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }


    override fun onBackPressed() {
        if (drawer_menu.isDrawerOpen(GravityCompat.START)) {
            drawer_menu.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        var employee: Employee? = null
        var appContext: Application? = null
        var context: MainActivity? = null
        var selectedPosition: Int = 0
    }
}