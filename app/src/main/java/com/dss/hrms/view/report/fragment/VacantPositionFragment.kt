package com.dss.hrms.view.report.fragment

import android.Manifest
import android.app.Dialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentVacantPositionBinding
import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.Office
import com.dss.hrms.model.ReportResponse
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OfficeDataValueListener
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.report.VacantPositionSummaryValueListener
import com.dss.hrms.view.report.viewmodel.CommonReportViewModel
import com.dss.hrms.view.spinner.CommonSpinnerAdapter
import com.dss.hrms.viewmodel.CommonViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import java.io.File
import java.util.HashMap
import javax.inject.Inject

class VacantPositionFragment : DaggerFragment() {

    var commonReportViewModel: CommonReportViewModel? = null

    var dataList = arrayListOf<ReportResponse.VacantPositionSummary>()

    lateinit var binding: FragmentVacantPositionBinding


    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var utilViewModel: UtilViewModel
    lateinit var commonViewModel: CommonViewModel

    var isAlreadyViewCreated = false

    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var officeLeadCategory: SpinnerDataModel? = null
    var office: Office? = null
    var designation: SpinnerDataModel? = null
    var headOfficeBranches: HeadOfficeDepartmentApiResponse.HeadOfficeBranch? = null
    var section: HeadOfficeDepartmentApiResponse.Section? = null
    var subSection: HeadOfficeDepartmentApiResponse.Subsection? = null
    var ctx: Context? = null
    var isOfficeAlreadySet = false
    var downloadID: Long = -1
    var dialog: Dialog? = null
    lateinit var downloadManager: DownloadManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!isAlreadyViewCreated) {
            ctx = activity
            isAlreadyViewCreated = true
            binding = FragmentVacantPositionBinding.inflate(inflater, container, false)
            init()
            activity?.registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )


            binding.employeeNameOrId.llBody.visibility = View.GONE
            loadDesignationList()
            binding.officeHeader.tvClose.visibility = View.GONE
            binding.headOfficesBranches.llBody.visibility =
                View.GONE
            binding.branchesWiseSection.llBody.visibility =
                View.GONE
            binding.sectionWiseSubsection.llBody.visibility =
                View.GONE
            commonViewModel.getCommonData("/api/auth/sixteen-category/list")
                ?.observe(viewLifecycleOwner,
                    Observer { list ->
                        list?.let {
                            SpinnerAdapter().setSpinner(
                                binding?.officeLeadCategory?.spinner!!,
                                context,
                                list,
                                null,
                                object : CommonSpinnerSelectedItemListener {
                                    override fun selectedItem(any: Any?) {
                                        officeLeadCategory = any as SpinnerDataModel
                                        officeLeadCategory?.let { oflC ->
                                            oflC?.id?.let {
                                                division = null
                                                district = null
                                                headOfficeBranches = null
                                                section = null
                                                subSection = null
                                                if (it == 1) {
                                                    headOfficeBranches()
                                                    binding.headOfficesBranches.llBody.visibility =
                                                        View.VISIBLE
                                                    binding.branchesWiseSection.llBody.visibility =
                                                        View.VISIBLE
                                                    binding.sectionWiseSubsection.llBody.visibility =
                                                        View.VISIBLE
                                                    binding.division.llBody.visibility = View.GONE
                                                    binding.district.llBody.visibility = View.GONE
                                                } else {
                                                    setDivision()
                                                    binding.headOfficesBranches.llBody.visibility =
                                                        View.GONE
                                                    binding.branchesWiseSection.llBody.visibility =
                                                        View.GONE
                                                    binding.sectionWiseSubsection.llBody.visibility =
                                                        View.GONE
                                                    binding.division.llBody.visibility =
                                                        View.VISIBLE
                                                    binding.district.llBody.visibility =
                                                        View.VISIBLE
                                                }
                                                searchOffice()
                                            }
                                        }
                                    }
                                }
                            )
                        }

                    })

            setDivision()
            commonViewModel.getOffice("/api/auth/office/list/basic")?.observe(viewLifecycleOwner,
                Observer { list ->
                    if (!isOfficeAlreadySet) {
                        setOffice(list)
                    }
                })

            binding.search.setOnClickListener {
                //   onDownload()
                searchVacantPositionSummary()
            }
            //

            binding.btnDownloadPdf.setOnClickListener {
                onDownload()
            }
        }


        return binding.root

    }

    override fun onDestroy() {
        activity?.unregisterReceiver(onDownloadComplete)
        super.onDestroy()
    }

    private fun onDownload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Toast.makeText(
                activity,
                getString(R.string.downloading_see_progess_on_notification_bar),
                Toast.LENGTH_LONG
            ).show()
            downloadFile(
                "" + getString(R.string.vacant_position_summary),
                "" + getString(R.string.vacant_position_summary),
                getDownloadUrl()
            )
        } else {
            requestPermission()
        }

    }

    private fun downloadFile(fileName: String, desc: String, url: String) {
        // fileName -> fileName with extension
        val file = File(activity?.getExternalFilesDir(null), "Dss")
        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle(fileName)
            .setDescription(desc)
            .setDestinationUri(Uri.fromFile(file))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        downloadManager = ctx?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setDestinationInExternalFilesDir(
            activity,
            Environment.DIRECTORY_DOWNLOADS,
            "dsshrm.pdf"
        );

        dialog = CustomLoadingDialog().createLoadingDialog(activity)
        dialog?.show()
        downloadID = downloadManager.enqueue(request)
    }

    private fun getDownloadUrl(): String {
        return "${RetrofitInstance.BASE_URL}/api/employee/summery-report-pdf?language=${preparence.getLanguage()}" +
                "&division_id=${division?.id?.let { it }}&" + "district_id=${district?.id?.let { it }}&office_id=${office?.id?.let { it }}" +
                "&designation_id=${designation?.id?.let { it }}&sixteen_category_id=${officeLeadCategory?.id?.let { it }}" +
                "&head_office_department_id=${headOfficeBranches?.id?.let { it }}&head_office_section_id=${section?.id?.let { it }}" +
                "&head_office_sub_section_id=${subSection?.id?.let { it }}"
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

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID === id) {
                dialog?.dismiss()
                Toast.makeText(activity, "Download Completed", Toast.LENGTH_SHORT).show()
                Log.e(
                    "uri",
                    "uri.......................................................................${
                        downloadManager.getUriForDownloadedFile(downloadID)
                    }"
                )
                try {
                    val action =
                        VacantPositionFragmentDirections.actionVacantPositionFragmentToPdfViewerFragment(
                            downloadManager.getUriForDownloadedFile(downloadID).toString(),
                            getString(R.string.vacant_position_summary)
                        )

                    findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("error", e.message.toString());
                }
            }
        }
    }

    fun searchVacantPositionSummary() {
        var dialog = CustomLoadingDialog().createLoadingDialog(context)
        commonReportViewModel?.getVacantPositionSummaryList(getMapData(),
            object : VacantPositionSummaryValueListener {
                override fun valueChange(list: List<ReportResponse.VacantPositionSummary>?) {
                    Log.e("officelist", " list : " + list?.size)
                    dialog?.dismiss()


                }
            })

        commonReportViewModel?.vacantPositionSummary?.observe(viewLifecycleOwner, Observer { list ->
            val templist = arrayListOf<ReportResponse.VacantPositionSummary>()
            dialog?.dismiss()
            if (list == null){
                return@Observer
            }
            templist += list
            if (templist != null && templist?.size!! > 0) {
                var permitted = 0
                var working = 0
                var empty = 0

                templist?.forEach({ element ->
                    element.permitted?.let { permitted += it }
                    element.working?.let { working += it }
                    element.empty?.let { empty += it }
                })

                templist.add(
                    ReportResponse.VacantPositionSummary(
                        getString(R.string.total),
                        getString(R.string.total_bn), permitted, working, empty, 0, 0, null
                    )
                )
                dataList = templist
                dataList?.let {
                    if (it.size > 0) {
                        val action =
                            VacantPositionFragmentDirections.actionVacantPositionFragmentToVacantPositionReportShowingFragment(
                                dataList?.toTypedArray()
                            )
                        findNavController().navigate(action)
//                                Navigation.findNavController(binding.root)
//                                    .navigate(R.id.action_searchEmployeeFragment_to_employeeBottomSheetFragment)
                    }
                }
            } else {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_LONG).show()
            }

        })

    }

    fun init() {
        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
        utilViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(UtilViewModel::class.java)
        commonReportViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(CommonReportViewModel::class.java)
        commonViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(CommonViewModel::class.java)
    }

    fun setDivision() {
        commonViewModel.getCommonData("/api/auth/division/list")?.observe(viewLifecycleOwner,
            Observer { list ->
                list?.let {
                    SpinnerAdapter().setSpinner(
                        binding?.division?.spinner!!,
                        context,
                        list,
                        null,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                division = any as SpinnerDataModel
                                division?.id?.let {
                                    district = null
                                    searchOffice()
                                }
                                getDistrict(if (division?.id == null) 1 else division?.id, null)
                            }

                        }
                    )
                }
            })

    }


    fun searchOffice() {
        Log.e("myofficemapdata", "officemap data : ${getMapData()}")
        commonRepo.getOfficeWithWhereClause(
            getMapData(),
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    Log.e("officelist", " list : " + list?.size)
                    setOffice(list)
                }
            })
    }


    fun setOffice(list: List<Office>?) {
        isOfficeAlreadySet = true
        list?.let {
            SpinnerAdapter().setOfficeSpinner(
                binding?.office?.spinner!!,
                context,
                list,
                0,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        office = any as Office

                        if (office?.id != null) {
                            office?.id?.let { loadDesignation(office?.id) }
                        } else {
                            loadDesignationList()
                        }

                        Log.e("selected item", " item : " + office?.name)
                    }

                }
            )
        }
    }


    fun getDistrict(divisionId: Int?, districtId: Int?) {
        commonViewModel.getDistrict(divisionId)?.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                SpinnerAdapter().setSpinner(
                    binding?.district?.spinner!!,
                    context,
                    list,
                    districtId,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {
                            district = any as SpinnerDataModel
                            district?.id?.let {
                                searchOffice()
                            }

                        }

                    }
                )
            }
        })
    }

    fun headOfficeBranches() {
        utilViewModel.getHeadOfficeDepartment()
            ?.observe(viewLifecycleOwner,
                Observer { branches ->
                    branches?.let {
                        CommonSpinnerAdapter().setBranchSpinner(
                            binding?.headOfficesBranches?.spinner!!,
                            context,
                            branches,
                            null,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    any?.let {
                                        headOfficeBranches =
                                            any as HeadOfficeDepartmentApiResponse.HeadOfficeBranch
                                        headOfficeBranches?.id?.let {
                                            section = null
                                            subSection = null
                                            searchOffice()
                                        }
                                        headOfficeBranches?.sections?.let { it1 ->
                                            setSection(it1)
                                        }
                                    }
                                }

                            }
                        )

                    }
                })
    }

    fun setSection(dataList: List<HeadOfficeDepartmentApiResponse.Section>) {
        dataList?.let { list ->
            CommonSpinnerAdapter().setSectionSpinner(
                binding?.branchesWiseSection?.spinner!!,
                context,
                list,
                null,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        any?.let {
                            section = any as HeadOfficeDepartmentApiResponse.Section
                            section?.id?.let {
                                subSection = null
                                searchOffice()
                            }
                            section?.subsections?.let { it1 -> setSubSection(it1) }
                        }
                    }

                }
            )

        }

    }

    fun setSubSection(dataList: List<HeadOfficeDepartmentApiResponse.Subsection>) {
        dataList?.let { list ->
            CommonSpinnerAdapter().setSubSectionSpinner(
                binding?.sectionWiseSubsection?.spinner!!,
                context,
                list,
                null,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        any?.let {
                            subSection = any as HeadOfficeDepartmentApiResponse.Subsection
                            subSection?.id?.let {
                                searchOffice()
                            }
                        }
                    }

                }
            )
        }
    }

    fun loadDesignation(officeId: Int?) {
        commonViewModel.getDesignationData("/api/auth/office/${officeId}")
            ?.observe(viewLifecycleOwner,
                Observer { list ->
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.designation.spinner,
                            context,
                            list,
                            0,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    designation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                })
    }

    fun loadDesignationList() {
        commonRepo.getCommonData("/api/auth/designation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.designation.spinner,
                            context,
                            list,
                            0,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    designation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

    }

    fun getMapData(): HashMap<Any, Any?> {
        var map = HashMap<Any, Any?>()
        officeLeadCategory?.id?.let {
            map.put("sixteen_category_id", it)
        }
        headOfficeBranches?.id?.let {
            map.put("head_office_department_id", it)
        }
        section?.id?.let {
            map.put("head_office_section_id", it)
        }
        subSection?.id?.let {
            map.put("head_office_sub_section_id", it)
        }
        division?.id?.let {
            map.put("division_id", it)
        }
        district?.id?.let {
            map.put("district_id", it)
        }
        designation?.id?.let {
            map.put("designation_id", it)
        }
        return map
    }
}