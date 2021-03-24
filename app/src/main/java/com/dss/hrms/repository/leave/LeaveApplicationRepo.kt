package com.dss.hrms.repository.leave

import android.util.Log
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.LeaveApiService
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

                Log.e("leaveapplication","leave application ..................... ${response?.body()?.data?.row}")

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
}