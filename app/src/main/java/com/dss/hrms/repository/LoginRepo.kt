package com.dss.hrms.repository

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.btbapp.alquranapp.retrofit.ApiService
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.model.LoginInfo
import com.dss.hrms.model.login.ResetPassword
import com.dss.hrms.model.login.ResetPasswordReq
import com.dss.hrms.model.login.VerifyOtp
import com.dss.hrms.retrofit.RetrofitInstance
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepo {
    private var application: Application? = null
    private var apiService: ApiService? = null

    constructor(application: Application?) {
        this.application = application
        apiService = RetrofitInstance.retrofitInstance!!.create(ApiService::class.java)
    }

    fun login(email: String, password: String): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        var map = HashMap<Any, Any>()
        map.put("userid", email)
        map.put("password", password)
        val call: Call<Any?>? = apiService?.userLogin(preparence?.getLanguage()!!, map)
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 && !TextUtils.isEmpty(status) && status.equals("success")) {
                            Log.e("response", "response : " + jsonObjectParent)
                            val dataJA = jsonObjectParent.getJSONObject("data")
                            val loginInfo =
                                Gson().fromJson(dataJA.toString(), LoginInfo::class.java)
                            liveData.postValue(loginInfo)
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    Log.e("response", "response.body() : ")
                    ///  val jsonObjectParent = JSONObject(Gson().toJson(response.errorBody()))
                    // Log.e("response", "response.body() : " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                Log.e("response", "onFailure : " + t.message)
            }

        })
        return liveData
    }

    fun resetPasswordOtp(number: String): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence = application?.let { MySharedPreparence(it) }
        var map = HashMap<Any, Any>()
        map.put("phone_number", number)
        val call: Call<Any>? = apiService?.resetPasswordOtp(preparence?.getLanguage()!!, map)
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 400) {
                            Log.e("response", "response : " + jsonObjectParent)
                            // val dataJA = jsonObjectParent.getJSONObject("data")
                            val resetPasswordReq =
                                Gson().fromJson(
                                    jsonObjectParent.toString(),
                                    ResetPasswordReq::class.java
                                )
                            liveData.postValue(resetPasswordReq)
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    Log.e("response", "response.body() : ")
                    ///  val jsonObjectParent = JSONObject(Gson().toJson(response.errorBody()))
                    // Log.e("response", "response.body() : " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                Log.e("response", "onFailure : " + t.message)
            }

        })
        return liveData
    }

    fun verifyOtp(token: String, otp: String): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence = application?.let { MySharedPreparence(it) }

        var map = HashMap<Any, Any>()
        map.put("token", token)
        map.put("otp_code", otp)
        val call: Call<Any?>? = apiService?.verifyOtp(preparence?.getLanguage()!!, map)
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if ((code == 200 || code == 400)) {
                            Log.e("response", "response : " + jsonObjectParent)
                            val verifyOtp =
                                Gson().fromJson(jsonObjectParent.toString(), VerifyOtp::class.java)
                            liveData.postValue(verifyOtp)
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    Log.e("response", "response.body() : ")
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                Log.e("response", "onFailure : " + t.message)
            }

        })
        return liveData
    }


    fun resetPass(
        reset_token: String,
        password: String
    ): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence = application?.let { MySharedPreparence(it) }

        var map = HashMap<Any, Any>()
        map.put("reset_token", reset_token)
        map.put("password", password)
        map.put("password_confirmation", password)
        val call: Call<Any?>? = apiService?.resetPass(preparence?.getLanguage()!!, map)
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 400) {
                            Log.e("response", "response : " + jsonObjectParent)

                            val resetPassword = Gson().fromJson(
                                jsonObjectParent.toString(),
                                ResetPassword::class.java
                            )
                            liveData.postValue(resetPassword)
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    Log.e("response", "response.body() : ")
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                Log.e("response", "onFailure : " + t.message)
            }

        })
        return liveData
    }


}