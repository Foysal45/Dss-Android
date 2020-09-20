package com.dss.hrms.activity.ac_login_signup.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.activity.ac_main.view.MainActivity
import com.dss.hrms.helper.LanguageChange
import com.dss.hrms.R
import com.dss.hrms.activity.ac_base.view.BaseActivity
import com.dss.hrms.activity.ac_login_signup.viewModel.SignInActivityViewModel
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import kotlinx.android.synthetic.main.activity_login_signup.*
import java.util.*


class LoginSignupActivity : BaseActivity() {
    var sharedPref: SharedPref? = null
    var signInActivityViewModel: SignInActivityViewModel? = null
    var lan: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguageChange.languageSet(this)
        setContentView(R.layout.activity_login_signup)
        sharedPref = SharedPref(this)
        signInActivityViewModel =
            ViewModelProvider(this)[SignInActivityViewModel::class.java]

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

        login.setOnClickListener(View.OnClickListener {
            signInActivityViewModel!!.signIn(
                UserLoginReq(
                    email.text.toString(),
                    password.text.toString()
                )
            )
                .observe(this, Observer<Any> { any ->

                    if (any is UserLoginRes) {
                        e_email.visibility = View.GONE
                        e_password.visibility = View.GONE
                        sharedPref!!.access_token = any.getData().getToken()
                        sharedPref!!.uEmployeeId = any.getData().getEmployeeId()
                        sharedPref!!.uEmail = any.getData().getEmail()
                        sharedPref!!.uName = any.getData().getUsername()
                        sharedPref!!.uPhone = any.getData().getPhoneNumber()
                        sharedPref!!.uPassword = password.text.toString()
                        sharedPref!!.isLogin = true
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else if (any is ApiError) {
                        e_email.visibility = View.GONE
                        e_password.visibility = View.GONE
                        try {
                            if (any.getError().isEmpty()) {
                                toast(this, any.getMessage())
                                Log.d("ok","error")
                            } else {
                                for (n in any.getError().indices) {
                                    val error = any.getError()[n].getField()
                                    val massage = any.getError()[n].getMessage()

                                    when (error) {
                                        "email" -> {
                                            e_email.visibility = View.VISIBLE
                                            e_email.text = ErrorUtils2.mainError(massage)
                                        }
                                        "password" -> {
                                            e_password.visibility = View.VISIBLE
                                            e_password.text = ErrorUtils2.mainError(massage)
                                        }
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            toast(this, e.toString())
                        }


                    } else if (any is Throwable) {
                        toast(this, any.toString())
                    }

                })


        })


    }

    private fun loadScreen() {
        if (sharedPref!!.isEnglish!!) {
            en.setBackgroundResource(R.drawable.shape_rec_left_green_10dp_solod)
            en.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            bn.setBackgroundResource(R.drawable.shape_rec_right_white_light_10dp_solod)
            bn.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        } else {
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