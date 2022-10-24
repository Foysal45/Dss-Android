package com.dss.hrms.view.report.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogCommonReportSearchBinding
import com.dss.hrms.model.*

import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.repository.report.CommonReportRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.HeadOfficeDepartmentDataValueListener
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
import java.util.HashMap
import javax.inject.Inject

class CommonReportSearchingDialog @Inject constructor() {

    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var commonReportRepo: CommonReportRepo

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var utilViewModel: UtilViewModel
    lateinit var commonViewModel: CommonViewModel

    lateinit var dialogCustome: Dialog
    lateinit var binding: DialogCommonReportSearchBinding

    lateinit var dataList: List<RoleWiseEmployeeResponseClass.RoleWiseEmployee>

    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var officeLeadCategory: SpinnerDataModel? = null
    var office: Office? = null
    var designation: SpinnerDataModel? = null
    var headOfficeBranches: HeadOfficeDepartmentApiResponse.HeadOfficeBranch? = null
    var section: HeadOfficeDepartmentApiResponse.Section? = null
    var subSection: HeadOfficeDepartmentApiResponse.Subsection? = null
    var context: Context? = null
    var isOfficeAlreadySet = false

    var commonReportViewModel: CommonReportViewModel? = null

    fun showCommonReportSearchDialog(
        context: Context?, utilViewModel: UtilViewModel,
        commonReportViewModel: CommonReportViewModel?
    ) {
        dialogCustome = context?.let { Dialog(it) }!!
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.context = context
        this.commonReportViewModel = commonReportViewModel
        this.utilViewModel = utilViewModel
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_common_report_search,
            null,
            false
        )
        binding?.getRoot()?.let { dialogCustome?.setContentView(it) }

        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )


        binding?.officeHeader?.tvClose?.setOnClickListener {
            dialogCustome.dismiss()
        }

        loadDesignationList()

        binding.headOfficesBranches.llBody.visibility =
            View.GONE
        binding.branchesWiseSection.llBody.visibility =
            View.GONE
        binding.sectionWiseSubsection.llBody.visibility =
            View.GONE
        headOfficeBranches()

        commonRepo.getCommonData("/api/auth/sixteen-category/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
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
                                                binding.headOfficesBranches.llBody.visibility = View.VISIBLE
                                                binding.branchesWiseSection.llBody.visibility = View.VISIBLE
                                                binding.sectionWiseSubsection.llBody.visibility = View.VISIBLE
                                                binding.division.llBody.visibility = View.GONE
                                                binding.district.llBody.visibility = View.GONE
                                            } else {
                                                setDivision()
                                                binding.headOfficesBranches.llBody.visibility = View.GONE
                                                binding.branchesWiseSection.llBody.visibility = View.GONE
                                                binding.sectionWiseSubsection.llBody.visibility = View.GONE
                                                binding.division.llBody.visibility = View.VISIBLE
                                                binding.district.llBody.visibility = View.VISIBLE
                                            }
                                            //searchOffice()
                                        }
                                    }
                                }
                            }
                        )
                    }
                }

            })

        setDivision()
        commonRepo.getOffice("/api/auth/office/list/basic",
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    if (!isOfficeAlreadySet) {
                        setOffice(list)
                    }
                }
            })

        binding.search.setOnClickListener {
            searchVacantPositionSummary()
        }
        dialogCustome?.show()
    }


    fun searchVacantPositionSummary() {
        var dialog = CustomLoadingDialog().createLoadingDialog(context)
        commonReportViewModel?.getVacantPositionSummaryList(getMapData(),
            object : VacantPositionSummaryValueListener {
                override fun valueChange(list: List<ReportResponse.VacantPositionSummary>?) {
                    Log.e("officelist", " list : " + list?.size)
                    dialog?.dismiss()
                    dialogCustome?.dismiss()
                }
            })
    }

//    fun searchOffice() {
//        var dialog = CustomLoadingDialog().createLoadingDialog(context)
//        commonRepo.getOfficeWithWhereClause(
//            getMapData(),
//            object : OfficeDataValueListener {
//                override fun valueChange(list: List<Office>?) {
//                    Log.e("officelist", " list : " + list?.size)
//                    dialog?.dismiss()
//                    dialogCustome.dismiss()
//                }
//            })
//    }


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

    fun setDivision() {
        commonRepo.getCommonData("/api/auth/division/list", object : CommonDataValueListener {
            override fun valueChange(list: List<SpinnerDataModel>?) {
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
                                   // searchOffice()
                                }
                                getDistrict(if (division?.id == null) 1 else division?.id, null)
                            }

                        }
                    )
                }
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
        commonRepo.getDistrict(divisionId, object : CommonDataValueListener {
            override fun valueChange(list: List<SpinnerDataModel>?) {
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
                                   // searchOffice()
                                }

                            }

                        }
                    )
                }
            }
        })

    }

    fun headOfficeBranches() {
        utilViewModel.getHeadOfficeDepartment(object : HeadOfficeDepartmentDataValueListener {
            override fun valueChange(branches: List<HeadOfficeDepartmentApiResponse.HeadOfficeBranch>?) {
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
                                       // searchOffice()
                                    }
                                    headOfficeBranches?.sections?.let { it1 ->
                                        setSection(it1)
                                    }
                                }
                            }

                        }
                    )

                }
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
                                //searchOffice()
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
                               // searchOffice()
                            }
                        }
                    }

                }
            )
        }
    }

    fun loadDesignation(officeId: Int?) {
        commonRepo.getDesignationData("/api/auth/office/${officeId}",
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