package com.dss.hrms.view.training.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.repository.training.TrainingRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrainingManagementViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var trainingRepo: TrainingRepo

    private var _resourcePerson = MutableLiveData<List<TrainingResponse.ResourcePerson>>()
    var resourcePerson: LiveData<List<TrainingResponse.ResourcePerson>> = _resourcePerson


    fun getResourcePerson() {
        viewModelScope.launch {
            var response = trainingRepo.getResourcePersonList()
            if (response is TrainingResponse.ResourcePersonResponse) {
                _resourcePerson.postValue(response.data)
            } else {
                _resourcePerson.postValue(null)
                // _resourcePerson.value = null
            }
        }

    }

    fun updateResourcePerson(map: HashMap<Any, Any?>, id: Int): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = trainingRepo.updateResourcePerson(map, liveData, id)
        }
        return liveData
    }
}