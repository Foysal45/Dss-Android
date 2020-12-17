package com.dss.hrms.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dss.hrms.model.Employee
import com.dss.hrms.model.LoginInfo
import com.dss.hrms.model.login.ResetPassword
import com.dss.hrms.model.login.ResetPasswordReq
import com.dss.hrms.model.login.VerifyOtp
import com.dss.hrms.repository.EmployeeInfoRepo
import com.dss.hrms.repository.LoginRepo

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var loginRepo: LoginRepo? = null

    init {
        loginRepo = LoginRepo(application)
    }

    fun login(email: String, password: String): MutableLiveData<Any>? {
        return loginRepo?.login(email, password)
    }

    fun resetPasswordOtp(number: String): MutableLiveData<Any>? {
        return loginRepo?.resetPasswordOtp(number)
    }

    fun verifyOtp(token: String, otp: String): MutableLiveData<Any>? {
        return loginRepo?.verifyOtp(token, otp)
    }

    fun resetPassword(
        reset_token: String,
        password: String
    ): MutableLiveData<Any>? {
        return loginRepo?.resetPass(reset_token, password)
    }
}