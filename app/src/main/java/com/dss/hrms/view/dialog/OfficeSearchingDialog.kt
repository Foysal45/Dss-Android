package com.dss.hrms.view.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogOfficeSearchBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.Office
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.HeadOfficeDepartmentDataValueListener
import com.dss.hrms.view.allInterface.OfficeDataValueListener
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.spinner.CommonSpinnerAdapter
import com.dss.hrms.viewmodel.CommonViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import java.util.HashMap
import javax.inject.Inject

class OfficeSearchingDialog @Inject constructor() {

    @Inject
    lateinit var commonRepo: CommonRepo


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData


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
    var context: Context? = null
    lateinit var officeValueListener: OfficeDataValueListener


    fun showOfficeSearchDialog(
        context: Context?, utilViewModel: UtilViewModel,
        officeDataValueListener: OfficeDataValueListener
    ) {
        dialogCustome = context?.let { Dialog(it) }!!
        dialogCustome.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.context = context
        this.officeValueListener = officeDataValueListener
        this.utilViewModel = utilViewModel
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_office_search, null, false)
        binding.root.let { dialogCustome.setContentView(it) }

        val window: Window? = dialogCustome.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)


        binding.officeHeader.tvClose.setOnClickListener {
            dialogCustome.dismiss()
        }

        binding.headOfficeDepartmentDialog.llBody.visibility = View.GONE
        binding.departmentWiseSectionDialog.llBody.visibility = View.GONE
        binding.sectionWiseSubSectionDialog.llBody.visibility = View.GONE
        binding.sectionWiseSubSubSectionDialog.llBody.visibility = View.GONE
        binding.division.llBody.visibility = View.GONE
        binding.district.llBody.visibility = View.GONE
        binding.officeDialog.llBody.visibility = View.GONE
        binding.designation.llBody.visibility = View.GONE

        loadDesignationList()

        commonRepo.getAllDistrict(
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.district.spinner,
                            context,
                            list,
                            null,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    district = any as SpinnerDataModel
                                }
                            }
                        )
                    }
                }

            }
        )


        //set spinner for the Office Type Divisional/Head Office
        SpinnerAdapter().setSpinner(
            binding.officeTypeDialog.spinner, context,
            currentOfficeTypeData(),headOfficeBranches?.id, //employeeProfileData.employee?.jobjoinings?.get(0)?.office?.office_type_id,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                   // currentOfficeType = any as SpinnerDataModel
                    officeLeadCategory = any as SpinnerDataModel
                   // headOfficeBranches = any as HeadOfficeDepartmentApiResponse.HeadOfficeBranch
                    if (officeLeadCategory?.id == 1){
                        district = null
                        division = null
                        binding.headOfficeDepartmentDialog.llBody.visibility = View.VISIBLE
                        binding.departmentWiseSectionDialog.llBody.visibility = View.VISIBLE
                        binding.sectionWiseSubSectionDialog.llBody.visibility = View.VISIBLE
                        binding.sectionWiseSubSubSectionDialog.llBody.visibility = View.VISIBLE
                        /*binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = "Head Office Department"
                        loadHeadOfficeDepartment()*/
                    }else if (officeLeadCategory?.id == 4){
                        headOfficeBranches = null
                        section = null
                        subSection = null
                        binding.division.llBody.visibility = View.VISIBLE
                        binding.district.llBody.visibility = View.VISIBLE
                        binding.officeDialog.llBody.visibility = View.VISIBLE
                       /* binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = "Division"
                        setDivision()*/
                    }else{
                        binding.headOfficeDepartmentDialog.llBody.visibility = View.GONE
                        binding.departmentWiseSectionDialog.llBody.visibility = View.GONE
                        binding.sectionWiseSubSectionDialog.llBody.visibility = View.GONE
                        binding.sectionWiseSubSubSectionDialog.llBody.visibility = View.GONE
                        binding.division.llBody.visibility = View.GONE
                        binding.district.llBody.visibility = View.GONE
                        binding.officeDialog.llBody.visibility = View.GONE
                        binding.designation.llBody.visibility = View.GONE
                    }
                }
            }
        )

        headOfficeBranches()

    /*    commonRepo.getCommonData("/api/auth/sixteen-category/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.officeTypeDialog.spinner,
                            context,
                            list,
                            null,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    officeLeadCategory = any as SpinnerDataModel
                                    officeLeadCategory?.let { oflC ->
                                        oflC.id?.let {
                                            if (it == 1) {
                                                district = null
                                                division = null
                                                binding.headOfficeDepartmentDialog.llBody.visibility = View.VISIBLE
                                                binding.departmentWiseSectionDialog.llBody.visibility = View.VISIBLE
                                                binding.sectionWiseSubSectionDialog.llBody.visibility = View.VISIBLE
                                                binding.division.llBody.visibility = View.GONE
                                                binding.district.llBody.visibility = View.GONE
                                            } else {
                                                headOfficeBranches = null
                                                section = null
                                                subSection = null
                                                binding.headOfficeDepartmentDialog.llBody.visibility = View.GONE
                                                binding.departmentWiseSectionDialog.llBody.visibility = View.GONE
                                                binding.sectionWiseSubSectionDialog.llBody.visibility = View.GONE
                                                binding.division.llBody.visibility = View.VISIBLE
                                                binding.district.llBody.visibility = View.VISIBLE
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }

            })*/

        commonRepo.getCommonData("/api/auth/division/list", object : CommonDataValueListener {
            override fun valueChange(list: List<SpinnerDataModel>?) {
                list?.let {
                    SpinnerAdapter().setSpinner(
                        binding.division.spinner,
                        context,
                        list,
                        null,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                division = any as SpinnerDataModel
                                getDistrict(if (division?.id == null) 1 else division?.id, null)
                            }

                        }
                    )
                }
            }
        })

        //Submit search data
        binding.search.setOnClickListener {
            searchOffice()
        }
        dialogCustome.show()
    }

    //For OfficeType
    private fun currentOfficeTypeData(): List<SpinnerDataModel> {

        val headOffice = SpinnerDataModel()
        headOffice.apply {
            id = 1
            name = "Head Office"
            name_bn = "হেড অফিস"

        }
        val divisionalOffice = SpinnerDataModel()
        divisionalOffice.apply {
            id = 4
            name = "Divisional"
            name_bn = "বিভাগীয়"


        }
        return arrayListOf(headOffice, divisionalOffice)
    }


    private fun searchOffice() {
        val dialog = CustomLoadingDialog().createLoadingDialog(context)
        commonRepo.getOfficeWithWhereClause(
            getMapData(),
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    Log.e("officelist", " list : " + list?.size)
                //    Toast.makeText(context,"office : ${list?.size}",Toast.LENGTH_LONG).show()
                    officeValueListener.valueChange(list)
                    dialog?.dismiss()
                    dialogCustome.dismiss()
                }
            })
    }

    fun getMapData(): HashMap<Any, Any?> {
        val map = HashMap<Any, Any?>()
        officeLeadCategory?.id?.let {
            map.put("office_type_id", it)
        }
        headOfficeBranches?.id?.let {
            map.put("head_office_department_id", it)
        }
        section?.id?.let {
            map.put("department_wise_section_id", it)
        }
        subSection?.id?.let {
            map.put("section_wise_sub_section_id", it)
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
        commonRepo.getDistrict(divisionId, object : CommonDataValueListener {
            override fun valueChange(list: List<SpinnerDataModel>?) {
                list?.let {
                    SpinnerAdapter().setSpinner(
                        binding.district.spinner,
                        context,
                        list,
                        districtId,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                district = any as SpinnerDataModel
                            }

                        }
                    )
                }
            }
        })

    }

    private fun headOfficeBranches() {
        utilViewModel.getHeadOfficeDepartment(object : HeadOfficeDepartmentDataValueListener {
            override fun valueChange(branches: List<HeadOfficeDepartmentApiResponse.HeadOfficeBranch>?) {
                branches?.let {
                    CommonSpinnerAdapter().setBranchSpinner(
                        binding.headOfficeDepartmentDialog.spinner,
                        context,
                        branches,
                        null,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                any?.let {
                                    headOfficeBranches =
                                        any as HeadOfficeDepartmentApiResponse.HeadOfficeBranch
                                    headOfficeBranches?.sections?.let { it1 -> setSection(it1) }
                                }
                            }

                        }
                    )

                }
            }

        })
    }


    fun setSection(dataList: List<HeadOfficeDepartmentApiResponse.Section>) {
        dataList.let { list ->
            CommonSpinnerAdapter().setSectionSpinner(
                binding.departmentWiseSectionDialog.spinner,
                context,
                list,
                null,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {

                        any?.let {
                            section = any as HeadOfficeDepartmentApiResponse.Section
                            section?.subsections?.let { it1 -> setSubSection(it1) }
                        }
                    }

                }
            )

        }

    }

    fun setSubSection(dataList: List<HeadOfficeDepartmentApiResponse.Subsection>) {
        dataList.let { list ->
            CommonSpinnerAdapter().setSubSectionSpinner(
                binding.sectionWiseSubSectionDialog.spinner,
                context,
                list,
                null,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        any?.let {
                            subSection = any as HeadOfficeDepartmentApiResponse.Subsection
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

    private fun loadDesignationList() {
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