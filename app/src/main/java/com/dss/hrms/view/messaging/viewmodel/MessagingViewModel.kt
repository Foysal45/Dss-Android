package com.dss.hrms.view.messaging.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.repository.message.MessageRepo
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
    lateinit var messageRepo: MessageRepo

    fun sendEmail(map: HashMap<Any, Any?>): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = messageRepo.sendEmail(map, liveData)
        }
        return liveData
    }

    fun sendSms(map: HashMap<Any, Any?>): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = messageRepo.sendSms(map, liveData)
        }
        return liveData
    }
}