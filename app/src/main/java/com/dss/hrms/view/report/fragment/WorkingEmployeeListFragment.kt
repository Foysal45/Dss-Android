package com.dss.hrms.view.report.fragment

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dss.hrms.databinding.FragmentWorkingEmployeeListBinding
import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.Office
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OfficeDataValueListener
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.spinner.CommonSpinnerAdapter
import com.dss.hrms.viewmodel.CommonViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject


class WorkingEmployeeListFragment : DaggerFragment() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var utilViewModel: UtilViewModel
    lateinit var commonViewModel: CommonViewModel

    lateinit var binding: FragmentWorkingEmployeeListBinding

    var isAlreadyViewCreated = false

    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var officeLeadCategory: SpinnerDataModel? = null
    var office: Office? = null
    var designation: SpinnerDataModel? = null
    var headOfficeBranches: HeadOfficeDepartmentApiResponse.HeadOfficeBranch? = null
    var section: HeadOfficeDepartmentApiResponse.Section? = null
    var subSection: HeadOfficeDepartmentApiResponse.Subsection? = null


    var isOfficeAlreadySet = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!isAlreadyViewCreated) {
            isAlreadyViewCreated = true
            binding = FragmentWorkingEmployeeListBinding.inflate(inflater, container, false)
            init()
            loadDesignationList()
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
//                val url = "url you want to download"
//                val request = DownloadManager.Request(Uri.parse(url))
//                request.setDescription("Some descrition")
//                request.setTitle("Some title")
//// in order for this if to run, you must use the android 3.2 to compile your app
//// in order for this if to run, you must use the android 3.2 to compile your app
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                    request.allowScanningByMediaScanner()
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                }
//                request.setDestinationInExternalPublicDir(
//                    Environment.DIRECTORY_DOWNLOADS,
//                    "name-of-the-file.ext"
//                )
//                val manager =
//                    getSystemService<Any>(activity.DOWNLOAD_SERVICE) as DownloadManager?
//                manager!!.enqueue(request)

            }
        }
        return binding.root

    }




    fun init() {
        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
        utilViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(UtilViewModel::class.java)

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

}