package com.dss.hrms.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.repository.LoginRepo
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var loginRepo: LoginRepo


    @Inject
    lateinit var preparence: MySharedPreparence

    fun login(email: String, password: String): Flow<Any?>? = flow {
        // viewModelScope.launch() {
        // var value = async { loginRepo.login(email, password) }.await()
        Log.e("LoginActivity", "viewmodel  : ")
        emit(loginRepo.login(email, password))
        // }
    }


    fun resetPasswordOtp(number: String): Flow<Any?>? = flow {
        // viewModelScope.launch() {
        // var value = async { loginRepo.login(email, password) }.await()
        emit(loginRepo.resetPasswordOtp(number))
        // }
    }

    fun verifyOtp(token: String, otp: String): MutableLiveData<Any>? {
        // return loginRepo?.verifyOtp(token, otp)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        viewModelScope.launch {
            var value = async { loginRepo?.verifyOtp(token, otp) }.await()
            liveData?.postValue(value)
        }
        return liveData
    }

    fun resetPassword(
        password: String,
        reset_token: String

    ): MutableLiveData<Any>? {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        viewModelScope.launch {
            var value = withContext(Dispatchers.Default) {
                loginRepo.resetPass(
                    reset_token,
                    password
                )
            }
            liveData?.postValue(value)
        }
        return liveData
        //  return loginRepo?.resetPass(reset_token, password)
    }


    fun changePassword(
        current_password: String,
        password: String,
        bearerToken: String
    ): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        viewModelScope.launch {
            val value =
                withContext(Dispatchers.Default) {
                    loginRepo.changePass(
                        current_password,
                        password,
                        password,
                        bearerToken
                    )
                }
            liveData.postValue(value)
        }
        return liveData
        //  return loginRepo?.resetPass(reset_token, password)
    }
}