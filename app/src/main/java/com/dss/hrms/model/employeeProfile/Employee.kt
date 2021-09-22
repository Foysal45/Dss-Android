package com.dss.hrms.model.employeeProfile

import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import javax.inject.Singleton

class Employee {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("profile_id")
    val profile_id: String? = null

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
    val has_freedom_fighter_quota: Boolean = false


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
    val blood_group: BloodGroup? = null

    @SerializedName("job_joining_date")
    val job_joining_date: String? = null

    @SerializedName("religion")
    val religion: Religion? = null

    @SerializedName("disability_type")
    val disability_type: DisabilityType? = null

    @SerializedName("disability_degree")
    val disability_degree: DisabilityDegree? = null

    @SerializedName("employee_type")
    val employee_type: EmployeeType? = null

    @SerializedName("marital_status")
    val marital_status: MaritalStatus? = null

    @SerializedName("user")
    val user: User? = null

    @SerializedName("employment_job_status")
    val employment_job_status: EmploymentJobStatus? = null

    @SerializedName("jobjoinings")
    val jobjoinings: List<Jobjoinings>? = null

    @SerializedName("employee_quotas")
    val employee_quotas: List<EmployeeQuotas>? = null

    @SerializedName("present_addresses")
    val presentAddresses: List<PresentAddresses>? = null

    @SerializedName("permanent_addresses")
    val permanentAddresses: List<PermanentAddresses>? = null

    @SerializedName("educational_qualifications")
    val educationalQualifications: List<EducationalQualifications>? = null

    @SerializedName("disciplinary_actions")
    val disciplinaryActions: List<DisciplinaryAction>? = null

    @SerializedName("promotions")
    val promotions: List<Promotions>? = null

    @SerializedName("references")
    val references: List<References>? = null

    @SerializedName("nominees")
    val nominees: List<Nominee>? = null


    @SerializedName("gender")
    val gender: Gender? = null

    @SerializedName("spouses")
    val spouses: List<Spouses>? = null

    @SerializedName("childs")
    val childs: List<Childs>? = null

    @SerializedName("languages")
    val languages: List<Languages>? = null

    @SerializedName("local_trainings")
    val local_trainings: List<LocalTrainings>? = null

    @SerializedName("foreigntrainings")
    val foreigntrainings: List<Foreigntrainings>? = null

    @SerializedName("foreign_travels")
    var foreign_travels: List<ForeignTravels>? = null

    @SerializedName("additional_qualifications")
    val additional_qualifications: List<AdditionalQualifications>? = null

    @SerializedName("publications")
    val publications: List<Publications>? = null

    @SerializedName("honours_awards")
    val honours_awards: List<HonoursAwards>? = null

    @SerializedName("official_residentials")
    val official_residentials: List<OfficialResidentials>? = null

    //    @SerializedName("posting_records")
//    val posting_records: List<PostingRecords>? = null
    inner class EmploymentJobStatus {
        @SerializedName("id")
        val id: Int? = 0

        @SerializedName("empolyee_id")
        val empolyee_id: Int? = 0

        @SerializedName("employment_status_id")
        val employment_status_id: Int? = 0

        @SerializedName("status_date")
        val status_date: String? = null

        @SerializedName("employeementstatus")
        val employeementstatus: Employeementstatus? = null
    }


    inner class Employeementstatus {
        @SerializedName("id")
        val id: Int? = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("priority_order")
        val priority_order: String? = null

        @SerializedName("status")
        val status: Int? = 0
    }

    inner class DisabilityType {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("disability_type")
        val disability_type: String? = null

        @SerializedName("disability_type_bn")
        val disability_type_bn: String? = null

        @SerializedName("status")
        val status: Int? = null

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

    }

    inner class DisabilityDegree {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("disability_degree")
        val disability_degree: String? = null

        @SerializedName("disability_degree_bn")
        val disability_degree_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class MaritalStatus {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int? = null

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class Religion {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int? = null

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class BloodGroup {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int? = null

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class EmployeeType {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_type")
        val employee_type: String? = null

        @SerializedName("employee_type_bn")
        val employee_type_bn: String? = null

        @SerializedName("status")
        val status: Int? = null
    }

    inner class User {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("phone_number")
        val phone_number: String? = null

        @SerializedName("username")
        val username: String? = null

        @SerializedName("email")
        val email: String? = null

        @SerializedName("email_verified_at")
        val email_verified_at: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("roles")
        val roles: List<Role>? = null
    }

    class Role {
        @SerializedName("id")
        val id: Int? = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("guard_name")
        val guard_name: String? = null

        @SerializedName("guard_name_bn")
        val guard_name_bn: String? = null
    }

    inner class Jobjoinings {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("office_id")
        val office_id: Int = 0

        @SerializedName("additional_designation_id")
        val additional_designation_id: Int = 0

        @SerializedName("designation_id")
        val designation_id: Int = 0

        @SerializedName("department_id")
        val department_id: Int = 0

        @SerializedName("job_type_id")
        val job_type_id: Int = 0

        @SerializedName("employee_class_id")
        val employee_class_id: Int = 0

        @SerializedName("grade_id")
        val grade_id: Int = 0

        @SerializedName("pay_scale")
        val pay_scale: String? = null

        @SerializedName("pay_scale_id")
        val pay_scale_id: Int? = null

        @SerializedName("joining_date")
        val joining_date: String? = null

        @SerializedName("confirmation_date")
        val confirmation_date: String? = null

        @SerializedName("pension_date")
        val pension_date: String? = null

        @SerializedName("prl_date")
        val prl_date: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("office")
        val office: Office? = null

        @SerializedName("designation")
        val designation: Designation? = null

        @SerializedName("additional_designation")
        val additional_designation: AdditionalDesignation? = null

        @SerializedName("department")
        val department: Department? = null

        @SerializedName("job_type")
        val job_type: Job_type? = null

        @SerializedName("employee_class")
        val employee_class: Employee_class? = null

        @SerializedName("grade")
        val grade: Grade? = null


        inner class Office {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("office_type_id")
            val office_type_id: Int = 0

            @SerializedName("division_id")
            val division_id: Int = 0

            @SerializedName("district_id")
            val district_id: Int = 0

            @SerializedName("upazila_id")
            val upazila_id: Int = 0

            @SerializedName("office_category_id")
            val office_category_id: String? = null

            @SerializedName("office_name")
            val office_name: String? = null

            @SerializedName("office_name_bn")
            val office_name_bn: String? = null

            @SerializedName("office_code")
            val office_code: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class Designation {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class AdditionalDesignation {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("priority_order")
            val priority_order: Int? = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class Department {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class Job_type {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class Employee_class {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("rank")
            val rank: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class Grade {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("rank")
            val rank: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }
    }

    inner class EmployeeQuotas {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("quota_information_id")
        val quota_information_id: Int = 0

        @SerializedName("quota_information_detail_id")
        val quota_information_detail_id: Int = 0

        @SerializedName("description")
        val description: String? = null

        @SerializedName("description_bn")
        val description_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null


        @SerializedName("quota_documents")
        val quota_documnets: List<String>? = null

        @SerializedName("quota_information")
        val quotaInformation: QuotaInformation? = null

        @SerializedName("quota_information_details")
        val quotaInformationDetails: QuotaInformationDetails? = null


        inner class QuotaInformation {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class QuotaInformationDetails {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("quota_information_id")
            val quota_information_id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int? = null

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

    }


    inner class Nominee {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("date_of_birth")
        val date_of_birth: String? = null

        @SerializedName("relation")
        val relation: String? = null

        @SerializedName("allocated_percentage")
        val allocated_percentage: String? = null

        @SerializedName("nominee_signature")
        val nominee_signature: String? = null

        @SerializedName("nominee_document_path")
        val nominee_document_path: String? = null


        @SerializedName("has_disability")
        val has_disability: Int = 0

        @SerializedName("marital_status_id")
        val marital_status_id: Int? = null

        @SerializedName("gender_id")
        val gender_id: Int? = null

        @SerializedName("gender")
        val gender: Gender? = null


        @SerializedName("municipality_id")
        val municipality_id: Int? = null


        @SerializedName("city_corporation_id")
        val city_corporation_id: Int? = null

        @SerializedName("local_government_type_id")
        val local_government_type_id: Int? = null

        @SerializedName("address_details")
        val address_details: String? = null

        @SerializedName("division_id")
        var division_id: Int = 0

        @SerializedName("district_id")
        val district_id: Int = 0

        @SerializedName("upazila_id")
        val upazila_id: Int = 0

        @SerializedName("union_id")
        val union_id: Int = 0


        @SerializedName("marital_status")
        val marital_status: MaritalStatus? = null
    }

    inner class PresentAddresses {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("division_id")
        val division_id: Int = 0

        @SerializedName("district_id")
        val district_id: Int = 0

        @SerializedName("upazila_id")
        val upazila_id: Int = 0

        @SerializedName("phone_no")
        val phone_no: String? = null

        @SerializedName("police_station")
        val police_station: String? = null

        @SerializedName("police_station_bn")
        val police_station_bn: String? = null

        @SerializedName("post_office")
        val post_office: String? = null

        @SerializedName("post_office_bn")
        val post_office_bn: String? = null

        @SerializedName("post_code")
        val post_code: String? = null

        @SerializedName("road_word_no")
        val road_word_no: String? = null

        @SerializedName("road_word_no_bn")
        val road_word_no_bn: String? = null

        @SerializedName("village_house_no")
        val village_house_no: String? = null

        @SerializedName("village_house_no_bn")
        val village_house_no_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("union_id")
        val union_id: Int? = 0

        @SerializedName("municipality_id")
        val municipality_id: Int? = 0

        @SerializedName("local_government_type_id")
        val local_government_type_id: Int? = 0

        @SerializedName("city_corporation_id")
        val city_corporation_id: Int? = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("division")
        val division: Division? = null

        @SerializedName("district")
        val district: District? = null

        @SerializedName("upazila")
        var upazila: Upazila? = null

        @SerializedName("ward_no")
        val wardNo: String? = null

        @SerializedName("local_government_type")
        var localGovernmentType: LocalGovernmentType? = null

        @SerializedName("city_corporation")
        var cityCorporation: CityCorporation? = null

        @SerializedName("municipality")
        var municipality: municipality? = null

        @SerializedName("union")
        var Union: union? = null
    }

    inner class PermanentAddresses {
        @SerializedName("id")
        var id: Int = 0


        @SerializedName("employee_id")
        var employee_id: Int = 0

        @SerializedName("division_id")
        var division_id: Int = 0

        @SerializedName("district_id")
        var district_id: Int = 0

        @SerializedName("upazila_id")
        var upazila_id: Int = 0

        @SerializedName("phone_no")
        var phone_no: String? = null

        @SerializedName("police_station")
        var police_station: String? = null

        @SerializedName("police_station_bn")
        var police_station_bn: String? = null

        @SerializedName("post_office")
        var post_office: String? = null

        @SerializedName("post_office_bn")
        var post_office_bn: String? = null

        @SerializedName("post_code")
        var post_code: String? = null

        @SerializedName("road_word_no")
        var road_word_no: String? = null

        @SerializedName("road_word_no_bn")
        var road_word_no_bn: String? = null

        @SerializedName("village_house_no")
        var village_house_no: String? = null

        @SerializedName("village_house_no_bn")
        var village_house_no_bn: String? = null

        @SerializedName("status")
        var status: Int = 0

        @SerializedName("union_id")
        var union_id: Int? = 0

        @SerializedName("municipality_id")
        var municipality_id: Int? = 0

        @SerializedName("local_government_type_id")
        var local_government_type_id: Int? = 0

        @SerializedName("city_corporation_id")
        var city_corporation_id: Int? = 0

        @SerializedName("deleted_at")
        var deleted_at: String? = null

        @SerializedName("created_at")
        var created_at: String? = null

        @SerializedName("updated_at")
        var updated_at: String? = null

        @SerializedName("division")
        var division: Division? = null

        @SerializedName("district")
        var district: District? = null

        @SerializedName("upazila")
        var upazila: Upazila? = null

        @SerializedName("local_government_type")
        var localGovernmentType: LocalGovernmentType? = null

        @SerializedName("city_corporation")
        var cityCorporation: CityCorporation? = null

        @SerializedName("municipality")
        var municipality: municipality? = null

        @SerializedName("union")
        var union: union? = null

    }

    inner class LocalGovernmentType {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

    }

    inner class CityCorporation {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("division_id")
        val divisionID: Int = 0

        @SerializedName("district_id")
        val districtID: Int = 0

        val name: String? = null

        @SerializedName("name_bn")
        val nameBn: String? = null

        val status: Int = 0

        @SerializedName("deleted_at")
        val deletedAt: String? = null

        @SerializedName("created_at")
        val createdAt: String? = null

        @SerializedName("updated_at")
        val updatedAt: String? = null
    }


    inner class EducationalQualifications {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("examination_id")
        val examination_id: Int = 0

        @SerializedName("educational_institute_id")
        val educational_institute_id: Int = 0

        @SerializedName("board_id")
        val board_id: Int = 0

        @SerializedName("passing_year")
        val passing_year: String? = null

        @SerializedName("division_cgpa")
        val division_cgpa: String? = null


        @SerializedName("document_path")
        val documentPath: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("examination")
        val examination: Examination? = null

        @SerializedName("board")
        val board: Board? = null

        @SerializedName("educational_institute")
        val educational_institute: EducationalInstitute? = null


        inner class Examination {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null


            @SerializedName("status")
            val status: Int? = null

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class Board {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null


            @SerializedName("status")
            val status: Int? = null

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class EducationalInstitute {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null


            @SerializedName("status")
            val status: Int? = null

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }


    }

    inner class DisciplinaryAction {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("disciplinary_action_category_id")
        val disciplinary_action_category_id: Int = 0

        @SerializedName("disciplinary_action_details")
        val disciplinary_action_details: String? = null

        @SerializedName("disciplinary_action_details_bn")
        val disciplinary_action_details_bn: String? = null

        @SerializedName("present_status")
        val present_status: Int = 0

        @SerializedName("judgment")
        val judgment: String? = null

        @SerializedName("judgment_bn")
        val judgment_bn: String? = null

        @SerializedName("final_judgment")
        val final_judgment: String? = null

        @SerializedName("final_judgment_bn")
        val final_judgment_bn: String? = null

        @SerializedName("remarks")
        val remarks: String? = null

        @SerializedName("remarks_bn")
        val remarks_bn: String? = null

        @SerializedName("date")
        val date: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("disciplinary_action_category")
        val disciplinaryActionCategory: DisciplinaryActionCategory? = null

        inner class DisciplinaryActionCategory {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }
    }

    inner class Promotions {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("memo_no")
        val memo_no: String? = null

        @SerializedName("memo_no_bn")
        val memo_no_bn: String? = null

        @SerializedName("memo_date")
        val memo_date: String? = null

        @SerializedName("division_id")
        val division_id: Int = 0

        @SerializedName("district_id")
        val district_id: Int = 0

        @SerializedName("previous_post_id")
        val previous_post_id: Int = 0

        @SerializedName("current_position_id")
        val current_position_id: Int = 0

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("division")
        val division: Division? = null

        @SerializedName("district")
        val district: District? = null

        @SerializedName("previous_posts")
        val previous_posts: PreviousPosts? = null

        @SerializedName("current_position")
        val current_position: CurrentPosition? = null


        inner class PreviousPosts {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

        inner class CurrentPosition {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }
    }

    inner class References {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("relation")
        val relation: String? = null

        @SerializedName("relation_bn")
        val relation_bn: String? = null

        @SerializedName("contact_no")
        val contact_no: String? = null

        @SerializedName("contact_no_bn")
        val contact_no_bn: String? = null

        @SerializedName("address")
        val address: String? = null

        @SerializedName("address_bn")
        val address_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class Gender {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class Spouses {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("relation")
        val relation: String? = null

        @SerializedName("division_id")
        val division_id: Int = 0

        @SerializedName("occupation_id")
        val occupation_id: Int = 0

        @SerializedName("local_government_type")
        var localGovernmentType: LocalGovernmentType? = null

        @SerializedName("spouse_workstation_id")
        val spouse_workstation_id: Int = 0

        @SerializedName("spouse_job_type_id")
        val spouse_job_type_id: Int? = 0

        @SerializedName("district_id")
        val district_id: Int = 0

        @SerializedName("upazila_id")
        val upazila_id: Int? = 0

        @SerializedName("email_address")
        val email_address: String? = null

        @SerializedName("office_name")
        val office_name: String? = null

        @SerializedName("religion_id")
        val religion_id: String? = null

        @SerializedName("phone_no")
        val phone_no: String? = null

        @SerializedName("phone_no_bn")
        val phone_no_bn: String? = null

        @SerializedName("mobile_no")
        val mobile_no: String? = null

        @SerializedName("mobile_no_bn")
        val mobile_no_bn: String? = null

        @SerializedName("occupation")
        var occupation: CommonModel? = null

        @SerializedName("spouse_workstation")
        var spouse_workstation: CommonModel? = null

        @SerializedName("spouse_job_type")
        var spouse_job_type: CommonModel? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("division")
        val division: Division? = null

        @SerializedName("district")
        val distric: District? = null

        @SerializedName("upazila")
        val upazila: Upazila? = null


        @SerializedName("police_station")
        val police_station: String? = null

        @SerializedName("police_station_bn")
        val police_station_bn: String? = null

        @SerializedName("post_office")
        val post_office: String? = null

        @SerializedName("post_office_bn")
        val post_office_bn: String? = null

        @SerializedName("post_code")
        val post_code: String? = null

        @SerializedName("road_word_no")
        val road_word_no: String? = null

        @SerializedName("road_word_no_bn")
        val road_word_no_bn: String? = null

        @SerializedName("village_house_no")
        val village_house_no: String? = null

        @SerializedName("village_house_no_bn")
        val village_house_no_bn: String? = null


        @SerializedName("union_id")
        val union_id: Int? = 0

        @SerializedName("municipality_id")
        val municipality_id: Int? = 0

        @SerializedName("local_government_type_id")
        val local_government_type_id: Int? = 0

        @SerializedName("city_corporation_id")
        val city_corporation_id: Int? = 0



        @SerializedName("city_corporation")
        var cityCorporation: CityCorporation? = null

        @SerializedName("municipality")
        var municipality: municipality? = null

        @SerializedName("union")
        var union: union? = null

        override fun toString(): String {
            return name.toString()
        }

        inner class Occupation {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }
    }

    inner class Childs {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("name_of_children")
        val name_of_children: String? = null

        @SerializedName("name_of_children_bn")
        val name_of_children_bn: String? = null

        @SerializedName("date_of_birth")
        val date_of_birth: String? = null

        @SerializedName("gender_id")
        val gender_id: Int = 0

        @SerializedName("birth_certificate")
        val birth_certificate: String? = null

        @SerializedName("nid")
        val nid: String? = null

        @SerializedName("passport")
        val passport: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null


        @SerializedName("birth_certificate_document_path")
        val birth_certificate_document_path: String? = null

        @SerializedName("passport_document_path")
        val passport_document_path: String? = null

        @SerializedName("nid_document_path")
        val nid_document_path: String? = null


        @SerializedName("gender")
        val gender: Gender? = null

        inner class Gender {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }

    }

    inner class Languages {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("name_of_language")
        val name_of_language: String? = null

        @SerializedName("name_of_language_bn")
        val name_of_language_bn: String? = null

        @SerializedName("name_of_institute")
        val name_of_institute: String? = null

        @SerializedName("name_of_institute_bn")
        val name_of_institute_bn: String? = null

        @SerializedName("expertise_level")
        val expertise_level: String? = null

        @SerializedName("certification_date")
        val certification_date: String? = null

        @SerializedName("certificate_document_path")
        val certificate_document_path: String? = null

        @SerializedName("certificate")
        val certificate: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class LocalTrainings {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("course_title")
        val course_title: String? = null

        @SerializedName("course_title_bn")
        val course_title_bn: String? = null

        @SerializedName("name_of_institute")
        val name_of_institute: String? = null

        @SerializedName("name_of_institute_bn")
        val name_of_institute_bn: String? = null

        @SerializedName("hrm_training_category_id")
        val hrm_training_category_id: Int= 0
        @SerializedName("location")
        val location: String? = null

        @SerializedName("location_bn")
        val location_bn: String? = null

        @SerializedName("from_date")
        val from_date: String? = null

        @SerializedName("to_date")
        val to_date: String? = null

        @SerializedName("local_training_document_path")
        val local_training_document_path: String? = null

        @SerializedName("certificate")
        val certificate: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null


        @SerializedName("hrm_training_category")
        val hrm_training_category: hrm_training_category? = null

        override fun toString(): String {
            return "LocalTrainings(id=$id, employee_id=$employee_id, course_title=$course_title, course_title_bn=$course_title_bn, name_of_institute=$name_of_institute, name_of_institute_bn=$name_of_institute_bn, location=$location, location_bn=$location_bn, from_date=$from_date, to_date=$to_date, local_training_document_path=$local_training_document_path, certificate=$certificate, status=$status, deleted_at=$deleted_at, created_at=$created_at, updated_at=$updated_at)"
        }

    }

    inner class Foreigntrainings {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("course_title")
        val course_title: String? = null

        @SerializedName("hrm_training_category")
        val hrm_training_category: hrm_training_category? = null

        @SerializedName("course_title_bn")
        val course_title_bn: String? = null

        @SerializedName("hrm_training_category_id")
        val hrm_training_category_id: Int= 0

        @SerializedName("foreign_training_document_path")
        val foreign_training_document_path: String? = null

        @SerializedName("name_of_institute")
        val name_of_institute: String? = null

        @SerializedName("name_of_institute_bn")
        val name_of_institute_bn: String? = null

        @SerializedName("country_id")
        val country_id: Int = 0

        @SerializedName("from_date")
        val from_date: String? = null

        @SerializedName("to_date")
        val to_date: String? = null

        @SerializedName("certificate")
        val certificate: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("country")
        val country: Country? = null

        inner class Country {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }
    }

    inner class ForeignTravels {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("document_path")
        val document_path: String? = null

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("country_id")
        val country_id: Int = 0

        @SerializedName("purpose")
        val purpose: String? = null

        @SerializedName("purpose_bn")
        val purpose_bn: String? = null

        @SerializedName("details")
        val details: String? = null

        @SerializedName("details_bn")
        val details_bn: String? = null

        @SerializedName("from_date")
        val from_date: String? = null

        @SerializedName("to_date")
        val to_date: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("country")
        val country: Country? = null

        inner class Country {
            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }
    }

    @Singleton
    class AdditionalQualifications @Inject constructor() {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("qualification_name")
        val qualification_name: String? = null

        @SerializedName("qualification_name_bn")
        val qualification_name_bn: String? = null

        @SerializedName("qualification_details")
        val qualification_details: String? = null

        @SerializedName("qualification_details_bn")
        val qualification_details_bn: String? = null

        @SerializedName("additional_professional_qualification_document_path")
        val additional_professional_qualification_document_path: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class Publications {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("publication_type")
        val publication_type: Int? = null

        @SerializedName("publication_name")
        val publication_name: String? = null

        @SerializedName("publication_name_bn")
        val publication_name_bn: String? = null

        @SerializedName("publication_details")
        val publication_details: String? = null

        @SerializedName("publication_details_bn")
        val publication_details_bn: String? = null


        @SerializedName("document_path")
        val document_path: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("type")
        val type: PublicationType? = null

        inner class PublicationType {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null

        }

    }

    inner class HonoursAwards {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("honours_awards_document_path")
        val honours_awards_document_path: String? = null

        @SerializedName("award_title")
        val award_title: String? = null

        @SerializedName("award_title_bn")
        val award_title_bn: String? = null

        @SerializedName("award_details")
        val award_details: String? = null

        @SerializedName("award_details_bn")
        val award_details_bn: String? = null

        @SerializedName("award_date")
        val award_date: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }


    inner class OfficialResidentials {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int = 0

        @SerializedName("designation_id")
        val designation_id: Int = 0

        @SerializedName("memo_no")
        val memo_no: String? = null

        @SerializedName("memo_no_bn")
        val memo_no_bn: String? = null

        @SerializedName("memo_date")
        val memo_date: String? = null

        @SerializedName("office_zone")
        val office_zone: String? = null

        @SerializedName("location")
        val location: String? = null

        @SerializedName("quarter_name")
        val quarter_name: String? = null

        @SerializedName("flat_no_flat_type")
        val flat_no_flat_type: String? = null

        @SerializedName("division_id")
        val division_id: Int? = null

        @SerializedName("upazila_id")
        val upazila_id: Int? = null

        @SerializedName("district_id")
        val district_id: Int? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("designation")
        val designation: Designation? = null

        @SerializedName("division")
        val division: CommonModel? = null

        @SerializedName("district")
        val district: CommonModel? = null

        @SerializedName("upazila")
        val upazila: CommonModel? = null

        inner class Designation {

            @SerializedName("id")
            val id: Int = 0

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int = 0

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }
    }

    inner class PostingRecords {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("employee_id")
        val employee_id: Int? = null

        @SerializedName("transfer_type_id")
        val transfer_type_id: Int? = null

        @SerializedName("transfer_from_id")
        val transfer_from_id: Int? = null

        @SerializedName("transfer_to_id")
        val transfer_to_id: Int? = null

        @SerializedName("effective_date")
        val effective_date: String? = null

        @SerializedName("status")
        val status: Int? = null

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

        @SerializedName("transfer_type")
        val transfer_type: TransferType? = null

        @SerializedName("transfer_from")
        val transfer_from: TransferFrom? = null

        @SerializedName("transfer_to")
        val transfer_to: TransfeTo? = null

        inner class TransferFrom {
            @SerializedName("id")
            val id: Int? = null

            @SerializedName("office_type_id")
            val office_type_id: Int? = null

            @SerializedName("division_id")
            val division_id: Int? = null

            @SerializedName("district_id")
            val district_id: String? = null

            @SerializedName("upazila_id")
            val upazila_id: String? = null

            @SerializedName("office_category_id")
            val office_category_id: Int? = null

            @SerializedName("office_name")
            val office_name: String? = null

            @SerializedName("office_name_bn")
            val office_name_bn: String? = null

            @SerializedName("office_code")
            val office_code: String? = null

            @SerializedName("status")
            val status: Int? = null

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null

            @SerializedName("division")
            val division: Division? = null

            @SerializedName("district")
            val district: String? = null

            @SerializedName("upazila")
            val upazila: String? = null
        }

        inner class TransfeTo {
            @SerializedName("id")
            val id: Int? = null

            @SerializedName("office_type_id")
            val office_type_id: Int? = null

            @SerializedName("division_id")
            val division_id: Int? = null

            @SerializedName("district_id")
            val district_id: String? = null

            @SerializedName("upazila_id")
            val upazila_id: String? = null

            @SerializedName("office_category_id")
            val office_category_id: Int? = null

            @SerializedName("office_name")
            val office_name: String? = null

            @SerializedName("office_name_bn")
            val office_name_bn: String? = null

            @SerializedName("office_code")
            val office_code: String? = null

            @SerializedName("status")
            val status: Int? = null

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null

            @SerializedName("division")
            val division: Division? = null

            @SerializedName("district")
            val district: String? = null

            @SerializedName("upazila")
            val upazila: String? = null
        }

        inner class TransferType {
            @SerializedName("id")
            val id: Int? = null

            @SerializedName("name")
            val name: String? = null

            @SerializedName("name_bn")
            val name_bn: String? = null

            @SerializedName("status")
            val status: Int? = null

            @SerializedName("deleted_at")
            val deleted_at: String? = null

            @SerializedName("created_at")
            val created_at: String? = null

            @SerializedName("updated_at")
            val updated_at: String? = null
        }
    }


    inner class Upazila {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("district_id")
        val district_id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class union {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("district_id")
        val upazila_id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("is_municipality")
        val is_municipality: Int? = null

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class municipality {

        @SerializedName("id")
        val id: Int = 0

        @SerializedName("district_id")
        val upazila_id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class District {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("division_id")
        val division_id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    inner class Division {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("name")
        val name: String? = null

        @SerializedName("name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null

    }

    inner class CommonModel {

        @SerializedName("id")
        var id: Int = 0

        @SerializedName("name")
        var name: String? = null

        @SerializedName("name_bn")
        var name_bn: String? = null

        @SerializedName("status")
        var status: Int = 0

        @SerializedName("rank")
        var rank: Int = 0

        @SerializedName("deleted_at")
        var deleted_at: String? = null

        @SerializedName("created_at")
        var created_at: String? = null

        @SerializedName("updated_at")
        var updated_at: String? = null
    }

    inner class hrm_training_category {

        @SerializedName("id")
        var id: Int = 0

        @SerializedName("name")
        var name: String? = null

        @SerializedName("name_bn")
        var name_bn: String? = null

        @SerializedName("status")
        var status: Int = 0
        @SerializedName("deleted_at")
        var deleted_at: String? = null

        @SerializedName("created_at")
        var created_at: String? = null

        @SerializedName("updated_at")
        var updated_at: String? = null
    }

}