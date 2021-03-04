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
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogTrainingLoyeoutBinding
import com.dss.hrms.databinding.FragmentCourseScheduleBinding
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.training.`interface`.OnCourseScheduleClickListener
import com.dss.hrms.view.training.adaoter.CourseScheduleAdapter
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import java.util.HashMap
import javax.inject.Inject


class CourseScheduleFragment : DaggerFragment() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var budgetAndScheduleViewModel: BudgetAndScheduleViewModel

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataList: List<BudgetAndSchedule.CourseSchedule?>
    lateinit var batchScheduleAdapter: CourseScheduleAdapter

    lateinit var binding: FragmentCourseScheduleBinding
    var designationList = arrayListOf<SpinnerDataModel>()

    var loadingDialog: Dialog? = null
    var dialogCustome: Dialog? = null
    lateinit var dialogTrainingLoyeoutBinding: DialogTrainingLoyeoutBinding
    var isDesignationFirstSelection: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCourseScheduleBinding.inflate(inflater, container, false)
        init()
        budgetAndScheduleViewModel.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            getCourseSchedule()
            courseSchedule.observe(viewLifecycleOwner, Observer {
                dialog?.dismiss()
                dataList = it
                dataList += it
                dataList += it
                dataList += it
                dataList += it
                prepareRecycleView()

            })
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
    }

    fun prepareRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        batchScheduleAdapter = CourseScheduleAdapter()
        activity?.let {
            batchScheduleAdapter.setInitialData(
                dataList,
                it,
                object : OnCourseScheduleClickListener {
                    override fun onClick(
                        courseSchedule: BudgetAndSchedule.CourseSchedule?,
                        position: Int,
                        operation: Operation
                    ) {
                        when (operation) {
                            Operation.EDIT ->
                                showEditCreateDialog(operation, courseSchedule)

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
        courseSchedule: BudgetAndSchedule.CourseSchedule?
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
        dialogTrainingLoyeoutBinding.llCourseSchedule.visibility = View.VISIBLE
        dialogTrainingLoyeoutBinding.courseTitle.etText.setText(courseSchedule?.course_schedule_title)
        dialogTrainingLoyeoutBinding.courseTitleBn.etText.setText(courseSchedule?.course_schedule_title_bn)
        dialogTrainingLoyeoutBinding.courseCourseId.etText.setText("${courseSchedule?.course_id}")
        dialogTrainingLoyeoutBinding.courseTotalSeat.etText.setText("${courseSchedule?.total_seat}")


        courseSchedule?.designations?.let {
            it.forEach { item ->
                item?.designation?.let { it1 ->
                    designationList.add(it1)
                    dialogTrainingLoyeoutBinding.tvDesignation.append(
                        if (preparence.getLanguage().equals("en")) {
                            "${item?.designation?.name},"
                        } else "${item?.designation?.name_bn},"
                    )
                }
            }
        }

        commonRepo?.getCommonData("/api/auth/designation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            dialogTrainingLoyeoutBinding?.courseDesignation?.spinner!!,
                            context,
                            list,
                            designationList?.get(designationList?.size - 1).id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    var spinnerDataModel = any as SpinnerDataModel
                                    if (!isDesignationFirstSelection) {
                                        designationList?.add(spinnerDataModel)
                                        dialogTrainingLoyeoutBinding.tvDesignation.append(
                                            if (preparence.getLanguage().equals("en")) {
                                                "${spinnerDataModel?.name},"
                                            } else "${spinnerDataModel?.name_bn},"
                                        )

                                    } else {
                                        isDesignationFirstSelection = false
                                    }
                                }
                            }
                        )
                    }
                }
            })



        dialogTrainingLoyeoutBinding.courseScheduleHeader.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }

        dialogTrainingLoyeoutBinding.courseScheduleUpdateButton.btnUpdate.setText(getString(R.string.update))
        dialogTrainingLoyeoutBinding.courseScheduleUpdateButton.btnUpdate.setOnClickListener {
            invisiableAllError()
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            when (operation) {
                Operation.EDIT ->
                    courseSchedule?.id?.let { it1 ->
                        budgetAndScheduleViewModel.updateCourseSchedule(
                            getMapData(courseSchedule),
                            it1
                        ).observe(viewLifecycleOwner, Observer {
                            loadingDialog?.dismiss()
                            showResponse(it)
                        })
                    }
                Operation.CREATE -> {
                }
            }
        }
        dialogCustome?.show()
    }

    fun showResponse(any: Any?) {
        if (any is String) {
            toast(activity, any)
            budgetAndScheduleViewModel.getCourseSchedule()
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
                                dialogTrainingLoyeoutBinding?.courseTitle?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseTitle?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        Log.d("ok", "error ${ErrorUtils2.mainError(message)}")
                        when (error) {
                            "course_schedule_title" -> {
                                dialogTrainingLoyeoutBinding?.courseTitle?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseTitle?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "course_schedule_title_bn" -> {
                                dialogTrainingLoyeoutBinding?.courseTitleBn?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseTitleBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "designations" -> {
                                dialogTrainingLoyeoutBinding?.courseDesignation?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseDesignation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "course_id" -> {
                                dialogTrainingLoyeoutBinding?.courseCourseId?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseCourseId?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "total_seat" -> {
                                dialogTrainingLoyeoutBinding?.courseTotalSeat?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseTotalSeat?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "coordinator" -> {
                                dialogTrainingLoyeoutBinding?.courseCoOrdinator?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseCoOrdinator?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "co_coordinator" -> {
                                dialogTrainingLoyeoutBinding?.courseCoCoOrdinator?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseCoCoOrdinator?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "staff1" -> {
                                dialogTrainingLoyeoutBinding?.courseStaff1?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseStaff1?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "staff2" -> {
                                dialogTrainingLoyeoutBinding?.courseStaff2?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseStaff2?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "staff3" -> {
                                dialogTrainingLoyeoutBinding?.courseStaff3?.tvError.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseStaff3?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                toast(EmployeeInfoActivity.context, e.toString())
            }

        } else if (any is Throwable) {
            toast(EmployeeInfoActivity.context, any.toString())
        } else {
            EmployeeInfoActivity?.context?.getString(R.string.failed)?.let {
                toast(
                    EmployeeInfoActivity.context,
                    it
                )
            }
        }
    }

    fun getMapData(courseSchedule: BudgetAndSchedule.CourseSchedule?): HashMap<Any, Any?> {
        var designationIdList = arrayListOf<Int>()
        designationList?.forEach { element ->
            element?.id?.let { designationIdList.add(it) }
        }
        var map = HashMap<Any, Any?>()
        map.put(
            "course_schedule_title",
            dialogTrainingLoyeoutBinding.courseTitle.etText.text.trim().toString()
        )
        map.put(
            "course_schedule_title_bn",
            dialogTrainingLoyeoutBinding.courseTitleBn.etText.text.trim().toString()
        )
        map.put(
            "total_seat",
            dialogTrainingLoyeoutBinding.courseTotalSeat.etText.text.trim().toString()
        )
        map.put(
            "course_id",
            dialogTrainingLoyeoutBinding.courseCourseId.etText.text.trim().toString()
        )
        map.put("coordinator", courseSchedule?.coordinator?.id)
        map.put("co_coordinator", courseSchedule?.co_coordinator?.id)
        map.put("designations", designationIdList)
        map.put("staff1", courseSchedule?.staff1?.id)
        map.put("staff2", courseSchedule?.staff2?.id)
        map.put("staff3", courseSchedule?.staff3?.id)
        return map
    }

    fun invisiableAllError() {
        dialogTrainingLoyeoutBinding.courseTitle?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseTitleBn?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseTotalSeat?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseCourseId?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseDesignation?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseCoOrdinator?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseCoCoOrdinator?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseStaff1?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseStaff2?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.courseStaff3?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }


}