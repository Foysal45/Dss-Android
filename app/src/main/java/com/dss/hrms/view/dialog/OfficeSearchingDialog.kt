package com.dss.hrms.view.dialog

import android.app.Dialog
import android.content.Context
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogOfficeSearchBinding
import com.dss.hrms.databinding.DialogPayrollManagementLayoutBinding
import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.Office
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.spinner.CommonSpinnerAdapter
import com.dss.hrms.viewmodel.CommonViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import javax.inject.Inject

class OfficeSearchingDialog @Inject constructor() {

    @Inject
    lateinit var commonRepo: CommonRepo

    lateinit var dialogCustome: Dialog
    lateinit var binding: DialogOfficeSearchBinding

    lateinit var utilViewModel: UtilViewModel
    lateinit var commonViewModel: CommonViewModel

    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var officeLeadCategory: SpinnerDataModel? = null
    var office: Office? = null
    var designation: SpinnerDataModel? = null
    var headOfficeBranches: HeadOfficeDepartmentApiResponse.HeadOfficeBranch? = null
    var section: HeadOfficeDepartmentApiResponse.Section? = null
    var subSection: HeadOfficeDepartmentApiResponse.Subsection? = null

//
//    fun showOfficeSearchDialog(context: Context?) {
//        dialogCustome = context?.let { Dialog(it) }!!
//        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        binding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),
//            R.layout.dialog_office_search,
//            null,
//            false
//        )
//        binding?.getRoot()?.let { dialogCustome?.setContentView(it) }
//        var window: Window? = dialogCustome?.getWindow()
//        window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        commonRepo.getAllDistrict(
//            object : CommonDataValueListener {
//                override fun valueChange(list: List<SpinnerDataModel>?) {
//                    list?.let {
//                        SpinnerAdapter().setSpinner(
//                            binding.district?.spinner!!,
//                            context,
//                            list,
//                            null,
//                            object : CommonSpinnerSelectedItemListener {
//                                override fun selectedItem(any: Any?) {
//                                    district = any as SpinnerDataModel
//                                }
//                            }
//                        )
//                    }
//                }
//
//            }
//        )
//
//
//        binding.headOfficesBranches.llBody.visibility =
//            View.GONE
//        binding.branchesWiseSection.llBody.visibility =
//            View.GONE
//        binding.sectionWiseSubsection.llBody.visibility =
//            View.GONE
//        headOfficeBranches()
//
//        commonRepo.getCommonData("/api/auth/sixteen-category/list",object : CommonDataValueListener{
//            override fun valueChange(list: List<SpinnerDataModel>?) {
//                list?.let {
//                    SpinnerAdapter().setSpinner(
//                        binding?.officeLeadCategory?.spinner!!,
//                        context,
//                        list,
//                        null,
//                        object : CommonSpinnerSelectedItemListener {
//                            override fun selectedItem(any: Any?) {
//                                officeLeadCategory = any as SpinnerDataModel
//                                officeLeadCategory?.let { oflC ->
//                                    oflC?.id?.let {
//                                        if (it == 1) {
//                                            binding.headOfficesBranches.llBody.visibility =
//                                                View.VISIBLE
//                                            binding.branchesWiseSection.llBody.visibility =
//                                                View.VISIBLE
//                                            binding.sectionWiseSubsection.llBody.visibility =
//                                                View.VISIBLE
//                                            binding.division.llBody.visibility = View.GONE
//                                            binding.district.llBody.visibility = View.GONE
//                                        } else {
//                                            binding.headOfficesBranches.llBody.visibility =
//                                                View.GONE
//                                            binding.branchesWiseSection.llBody.visibility =
//                                                View.GONE
//                                            binding.sectionWiseSubsection.llBody.visibility =
//                                                View.GONE
//                                            binding.division.llBody.visibility =
//                                                View.VISIBLE
//                                            binding.district.llBody.visibility =
//                                                View.VISIBLE
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    )
//                }
//            }
//
//        })
//
//        commonRepo.getCommonData("/api/auth/division/list",object :CommonDataValueListener{
//            override fun valueChange(list: List<SpinnerDataModel>?) {
//                list?.let {
//                    SpinnerAdapter().setSpinner(
//                        binding?.division?.spinner!!,
//                        context,
//                        list,
//                        null,
//                        object : CommonSpinnerSelectedItemListener {
//                            override fun selectedItem(any: Any?) {
//                                division = any as SpinnerDataModel
//                                getDistrict(if (division?.id == null) 1 else division?.id, null)
//                            }
//
//                        }
//                    )
//                }
//            }
//
//        })
//        commonRepo.getOffice()
//
//        commonViewModel.getOffice("/api/auth/office/list/basic")?.observe(viewLifecycleOwner,
//            Observer { list ->
//                list?.let {
//                    SpinnerAdapter().setOfficeSpinner(
//                        binding?.office?.spinner!!,
//                        context,
//                        list,
//                        0,
//                        object : CommonSpinnerSelectedItemListener {
//                            override fun selectedItem(any: Any?) {
//                                office = any as Office
//                                loadDesignation(office?.id)
//                                Log.e("selected item", " item : " + office?.name)
//                            }
//
//                        }
//                    )
//                }
//            })
//
//
//        dialogCustome?.show()
//
//
//    }
//
//
//
//    fun getDistrict(divisionId: Int?, districtId: Int?) {
//        commonViewModel.getDistrict(divisionId)?.observe(viewLifecycleOwner, Observer { list ->
//            list?.let {
//                SpinnerAdapter().setSpinner(
//                    binding?.district?.spinner!!,
//                    context,
//                    list,
//                    districtId,
//                    object : CommonSpinnerSelectedItemListener {
//                        override fun selectedItem(any: Any?) {
//                            district = any as SpinnerDataModel
//                        }
//
//                    }
//                )
//            }
//        })
//    }
//
//    fun headOfficeBranches() {
//        utilViewModel.getHeadOfficeDepartment()
//            ?.observe(viewLifecycleOwner,
//                Observer { branches ->
//
//                    branches?.let {
//                        CommonSpinnerAdapter().setBranchSpinner(
//                            binding?.headOfficesBranches?.spinner!!,
//                            context,
//                            branches,
//                            null,
//                            object : CommonSpinnerSelectedItemListener {
//                                override fun selectedItem(any: Any?) {
//                                    any?.let {
//                                        headOfficeBranches =
//                                            any as HeadOfficeDepartmentApiResponse.HeadOfficeBranch
//                                        headOfficeBranches?.sections?.let { it1 -> setSection(it1) }
//                                    }
//                                }
//
//                            }
//                        )
//
//                    }
//                })
//    }
//
//
//    fun setSection(dataList: List<HeadOfficeDepartmentApiResponse.Section>) {
//        dataList?.let { list ->
//            CommonSpinnerAdapter().setSectionSpinner(
//                binding?.branchesWiseSection?.spinner!!,
//                context,
//                list,
//                null,
//                object : CommonSpinnerSelectedItemListener {
//                    override fun selectedItem(any: Any?) {
//
//                        any?.let {
//                            section = any as HeadOfficeDepartmentApiResponse.Section
//                            section?.subsections?.let { it1 -> setSubSection(it1) }
//                        }
//                    }
//
//                }
//            )
//
//        }
//
//    }
//
//    fun setSubSection(dataList: List<HeadOfficeDepartmentApiResponse.Subsection>) {
//        dataList?.let { list ->
//            CommonSpinnerAdapter().setSubSectionSpinner(
//                binding?.sectionWiseSubsection?.spinner!!,
//                context,
//                list,
//                null,
//                object : CommonSpinnerSelectedItemListener {
//                    override fun selectedItem(any: Any?) {
//                        any?.let {
//                            subSection = any as HeadOfficeDepartmentApiResponse.Subsection
//                        }
//                    }
//
//                }
//            )
//        }
//    }
//
//    fun loadDesignation(officeId: Int?) {
//        commonViewModel.getDesignationData("/api/auth/office/${officeId}")
//            ?.observe(viewLifecycleOwner,
//                Observer { list ->
//                    list?.let {
//                        SpinnerAdapter().setSpinner(
//                            binding.designation.spinner,
//                            context,
//                            list,
//                            0,
//                            object : CommonSpinnerSelectedItemListener {
//                                override fun selectedItem(any: Any?) {
//                                    designation = any as SpinnerDataModel
//                                }
//
//                            }
//                        )
//                    }
//                })
//    }



}