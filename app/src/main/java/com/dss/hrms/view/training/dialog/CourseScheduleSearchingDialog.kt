package com.dss.hrms.view.training.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogTrainingSearchBinding
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DateConverter
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.training.OnCourseScheduleValueListener
import com.dss.hrms.view.training.`interface`.OnCourseScheduleClickListener
import com.dss.hrms.view.training.`interface`.OnResourcePersonValueListener
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.view.training.viewmodel.TrainingManagementViewModel
import java.util.HashMap
import javax.inject.Inject

class CourseScheduleSearchingDialog @Inject constructor() {

    @Inject
    lateinit var commonRepo: CommonRepo

    lateinit var budgetAndScheduleViewModel: BudgetAndScheduleViewModel

    lateinit var dialogCustome: Dialog
    lateinit var binding: DialogTrainingSearchBinding


    var context: Context? = null
    var designation: SpinnerDataModel? = null

    fun showCourseScheduleSearchDialog(
        context: Context?,
        budgetAndScheduleViewModel: BudgetAndScheduleViewModel
    ) {
        dialogCustome = context?.let { Dialog(it) }!!
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.context = context
        this.budgetAndScheduleViewModel = budgetAndScheduleViewModel
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_training_search,
            null,
            false
        )
        binding?.getRoot()?.let { dialogCustome?.setContentView(it) }

        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        binding?.courceScheduleHeader?.tvClose?.setOnClickListener {
            dialogCustome.dismiss()
        }

        binding.llCourseScheduleSearch.visibility = View.VISIBLE


        binding.courseScheduleSearch.setOnClickListener {
            searchCourseSchedule()
        }
        dialogCustome?.show()
    }

    fun searchCourseSchedule() {
        var dialog = CustomLoadingDialog().createLoadingDialog(context)
        budgetAndScheduleViewModel.searchCourseSchedule(
            getMapData(),
            object : OnCourseScheduleValueListener {
                override fun onValueChange(courseScheduleList: List<BudgetAndSchedule.CourseSchedule>?) {
                    Log.e("searchCourseSchedule", " list : " + courseScheduleList?.size)
                    //  onResourcePersonValueListener.onValueChange(list)
                    dialog?.dismiss()
                    dialogCustome.dismiss()
                }
            })
    }

    fun getMapData(): HashMap<Any, Any?> {
        var map = HashMap<Any, Any?>()
        binding.courceScheduleTitle.etText.text.trim().toString()?.let {
            map.put("course_schedule_title", it)
        }
        binding.courceScheduleTitleBn.etText.text.trim().toString()?.let {
            map.put("course_schedule_title_bn", it)
        }
        binding.courceScheduleTotalSeat.etText.text.trim().toString()?.let {
            map.put("total_seat", it)
        }
        return map
    }

    fun getMapData1(): HashMap<Any, Any?> {
        var batchStartDate =
            DateConverter.changeDateFormateForSending(
                binding.batchScheduleStartDate.tvText.text?.trim().toString()
            )

        var batchEndDate =
            DateConverter.changeDateFormateForSending(
                binding.batchScheduleEndDate.tvText.text.trim().toString()
            )


        var map = HashMap<Any, Any?>()
        binding.batchScheduleName.etText.text.trim().toString()?.let {
            map.put("batch_name", it)
        }
        binding.batchScheduleNameBn.etText.text.trim().toString()?.let {
            map.put("batch_name_bn", it)
        }
        batchStartDate?.let {
            map.put("start_date", it)
        }
        batchEndDate.let {
            map.put("end_date", it)
        }
        return map
    }
}