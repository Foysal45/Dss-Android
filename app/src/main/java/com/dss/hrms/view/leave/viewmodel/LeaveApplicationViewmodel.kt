package com.dss.hrms.view.leave.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.repository.leave.LeaveApplicationRepo
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
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
}