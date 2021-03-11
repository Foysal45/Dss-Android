package com.dss.hrms.view.messaging.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.databinding.FragmentSearchEmployeeBinding
import com.dss.hrms.model.Office
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OfficeDataValueListener
import com.dss.hrms.view.messaging.`interface`.OnEmployeeClickListener
import com.dss.hrms.view.messaging.adapter.EmployeeAdapter
import com.dss.hrms.view.training.`interface`.OnCourseScheduleClickListener
import com.dss.hrms.view.training.adaoter.CourseScheduleAdapter
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
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

    lateinit var binding: FragmentSearchEmployeeBinding

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataList: List<RoleWiseEmployeeResponseClass.RoleWiseEmployee?>
    lateinit var adapter: EmployeeAdapter

    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var officeLeadCategory: SpinnerDataModel? = null
    var office: Office? = null
    var designation: SpinnerDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchEmployeeBinding.inflate(inflater, container, false)

        init()

        commonRepo.getCommonData("/api/auth/sixteen-category/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.officeLeadCategory?.spinner!!,
                            context,
                            list,
                            null,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    officeLeadCategory = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo.getCommonData("/api/auth/division/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
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
                }
            })

        commonRepo.getOffice("/api/auth/office/list/basic",
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
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
                }
            })

        binding.search.setOnClickListener {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            employeeViewModel?.apply {
                getEmployeeList(
//                    office?.id.toString(),
//                    division?.id?.toString(),
//                    district?.id?.toString(),
//                    officeLeadCategory?.id?.toString(),
//                    designation?.id?.toString(),
//                    binding?.employeeNameOrId?.etText?.text?.trim().toString()
                    "1", "1", "1", "1", "1", "Raju"
                ).observe(viewLifecycleOwner, Observer {
                    dialog?.dismiss()
                    dataList = it
                    dataList += it
                    dataList += it
                    dataList += it
                    dataList += it
                    dataList += it
                    dataList += it
                    dataList += it
                    dataList += it
                    if (dataList != null)
                        prepareRecycleView()
                })
            }
        }
        return binding.root
    }


    fun init() {
        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
    }

    fun prepareRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recycleView.layoutManager = linearLayoutManager
        adapter = EmployeeAdapter()
        activity?.let {
            adapter.setInitialData(
                dataList,
                it,
                object : OnEmployeeClickListener {
                    override fun onClick(
                        roleWiseEmployee: RoleWiseEmployeeResponseClass.RoleWiseEmployee?,
                        position: Int,
                        isChecked: Boolean
                    ) {
                        Toast.makeText(activity, "isChecked ${isChecked}", Toast.LENGTH_LONG).show()
                    }

                })
        }
        binding.recycleView.adapter = adapter
    }


    fun getDistrict(divisionId: Int?, districtId: Int?) {
        commonRepo.getDistrict(divisionId,
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
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
                }
            })
    }

    fun loadDesignation(officeId: Int?) {
        commonRepo.getDesignationData("/api/auth/office/${officeId}",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
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