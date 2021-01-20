package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.btbapp.alquranapp.retrofit.ApiService
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.namaztime.namaztime.database.MySharedPreparence
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Part
import javax.inject.Inject


class EmployeeInfoEditCreateRepo @Inject constructor() {
    @Inject
    lateinit var application: Application

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var preparence: MySharedPreparence


    fun editJobjoiningInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateJobjoiningInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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


    fun updateQuotaInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateQuotaInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        //    Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateBasicInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateBasicInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //  Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updatePresentInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updatePresentInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //  Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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


    fun addPresentInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addPresentInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        //    Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    // Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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


    fun updatePermanentInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updatePermanetInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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


    fun addPermanentInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addPermanentInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        //   Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateEducationQualification(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateEducationQualificationInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    // Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun addEducationQualificationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addEducationQualificationInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    // Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
                    ///  val jsonObjectParent = JSONObject(Gson().toJson(response.errorBody()))
                    // Log.e("response", "response.body() : " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                //  Log.e("response", "onFailure : " + t.message)
            }

        })
        return liveData
    }

    fun addSpouseInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addSpouseInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        //   Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            //   Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        // Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
                    ///  val jsonObjectParent = JSONObject(Gson().toJson(response.errorBody()))
                    // Log.e("response", "response.body() : " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                //  Log.e("response", "onFailure : " + t.message)
            }

        })
        return liveData
    }

    fun updateSpouseInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateSpouseInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //     Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun addChildInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addChildInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateChildInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateChildInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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


    fun addLanguageInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addLanguageInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //    Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateLanguageInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateLanguageInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //  Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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


    fun addLocalTrainingInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addLocalTrainingInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateLocalTrainingInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateLocalTrainingInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //      Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun addForeignTrainingInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addForeignTrainingInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //  Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateForeignTrainingInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateForeignTrainingInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    // Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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


    fun addPublicationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addPublicationInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updatePublicationInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))

        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updatePublicationInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //    Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun addReferenceInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addReferenceInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateReferenceInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateReferenceInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun addOfficialResidentialInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addOfficialResidentialInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //  Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateOfficialResidentialInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateOfficialResidentialInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun addForeignTravellInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addForeignTravelInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    // Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateForeignTravelInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateForeignTravelInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //  Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun addHonoursAwardInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addHonoursAwardInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateHonoursAwardInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateHonoursAwardInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //    Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun addAdditionalQualificationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.addAdditionalQualificationInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            arr
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Added")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //   Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun uploadProfilePic(
        file: MultipartBody.Part?, picName: String, profile_pic: RequestBody
    ): MutableLiveData<Any> {
//        var arr = arrayListOf<MultipartBody.Part?>(filenames)
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.uploadProfilePic(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            profile_pic,
            file

        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201) {
                            Log.e("response", "response : " + jsonObjectParent)
                            var array = jsonObjectParent.getJSONArray("data")
                            if (array != null && array.length() > 0) {
                                var obj = array.getJSONObject(0)
                                liveData.postValue(obj.getString("path"))
                            } else {
                                liveData.postValue(null)
                            }
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(null)
                    //    Log.e("response", "response.body() : " + response)
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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

    fun updateAdditionalQualificationInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any> {
        Log.e("id", "id : " + id)
        Log.e("test", "test map data " + Gson().toJson(map))
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        var preparence: MySharedPreparence? = application?.let { MySharedPreparence(it) }
        val call: Call<Any?>? = apiService?.updateAdditionalQualificationInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )
        call?.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

                if (response.isSuccessful) {
                    try {
                        val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                        val code: Int = jsonObjectParent.getInt("code")
                        val status = jsonObjectParent.getString("status")
                        Log.e("response", "response : " + jsonObjectParent)
                        if (code == 200 || code == 201
                        ) {
                            Log.e("response", "response : " + jsonObjectParent)

                            liveData.postValue("Updated")
                        } else {
                            liveData.postValue(null)
                        }
                    } catch (e: JSONException) {
                        liveData.postValue(null)
                        Log.e("response", "JSONException : " + e.message)
                    }
                } else {
                    liveData.postValue(ErrorUtils2.parseError(response))
                    //    Log.e("response", "response.body() : " + Gson().toJson(response.raw()))
                    //  liveData.postValue(ErrorUtils2.parseError(response))
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


}