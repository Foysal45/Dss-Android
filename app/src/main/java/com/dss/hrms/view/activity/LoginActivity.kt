package com.dss.hrms.view.activity

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.model.LoginInfo
import com.dss.hrms.model.login.ResetPasswordReq
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.CustomVisibility
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.OnNetworkStateChangeListener
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.receiver.NetworkChangeReceiver
import com.dss.hrms.viewmodel.LoginViewModel
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_login.*
import org.w3c.dom.Text


class LoginActivity : BaseActivity(), OnNetworkStateChangeListener {
    var preparence: MySharedPreparence? = null
    var loginViewModel: LoginViewModel? = null
    var lan: String? = null
    var mNetworkReceiver: NetworkChangeReceiver? = null
    var isShowPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preparence = MySharedPreparence(this)
        setLocalLanguage(preparence!!.getLanguage())
        if (preparence!!.isLogin()!!) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_login)
        init()
        loadScreen()
        clickListener()

//        if (preparence!!.isRemember()!!) {
//            setData()
//        }

    }

//    fun setData() {
//        etEmail.setText(preparence?.getEmail())
//        etPassword.setText(preparence?.getPassword())
//    }

    fun clickListener() {
        ivPassword.setOnClickListener({
            if (isShowPassword) {
                etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivPassword.setImageResource(R.drawable.ic_password_view)
                isShowPassword = false
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT
                ivPassword.setImageResource(R.drawable.password_hide)
                isShowPassword = true
            }
        })

        cbRemenber.setOnCheckedChangeListener({ buttonView, isChecked ->
            if (isChecked) {
                preparence!!.setRemember(true)
            } else {
                preparence!!.setRemember(false)
                etEmail.setText("")
                etPassword.setText("")
            }
        })

        tvEn.setOnClickListener {
            if (preparence!!.getLanguage().equals("en"))
                return@setOnClickListener
            preparence?.setLanguage("en")
            finish()
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        tvBn.setOnClickListener {
            if (preparence!!.getLanguage().equals("bn"))
                return@setOnClickListener
            preparence?.setLanguage("bn")
            finish()
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        login.setOnClickListener({
            login()
        })

        f_pass.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ForgetPAssActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun init() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        cbRemenber.isChecked = preparence?.isRemember()!!
        mNetworkReceiver = NetworkChangeReceiver(this)
        registerNetworkBroadcast()
    }

    fun login() {
        var email = etEmail.text.toString().trim()
        var password = etPassword.text.toString().trim()
        //   if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
        loading_dialog.visibility = View.VISIBLE
        login.visibility = View.GONE
        //var dialog = CustomLoadingDialog().createLoadingDialog(this)
        loginViewModel?.login(email, password)?.observe(this, Observer { any ->
            //  dialog?.dismiss()
            loading_dialog.visibility = View.GONE
            login.visibility = View.VISIBLE
            Log.e("LoginActivity", "response : " + any)
            if (any is LoginInfo) {

                preparence?.setLoginStatus(true)
                preparence?.setEmail(email)
                preparence?.setPassword(password)
                preparence?.setLoginStatus(true)
                any.token?.let { preparence?.setToken(it) }
                var loginInfo = any as LoginInfo
                preparence?.setLoginInfo(loginInfo?.apply { this.password = password }
                    .let { Gson().toJson(it) })
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            } else if (any is ApiError) {
                e_email.visibility = View.GONE
                e_password.visibility = View.GONE
                try {
                    if (any.getError().isEmpty()) {
                        toast(this, any.getMessage())
                        Log.d("ok", "error")
                    } else {
                        for (n in any.getError().indices) {
                            val error = any.getError()[n].getField()
                            val message = any.getError()[n].getMessage()
                            if (TextUtils.isEmpty(error)) {
                                message?.let {
                                    e_password.visibility = View.VISIBLE
                                    e_password.text = ErrorUtils2.mainError(message)
                                }

                            }

                            when (error) {
                                "email" -> {
                                    e_email.visibility = View.VISIBLE
                                    e_email.text = ErrorUtils2.mainError(message)
                                }
                                "password" -> {
                                    e_password.visibility = View.VISIBLE
                                    e_password.text = ErrorUtils2.mainError(message)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    toast(this, e.toString())
                }


            } else if (any is Throwable) {
                toast(this, any.toString())
            } else {
                toast(this, getString(R.string.failed))
            }
        })
        //   } else {
        //   toast(this, getString(R.string.required_field_cannot_be_empty))

        // }
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

    private fun registerNetworkBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(
                mNetworkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    private fun unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkChanges()
    }


    override fun onChange(isConnected: Boolean) {
        if (isConnected) {
            noInternetTV.setBackgroundColor(resources.getColor(R.color.green))
            noInternetTV.setText(resources.getString(R.string.back_online))
            Handler().postDelayed({
                CustomVisibility.collapse(
                    noInternetTV,
                    500
                )
            }, 2000)
        } else {
            noInternetTV.setBackgroundColor(resources.getColor(R.color.red))
            noInternetTV.setText(resources.getString(R.string.no_internet_connection))
            CustomVisibility.expand(noInternetTV, 500)
        }
    }

    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}