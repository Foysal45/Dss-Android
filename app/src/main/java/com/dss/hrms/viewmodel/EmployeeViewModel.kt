package com.dss.hrms.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.PermissionResponse
import com.dss.hrms.model.ReportResponse
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.repository.EmployeeInfoRepo
import com.dss.hrms.util.Status
import com.dss.hrms.view.report.VacantPositionSummaryValueListener
import com.dss.hrms.view.training.model.BudgetAndSchedule
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var employeeInfoRepo: EmployeeInfoRepo


    private var _userPermission: MutableLiveData<List<Any>> =
        MutableLiveData()
    var userPermission: LiveData<List<Any>> =
        _userPermission
    suspend fun getEmployeeInfo(employeeId: Int?): Flow<Any?>? = flow {
        //   val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        //  viewModelScope.launch(dispatcher.Main) {
        emit(employeeInfoRepo?.getEmployeeInfo(employeeId))
        // }
    }

    fun getUserPermissions(
    ) {
        viewModelScope.launch {

            var response =
                employeeInfoRepo.getUserPermissions()

            if (response is PermissionResponse) {
               // Log.e("employeeviewmodel","response : ${response.data}")
                _userPermission.postValue(response.data)
            } else {
                _userPermission.postValue(null)
            }
        }
    }
    fun getRoleWiseEmployeeInfo(role: String?): MutableLiveData<List<RoleWiseEmployeeResponseClass.RoleWiseEmployee?>> {
        var liveData = MutableLiveData<List<RoleWiseEmployeeResponseClass.RoleWiseEmployee?>>()
        viewModelScope.launch {
            var resonse = employeeInfoRepo.getRoleWiseEmployeeInfo(role)
            if (resonse != null)
                if (resonse is RoleWiseEmployeeResponseClass.RoleWiseEmployeeRes) {
                    liveData.postValue(resonse.data)
                } else {
                    liveData.postValue(null)
                }
        }
        return liveData
    }

    fun getEmployeeList(
        office_id: String?,
        head_office_department_id: String?,
        head_office_section_id: String?,
        head_office_sub_section_id: String?,
        division_id: String?,
        district_id: String?,
        sixteen_category_id: String?,
        designation_id: String?,
        term: String?
    ): MutableLiveData<List<RoleWiseEmployeeResponseClass.RoleWiseEmployee>> {
        var liveData = MutableLiveData<List<RoleWiseEmployeeResponseClass.RoleWiseEmployee>>()
        viewModelScope.launch {
            var resonse = employeeInfoRepo.getEmployeeList(
                office_id,
                head_office_department_id,
                head_office_section_id,
                head_office_sub_section_id,
                division_id,
                district_id,
                sixteen_category_id,
                designation_id,
                term
            )
            if (resonse is RoleWiseEmployeeResponseClass.EmployeeListResponse) {
                liveData.postValue(resonse.data?.data)
            } else {
                liveData.postValue(null)
            }
        }
        return liveData
    }
}