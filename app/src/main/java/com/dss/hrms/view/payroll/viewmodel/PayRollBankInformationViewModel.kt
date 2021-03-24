package com.dss.hrms.view.payroll.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.repository.payroll.PayRollBankInfoRepo
import com.dss.hrms.view.payroll.model.PayRollBankInfo
import com.dss.hrms.view.payroll.model.PayrollBankInformationResponse
import com.dss.hrms.view.training.model.BudgetAndSchedule
import kotlinx.coroutines.launch
import javax.inject.Inject

class PayRollBankInformationViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {


    private var _bankInfo = MutableLiveData<List<PayRollBankInfo?>>()
    var bankInfo: LiveData<List<PayRollBankInfo?>> = _bankInfo


    @Inject
    lateinit var payRollBankInfoRepo: PayRollBankInfoRepo

    fun getBankInfo(id: Int?) {
        viewModelScope.launch {
            var response = payRollBankInfoRepo?.getBankList(id)
            if (response is PayrollBankInformationResponse) {
                _bankInfo.postValue(response.data)
            } else {
                _bankInfo.postValue(null)
            }
        }
    }

}