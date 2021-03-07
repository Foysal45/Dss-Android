package com.dss.hrms.view.training.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.repository.training.BudgetAndScheduleRepo
import com.dss.hrms.view.training.model.BudgetAndSchedule
import kotlinx.coroutines.launch
import javax.inject.Inject

class BudgetAndScheduleViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var budgetAndScheduleRepo: BudgetAndScheduleRepo

    private var _batchSchedule = MutableLiveData<List<BudgetAndSchedule.BatchSchedule?>>()
    var batchSchedule: LiveData<List<BudgetAndSchedule.BatchSchedule?>> = _batchSchedule

    private var _courseSchedule = MutableLiveData<List<BudgetAndSchedule.CourseSchedule?>>()
    var courseSchedule: LiveData<List<BudgetAndSchedule.CourseSchedule?>> = _courseSchedule


    fun getCourseSchedule() {
        viewModelScope.launch {
            var response = budgetAndScheduleRepo.getCourseSchedule()
            if (response is BudgetAndSchedule.CourseScheduleResponse) {
                _courseSchedule.postValue(response.data?.data)
            } else {
                _courseSchedule.postValue(null)
            }
        }

    }

    fun getCourseScheduleList(): MutableLiveData<List<BudgetAndSchedule.CourseScheduleList>?> {
        var liveData = MutableLiveData<List<BudgetAndSchedule.CourseScheduleList>?>()
        viewModelScope.launch {
            var response = budgetAndScheduleRepo.getCourseScheduleList()
            if (response is BudgetAndSchedule.CourseScheduleListResponse) {
                liveData.postValue(response?.data)
            } else {
                liveData.postValue(null)
            }
        }
        return liveData
    }

    fun addCourseSchedule(map: HashMap<Any, Any?>): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = budgetAndScheduleRepo.addCourseSchedule(map, liveData)
        }
        return liveData
    }

    fun updateCourseSchedule(map: HashMap<Any, Any?>, id: Int): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = budgetAndScheduleRepo.updateCourseSchedule(map, liveData, id)
        }
        return liveData
    }

    fun getBatchSchedule() {
        viewModelScope.launch {
            var response = budgetAndScheduleRepo.getBatchScheduleList()
            if (response is BudgetAndSchedule.BatchScheduleResponse) {
                _batchSchedule.postValue(response.data?.data)
            } else {
                _batchSchedule.postValue(null)
            }
        }

    }

    fun updateBatchSchedule(map: HashMap<Any, Any?>, id: Int): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = budgetAndScheduleRepo.updateBatchSchedule(map, liveData, id)
        }
        return liveData
    }

    fun addBatchSchedule(map: HashMap<Any, Any?>): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = budgetAndScheduleRepo.addBatchSchedule(map, liveData)
        }
        return liveData
    }
}