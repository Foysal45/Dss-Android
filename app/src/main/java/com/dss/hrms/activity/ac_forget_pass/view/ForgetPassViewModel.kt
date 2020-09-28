package com.dss.hrms.activity.ac_forget_pass.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2.parseError
import com.dss.hrms.network.model.forget_pass.request.ForgetPassReq
import com.dss.hrms.network.model.forget_pass.response.ForgetPassRes
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPassViewModel :ViewModel(){
    var resultLiveData : MutableLiveData<Any>?=null
    fun forgetPass(phone: String): MutableLiveData<Any> {
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<ForgetPassRes> = RetrofitClient.getInstance().getApi().ForgetPassRes_(
            "application/json","application/json",
            ForgetPassReq(phone)
        )
        call.enqueue(object : Callback<ForgetPassRes> {
            override fun onResponse(call: Call<ForgetPassRes>, response: Response<ForgetPassRes>) {
                resultLiveData!!.postValue(null)
                if (response.isSuccessful) {
                    resultLiveData!!.setValue(response.body())
                }
                else
                {
                    resultLiveData!!.setValue(parseError(response))
                }
            }
            override fun onFailure(call: Call<ForgetPassRes>, t: Throwable) {
                resultLiveData!!.postValue(null)
                resultLiveData!!.value = t
            }
        })
        return resultLiveData as MutableLiveData<Any>
    }
}