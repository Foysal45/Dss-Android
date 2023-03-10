package com.dss.hrms.view.auth

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dss.hrms.R
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.model.login.VerifyOtp
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.auth.SmsBroadcastReceiver.SmsBroadcastReceiverListener
import com.dss.hrms.viewmodel.LoginViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_o_t_p.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


class OTPActivity : BaseActivity() {

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
        setLocalLanguage(preparence!!.getLanguage())
        setContentView(R.layout.activity_o_t_p)
        backBtnIV.setOnClickListener {
            onBackPressed();
        }
        init()
        submit.setOnClickListener {
            verifyOtp()
        }
        //  startSmsUserConsent()
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
                //    getOtpFromMessage(message)
            }
        }
    }


    private fun getOtpFromMessage(message: String?) {
        // This will match any 6 digit number in the message
        val pattern: Pattern = Pattern.compile("(|^)\\d{6}")
        val matcher: Matcher = pattern.matcher(message)
        if (matcher.find()) {
            // otpText.setText(matcher.group(0))
        }
    }


    private fun registerBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver!!.smsBroadcastReceiverListener =
            object : SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent) {
                    startActivityForResult(intent, REQ_USER_CONSENT)
                }

                override fun onFailure() {}
            }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver, intentFilter)
    }
//
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

    fun verifyOtp() {
        val one = etFirst.text.toString().trim()
        val two = etSecond.text.toString().trim()
        val three = etThird.text.toString().trim()
        val four = etFourth.text.toString().trim()
        val five = etFifth.text.toString().trim()
        val six = etSixth.text.toString().trim()
        val otpSum = one + two + three + four + five + six
        e_otp_code.visibility = View.GONE
        if (otpSum != null && otpSum.equals(intent.getStringExtra("otp"))) {
            var dialog = CustomLoadingDialog().createLoadingDialog(this)
            loginViewModel?.verifyOtp(
                "" + intent.getStringExtra("token"),
                otpSum
            )?.observe(this, Observer<Any> { any ->
                if (any is VerifyOtp) {
                    dialog?.dismiss()
                    if (any.code == 200) {

                        Log.d("resetToken ", " otp "+any.data?.reset_token)
                        startActivity(


                            Intent(this, ChangePassActivity::class.java)
                                .putExtra("reset_token", any.data?.reset_token)
                                .putExtra("isReset" , true)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                } else if (any is ApiError) {
                    e_otp_code.visibility = View.GONE
                    try {
                        if (any.getError().isEmpty()) {
                            toast(this, any.getMessage())
                            Log.d("ok", "error")
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
        } else {
            if (otpSum != null) {
                e_otp_code.visibility = View.VISIBLE
                e_otp_code.setText("" + getString(R.string.wrong_otp))
            } else {
                toast(this, "" + getString(R.string.failed))
            }
        }
    }

    fun init() {
        loginViewModel =
            ViewModelProviders.of(this, viewModelProviderFactory).get(LoginViewModel::class.java)


        val textWatcher1: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count > 0) {
                    etSecond.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }
        val textWatcher2: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                val ss = s.toString()
                if (ss.length == 1) {
                    etThird.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }
        val textWatcher3: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                val ss = s.toString()
                if (ss.length == 1) {
                    etFourth.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}


        }
        val textWatcher4: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
//                if (count > 0) {
//                    etFifth.requestFocus()
//                }
            }

            override fun afterTextChanged(s: Editable) {}
        }
        val textWatcher5: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count > 0) {
                    etSixth.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }

        val textWatcher6: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count > 0) {
                    etSixth.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }

        etFirst.addTextChangedListener(textWatcher1)
        etSecond.addTextChangedListener(textWatcher2)
        etThird.addTextChangedListener(textWatcher3)
        etFourth.addTextChangedListener(textWatcher4)
        // etFifth.addTextChangedListener(textWatcher5)


    }

    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

}