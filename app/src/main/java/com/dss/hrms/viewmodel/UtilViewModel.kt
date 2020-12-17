package com.dss.hrms.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dss.hrms.model.Employee
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.UtilRepoRepo

class UtilViewModel(application: Application) : AndroidViewModel(application) {
    private var utilRepoRepo: UtilRepoRepo? = null

    init {
        utilRepoRepo = UtilRepoRepo(application)
    }

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