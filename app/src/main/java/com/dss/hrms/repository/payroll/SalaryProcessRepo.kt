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

class SalaryProcessRepo @Inject constructor() {


    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var payrollApiService: PayrollApiService

    @Inject
    lateinit var application: Application


    suspend fun salaryGenerate(
        employeeId: String?,
        office_id: String?,
        salary_year: String?,
        salary_month: String?
    ): Any? {

        return withContext(Dispatchers.IO) {
            try {
                var response = payrollApiService.salaryGenerate(
                    preparence.getLanguage(), "Bearer ${preparence.getToken()}",
                    salary_year, employeeId, salary_month
                )
                Log.e("salaryprocessresponse", "salary process header : ${response?.body()?.data?.row}")
                Log.e("salaryprocessresponse", "salary process row : ${response?.body()?.data?.row}")
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
               // if (response?.body() == 200 || response?.body() == 201) {
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
                Log.e("salaryprocessresponse", "salary process erro : ${e}")
            }

        }
    }


}