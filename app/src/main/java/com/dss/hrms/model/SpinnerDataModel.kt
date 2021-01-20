package com.dss.hrms.model

import com.google.gson.annotations.SerializedName

class SpinnerDataModel {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("name_bn")
    var name_bn: String? = null

    @SerializedName("disability_type")
    var disability_type: String? = null

    @SerializedName("disability_type_bn")
    var disability_type_bn: String? = null

    @SerializedName("disability_degree")
    var disability_degree: String? = null

    @SerializedName("disability_degree_bn")
    var disability_degree_bn: String? = null

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

    @SerializedName("paysacle")
    var paysacle: List<Paysacle>? = null


}