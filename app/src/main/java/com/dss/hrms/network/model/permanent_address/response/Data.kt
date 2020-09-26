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
    private var district_id: String?
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

    fun setVillage_house_no(villageHouseNo: String) {
        this.villageHouseNo = villageHouseNo
    }

    fun getVillage_house_no(): String {
        if (villageHouseNo.equals(null)) {
            return ""
        }
        return villageHouseNo!!
    }


    fun setRoad_word_no(road_word_no: String) {
        this.road_word_no = road_word_no
    }


    fun getRoad_word_no(): String {
        if (road_word_no.equals(null)) {
            return ""
        }
        return road_word_no!!
    }




}