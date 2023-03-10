package com.dss.hrms.repository.training

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.retrofit.TrainingApiService
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class TrainingRepo @Inject constructor() {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var trainingApiService: TrainingApiService

    @Inject
    lateinit var preparence: MySharedPreparence

    suspend fun getTrainingModules(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.getModules(
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


    suspend fun getTrainingCourses(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.getCourses(
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
    suspend fun getTrainingDashboard(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                val response = trainingApiService.getTrainingDashboard(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}"
                )

                Log.e("dashboard "," dashboard : ${response.body()?.code}")
//                Log.e("dashboard "," dashboard : ${response.body()?.data}")
                if (response.body()?.code == 200 || response.body()?.code == 201)
                {
                    response.body()
                }
                else {
                    response.body()
                }

            } catch (e: Exception) {

                Log.e("repo", "response:...................... ${e.message} ..................header ${e}")
                null
            }

        }

    }
 suspend fun searchResourcePersonList(map: HashMap<Any, Any?>): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.searchResourcePerson(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map["person_name"]?.let { it as String },
                    map["short_name"]?.let { it as String },
                    map["designation_id"]?.let { it as Int },
                    map["first_email"]?.let { it as String },
                    map["first_mobile"]?.let { it as String }

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


    suspend fun addResourcePerson(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                val response = trainingApiService.addResourcePerson(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map
                )
                Log.e(
                    "repo",
                    "response: ${response} response: ${response.raw()} header ${response.body()}"
                )
                if (response.isSuccessful) {
                    if (response.body()?.code == 200 || response.body()?.code == 201) {
                        liveData.postValue("Added")
                    } else {
                        liveData.postValue("Error");
                    }
                } else {
                    response?.let {

                        val apiError = ErrorUtils2.parseError(response)
                        Log.e(
                            "repo",
                            "apiError apiError : ${apiError.getError().get(0).getField()}}"
                        )
                        liveData.postValue(apiError)
                    }

                }

            } catch (e: Exception) {
                liveData.postValue(null)
            }
        }
        return liveData
    }

    suspend fun updateResourcePerson(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>,
        resourcePersonId: Int
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                val response = trainingApiService.updateResourcePerson(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    resourcePersonId,
                    map
                )
                Log.e(
                    "repo",
                    "response: ${response} response: ${response.raw()} header ${response.body()}"
                )
                if (response.isSuccessful) {
                    if (response.body()?.code == 200 || response.body()?.code == 201) {
                        liveData.postValue("Updated")
                    } else {
                        liveData.postValue("Error");
                    }
                } else {
                    response.let {

                        val apiError = ErrorUtils2.parseError(response)
                        Log.e(
                            "repo",
                            "apiError apiError : ${apiError.getError().get(0).getField()}}"
                        )
                        liveData.postValue(apiError)
                    }

                }

            } catch (e: Exception) {
                liveData.postValue(null)
            }
        }
        return liveData
    }
}