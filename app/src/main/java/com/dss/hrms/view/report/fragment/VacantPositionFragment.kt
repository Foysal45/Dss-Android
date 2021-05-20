package com.dss.hrms.view.report.fragment

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogCommonReportSearchBinding
import com.dss.hrms.databinding.FragmentVacantPositionBinding
import com.dss.hrms.model.ReportResponse
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.view.leave.adapter.LeaveSummaryAdapter
import com.dss.hrms.view.leave.model.LeaveSummaryApiResponse
import com.dss.hrms.view.report.adapter.VacantPositionSummaryAdapter
import com.dss.hrms.view.report.dialog.CommonReportSearchingDialog
import com.dss.hrms.view.report.viewmodel.CommonReportViewModel
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class VacantPositionFragment : DaggerFragment() {

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

    lateinit var binding: FragmentVacantPositionBinding
    var ctx: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVacantPositionBinding.inflate(inflater, container, false)
        ctx = activity
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
                val templist= arrayListOf<ReportResponse.VacantPositionSummary>()
                templist += list
                if (templist != null && templist?.size!! > 0) {
                    var permitted=0
                    var working=0
                    var empty=0

                    templist?.forEach({element->
                        element.permitted?.let { permitted+=it }
                        element.working?.let { working+=it }
                        element.empty?.let { empty+=it }
                    })

                    templist.add(ReportResponse.VacantPositionSummary(getString(R.string.total),
                        getString(R.string.total_bn),permitted,working,empty,0,0,null))
                    dataList =templist
                    binding.llDownload.visibility = View.VISIBLE
                    prepareRecyleView()
                } else {
                    binding.llDownload.visibility = View.GONE
                }

            })
        }

        binding.btnDownload.setOnClickListener {

            onDownload()
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

    private fun onDownload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Toast.makeText(
                activity,
                getString(R.string.downloading_see_progess_on_notification_bar),
                Toast.LENGTH_LONG
            ).show()
            downloadFile(
                ""+getString(R.string.vacant_position_summary),
                ""+getString(R.string.vacant_position_summary),
                getDownloadUrl()
            )
        } else {
            requestPermission()
        }

    }

    private fun downloadFile(fileName: String, desc: String, url: String) {
        // fileName -> fileName with extension
        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle(fileName)
            .setDescription(desc)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)

            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        val downloadManager = ctx?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadID = downloadManager.enqueue(request)

        //   To Store file in External App-Specific Directory
        //  .setDestinationInExternalFilesDir(context, Environment.DIRECTORY_MUSIC,fileName)

        //   To Store file in External Public Directory
        // .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)

    }

    private fun getDownloadUrl(): String {
        return "${RetrofitInstance.BASE_URL}/api/employee/summery-report-pdf?language=${preparence.getLanguage()}" +
                "&division_id=${commonReportSearchingDialog?.division?.id?.let { it }}&" + "district_id=${commonReportSearchingDialog?.district?.id?.let { it }}&office_id=${commonReportSearchingDialog?.office?.id?.let { it }}" +
                "&designation_id=${commonReportSearchingDialog?.designation?.id?.let { it }}&sixteen_category_id=${commonReportSearchingDialog?.officeLeadCategory?.id?.let { it }}" +
                "&head_office_department_id=${commonReportSearchingDialog?.headOfficeBranches?.id?.let { it }}&head_office_section_id=${commonReportSearchingDialog?.section?.id?.let { it }}" +
                "&head_office_sub_section_id=${commonReportSearchingDialog?.subSection?.id?.let { it }}"
    }

    private fun requestPermission() {
        activity?.let {
            val result = ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                12
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12) {
            onDownload()
        }
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