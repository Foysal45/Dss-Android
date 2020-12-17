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
import com.dss.hrms.model.Office
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DatePicker
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.CommonDataValueListener
import com.dss.hrms.view.`interface`.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.`interface`.OfficeDataValueListener
import com.dss.hrms.view.`interface`.OnDateListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*

class EditJobJoiningInformation {
    var dialogCustome: Dialog? = null

    fun showDialog(context: Context, jobjoinings: Employee.Jobjoinings) {

        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        var dialogCustomeBinding: DialogPersonalInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info,
            null,
            false
        )
        dialogCustome?.setContentView(dialogCustomeBinding.getRoot())
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateJobjoiningInfo(context, dialogCustomeBinding, jobjoinings)
        dialogCustome?.show()

    }

    fun updateJobjoiningInfo(
        context: Context,
        binding: DialogPersonalInfoBinding,
        jobjoinings: Employee.Jobjoinings?
    ) {
//        binding.hBasicInformation.tvTitle.setText(context?.getString(R.string.personal_information))
//        binding.fEmployeeId.tvTitle.setText(context?.getString(R.string.employee_id))
//        binding.fEmployeeId.etText.setHint(context?.getString(R.string.employee_id))
//
//        binding.fNameEng.tvTitle.setText(context?.getString(R.string.name))
//        binding.fNameEng.etText.setHint(context?.getString(R.string.name))
//
//        binding.fNameBangla.tvTitle.setText(context?.getString(R.string.name_b))
//        binding.fNameBangla.etText.setHint(context?.getString(R.string.name_b))
//
//        binding.fDOB.tvTitle.setText(context?.getString(R.string.birth))
//        binding.fDOB.tvText.setText(context?.getString(R.string.birth))
//
//        binding.fFatherNameEng.tvTitle.setText(context?.getString(R.string.f_name))
//        binding.fFatherNameEng.etText.setHint(context?.getString(R.string.f_name))
//        binding.fFatherNameBangla.tvTitle.setText(context?.getString(R.string.f_name_b))
//        binding.fFatherNameBangla.etText.setHint(context?.getString(R.string.f_name_b))
//        binding.fMotherNameEng.tvTitle.setText(context?.getString(R.string.m_name))
//        binding.fMotherNameEng.etText.setHint(context?.getString(R.string.m_name))
//        binding.fMotherNameBangla.tvTitle.setText(context?.getString(R.string.m_name_b))
//        binding.fMotherNameBangla.etText.setHint(context?.getString(R.string.m_name_b))
//        binding.fGender.tvTitle.setText(context?.getString(R.string.gender))
//        binding.fGender.etText.setHint(context?.getString(R.string.gender))
//        binding.tvImageTitle.setText(context?.getString(R.string.photo))

        //    jobjoinings?.user?.employee_id?.let { binding.fEmployeeId.etText.setText("" + it) }

        binding.llJobjoningInfo.visibility = View.VISIBLE
        binding.hJobJoiningInformation.tvClose.setOnClickListener({
            dialogCustome?.dismiss()
        })


        var office: Office? = null
        var desingNation: SpinnerDataModel? = null;
        var department: SpinnerDataModel? = null;
        var jobType: SpinnerDataModel? = null
        var _class: SpinnerDataModel? = null
        var grade: SpinnerDataModel? = null

        binding.fJobJoiningJoiningDate.tvText.setText(jobjoinings?.joining_date)
        binding.fJobJoiningPensionDate.tvText.setText(jobjoinings?.pension_date)
        binding.fJobJoiningPrlDate.tvText.setText(jobjoinings?.prl_date)

        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/designation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningDesignation.spinner,
                            context,
                            list,
                            jobjoinings?.designation_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    desingNation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        Log.e("officeid", "office id : " + jobjoinings?.office_id)
        CommonRepo(MainActivity.appContext).getOffice("/api/auth/office/list",
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setOfficeSpinner(
                            binding.fJobJoiningOffice.spinner,
                            context,
                            list,
                            jobjoinings?.office_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    office = any as Office
                                    Log.e("selected item", " item : " + office?.name)
                                }

                            }
                        )
                    }
                }
            })
        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/department/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningDepartment.spinner,
                            context,
                            list,
                            jobjoinings?.department_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    department = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/job-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //  Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningJobType.spinner,
                            context,
                            list,
                            jobjoinings?.job_type_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    jobType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        CommonRepo(MainActivity.appContext).getCommonData2("/api/auth/employee-class",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningClass.spinner,
                            context,
                            list,
                            jobjoinings?.employee_class_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    _class = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/salary-grade/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    Log.e("grade", "grade message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningGrade.spinner,
                            context,
                            list,
                            jobjoinings?.grade_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    grade = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        binding.fJobJoiningJoiningDate.tvText.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding.fJobJoiningJoiningDate.tvText.setText("" + it) }
                }
            })
        })
        binding.fJobJoiningPensionDate.tvText.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding.fJobJoiningPensionDate.tvText.setText("" + it) }
                }
            })
        })
        binding.fJobJoiningPrlDate.tvText.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding.fJobJoiningPrlDate.tvText.setText("" + it) }
                }
            })
        })

        binding.jobjoiningUpdateButton.btnUpdate.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!)
                    .get(EmployeeInfoEditCreateViewModel::class.java)

            var map = HashMap<Any, Any?>()
            map.put("employee_id", MainActivity?.employee?.user?.employee_id)
            map.put("office_id", office?.id)
            map.put("designation_id", desingNation?.id)
            map.put("department_id", department?.id)
            map.put("job_type_id", jobType?.id)
            map.put("employee_class_id", _class?.id)
            map.put("grade_id", grade?.id)
            map.put("joining_date", binding.fJobJoiningJoiningDate.tvText.text.toString())
            map.put("pension_date", binding.fJobJoiningPensionDate.tvText.text.toString())
            map.put("prl_date", binding.fJobJoiningPrlDate.tvText.text.toString())
            map.put("status", jobjoinings?.status)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            employeeInfoEditCreateRepo?.updateJobJoiningInfo(jobjoinings?.id, map)
                ?.observe(EmployeeInfoActivity.context!!,
                    Observer { any ->
                        dialog?.dismiss()
                        Log.e("yousuf", "error : " + Gson().toJson(any))

                        if (any is String) {
                            toast(EmployeeInfoActivity.context, "Updated")

                            MainActivity.selectedPosition = 7
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
                                                binding.fJobJoiningPrlDate.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningPrlDate.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }

                                        }

                                        when (error) {
                                            "office_id" -> {
                                                binding.fJobJoiningOffice.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningOffice.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "designation_id" -> {
                                                binding.fJobJoiningDesignation.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningDesignation.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "department_id" -> {
                                                binding.fJobJoiningDepartment.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningDepartment.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "job_type_id" -> {
                                                binding.fJobJoiningJobType.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningJobType.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "employee_class_id" -> {
                                                binding.fJobJoiningClass.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningClass.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "grade_id" -> {
                                                binding.fJobJoiningGrade.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningGrade.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "joining_date" -> {
                                                binding.fJobJoiningJoiningDate.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningJoiningDate.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "pension_date" -> {
                                                binding.fJobJoiningPensionDate.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningPensionDate.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "prl_date" -> {
                                                binding.fJobJoiningPrlDate.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningPrlDate.tvError.text =
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
                    })
        })

    }

    fun invisiableAllError(binding: DialogPersonalInfoBinding) {
        binding.fJobJoiningOffice.tvError.visibility =
            View.GONE

        binding.fJobJoiningDesignation.tvError.visibility =
            View.GONE

        binding.fJobJoiningDepartment.tvError.visibility =
            View.GONE

        binding.fJobJoiningJobType.tvError.visibility =
            View.GONE

        binding.fJobJoiningClass.tvError.visibility =
            View.GONE

        binding.fJobJoiningGrade.tvError.visibility =
            View.GONE

        binding.fJobJoiningJoiningDate.tvError.visibility =
            View.GONE

        binding.fJobJoiningPensionDate.tvError.visibility =
            View.GONE


        binding.fJobJoiningPrlDate.tvError.visibility =
            View.GONE


    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}