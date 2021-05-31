package com.dss.hrms.view.training.model

data class HonorariumHeadResponse(val message:String?,val code:Int?,val data:List<HonorariumHead>)

data class HonorariumHead(
    val id: Int?,
    val honorarium_type_id: Int?,
    val honorarium_date: String?,
    val status: Int?
)
