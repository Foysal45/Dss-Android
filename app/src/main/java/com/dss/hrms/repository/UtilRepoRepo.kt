package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dss.hrms.retrofit.ApiService
import com.dss.hrms.model.SpinnerDataModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject


class UtilRepoRepo @Inject constructor() {
    @Inject
    lateinit var application: Application

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var preparence: MySharedPreparence


    suspend fun getHeadOfficeDepartMent(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = apiService.headOfficeDepartmentList(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}"
                )
                Log.e("response", "response : ")
                if (response?.body()?.code == 200 || response?.body()?.code == 201)
                    response?.body()
                else
                    null
            } catch (e: Exception) {
                Log.e("responseexceotion", "response : exceotion: ${e.message}")
                null
            }

        }
    }


    fun getDivision(): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
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

                        if (code == 200 || code == 201) {
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

                        if (code == 200 || code == 201) {
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

                        if (code == 200 || code == 201) {
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

                        if (code == 200 || code == 201) {
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

                        if (code == 200 || code == 201) {
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