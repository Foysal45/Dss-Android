package com.dss.hrms.view.training.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogTrainingLoyeoutBinding
import com.dss.hrms.databinding.FragmentBatchScheduleBinding
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.*
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.training.`interface`.OnBatchScheduleClickListener
import com.dss.hrms.view.training.adaoter.BatchScheduleAdapter
import com.dss.hrms.view.training.adaoter.spinner.CourseScheduleSpinnerAdapter
import com.dss.hrms.view.training.adaoter.spinner.RoleWiseEmployeeAdapter
import com.dss.hrms.view.training.dialog.BatchScheduleSearchingDialog
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import java.util.HashMap
import javax.inject.Inject


class BatchScheduleFragment : DaggerFragment() {

    @Inject
    lateinit var commonRepo: CommonRepo


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var batchScheduleSearchDialog: BatchScheduleSearchingDialog

    lateinit var budgetAndScheduleViewModel: BudgetAndScheduleViewModel
    lateinit var employeeViewModel: EmployeeViewModel

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataList: List<BudgetAndSchedule.BatchSchedule?>
    lateinit var batchScheduleAdapter: BatchScheduleAdapter

    lateinit var binding: FragmentBatchScheduleBinding


    var loadingDialog: Dialog? = null
    var dialogCustome: Dialog? = null
    lateinit var dialogTrainingLoyeoutBinding: DialogTrainingLoyeoutBinding
    var coordinator: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var cocoordinator: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var staff1: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var staff2: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var staff3: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var courseScheduleListData: BudgetAndSchedule.CourseSchedule? = null


    var coordinatorExternal: SpinnerDataModel? = null
    var cocoordinatorExternal: SpinnerDataModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBatchScheduleBinding.inflate(inflater, container, false)

        init()
        budgetAndScheduleViewModel.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            getBatchSchedule()
            Log.e("batchschedule", "batch schedule............................................")
            batchSchedule.observe(viewLifecycleOwner, Observer {
                dialog?.dismiss()
                it?.let {
                    dataList = it
                    prepareRecycleView()
                }
            })
        }

        binding.llSearch.setOnClickListener {
            batchScheduleSearchDialog.showBatchScheduleSearchDialog(
                activity,
                budgetAndScheduleViewModel
            )
        }

        binding.fab.setOnClickListener {
            showEditCreateDialog(Operation.CREATE, null)
        }

        return binding.root
    }


    fun init() {
        budgetAndScheduleViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(BudgetAndScheduleViewModel::class.java)

        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
    }

    fun prepareRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        batchScheduleAdapter = BatchScheduleAdapter()
        activity?.let {
            batchScheduleAdapter.setInitialData(
                dataList,
                it,
                object : OnBatchScheduleClickListener {
                    override fun onClick(
                        batchSchedule: BudgetAndSchedule.BatchSchedule?,
                        position: Int,
                        operation: Operation
                    ) {
                        when (operation) {
                            Operation.EDIT ->
                                //  Toast.makeText(activity, "" + operation, Toast.LENGTH_LONG).show()
                                showEditCreateDialog(operation, batchSchedule)
                            Operation.CREATE ->
                                Toast.makeText(activity, "" + operation, Toast.LENGTH_LONG).show()

                        }
                    }

                })
        }
        binding.recyclerView.adapter = batchScheduleAdapter
    }

    fun showEditCreateDialog(
        operation: Operation,
        batchSchedule: BudgetAndSchedule.BatchSchedule?
    ) {

        dialogCustome = activity?.let { Dialog(it) }
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogTrainingLoyeoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_training_loyeout,
            null,
            false
        )
        dialogTrainingLoyeoutBinding?.getRoot()?.let { dialogCustome?.setContentView(it) }
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogTrainingLoyeoutBinding.llBatchSchedule.visibility = View.VISIBLE
        dialogTrainingLoyeoutBinding.batchBatchName.etText.setText(batchSchedule?.batch_name)
        dialogTrainingLoyeoutBinding.batchBatchNameBn.etText.setText(batchSchedule?.batch_name_bn)
        dialogTrainingLoyeoutBinding.batchTotalSeatts.etText.setText(batchSchedule?.total_seat)

        dialogTrainingLoyeoutBinding.batchScheduleHeader.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }

        Log.e(
            "bathschedule",
            "batchschedule...................course_co_coordinator_is_external............................................${batchSchedule?.course_co_coordinator_is_external}"
        )
        if (batchSchedule?.course_coordinator_is_external == 1) {
            dialogTrainingLoyeoutBinding.batchCoOrdinatorExternal.llBody.visibility =
                View.VISIBLE
            dialogTrainingLoyeoutBinding.batchCoOrdinator.llBody.visibility = View.GONE
            dialogTrainingLoyeoutBinding.cbBastchCoordinatorExternal.isChecked = true

        } else {
            dialogTrainingLoyeoutBinding.batchCoOrdinatorExternal.llBody.visibility = View.GONE
            dialogTrainingLoyeoutBinding.batchCoOrdinator.llBody.visibility = View.VISIBLE
            dialogTrainingLoyeoutBinding.cbBastchCoordinatorExternal.isChecked = false
        }


        if (batchSchedule?.course_co_coordinator_is_external == 1) {
            dialogTrainingLoyeoutBinding.batchCoCoOrdinatorExternal.llBody.visibility =
                View.VISIBLE
            dialogTrainingLoyeoutBinding.batchCoCoOrdinator.llBody.visibility = View.GONE
            dialogTrainingLoyeoutBinding.cbBastchCoCoordinatorExternal.isChecked = true
        } else {
            dialogTrainingLoyeoutBinding.batchCoCoOrdinatorExternal.llBody.visibility = View.GONE
            dialogTrainingLoyeoutBinding.batchCoCoOrdinator.llBody.visibility = View.VISIBLE
            dialogTrainingLoyeoutBinding.cbBastchCoCoordinatorExternal.isChecked = false
        }


        dialogTrainingLoyeoutBinding.cbBastchCoCoordinatorExternal.setOnClickListener {
            if (dialogTrainingLoyeoutBinding.cbBastchCoCoordinatorExternal.isChecked) {
                dialogTrainingLoyeoutBinding.batchCoCoOrdinatorExternal.llBody.visibility =
                    View.VISIBLE
                dialogTrainingLoyeoutBinding.batchCoCoOrdinator.llBody.visibility = View.GONE
            } else {
                dialogTrainingLoyeoutBinding.batchCoCoOrdinatorExternal.llBody.visibility =
                    View.GONE
                dialogTrainingLoyeoutBinding.batchCoCoOrdinator.llBody.visibility = View.VISIBLE
            }
        }

        dialogTrainingLoyeoutBinding.cbBastchCoordinatorExternal.setOnClickListener {
            if (dialogTrainingLoyeoutBinding.cbBastchCoordinatorExternal.isChecked) {
                dialogTrainingLoyeoutBinding.batchCoOrdinatorExternal.llBody.visibility =
                    View.VISIBLE
                dialogTrainingLoyeoutBinding.batchCoOrdinator.llBody.visibility = View.GONE
            } else {
                dialogTrainingLoyeoutBinding.batchCoOrdinatorExternal.llBody.visibility = View.GONE
                dialogTrainingLoyeoutBinding.batchCoOrdinator.llBody.visibility = View.VISIBLE
            }
        }




        dialogTrainingLoyeoutBinding.batchStartDate.tvText.setText(batchSchedule?.start_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })
        dialogTrainingLoyeoutBinding.batchEndDate.tvText.setText(batchSchedule?.end_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })
        dialogTrainingLoyeoutBinding.batchRegistrationStartDate.tvText.setText(batchSchedule?.reg_start_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })
        dialogTrainingLoyeoutBinding.batchRegistrationEndDate.tvText.setText(batchSchedule?.reg_end_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })

        dialogTrainingLoyeoutBinding.batchStartDate.llBody.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { dialogTrainingLoyeoutBinding.batchStartDate.tvText.setText("" + it) }
                }
            })
        }
        dialogTrainingLoyeoutBinding.batchEndDate.llBody.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { dialogTrainingLoyeoutBinding.batchEndDate.tvText.setText("" + it) }
                }
            })
        }
        dialogTrainingLoyeoutBinding.batchRegistrationStartDate.llBody.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let {
                        dialogTrainingLoyeoutBinding.batchRegistrationStartDate.tvText.setText(
                            "" + it
                        )
                    }
                }
            })
        }
        dialogTrainingLoyeoutBinding.batchRegistrationEndDate.llBody.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let {
                        dialogTrainingLoyeoutBinding.batchRegistrationEndDate.tvText.setText(
                            "" + it
                        )
                    }
                }
            })
        }


        budgetAndScheduleViewModel?.apply {
            getCourseScheduleList().observe(viewLifecycleOwner, Observer {
                Log.e("course", "course schedule ; " + it?.size)

                CourseScheduleSpinnerAdapter().setCourseScheduleSpinner(
                    dialogTrainingLoyeoutBinding?.batchCourseSchedule?.spinner!!,
                    context,
                    it,
                    batchSchedule?.course_schedule_id,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {

                            any?.let {
                                courseScheduleListData =
                                    any as BudgetAndSchedule.CourseSchedule
                            }

                        }
                    }
                )
            })


        }


        commonRepo.getCommonData("/api/auth/co-coordinator/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            dialogTrainingLoyeoutBinding?.batchCoCoOrdinatorExternal?.spinner!!,
                            context,
                            list,
                            batchSchedule?.external_co_coordinator?.id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    cocoordinatorExternal = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })



        commonRepo.getCommonData("/api/auth/coordinator/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            dialogTrainingLoyeoutBinding?.batchCoOrdinatorExternal?.spinner!!,
                            context,
                            list,
                            batchSchedule?.external_coordinator?.id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    coordinatorExternal = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })



        employeeViewModel?.apply {
            getRoleWiseEmployeeInfo(Role.COORDINATOR).observe(viewLifecycleOwner, Observer {
                RoleWiseEmployeeAdapter().setRoleWiseEmployeeSpinner(
                    dialogTrainingLoyeoutBinding?.batchCoOrdinator?.spinner!!,
                    context,
                    it,
                    batchSchedule?.course_coordinator,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {

                            any?.let {
                                coordinator =
                                    any as RoleWiseEmployeeResponseClass.RoleWiseEmployee
                            }

                        }
                    }
                )
            })
            getRoleWiseEmployeeInfo(Role.CO_COORDINATOR).observe(viewLifecycleOwner, Observer {
                RoleWiseEmployeeAdapter().setRoleWiseEmployeeSpinner(
                    dialogTrainingLoyeoutBinding?.batchCoCoOrdinator?.spinner!!,
                    context,
                    it,
                    batchSchedule?.course_co_coordinator,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {

                            any?.let {
                                cocoordinator =
                                    any as RoleWiseEmployeeResponseClass.RoleWiseEmployee
                            }

                        }
                    }
                )
            })
            getRoleWiseEmployeeInfo(Role.STAFF1).observe(viewLifecycleOwner, Observer {
                RoleWiseEmployeeAdapter().setRoleWiseEmployeeSpinner(
                    dialogTrainingLoyeoutBinding?.batchStaff1?.spinner!!,
                    context,
                    it,
                    batchSchedule?.staff1?.id,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {

                            any?.let {
                                staff1 =
                                    any as RoleWiseEmployeeResponseClass.RoleWiseEmployee
                            }

                        }
                    }
                )
            })
            getRoleWiseEmployeeInfo(Role.STAFF2).observe(viewLifecycleOwner, Observer {
                RoleWiseEmployeeAdapter().setRoleWiseEmployeeSpinner(
                    dialogTrainingLoyeoutBinding?.batchStaff2?.spinner!!,
                    context,
                    it,
                    batchSchedule?.staff2?.id,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {

                            any?.let {
                                staff2 =
                                    any as RoleWiseEmployeeResponseClass.RoleWiseEmployee
                            }

                        }
                    }
                )
            })
            getRoleWiseEmployeeInfo(Role.STAFF3).observe(viewLifecycleOwner, Observer {
                RoleWiseEmployeeAdapter().setRoleWiseEmployeeSpinner(
                    dialogTrainingLoyeoutBinding?.batchStaff3?.spinner!!,
                    context,
                    it,
                    batchSchedule?.staff3?.id,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {

                            any?.let {
                                staff3 =
                                    any as RoleWiseEmployeeResponseClass.RoleWiseEmployee
                            }

                        }
                    }
                )
            })


        }


        dialogTrainingLoyeoutBinding.batchScheduleHeader.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }
        if (operation == Operation.EDIT) dialogTrainingLoyeoutBinding.batchScheduleUpdateButton.btnUpdate.setText(
            getString(R.string.update)
        ) else dialogTrainingLoyeoutBinding.batchScheduleUpdateButton.btnUpdate.setText(getString(R.string.create))
        dialogTrainingLoyeoutBinding.batchScheduleUpdateButton.btnUpdate.setOnClickListener {
            invisiableAllError()
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            when (operation) {
                Operation.EDIT ->
                    batchSchedule?.id?.let { it1 ->
                        budgetAndScheduleViewModel.updateBatchSchedule(
                            getMapData(batchSchedule),
                            it1
                        ).observe(viewLifecycleOwner, Observer {
                            loadingDialog?.dismiss()
                            showResponse(it)
                        })
                    }
                Operation.CREATE -> {
                    budgetAndScheduleViewModel.addBatchSchedule(
                        getMapData(batchSchedule)
                    ).observe(viewLifecycleOwner, Observer {
                        loadingDialog?.dismiss()
                        showResponse(it)
                    })
                }
            }
        }
        dialogCustome?.show()
    }

    fun showResponse(any: Any?) {
        if (any is String) {
            toast(activity, any)
            dialogCustome?.dismiss()
            budgetAndScheduleViewModel.getBatchSchedule()

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
                                dialogTrainingLoyeoutBinding?.batchCourseSchedule?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchCourseSchedule?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        Log.e("ok", "error ${message}")
                        when (error) {
                            "course_schedule_id" -> {
                                dialogTrainingLoyeoutBinding?.batchCourseSchedule?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchCourseSchedule?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "batch_name" -> {
                                dialogTrainingLoyeoutBinding?.batchBatchName?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchBatchName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "batch_name_bn" -> {
                                dialogTrainingLoyeoutBinding?.batchBatchNameBn?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchBatchNameBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "total_seat" -> {
                                dialogTrainingLoyeoutBinding?.batchTotalSeatts?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchTotalSeatts?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "start_date" -> {
                                dialogTrainingLoyeoutBinding?.batchStartDate?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchStartDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "end_date" -> {
                                dialogTrainingLoyeoutBinding?.batchEndDate?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchEndDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "reg_start_date" -> {
                                dialogTrainingLoyeoutBinding?.batchRegistrationStartDate?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchRegistrationStartDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "reg_end_date" -> {
                                dialogTrainingLoyeoutBinding?.batchRegistrationEndDate?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchRegistrationEndDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "course_coordinator" -> {
                                dialogTrainingLoyeoutBinding?.batchCoOrdinator?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchCoOrdinator?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "course_co_coordinator" -> {
                                dialogTrainingLoyeoutBinding?.batchCoCoOrdinator?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchCoCoOrdinator?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "staff1" -> {
                                dialogTrainingLoyeoutBinding?.batchStaff1?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchStaff1?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "staff2" -> {
                                dialogTrainingLoyeoutBinding?.batchStaff2?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchStaff2?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "staff3" -> {
                                dialogTrainingLoyeoutBinding?.batchStaff3?.tvError.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.batchStaff3?.tvError?.text =
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
            }
        }
    }

    fun getMapData(batchSchedule: BudgetAndSchedule.BatchSchedule?): HashMap<Any, Any?> {
        var batchStartDate =
            DateConverter.changeDateFormateForSending(
                dialogTrainingLoyeoutBinding.batchStartDate?.tvText?.text?.trim().toString()
            )

        var batchEndDate =
            DateConverter.changeDateFormateForSending(
                dialogTrainingLoyeoutBinding.batchEndDate?.tvText?.text?.trim().toString()
            )
        var batchRegistrationStartDate =
            DateConverter.changeDateFormateForSending(
                dialogTrainingLoyeoutBinding.batchRegistrationStartDate?.tvText?.text?.trim()
                    .toString()
            )
        var batchRegistrationEndDate =
            DateConverter.changeDateFormateForSending(
                dialogTrainingLoyeoutBinding.batchRegistrationEndDate?.tvText?.text?.trim()
                    .toString()
            )

        var map = HashMap<Any, Any?>()
        map.put(
            "course_schedule_id",
            courseScheduleListData?.id
        )
        map.put(
            "batch_name",
            dialogTrainingLoyeoutBinding.batchBatchName.etText?.text.trim().toString()
        )
        map.put(
            "batch_name_bn",
            dialogTrainingLoyeoutBinding.batchBatchNameBn.etText?.text.trim().toString()
        )
        map.put(
            "total_seat",
            dialogTrainingLoyeoutBinding.batchTotalSeatts.etText?.text.trim().toString()
        )
        map.put("start_date", batchStartDate)
        map.put("end_date", batchEndDate)
        map.put("reg_start_date", batchRegistrationStartDate)
        map.put("reg_end_date", batchRegistrationEndDate)

        map.put(
            "course_co_coordinator_is_external",
            dialogTrainingLoyeoutBinding.cbBastchCoCoordinatorExternal.isChecked
        )
        map.put(
            "course_coordinator_is_external",
             dialogTrainingLoyeoutBinding.cbBastchCoordinatorExternal.isChecked
        )
        map.put(
            "course_coordinator",
            if (dialogTrainingLoyeoutBinding.cbBastchCoordinatorExternal.isChecked) coordinatorExternal?.id else coordinator?.id
        )
        map.put(
            "course_co_coordinator",
            if (dialogTrainingLoyeoutBinding.cbBastchCoCoordinatorExternal.isChecked) cocoordinatorExternal?.id else cocoordinator?.id
        )
        // map.put("designation_id", courseSchedule?.de)
        map.put("staff1", staff1?.id)
        map.put("staff2", staff2?.id)
        map.put("staff3", staff3?.id)
        return map
    }

    fun invisiableAllError() {
        dialogTrainingLoyeoutBinding.batchCourseSchedule?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchBatchName?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchBatchNameBn?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchCoOrdinator?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchCoCoOrdinator?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchTotalSeatts?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchStartDate?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchEndDate?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchRegistrationStartDate?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchRegistrationEndDate?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchStaff1?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchStaff2?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.batchStaff3?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}