package com.dss.hrms.network.model.permanent_address.response

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
    @SerializedName("village_house_no")
    private var villageHouseNo: String?,
    @SerializedName("road_word_no")
    private var road_word_no: String?,
    @SerializedName("post_office")
    private var post_office: String?,
    @SerializedName("police_station")
    private var police_station: String?,
    @SerializedName("district_id")
    private var district_id: String?,
    @SerializedName("district_id")
    private var district_id: String?,
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