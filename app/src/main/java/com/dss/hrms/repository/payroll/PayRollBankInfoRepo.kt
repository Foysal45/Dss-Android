package com.dss.hrms.repository.payroll

import android.app.Application
import android.util.Log
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.PayrollApiService
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class PayRollBankInfoRepo @Inject constructor() {

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var payrollApiService: PayrollApiService

    @Inject
    lateinit var application: Application


    suspend fun getBankList(employeeId: Int?): Any? {

        return withContext(Dispatchers.IO) {
            try {
                var response = payrollApiService?.bankInfo(
                    preparence.getLanguage(), "Bearer ${preparence.getToken()}",
                    employeeId
                )
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                    response.body()
                } else {
                    response?.let {
                        var apiError = ErrorUtils2.parseError(
                            it
                        )
                        apiError
                    }
                }
            } catch (e: Exception) {
                e.message
                Log.e("response", "Response : ${e.message}")
            }
        }
    }


}