package com.dss.hrms.repository

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.btbapp.alquranapp.retrofit.ApiService
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.retrofit.RetrofitInstance.retrofitInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namaztime.namaztime.database.MySharedPreparence
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class UtilRepoRepo {
    private var application: Application? = null
    private var apiService: ApiService? = null

    constructor(application: Application?) {
        this.application = application
        apiService = retrofitInstance!!.create(ApiService::class.java)
    }

    fun getDivision(): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
        var preparence = application?.let { MySharedPreparence(it) }

        val call: Call<Any?>? =
            apiService?.getDivision(preparence?.getLanguage()!!, "Bearer ${preparence?.getToken()}")
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                Log.e("message", "response : " + response.body())
                if (response.body() != null) {

                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")

                        if (code == 200 && !TextUtils.isEmpty(status) && status.equals("success")) {
                            val dObj = jsonObjectParent.getJSONObject("data")
                            val dataJA = dObj.getJSONArray("data")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val division: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(division)
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    Log.e("message", "response : " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
            }

        })
        return liveData
    }

    fun getDistrict(divisionId: Int): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getDistrict(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}",
                divisionId
            )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.body() != null) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")

                        if (code == 200 && !TextUtils.isEmpty(status) && status.equals("success")) {
                            val dataJO = jsonObjectParent.getJSONObject("data")
                            val dataJA = dataJO.getJSONArray("districts")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val district: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(district)
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

    fun getUpazila(districtId: Int): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getUpazila(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}",
                districtId
            )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.body() != null) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")

                        if (code == 200 && !TextUtils.isEmpty(status) && status.equals("success")) {
                            val dataJO = jsonObjectParent.getJSONObject("data")
                            val dataJA = dataJO.getJSONArray("upazilas")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val upazila: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(upazila)
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

    fun getCommonData(identity: String): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getCommonData(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}",
                identity
            )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.body() != null) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")

                        if (code == 200 && !TextUtils.isEmpty(status) && status.equals("success")) {
                            val dataJA = jsonObjectParent.getJSONArray("data")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val upazila: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(upazila)
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

  fun getCommonData2(identity: String): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getCommonData(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}",
                identity
            )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.body() != null) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")

                        if (code == 200 && !TextUtils.isEmpty(status) && status.equals("success")) {
                            val dObj = jsonObjectParent.getJSONObject("data")
                            val dataJA = dObj.getJSONArray("data")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val dataList: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(dataList)
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