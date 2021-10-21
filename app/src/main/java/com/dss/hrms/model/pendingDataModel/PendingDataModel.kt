package com.dss.hrms.model.pendingDataModel

import com.dss.hrms.model.employeeProfile.Employee
import com.google.gson.annotations.SerializedName

class PendingDataModel {
    var jobJoiningInformation: List<jobJoiningInformationPendingModel>? = null
    var employee: MutableList<EmployeeModel>? = null
    var quotaInformation: MutableList<QuotaInformationPendingModel>? = null
    var presentAddress: MutableList<PresentAddressPendingModel>? = null
    var permanentAddress: MutableList<PermanentAddressPendingModel>? = null
    var educationQuality: MutableList<EducationalQualificationsPendingModel>? = null
    var languageInfo: MutableList<languageInfoPendingModel>? = null
    var localTrainig: MutableList<localTrainigPendingModel>? = null
    var foreignTraining: MutableList<foreignTrainingPendingModel>? = null
    var foreignTravel: MutableList<foreignTravelPendingModel>? = null
    var officialResidentialInfo: MutableList<officialResidentialInfoPendingModel>? = null
    var additionalProfessionalQualifications: MutableList<addtionalProfeQualiPendingModel>? = null
    var publications: MutableList<publicationsPendingModel>? = null
    var honoursAndAward: MutableList<honoursAndAwardPendingModel>? = null
    var desciplinaryAction: MutableList<desciplinaryActionPendingModel>? = null
    var reference: MutableList<referencePendingModel>? = null
    val nomineeInfo: MutableList<nominePendingModel>? = null
    val childrenInfo: MutableList<childrenInfoPendingModel>? = null
    val spouse: MutableList<spousePendingModel>? = null


    inner class jobJoiningInformationPendingModel {
        @SerializedName("id")
        val id: Int = 0
        var parent_id: Int? = null
        @SerializedName("data")
        val data: Employee.Jobjoinings? = null

    }
}

class employeePendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee? = null
}

class spousePendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.Spouses? = null
}

class childrenInfoPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.Childs? = null
}

class nominePendingModel {
    var parent_id: Int? = null
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.Nominee? = null
}

class referencePendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.References? = null
}

class desciplinaryActionPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.DisciplinaryAction? = null
}

class honoursAndAwardPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.HonoursAwards? = null
}

class publicationsPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.Publications? = null
}

class addtionalProfeQualiPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.AdditionalQualifications? = null
}

class officialResidentialInfoPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.OfficialResidentials? = null
}

class foreignTravelPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.ForeignTravels? = null
}

class foreignTrainingPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.Foreigntrainings? = null
}

class localTrainigPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.LocalTrainings? = null
}


class languageInfoPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.Languages? = null
}

class EducationalQualificationsPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.EducationalQualifications? = null
}

class PermanentAddressPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.PermanentAddresses? = null
}

class PresentAddressPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    var data: Employee.PresentAddresses? = null
}

class QuotaInformationPendingModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    val data: Employee.EmployeeQuotas? = null
}

class EmployeeModel {
    @SerializedName("id")
    val id: Int = 0
    var parent_id: Int? = null
    @SerializedName("data")
    val data: Employee? = null
}



