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

class TrainingRepo @Inject constructor() {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var trainingApiService: TrainingApiService

    @Inject
    lateinit var preparence: MySharedPreparence

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


    suspend fun updateResourcePerson(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>,
        resourcePersonId: Int
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService.updateResourcePerson(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    resourcePersonId,
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