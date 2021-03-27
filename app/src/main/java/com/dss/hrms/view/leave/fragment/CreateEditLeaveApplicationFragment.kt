package com.dss.hrms.view.leave.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentCreateEditLeaveApplicationBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.leave.viewmodel.LeaveApplicationViewmodel
import com.dss.hrms.view.messaging.fragment.EmployeeBottomSheetFragmentArgs
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CreateEditLeaveApplicationFragment : DaggerFragment() {
    private val args by navArgs<CreateEditLeaveApplicationFragmentArgs>()

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfile: EmployeeProfileData

    var employee: Employee? = null
    lateinit var binding: FragmentCreateEditLeaveApplicationBinding

    lateinit var leaveApplicationViewModel: LeaveApplicationViewmodel


    var leaveApplication: LeaveApplicationApiResponse.LeaveApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateEditLeaveApplicationBinding.inflate(inflater, container, false)
        init()
        leaveApplication = args.leaveApplication

        Log.e("createedit", "createedit : ........${leaveApplication?.apply_date}")
//
//
//        leaveApplicationViewModel.apply {
//            var loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
//            getLeaveApplication(employee?.user?.employee_id.toString())
//
//            leaveApplication.observe(viewLifecycleOwner, Observer { leaaveAppList ->
//                loadingDialog?.dismiss()
//                leaaveAppList?.let {
//                    dataList = leaaveAppList
//                    prepareRecyleView()
//                }
//            })
//        }
//

        return binding.root
    }


    private fun init() {
        leaveApplicationViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(LeaveApplicationViewmodel::class.java)
    }


}