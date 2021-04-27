package com.dss.hrms.view.report.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogCommonReportSearchBinding
import com.dss.hrms.databinding.FragmentVacantPositionBinding
import com.dss.hrms.model.ReportResponse
import com.dss.hrms.view.leave.adapter.LeaveSummaryAdapter
import com.dss.hrms.view.leave.model.LeaveSummaryApiResponse
import com.dss.hrms.view.report.adapter.VacantPositionSummaryAdapter
import com.dss.hrms.view.report.dialog.CommonReportSearchingDialog
import com.dss.hrms.view.report.viewmodel.CommonReportViewModel
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class VacantPositionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var utilViewModel: UtilViewModel

    var commonReportViewModel: CommonReportViewModel? = null

    // var dataList = arrayListOf<ReportResponse.VacantPositionSummary>()
    var dataList: List<ReportResponse.VacantPositionSummary>? = null

    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter: VacantPositionSummaryAdapter

    @Inject
    lateinit var commonReportSearchingDialog: CommonReportSearchingDialog

    lateinit var binding: FragmentVacantPositionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVacantPositionBinding.inflate(inflater, container, false)
        init()
        binding.llSearch.setOnClickListener {
            commonReportSearchingDialog.showCommonReportSearchDialog(
                activity,
                utilViewModel,
                commonReportViewModel
            )
        }


        commonReportViewModel?.apply {
            vacantPositionSummary.observe(viewLifecycleOwner, Observer { list ->
                list?.let {
                    dataList = it
                    prepareRecyleView()
                }

            })
        }

        return binding.root
    }

    fun prepareRecyleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        activity?.let {
            adapter.setInitialData(
                dataList, it
            )
        }
        binding.recyclerView.adapter = adapter
    }


    fun init() {
        utilViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(UtilViewModel::class.java)

        commonReportViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(CommonReportViewModel::class.java)
    }
}