package com.dss.hrms.model

class HeadOfficeDepartmentApiResponse {

    data class HeadOfficeDepartmentResponse(
        val status: String?,
        val code: Int?,
        val message: String?,
        val data: List<HeadOfficeBranch>
    )

    data class HeadOfficeBranch(
        val id: Int?,
        val name: String?,
        val name_bn: String?,
        val status: Int?,
        val sections: List<Section>?
    )

    data class Section(
        val id: Int?,
        val name: String?,
        val name_bn: String?,
        val head_office_department_id: Int?,
        val status: Int?,
        val subsections: List<Subsection>?
    )

    data class Subsection(
        val id: Int?,
        val name: String?,
        val name_bn: String?,
        val head_office_department_id: Int?,
        val status: Int?,
        val sub_subsections: List<SubSubsection>?
    )

    data class SubSubsection(
        val id: Int?,
        val name: String?,
        val name_bn: String?,
        val head_office_department_id: Int?,
        val status: Int?
    )
}