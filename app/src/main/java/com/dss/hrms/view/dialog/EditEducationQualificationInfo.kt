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
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject

class EditEducationQualificationInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0
    var dialogCustome: Dialog? = null
    var educationalQualification: Employee.EducationalQualifications? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var degreeName: SpinnerDataModel? = null
    var board: SpinnerDataModel? = null
    var instituteName: SpinnerDataModel? = null
    var instituteId: String? = null
    var boardId: String? = null


    fun showDialog(context: Context, position: Int?, key: String) {
        this.position = position
        this.educationalQualification =
            position?.let { employeeProfileData?.employee?.educationalQualifications?.get(it) }
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
        updateEducationQualification(context)
        dialogCustome?.show()

    }

    fun updateEducationQualification(
        context: Context
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
        binding?.fEQPassingYear?.etText?.setText(educationalQualification?.passing_year)
        binding?.fEQDivisionOrCgpa?.etText?.setText(educationalQualification?.division_cgpa)


        commonRepo.getCommonData("/api/auth/examination/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fEQNameOfD?.spinner!!,
                            context,
                            list,
                            educationalQualification?.examination_id,
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


        commonRepo.getCommonData("/api/auth/board/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fEQBoardOrUniversity?.spinner!!,
                            context,
                            list,
                            educationalQualification?.board_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    board = any as SpinnerDataModel
                                    instituteName = null
                                    boardId = board?.id?.toString()
                                    instituteId = null

                                }

                            }
                        )
                    }
                }
            })
        commonRepo.getCommonData("/api/auth/education-institute/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fEQInstitute?.spinner!!,
                            context,
                            list,
                            educationalQualification?.educational_institute_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    instituteName = any as SpinnerDataModel
                                    board = null
                                    instituteId = instituteName?.id?.toString()
                                    boardId = null
                                }

                            }
                        )
                    }
                }
            })


        binding?.educationBtnUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateEducationQualificationInfo(
                        educationalQualification?.id,
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
        map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
        map.put("examination_id", degreeName?.id)
        if (instituteId != null) map.put(
            "educational_institute_id",
            instituteId
        ) else map.put("educational_institute_id", "")
        if (boardId != null) map.put("board_id", boardId) else map.put("board_id", "")
        map.put("passing_year", binding?.fEQPassingYear?.etText?.text.toString())
        map.put("division_cgpa", binding?.fEQDivisionOrCgpa?.etText?.text.toString())
        map.put("status", educationalQualification?.status)
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