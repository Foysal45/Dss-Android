package com.dss.hrms.repository.training

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.TrainingApiService
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class BudgetAndScheduleRepo @Inject constructor() {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var trainingApiService: TrainingApiService

    @Inject
    lateinit var preparence: MySharedPreparence

    suspend fun getBatchScheduleList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.batchSchedule(
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

    suspend fun addBatchSchedule(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.addBatchSchedule(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map
                )
                Log.e("repo", "response: ${response} header ${response.body()}")
                if (response.isSuccessful)
                    if (response.body()?.code == 200 || response.body()?.code == 201) {
                        liveData.postValue("Added")
                    } else {
                        liveData.postValue("Error");
                    }
                else response?.let { liveData.postValue(ErrorUtils2.parseError(response)) }

            } catch (e: Exception) {
                liveData.postValue(null)
            }
        }
        return liveData
    }

    suspend fun updateBatchSchedule(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>,
        batchId: Int
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.updateBatchSchedule(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    batchId,
                    map
                )
                Log.e("repo", "response: ${response} header ${response.body()}")
                if (response.isSuccessful) {
                    if (response.body()?.code == 200 || response.body()?.code == 201) {
                        liveData.postValue("Updated")
                    } else {
                        liveData.postValue("Error");
                    }
                } else {
                    var error =ErrorUtils2.parseError(response)
                    Log.e("response", "Error response ${error.getError().size}")
                    response?.let { liveData.postValue(error) }
                }

            } catch (e: Exception) {
                Log.e("response", "batchschedule response ${e.message}")
                liveData.postValue(null)
            }
        }
        return liveData
    }

    suspend fun getCourseSchedule(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.courseSchedule(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}"
                )
                Log.e("repo", "response: " + response.body())
                if (response.body()?.code == 200 || response.body()?.code == 201)
                    response.body()
                else
                    null
            } catch (e: Exception) {
                null
            }

        }

    }


    suspend fun getCourseList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.courseList(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}"
                )
                Log.e("repo", "response:getCourseScheduleList " + response?.body())
                if (response?.body()?.code == 200 || response?.body()?.code == 201)
                    response?.body()
                else
                    null
            } catch (e: Exception) {
                Log.e("exception", "Exception ; " + e.message)
                null
            }

        }

    }


    suspend fun getCourseScheduleList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.courseScheduleList(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}"
                )
                Log.e("repo", "response:getCourseScheduleList " + response?.body())
                if (response?.body()?.code == 200 || response?.body()?.code == 201)
                    response?.body()
                else
                    null
            } catch (e: Exception) {
                Log.e("exception", "Exception ; " + e.message)
                null
            }

        }

    }

    suspend fun addCourseSchedule(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.addCourseSchedule(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map
                )
                Log.e("repo", "response: ${response} header ${response.headers()}")
                if (response.isSuccessful)
                    if (response.body()?.code == 200 || response.body()?.code == 201) {
                        liveData.postValue("Added")
                    } else {
                        liveData.postValue("Error");
                    }
                else response?.let { liveData.postValue(ErrorUtils2.parseError(response)) }

            } catch (e: Exception) {
                liveData.postValue(null)
            }
        }
        return liveData
    }

    suspend fun updateCourseSchedule(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>,
        courseId: Int
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.updateCourseSchedule(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    courseId,
                    map
                )
                Log.e("repo", "response: ${response} header ${response.headers()}")
                if (response.isSuccessful)
                    if (response.body()?.code == 200 || response.body()?.code == 201) {
                        liveData.postValue("Updated")
                    } else {
                        liveData.postValue("Error");
                    }
                else response?.let { liveData.postValue(ErrorUtils2.parseError(response)) }

            } catch (e: Exception) {
                liveData.postValue(null)
            }
        }
        return liveData
    }
}