package com.dss.hrms.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.EmployeeInfoEditCreateRepo
import com.dss.hrms.repository.LoginRepo
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import java.lang.Exception
import javax.inject.Inject

class EmployeeInfoEditCreateViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var employeeInfoEditCreateRepo: EmployeeInfoEditCreateRepo


    fun updateJobJoiningInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.editJobjoiningInfo(id, map)
    }

    fun updateQuotaInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.updateQuotaInfo(id, map)
    }

    fun updateBasicInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateBasicInfo(id, map, liveData)
        }
        return liveData
    }

    fun updateNomineeInfo(map: HashMap<Any, Any?>? , id : Int): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateNomineeInfo(map, liveData , id )
        }
        return liveData
    }

    fun updatePresentInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updatePresentInfo(id, map, liveData)
        }
        return liveData
    }

    fun addPresentInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addPresentInfo(map, liveData)
        }
        return liveData
    }

    fun updatePermanentInfo(id: Int?, map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updatePermanentInfo(id, map, liveData)
        }
        return liveData
    }

    fun addPermanentInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addPermanentInfo(map, liveData)
        }
        return liveData
    }

    fun updateEducationQualificationInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateEducationQualification(id, map, liveData)
        }
        return liveData
    }

    fun addEducationQualificationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addEducationQualificationInfo(map, liveData)
        }
        return liveData
    }

    fun updateSpouseInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateSpouseInfo(id, map, liveData)
        }
        return liveData
    }

    fun addSpouseInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            try {
                liveData = employeeInfoEditCreateRepo?.addSpouseInfo(map, liveData)
            }catch (ex : Exception){
                Log.d("LIOVE", "addSpouseInfo: ${ex.localizedMessage} ")
            }

        }
        return liveData

    }


    fun getAllHrTrainingList(
    ): MutableLiveData<List<SpinnerDataModel>>? {
        return employeeInfoEditCreateRepo.getAllHrTraining()
    }

    fun updateChildInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateChildInfo(id, map, liveData)
        }
        return liveData

    }

    fun addChildInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addChildInfo(map, liveData)
        }
        return liveData
    }

    fun updateLanguageInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateLanguageInfo(id, map, liveData)
        }
        return liveData
    }

    fun addLanguageInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addLanguageInfo(map, liveData)
        }
        return liveData

    }

    fun updateLocalTrainingInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateLocalTrainingInfo(id, map, liveData)
        }
        return liveData


    }

    fun addLocalTrainingInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addLocalTrainingInfo(map, liveData)
        }
        return liveData
    }

    fun updateForeignTrainingInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateForeignTrainingInfo(id, map, liveData)
        }
        return liveData

    }

    fun addForeignTrainingInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addForeignTrainingInfo(map, liveData)
        }
        return liveData

    }


    fun updatePublicationInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updatePublicationInfo(id, map, liveData)
        }
        return liveData
    }

    fun addPublicationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addPublicationInfo(map, liveData)
        }
        return liveData
    }

    fun updateReferenceInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateReferenceInfo(id, map, liveData)
        }
        return liveData
    }

    fun addReferenceInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addReferenceInfo(map, liveData)
        }
        return liveData
    }

    fun updateOfficialResidentialInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateOfficialResidentialInfo(id, map, liveData)
        }
        return liveData
    }

    fun addOfficialResidentialInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addOfficialResidentialInfo(map, liveData)
        }
        return liveData
    }

    fun updateForeignTravelInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateForeignTravelInfo(id, map, liveData)
        }
        return liveData
    }

    fun addForeignTravelInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addForeignTravellInfo(map, liveData)
        }
        return liveData
    }

    fun updateHonoursAwardInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.updateHonoursAwardInfo(id, map, liveData)
        }
        return liveData
    }

    fun addHonoursAwardInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addHonoursAwardInfo(map, liveData)
        }
        return liveData
    }

    fun updateAdditionalQualificationInfo(
        id: Int?,
        map: HashMap<Any, Any?>?
    ): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData =
                employeeInfoEditCreateRepo?.updateAdditionalQualificationInfo(id, map, liveData)
        }
        return liveData
    }

    fun addAdditionalQualificationInfo(map: HashMap<Any, Any?>?): MutableLiveData<Any>? {
        var liveData: MutableLiveData<Any>? = MutableLiveData<Any>()
        viewModelScope.launch(Dispatchers.Default) {
            liveData = employeeInfoEditCreateRepo?.addAdditionalQualificationInfo(map, liveData)
        }
        return liveData
    }


    fun uploadProfilePic(
        file: MultipartBody.Part?,
        picName: String,
        profile_pic: RequestBody
    ): MutableLiveData<Any>? {
        return employeeInfoEditCreateRepo?.uploadProfilePic(file, picName, profile_pic)
    }


    override fun onCleared() {
        super.onCleared()
        Log.e("employee", "jobjoining onCleared");

    }

}