package com.dss.hrms.view.personalinfo.dialog

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
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.Office
import com.dss.hrms.model.Paysacle
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DateConverter
import com.dss.hrms.util.DatePicker
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.*
import com.dss.hrms.view.dialog.OfficeSearchingDialog
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject

class EditJobJoiningInformation @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData


    @Inject
    lateinit var officeSearchingDialog: OfficeSearchingDialog

    lateinit var utilViewmodel: UtilViewModel

    var mainOfficeList: List<Office>? = null

    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var payScale: Paysacle? = null
    var jobjoining: Employee.Jobjoinings? = null

    lateinit var binding: DialogPersonalInfoBinding
    var office: Office? = null
    var desingNation: SpinnerDataModel? = null;
    var additionalDesingNation: SpinnerDataModel? = null
    var department: SpinnerDataModel? = null;
    var jobType: SpinnerDataModel? = null
    var currentJob: SpinnerDataModel? = null
    var _class: SpinnerDataModel? = null
    var grade: SpinnerDataModel? = null
    lateinit var context: Context


    fun showDialog(
        context: Context,
        position: Int?,
        utilViewmodel: UtilViewModel
    ) {

        this.position = position
        this.jobjoining = position?.let { employeeProfileData?.employee?.jobjoinings?.get(it) }
        this.utilViewmodel = utilViewmodel
        this.context = context
        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info,
            null,
            false
        )
        dialogCustome?.setContentView(binding.getRoot())
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateJobjoiningInfo(context, binding)
        dialogCustome?.show()

    }

    fun updateJobjoiningInfo(
        context: Context,
        binding: DialogPersonalInfoBinding
    ) {

        binding.llJobjoningInfo.visibility = View.VISIBLE
        binding.hJobJoiningInformation.tvClose.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (employeeProfileData?.employee?.designation_id == jobjoining?.designation_id &&
            employeeProfileData?.employee?.office_id == jobjoining?.office_id) {
            currentJob=currentJobData().get(0)
        }else{
            currentJob=currentJobData().get(1)
        }

        binding.fJobJoiningPensionDate.llBody.visibility = View.GONE
        binding.fJobJoiningPrlDate.llBody.visibility = View.GONE

        binding.fJobJoiningJoiningDate.tvText.setText(jobjoining?.joining_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })
        binding.fJobJoiningConfirmationDate.tvText.setText(jobjoining?.confirmation_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })
        binding.fJobJoiningPensionDate.tvText.setText(jobjoining?.pension_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })
        binding.fJobJoiningPrlDate.tvText.setText(jobjoining?.prl_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })


        binding.JobJoiningIvSearch.setOnClickListener {
            officeSearchingDialog.showOfficeSearchDialog(
                context,
                utilViewmodel,
                object : OfficeDataValueListener {
                    override fun valueChange(officeList: List<Office>?) {
                        mainOfficeList = officeList
                        setOffice(context, binding)
                    }
                })
        }


        Log.e("officeid", "office id : " + jobjoining?.office_id)
        commonRepo.getOffice("/api/auth/office/list/basic",
            object : OfficeDataValueListener {
                override fun valueChange(officeList: List<Office>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    if (mainOfficeList == null) {
                        mainOfficeList = officeList
                        setOffice(context, binding)
                    }
                }
            })
        commonRepo.getCommonData("/api/auth/department/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningDepartment.spinner,
                            context,
                            list,
                            jobjoining?.department_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    department = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        commonRepo.getCommonData("/api/auth/job-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //  Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningJobType.spinner,
                            context,
                            list,
                            jobjoining?.job_type_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    jobType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo.getCommonData2("/api/auth/employee-class",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningClass.spinner,
                            context,
                            list,
                            jobjoining?.employee_class_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    _class = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        commonRepo.getCommonData("/api/auth/salary-grade/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    Log.e("grade", "grade message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningGrade.spinner,
                            context,
                            list,
                            jobjoining?.grade_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    grade = any as SpinnerDataModel

                                    grade?.paysacle?.let {
                                        setPayScale(
                                            grade?.id,
                                            jobjoining?.pay_scale
                                        )
                                    }
                                }

                            }
                        )
                    }
                }
            })

        SpinnerAdapter().setSpinner(
            binding.fJobJoiningCurrentJob.spinner,
            context,
            currentJobData(),
            currentJob?.id,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                     currentJob=any as SpinnerDataModel
                }
            }
        )


        binding.fJobJoiningJoiningDate.tvText.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding.fJobJoiningJoiningDate.tvText.setText("" + it) }
                }
            })
        })
        binding.fJobJoiningConfirmationDate.tvText.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding.fJobJoiningConfirmationDate.tvText.setText("" + it) }
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
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)

            var joininfDate =
                DateConverter.changeDateFormateForSending(binding.fJobJoiningJoiningDate.tvText.text.toString())
            var confirmation_date =
                DateConverter.changeDateFormateForSending(binding.fJobJoiningConfirmationDate.tvText.text.toString())
            var pension_date =
                DateConverter.changeDateFormateForSending(binding.fJobJoiningPensionDate.tvText.text.toString())
            var prl_date =
                DateConverter.changeDateFormateForSending(binding.fJobJoiningPrlDate.tvText.text.toString())

            var map = HashMap<Any, Any?>()
            Log.e(
                "employeeid",
                "employee id ${employeeProfileData?.employee?.id} jobjoining?.id  ${jobjoining?.id}"
            )
            map.put("employee_id", employeeProfileData?.employee?.id)
            map.put("office_id", office?.id)
            map.put("designation_id", desingNation?.id)
            map.put("additional_designation_id", additionalDesingNation?.id)
            map.put("department_id", department?.id)
            map.put("job_type_id", jobType?.id)
            map.put("employee_class_id", _class?.id)
            map.put("grade_id", grade?.id)
            map.put("pay_scale", payScale?.amount)
            map.put("current", if (currentJob?.id==1) true else false )
            map.put("joining_date", joininfDate)
            map.put("confirmation_date", confirmation_date)
            map.put("pension_date", pension_date)
            map.put("prl_date", prl_date)
            map.put("status", jobjoining?.status)

            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            employeeInfoEditCreateRepo?.updateJobJoiningInfo(jobjoining?.id, map)
                ?.observe(
                    EmployeeInfoActivity.context!!,
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
                                            "additional_designation_id" -> {
                                                binding.fJobJoiningAdditionalDesignation.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningAdditionalDesignation.tvError.text =
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
                                            "pay_scale" -> {
                                                binding.fJobJoiningPayScale.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningPayScale.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "joining_date" -> {
                                                binding.fJobJoiningJoiningDate.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningJoiningDate.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "confirmation_date" -> {
                                                binding.fJobJoiningConfirmationDate.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningConfirmationDate.tvError.text =
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

    fun setPayScale(gradeId: Int?, payScaleAmount: String?) {
        commonRepo.getSpecificSalaryGrade("/api/auth/salary-grade/${gradeId}",
            object : PayScaleValueListener {
                override fun valueChange(spinnerDataModel: SpinnerDataModel?) {
                    Log.e(
                        "payscale",
                        "payscale message " + Gson().toJson(spinnerDataModel?.paysacle)
                    )
                    spinnerDataModel?.paysacle?.let {
                        SpinnerAdapter().setPayscale(
                            binding.fJobJoiningPayScale.spinner,
                            context,
                            it,
                            payScaleAmount,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    payScale = any as Paysacle
                                }
                            }
                        )
                    }
                }
            })

    }

    fun setOffice(
        context: Context,
        binding: DialogPersonalInfoBinding
    ) {
        mainOfficeList?.let {
            SpinnerAdapter().setOfficeSpinner(
                binding.fJobJoiningOffice.spinner,
                context,
                it,
                jobjoining?.office_id,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        office = any as Office
                        loadDesignation(office?.id, context, binding)
                        loadAdditionalDesignation(office?.id, context, binding)
                        Log.e("selected item", " item : " + office?.name)
                    }

                }
            )
        }
    }

    fun loadDesignation(officeId: Int?, context: Context, binding: DialogPersonalInfoBinding) {
        commonRepo.getDesignationData("/api/auth/office/${officeId}",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningDesignation.spinner,
                            context,
                            list,
                            jobjoining?.designation_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    desingNation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
    }

    fun loadAdditionalDesignation(
        officeId: Int?,
        context: Context,
        binding: DialogPersonalInfoBinding
    ) {
        commonRepo.getDesignationData("/api/auth/office/${officeId}",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningAdditionalDesignation.spinner,
                            context,
                            list,
                            jobjoining?.additional_designation_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    additionalDesingNation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
    }

    fun currentJobData(): List<SpinnerDataModel> {

        var rel = SpinnerDataModel()
        rel.apply {
            id = 1
            name = "Yes"
            name_bn = "হ্যাঁ"

        }
        var rel1 = SpinnerDataModel()
        rel1.apply {
            id = 0
            name = "No"
            name_bn = "না"


        }
        return arrayListOf(rel, rel1)
    }

    fun invisiableAllError(binding: DialogPersonalInfoBinding) {
        binding.fJobJoiningOffice.tvError.visibility =
            View.GONE

        binding.fJobJoiningDesignation.tvError.visibility =
            View.GONE
        binding.fJobJoiningAdditionalDesignation.tvError.visibility =
            View.GONE

        binding.fJobJoiningDepartment.tvError.visibility =
            View.GONE

        binding.fJobJoiningJobType.tvError.visibility =
            View.GONE

        binding.fJobJoiningClass.tvError.visibility =
            View.GONE

        binding.fJobJoiningGrade.tvError.visibility =
            View.GONE

        binding.fJobJoiningPayScale.tvError.visibility =
            View.GONE

        binding.fJobJoiningJoiningDate.tvError.visibility =
            View.GONE

        binding.fJobJoiningConfirmationDate.tvError.visibility =
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