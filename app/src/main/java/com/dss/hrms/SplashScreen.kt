package com.dss.hrms

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.anggrayudi.storage.SimpleStorage
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import android.content.IntentSender.SendIntentException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dss.hrms.model.commonSpinnerDataLoad.CommonData
import com.dss.hrms.util.HelperClass
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.auth.LoginActivity
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject


class SplashScreen : BaseActivity() {

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var employeeViewModel: EmployeeViewModel
    private val REQUEST_UPDATE = 100
    var commonDataDropdown: MutableLiveData<CommonData>? = MutableLiveData()
    private val storage = SimpleStorage(this)
    private lateinit var appUpdateManager: AppUpdateManager
    private val APP_UPDATE_TYPE_SUPPORTED = AppUpdateType.IMMEDIATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        appUpdateManager = AppUpdateManagerFactory.create(this)
        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            //checking for play store update
            checkForUpdates()
        }, 500)
    }

    private fun checkForUpdates() {

        val appUpdateInfo = appUpdateManager.appUpdateInfo

        appUpdateInfo.addOnSuccessListener {
            handleUpdate(appUpdateManager, appUpdateInfo)
        }
    }

    private fun handleUpdate(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {

        if (info.result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            handleImmediateUpdate(manager, info)
        } else {
            Log.d("TAGasdfsa", "handleUpdate: ${info.result.updateAvailability()}")
            loadData()
        }


    }

    private fun handleImmediateUpdate(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {
        if ((info.result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                    info.result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                    )
        ) {

            manager.startUpdateFlowForResult(
                info.result,
                AppUpdateType.IMMEDIATE,
                this@SplashScreen,
                REQUEST_UPDATE
            )
        }
    }

    private fun loadData() {
        employeeViewModel.apply {

            getCommonDataDropDown()

            employeeViewModel._CommonDataResp?.observe(this@SplashScreen, Observer { commonData ->
                if (commonData != null) {
                    /*
                     plan to store  it
                     */
                    preparence.put(commonData, HelperClass.COMMON_DATA)

                    goToHome()

                }
            })

        }


    }

    private fun goToHome() {
        val mainIntent = Intent(this, LoginActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        it,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_UPDATE
                    )
                } catch (e: SendIntentException) {
                    e.printStackTrace()
                }

            }

        }
    }
}