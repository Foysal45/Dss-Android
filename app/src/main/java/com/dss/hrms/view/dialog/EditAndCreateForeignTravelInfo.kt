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
import com.dss.hrms.util.DatePicker
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject

class EditAndCreateForeignTravelInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var foreignTravels1: Employee.ForeignTravels? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var country: SpinnerDataModel? = null


    fun showDialog(
        context: Context,
        position: Int?,
        key: String
    ) {
        this.foreignTravels1 =
            position?.let { employeeProfileData?.employee?.foreign_travels?.get(it) }
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
        updateForeignTravel(context)
        dialogCustome?.show()

    }

    fun updateForeignTravel(
        context: Context
    ) {

        binding?.llForeingTravelInfo?.visibility = View.VISIBLE
        binding?.hForeignTravelInfo?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.foreignTravelBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.foreignTravelBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }
        binding?.fForeignTravelPurpose?.etText?.setText(foreignTravels1?.purpose)
        binding?.fForeignTravelPurposeBn?.etText?.setText(foreignTravels1?.purpose_bn)
        binding?.fForeignTravelDetailsEn?.etText?.setText(foreignTravels1?.details)
        binding?.fForeignTravelDetailsBn?.etText?.setText(foreignTravels1?.details_bn)
        binding?.fForeignTravelToDate?.tvText?.setText(foreignTravels1?.to_date)
        binding?.fForeignTravelFromDate?.tvText?.setText(foreignTravels1?.from_date)

        binding?.fForeignTravelFromDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fForeignTravelFromDate?.tvText?.setText("" + it) }
                }
            })
        })
        binding?.fForeignTravelToDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fForeignTravelToDate?.tvText?.setText("" + it) }
                }
            })
        })
        commonRepo.getCommonData("/api/auth/country/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fForeignTravelCountry?.spinner!!,
                            context,
                            list,
                            foreignTravels1?.country_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    country = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })



        binding?.foreignTravelBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateForeignTravelInfo(
                        foreignTravels1?.id,
                        getMapData()
                    )
                        ?.observe(EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                } else {
                    employeeInfoEditCreateRepo?.addForeignTravelInfo(getMapData())
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
            MainActivity.selectedPosition = 12
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
                                binding?.fForeignTravelToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelToDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "country_id" -> {
                                binding?.fForeignTravelCountry?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelCountry?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "purpose" -> {
                                binding?.fForeignTravelPurpose?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelPurpose?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "purpose_bn" -> {
                                binding?.fForeignTravelPurposeBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelPurposeBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "details" -> {
                                binding?.fForeignTravelDetailsEn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelDetailsEn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "details_bn" -> {
                                binding?.fForeignTravelDetailsBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelDetailsBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "from_date" -> {
                                binding?.fForeignTravelFromDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelFromDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "to_date" -> {
                                binding?.fForeignTravelToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelToDate?.tvError?.text =
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
        map.put("country_id", country?.id)
        map.put("purpose", binding?.fForeignTravelPurpose?.etText?.text.toString())
        map.put("purpose_bn", binding?.fForeignTravelPurposeBn?.etText?.text.toString())
        map.put("details", binding?.fForeignTravelDetailsEn?.etText?.text.toString())
        map.put("details_bn", binding?.fForeignTravelDetailsBn?.etText?.text.toString())
        map.put("from_date", binding?.fForeignTravelFromDate?.tvText?.text.toString())
        map.put("to_date", binding?.fForeignTravelToDate?.tvText?.text.toString())
        map.put("status", foreignTravels1?.status)
        return map
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fForeignTravelCountry?.tvError?.visibility =
            View.GONE

        binding?.fForeignTravelPurpose?.tvError?.visibility =
            View.GONE

        binding?.fForeignTravelPurposeBn?.tvError?.visibility =
            View.GONE
        binding?.fForeignTravelDetailsEn?.tvError?.visibility =
            View.GONE
        binding?.fForeignTravelDetailsBn?.tvError?.visibility =
            View.GONE

        binding?.fForeignTravelFromDate?.tvError?.visibility =
            View.GONE

        binding?.fForeignTravelToDate?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}