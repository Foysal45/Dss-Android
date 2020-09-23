package com.dss.hrms.network.model.educational_qualification.response

import com.google.gson.annotations.SerializedName

/**
 * Data.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 21-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class Data(
    @SerializedName("employee_id")
    private var employeeId: Int?,
    @SerializedName("name_of_degree")
    private var nameDegree: String?,
    @SerializedName("name_of_institute")
    private var nameInstitute: String?,
    @SerializedName("board_university")
    private var board_university: String?,
    @SerializedName("passing_year")
    private var passing_year: String?,
    @SerializedName("division_cgpa")
    private var division_cgpa: String?
) {
    fun setEmployeeId(employeeId: Int) {
        this.employeeId = employeeId
    }

    fun getEmployeeId(): Int {
        if (employeeId==null) {
            employeeId!!
            return 0
        }
        return employeeId!!
    }

    fun setNameDegree(nameDegree: String) {
        this.nameDegree = nameDegree
    }

    fun getNameDegree(): String {
        if (nameDegree.equals(null)) {
            return ""
        }
        return nameDegree!!
    }

 fun setNameInstitute(nameInstitute: String) {
        this.nameInstitute = nameInstitute
    }

    fun getNameInstitute(): String {
        if (nameInstitute.equals(null)) {
            return ""
        }
        return nameInstitute!!
    }

    fun setBoard(board_university: String) {
        this.board_university = board_university
    }

    fun getBoard(): String {
        if (board_university.equals(null)) {
            return ""
        }
        return board_university!!
    }

    fun setYear(passing_year: String) {
        this.passing_year = passing_year
    }

    fun getYear(): String {
        if (passing_year.equals(null)) {
            return ""
        }
        return passing_year!!
    }

    fun setCgpa(division_cgpa: String) {
        this.division_cgpa = division_cgpa
    }

    fun getCgpa(): String {
        if (division_cgpa.equals(null)) {
            return ""
        }
        return division_cgpa!!
    }



}