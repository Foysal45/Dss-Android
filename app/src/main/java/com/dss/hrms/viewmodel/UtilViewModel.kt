package com.dss.hrms.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.UtilRepoRepo
import com.dss.hrms.view.allInterface.HeadOfficeDepartmentDataValueListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class UtilViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var utilRepoRepo: UtilRepoRepo


    fun getDivision(): MutableLiveData<List<SpinnerDataModel>>? {
        return utilRepoRepo?.getDivision()
    }

    fun getDistrict(divisionId: Int): MutableLiveData<List<SpinnerDataModel>>? {
        return utilRepoRepo?.getDistrict(divisionId)
    }

    fun getUpozila(districtId: Int): MutableLiveData<List<SpinnerDataModel>>? {
        return utilRepoRepo?.getUpazila(districtId)
    }

    fun getCommonData(identity: String): MutableLiveData<List<SpinnerDataModel>>? {
        return utilRepoRepo?.getCommonData(identity)
    }

    fun getCommonData2(identity: String): MutableLiveData<List<SpinnerDataModel>>? {
        return utilRepoRepo?.getCommonData2(identity)
    }


    fun getHeadOfficeDepartment(): MutableLiveData<List<HeadOfficeDepartmentApiResponse.HeadOfficeBranch>> {
        var liveData = MutableLiveData<List<HeadOfficeDepartmentApiResponse.HeadOfficeBranch>>()
        viewModelScope.launch {
            var resonse = utilRepoRepo.getHeadOfficeDepartMent()
            if (resonse != null)
                if (resonse is HeadOfficeDepartmentApiResponse.HeadOfficeDepartmentResponse) {
                    liveData.postValue(resonse.data)
                } else {
                    liveData.postValue(null)
                }
        }
        return liveData
    }

    fun getHeadOfficeDepartment(onHeadOfficeDepartmentDataValueListener: HeadOfficeDepartmentDataValueListener) {
        viewModelScope.launch {
            var resonse = utilRepoRepo.getHeadOfficeDepartMent()
            if (resonse != null)
                if (resonse is HeadOfficeDepartmentApiResponse.HeadOfficeDepartmentResponse) {
                    onHeadOfficeDepartmentDataValueListener.valueChange(resonse.data)
                } else {
                    onHeadOfficeDepartmentDataValueListener.valueChange(null)
                }
        }
    }
}