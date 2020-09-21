package com.dss.hrms.network.model.local_training.response

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
    @SerializedName("course_title")
    private var course_title: String?,
    @SerializedName("name_of_institute")
    private var name_of_institute: String?,
    @SerializedName("from_date")
    private var from_date: String?,
    @SerializedName("to_date")
    private var to_date: String?,
    @SerializedName("certificate")
    private var certificate: String?
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

    fun setCourse_title(course_title: String) {
        this.course_title = course_title
    }

    fun getCourse_title(): String {
        if (course_title.equals(null)) {
            return ""
        }
        return course_title!!
    }

    fun setName_of_institute(name_of_institute: String) {
        this.name_of_institute = name_of_institute
    }

    fun getName_of_institute(): String {
        if (name_of_institute.equals(null)) {
            return ""
        }
        return name_of_institute!!
    }

    fun setFrom_date(from_date: String) {
        this.from_date = from_date
    }

    fun getFrom_date(): String {
        if (from_date.equals(null)) {
            return ""
        }
        return from_date!!
    }
   fun setTo_date(to_date: String) {
        this.to_date = to_date
    }

    fun getTo_date(): String {
        if (to_date.equals(null)) {
            return ""
        }
        return to_date!!
    }
   fun setCertificate(certificate: String) {
        this.certificate = certificate
    }

    fun getCertificate(): String {
        if (certificate.equals(null)) {
            return ""
        }
        return certificate!!
    }


}