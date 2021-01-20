package com.dss.hrms.model

import com.google.gson.annotations.SerializedName

class Paysacle {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("grade_id")
    var grade_id: Int? = null

    @SerializedName("amount")
    var amount: String? = null

    @SerializedName("deleted_at")
    var deleted_at: String? = null

    @SerializedName("created_at")
    var created_at: String? = null

    @SerializedName("updated_at")
    var updated_at: String? = null

}