package com.dss.hrms.view.leave.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentLeaveSummaryBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.leave.`interface`.OnLeaveApplicationClickListener
import com.dss.hrms.view.leave.adapter.LeaveApplicationAdapter
import com.dss.hrms.view.leave.adapter.LeaveSummaryAdapter
import com.dss.hrms.view.leave.model.LeaveSummaryApiResponse
import com.dss.hrms.view.leave.viewmodel.LeaveSummaryViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class LeaveSummaryFragment : DaggerFragment() {

    @Inject
    lateinit var viewmodelProviderFactory: ViewModelProviderFactory

    lateinit var leaveSummaryViewModel: LeaveSummaryViewModel

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData
    var employee: Employee? = null


    lateinit var binding: FragmentLeaveSummaryBinding
    var dataList = arrayListOf<LeaveSummaryApiResponse.LeaveSummaryModifiedModel>()

    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var adapter: LeaveSummaryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeaveSummaryBinding.inflate(inflater, container, false)
        init()
        employee = employeeProfileData.employee


        leaveSummaryViewModel.apply {
            var loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)

            getLeaveSummary(employee?.user?.employee_id.toString())

            leaveSummaryHeader.observe(viewLifecycleOwner, Observer { data ->
                loadingDialog?.dismiss()

                data?.let {
                    makeModifiedClass(data)
                }
            })
        }



        return binding.root
    }

    fun makeModifiedClass(headerList: List<LeaveSummaryApiResponse.Header>) {
        for (i in headerList.indices) {
            var customeClass = LeaveSummaryApiResponse.LeaveSummaryModifiedModel(
                headerList?.get(i)?.name,
                headerList?.get(i)?.name_bn,
                leaveSummaryViewModel.leaveSummaryRow.value?.total?.get(i),
                leaveSummaryViewModel.leaveSummaryRow.value?.applied?.get(i),
                leaveSummaryViewModel.leaveSummaryRow.value?.enjoyed?.get(i),
                leaveSummaryViewModel.leaveSummaryRow.value?.remaining_balance?.get(i)
            )

            Log.e("totalleave","totalleave ............................${ leaveSummaryViewModel.leaveSummaryRow.value?.total?.get(i)}")

            dataList.add(customeClass)
        }
        prepareRecyleView()
    }

    fun prepareRecyleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = LeaveSummaryAdapter()
        activity?.let {
            adapter.setInitialData(
                dataList, it
            )
        }
        binding.recyclerView.adapter = adapter
    }

    fun init() {

        leaveSummaryViewModel =
            ViewModelProvider(this, viewmodelProviderFactory).get(LeaveSummaryViewModel::class.java)

    }

}