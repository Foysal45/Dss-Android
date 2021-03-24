package com.dss.hrms.view.leave.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentLeaveApplicationBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.leave.`interface`.OnLeaveApplicationClickListener
import com.dss.hrms.view.leave.adapter.LeaveApplicationAdapter
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.leave.viewmodel.LeaveApplicationViewmodel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LeaveApplicationFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfile: EmployeeProfileData

    var employee: Employee? = null


    lateinit var leaveApplicationViewModel: LeaveApplicationViewmodel
    var dataList: List<LeaveApplicationApiResponse.LeaveApplication>? = null
    lateinit var adapter: LeaveApplicationAdapter

    lateinit var binding: FragmentLeaveApplicationBinding

    lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeaveApplicationBinding.inflate(inflater, container, false)
        employee = employeeProfile?.employee

        init()

        leaveApplicationViewModel.apply {
            var loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            getLeaveApplication(employee?.user?.employee_id.toString())

            leaveApplication.observe(viewLifecycleOwner, Observer { leaaveAppList ->
                loadingDialog?.dismiss()
                leaaveAppList?.let {
                    dataList = leaaveAppList
                    prepareRecyleView()
                }
            })
        }
        return binding.root
    }

    private fun init() {
        leaveApplicationViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(LeaveApplicationViewmodel::class.java)
    }

    fun prepareRecyleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = LeaveApplicationAdapter()
        activity?.let {
            adapter.setInitialData(
                dataList, it, object : OnLeaveApplicationClickListener {
                    override fun onClick(any: Any?, position: Int, operation: Operation) {

                    }
                }
            )
        }
        binding.recyclerView.adapter = adapter
    }
}