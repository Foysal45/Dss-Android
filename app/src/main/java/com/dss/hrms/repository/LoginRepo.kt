package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import com.dss.hrms.retrofit.ApiService
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.model.login.ResetPassword
import com.dss.hrms.model.login.ResetPasswordReq
import com.dss.hrms.model.login.VerifyOtp
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class LoginRepo @Inject constructor() {
    @Inject
    lateinit var application: Application

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var preparence: MySharedPreparence

    suspend fun login(email: String, password: String): Any? {
        var map = HashMap<Any, Any>()
        map.put("userid", email)
        map.put("password", password)
        Log.e("LoginActivity", "repo  : ")
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService?.login(preparence?.getLanguage()!!, map)
                Log.e("apiresponse", "response ${response}")
                if (response?.body()?.code == 200 || response?.body()?.code == 201) response?.body()?.data
                else response?.let {
                    var apiError = ErrorUtils2.parseError(
                        it
                    )
                    apiError
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "repo message : ${e.message}")
                null
            }
        }
    }

    suspend fun resetPasswordOtp(number: String): Any? {
        var map = HashMap<Any, Any>()
        map.put("phone_number", number)
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService?.resetPasswordOtp(preparence?.getLanguage()!!, map)
                Log.e("apiresponse", "response ${response?.body()?.code}")
                if (response?.body()?.code == 200 || response?.body()?.code == 201) response?.body() as ResetPasswordReq
                else response?.let {
                    var apiError = ErrorUtils2.parseError(
                        it
                    )
                    apiError
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun verifyOtp(token: String, otp: String): Any? {
        var map = HashMap<Any, Any>()
        map.put("token", token)
        map.put("otp_code", otp)
        return withContext(Dispatchers.IO) {

            try {
                val response = apiService?.verifyOtp(preparence?.getLanguage()!!, map)
                Log.e("apiresponse", "response ${response?.body()?.code}")
                if (response?.body()?.code == 200 || response?.body()?.code == 201) response?.body() as VerifyOtp
                else response?.let {
                    var apiError = ErrorUtils2.parseError(
                        it
                    )
                    apiError
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun resetPass(reset_token: String, password: String): Any? {
        var map = HashMap<Any, Any>()
        map.put("reset_token", reset_token)
        map.put("password", password)
        map.put("password_confirmation", password)
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService?.resetPass(preparence?.getLanguage()!!, map)
                Log.e("apiresponse", "response ${response?.body()?.code}")
                if (response?.body()?.code == 200 || response?.body()?.code == 201) response?.body() as ResetPassword
                else response?.let {
                    var apiError = ErrorUtils2.parseError(
                        it
                    )
                    apiError
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun changePass(
        current_password: String,
        confirm_password: String,
        password: String,
        bearerToken: String
    ): Any? {
        var map = HashMap<Any, Any>()
        map.put("current_password", current_password)
        map.put("password", password)
        map.put("confirm_password", confirm_password)
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService?.changePass(preparence?.getLanguage()!!, bearerToken,  map)
                Log.e("apiresponse", "response ${response?.body()?.code}")
                if (response?.body()?.code == 200 || response?.body()?.code == 201) response?.body() as ResetPassword
                else response?.let {
                    var apiError = ErrorUtils2.parseError(
                        it
                    )
                    apiError
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}