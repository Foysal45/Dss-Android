package com.dss.hrms.activity.ac_otp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2.parseError
import com.dss.hrms.network.model.forget_pass.request.ForgetPassReq
import com.dss.hrms.network.model.forget_pass.response.ForgetPassRes
import com.dss.hrms.network.model.otp.reponse.OtpRes
import com.dss.hrms.network.model.otp.request.OtpReq
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpViewModel :ViewModel(){
    var resultLiveData : MutableLiveData<Any>?=null
    fun otp(token: String,otp_code:String): MutableLiveData<Any> {
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<OtpRes> = RetrofitClient.getInstance().getApi().OtpRes_(
            "application/json","application/json",
            OtpReq(token,otp_code)
        )
        call.enqueue(object : Callback<OtpRes> {
            override fun onResponse(call: Call<OtpRes>, response: Response<OtpRes>) {
                resultLiveData!!.postValue(null)
                if (response.isSuccessful) {
                    resultLiveData!!.setValue(response.body())
                }
                else
                {
                    resultLiveData!!.setValue(parseError(response))
                }
            }
            override fun onFailure(call: Call<OtpRes>, t: Throwable) {
                resultLiveData!!.postValue(null)
                resultLiveData!!.value = t
            }
        })
        return resultLiveData as MutableLiveData<Any>
    }
}