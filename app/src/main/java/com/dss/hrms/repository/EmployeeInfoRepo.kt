package com.dss.hrms.repository

import android.app.Application
import android.content.SharedPreferences
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.btbapp.alquranapp.retrofit.ApiService
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.model.Employee
import com.dss.hrms.retrofit.RetrofitInstance.retrofitInstance
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EmployeeInfoRepo {
    private var application: Application? = null
    private var apiService: ApiService? = null

    constructor(application: Application?) {
        this.application = application
        apiService = retrofitInstance!!.create(ApiService::class.java)
    }

    fun getEmployeeInfo(employeeId: Int?): MutableLiveData<Any>? {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        val token = application?.let { MySharedPreparence(it).getToken() }
        val call: Call<Any?>? = apiService?.getEmployeeInfo("Bearer ${token}", employeeId)
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.body() != null) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")

                        if (code == 200 && !TextUtils.isEmpty(status) && status.equals("success")) {
                            val dataJA = jsonObjectParent.getJSONObject("data")
                            val employee =
                                Gson().fromJson(dataJA.toString(), Employee::class.java)
                            liveData.postValue(employee)
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                    }
                } else {
                   // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
            }

        })
        return liveData
    }
}