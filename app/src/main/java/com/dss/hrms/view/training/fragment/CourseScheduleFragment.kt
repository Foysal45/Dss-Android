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
import com.dss.hrms.databinding.FragmentCourseScheduleBinding
import com.dss.hrms.model.JsonKeyReader
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.util.Operation.*
import com.dss.hrms.util.Role
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.training.TrainingActivity
import com.dss.hrms.view.training.`interface`.OnCourseScheduleClickListener
import com.dss.hrms.view.training.adaoter.CourseScheduleAdapter
import com.dss.hrms.view.training.adaoter.spinner.CourseScheduleSpinnerAdapter
import com.dss.hrms.view.training.adaoter.spinner.RoleWiseEmployeeAdapter
import com.dss.hrms.view.training.dialog.CourseScheduleSearchingDialog
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import java.util.*
import javax.inject.Inject


class CourseScheduleFragment : DaggerFragment() {

    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var courseScheduleSearchDialog: CourseScheduleSearchingDialog

    lateinit var budgetAndScheduleViewModel: BudgetAndScheduleViewModel
    lateinit var employeeViewModel: EmployeeViewModel

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataList: List<BudgetAndSchedule.CourseSchedule?>
    lateinit var batchScheduleAdapter: CourseScheduleAdapter

    lateinit var binding: FragmentCourseScheduleBinding
    var designationList = arrayListOf<SpinnerDataModel>()

    var loadingDialog: Dialog? = null
    var dialogCustome: Dialog? = null
    lateinit var dialogTrainingLoyeoutBinding: DialogTrainingLoyeoutBinding
    var isDesignationFirstSelection: Boolean = true

    var coordinator: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var coordinatorExternal: SpinnerDataModel? = null
    var cocoordinatorExternal: SpinnerDataModel? = null
    var cocoordinator: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var staff1: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var staff2: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var staff3: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var course: BudgetAndSchedule.Course? = null


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
                it?.let {
                    dataList = it
                    prepareRecycleView()

                }

            })
        }

        binding.llSearch.setOnClickListener {
            courseScheduleSearchDialog.showCourseScheduleSearchDialog(
                activity,
                budgetAndScheduleViewModel
            )
        }
        binding.fab.setOnClickListener {
            showEditCreateDialog(CREATE, null)
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

        val list = preparence.get("permissionList") as List<Any>?

        if (!JsonKeyReader.hasPermission(
                "budgetandschedulecourseschedule-add",
                list
            )
        ) {
            binding.fab.visibility = View.GONE
        }
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
                            EDIT -> {
                                val list = preparence.get("permissionList") as List<Any>?

                                if (JsonKeyReader.hasPermission(
                                        "budgetandschedulecourseschedule-edit",
                                        list
                                    )
                                ) {
                                    showEditCreateDialog(operation, courseSchedule)
                                }
                            }


                            CREATE ->
                                Toast.makeText(activity, "" + operation, Toast.LENGTH_LONG).show()

                            else -> {}
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

        dialogCustome = TrainingActivity.context?.let { Dialog(it) }
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogTrainingLoyeoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(TrainingActivity.context),
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
        dialogTrainingLoyeoutBinding.courseTotalSeat.etText.setText(courseSchedule?.total_seat.toString())

        dialogTrainingLoyeoutBinding.courseScheduleHeader.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }



        if (courseSchedule?.coordinator_is_external == 1) {
            dialogTrainingLoyeoutBinding.courseCoOrdinatorExternal.llBody.visibility =
                View.VISIBLE
            dialogTrainingLoyeoutBinding.courseCoOrdinator.llBody.visibility = View.GONE
            dialogTrainingLoyeoutBinding.cbCoCoordinatorExternal.isChecked = true

        } else {
            dialogTrainingLoyeoutBinding.courseCoOrdinatorExternal.llBody.visibility = View.GONE
            dialogTrainingLoyeoutBinding.courseCoOrdinator.llBody.visibility = View.VISIBLE
            dialogTrainingLoyeoutBinding.cbCoCoordinatorExternal.isChecked = false
        }



        if (courseSchedule?.co_coordinator_is_external == 1) {
            dialogTrainingLoyeoutBinding.courseCoCoOrdinatorExternal.llBody.visibility =
                View.VISIBLE
            dialogTrainingLoyeoutBinding.courseCoCoOrdinator.llBody.visibility = View.GONE
            dialogTrainingLoyeoutBinding.cbCoCoCoordinatorExternal.isChecked = true
        } else {
            dialogTrainingLoyeoutBinding.courseCoCoOrdinatorExternal.llBody.visibility = View.GONE
            dialogTrainingLoyeoutBinding.courseCoCoOrdinator.llBody.visibility = View.VISIBLE
            dialogTrainingLoyeoutBinding.cbCoCoCoordinatorExternal.isChecked = false
        }


        dialogTrainingLoyeoutBinding.cbCoCoCoordinatorExternal.setOnClickListener {
            if (dialogTrainingLoyeoutBinding.cbCoCoCoordinatorExternal.isChecked) {
                dialogTrainingLoyeoutBinding.courseCoCoOrdinatorExternal.llBody.visibility =
                    View.VISIBLE
                dialogTrainingLoyeoutBinding.courseCoCoOrdinator.llBody.visibility = View.GONE
            } else {
                dialogTrainingLoyeoutBinding.courseCoCoOrdinatorExternal.llBody.visibility =
                    View.GONE
                dialogTrainingLoyeoutBinding.courseCoCoOrdinator.llBody.visibility = View.VISIBLE
            }
        }

        dialogTrainingLoyeoutBinding.cbCoCoordinatorExternal.setOnClickListener {
            if (dialogTrainingLoyeoutBinding.cbCoCoordinatorExternal.isChecked) {
                dialogTrainingLoyeoutBinding.courseCoOrdinatorExternal.llBody.visibility =
                    View.VISIBLE
                dialogTrainingLoyeoutBinding.courseCoOrdinator.llBody.visibility = View.GONE
            } else {
                dialogTrainingLoyeoutBinding.courseCoOrdinatorExternal.llBody.visibility = View.GONE
                dialogTrainingLoyeoutBinding.courseCoOrdinator.llBody.visibility = View.VISIBLE
            }
        }

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

        Log.e(
            "coordinatorid",
            "coordination id .........................................${cocoordinatorExternal?.id}"
        )

        commonRepo.getCommonData("/api/auth/co-coordinator/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            dialogTrainingLoyeoutBinding?.courseCoCoOrdinatorExternal?.spinner!!,
                            context,
                            list,
                            courseSchedule?.external_co_coordinator?.id,
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
                            dialogTrainingLoyeoutBinding?.courseCoOrdinatorExternal?.spinner!!,
                            context,
                            list,
                            courseSchedule?.external_coordinator?.id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    coordinatorExternal = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo?.getCommonData("/api/auth/designation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            dialogTrainingLoyeoutBinding?.courseDesignation?.spinner!!,
                            context,
                            list,
                            if (designationList?.size!! >= 1) designationList?.get(designationList?.size?.minus(
                                1
                            ) ?: 0)?.id else 0,
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




        budgetAndScheduleViewModel?.apply {
            getCourseList().observe(viewLifecycleOwner, Observer {
                Log.e("course", "course schedule ; " + it?.size)

                CourseScheduleSpinnerAdapter().setCourseSpinner(
                    dialogTrainingLoyeoutBinding?.courseCourseId?.spinner!!,
                    context,
                    it,
                    courseSchedule?.course_id,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {

                            any?.let {
                                // Log.e("courseschedule","course schedule : ${(any as BudgetAndSchedule.CourseScheduleList).id}")
                                course = (any as BudgetAndSchedule.Course)
                            }

                        }
                    }
                )
            })


        }


        employeeViewModel?.apply {
            getRoleWiseEmployeeInfo(Role.COORDINATOR).observe(viewLifecycleOwner, Observer {
                RoleWiseEmployeeAdapter().setRoleWiseEmployeeSpinner(
                    dialogTrainingLoyeoutBinding?.courseCoOrdinator?.spinner!!,
                    context,
                    it,
                    courseSchedule?.course_coordinator?.id,
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
                    dialogTrainingLoyeoutBinding?.courseCoCoOrdinator?.spinner!!,
                    context,
                    it,
                    courseSchedule?.course_co_coordinator?.id,
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

            getRoleWiseEmployeeInfo(Role.STAFF2).observe(viewLifecycleOwner, Observer {
                RoleWiseEmployeeAdapter().setRoleWiseEmployeeSpinner(
                    dialogTrainingLoyeoutBinding?.courseStaff2?.spinner!!,
                    context,
                    it,
                    courseSchedule?.staff2?.id,
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
                    dialogTrainingLoyeoutBinding?.courseStaff3?.spinner!!,
                    context,
                    it,
                    courseSchedule?.staff3?.id,
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
            getRoleWiseEmployeeInfo(Role.STAFF1).observe(viewLifecycleOwner, Observer {
                Log.e("staff1tag", "staff 1 ${it.size}")
                RoleWiseEmployeeAdapter().setRoleWiseEmployeeSpinner(
                    dialogTrainingLoyeoutBinding?.courseStaff1?.spinner!!,
                    context,
                    it,
                    courseSchedule?.staff1?.id,
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

        }



        dialogTrainingLoyeoutBinding.courseScheduleHeader.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }

        if (operation == EDIT) dialogTrainingLoyeoutBinding.courseScheduleUpdateButton.btnUpdate.setText(
            getString(R.string.update)
        ) else dialogTrainingLoyeoutBinding.courseScheduleUpdateButton.btnUpdate.setText(getString(R.string.create))
        dialogTrainingLoyeoutBinding.courseScheduleUpdateButton.btnUpdate.setOnClickListener {
            invisiableAllError()
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            when (operation) {
                EDIT ->
                    courseSchedule?.id?.let { it1 ->
                        budgetAndScheduleViewModel.updateCourseSchedule(
                            getMapData(courseSchedule),
                            it1
                        ).observe(viewLifecycleOwner, Observer {
                            loadingDialog?.dismiss()
                            showResponse(it)
                        })
                    }
                CREATE -> {
                    budgetAndScheduleViewModel.addCourseSchedule(
                        getMapData(courseSchedule)
                    ).observe(viewLifecycleOwner, Observer {
                        loadingDialog?.dismiss()
                        showResponse(it)
                    })
                }
                VIEW -> TODO()
                DELETE -> TODO()
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
                                dialogTrainingLoyeoutBinding?.courseStaff3?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.courseStaff3?.tvError?.text =
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
            EmployeeInfoActivity?.context?.getString(R.string.failed)?.let {
                toast(
                    activity,
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
            dialogTrainingLoyeoutBinding.courseTitle?.etText?.text?.trim().toString()
        )
        map.put(
            "course_schedule_title_bn",
            dialogTrainingLoyeoutBinding.courseTitleBn?.etText?.text?.trim().toString()
        )
        map.put(
            "total_seat",
            dialogTrainingLoyeoutBinding.courseTotalSeat?.etText?.text?.trim().toString()
        )
        map.put("course_id", course?.id)
        map.put("designations", designationIdList)
        map.put(
            "co_coordinator_is_external",
            dialogTrainingLoyeoutBinding.cbCoCoCoordinatorExternal.isChecked
        )
        map.put(
            "coordinator_is_external",
            dialogTrainingLoyeoutBinding.cbCoCoordinatorExternal.isChecked
        )
        map.put(
            "coordinator",
            if (dialogTrainingLoyeoutBinding.cbCoCoordinatorExternal.isChecked) coordinatorExternal?.id else coordinator?.id
        )
        map.put(
            "co_coordinator",
            if (dialogTrainingLoyeoutBinding.cbCoCoordinatorExternal.isChecked) cocoordinatorExternal?.id else cocoordinator?.id
        )
        // map.put("designation_id", courseSchedule?.de)
        map.put("staff1", staff1?.id)
        map.put("staff2", staff2?.id)
        map.put("staff3", staff3?.id)
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