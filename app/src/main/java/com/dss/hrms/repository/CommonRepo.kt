package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.btbapp.alquranapp.retrofit.ApiService
import com.dss.hrms.model.Office
import com.dss.hrms.model.SpecificDistrictModel
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.Union
import com.dss.hrms.view.allInterface.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namaztime.namaztime.database.MySharedPreparence
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.HashMap
import javax.inject.Inject


class CommonRepo @Inject constructor() {
    @Inject
    lateinit var application: Application

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var preparence: MySharedPreparence


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

    fun getDistrict(
        divisionId: Int?,
        commonDataValueListener: CommonDataValueListener
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

                        if (code == 200 || code == 201) {
                            val dataJO = jsonObjectParent.getJSONObject("data")
                            val dataJA = dataJO.getJSONArray("districts")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val district: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(district)
                            commonDataValueListener.valueChange(district)
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        commonDataValueListener.valueChange(null)
                        liveData.postValue(null)
                    }
                } else {
                    commonDataValueListener.valueChange(null)
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                commonDataValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getDistrict(
        divisionId: Int?
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

    fun getAllDistrict(
    ): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getAllDistrict(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}"
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


    fun getAllHrTraining(
        commonDataValueListener: CommonDataValueListener
    ): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getHrTrainingList(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}"
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
                            val district: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(district)
                            commonDataValueListener.valueChange(district)
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        commonDataValueListener.valueChange(null)
                        liveData.postValue(null)
                    }
                } else {
                    commonDataValueListener.valueChange(null)
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                commonDataValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getAllHrTraining(): MutableList<SpinnerDataModel> {
        val liveData: MutableList<SpinnerDataModel> = arrayListOf()

        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getHrTrainingList(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}"
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
                            val district: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.clear()
                            liveData.addAll(district)

                        } else {
                            liveData.clear()
                        }
                    } catch (e: JSONException) {

                        liveData.clear()
                    }
                } else {

                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.clear()
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.clear()

            }

        })
        return liveData
    }

    fun getAllDistrict(
        commonDataValueListener: CommonDataValueListener
    ): MutableLiveData<List<SpinnerDataModel>>? {
        val liveData: MutableLiveData<List<SpinnerDataModel>> =
            MutableLiveData<List<SpinnerDataModel>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getAllDistrict(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}"
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
                            val district: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            commonDataValueListener.valueChange(district)
                            liveData.postValue(district)
                        } else {
                            commonDataValueListener.valueChange(null)
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        commonDataValueListener.valueChange(null)
                        liveData.postValue(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    commonDataValueListener.valueChange(null)
                    liveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
            }

        })
        return liveData
    }

    fun getUpazila(
        districtId: Int?,
        commonDataValueListener: CommonDataValueListener
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

                        if (code == 200 || code == 201) {
                            val dataJO = jsonObjectParent.getJSONObject("data")
                            val dataJA = dataJO.getJSONArray("upazilas")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val upazila: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(upazila)
                            commonDataValueListener.valueChange(upazila)
                        } else {
                            liveData.postValue(null)
                            commonDataValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        commonDataValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    commonDataValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                commonDataValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getUpazilaWithMunicipalities(
        districtId: Int?,
        specificDistrictValueListener: SpecificDistrictValueListener
    ): MutableLiveData<SpecificDistrictModel>? {
        val liveData: MutableLiveData<SpecificDistrictModel> =
            MutableLiveData<SpecificDistrictModel>()
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

                        if (code == 200 || code == 201) {
                            val dataJO = jsonObjectParent.getJSONObject("data")

                            val data: SpecificDistrictModel =
                                Gson().fromJson(
                                    dataJO.toString(),
                                    SpecificDistrictModel::class.java
                                )
                            liveData.postValue(data)
                            specificDistrictValueListener.valueChange(data)
                        } else {
                            liveData.postValue(null)
                            specificDistrictValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        specificDistrictValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    specificDistrictValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                specificDistrictValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getUpazila(
        districtId: Int?
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

    fun getCommonData(
        identity: String,
        commonDataValueListener: CommonDataValueListener
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

                        if (code == 200 || code == 201) {
                            val dataJA = jsonObjectParent.getJSONArray("data")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val dataList: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(dataList)
                            commonDataValueListener.valueChange(dataList)
                        } else {
                            liveData.postValue(null)
                            commonDataValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        commonDataValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    commonDataValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                commonDataValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getUserRole(
        identity: String,
        commonDataValueListener: CommonDataValueListener
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

                        if (code == 200 || code == 201) {
                            val dataJO = jsonObjectParent.getJSONObject("data")
                            val userJO = dataJO.getJSONObject("user")
                            val roleJA = userJO.getJSONArray("roles")

                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val dataList: List<SpinnerDataModel> =
                                Gson().fromJson(roleJA.toString(), type)
                            liveData.postValue(dataList)
                            commonDataValueListener.valueChange(dataList)
                        } else {
                            liveData.postValue(null)
                            commonDataValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        commonDataValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    commonDataValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                commonDataValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getSpecificSalaryGrade(
        identity: String,
        payScaleValueListener: PayScaleValueListener
    ): MutableLiveData<SpinnerDataModel>? {
        val liveData: MutableLiveData<SpinnerDataModel> =
            MutableLiveData()
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

                        if (code == 200 || code == 201) {
                            val dataJA = jsonObjectParent.getJSONObject("data")
                            val data: SpinnerDataModel =
                                Gson().fromJson(dataJA.toString(), SpinnerDataModel::class.java)
                            liveData.postValue(data)
                            payScaleValueListener.valueChange(data)
                        } else {
                            liveData.postValue(null)
                            payScaleValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        payScaleValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    payScaleValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                payScaleValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getUnion(
        upozilaId: Int?,
        unionDataValueListener: UnionDataValueListener
    ): MutableLiveData<List<Union>>? {
        val liveData: MutableLiveData<List<Union>> =
            MutableLiveData<List<Union>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getUnion(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}",
                upozilaId
            )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.body() != null) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")

                        if (code == 200 || code == 201) {
                            val dataJA = jsonObjectParent.getJSONObject("data")
                            val dataJA1 = dataJA.getJSONArray("unions")
                            val type: Type = object : TypeToken<List<Union?>?>() {}.type
                            val dataList: List<Union> =
                                Gson().fromJson(dataJA1.toString(), type)
                            liveData.postValue(dataList)
                            unionDataValueListener.valueChange(dataList)
                        } else {
                            liveData.postValue(null)
                            unionDataValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        unionDataValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    unionDataValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                unionDataValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getCommonData(
        identity: String
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

                        if (code == 200 || code == 201) {
                            val dataJA = jsonObjectParent.getJSONArray("data")
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

    fun getCommonData2(
        identity: String,
        commonDataValueListener: CommonDataValueListener
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

                        if (code == 200 || code == 201) {
                            val dObj = jsonObjectParent.getJSONObject("data")
                            val dataJA = dObj.getJSONArray("data")
                            val type: Type = object : TypeToken<List<SpinnerDataModel?>?>() {}.type
                            val dataList: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(dataList)
                            commonDataValueListener.valueChange(dataList)
                        } else {
                            liveData.postValue(null)
                            commonDataValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        commonDataValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    commonDataValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                commonDataValueListener.valueChange(null)
            }

        })
        return liveData
    }

    fun getOffice(
        identity: String,
        officeDataValueListener: OfficeDataValueListener
    ): MutableLiveData<List<Office>>? {
        val liveData: MutableLiveData<List<Office>> =
            MutableLiveData<List<Office>>()
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

                        if (code == 200 || code == 201) {
                            val dataJA = jsonObjectParent.getJSONArray("data")
                            val type: Type = object : TypeToken<List<Office?>?>() {}.type
                            val dataList: List<Office> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(dataList)
                            officeDataValueListener.valueChange(dataList)
                        } else {
                            liveData.postValue(null)
                            officeDataValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        officeDataValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    officeDataValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                officeDataValueListener.valueChange(null)
            }

        })
        return liveData
    }


    fun getOfficeWithWhereClause(
        map: HashMap<Any, Any?>,
        officeDataValueListener: OfficeDataValueListener
    ): MutableLiveData<List<Office>>? {
        val liveData: MutableLiveData<List<Office>> =
            MutableLiveData<List<Office>>()
        var preparence = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? =
            apiService?.getOfficeDataWithWhereClause(
                preparence?.getLanguage()!!,
                "Bearer ${preparence?.getToken()}",
                map.get("division_id")?.let { it as Int },
                map.get("district_id")?.let { it as Int },
                map.get("sixteen_category_id")?.let { it as Int },
                map.get("head_office_department_id")?.let { it as Int },
                map.get("head_office_section_id")?.let { it as Int },
                map.get("head_office_sub_section_id")?.let { it as Int },
                map.get("designation_id")?.let { it as Int }
            )
        Log.e("repo", "repos whereClause ${map}")
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.body() != null) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("repo", "repos ${jsonObjectParent}")

                        if (code == 200 || code == 201) {
                            val dataJA = jsonObjectParent.getJSONArray("data")
                            val type: Type = object : TypeToken<List<Office?>?>() {}.type
                            val dataList: List<Office> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(dataList)
                            officeDataValueListener.valueChange(dataList)
                        } else {

                            liveData.postValue(null)
                            officeDataValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        Log.e("repo", "repos ${e.message}")
                        liveData.postValue(null)
                        officeDataValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    Log.e("repo", "repos ${response}")
                    liveData.postValue(null)
                    officeDataValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                Log.e("repo", "onFailure ${t.message}")
                liveData.postValue(null)
                officeDataValueListener.valueChange(null)
            }

        })
        return liveData
    }


    fun getOffice(
        identity: String
    ): MutableLiveData<List<Office>>? {
        val liveData: MutableLiveData<List<Office>> =
            MutableLiveData<List<Office>>()
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

                        if (code == 200 || code == 201) {
                            val dataJA = jsonObjectParent.getJSONArray("data")
                            val type: Type = object : TypeToken<List<Office?>?>() {}.type
                            val dataList: List<Office> =
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


    fun getDesignationData(
        identity: String,
        commonDataValueListener: CommonDataValueListener
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

                        if (code == 200 || code == 201) {
                            val jsonObj = jsonObjectParent.getJSONObject("data")
                            val dataJA = jsonObj.getJSONArray("officehasdesgnations")
                            var list = arrayListOf<SpinnerDataModel>()
                            var i = 0
                            while (i < dataJA.length()) {
                                var dataObj = dataJA.getJSONObject(i).getJSONObject("designation")
                                var spinnerDataModel = Gson().fromJson(
                                    dataObj.toString(),
                                    SpinnerDataModel::class.java
                                )
                                list.add(spinnerDataModel)
                                i++
                            }
                            var dataList = listOf<SpinnerDataModel>()
                            dataList = dataList + list
                            liveData.postValue(dataList)
                            commonDataValueListener.valueChange(dataList)
                        } else {
                            liveData.postValue(null)
                            commonDataValueListener.valueChange(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        commonDataValueListener.valueChange(null)
                    }
                } else {
                    // liveData.postValue(ErrorUtils2.parseError(response))
                    liveData.postValue(null)
                    commonDataValueListener.valueChange(null)
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                commonDataValueListener.valueChange(null)
            }

        })
        return liveData
    }


    fun getDesignationData(
        identity: String
    ): MutableLiveData<List<SpinnerDataModel>>? {
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

                        if (code == 200 || code == 201) {
                            val jsonObj = jsonObjectParent.getJSONObject("data")
                            val dataJA = jsonObj.getJSONArray("officehasdesgnations")
                            var list = arrayListOf<SpinnerDataModel>()
                            var i = 0
                            while (i < dataJA.length()) {
                                var dataObj = dataJA.getJSONObject(i).getJSONObject("designation")
                                var spinnerDataModel = Gson().fromJson(
                                    dataObj.toString(),
                                    SpinnerDataModel::class.java
                                )
                                list.add(spinnerDataModel)
                                i++
                            }
                            var dataList = listOf<SpinnerDataModel>()
                            dataList = dataList + list
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