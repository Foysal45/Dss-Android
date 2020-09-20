package com.dss.hrms.network.model.honours_award.response

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
    @SerializedName("award_title")
    private var awardTitle: String?,
    @SerializedName("award_details")
    private var awardDetails: String?,
    @SerializedName("award_date")
    private var awardDate: String?
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

    fun setAwardTitle(awardTitle: String) {
        this.awardTitle = awardTitle
    }

    fun getAwardTitle(): String {
        if (awardTitle.equals(null)) {
            return ""
        }
        return awardTitle!!
    }

    fun setAwardDetails(awardDetails: String) {
        this.awardDetails = awardDetails
    }

    fun getAwardDetails(): String {
        if (awardDetails.equals(null)) {
            return ""
        }
        return awardDetails!!
    }

    fun setAwardDate(awardDate: String) {
        this.awardDate = awardDate
    }

    fun getAwardDate(): String {
        if (awardDate.equals(null)) {
            return ""
        }
        return awardDate!!
    }


}