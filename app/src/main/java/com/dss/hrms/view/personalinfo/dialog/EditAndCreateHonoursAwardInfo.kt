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
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DateConverter
import com.dss.hrms.util.DatePicker
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject

class EditAndCreateHonoursAwardInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var honoursAward: Employee.HonoursAwards? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String


    fun showDialog(
        context: Context,
        position: Int?,
        key: String
    ) {
        this.position = position
        this.honoursAward = position?.let { employeeProfileData?.employee?.honours_awards?.get(it) }
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
        updateHonoursAward(context)
        dialogCustome?.show()

    }

    fun updateHonoursAward(
        context: Context
    ) {

        binding?.llHonoursAndAward?.visibility = View.VISIBLE
        binding?.hHonoursAndAward?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.honoursAwardBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.honoursAwardBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fAwardTitle?.etText?.setText(honoursAward?.award_title)
        binding?.fAwardTitleBn?.etText?.setText(honoursAward?.award_title_bn)
        binding?.fAwardDetailsDetails?.etText?.setText(honoursAward?.award_details)
        binding?.fAwardDetailsDetailsBn?.etText?.setText(honoursAward?.award_details_bn)
        binding?.fAwardDate?.tvText?.setText(honoursAward?.award_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })

        binding?.fAwardDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fAwardDate?.tvText?.setText("" + it) }
                }
            })
        })


        binding?.honoursAwardBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateHonoursAwardInfo(
                        honoursAward?.id,
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
                    employeeInfoEditCreateRepo?.addHonoursAwardInfo(getMapData())
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
            MainActivity.selectedPosition = 15
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
                                binding?.fAwardDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "award_title" -> {
                                binding?.fAwardTitle?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardTitle?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "award_title_bn" -> {
                                binding?.fAwardTitleBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardTitleBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "award_details" -> {
                                binding?.fAwardDetailsDetails?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDetailsDetails?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "award_details_bn" -> {
                                binding?.fAwardDetailsDetailsBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDetailsDetailsBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "award_date" -> {
                                binding?.fAwardDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDate?.tvError?.text =
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
        var award_date =
            DateConverter.changeDateFormateForSending(binding?.fAwardDate?.tvText?.text.toString())
        var map = HashMap<Any, Any?>()
        map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
        map.put("award_title", binding?.fAwardTitle?.etText?.text.toString())
        map.put("award_title_bn", binding?.fAwardTitleBn?.etText?.text.toString())
        map.put("award_details", binding?.fAwardDetailsDetails?.etText?.text.toString())
        map.put("award_details_bn", binding?.fAwardDetailsDetailsBn?.etText?.text.toString())
        map.put("award_date", award_date)
        map.put("status", honoursAward?.status)
        return map
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fAwardTitle?.tvError?.visibility =
            View.GONE

        binding?.fAwardTitleBn?.tvError?.visibility =
            View.GONE

        binding?.fAwardDetailsDetails?.tvError?.visibility =
            View.GONE

        binding?.fAwardDetailsDetailsBn?.tvError?.visibility =
            View.GONE

        binding?.fAwardDate?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}