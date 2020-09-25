package com.dss.hrms.activity.ac_main.viewModel

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

class MainActivityViewModel : ViewModel() {
    var resultLiveData: MutableLiveData<Any>? = null
    fun baseData(activity: Activity,context: Context): MutableLiveData<Any> {
        val sharedPref = SharedPref(context)
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<BaseModel> = RetrofitClient.getInstance().getApi().BaseModel_(
            "application/json",
            "application/json",
            sharedPref.access_token!!,
            sharedPref.uEmployeeId!!
        )
        call.enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                resultLiveData!!.postValue(null)
                if (response.isSuccessful) {
                    resultLiveData!!.setValue(response.body())
                } else if (response.code() == 401) {
                    var intent=Intent(context,LoginSignupActivity::class.java)
                    sharedPref.access_token = ""
                    sharedPref.uEmployeeId = 0
                    //sharedPref!!.uEmail = any.getData().getEmail()
                    //sharedPref!!.uPassword = password.text.toString()
                    sharedPref.uName = ""
                    sharedPref.uPhone = ""
                    sharedPref.isLogin = false
                    context.startActivity(intent)
                    activity.finish()
                } else {
                    resultLiveData!!.setValue(parseError(response))
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                resultLiveData!!.postValue(null)
                resultLiveData!!.value = t
            }
        })
        return resultLiveData as MutableLiveData<Any>
    }
}