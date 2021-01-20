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

class EditOfficialResidentialIInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var designation: SpinnerDataModel? = null
    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var upazila: SpinnerDataModel? = null
    var status: SpinnerDataModel? = null
    var officialResidential: Employee.OfficialResidentials? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null

    lateinit var key: String

    fun showDialog(
        context: Context,
        position: Int?,
        key: String
    ) {
        this.position = position
        officialResidential =
            position?.let { employeeProfileData?.employee?.official_residentials?.get(it) }
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
        updateOfficialResidentialInfo(context)
        dialogCustome?.show()

    }

    fun updateOfficialResidentialInfo(
        context: Context
    ) {

        binding?.llOfficialResidentialInfo?.visibility = View.VISIBLE
        binding?.hOfficialResidentialInfo?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.residentialBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.residentialBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fORInfoMemoNo?.etText?.setText(officialResidential?.memo_no)
        binding?.fORInfoMemoNoBn?.etText?.setText(officialResidential?.memo_no_bn)
        binding?.fORInfoOfficeZone?.etText?.setText(officialResidential?.office_zone)
        binding?.fORInfoAddress?.etText?.setText(officialResidential?.location)
        binding?.fORInfoQuarterName?.etText?.setText(officialResidential?.quarter_name)
        binding?.fORInfoFlatNo?.etText?.setText(officialResidential?.flat_no_flat_type)
        binding?.fORInfoMemoDate?.tvText?.setText(officialResidential?.memo_date)



        binding?.fORInfoMemoDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fORInfoMemoDate?.tvText?.setText("" + it) }
                }
            })
        })

        commonRepo.getCommonData("/api/auth/designation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fORInfoDesignation?.spinner!!,
                            context,
                            list,
                            officialResidential?.designation_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    designation = any as SpinnerDataModel

                                }

                            }
                        )
                    }
                }
            })


        commonRepo.getCommonData("/api/auth/division/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fORInfoDivision?.spinner!!,
                            context,
                            list,
                            officialResidential?.division_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    division = any as SpinnerDataModel
                                    getDistrict(division?.id, officialResidential?.district_id)
                                }

                            }
                        )
                    }
                }
            })


        getStatusList()?.let {
            SpinnerAdapter().setSpinner(
                binding?.fORInfoStatus?.spinner!!,
                context,
                it,
                officialResidential?.status,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        status = any as SpinnerDataModel
                    }

                }
            )
        }


        binding?.residentialBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateOfficialResidentialInfo(
                        officialResidential?.id,
                        getMapData()
                    )
                        ?.observe(EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)

                            })
                } else {
                    employeeInfoEditCreateRepo?.addOfficialResidentialInfo(getMapData())
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

            MainActivity.selectedPosition = 11
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
                                binding?.fAddressVillageOrHouseNoBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAddressVillageOrHouseNoBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "division_id" -> {
                                binding?.fORInfoDivision?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoDivision?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "district_id" -> {
                                binding?.fORInfoDistrict?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoDistrict?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "upazila_id" -> {
                                binding?.fORInfoUpazila?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoUpazila?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "designation_id" -> {
                                binding?.fORInfoDesignation?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoDesignation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "memo_no" -> {
                                binding?.fORInfoMemoNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoMemoNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "memo_no_bn" -> {
                                binding?.fORInfoMemoNoBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoMemoNoBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "memo_date" -> {
                                binding?.fORInfoMemoDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoMemoDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "office_zone" -> {
                                binding?.fORInfoOfficeZone?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoOfficeZone?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "location" -> {
                                binding?.fORInfoAddress?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoAddress?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "quarter_name" -> {
                                binding?.fORInfoQuarterName?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoQuarterName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "flat_no_flat_type" -> {
                                binding?.fORInfoFlatNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoFlatNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "status" -> {
                                binding?.fORInfoStatus?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fORInfoStatus?.tvError?.text =
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
        Log.e("designation ", "id : " + designation?.id)
        var map = HashMap<Any, Any?>()
        map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
        map.put("designation_id", designation?.id)
        map.put("division_id", division?.id)
        map.put("district_id", district?.id)
        map.put("upazila_id", upazila?.id)
        map.put("memo_no", binding?.fORInfoMemoNo?.etText?.text.toString())
        map.put("memo_no_bn", binding?.fORInfoMemoNoBn?.etText?.text.toString())
        map.put("memo_date", binding?.fORInfoMemoDate?.tvText?.text.toString())
        map.put("office_zone", binding?.fORInfoOfficeZone?.etText?.text.toString())
        map.put("location", binding?.fORInfoAddress?.etText?.text.toString())
        map.put("quarter_name", binding?.fORInfoQuarterName?.etText?.text.toString())
        map.put("flat_no_flat_type", binding?.fORInfoFlatNo?.etText?.text.toString())
        map.put("status", status?.id)
        return map
    }


    fun getDistrict(divisionId: Int?, districtId: Int?) {
        commonRepo.getDistrict(divisionId,
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fORInfoDistrict?.spinner!!,
                            context,
                            list,
                            districtId,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    district = any as SpinnerDataModel
                                    getUpazila(district?.id, officialResidential?.upazila_id)
                                }

                            }
                        )
                    }
                }
            })
    }


    fun getUpazila(districtId: Int?, upazilaId: Int?) {
        commonRepo.getUpazila(districtId,
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fORInfoUpazila?.spinner!!,
                            context,
                            list,
                            upazilaId,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    upazila = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding??) {
        binding?.fORInfoMemoNo?.tvError?.visibility =
            View.GONE

        binding?.fORInfoMemoNoBn?.tvError?.visibility =
            View.GONE

        binding?.fORInfoMemoDate?.tvError?.visibility =
            View.GONE

        binding?.fORInfoOfficeZone?.tvError?.visibility =
            View.GONE
        binding?.fORInfoDivision?.tvError?.visibility =
            View.GONE
        binding?.fORInfoDistrict?.tvError?.visibility =
            View.GONE
        binding?.fORInfoUpazila?.tvError?.visibility =
            View.GONE
        binding?.fORInfoDesignation?.tvError?.visibility =
            View.GONE
        binding?.fORInfoAddress?.tvError?.visibility =
            View.GONE
        binding?.fORInfoQuarterName?.tvError?.visibility =
            View.GONE
        binding?.fORInfoFlatNo?.tvError?.visibility =
            View.GONE
        binding?.fORInfoStatus?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

    fun getStatusList(): List<SpinnerDataModel> {
        var rel = SpinnerDataModel()
        rel.apply {
            name = "Active"
            name_bn = "সক্রিয়"
            id = 1
        }
        var rel1 = SpinnerDataModel()
        rel1.apply {
            name = "Disable"
            name_bn = "অক্ষম"
            id = 0
        }
        return arrayListOf(rel, rel1)
    }

}