package com.dss.hrms.activity.ac_main.viewModel

import BaseModel
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2.parseError
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.network.model.honours_award.response.HonorAwardRes
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel :ViewModel(){
    var resultLiveData : MutableLiveData<Any>?=null
    fun baseData(context: Context): MutableLiveData<Any> {
        val sharedPref = SharedPref(context)
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<BaseModel> = RetrofitClient.getInstance().getApi().BaseModel_(
            "application/json","application/json",sharedPref.access_token!!,sharedPref.uEmployeeId!!
        )
        call.enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                resultLiveData!!.postValue(null)

                Log.d("code","response code : " + response.code())
                if (response.isSuccessful) {
                    resultLiveData!!.setValue(response.body())
                }
                else
                {
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