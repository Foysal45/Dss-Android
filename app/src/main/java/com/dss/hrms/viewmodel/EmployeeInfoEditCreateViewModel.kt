package com.dss.hrms.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dss.hrms.repository.EmployeeInfoEditCreateRepo
import com.dss.hrms.repository.LoginRepo
import okhttp3.MultipartBody
import retrofit2.http.Part

class EmployeeInfoEditCreateViewModel(application: Application) : AndroidViewModel(application) {
    private var employeeInfoEditCreateRepo: EmployeeInfoEditCreateRepo? = null

    init {
        employeeInfoEditCreateRepo = EmployeeInfoEditCreateRepo(application)
    }

    fun updateJobJoiningInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.editJobjoiningInfo(id, map)
    }

    fun updateQuotaInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateQuotaInfo(id, map)
    }

    fun updatePresentInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updatePresentInfo(id, map)
    }

    fun addPresentInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addPresentInfo(map)
    }

    fun updatePermanentInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updatePermanentInfo(id, map)
    }

    fun addPermanentInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addPermanentInfo(map)
    }

    fun updateEducationQualificationInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateEducationQualification(id, map)
    }

    fun addEducationQualificationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addEducationQualificationInfo(map)
    }

    fun updateSpouseInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateSpouseInfo(id, map)
    }

    fun addSpouseInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addSpouseInfo(map)
    }

    fun updateChildInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateChildInfo(id, map)
    }

    fun addChildInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addChildInfo(map)
    }

    fun updateLanguageInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateLanguageInfo(id, map)
    }

    fun addLanguageInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addLanguageInfo(map)
    }

    fun updateLocalTrainingInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateLocalTrainingInfo(id, map)
    }

    fun addLocalTrainingInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addLocalTrainingInfo(map)
    }

    fun updateForeignTrainingInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateForeignTrainingInfo(id, map)
    }

    fun addForeignTrainingInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addForeignTrainingInfo(map)
    }


    fun updatePublicationInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updatePublicationInfo(id, map)
    }

    fun addPublicationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addPublicationInfo(map)
    }

    fun updateReferenceInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateReferenceInfo(id, map)
    }

    fun addReferenceInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addReferenceInfo(map)
    }

    fun updateOfficialResidentialInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateOfficialResidentialInfo(id, map)
    }

    fun addOfficialResidentialInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addOfficialResidentialInfo(map)
    }

    fun updateForeignTravelInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateForeignTravelInfo(id, map)
    }

    fun addForeignTravelInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addForeignTravellInfo(map)
    }

    fun updateHonoursAwardInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateHonoursAwardInfo(id, map)
    }

    fun addHonoursAwardInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addHonoursAwardInfo(map)
    }

    fun updateAdditionalQualificationInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateAdditionalQualificationInfo(id, map)
    }

    fun addAdditionalQualificationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.addAdditionalQualificationInfo(map)
    }


    fun uploadProfilePic(@Part filenames: MultipartBody.Part?, picName: String): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.uploadProfilePic(filenames,picName)
    }


}