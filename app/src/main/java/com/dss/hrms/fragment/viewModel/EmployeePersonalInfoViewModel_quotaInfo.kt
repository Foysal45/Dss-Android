package com.dss.hrms.fragment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2.parseError
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeePersonalInfoViewModel_quotaInfo :ViewModel(){
    var resultLiveData : MutableLiveData<Any>?=null
    fun signIn(signInReq: UserLoginReq): MutableLiveData<Any> {
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<UserLoginRes> = RetrofitClient.getInstance().getApi().UserLoginRes_(
            "application/json","application/json",
            signInReq
        )
        call.enqueue(object : Callback<UserLoginRes> {
            override fun onResponse(call: Call<UserLoginRes>, response: Response<UserLoginRes>) {
                resultLiveData!!.postValue(null)
                if (response.isSuccessful) {
                    resultLiveData!!.setValue(response.body())
                }
                else
                {
                    resultLiveData!!.setValue(parseError(response))
                }
            }
            override fun onFailure(call: Call<UserLoginRes>, t: Throwable) {
                resultLiveData!!.postValue(null)
                resultLiveData!!.value = t
            }
        })
        return resultLiveData as MutableLiveData<Any>
    }
}