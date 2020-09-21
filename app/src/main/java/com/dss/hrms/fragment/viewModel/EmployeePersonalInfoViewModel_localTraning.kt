package com.dss.hrms.fragment.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2.parseError
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.network.model.honours_award.response.HonorAwardRes
import com.dss.hrms.network.model.local_training.response.LocalTraningRes
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeePersonalInfoViewModel_localTraning :ViewModel(){
    var resultLiveData : MutableLiveData<Any>?=null
    fun localTraning(context: Context): MutableLiveData<Any> {
        val sharedPref = SharedPref(context)
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<LocalTraningRes> = RetrofitClient.getInstance().getApi().LocalTraningRes_(
            "application/json","application/json",sharedPref.access_token!!,sharedPref.uEmployeeId!!
        )
        call.enqueue(object : Callback<LocalTraningRes> {
            override fun onResponse(call: Call<LocalTraningRes>, response: Response<LocalTraningRes>) {
                resultLiveData!!.postValue(null)
                if (response.isSuccessful) {
                    resultLiveData!!.setValue(response.body())
                }
                else
                {
                    resultLiveData!!.setValue(parseError(response))
                }
            }
            override fun onFailure(call: Call<LocalTraningRes>, t: Throwable) {
                resultLiveData!!.postValue(null)
                resultLiveData!!.value = t
            }
        })
        return resultLiveData as MutableLiveData<Any>
    }
}