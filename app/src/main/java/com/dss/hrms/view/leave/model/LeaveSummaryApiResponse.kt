package com.dss.hrms.view.leave.model

class LeaveSummaryApiResponse {



    data class LeaveSummaryResponse(
        val status: String?,
        val message: String?,
        val code: Int?,
        val data: LeaveSummaryData?
    )

    data class LeaveSummaryData(
        val header: List<Header>?,
        val row: Row
    )

    data class Header(
        val name: String?,
        val name_bn: String?
    )

    data class Row(
        val total: List<Int>?,
        val applied: List<Int>?,
        val enjoyed: List<Int>?,
        val remaining_balance: List<Int>?
    )

    class LeaveSummaryModifiedModel {
        var name: String? = null
        var name_bn: String? = null
        var total: Int? = null
        var applied: Int? = null
        var enjoyed: Int? = null
        var remaining_balance: Int? = null

        constructor(
            name: String?,
            name_bn: String?,
            total: Int?,
            applied: Int?,
            enjoyed: Int?,
            remaining_balance: Int?
        ) {
            this.name = name
            this.name_bn = name_bn
            this.total = total
            this.applied = applied
            this.enjoyed = enjoyed
            this.remaining_balance = remaining_balance
        }
    }

}