package com.dss.hrms.view.leave.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogLeaveManagementLayoutBinding
import com.dss.hrms.databinding.FragmentLeaveApplicationBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.util.*
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.leave.`interface`.OnLeaveApplicationClickListener
import com.dss.hrms.view.leave.adapter.LeaveApplicationAdapter
import com.dss.hrms.view.leave.adapter.spinner.LeavePolicySpinnerAdapter
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.leave.viewmodel.LeaveApplicationViewmodel
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.training.adaoter.spinner.HonorariumHeadAdapter
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.model.HonorariumHead
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.dialog_leave_management_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LeaveApplicationFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfile: EmployeeProfileData

    var employee: Employee? = null
    lateinit var dialogCustome: Dialog
    lateinit var leaveApplicationBinding: DialogLeaveManagementLayoutBinding

    lateinit var leaveApplicationViewModel: LeaveApplicationViewmodel
    var dataList: List<LeaveApplicationApiResponse.LeaveApplication>? = null
    lateinit var adapter: LeaveApplicationAdapter

    lateinit var binding: FragmentLeaveApplicationBinding

    lateinit var linearLayoutManager: LinearLayoutManager
    var leaveType: LeaveApplicationApiResponse.LeavePolicy? = null

    var applyDate: String? = null
    var loadingDialog: Dialog? = null
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

        binding.fab.setOnClickListener {
            showEditCreateDialog(Operation.CREATE, null)
        }

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


    fun showEditCreateDialog(
        operation: Operation,
        leaveApplication: LeaveApplicationApiResponse.LeaveApplication?
    ) {

        dialogCustome = activity?.let { Dialog(it) }!!
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        leaveApplicationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_leave_management_layout,
            null,
            false
        )
        leaveApplicationBinding?.getRoot()?.let { dialogCustome?.setContentView(it) }
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        leaveApplicationBinding.llLeaveApplication.visibility = View.VISIBLE

        leaveApplicationBinding.lEmergencyContantNo.etText.inputType = InputType.TYPE_CLASS_PHONE
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
        val currentDate = sdf.format(Date())
        if (operation == Operation.CREATE) applyDate = currentDate else applyDate =
            leaveApplication?.apply_date

        leaveApplicationBinding.lLeaveRequestReference.etText.setText(leaveApplication?.leave_request_ref)
        leaveApplicationBinding.lEmergencyContantNo.etText.setText(
            leaveApplication?.leave_application_details?.get(
                0
            )?.emergency_contact_no
        )


        leaveApplicationBinding.lFromDate.tvText?.setText(
            leaveApplication?.leave_application_details?.get(
                0
            )?.date_form?.let {
                DateConverter.changeDateFormateForShowing(
                    it
                )
            }
        )
        leaveApplicationBinding.lToDate.tvText?.setText(
            leaveApplication?.leave_application_details?.get(
                0
            )?.date_to?.let {
                DateConverter.changeDateFormateForShowing(
                    it
                )
            }
        )

        leaveApplicationBinding.hLeaveApplication.tvClose.setOnClickListener {
            dialogCustome.dismiss()
        }


        leaveApplicationBinding?.lFromDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { leaveApplicationBinding?.lFromDate?.tvText?.setText("" + it) }
                }
            })
        })

        leaveApplicationBinding?.lToDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { leaveApplicationBinding?.lToDate?.tvText?.setText("" + it) }
                }
            })
        })

        leaveApplicationViewModel.getLeavePolicyList()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                    LeavePolicySpinnerAdapter().setLeavePolicySpinner(
                        leaveApplicationBinding?.lLeaveType?.spinner!!,
                        context,
                        it,
                        leaveApplication?.leave_policy_id,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                any?.let {
                                    leaveType = any as LeaveApplicationApiResponse.LeavePolicy
                                }
                            }
                        }
                    )
                }
            })


        leaveApplicationBinding.leaveApplicatoinBtnUpdate.setOnClickListener {
            invisiableAllError()
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            if (operation == Operation.CREATE) {
                leaveApplicationViewModel.createLeaveApplication(getMapData(leaveApplication))
                    .observe(viewLifecycleOwner,
                        Observer {
                            loadingDialog?.dismiss()
                            showResponse(it)
                        })
            } else {

            }
        }

        dialogCustome?.show()
    }


    fun showResponse(any: Any?) {
        if (any is String) {
            toast(activity, any)
            leaveApplicationViewModel.getLeaveApplication(employee?.user?.employee_id.toString())
            dialogCustome?.dismiss()
        } else if (any is ApiError) {
            try {
                if (any.getError().isEmpty()) {
                    toast(activity, any.getMessage())
                    Log.d("ok", "error")
                } else {
                    for (n in any.getError().indices) {
                        val error = any.getError()[n].getField()
                        val message = any.getError()[n].getMessage()
                        if (TextUtils.isEmpty(error)) {
                            message?.let {
                                leaveApplicationBinding?.tvAttachmentError?.visibility =
                                    View.VISIBLE
                                leaveApplicationBinding?.tvAttachmentError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        Log.e("ok", "error ${ErrorUtils2.mainError(message)}")
                        when (error) {
                            "leave_request_ref" -> {
                                leaveApplicationBinding?.lLeaveRequestReference?.tvError?.visibility =
                                    View.VISIBLE
                                leaveApplicationBinding?.lLeaveRequestReference?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "leave_policy_id" -> {
                                leaveApplicationBinding?.lLeaveType?.tvError?.visibility =
                                    View.VISIBLE
                                leaveApplicationBinding?.lLeaveType?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "approval_status" -> {
//                                leaveApplicationBinding?.lLeaveType?.tvError?.visibility =
//                                    View.VISIBLE
//                                leaveApplicationBinding?.lLeaveType?.tvError?.text =
//                                    ErrorUtils2.mainError(message)
                            }
                            "apply_date" -> {
//                                dialogTrainingLoyeoutBinding?.courseCourseId?.tvError?.visibility =
//                                    View.VISIBLE
//                                dialogTrainingLoyeoutBinding?.courseCourseId?.tvError?.text =
//                                    ErrorUtils2.mainError(message)
                            }
                            "forword_to_employee_id" -> {
                                leaveApplicationBinding?.lSelectNotify?.tvError?.visibility =
                                    View.VISIBLE
                                leaveApplicationBinding?.lSelectNotify?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "leave_application_details" -> {
                                leaveApplicationBinding?.lFromDate?.tvError?.visibility =
                                    View.VISIBLE
                                leaveApplicationBinding?.lFromDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "leave_application_details.0.date_form" -> {
                                leaveApplicationBinding?.lFromDate?.tvError?.visibility =
                                    View.VISIBLE
                                leaveApplicationBinding?.lFromDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "leave_application_details.0.date_to" -> {
                                leaveApplicationBinding?.lToDate?.tvError?.visibility =
                                    View.VISIBLE
                                leaveApplicationBinding?.lToDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                toast(activity, e.toString())
            }

        } else if (any is Throwable) {
            toast(activity, any.toString())
        } else {
            activity?.getString(R.string.failed)?.let {
                toast(
                    activity,
                    it
                )
            }!!
        }
    }

    fun getMapData(leaveApplication: LeaveApplicationApiResponse.LeaveApplication?): HashMap<Any, Any?> {
        var map = HashMap<Any, Any?>()
        map.put("office_id", employee?.office_id.toString())
        map.put(
            "leave_request_ref",
            leaveApplicationBinding.lLeaveRequestReference?.etText?.text?.trim().toString()
        )
        map.put("leave_policy_id", leaveType?.id.toString())
        map.put(
            "approval_status",
            if (leaveApplication?.approval_status != null) leaveApplication?.approval_status else StaticKey.PENDING
        )
        map.put("forword_to_employee_id", "281")
        map.put("apply_date", applyDate)

        //  map.put("document_path", coordinator?.id)
        // map.put("note_leave", leaveApplication?.leave_application_details.note)
        map.put("note_date", applyDate)
        map.put(
            "leave_application_details",
            arrayListOf<HashMap<Any, Any?>>(leaveDeatails(leaveApplication))
        )
        return map
    }

    fun leaveDeatails(leaveApplication: LeaveApplicationApiResponse.LeaveApplication?): HashMap<Any, Any?> {
        var fromDate =
            DateConverter.changeDateFormateForSending(leaveApplicationBinding.lFromDate?.tvText?.text.toString())
        var toDate =
            DateConverter.changeDateFormateForSending(leaveApplicationBinding.lToDate?.tvText?.text.toString())
        var map = HashMap<Any, Any?>()
        map.put("date_form", fromDate)
        map.put("date_to", toDate)
        map.put("designation_id", employee?.designation_id)
        map.put("employee_id", employee?.user?.employee_id)
        map.put("reason", leaveApplicationBinding.etBody.text.trim().toString())
        map.put(
            "emergency_contact_no",
            leaveApplicationBinding.lEmergencyContantNo.etText.text.trim().toString()
        )
        map.put("leave_application_id", leaveApplication?.id)
        return map
    }


    fun invisiableAllError() {
        leaveApplicationBinding.lSelectNotify?.tvError?.visibility =
            View.GONE
        leaveApplicationBinding.lSelectResponsible?.tvError?.visibility =
            View.GONE
        leaveApplicationBinding.lLeaveType?.tvError?.visibility =
            View.GONE
        leaveApplicationBinding.lFromDate?.tvError?.visibility =
            View.GONE
        leaveApplicationBinding.lToDate?.tvError?.visibility =
            View.GONE
        leaveApplicationBinding.lLeaveRequestReference?.tvError?.visibility =
            View.GONE
        leaveApplicationBinding.lEmergencyContantNo?.tvError?.visibility =
            View.GONE
        leaveApplicationBinding?.tvReasonDetailsError?.visibility =
            View.GONE
        leaveApplicationBinding.tvAttachmentError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

}