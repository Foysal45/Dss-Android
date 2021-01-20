package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.btbapp.alquranapp.retrofit.ApiService
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.login.ResetPassword
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class EmployeeInfoRepo @Inject constructor() {
    @Inject
    lateinit var application: Application

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData


    suspend fun getEmployeeInfo(employeeId: Int?): Any? {
        val token = preparence?.getToken()
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService?.getEmployeeInfo("Bearer ${token}", employeeId)
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                    employeeProfileData.employee = response?.body()?.data as Employee
                    response?.body()?.data as Employee
                } else response?.let {
                    var apiError = ErrorUtils2.parseError(
                        it
                    )
                    apiError
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}