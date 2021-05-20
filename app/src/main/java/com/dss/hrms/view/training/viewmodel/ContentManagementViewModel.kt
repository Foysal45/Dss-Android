package com.dss.hrms.view.training.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.repository.training.ContentRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContentManagementViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var contentRepo: ContentRepo


    private var _category = MutableLiveData<List<TrainingResponse.Category>>()
    var category: LiveData<List<TrainingResponse.Category>> = _category

    private var _content = MutableLiveData<List<TrainingResponse.Content>>()
    var content: LiveData<List<TrainingResponse.Content>> = _content

    private var _faq = MutableLiveData<List<TrainingResponse.Faq>>()
    var faq: LiveData<List<TrainingResponse.Faq>> = _faq
    private var _resourcePerson = MutableLiveData<List<TrainingResponse.ResourcePerson>>()
    var resourcePerson: LiveData<List<TrainingResponse.ResourcePerson>> = _resourcePerson


    fun getCategory() {
        viewModelScope.launch {
            var response = contentRepo.getCategoryList()
            if (response is TrainingResponse.ContentCategory) {
                _category.postValue(response.data)
            } else {
                _category.postValue(null)
            }
        }
    }

    fun addCategory(map: HashMap<Any, Any>): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            liveData = contentRepo.addCategory(map, liveData)
        }
        return liveData
    }
    fun updateCategory(map: HashMap<Any, Any>, categoryId: Int): MutableLiveData<Any> {
        var liveData = MutableLiveData<Any>()

        viewModelScope.launch {
            liveData = contentRepo.updateCategory(map, liveData, categoryId)
        }
        return liveData
    }

    fun getContentList() {
        viewModelScope.launch {

            Log.e("viewnodel", "getContentList")
            var response = contentRepo.getContentList()
            if (response is TrainingResponse.ContentsContent) {
                _content.postValue(response.data)
            } else {
                _content.postValue(null)
            }
        }
    }

    fun getFaq() {
        viewModelScope.launch {
            var response = contentRepo.getFaqList()

            if (response is TrainingResponse.ContentsFaq) {
                _faq.postValue(response.data)
            } else {
                _faq.postValue(null)
            }
        }
    }
}