package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import com.btbapp.alquranapp.retrofit.ApiService
import com.dss.hrms.di.mainScope.EmployeePendingData
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.model.pendingDataModel.PendingDataModel
import com.dss.hrms.retrofit.NotificationApiService
import com.dss.hrms.util.HelperClass
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
    lateinit var notificationApiService: NotificationApiService

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    @Inject
    lateinit var employeePendingData: EmployeePendingData


    suspend fun getAllNotifications(platform: String?, limit: Int?, page: Int?): Any? {
        val token = preparence?.getToken()
        return withContext(Dispatchers.IO) {
            try {
                val response = notificationApiService?.getAllNotifications(
                    preparence.getLanguage()!!,
                    "Bearer ${token}",
                    platform,
                    page,
                    limit
                )

                Log.e(
                    "messagebody",
                    "message ......................" + response?.message() + "             headers  " + response?.headers()
                )
                Log.e(
                    "messagebody",
                    "message ......................" + response?.message() + "             headers  " + response?.headers()
                )
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                    response?.body()
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

    suspend fun getEmployeeInfo(employeeId: Int?): Any? {
        val token = preparence?.getToken()
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService?.getEmployeeInfo("Bearer ${token}", employeeId)


                if (response?.body()?.code == 200 || response?.body()?.code == 201) {

                    val obj = response.body()

                    val pedingDataObj: PendingDataModel? =
                        preparence.get(HelperClass.PEDING_DATA)
                    if (pedingDataObj != null && !pedingDataObj.presentAddress.isNullOrEmpty()) {

                        val list:MutableList<Employee.PresentAddresses>? =
                            obj?.data?.presentAddresses?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj.presentAddress!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.PresentAddresses? = HelperClass.SavePresentAddresssModel(item)
                            if (newObj != null) {
                                newObj.isPendingData = true
                                list?.add(newObj)
                            }
                        }

                        obj?.data?.presentAddresses = list

                    }

                    employeeProfileData.employee =obj?.data as Employee

                    obj.data as Employee
                     Log.d("response  tariqul ", ""+ employeeProfileData.employee!!.presentAddresses?.size)

                } else response?.let {
                    var apiError = ErrorUtils2.parseError(
                        it
                    )
                    apiError
                }
            } catch (e: Exception) {
                null
                Log.e(
                    "employee",
                    "...................Exception................${e.message}..........."
                )

            }
        }
    }


    suspend fun getPendingData(employeeId: Int?): Any? {
        val token = preparence?.getToken()
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService?.getPendingData("Bearer ${token}", employeeId)
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                    employeePendingData.PendingData = response?.body()?.data as PendingDataModel
                    val msg = response?.body()
                    Log.d("PENDING", "getPendingData: ${ msg?.message } ")
                    response?.body()?.data as PendingDataModel

                } else response?.let {
                    var apiError = ErrorUtils2.parseError(
                        it
                    )
                    apiError
                }
            } catch (e: Exception) {
                null
                Log.e(
                    "employee",
                    "...................Exception................${e.message}..........."
                )

            }
        }
    }


    suspend fun getUserPermissions(): Any? {
        val token = preparence?.getToken()
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    apiService?.getUserPermissions(
                        preparence.getLanguage()!!,
                        "Bearer ${token}"
                    )
//                Log.e(
//                    "employeerepository",
//                    "........................................response : ${response?.body()}"
//                )
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {


                    response?.body()
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