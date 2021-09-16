package com.dss.hrms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.dss.hrms.view.auth.LoginActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task

class SplashScreen : AppCompatActivity() {
    private val REQUEST_UPDATE = 100
    private val APP_UPDATE_TYPE_SUPPORTED = AppUpdateType.IMMEDIATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            checkForUpdates()
        }, 700)
    }

    private fun checkForUpdates() {

        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfo = appUpdateManager.appUpdateInfo
        appUpdateInfo.addOnSuccessListener {
            Log.e( "TAGGED",  "${appUpdateInfo.result.updateAvailability()}")
            handleUpdate(appUpdateManager, appUpdateInfo)
        }
    }

    private fun handleUpdate(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {
        if (APP_UPDATE_TYPE_SUPPORTED == AppUpdateType.IMMEDIATE) {

            if(info.result.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE){
                goToHome()
            }else {
                handleImmediateUpdate(manager, info)
            }

        }
    }

    private fun handleImmediateUpdate(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {
        if ((info.result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||

                    info.result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) &&

            info.result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
        ) {

            manager.startUpdateFlowForResult(
                info.result,
                AppUpdateType.IMMEDIATE,
                this,
                REQUEST_UPDATE
            )
        }
    }
    private fun goToHome(){
        val mainIntent = Intent(this, LoginActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

}