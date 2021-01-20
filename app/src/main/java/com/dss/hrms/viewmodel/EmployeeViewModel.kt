package com.dss.hrms.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.repository.EmployeeInfoRepo
import com.dss.hrms.util.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var employeeInfoRepo: EmployeeInfoRepo


    suspend fun getEmployeeInfo(employeeId: Int?): Flow<Any?>? = flow {
        //   val liveData: MutableLiveData<Any> = MutableLiveData<Any>()
        //  viewModelScope.launch(dispatcher.Main) {
        emit( employeeInfoRepo?.getEmployeeInfo(employeeId))
        // }
    }

}