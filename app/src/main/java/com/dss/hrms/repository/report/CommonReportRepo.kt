package com.dss.hrms.repository.report

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.retrofit.ReportApiService
import com.dss.hrms.view.report.VacantPositionSummaryValueListener
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

class CommonReportRepo @Inject constructor() {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var reportApiService: ReportApiService

    @Inject
    lateinit var preparence: MySharedPreparence


    suspend fun getVacantPositionSummaryList(map: HashMap<Any, Any?>): Any? {
        return withContext(Dispatchers.IO) {
            try {
                val response = reportApiService.vacantPositionSummaryReport(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map.get("division_id")?.let { it as Int },
                    map.get("district_id")?.let { it as Int },
                    map.get("sixteen_category_id")?.let { it as Int },
                    map.get("head_office_department_id")?.let { it as Int },
                    map.get("head_office_section_id")?.let { it as Int },
                    map.get("head_office_sub_section_id")?.let { it as Int },
                    map.get("designation_id")?.let { it as Int },
                    map.get("office_id")?.let { it as Int },
                    map.get("term")?.let { it as String }
                )
                Log.e(
                    "reportrepo",
                    "repo : ${response?.body()?.code} status : ${response?.body()?.status} "
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
                null
            }
        }
    }

    suspend fun getVacantPositionSummaryList(
        map: HashMap<Any, Any?>,
        vacantPositionSummaryValueListener: VacantPositionSummaryValueListener
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = reportApiService.vacantPositionSummaryReport(
                    preparence.getLanguage()!!,
                    "Bearer ${preparence.getToken()!!}",
                    map.get("division_id")?.let { it as Int },
                    map.get("district_id")?.let { it as Int },
                    map.get("sixteen_category_id")?.let { it as Int },
                    map.get("head_office_department_id")?.let { it as Int },
                    map.get("head_office_section_id")?.let { it as Int },
                    map.get("head_office_sub_section_id")?.let { it as Int },
                    map.get("designation_id")?.let { it as Int },
                    map.get("office_id")?.let { it as Int },
                    map.get("term")?.let { it as String }
                )
                Log.e(
                    "reportrepo",
                    "repo : ${response?.body()?.code} status : ${response?.body()?.status} "
                )
                if (response?.body()?.code == 200 || response?.body()?.code == 201) {
                    vacantPositionSummaryValueListener.valueChange(response?.body()?.data)
                } else {
                    response?.let {
                        var apiError = ErrorUtils2.parseError(
                            it
                        )
                        apiError
                    }
                    vacantPositionSummaryValueListener.valueChange(null)
                }
            } catch (e: Exception) {
                null
                vacantPositionSummaryValueListener.valueChange(null)
            }
        }
    }
}