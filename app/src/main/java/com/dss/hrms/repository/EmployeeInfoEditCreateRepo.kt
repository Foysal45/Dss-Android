package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.btbapp.alquranapp.retrofit.ApiService
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.error.ErrorUtils2
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namaztime.namaztime.database.MySharedPreparence
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
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
        apiService?.updateJobjoiningInfo(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Response<Any>?> {
                override fun onComplete() {
                    Log.e("TAG", "observable list - onComplete")
                    //   liveData.postValue(null)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e("TAG", "observable list - onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG", "observable list - onError ${e.message}")
                    liveData.postValue(null)
                }

                override fun onNext(response: Response<Any>) {
                    Log.e("response", "response : ${response.headers()} and ${response.raw()}")

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
                    }
                }
            })
        return liveData
    }


    fun updateQuotaInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
        val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        apiService?.updateQuotaInfo1(
            preparence?.getLanguage()!!,
            "Bearer ${preparence?.getToken()}",
            id,
            map
        )?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Response<Any>?> {
                override fun onComplete() {
                    Log.e("TAG", "observable list - onComplete")
                    // liveData.postValue(null)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e("TAG", "observable list - onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG", "observable list - onError ${e.message}")
                    liveData.postValue(null)
                }

                override fun onNext(response: Response<Any>) {
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
                    }
                }
            })
        return liveData
    }

    fun updateQuotaInfo1(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any> {
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
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.postValue(null)
                Log.e("response", "onFailure : " + t.message)
            }

        })
        return liveData
    }

    suspend fun updateBasicInfo(
        id: Int?,
        map: HashMap<Any, Any?>?,
        liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateBasicInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                                Log.e("response", "JSONException : " + e.message)
                            }
                        } else {
                            var value = ErrorUtils2.parseError(response)
                            Log.e("response", "response : " + Gson().toJson(value.getError()))
                            liveData?.postValue(value)
                        }
                    }
            }
        }
        return liveData
    }


    suspend fun updateNomineeInfo(
        map: HashMap<Any, Any?>?,
        liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateNomineeInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                val message = jsonObjectParent.getString("message")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue(message)
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                                Log.e("response", "JSONException : " + e.message)
                            }
                        } else {
                            var value = ErrorUtils2.parseError(response)
                            Log.e("response", "response : " + Gson().toJson(value.getError()))
                            liveData?.postValue(value)
                        }
                    }
            }
        }
        return liveData
    }


    suspend fun updatePresentInfo(
        id: Int?,
        map: HashMap<Any, Any?>?,
        liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {

        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updatePresentInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun addPresentInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addPresentInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun updatePermanentInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updatePermanetInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            Log.e("response", "response  error   response.message()  ${response.message()}  :  response.headers() ${ response.headers()}" + response.message())
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }


    suspend fun addPermanentInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addPermanentInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            Log.e("response", "response : " + response)
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun updateEducationQualification(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateEducationQualificationInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun addEducationQualificationInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addEducationQualificationInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun addSpouseInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addSpouseInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun updateSpouseInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateSpouseInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun addChildInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addChildInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData

    }

    suspend fun updateChildInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateChildInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }


    suspend fun addLanguageInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addLanguageInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun updateLanguageInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateLanguageInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }


    suspend fun addLocalTrainingInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addLocalTrainingInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData

    }

    suspend fun updateLocalTrainingInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateLocalTrainingInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData


    }

    suspend fun addForeignTrainingInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addForeignTrainingInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun updateForeignTrainingInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateForeignTrainingInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }


    suspend fun addPublicationInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addPublicationInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun updatePublicationInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updatePublicationInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData

    }

    suspend fun addReferenceInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addReferenceInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData

    }

    suspend fun updateReferenceInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateReferenceInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun addOfficialResidentialInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addOfficialResidentialInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData

    }

    suspend fun updateOfficialResidentialInfo(
        id: Int?,
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateOfficialResidentialInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun addForeignTravellInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addForeignTravelInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun updateForeignTravelInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateForeignTravelInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun addHonoursAwardInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addHonoursAwardInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun updateHonoursAwardInfo(
        id: Int?, map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateHonoursAwardInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    suspend fun addAdditionalQualificationInfo(
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.addAdditionalQualificationInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    arr
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Added")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
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

    suspend fun updateAdditionalQualificationInfo(
        id: Int?,
        map: HashMap<Any, Any?>?, liveData: MutableLiveData<Any>?
    ): MutableLiveData<Any>? {
        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        withContext(Dispatchers.IO) {
            flowOf(
                apiService?.updateAdditionalQualificationInfo(
                    preparence?.getLanguage()!!,
                    "Bearer ${preparence?.getToken()}",
                    id,
                    map
                )
            ).catch { throwable ->
                liveData?.postValue(null)
            }.collect { response ->
                if (response == null) {
                    liveData?.postValue(null)
                } else
                    response?.let {
                        if (response?.isSuccessful!!) {
                            try {
                                val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                                val code: Int = jsonObjectParent.getInt("code")
                                val status = jsonObjectParent.getString("status")
                                Log.e("response", "response : " + jsonObjectParent)
                                if (code == 200 || code == 201
                                ) {
                                    Log.e("response", "response : " + jsonObjectParent)
                                    liveData?.postValue("Updated")
                                } else {
                                    liveData?.postValue(null)
                                }
                            } catch (e: JSONException) {
                                liveData?.postValue(null)
                            }
                        } else {
                            liveData?.postValue(ErrorUtils2.parseError(response))
                        }
                    }
            }
        }
        return liveData
    }

    fun getAllHrTraining(
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
                            val hrTrainingList: List<SpinnerDataModel> =
                                Gson().fromJson(dataJA.toString(), type)
                            liveData.postValue(hrTrainingList)
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