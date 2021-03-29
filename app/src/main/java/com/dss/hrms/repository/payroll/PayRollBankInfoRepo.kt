package com.dss.hrms.repository.payroll

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.PayrollApiService
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.lang.Exception
import javax.inject.Inject

class PayRollBankInfoRepo @Inject constructor() {

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var payrollApiService: PayrollApiService

    @Inject
    lateinit var application: Application


    suspend fun getBankList(employeeId: Int?): Any? {

        return withContext(Dispatchers.IO) {
            try {
                var response = payrollApiService?.bankInfo(
                    preparence.getLanguage(), "Bearer ${preparence.getToken()}",
                    employeeId
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
                e.message
                Log.e("response", "Response : ${e.message}")
            }
        }
    }


    suspend fun createBankInfo(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>
    ): MutableLiveData<Any> {

        withContext(Dispatchers.IO) {
            try {
                var response = payrollApiService.createBankInformation(
                    preparence.getLanguage(),
                    "Bearer ${preparence.getToken()!!}",
                    map
                )

                Log.e(
                    "createBankinformation",
                    "createBankinformation : ${response?.raw()} status : ${response?.headers()} "
                )
                if (response == null) {
                    liveData.postValue(null)
                } else {
                    if (response.isSuccessful) {
                        try {
                            if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                                liveData?.postValue("Created")
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

            }

        }

        return liveData

    }

    suspend fun updateBankInfo(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>,
        id: Int?
    ): MutableLiveData<Any> {

        withContext(Dispatchers.IO) {
            try {
                var response = payrollApiService.updateBankInformation(
                    preparence.getLanguage(),
                    "Bearer ${preparence.getToken()!!}",
                    id,
                    map
                )

                Log.e(
                    "updateBankinformation",
                    "updateBankinformation : ${response?.raw()} status : ${response?.headers()} "
                )
                if (response == null) {
                    liveData.postValue(null)
                } else {
                    if (response.isSuccessful) {
                        try {
                            if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                                liveData?.postValue("Updated")
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

            }

        }

        return liveData

    }

}