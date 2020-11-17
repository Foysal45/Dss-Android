package com.dss.hrms.model

import com.google.gson.annotations.SerializedName

class Office {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("office_type_id")
    val office_type_id: Int? = 0

    @SerializedName("office_name")
    val name: String? = null

    @SerializedName("office_name_bn")
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