package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import com.btbapp.alquranapp.retrofit.ApiService
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.Office
import com.dss.hrms.model.employeeProfile.Employee
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun getRoleWiseEmployeeInfo(role: String?): Any? {
        return withContext(Dispatchers.IO) {
            try {
                Log.e("response", "response : ${role}")
                var response = apiService.getRoleWiseEmployeeInfo(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    role
                )
                Log.e("response", "response : ")
                if (response?.body()?.code == 200 || response?.body()?.code == 201)
                    response?.body()
                else
                    null
            } catch (e: Exception) {
                Log.e("responseexceotion", "response : exceotion: ${e.message}")
                null
            }

        }

    }


    suspend fun getEmployeeList(
        office_id: String?,
        head_office_department_id: String?,
        head_office_section_id: String?,
        head_office_sub_section_id: String?,
        division_id: String?,
        district_id: String?,
        sixteen_category_id: String?,
        designation_id: String?,
        term: String?
    ): Any? {
        return withContext(Dispatchers.IO) {
            try {
                var response = apiService.getEmployeeListAccordingToQuery(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    head_office_department_id, head_office_section_id, head_office_sub_section_id,
                    division_id, district_id, sixteen_category_id, designation_id, office_id, term
                )
                Log.e("response", "response : ${response?.raw()}")
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                    Log.e("response", "response : ${response.body()?.data}")
                    response?.body()
                } else
                    null
            } catch (e: Exception) {
                Log.e("responseexceotion", "response : exceotion: ${e.message}")
                null
            }

        }

    }
}