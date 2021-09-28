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


    inner class jobJoiningInformationPendingModel {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("data")
        val data: Employee.Jobjoinings? = null

    }
}

class referencePendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.References? = null
}

class desciplinaryActionPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.DisciplinaryAction? = null
}

class honoursAndAwardPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.HonoursAwards? = null
}

class publicationsPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.Publications? = null
}

class addtionalProfeQualiPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.AdditionalQualifications? = null
}

class officialResidentialInfoPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.OfficialResidentials? = null
}

class foreignTravelPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.ForeignTravels? = null
}

class foreignTrainingPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.Foreigntrainings? = null
}

class localTrainigPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.LocalTrainings? = null
}


class languageInfoPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.Languages? = null
}

class EducationalQualificationsPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.EducationalQualifications? = null
}

class PermanentAddressPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.PermanentAddresses? = null
}

class PresentAddressPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    var data: Employee.PresentAddresses? = null
}

class QuotaInformationPendingModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    val data: Employee.EmployeeQuotas? = null
}

class EmployeeModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("data")
    val data: PendingEmployeeModel? = null
}

class PendingEmployeeModel {

    @SerializedName("name")
    val name: String? = null

    @SerializedName("name_bn")
    val name_bn: String? = null

    @SerializedName("photo")
    val photo: String? = null

    @SerializedName("date_of_birth")
    var date_of_birth: String? = null

    @SerializedName("gender_id")
    val gender_id: Int = 0

    @SerializedName("fathers_name")
    val fathers_name: String? = null

    @SerializedName("fathers_name_bn")
    val fathers_name_bn: String? = null

    @SerializedName("mothers_name")
    val mothers_name: String? = null

    @SerializedName("mothers_name_bn")
    val mothers_name_bn: String? = null

    @SerializedName("nid_no")
    val nid_no: String? = null

    @SerializedName("punch_id")
    val punch_id: String? = null

    @SerializedName("tin_no")
    val tin_no: String? = null

    @SerializedName("marital_status_id")
    val marital_status_id: Int = 0

    @SerializedName("has_disability")
    val has_disability: Boolean = false

    @SerializedName("has_freedom_fighter_quota")
    val has_freedom_fighter_quota: Int = 0


    @SerializedName("disability_document_path")
    val disability_document_path: String? = null

    @SerializedName("freedom_fighter_document_path")
    val freedom_fighter_document_path: String? = null


    @SerializedName("disability_type_id")
    val disability_type_id: Int = 0

    @SerializedName("disability_degree_id")
    val disability_degree_id: Int = 0

    @SerializedName("disabled_person_id")
    val disabled_person_id: String? = null

    @SerializedName("office_id")
    val office_id: Int = 0

    @SerializedName("designation_id")
    val designation_id: Int = 0

    @SerializedName("employee_type_id")
    val employee_type_id: Int? = null

    @SerializedName("status")
    val status: Int = 0

    @SerializedName("deleted_at")
    val deleted_at: String? = null

    @SerializedName("created_at")
    val created_at: String? = null

    @SerializedName("updated_at")
    val updated_at: String? = null

    @SerializedName("present_basic_salary")
    val present_basic_salary: String? = null

    @SerializedName("present_gross_salary")
    val present_gross_salary: String? = null

    @SerializedName("blood_group")
    val blood_group: Employee.BloodGroup? = null

    @SerializedName("job_joining_date")
    val job_joining_date: String? = null

    @SerializedName("religion")
    val religion: Employee.Religion? = null

    @SerializedName("disability_type")
    val disability_type: Employee.DisabilityType? = null

    @SerializedName("disability_degree")
    val disability_degree: Employee.DisabilityDegree? = null

    @SerializedName("employee_type")
    val employee_type: Employee.EmployeeType? = null

    @SerializedName("marital_status")
    val marital_status: Employee.MaritalStatus? = null

    @SerializedName("user")
    val user: Employee.User? = null

    @SerializedName("employment_job_status")
    val employment_job_status: Employee.EmploymentJobStatus? = null
}

