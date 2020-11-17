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
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.CommonDataValueListener
import com.dss.hrms.view.`interface`.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*

class EditEducationQualificationInfo {
    var dialogCustome: Dialog? = null
    var qualifications: Employee.EducationalQualifications? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var degreeName: SpinnerDataModel? = null
    var board: SpinnerDataModel? = null
    var instituteName: SpinnerDataModel? = null
    var instituteId: String? = null
    var boardId: String? = null

    fun showDialog(
        context: Context,
        qualifications: Employee.EducationalQualifications,
        key: String
    ) {
        this.qualifications = qualifications
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
        updateEducationQualification(context, qualifications)
        dialogCustome?.show()

    }

    fun updateEducationQualification(
        context: Context,
        educationalQualifications: Employee.EducationalQualifications?
    ) {

        binding?.llEducationQualificaion?.visibility = View.VISIBLE
        binding?.hEducationQualification?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.educationBtnUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.educationBtnUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }
        binding?.fEQBoardOrUniversity?.llBody?.visibility =
            View.GONE
        binding?.fEQInstitute?.llBody?.visibility =
            View.GONE
        binding?.fEQPassingYear?.etText?.setText(educationalQualifications?.passing_year)
        binding?.fEQDivisionOrCgpa?.etText?.setText(educationalQualifications?.division_cgpa)


        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/examination/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fEQNameOfD?.spinner!!,
                            context,
                            list,
                            educationalQualifications?.examination_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    degreeName = any as SpinnerDataModel
                                    if (degreeName?.name?.toLowerCase()
                                            .equals("ssc") || degreeName?.name?.toLowerCase()
                                            .equals("hsc")
                                    ) {
                                        binding?.fEQBoardOrUniversity?.llBody?.visibility =
                                            View.VISIBLE
                                        binding?.fEQInstitute?.llBody?.visibility =
                                            View.GONE
                                    } else {
                                        binding?.fEQBoardOrUniversity?.llBody?.visibility =
                                            View.GONE
                                        binding?.fEQInstitute?.llBody?.visibility =
                                            View.VISIBLE
                                    }
                                }

                            }
                        )
                    }
                }
            })


        CommonRepo(MainActivity.appContext).getCommonData2("/api/auth/board/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fEQBoardOrUniversity?.spinner!!,
                            context,
                            list,
                            educationalQualifications?.board_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    board = any as SpinnerDataModel
                                    instituteName = null
                                    boardId = board?.id.toString()
                                    instituteId = ""

                                }

                            }
                        )
                    }
                }
            })
        CommonRepo(MainActivity.appContext).getCommonData2("/api/auth/education-institute/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fEQInstitute?.spinner!!,
                            context,
                            list,
                            educationalQualifications?.educational_institute_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    instituteName = any as SpinnerDataModel
                                    board = null
                                    instituteId = instituteName?.id.toString()
                                    boardId = ""
                                }

                            }
                        )
                    }
                }
            })


        binding?.educationBtnUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo = ViewModelProviders.of(MainActivity.context!!)
                .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateEducationQualificationInfo(
                        educationalQualifications?.id,
                        getMapData()
                    )
                        ?.observe(EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                } else {
                    employeeInfoEditCreateRepo?.addEducationQualificationInfo(getMapData())
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
            MainActivity.selectedPosition = 5
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
                                binding?.fEQDivisionOrCgpa?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fEQDivisionOrCgpa?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "examination_id" -> {
                                binding?.fEQNameOfD?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fEQNameOfD?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "educational_institute_id" -> {
                                binding?.fEQInstitute?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fEQInstitute?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "board_id" -> {
                                binding?.fEQBoardOrUniversity?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fEQBoardOrUniversity?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "passing_year" -> {
                                binding?.fEQPassingYear?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fEQPassingYear?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "division_cgpa" -> {
                                binding?.fEQDivisionOrCgpa?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fEQDivisionOrCgpa?.tvError?.text =
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
        map.put("examination_id", degreeName?.id)
        map.put("educational_institute_id", instituteId)
        map.put("board_id", boardId)
        map.put("passing_year", binding?.fEQPassingYear?.etText?.text.toString())
        map.put("division_cgpa", binding?.fEQDivisionOrCgpa?.etText?.text.toString())
        map.put("status", qualifications?.status)
        return map
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fEQNameOfD?.tvError?.visibility =
            View.GONE

        binding?.fEQBoardOrUniversity?.tvError?.visibility =
            View.GONE

        binding?.fEQInstitute?.tvError?.visibility =
            View.GONE

        binding?.fEQPassingYear?.tvError?.visibility =
            View.GONE

        binding?.fEQDivisionOrCgpa?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}