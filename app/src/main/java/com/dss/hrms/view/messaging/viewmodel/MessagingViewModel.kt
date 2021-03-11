package com.dss.hrms.view.messaging.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.repository.training.TrainingSimpleRepo
import com.dss.hrms.view.training.model.ExpertiseField
import com.dss.hrms.view.training.model.ExpertiseResponse
import com.dss.hrms.view.training.model.HonorariumHead
import com.dss.hrms.view.training.model.HonorariumHeadResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessagingViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var trainingSimpleRepo: TrainingSimpleRepo


    fun getHonorariumHeadList(): MutableLiveData<List<HonorariumHead>> {
        var liveData = MutableLiveData<List<HonorariumHead>>()
        viewModelScope.launch {
            var response = trainingSimpleRepo.getHonorariumHeadList()
            if (response is HonorariumHeadResponse) {
                liveData.postValue(response.data)
            } else {
                liveData.postValue(null)
            }
        }
        return liveData
    }

    fun getExpertiseList(): MutableLiveData<List<ExpertiseField>> {
        var liveData = MutableLiveData<List<ExpertiseField>>()
        viewModelScope.launch {
            var response = trainingSimpleRepo.getExpertiseList()
           // var res = (response as ExpertiseResponse)
            Log.e("viewmodel"," expertise ${response}")
            if (response is ExpertiseResponse) {
                liveData.postValue(response.data)
            } else {
                liveData.postValue(null)
            }
        }
        return liveData
    }
}