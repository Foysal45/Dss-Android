package com.dss.hrms.activity.ac_forget_pass.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.activity.ac_base.view.BaseActivity
import com.dss.hrms.activity.ac_login_signup.view.LoginSignupActivity
import com.dss.hrms.activity.ac_otp.view.OTPActivity
import com.dss.hrms.helper.LanguageChange
import com.dss.hrms.network.model.forget_pass.response.ForgetPassRes
import kotlinx.android.synthetic.main.activity_forget_p_ass.*

class ForgetPAssActivity : BaseActivity() {

    var forgetPassViewModel: ForgetPassViewModel? = null
    var lan: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_p_ass)
        LanguageChange.languageSet(this)
        lan = LanguageChange.langA
        forgetPassViewModel =
            ViewModelProvider(this)[ForgetPassViewModel::class.java]
        back.setOnClickListener {
            onBackPressed()
        }
        sent.setOnClickListener {

            forgetPassViewModel!!.forgetPass(phone_email.text.toString().trim())
                .observe(this, Observer<Any> { any ->

                    if (any is ForgetPassRes) {
                        Log.d("otp",any.getData().getOtp_code())
                         startActivity(Intent(this, OTPActivity::class.java).putExtra("token",any.getData().getToken()))
                        finish()
                    } else if (any is ApiError) {
                        e_phone_email.visibility = View.GONE
                        try {
                            if (any.getError().isEmpty()) {
                                toast(this, any.getMessage())
                                Log.d("ok","error")
                            } else {
                                for (n in any.getError().indices) {
                                    val error = any.getError()[n].getField()
                                    val massage = any.getError()[n].getMessage()

                                    when (error) {
                                        "phone_number" -> {
                                            e_phone_email.visibility = View.VISIBLE
                                            e_phone_email.text = ErrorUtils2.mainError(massage)
                                        }

                                    }
                                }
                            }
                        } catch (e: Exception) {
                            toast(this, e.toString())
                        }
                    }
                    else if (any is Throwable) {
                        toast(this, any.toString())
                    }

                })

        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginSignupActivity::class.java))
        finish()
    }
}