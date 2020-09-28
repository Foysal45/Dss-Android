package com.dss.hrms.activity.ac_otp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import com.dss.hrms.activity.change_pass.view.ChangePassActivity
import com.dss.hrms.network.model.otp.reponse.OtpRes
import kotlinx.android.synthetic.main.activity_o_t_p.*

class OTPActivity : BaseActivity() {
    var otpViewModel: OtpViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)
        otpViewModel =
            ViewModelProvider(this)[OtpViewModel::class.java]
        Log.d("token",""+intent.getStringExtra("token"))
        submit.setOnClickListener {
            otpViewModel!!.otp(""+intent.getStringExtra("token"),otp_code.text.toString().trim()) .observe(this, Observer<Any> { any ->
                if (any is OtpRes) {
                    Log.d("resetToken",any.getData().getReset_token())
                    startActivity(Intent(this, ChangePassActivity::class.java).putExtra("resetToken",any.getData().getReset_token()))
                    finish()
                }
                else if (any is ApiError) {
                    e_otp_code.visibility = View.GONE
                    try {
                        if (any.getError().isEmpty()) {
                            toast(this, any.getMessage())
                            Log.d("ok","error")
                        } else {
                            for (n in any.getError().indices) {
                                val error = any.getError()[n].getField()
                                val massage = any.getError()[n].getMessage()

                                when (error) {
                                    "otp_code" -> {
                                        e_otp_code.visibility = View.VISIBLE
                                        e_otp_code.text = ErrorUtils2.mainError(massage)
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

        }
    }
    override fun onBackPressed() {
        startActivity(Intent(this, LoginSignupActivity::class.java))
        finish()
    }
}