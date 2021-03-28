package com.dss.hrms.view.messaging.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentSearchEmployeeBinding
import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.Office
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OfficeDataValueListener
import com.dss.hrms.view.spinner.CommonSpinnerAdapter
import com.dss.hrms.viewmodel.CommonViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SearchEmployeeFragment : DaggerFragment() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var utilViewModel: UtilViewModel
    lateinit var commonViewModel: CommonViewModel

    lateinit var binding: FragmentSearchEmployeeBinding


    lateinit var dataList: List<RoleWiseEmployeeResponseClass.RoleWiseEmployee>
    var selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()

    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var officeLeadCategory: SpinnerDataModel? = null
    var office: Office? = null
    var designation: SpinnerDataModel? = null
    var headOfficeBranches: HeadOfficeDepartmentApiResponse.HeadOfficeBranch? = null
    var section: HeadOfficeDepartmentApiResponse.Section? = null
    var subSection: HeadOfficeDepartmentApiResponse.Subsection? = null
    var isAlreadyBacked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchEmployeeBinding.inflate(inflater, container, false)
        selectedDataList = arrayListOf()
        dataList = arrayListOf()
        init()
        val navController = findNavController();
// We use a String here, but any type that can be put in a Bundle is supported
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData(
            "key", selectedDataList
        )?.observe(viewLifecycleOwner,
            Observer { result ->
                // Do something with the result.
                result?.let {
                    if (it.size > 0) {

                        if (!isAlreadyBacked) {
                            selectedDataList.addAll(it)
                            isAlreadyBacked = true
                            Log.e("searchlist", "search lsit : ${selectedDataList.size}")
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "key",
                                result
                            )
                            // navController.popBackStack()
                        }
                    }
                }
            })

        binding.back.setOnClickListener {
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "key",
                selectedDataList
            )
           // findNavController().popBackStack(R.id.action_searchEmployeeFragment3_to_createEditLeaveApplicationFragment, true)
            navController.popBackStack()
        }

        binding.headOfficesBranches.llBody.visibility =
            View.GONE
        binding.branchesWiseSection.llBody.visibility =
            View.GONE
        binding.sectionWiseSubsection.llBody.visibility =
            View.GONE
        headOfficeBranches()
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
                                            if (it == 1) {
                                                binding.headOfficesBranches.llBody.visibility =
                                                    View.VISIBLE
                                                binding.branchesWiseSection.llBody.visibility =
                                                    View.VISIBLE
                                                binding.sectionWiseSubsection.llBody.visibility =
                                                    View.VISIBLE
                                                binding.division.llBody.visibility = View.GONE
                                                binding.district.llBody.visibility = View.GONE
                                            } else {
                                                binding.headOfficesBranches.llBody.visibility =
                                                    View.GONE
                                                binding.branchesWiseSection.llBody.visibility =
                                                    View.GONE
                                                binding.sectionWiseSubsection.llBody.visibility =
                                                    View.GONE
                                                binding.division.llBody.visibility = View.VISIBLE
                                                binding.district.llBody.visibility = View.VISIBLE
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }

                })
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
                                getDistrict(if (division?.id == null) 1 else division?.id, null)
                            }

                        }
                    )
                }
            })

        commonViewModel.getOffice("/api/auth/office/list/basic")?.observe(viewLifecycleOwner,
            Observer { list ->
                list?.let {
                    SpinnerAdapter().setOfficeSpinner(
                        binding?.office?.spinner!!,
                        context,
                        list,
                        0,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                office = any as Office
                                loadDesignation(office?.id)
                                Log.e("selected item", " item : " + office?.name)
                            }

                        }
                    )
                }
            })


        binding.search.setOnClickListener {
            isAlreadyBacked = false
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            employeeViewModel?.apply {
                getEmployeeList(
                    office?.id?.let { it.toString() },
                    headOfficeBranches?.id?.let { it.toString() },
                    section?.id?.let { it.toString() },
                    subSection?.id?.let { it.toString() },
                    division?.id?.let { it.toString() },
                    district?.id?.let { it.toString() },
                    officeLeadCategory?.id?.let { it.toString() },
                    designation?.id?.let { it.toString() },
                    binding?.employeeNameOrId?.etText?.text?.trim()?.let { it.toString() }
                    //    "1", "1", "1", "1", "1", "Raju"
                ).observe(viewLifecycleOwner, Observer {
                    dialog?.dismiss()
                    Log.e("data", "datalist : ${it.size}")
                    it?.let {
                        dataList = it
                    }

                    dataList?.let {
                        if (it.size > 0) {
                            val action =
                                SearchEmployeeFragmentDirections.actionSearchEmployeeFragmentToEmployeeBottomSheetFragment(
                                    dataList?.toTypedArray()
                                )
                            findNavController().navigate(action)
//                        Navigation.findNavController(binding.root)
//                            .navigate(R.id.action_searchEmployeeFragment_to_employeeBottomSheetFragment)
                        }
                    }
                })
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
                                        headOfficeBranches?.sections?.let { it1 -> setSection(it1) }
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
}