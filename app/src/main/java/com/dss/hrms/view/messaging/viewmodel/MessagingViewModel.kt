package com.dss.hrms.view.messaging.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.CourseModel
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.repository.message.MessageRepo
import com.dss.hrms.view.messaging.model.MessageModel
import com.dss.hrms.view.messaging.model.msgResp
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessagingViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var messageRepo: MessageRepo
    private var _msgResponse = MutableLiveData<List<MessageModel>>()
    var msg: LiveData<List<MessageModel>> = _msgResponse

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

    fun getMessageList() {

        viewModelScope.launch {
            val response = messageRepo.getMsgList()
            if (response is msgResp) {
                _msgResponse.postValue(response.data?.data)
            } else {
                _msgResponse.postValue(null)
                // _resourcePerson.value = null
            }
        }
    }

}