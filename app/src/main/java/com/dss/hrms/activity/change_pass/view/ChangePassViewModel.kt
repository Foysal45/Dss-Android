package com.dss.hrms.activity.change_pass.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2.parseError
import com.dss.hrms.network.model.forget_pass.request.ForgetPassReq
import com.dss.hrms.network.model.forget_pass.response.ForgetPassRes
import com.dss.hrms.network.model.otp.reponse.OtpRes
import com.dss.hrms.network.model.otp.request.OtpReq
import com.dss.hrms.network.model.reset_pass.request.ResetPassReq
import com.dss.hrms.network.model.reset_pass.response.ResetPassRes
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassViewModel :ViewModel(){
    var resultLiveData : MutableLiveData<Any>?=null
    fun changepass(reset_token: String,password:String,password_confirmation:String): MutableLiveData<Any> {
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<ResetPassRes> = RetrofitClient.getInstance().getApi().ResetPassRes_(
            "application/json","application/json",
            ResetPassReq(reset_token,password,password_confirmation)
        )
        call.enqueue(object : Callback<ResetPassRes> {
            override fun onResponse(call: Call<ResetPassRes>, response: Response<ResetPassRes>) {
                resultLiveData!!.postValue(null)
                if (response.isSuccessful) {
                    resultLiveData!!.setValue(response.body())
                }
                else
                {
                    resultLiveData!!.setValue(parseError(response))
                }
            }
            override fun onFailure(call: Call<ResetPassRes>, t: Throwable) {
                resultLiveData!!.postValue(null)
                resultLiveData!!.value = t
            }
        })
        return resultLiveData as MutableLiveData<Any>
    }
}