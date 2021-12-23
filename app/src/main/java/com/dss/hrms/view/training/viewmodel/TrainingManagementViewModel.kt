package com.dss.hrms.view.training.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.CourseModel
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.repository.training.TrainingRepo
import com.dss.hrms.view.training.`interface`.OnResourcePersonValueListener
import com.dss.hrms.view.training.model.TrainingDashBoard
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrainingManagementViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var trainingRepo: TrainingRepo

    private var _courseResponse = MutableLiveData<List<CourseModel>>()
    private var _resourcePerson = MutableLiveData<List<TrainingResponse.ResourcePerson>>()
    private var _modulesResponse = MutableLiveData<List<TrainingResponse.TrainingModules>>()
    var resourcePerson: LiveData<List<TrainingResponse.ResourcePerson>> = _resourcePerson
    var modules: LiveData<List<TrainingResponse.TrainingModules>> = _modulesResponse
    var courses: LiveData<List<CourseModel>> = _courseResponse
    private var _trainingDashboard = MutableLiveData<TrainingDashBoard.Dashboard?>()
    var trainingDashboard: LiveData<TrainingDashBoard.Dashboard?> = _trainingDashboard


    fun getTrainingDashboard() {
        viewModelScope.launch {
            var response = trainingRepo.getTrainingDashboard()
            if (response is TrainingDashBoard.TrainingDashBoardResponse) {
                _trainingDashboard.postValue(response.data)
            } else {
                _resourcePerson.postValue(null)
                // _resourcePerson.value = null

            }
        }
    }

    fun getResourcePerson() {
        viewModelScope.launch {
            val response = trainingRepo.getResourcePersonList()
            if (response is TrainingResponse.ResourcePersonResponse) {
                _resourcePerson.postValue(response.data)
            } else {
                _resourcePerson.postValue(null)
                // _resourcePerson.value = null
            }
        }

    }

    fun getTrainingManagementCourses() {

        viewModelScope.launch {
            val response = trainingRepo.getTrainingCourses()
            if (response is TrainingResponse.ResourcePersonCourseResponse) {
                _courseResponse.postValue(response.data.data)
            } else {
                _courseResponse.postValue(null)
                // _resourcePerson.value = null
            }
        }
    }


    fun getTrainingManagementModule() {

        viewModelScope.launch {
            val response = trainingRepo.getTrainingModules()
            if (response is TrainingResponse.ResourcePersonModulesResponse) {
                _modulesResponse.postValue(response.data.data)
            } else {
                _modulesResponse.postValue(null)
                // _resourcePerson.value = null
            }
        }
    }

    fun searchResourcePerson(
        map: HashMap<Any, Any?>,
        onResourcePersonValueListener: OnResourcePersonValueListener
    ) {
        viewModelScope.launch {
            val response = trainingRepo.searchResourcePersonList(map)
            if (response is TrainingResponse.ResourcePersonResponse) {
                _resourcePerson.postValue(response.data)
                onResourcePersonValueListener.onValueChange(response.data)
            } else {
                _resourcePerson.postValue(null)
                onResourcePersonValueListener.onValueChange(null)
                // _resourcePerson.value = null
            }
        }

    }

    fun addResourcePerson(map: HashMap<Any, Any?>): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = trainingRepo.addResourcePerson(map, liveData)
        }
        return liveData
    }

    fun updateResourcePerson(map: HashMap<Any, Any?>, id: Int): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = trainingRepo.updateResourcePerson(map, liveData, id)
        }
        return liveData
    }
}