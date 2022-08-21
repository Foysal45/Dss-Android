package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.Office
import com.dss.hrms.model.Paysacle
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
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
    var currentOfficeType: SpinnerDataModel? = null
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


        //for Office Type
        Log.d("office_type ",""+employeeProfileData.employee?.jobjoinings?.get(0)?.office_type)
        if (employeeProfileData.employee?.jobjoinings?.get(0)?.office_type == "head_office")
        {
            currentOfficeType?.id = 1
            Log.d("office_type ",""+ employeeProfileData.employee?.jobjoinings?.get(0)?.office_type +"  "+currentOfficeType?.id)
        } else {
            currentOfficeType?.id = 4
        }

        //for Joining Date
        binding.fJobJoiningJoiningDate.tvText.text = jobjoining?.joining_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        }

        //setting joining Date and Release Date according the employeementstatus?.id
        val idForJoiningAndReleaseDate = jobjoining?.employeementstatus?.id
        when(idForJoiningAndReleaseDate){
            1->{
                binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_effective_date}}")
                binding.fJobJoiningEditReleaseDateFrom.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_release_date}")
            }
            2->{
                binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_effective_date}}")
                binding.fJobJoiningEditReleaseDateFrom.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_release_date}")
            }
            4->{
                binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_effective_date}}")
                binding.fJobJoiningEditReleaseDateFrom.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_release_date}")
            }
            5->{
                //binding.fJobJoiningWhereToLien.llBody.visibility = View.GONE
                binding.fJobJoiningEditDateOf.tvTitle.text = "Suspension Date"
                binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Released Date from Suspension"
                binding.fJobJoiningEditDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_effective_date}")
                binding.fJobJoiningEditReleaseDateFrom.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_release_date}")
            }
        }


     /*   binding.JobJoiningIvSearch.setOnClickListener {
            officeSearchingDialog.showOfficeSearchDialog(
                context,
                utilViewmodel,
                object : OfficeDataValueListener {
                    override fun valueChange(officeList: List<Office>?) {
                        mainOfficeList = officeList
                        setOffice(context, binding)
                    }
                })
        }*/


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
     /*   commonRepo.getCommonData("/api/auth/department/list",
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
            })*/

     /*   commonRepo.getCommonData("/api/auth/job-type/list",
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
            })*/

        commonRepo.getCommonData2("/api/auth/employee-class",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditClass.spinner,
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

        commonRepo.getCommonData2("/api/auth/employee-class",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                       Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditOtherServiceParticulars.spinner,
                            context, list,
                            jobjoining?.employeementstatus?.id,
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
                            binding.fJobJoiningEditGrade.spinner,
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

        //set spinner for the Office Type Divisional/Head Office
        SpinnerAdapter().setSpinner(
            binding.fJobJoiningOfficeType.spinner, context,
            currentOfficeTypeData(), employeeProfileData.employee?.jobjoinings?.get(0)?.office?.office_type_id,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    currentOfficeType = any as SpinnerDataModel
                }
            }
        )

        //for Joining Date
        binding.fJobJoiningJoiningDate.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fJobJoiningJoiningDate.tvText.text = "" + it }
                }
            })
        }

        //for Joining Date Of
        binding.fJobJoiningEditDateOf.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fJobJoiningEditDateOf.tvText.text = "" + it }
                }
            })
        }

        //for Joining Release Date from
        binding.fJobJoiningEditReleaseDateFrom.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fJobJoiningEditReleaseDateFrom.tvText.text = "" + it }
                }
            })
        }
        /*  binding.fJobJoiningConfirmationDate.tvText.setOnClickListener({
              DatePicker().showDatePicker(context, object : OnDateListener {
                  override fun onDate(date: String) {
                      date?.let { binding.fJobJoiningConfirmationDate.tvText.setText("" + it) }
                  }
              })
          })*/
      /*  binding.fJobJoiningPensionDate.tvText.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding.fJobJoiningPensionDate.tvText.setText("" + it) }
                }
            })
        })*/
     /*   binding.fJobJoiningPrlDate.tvText.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding.fJobJoiningPrlDate.tvText.setText("" + it) }
                }
            })
        })*/

        binding.jobjoiningUpdateButton.btnUpdate.setOnClickListener {
            val employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)

            val joiningDate = DateConverter.changeDateFormateForSending(binding.fJobJoiningJoiningDate.tvText.text.toString())

            val joiningDateOf = DateConverter.changeDateFormateForSending(binding.fJobJoiningEditDateOf.tvText.text.toString())
            val joiningReleaseDateFrom = DateConverter.changeDateFormateForSending(binding.fJobJoiningEditReleaseDateFrom.tvText.text.toString())


            val map = HashMap<Any, Any?>()
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
            map.put("other_service_particulars", _class?.id)
            map.put("grade_id", grade?.id)
            map.put("pay_scale_id", payScale?.id)

            try {
                if (jobjoining?.isPendingData == true) {
                    var a = jobjoining!!.parent_id
                    map.put("parent_id", a)
                }
            } catch (Ex: java.lang.Exception) {

            }


            map.put("parent_id", jobjoining?.id)


            map.put("pay_scale", payScale?.amount)
            Log.e(
                "current",
                "..................................current  ${if (currentJob?.id == 1) true else false}................................"
            )

            map.put("current", if (currentJob?.id == 1) true else false)
            map.put("joining_date", joiningDate)
            map.put("joining_date_of", joiningDateOf)
            map.put("joining_release_date_from", joiningReleaseDateFrom)
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
                            toast(
                                EmployeeInfoActivity.context,
                                "" + context?.getString(R.string.updated)
                            )
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

                                        when (error) {
                                            "office_id" -> {
                                                binding.tvJobJoiningOfficeError.visibility =
                                                    View.VISIBLE
                                                binding.tvJobJoiningOfficeError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "designation_id" -> {
                                                binding.fJobJoiningEditDesignation.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningEditDesignation.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                          /*  "department_id" -> {
                                                binding.fJobJoiningDepartment.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningDepartment.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }*/
                                         /*   "job_type_id" -> {
                                                binding.fJobJoiningJobType.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningJobType.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }*/
                                            "employee_class_id" -> {
                                                binding.fJobJoiningEditClass.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningEditClass.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }

                                            "other_service_particulars" -> {
                                                binding.fJobJoiningEditOtherServiceParticulars.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningEditOtherServiceParticulars.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }

                                            "grade_id" -> {
                                                binding.fJobJoiningEditGrade.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningEditGrade.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "pay_scale_id" -> {
                                                binding.fJobJoiningEditPayScale.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningEditPayScale.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "pay_scale" -> {
                                                binding.fJobJoiningEditGrade.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningEditGrade.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "joining_date" -> {
                                                binding.fJobJoiningJoiningDate.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fJobJoiningJoiningDate.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }

                                            "joining_date_of" -> {
                                                binding.fJobJoiningEditDateOf.tvError.visibility = View.VISIBLE
                                                binding.fJobJoiningEditDateOf.tvError.text = ErrorUtils2.mainError(message)
                                            }

                                            "joining_release_date_from" -> {
                                                binding.fJobJoiningEditReleaseDateFrom.tvError.visibility = View.VISIBLE
                                                binding.fJobJoiningEditReleaseDateFrom.tvError.text = ErrorUtils2.mainError(message)
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
        }

    }

    fun setPayScale(gradeId: Int?, payScaleAmount: String?) {
        commonRepo.getSpecificSalaryGrade("/api/auth/salary-grade/${gradeId}",
            object : PayScaleValueListener {
                override fun valueChange(spinnerDataModel: SpinnerDataModel?) {
//                    Log.e(
//                        "payscale",
//                        "payscale message " + Gson().toJson(spinnerDataModel?.paysacle)
//                    )
                    Log.e(
                        "payscale",
                        "payscale message ............................................................................................" + jobjoining?.pay_scale_id
                    )
                    spinnerDataModel?.paysacle?.let {
                        SpinnerAdapter().setPayscale(
                            binding.fJobJoiningEditPayScale.spinner,
                            context,
                            it,
                            jobjoining?.pay_scale_id,
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
                binding.JobJoiningOfficeSpinner, context, it,
                jobjoining?.office_id,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        office = any as Office
                        loadDesignation(office?.id, context, binding)
                       // loadAdditionalDesignation(office?.id, context, binding)
                        Log.e("selected item", " item : " + office?.name)
                    }

                }
            )
        }
    }

    //for Designation
    fun loadDesignation(officeId: Int?, context: Context, binding: DialogPersonalInfoBinding) {
        commonRepo.getDesignationData("/api/auth/office/${officeId}",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditDesignation.spinner,
                            context, list,
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

    //For OfficeType
    private fun currentOfficeTypeData(): List<SpinnerDataModel> {

        val headOffice = SpinnerDataModel()
        headOffice.apply {
            id = 1
            name = "Head Office"
            name_bn = "হেড অফিস"

        }
        val divisionalOffice = SpinnerDataModel()
        divisionalOffice.apply {
            id = 4
            name = "Divisional"
            name_bn = "বিভাগীয়"


        }
        return arrayListOf(headOffice, divisionalOffice)
    }

    //For Hiding the Error
    fun invisiableAllError(binding: DialogPersonalInfoBinding) {
        binding.tvJobJoiningOfficeError.visibility = View.GONE

        binding.fJobJoiningEditDesignation.tvError.visibility = View.GONE

        /*binding.fJobJoiningDepartment.tvError.visibility =
            View.GONE*/

        binding.fJobJoiningEditClass.tvError.visibility = View.GONE

        binding.fJobJoiningEditOtherServiceParticulars.tvError.visibility = View.GONE

        binding.fJobJoiningEditGrade.tvError.visibility = View.GONE

        binding.fJobJoiningEditPayScale.tvError.visibility = View.GONE

        binding.fJobJoiningJoiningDate.tvError.visibility = View.GONE

        binding.fJobJoiningEditDateOf.tvError.visibility = View.GONE

        binding.fJobJoiningEditReleaseDateFrom.tvError.visibility = View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
    }
}