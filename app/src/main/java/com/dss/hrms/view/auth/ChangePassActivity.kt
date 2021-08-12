package com.dss.hrms.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders

import com.dss.hrms.R
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.model.login.ResetPassword
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.viewmodel.LoginViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_change_pass.*
import javax.inject.Inject


class ChangePassActivity : BaseActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    var loginViewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalLanguage(preparence?.getLanguage())
        setContentView(R.layout.activity_change_pass)
        backBtnIV.setOnClickListener({
            onBackPressed()
        });
        init()
        Log.e("resetToken", "" + intent.getStringExtra("reset_token"))
        submit.setOnClickListener {
            resetPassword()
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        super.onBackPressed()
    }

    fun resetPassword() {
        var password = password.text.toString().trim()
        var confirm_password = confirm_password.text.toString().trim()
        e_password.visibility = View.GONE
        e_confirm_password.visibility = View.GONE
        if (password != null && password.equals(confirm_password)) {
            var dialog = CustomLoadingDialog().createLoadingDialog(this)
            loginViewModel!!.resetPassword("" + intent.getStringExtra("reset_token"), password)
                ?.observe(this, Observer { any ->
                    dialog?.dismiss()

                    if (any is ResetPassword) {
                        if (any.code == 200) {
                            startActivity(
                                Intent(
                                    this,
                                    LoginActivity::class.java
                                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            )
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    } else if (any is ApiError) {
                        e_password.visibility = View.GONE
                        e_confirm_password.visibility = View.GONE
                        try {
                            if (any.getError().isEmpty()) {
                                toast(this, any.getMessage())
                                Log.d("ok", "error")
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
                    } else if (any is Throwable) {
                        toast(this, any.toString())
                    }

                })
        } else {
            if (password != null) {
                e_confirm_password.visibility = View.VISIBLE
                e_confirm_password.setText("" + getString(R.string.password_mismatch))
            }
        }
    }

    fun init() {
        loginViewModel =
            ViewModelProviders.of(this, viewModelProviderFactory).get(LoginViewModel::class.java)
    }

    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

}