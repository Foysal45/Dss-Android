package com.dss.hrms.view.leave.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.hrms.repository.leave.LeaveApplicationRepo
import com.dss.hrms.view.leave.model.LeaveSummaryApiResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class LeaveSummaryViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var leaveApplicationRepo: LeaveApplicationRepo


    private var _leaveSummaryHeader = MutableLiveData<List<LeaveSummaryApiResponse.Header>>()
    var leaveSummaryHeader: LiveData<List<LeaveSummaryApiResponse.Header>> = _leaveSummaryHeader
    private var _leaveSummaryRow = MutableLiveData<LeaveSummaryApiResponse.Row>()
    var leaveSummaryRow: LiveData<LeaveSummaryApiResponse.Row> = _leaveSummaryRow


    fun getLeaveSummary(employeeId: String?) {
        viewModelScope.launch {
            var response = leaveApplicationRepo.getLeaveSummary(employeeId)
            if (response is LeaveSummaryApiResponse.LeaveSummaryResponse) {
                _leaveSummaryRow.postValue(response.data?.row)
                _leaveSummaryHeader.postValue(response.data?.header)
                Log.e("totalleave","totalleave ............................${response.data?.row}")

            } else {
                _leaveSummaryHeader.postValue(null)
                _leaveSummaryRow.postValue(null)
            }
        }
    }
}