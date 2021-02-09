package com.dss.hrms.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dss.hrms.repository.LoginRepo
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        reset_token: String,
        password: String
    ): MutableLiveData<Any>? {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        viewModelScope.launch {
            var value = async { loginRepo?.resetPass(reset_token, password) }.await()
            liveData?.postValue(value)
        }
        return liveData
        //  return loginRepo?.resetPass(reset_token, password)
    }
}