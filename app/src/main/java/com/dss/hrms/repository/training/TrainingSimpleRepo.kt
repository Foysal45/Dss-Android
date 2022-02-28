package com.dss.hrms.repository.training

import android.app.Application
import android.util.Log
import com.btbapp.alquranapp.retrofit.ApiService
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.retrofit.TrainingApiService
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class TrainingSimpleRepo @Inject constructor() {
    @Inject
    lateinit var application: Application

    @Inject
    lateinit var trainingApiService: TrainingApiService

    @Inject
    lateinit var preparence: MySharedPreparence


    suspend fun getHonorariumHeadList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService?.getHonorariumHead(
                    preparence?.getLanguage(),
                    "Bearer ${preparence.getToken()}"
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
                Log.e("response", "Response : ${e.message}")
            }
        }
    }

    suspend fun getExpertiseList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = trainingApiService?.getExpertiseList(
                    preparence?.getLanguage(),
                    "Bearer ${preparence.getToken()}"
                )
                Log.e("response", "Response : ${response?.body()}")

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
                Log.e("response", "Response : ${e.message}")
            }
        }
    }
}