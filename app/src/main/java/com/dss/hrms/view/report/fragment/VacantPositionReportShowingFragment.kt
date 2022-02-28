package com.dss.hrms.view.report.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentEmployeeBottomSheetBinding
import com.dss.hrms.databinding.FragmentVacantPositionReportShowingBinding
import com.dss.hrms.model.ReportResponse
import com.dss.hrms.view.messaging.fragment.EmployeeBottomSheetFragmentArgs
import com.dss.hrms.view.report.adapter.VacantPositionSummaryAdapter
import com.dss.hrms.view.report.dialog.CommonReportSearchingDialog
import com.dss.hrms.view.report.viewmodel.CommonReportViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class VacantPositionReportShowingFragment : DaggerFragment() {

    private val args by navArgs<VacantPositionReportShowingFragmentArgs>()


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var utilViewModel: UtilViewModel

    @Inject
    lateinit var preparence: MySharedPreparence
    var commonReportViewModel: CommonReportViewModel? = null

    // var dataList = arrayListOf<ReportResponse.VacantPositionSummary>()
    var dataList: List<ReportResponse.VacantPositionSummary>? = null

    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter: VacantPositionSummaryAdapter

    @Inject
    lateinit var commonReportSearchingDialog: CommonReportSearchingDialog
    lateinit var binding: FragmentVacantPositionReportShowingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVacantPositionReportShowingBinding.inflate(inflater, container, false)
        dataList = args.vacantPositionSummary?.toList()
        dataList?.let {
            prepareRecyleView()
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

}