package com.dss.hrms.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.model.login.ResetPasswordReq
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.viewmodel.LoginViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_forget_p_ass.*
import kotlinx.android.synthetic.main.activity_forget_p_ass.backBtnIV
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_o_t_p.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.EnumSet.of
import javax.inject.Inject

class ForgetPAssActivity : BaseActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    var loginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalLanguage(preparence?.getLanguage())
        setContentView(R.layout.activity_forget_p_ass)
        backBtnIV.setOnClickListener({
            onBackPressed();
        });
        init()
        sent.setOnClickListener {
            forgetPassword()
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        super.onBackPressed()
    }

    fun forgetPassword() {
        e_phone_email.visibility = View.GONE
        var phone = phone_email.text.toString().trim()
        // if (!TextUtils.isEmpty(phone)) {
        var dialog = CustomLoadingDialog().createLoadingDialog(this)
        Log.e("apiresponse", "called Activity")
        lifecycleScope.launch {
            async { loginViewModel?.resetPasswordOtp(phone) }.await()
                ?.collect { any ->
                    dialog?.dismiss()
                    if (any is ResetPasswordReq) {
                        var resetPasswordReq: ResetPasswordReq = any as ResetPasswordReq
                        if (resetPasswordReq.code == 200) {
                            Log.e("otp", "otp : " + resetPasswordReq.data?.otp)
                            Intent(this@ForgetPAssActivity, OTPActivity::class.java).apply {
                                putExtra("otp", resetPasswordReq.data?.otp)
                                putExtra("token", resetPasswordReq.data?.token)
                                startActivity(this)
                            }
                            finish()
                        }
                    } else if (any is ApiError) {
                        e_phone_email.visibility = View.GONE
                        try {
                            if (any.getError().isEmpty()) {
                                toast(this@ForgetPAssActivity, any.getMessage())
                                Log.d("ok", "error")
                            } else {
                                for (n in any.getError().indices) {
                                    val error = any.getError()[n].getField()
                                    val message = any.getError()[n].getMessage()
                                    if (TextUtils.isEmpty(error)) {
                                        message?.let {
                                            e_phone_email.visibility = View.VISIBLE
                                            e_phone_email.text = ErrorUtils2.mainError(message)
                                        }

                                    }
                                    when (error) {
                                        "phone_number" -> {
                                            e_phone_email.visibility = View.VISIBLE
                                            e_phone_email.text = ErrorUtils2.mainError(message)
                                        }

                                    }
                                }
                            }
                        } catch (e: Exception) {
                            toast(this@ForgetPAssActivity, e.toString())
                        }
                    } else if (any is Throwable) {
                        toast(this@ForgetPAssActivity, any.toString())
                    } else {
                        toast(this@ForgetPAssActivity, getString(R.string.failed))
                    }
                }
        }
    }

    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

    fun init() {

        loginViewModel =
            ViewModelProviders.of(this, viewModelProviderFactory).get(LoginViewModel::class.java)
    }
}