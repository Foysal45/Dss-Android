package com.dss.hrms.view.payroll.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.repository.payroll.SalaryProcessRepo
import com.dss.hrms.view.payroll.model.Header
import com.dss.hrms.view.payroll.model.Row
import com.dss.hrms.view.payroll.model.SalaryGenerateResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class SalaryGenerateViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var salaryProcessReop: SalaryProcessRepo

    private var _salaryHeader: MutableLiveData<List<Header>> =
        MutableLiveData()
    var salaryHeader: LiveData<List<Header>> = _salaryHeader

    private var _salaryRow: MutableLiveData<List<Row>> =
        MutableLiveData()
    var salaryRow: LiveData<List<Row>> = _salaryRow


    fun getSalaryData(
        employeeId: String?,
        office_id: String?,
        salary_year: String?,
        salary_month: String?
    ) {
        viewModelScope.launch {

            var response =
                salaryProcessReop.salaryGenerate(employeeId, office_id, salary_year, salary_month)

            if (response is SalaryGenerateResponse) {
                _salaryHeader.postValue(response.data.header)
                response?.data?.row?.let {
                    if (it.size > 0) _salaryRow.postValue(response.data.row[0]) else _salaryRow.postValue(
                        null
                    )
                }
            } else {
                _salaryHeader.postValue(null)
                _salaryRow.postValue(null)
            }
        }
    }
}