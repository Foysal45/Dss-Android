package com.dss.hrms.repository.message

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.MessagingApiService
import com.dss.hrms.retrofit.TrainingApiService
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

class MessageRepo @Inject constructor() {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var messageApiService: MessagingApiService

    @Inject
    lateinit var preparence: MySharedPreparence


    suspend fun sendEmail(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                val response = messageApiService.sendEmail(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map
                )
                Log.e(
                    "messagingApiService",
                    "messagingApiService : ${response?.headers()} status : ${response?.raw()} "
                )
                if (response == null) {
                    liveData.postValue(null)
                } else {
                    if (response.isSuccessful) {
                        try {
                            if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                                liveData?.postValue("Sent")
                            } else {
                                liveData?.postValue(null)
                            }
                        } catch (e: JSONException) {
                            Log.e("repo", "excep " + e.message)
                            liveData?.postValue(null)
                        }
                    } else {
                        response?.let {

                            var apiError = ErrorUtils2.parseError(response)
                            Log.e(
                                "repo",
                                "apiError apiError : ${apiError.getError().get(0).getField()}}"
                            )
                            liveData.postValue(apiError)
                        }
                    }
                }
            } catch (e: Exception) {
                liveData?.postValue(null)
            }
        }
        return liveData
    }


    suspend fun sendSms(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                val response = messageApiService.sendSms(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map
                )
                Log.e(
                    "messagingApiService",
                    "messagingApiService : ${response?.body()?.code} status : ${response?.body()?.status} "
                )
                if (response == null) {
                    liveData.postValue(null)
                } else {
                    if (response.isSuccessful) {
                        try {
                            if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                                liveData?.postValue("Sent")
                            } else {
                                liveData?.postValue(null)
                            }
                        } catch (e: JSONException) {
                            Log.e("repo", "excep " + e.message)
                            liveData?.postValue(null)
                        }
                    } else {
                        response?.let {

                            var apiError = ErrorUtils2.parseError(response)
                            Log.e(
                                "repo",
                                "apiError apiError : ${apiError.getError().get(0).getField()}}"
                            )
                            liveData.postValue(apiError)
                        }
                    }
                }
            } catch (e: Exception) {
                liveData?.postValue(null)
            }
        }
        return liveData
    }
}