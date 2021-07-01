package com.dss.hrms.view.auth

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
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.model.login.LoginInfo
import com.dss.hrms.util.CustomVisibility
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.allInterface.OnNetworkStateChangeListener
import com.dss.hrms.view.receiver.NetworkChangeReceiver
import com.dss.hrms.viewmodel.LoginViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginActivity : BaseActivity(), OnNetworkStateChangeListener {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var requestManager: RequestManager


    var loginViewModel: LoginViewModel? = null
    var lan: String? = null
    var mNetworkReceiver: NetworkChangeReceiver? = null
    var isShowPassword = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preparence?.let {
            setLocalLanguage(preparence.getLanguage())
            if (preparence.isLogin()!!) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        viewModelProviderFactory?.let {
            Log.e("loginactivity", "viewmodel factory module : " + viewModelProviderFactory)
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
            Intent(this, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        tvBn.setOnClickListener {
            if (preparence?.getLanguage().equals("bn"))
                return@setOnClickListener
            preparence?.setLanguage("bn")
            Intent(this, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        login.setOnClickListener({
            login()
        })
        f_pass.setOnClickListener {
            Intent(this, ForgetPAssActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    fun init() {
        //lazy {loginViewModel= ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java) }
        loginViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)
        cbRemenber.isChecked = preparence?.isRemember()!!
        mNetworkReceiver = NetworkChangeReceiver(this)
        registerNetworkBroadcast()
    }


    fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("device_token", "Fetching FCM registration token failed ", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.e("device_token", "token : " + token)

            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        })
    }

    fun login() {
        var email = etEmail.text.toString().trim()
        var password = etPassword.text.toString().trim()
        loading_dialog.visibility = View.VISIBLE
        login.visibility = View.GONE
        //  var dialog = CustomLoadingDialog().createLoadingDialog(this)
        Log.e("LoginActivity", "login  : ")
        lifecycleScope.launch {
            async { loginViewModel?.login(email, password) }.await()
                ?.collect {
                    loading_dialog.visibility = View.GONE
                    login.visibility = View.VISIBLE
                    Log.e("LoginActivity", "response : " + it)
                    if (it is LoginInfo) {
                        preparence?.setLoginStatus(true)
                        preparence?.setEmail(email)
                        preparence?.setPassword(password)
                        preparence?.setLoginStatus(true)
                        it.token?.let { preparence?.setToken(it) }
                        var loginInfo = it as LoginInfo
                        preparence?.setLoginInfo(loginInfo?.apply { this.password = password }
                            .let { Gson().toJson(it) })
                        getDeviceToken()
                    } else if (it is ApiError) {
                        e_email.visibility = View.GONE
                        e_password.visibility = View.GONE
                        try {
                            if (it.getError().isEmpty()) {
                                toast(applicationContext, it.getMessage())
                                Log.d("ok", "error")
                            } else {
                                for (n in it.getError().indices) {
                                    val error = it.getError()[n].getField()
                                    val message = it.getError()[n].getMessage()
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
                            toast(applicationContext, e.toString())
                        }
                    } else if (it is Throwable) {
                        toast(applicationContext, it.toString())
                    } else {
                        toast(applicationContext, getString(R.string.failed))
                    }

                }
        }
    }

    private fun loadScreen() {
        if (preparence?.getLanguage().equals("en")) {
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