package com.dss.hrms.view.settings

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.dss.hrms.R
import com.dss.hrms.view.activity.BaseActivity
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_employee_info.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.back
import javax.inject.Inject


class SettingsActivity : BaseActivity() {
    @Inject
    lateinit var preparence: MySharedPreparence


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalLanguage(preparence!!.getLanguage())
        setContentView(R.layout.activity_settings)
        back.setOnClickListener({
            onBackPressed()
        })
        loadScreen()

        tvEn.setOnClickListener {
            if (preparence!!.getLanguage().equals("en"))
                return@setOnClickListener
            preparence?.setLanguage("en")
            finish()
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        tvBn.setOnClickListener {
            if (preparence!!.getLanguage().equals("bn"))
                return@setOnClickListener
            preparence?.setLanguage("bn")
            finish()
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }



        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, PackageManager.GET_ACTIVITIES)
        Log.e("settings",("PackageName = " + info.packageName + "\nVersionCode = "
                + info.versionCode + "\nVersionName = "
                + info.versionName + "\nPermissions = " + info.permissions))
        tvVersion.setText(info.versionName)
//        try {
//            val pInfo: PackageInfo =
//                getPackageManager().getPackageInfo(getPackageName(), 0)
//            val version = pInfo.versionName
//            tvVersion.setText(version)
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }

    }


    private fun loadScreen() {
        if (preparence!!.getLanguage().equals("en")) {
            tvEn.setBackgroundResource(R.drawable.shape_rec_left_green_10dp_solod)
            tvEn.setTextColor(ContextCompat.getColor(this, R.color.white))
            tvBn.setBackgroundResource(R.drawable.shape_rec_right_white_light_10dp_solod)
            tvBn.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else {
            tvBn.setBackgroundResource(R.drawable.shape_rec_right_green_10dp_solod)
            tvBn.setTextColor(ContextCompat.getColor(this, R.color.white))
            tvEn.setBackgroundResource(R.drawable.shape_rec_left_white_light_10dp_solod)
            tvEn.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }
}