package com.dss.hrms.activity.change_pass.view

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
import com.dss.hrms.activity.ac_otp.view.OtpViewModel
import com.dss.hrms.network.model.otp.reponse.OtpRes
import com.dss.hrms.network.model.reset_pass.response.ResetPassRes
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_change_pass.submit
import kotlinx.android.synthetic.main.activity_o_t_p.*

class ChangePassActivity : BaseActivity() {


    var changePassViewModel: ChangePassViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        changePassViewModel =
            ViewModelProvider(this)[ChangePassViewModel::class.java]
        Log.d("resetToken",""+intent.getStringExtra("resetToken"))
        submit.setOnClickListener {
            changePassViewModel!!.changepass(""+intent.getStringExtra("resetToken"),password.text.toString().trim(),confirm_password.text.toString().trim()).observe(this,
                Observer<Any> { any ->
                if (any is ResetPassRes) {
                    startActivity(Intent(this, LoginSignupActivity::class.java))
                    finish()
                } else if (any is ApiError) {
                    e_password.visibility = View.GONE
                    e_confirm_password.visibility = View.GONE
                    try {
                        if (any.getError().isEmpty()) {
                            toast(this, any.getMessage())
                            Log.d("ok","error")
                        } else {
                            for (n in any.getError().indices) {
                                val error = any.getError()[n].getField()
                                val massage = any.getError()[n].getMessage()

                                when (error) {
                                    "password" -> {
                                        e_password.visibility = View.VISIBLE
                                        e_password.text = ErrorUtils2.mainError(massage)
                                    }
                                    "password_confirmation" -> {
                                        e_confirm_password.visibility = View.VISIBLE
                                        e_confirm_password.text = ErrorUtils2.mainError(massage)
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