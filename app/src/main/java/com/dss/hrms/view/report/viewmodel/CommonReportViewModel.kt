package com.dss.hrms.view.report.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.model.ReportResponse
import com.dss.hrms.repository.payroll.SalaryProcessRepo
import com.dss.hrms.repository.report.CommonReportRepo
import com.dss.hrms.view.payroll.model.Header
import com.dss.hrms.view.payroll.model.Row
import com.dss.hrms.view.payroll.model.SalaryGenerateResponse
import com.dss.hrms.view.report.VacantPositionSummaryValueListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommonReportViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var commonReportRepo: CommonReportRepo

    private var _vacantPositionSummary: MutableLiveData<List<ReportResponse.VacantPositionSummary>> =
        MutableLiveData()
    var vacantPositionSummary: LiveData<List<ReportResponse.VacantPositionSummary>> =
        _vacantPositionSummary

    fun getVacantPositionSummaryList(
        map: HashMap<Any, Any?>,
        vacantPositionSummaryValueListener: VacantPositionSummaryValueListener
    ) {
        viewModelScope.launch {

            var response =
                commonReportRepo.getVacantPositionSummaryList(map)

            if (response is ReportResponse.VacantPositionSummaryResponse) {
                _vacantPositionSummary.postValue(response.data)
                vacantPositionSummaryValueListener.valueChange(response.data)
            } else {
                _vacantPositionSummary.postValue(null)
                vacantPositionSummaryValueListener.valueChange(null)
            }
        }
    }
}