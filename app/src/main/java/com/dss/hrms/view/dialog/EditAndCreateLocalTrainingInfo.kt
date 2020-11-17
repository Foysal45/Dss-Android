package com.dss.hrms.view.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.model.Employee
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DatePicker
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.CommonDataValueListener
import com.dss.hrms.view.`interface`.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.`interface`.OnDateListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*

class EditAndCreateLocalTrainingInfo {
    var dialogCustome: Dialog? = null
    var localTrainings: Employee.LocalTrainings? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var gender: SpinnerDataModel? = null


    fun showDialog(
        context: Context,
        localTrainings1: Employee.LocalTrainings,
        key: String
    ) {
        this.localTrainings = localTrainings1
        this.context = context
        this.key = key
        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info,
            null,
            false
        )
        binding?.getRoot()?.let { dialogCustome?.setContentView(it) }
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateLocalTraining(context, localTrainings1)
        dialogCustome?.show()

    }

    fun updateLocalTraining(
        context: Context,
        localTrainings1: Employee.LocalTrainings?
    ) {

        binding?.llLocalTrainingInfo?.visibility = View.VISIBLE
        binding?.fLocalTrainingLocation?.llBody?.visibility = View.VISIBLE
        binding?.fLocalTrainingLocationBn?.llBody?.visibility = View.VISIBLE
        binding?.fLocalTrainingCountry?.llBody?.visibility = View.GONE
        binding?.hLocaltraining?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })
        binding?.hLocaltraining?.title=context.getString(R.string.local_training)
        if (key.equals(StaticKey.CREATE)) {
            binding?.trainingBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.trainingBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fLocalTrainingCourseT?.etText?.setText(localTrainings1?.course_title)
        binding?.fLocalTrainingCourseTBn?.etText?.setText(localTrainings1?.course_title_bn)
        binding?.fLocalTrainingNOInst?.etText?.setText(localTrainings1?.name_of_institute)
        binding?.fLocalTrainingNOInstBn?.etText?.setText(localTrainings1?.name_of_institute_bn)
        binding?.fLocalTrainingLocation?.etText?.setText(localTrainings1?.location)
        binding?.fLocalTrainingLocationBn?.etText?.setText(localTrainings1?.location_bn)
        binding?.fLocalTrainingFromDate?.tvText?.setText(localTrainings1?.from_date)
        binding?.fLocalTrainingToDate?.tvText?.setText(localTrainings1?.to_date)

        binding?.fLocalTrainingToDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fLocalTrainingToDate?.tvText?.setText("" + it) }
                }
            })
        })
        binding?.fLocalTrainingFromDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fLocalTrainingFromDate?.tvText?.setText("" + it) }
                }
            })
        })


        binding?.trainingBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo = ViewModelProviders.of(MainActivity.context!!)
                .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateLocalTrainingInfo(
                        localTrainings1?.id,
                        getMapData()
                    )
                        ?.observe(EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                } else {
                    employeeInfoEditCreateRepo?.addLocalTrainingInfo(getMapData())
                        ?.observe(EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                }
            }
        })


    }

    fun showResponse(any: Any) {
        if (any is String) {
            toast(EmployeeInfoActivity.context, any)
            MainActivity.selectedPosition = 9
            EmployeeInfoActivity.refreshEmployeeInfo()
            dialogCustome?.dismiss()
        } else if (any is ApiError) {
            try {
                if (any.getError().isEmpty()) {
                    toast(EmployeeInfoActivity?.context, any.getMessage())
                    Log.d("ok", "error")
                } else {
                    for (n in any.getError().indices) {
                        val error = any.getError()[n].getField()
                        val message = any.getError()[n].getMessage()
                        if (TextUtils.isEmpty(error)) {
                            message?.let {
                                binding?.fLocalTrainingToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingToDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "course_title" -> {
                                binding?.fLocalTrainingCourseT?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingCourseT?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "course_title_bn" -> {
                                binding?.fLocalTrainingCourseTBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingCourseTBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_of_institute" -> {
                                binding?.fLocalTrainingNOInst?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingNOInst?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_of_institute_bn" -> {
                                binding?.fLocalTrainingNOInstBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingNOInstBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "location" -> {
                                binding?.fLocalTrainingLocation?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingLocation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "location_bn" -> {
                                binding?.fLocalTrainingLocationBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingLocationBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "from_date" -> {
                                binding?.fLocalTrainingFromDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingFromDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "to_date" -> {
                                binding?.fLocalTrainingToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingToDate?.tvError?.text =
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

    fun getMapData(): HashMap<Any, Any?> {

        var map = HashMap<Any, Any?>()
        map.put("employee_id", MainActivity?.employee?.user?.employee_id)
        map.put("course_title", binding?.fLocalTrainingCourseT?.etText?.text.toString())
        map.put("course_title_bn", binding?.fLocalTrainingCourseTBn?.etText?.text.toString())
        map.put("name_of_institute", binding?.fLocalTrainingNOInst?.etText?.text.toString())
        map.put("name_of_institute_bn", binding?.fLocalTrainingNOInstBn?.etText?.text.toString())
        map.put("location", binding?.fLocalTrainingLocation?.etText?.text.toString())
        map.put("location_bn", binding?.fLocalTrainingLocationBn?.etText?.text.toString())
        map.put("from_date", binding?.fLocalTrainingFromDate?.tvText?.text.toString())
        map.put("to_date", binding?.fLocalTrainingToDate?.tvText?.text.toString())
        map.put("status", localTrainings?.status)
        return map
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fLocalTrainingCourseT?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingCourseTBn?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingNOInst?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingNOInstBn?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingLocation?.tvError?.visibility =
            View.GONE
        binding?.fLocalTrainingLocationBn?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingFromDate?.tvError?.visibility =
            View.GONE
        binding?.fLocalTrainingToDate?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

}