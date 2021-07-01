package com.dss.hrms.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.model.login.VerifyOtp
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.viewmodel.LoginViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_o_t_p.*
import javax.inject.Inject


class OTPActivity : BaseActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    var loginViewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalLanguage(preparence!!.getLanguage())
        setContentView(R.layout.activity_o_t_p)
        backBtnIV.setOnClickListener({
            onBackPressed();
        });
        init()
        submit.setOnClickListener {
            verifyOtp()
        }

    }


    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        super.onBackPressed()
    }

    fun verifyOtp() {
        var one = etFirst.text.toString().trim()
        var two = etSecond.text.toString().trim()
        var three = etThird.text.toString().trim()
        var four = etFourth.text.toString().trim()
        var five = etFifth.text.toString().trim()
        var six = etSixth.text.toString().trim()
        var otpSum = one + two + three + four + five + six
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
                        startActivity(
                            Intent(this, ChangePassActivity::class.java)
                                .putExtra("reset_token", any.data?.reset_token)
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
                if (count > 0) {
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
                if (count > 0) {
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
                if (count > 0) {
                    etFifth.requestFocus()
                }
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
        etFifth.addTextChangedListener(textWatcher5)


    }

    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

}