package com.dss.hrms.fragment.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2.parseError
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.network.model.foreign_traning.response.ForeignTraningRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeePersonalInfoViewModel_foreignTraning :ViewModel(){
    var resultLiveData : MutableLiveData<Any>?=null
    fun foreignTraning(context: Context): MutableLiveData<Any> {
        val sharedPref = SharedPref(context)
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<ForeignTraningRes> = RetrofitClient.getInstance().getApi().ForeignTraningRes_(
            "application/json","application/json",sharedPref.access_token!!,sharedPref.uEmployeeId!!
        )
        call.enqueue(object : Callback<ForeignTraningRes> {
            override fun onResponse(call: Call<ForeignTraningRes>, response: Response<ForeignTraningRes>) {
                resultLiveData!!.postValue(null)
                if (response.isSuccessful) {
                    resultLiveData!!.setValue(response.body())
                }
                else
                {
                    resultLiveData!!.setValue(parseError(response))
                }
            }
            override fun onFailure(call: Call<ForeignTraningRes>, t: Throwable) {
                resultLiveData!!.postValue(null)
                resultLiveData!!.value = t
            }
        })
        return resultLiveData as MutableLiveData<Any>
    }
}