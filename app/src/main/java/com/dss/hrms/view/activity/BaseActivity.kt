package com.dss.hrms.view.activity

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogConfirmationBinding
import dagger.android.support.DaggerAppCompatActivity
import java.util.*


open class BaseActivity : DaggerAppCompatActivity() {
    protected var dialogConfirmation: Dialog? = null
    protected var dialogConfirmationBinding: DialogConfirmationBinding? = null

    protected fun setLocalLanguage(type: String?) {
        val locale = Locale(type)
        Locale.setDefault(locale)
        val configuration: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            createConfigurationContext(configuration)
        }
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

//
//     protected fun changeStatusBarColor(){
//         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//             val flag = android.view.Sta.LightStatusBar as Android.Views.StatusBarVisibility
//             SystemColor.window.DecorView.SystemUiVisibility = if (darkStatusBarTint) flag else 0
//         }
//     }

    protected open fun showConfirmationDialog(context: Context?) {
        dialogConfirmation = Dialog(context!!)
        dialogConfirmation!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogConfirmationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_confirmation,
            null,
            false
        )
        dialogConfirmation!!.setContentView(dialogConfirmationBinding!!.getRoot())
        val window: Window? = dialogConfirmation!!.getWindow()
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogConfirmation!!.show()
    }
}