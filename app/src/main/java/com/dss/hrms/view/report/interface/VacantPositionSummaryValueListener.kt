package com.dss.hrms.view.report

import com.dss.hrms.model.ReportResponse
import com.dss.hrms.model.SpinnerDataModel

interface VacantPositionSummaryValueListener {
    fun valueChange(list: List<ReportResponse.VacantPositionSummary>?);
}