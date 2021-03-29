package com.dss.hrms.repository.leave

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.LeaveApiService
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.lang.Exception
import javax.inject.Inject

class LeaveApplicationRepo @Inject constructor() {
    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var leaveApiService: LeaveApiService


    suspend fun getLeaveApplication(employeeId: String?): Any? {

        return withContext(Dispatchers.IO) {

            try {
                var response = leaveApiService.leaveApplicationSearch(
                    preparence.getLanguage(),
                    "Bearer ${preparence.getToken()}",
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

    suspend fun getLeaveSummary(employeeId: String?): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = leaveApiService.leaveSummary(
                    preparence.getLanguage(),
                    "Bearer ${preparence.getToken()}",
                    employeeId
                )

                Log.e(
                    "leaveapplication",
                    "leave application ..................... ${response?.body()?.data?.row}"
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

    suspend fun getLeavePolicyList(): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = leaveApiService?.leavePolicy(
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

    suspend fun createLeaveApplication(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                val response = leaveApiService.createLeaveApplication(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map
                )
                Log.e(
                    "createLeaveApplication",
                    "createLeaveApplication : ${response?.raw()} status : ${response?.headers()} "
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
                liveData?.postValue(null)
            }
        }
        return liveData
    }

    suspend fun updateLeaveApplication(
        map: HashMap<Any, Any?>,
        liveData: MutableLiveData<Any>,
        leave_applicationId: Int?
    ): MutableLiveData<Any> {
        withContext(Dispatchers.IO) {
            try {
                val response = leaveApiService.updateLeaveApplication(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    leave_applicationId,
                    map
                )
                Log.e(
                    "createLeaveApplication",
                    "cupdateLeaveApplication : ${response?.raw()} status : ${response?.headers()} "
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
                liveData?.postValue(null)
            }
        }
        return liveData
    }
}