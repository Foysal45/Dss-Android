package com.dss.hrms.view.auth

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.dss.hrms.R
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.model.login.ResetPasswordReq
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.viewmodel.LoginViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_forget_p_ass.*
import kotlinx.android.synthetic.main.activity_forget_p_ass.backBtnIV
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_o_t_p.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class ForgetPAssActivity : BaseActivity() {

    private val REQ_USER_CONSENT = 200
    var smsBroadcastReceiver: SmsBroadcastReceiver? = null
    var textViewMessage: TextView? = null
    var otpText: EditText? = null

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
       // startSmsUserConsent()
    }


    private fun startSmsUserConsent() {
        val client = SmsRetriever.getClient(this)
        client.startSmsUserConsent(null).addOnSuccessListener {
            Toast.makeText(
                applicationContext,
                "On Success",
                Toast.LENGTH_LONG
            ).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "On OnFailure", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == RESULT_OK && data != null) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                // textViewMessage!!.text =
                //   String.format("%s - %s", getString(R.string.received_message), message)
                getOtpFromMessage(message)
            }
        }
    }


    private fun getOtpFromMessage(message: String?) {
        // This will match any 6 digit number in the message
        Toast.makeText(this,"otp message ${message}",Toast.LENGTH_LONG).show()
        Log.e("otp",".................................${message}........................")
        val pattern: Pattern = Pattern.compile("(|^)\\d{6}")
        val matcher: Matcher = pattern.matcher(message)
        if (matcher.find()) {
            // otpText.setText(matcher.group(0))
        }
    }


    private fun registerBroadcastReceiver() {

        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver!!.smsBroadcastReceiverListener =
            object : SmsBroadcastReceiver.SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent) {
                   // startActivityForResult(intent, REQ_USER_CONSENT)
                    Log.e("sms",".........................sms received onSuccess...........................")

                }

                override fun onFailure() {
                    Log.e("sms",".........................sms received onFailure...........................")

                }
            }
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver, intentFilter)
    }

//    override fun onStart() {
//        super.onStart()
//        registerBroadcastReceiver()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        unregisterReceiver(smsBroadcastReceiver)
//    }

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