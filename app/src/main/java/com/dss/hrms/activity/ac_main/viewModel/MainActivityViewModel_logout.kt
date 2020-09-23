package com.dss.hrms.activity.ac_main.viewModel

import BaseLogout
import BaseModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2.parseError
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.activity.ac_login_signup.view.LoginSignupActivity
import com.dss.hrms.network.model.honours_award.response.HonorAwardRes
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel_logout :ViewModel(){
    var resultLiveData : MutableLiveData<Any>?=null
    fun logout(activity:Activity,context: Context): MutableLiveData<Any> {
        val sharedPref = SharedPref(context)
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<BaseLogout> = RetrofitClient.getInstance().getApi().logout_(
            "application/json","application/json",sharedPref.access_token!!
        )
        call.enqueue(object : Callback<BaseLogout> {
            override fun onResponse(call: Call<BaseLogout>, response: Response<BaseLogout>) {
                resultLiveData!!.postValue(null)
                Log.d("code", "response code logout: " + response.code())
                if (response.isSuccessful){
                    var intent= Intent(context, LoginSignupActivity::class.java)
                    sharedPref.access_token = ""
                    sharedPref.uEmployeeId = 0
                    //sharedPref!!.uEmail = any.getData().getEmail()
                    //sharedPref!!.uPassword = password.text.toString()
                    sharedPref.uName = ""
                    sharedPref.uPhone = ""
                    sharedPref.isLogin = false
                    context.startActivity(intent)
                    activity.finish()
                }
            }
            override fun onFailure(call: Call<BaseLogout>, t: Throwable) {
                resultLiveData!!.postValue(null)
            }
        })
        return resultLiveData as MutableLiveData<Any>
    }
}