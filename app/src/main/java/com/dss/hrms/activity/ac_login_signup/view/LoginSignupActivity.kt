package com.dss.hrms.activity.ac_login_signup.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.Helper.LanguageChange
import com.dss.hrms.R
import com.dss.hrms.activity.ac_base.view.BaseActivity
import kotlinx.android.synthetic.main.activity_login_signup.*
import java.util.*


class LoginSignupActivity : BaseActivity() {
    var sharedPref: SharedPref? = null
    var lan: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(this)
        LanguageChange.languageSet(this)
        setContentView(R.layout.activity_login_signup)
        lan = LanguageChange.langA
        loadScreen()



        en.setOnClickListener {
            if (sharedPref!!.isEnglish!!)
                return@setOnClickListener
            sharedPref!!.isEnglish = true
            loadScreen()
            sharedPref!!.lang = 1
            ChangeLang(sharedPref!!.lang!!)
        }
        bn.setOnClickListener {
            if (!sharedPref!!.isEnglish!!)
                return@setOnClickListener
            sharedPref!!.isEnglish = false
            loadScreen()
            sharedPref!!.lang = 2
            ChangeLang(sharedPref!!.lang!!)
        }

    }

    private fun loadScreen() {
        if (sharedPref!!.isEnglish!!)
        {
            en.setBackgroundResource(R.drawable.shape_rec_left_green_10dp_solod)
            en.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            bn.setBackgroundResource(R.drawable.shape_rec_right_white_light_10dp_solod)
            bn.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        }
        else{
            bn.setBackgroundResource(R.drawable.shape_rec_right_green_10dp_solod)
            bn.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            en.setBackgroundResource(R.drawable.shape_rec_left_white_light_10dp_solod)
            en.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        }
    }

    override fun onResume() {
        super.onResume()
        if (lan != LanguageChange.langA) {
            recreate()
        }
    }

    private fun setLocal(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        getResources().updateConfiguration(
            config,
            getResources().getDisplayMetrics()
        )
    }

    private fun ChangeLang(lang: Int) {
        var lan = "en"
        when (lang) {
            1 -> lan = "en"
            2 -> lan = "bn"
        }
        setLocal(lan)
        LanguageChange.langA = lan
        Objects.requireNonNull(this).recreate()
    }
}