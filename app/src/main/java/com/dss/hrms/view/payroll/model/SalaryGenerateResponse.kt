package com.dss.hrms.view.payroll.model


data class SalaryGenerateResponse(
    val status: String?,
    val message: String?,
    val code: Int?,
    val data: SalaryGenerate
)

data class SalaryGenerate(
    val header: List<Header>,
    val row: List<List<Row>>
)

data class Header(val name: String?, val name_bn: String?, val type: String?)
data class Row(val name: String?, val name_bn: String?, val amount: Int?, val type: String?)
