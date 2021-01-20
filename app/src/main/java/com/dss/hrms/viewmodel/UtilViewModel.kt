package com.dss.hrms.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.UtilRepoRepo
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
}