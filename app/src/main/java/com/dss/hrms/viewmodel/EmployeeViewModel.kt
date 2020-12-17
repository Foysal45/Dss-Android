package com.dss.hrms.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dss.hrms.model.Employee
import com.dss.hrms.repository.EmployeeInfoRepo

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private var employeeInfoRepo: EmployeeInfoRepo? = null

    init {
        employeeInfoRepo = EmployeeInfoRepo(application)
    }

    fun getEmployeeInfo(employeeId: Int?): MutableLiveData<Any>? {
        return employeeInfoRepo?.getEmployeeInfo(employeeId)
    }
}