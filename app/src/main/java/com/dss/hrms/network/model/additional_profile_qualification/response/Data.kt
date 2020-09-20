package com.dss.hrms.network.model.additional_profile_qualification.response

import com.google.gson.annotations.SerializedName

/**
 * Data.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 20-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class Data(
    @SerializedName("employee_id")
    private var employeeId: Int?,
    @SerializedName("qualification_name")
    private var qualificationName: String?,
    @SerializedName("qualification_details")
    private var qualificationDetails: String?
) {
    fun setEmployeeId(employeeId: Int) {
        this.employeeId = employeeId
    }

    fun getEmployeeId(): Int {
        if (employeeId==null) {
            return 0
        }
        return employeeId!!
    }

    fun setQualificationName(qualificationName: String) {
        this.qualificationName = qualificationName
    }

    fun getQualificationName(): String {
        if (qualificationName.equals(null)) {
            return ""
        }
        return qualificationName!!
    }

    fun setQualificationDetails(qualificationDetails: String) {
        this.qualificationDetails = qualificationDetails
    }

    fun getQualificationDetails(): String {
        if (qualificationDetails.equals(null)) {
            return ""
        }
        return qualificationDetails!!
    }



}