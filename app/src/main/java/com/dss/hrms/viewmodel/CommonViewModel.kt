package com.dss.hrms.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.Office
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.repository.UtilRepoRepo
import com.dss.hrms.view.allInterface.CommonDataValueListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommonViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var commonRepo: CommonRepo


    fun getCommonData(
        identity: String
    ): MutableLiveData<List<SpinnerDataModel>>? {
        return commonRepo.getCommonData(identity)
    }

    fun getDistrict(
        identity: Int?
    ): MutableLiveData<List<SpinnerDataModel>>? {
        return commonRepo.getDistrict(identity)
    }

    fun getAllDistrict(
    ): MutableLiveData<List<SpinnerDataModel>>? {
        return commonRepo.getAllDistrict()
    }

    fun getUpozilla(
        identity: Int
    ): MutableLiveData<List<SpinnerDataModel>>? {
        return commonRepo.getUpazila(identity)
    }


    fun getDesignationData(
        identity: String
    ): MutableLiveData<List<SpinnerDataModel>>? {
        return commonRepo.getDesignationData(identity)
    }

    fun getOffice(
        identity: String
    ): MutableLiveData<List<Office>>? {
        return commonRepo.getOffice(identity)
    }
}