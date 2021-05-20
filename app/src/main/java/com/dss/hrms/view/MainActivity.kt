package com.dss.hrms.view

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chaadride.network.error.ApiError
import com.dss.hrms.R
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.JsonKeyReader
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.login.LoginInfo
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.CustomVisibility
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.login.LoginActivity
import com.dss.hrms.view.settings.SettingsActivity
import com.dss.hrms.view.allInterface.OnNetworkStateChangeListener
import com.dss.hrms.view.leave.LeaveActivity
import com.dss.hrms.view.messaging.MessagingActivity
import com.dss.hrms.view.payroll.PayrollActivity
import com.dss.hrms.view.receiver.NetworkChangeReceiver
import com.dss.hrms.view.report.ReportActivity
import com.dss.hrms.view.training.TrainingActivity
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dashboard_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.nav_menu_layout.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.HashMap
import javax.inject.Inject

class MainActivity : BaseActivity(), OnNetworkStateChangeListener {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var loginInfo: LoginInfo

    @Inject
    // @Named("employee")
    lateinit var employeeProfileData: EmployeeProfileData

    var employee: Employee? = null


    lateinit var employeeViewModel: EmployeeViewModel
    var mNetworkReceiver: NetworkChangeReceiver? = null
    var utilViewModel: UtilViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalLanguage(preparence.getLanguage())
        setContentView(R.layout.activity_main)
        appContext = application
        context = this
        init()
        employeeViewModel?.apply {
            getUserPermissions()

            userPermission.observe(this@MainActivity, Observer { list ->
                list?.let {

                    if (JsonKeyReader.hasPermission("reports", it)) {
                        rlReport.visibility = View.VISIBLE
                        menu_dashboard_report.visibility= View.VISIBLE
                    } else {
                        rlReport.visibility = View.GONE
                        menu_dashboard_report.visibility = View.GONE
                    }
                    Log.e(
                        "permission",
                        ".......................................permission result ${
                            JsonKeyReader.hasPermission(
                                "commonstationary-edit",
                                it
                            )
                        }"
                    )
                }
            })
        }
        Log.e("mainactivity", "inject login data " + loginInfo?.email)
        Log.e("mainactivity", "inject employee data " + employeeProfileData?.employee?.profile_id)
        getEmployeeInfo()

        btnPersonalInfo.setOnClickListener({
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 0))
            selectedPosition = 0
        })
        btnSettings.setOnClickListener({
            Intent(this, SettingsActivity::class.java).apply {
                startActivity(this)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })
        btnTraining.setOnClickListener({
            Intent(this, TrainingActivity::class.java).apply {
                startActivity(this)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })
        btnMessaging.setOnClickListener({
            Intent(this, MessagingActivity::class.java).apply {
                startActivity(this)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })
        btnPayroll.setOnClickListener({
            Intent(this, PayrollActivity::class.java).apply {
                startActivity(this)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })
        btnLeave.setOnClickListener({
            Intent(this, LeaveActivity::class.java).apply {
                startActivity(this)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })
        btnReport.setOnClickListener({
            Intent(this, ReportActivity::class.java).apply {
                startActivity(this)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }


    override fun onStart() {
        super.onStart()
        drawerMenu()

    }

    fun init() {
        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
        mNetworkReceiver = NetworkChangeReceiver(this)
        registerNetworkBroadcast()
    }

    fun getEmployeeInfo() {
        var dialog = CustomLoadingDialog().createLoadingDialog(this)
        lifecycleScope.launch {
            employeeViewModel?.getEmployeeInfo(loginInfo?.employee_id)
                ?.collect {
                    dialog?.dismiss()
                    if (it is Employee) {
                        employee = employeeProfileData.employee
                        drawerMenu()
                        Log.e(
                            "mainactivity",
                            "inject employee data name : " + employeeProfileData?.employee?.name_bn
                        )
                        //   Log.e("MainActivity", "response : " + any)
                    } else if (it is ApiError) {

                    } else if (it is Throwable) {
                        toast(appContext!!, it.toString())
                    }
                }
        }
    }


    fun drawerMenu() {


        Glide.with(this).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_baseline_image_24)
        ).load(RetrofitInstance.BASE_URL + employee?.photo)
            .into(headerLayout.menu_logo)


        if (employee?.jobjoinings != null && employee?.jobjoinings!!.size > 0)
            if (preparence.getLanguage().equals("en")) {
                headerLayout.menu_name.setText(employee?.name)
                headerLayout.menu_office_address.setText(employee?.jobjoinings!![0]?.office?.office_name)
                headerLayout.menu_designation.setText(employee?.jobjoinings!![0]?.designation?.name)
            } else {
                headerLayout.menu_name.setText(employee?.name_bn)
                headerLayout.menu_office_address.setText(employee?.jobjoinings!![0]?.office?.office_name_bn)
                headerLayout.menu_designation.setText(employee?.jobjoinings!![0]?.designation?.name_bn)
            }


        headerLayout.rlPersonalInfo.setOnClickListener({
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 0))
        })


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

//
//        menu_profile_promotion.setOnClickListener({ view ->
//            selectedPosition = 17
//            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 17))
//        })
        menu_profile_reference.setOnClickListener({ view ->
            selectedPosition = 17
            startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 17))
        })
        menu_profile_nominee.setOnClickListener({ view ->
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


        this.menu_dashboard_settings.setOnClickListener {
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        menu_dashboard_training.setOnClickListener {
            startActivity(
                Intent(this, TrainingActivity::class.java)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        this.menu_dashboard_leave.setOnClickListener {
            startActivity(
                Intent(this, LeaveActivity::class.java)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        this.menu_dashboard_payroll.setOnClickListener {
            startActivity(
                Intent(this, PayrollActivity::class.java)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        this.menu_dashboard_messaging.setOnClickListener {
            startActivity(
                Intent(this, MessagingActivity::class.java)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        menu_dashboard_report.setOnClickListener {
            startActivity(
                Intent(this, ReportActivity::class.java)
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
        // var employee: Employee? = null
        var appContext: Application? = null
        var context: MainActivity? = null
        var selectedPosition: Int = 0
    }
}