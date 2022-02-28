package com.dss.hrms.view.leave.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.repository.leave.LeaveApplicationRepo
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.training.model.HonorariumHead
import com.dss.hrms.view.training.model.HonorariumHeadResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class LeaveApplicationViewmodel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var leaveApplicationRepo: LeaveApplicationRepo


    var _leaveApplication: MutableLiveData<List<LeaveApplicationApiResponse.LeaveApplication>> =
        MutableLiveData()
    var leaveApplication: LiveData<List<LeaveApplicationApiResponse.LeaveApplication>> =
        _leaveApplication


    fun getLeaveApplication(employeeId: String?) {
        viewModelScope.launch {
            var response = leaveApplicationRepo.getLeaveApplication(employeeId)

            if (response is LeaveApplicationApiResponse.LeaveApplicationResponse) {
                _leaveApplication.postValue(response.data)
            } else {
                _leaveApplication.postValue(null)
            }
        }
    }

    fun createLeaveApplication(map: HashMap<Any, Any?>): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = leaveApplicationRepo.createLeaveApplication(map, liveData)
        }
        return liveData
    }

    fun updateLeaveApplication(applicationId: Int?, map: HashMap<Any, Any?>): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = leaveApplicationRepo.updateLeaveApplication(map, liveData, applicationId)
        }
        return liveData
    }

    fun getLeavePolicyList(): MutableLiveData<List<LeaveApplicationApiResponse.LeavePolicy>> {
        var liveData = MutableLiveData<List<LeaveApplicationApiResponse.LeavePolicy>>()
        viewModelScope.launch {
            var response = leaveApplicationRepo.getLeavePolicyList()
            if (response is LeaveApplicationApiResponse.LeavePolicyResponse) {
                liveData.postValue(response.data)
            } else {
                liveData.postValue(null)
            }
        }
        return liveData
    }

}