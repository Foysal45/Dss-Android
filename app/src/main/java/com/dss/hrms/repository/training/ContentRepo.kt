package com.dss.hrms.repository.training

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.TrainingApiService
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

class ContentRepo @Inject constructor() {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var trainingApiService: TrainingApiService

    @Inject
    lateinit var preparence: MySharedPreparence


    suspend fun getCategoryList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                val response = trainingApiService.category(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}"
                )
                Log.e(
                    "trainingrepo",
                    "repo : ${response?.body()?.code} status : ${response?.body()?.status} "
                )
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                    response.body()
                } else {
                    response?.let {
                        var apiError = ErrorUtils2.parseError(
                            it
                        )
                        apiError
                    }
                }
            } catch (e: Exception) {
                null
            }
        }
    }


    suspend fun updateCategory(
        map: HashMap<Any, Any>,
        liveData: MutableLiveData<Any>,
        categoryId: Int
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.updateCategory(
                    preparence.getLanguage(),
                    "Bearer ${preparence?.getToken()}",
                    categoryId,
                    map
                )
                Log.e("repo", "response: ${response}" )
                if (response == null) {
                    liveData.postValue(null)
                } else {
                    if (response.isSuccessful) {
                        try {
                            val jsonObjectParent = JSONObject(Gson().toJson(response.body()))
                            val code: Int = jsonObjectParent.getInt("code")
                            Log.e("response", "response : " + jsonObjectParent)
                            if (code == 200 || code == 201
                            ) {
                                Log.e("response", "response : " + jsonObjectParent)
                                liveData?.postValue("Updated")
                            } else {
                                liveData?.postValue(null)
                            }
                        } catch (e: JSONException) {
                            Log.e("repo","excep "+e.message)
                            liveData?.postValue(null)
                        }
                    } else {
                        liveData.postValue(ErrorUtils2.parseError(response))

                    }
                }
            } catch (e: Exception) {
                Log.e("repo","excep "+e.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    suspend fun getContentList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    trainingApiService.content(
                        preparence.getLanguage()!!,
                        "Bearer ${preparence.getToken()!!}"
                    )
                Log.e(
                    "trainingrepo",
                    "repo : ${response?.body()?.code} status : ${response?.body()?.status}"
                )
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                    response?.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e("viewnodel", "Exception " + e.message)
                null
            }

        }

    }

    suspend fun getFaqList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.faq(
                    preparence.getLanguage(),
                    "Bearer ${preparence.getToken()!!}"
                )
                if (response.body()?.code == 200 || response.body()?.code == 201) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getResourcePersonList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.resourcePerson(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}"
                )
                if (response.body()?.code == 200 || response.body()?.code == 201)
                    response.body()
                else
                    null

            } catch (e: Exception) {
                null
            }

        }

    }

}