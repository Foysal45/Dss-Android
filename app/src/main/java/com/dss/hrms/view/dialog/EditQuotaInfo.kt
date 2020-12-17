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
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.CommonDataValueListener
import com.dss.hrms.view.`interface`.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*

class EditQuotaInfo {
    var dialogCustome: Dialog? = null

    fun showDialog(context: Context, quotaInfo: Employee.EmployeeQuotas) {

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
        updateJobjoiningInfo(context, dialogCustomeBinding, quotaInfo)
        dialogCustome?.show()

    }

    fun updateJobjoiningInfo(
        context: Context,
        binding: DialogPersonalInfoBinding,
        employeeQuotas: Employee.EmployeeQuotas?
    ) {
        binding.llQuotaInfo.visibility = View.VISIBLE
        binding.hQuota.tvClose.setOnClickListener({
            dialogCustome?.dismiss()
        })

        var quotaName: SpinnerDataModel? = null
        var quotaType: SpinnerDataModel? = null

        binding.fQuotaDescription.etText.setText(employeeQuotas?.description)
        binding.fQuotaDescriptionBn.etText.setText(employeeQuotas?.description_bn)


        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/quota-information/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fQuotaName.spinner,
                            context,
                            list,
                            employeeQuotas?.quota_information_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    quotaName = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })


        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/quota-information-details/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fQuotaType.spinner,
                            context,
                            list,
                            employeeQuotas?.quota_information_detail_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    quotaType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })


        binding.quotaBtnUpdate.btnUpdate.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!)
                    .get(EmployeeInfoEditCreateViewModel::class.java)

            var map = HashMap<Any, Any?>()
            map.put("employee_id", MainActivity?.employee?.user?.employee_id)
            map.put("quota_information_id", quotaName?.id)
            map.put("quota_information_detail_id", quotaType?.id)
            map.put("description", binding.fQuotaDescription.etText.text.toString())
            map.put("description_bn", binding.fQuotaDescriptionBn.etText.text.toString())
            map.put("status", employeeQuotas?.status)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            employeeInfoEditCreateRepo?.updateQuotaInfo(employeeQuotas?.id, map)
                ?.observe(EmployeeInfoActivity.context!!,
                    Observer { any ->
                        dialog?.dismiss()
                        Log.e("yousuf", "error : " + Gson().toJson(any))

                        if (any is String) {
                            toast(EmployeeInfoActivity.context, "Updated")

                            MainActivity.selectedPosition = 8
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
                                                binding.fQuotaDescriptionBn.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaDescriptionBn.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }

                                        }
                                        when (error) {
                                            "quota_information_id" -> {
                                                binding.fQuotaName.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaName.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "quota_information_detail_id" -> {
                                                binding.fQuotaType.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaType.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "description" -> {
                                                binding.fQuotaDescription.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaDescription.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "description_bn" -> {
                                                binding.fQuotaDescriptionBn.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaDescriptionBn.tvError.text =
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
        binding.fQuotaName.tvError.visibility =
            View.GONE

        binding.fQuotaType.tvError.visibility =
            View.GONE

        binding.fQuotaDescription.tvError.visibility =
            View.GONE

        binding.fQuotaDescriptionBn.tvError.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}