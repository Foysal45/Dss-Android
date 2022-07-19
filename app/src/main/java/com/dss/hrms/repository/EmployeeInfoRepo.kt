package com.dss.hrms.repository

import android.app.Application
import android.util.Log
import com.dss.hrms.retrofit.ApiService
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
        val token = preparence.getToken()
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService?.getEmployeeInfo("Bearer ${token}", employeeId)


                if (response?.body()?.code == 200 || response?.body()?.code == 201) {

                    val obj = response.body()

                    val pedingDataObj: PendingDataModel? =
                        preparence.get(HelperClass.PEDING_DATA)
                    if (pedingDataObj != null && !pedingDataObj.presentAddress.isNullOrEmpty()) {
                        val list: MutableList<Employee.PresentAddresses>? =
                            obj?.data?.presentAddresses?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj.presentAddress!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.PresentAddresses =
                                HelperClass.SavePresentAddresssModel(item)
                            newObj.isPendingData = true
                            list?.add(newObj)
                        }

                        obj?.data?.presentAddresses = list

                    }
                    if (pedingDataObj != null && !pedingDataObj.permanentAddress.isNullOrEmpty()) {

                        val list: MutableList<Employee.PermanentAddresses>? =
                            obj?.data?.permanentAddresses?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj.permanentAddress!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.PermanentAddresses =
                                HelperClass.SavePeramnentAddresssModel(item)
                            newObj.isPendingData = true
                            list?.add(newObj)
                        }

                        obj?.data?.permanentAddresses = list
                    }

                    pedingDataObj?.jobJoiningInformation.let {
                        val list: MutableList<Employee.Jobjoinings>? =
                            obj?.data?.jobjoinings?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj?.jobJoiningInformation!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.Jobjoinings =
                                HelperClass.saveJobJoiningModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }

                        obj?.data?.jobjoinings = list
                    }

                    pedingDataObj?.quotaInformation.let {
                        val list: MutableList<Employee.EmployeeQuotas>? =
                            obj?.data?.employee_quotas?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj?.quotaInformation!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.EmployeeQuotas =
                                HelperClass.saveQuotaModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }

                        obj?.data?.employee_quotas = list
                    }

                    pedingDataObj?.educationQuality.let {
                        val list: MutableList<Employee.EducationalQualifications>? =
                            obj?.data?.educationalQualifications?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj?.educationQuality!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.EducationalQualifications =
                                HelperClass.saveEducationQualityModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.educationalQualifications = list
                    }

                    pedingDataObj?.languageInfo.let {
                        val list: MutableList<Employee.Languages>? =
                            obj?.data?.languages?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj?.languageInfo!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.Languages =
                                HelperClass.saveLanguageModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.languages = list
                    }


                    pedingDataObj?.localTrainig.let {
                        val list: MutableList<Employee.LocalTrainings>? =
                            obj?.data?.local_trainings?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj?.localTrainig!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.LocalTrainings =
                                HelperClass.saveLocalTrainingsModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.local_trainings = list
                    }


                    pedingDataObj?.foreignTraining.let {
                        val list: MutableList<Employee.Foreigntrainings>? =
                            obj?.data?.foreigntrainings?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj?.foreignTraining!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.Foreigntrainings =
                                HelperClass.saveForeignTrainingsModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.foreigntrainings = list
                    }

                    pedingDataObj?.foreignTravel.let {
                        val list: MutableList<Employee.ForeignTravels>? =
                            obj?.data?.foreign_travels?.toMutableList()

                        // concant the model here
                        for (item in pedingDataObj?.foreignTravel!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.ForeignTravels =
                                HelperClass.saveForeignTravelModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.foreign_travels = list
                    }
                    pedingDataObj?.officialResidentialInfo.let {
                        val list: MutableList<Employee.OfficialResidentials>? =
                            obj?.data?.official_residentials?.toMutableList()


                        for (item in pedingDataObj?.officialResidentialInfo!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.OfficialResidentials =
                                HelperClass.saveOfficialResidentialModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.official_residentials = list
                    }

                    pedingDataObj?.additionalProfessionalQualifications.let {
                        val list: MutableList<Employee.AdditionalQualifications>? =
                            obj?.data?.additional_qualifications?.toMutableList()


                        for (item in pedingDataObj?.additionalProfessionalQualifications!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.AdditionalQualifications =
                                HelperClass.saveAddiiontalQualificationModel(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.additional_qualifications = list
                    }

                    pedingDataObj?.publications.let {
                        val list: MutableList<Employee.Publications>? =
                            obj?.data?.publications?.toMutableList()

                        for (item in pedingDataObj?.publications!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.Publications =
                                HelperClass.savePublication(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.publications = list
                    }

                    pedingDataObj?.honoursAndAward.let {
                        val list: MutableList<Employee.HonoursAwards>? =
                            obj?.data?.honours_awards?.toMutableList()

                        for (item in pedingDataObj?.honoursAndAward!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.HonoursAwards =
                                HelperClass.saveHonoursAndAward(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.honours_awards = list
                    }


                    pedingDataObj?.desciplinaryAction.let {
                        val list: MutableList<Employee.DisciplinaryAction>? =
                            obj?.data?.disciplinaryActions?.toMutableList()

                        for (item in pedingDataObj?.desciplinaryAction!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.DisciplinaryAction =
                                HelperClass.savDesciplinary(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.disciplinaryActions = list
                    }

                    pedingDataObj?.reference.let {
                        val list: MutableList<Employee.References>? =
                            obj?.data?.references?.toMutableList()

                        for (item in pedingDataObj?.reference!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.References =
                                HelperClass.saveReference(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.references = list
                    }

                    pedingDataObj?.nomineeInfo.let {
                        val list: MutableList<Employee.Nominee>? =
                            obj?.data?.nominees?.toMutableList()

                        for (item in pedingDataObj?.nomineeInfo!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.Nominee =
                                HelperClass.saveNominne(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.nominees = list
                    }


                    pedingDataObj?.childrenInfo.let {
                        val list: MutableList<Employee.Childs>? =
                            obj?.data?.childs?.toMutableList()

                        for (item in pedingDataObj?.childrenInfo!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.Childs =
                                HelperClass.saveChild(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.childs = list
                    }

                    pedingDataObj?.spouse.let {
                        val list: MutableList<Employee.Spouses>? =
                            obj?.data?.spouses?.toMutableList()

                        for (item in pedingDataObj?.spouse!!) {
                            item.data?.isPendingData = true
                            val newObj: Employee.Spouses =
                                HelperClass.saveSpouse(item)
                            newObj.isPendingData = true

                            list?.add(newObj)
                        }
                        obj?.data?.spouses = list
                    }
                    /*
                     pending employee has only one pending object no need to create more

                     */


                    employeeProfileData.employee = obj?.data as Employee

                    obj.data as Employee
                    Log.d(
                        "presesentAddress",
                        "" + employeeProfileData.employee!!.jobjoinings?.size
                    )

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
                    Log.d("PENDING", "getPendingData: ${msg?.message} ")
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

    suspend fun getCommonDataDropdown(): Any? {
        val token = preparence?.getToken()
        return withContext(Dispatchers.Default) {
            try {
                val response =
                    apiService?.getCommonDataDropDown(
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