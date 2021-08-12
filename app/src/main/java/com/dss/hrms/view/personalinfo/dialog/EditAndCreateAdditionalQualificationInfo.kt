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
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject

class EditAndCreateAdditionalQualificationInfo @Inject constructor() {

    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    @Inject
    lateinit var employeeProfileData: EmployeeProfileData


    var dialogCustome: Dialog? = null


    var additionalQualifications: Employee.AdditionalQualifications? = null


    var binding: DialogPersonalInfoBinding? = null

    var context: Context? = null
    lateinit var key: String
    var position: Int? = 0


    fun showDialog(
        context: Context,
        position: Int?,
        key: String
    ) {
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


        Log.e("editcreate", "edit create " + employeeProfileData.employee?.profile_id)

        additionalQualifications = position?.let {
            employeeProfileData?.employee?.additional_qualifications?.get(
                it
            )
        }
        updateAdditionalQualification(context)
        dialogCustome?.show()

    }

    fun updateAdditionalQualification(
        context: Context
    ) {

        binding?.llAdditionalQualification?.visibility = View.VISIBLE
        binding?.hAdditionalQualification?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.additionalPQBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.additionalPQBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fAQName?.etText?.setText(additionalQualifications?.qualification_name)
        binding?.fAQNameBn?.etText?.setText(additionalQualifications?.qualification_name_bn)
        binding?.fAQDetails?.etText?.setText(additionalQualifications?.qualification_details)
        binding?.fAQDetailsBn?.etText?.setText(additionalQualifications?.qualification_details_bn)


        binding?.additionalPQBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {

                    employeeInfoEditCreateRepo?.updateAdditionalQualificationInfo(
                        additionalQualifications?.id,
                        getMapData()
                    )
                        ?.observe(
                            EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                } else {
                    employeeInfoEditCreateRepo?.addAdditionalQualificationInfo(getMapData())
                        ?.observe(
                            EmployeeInfoActivity.context!!,
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
            MainActivity.selectedPosition = 13
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
                                binding?.fAQDetailsBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAQDetailsBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "qualification_name" -> {
                                binding?.fAQName?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAQName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "qualification_name_bn" -> {
                                binding?.fAQNameBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAQNameBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "qualification_details" -> {
                                binding?.fAQDetails?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAQDetails?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "qualification_details_bn" -> {
                                binding?.fAQDetailsBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAQDetailsBn?.tvError?.text =
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
        map.put("qualification_name", binding?.fAQName?.etText?.text.toString())
        map.put("qualification_name_bn", binding?.fAQNameBn?.etText?.text.toString())
        map.put("qualification_details", binding?.fAQDetails?.etText?.text.toString())
        map.put("qualification_details_bn", binding?.fAQDetailsBn?.etText?.text.toString())
        if (additionalQualifications?.status != null) map.put(
            "status",
            additionalQualifications?.status
        ) else map.put("status", 1)
        return map
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fAQName?.tvError?.visibility =
            View.GONE

        binding?.fAQNameBn?.tvError?.visibility =
            View.GONE

        binding?.fAQDetails?.tvError?.visibility =
            View.GONE

        binding?.fAQDetailsBn?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}