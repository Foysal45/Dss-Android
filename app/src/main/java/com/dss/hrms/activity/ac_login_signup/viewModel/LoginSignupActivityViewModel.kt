package com.dss.hrms.activity.ac_login_signup.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaadride.network.api.RetrofitClient
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginSignupActivityViewModel: ViewModel() {

    var resultLiveData: MutableLiveData<Any>? = null
    fun login(userLoginReq: UserLoginReq): MutableLiveData<Any> {
        if (resultLiveData == null) {
            resultLiveData = MutableLiveData()
        }
        val call: Call<UserLoginRes> = RetrofitClient.getInstance().getApi().UserLoginRes_("application/json","application/json",userLoginReq
        )

        call.enqueue(object : Callback<UserLoginRes> {
            override fun onResponse(call: Call<UserLoginRes>, response: Response<UserLoginRes>) {
                resultLiveData!!.postValue(null)
                if (response.isSuccessful)
                {
                    resultLiveData!!.value = response.body()
                }

                else{
                    resultLiveData!!.value=ErrorUtils2.parseError(response)
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