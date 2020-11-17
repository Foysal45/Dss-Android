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

class EditAndCreateSpouseInfo {
    var dialogCustome: Dialog? = null
    var spouses: Employee.Spouses? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var occupation: SpinnerDataModel? = null
    var workStation: SpinnerDataModel? = null
    var jobType: SpinnerDataModel? = null
    var relation: SpinnerDataModel? = null
    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null

    fun showDialog(
        context: Context,
        spouses1: Employee.Spouses,
        key: String
    ) {
        this.spouses = spouses1
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
        updateEducationQualification(context, spouses1)
        dialogCustome?.show()

    }

    fun updateEducationQualification(
        context: Context,
        spouses1: Employee.Spouses?
    ) {

        binding?.llSpouse?.visibility = View.VISIBLE
        binding?.hSpouse?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.spouseBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.spouseBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fSpouseNameEn?.etText?.setText(spouses1?.name)
        binding?.fSpouseNameBn?.etText?.setText(spouses1?.name_bn)
        binding?.fSpousePhoneNo?.etText?.setText(spouses1?.phone_no)
        binding?.fSpouseMobileNo?.etText?.setText(spouses1?.mobile_no)


        CommonRepo(MainActivity.appContext).getCommonData2("/api/auth/spouse-occupation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fSpouseOccupation?.spinner!!,
                            context,
                            list,
                            spouses1?.occupation_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    occupation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })


        CommonRepo(MainActivity.appContext).getCommonData2("/api/auth/spouse-workstation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fSpouseOffice?.spinner!!,
                            context,
                            list,
                            spouses1?.spouse_workstation_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    workStation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/job-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fSpouseJobType?.spinner!!,
                            context,
                            list,
                            spouses1?.spouse_job_type_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    jobType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })


        getRelationList()?.let {
            SpinnerAdapter().setSpinner(
                binding?.fSpouseRelation?.spinner!!,
                context,
                getRelationList(),
                spouses1?.spouse_job_type_id,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        relation = any as SpinnerDataModel
                    }

                }
            )
        }
        CommonRepo(MainActivity.appContext).getCommonData("/api/auth/division/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fSpouseDivision?.spinner!!,
                            context,
                            list,
                            spouses1?.division_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    division = any as SpinnerDataModel
                                    getDistrict(division?.id, spouses1?.district_id)
                                }

                            }
                        )
                    }
                }
            })

        binding?.spouseBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo = ViewModelProviders.of(MainActivity.context!!)
                .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateSpouseInfo(
                        spouses1?.id,
                        getMapData()
                    )
                        ?.observe(EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                } else {
                    employeeInfoEditCreateRepo?.addSpouseInfo(getMapData())
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
            MainActivity.selectedPosition = 1
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
                                binding?.fSpouseMobileNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseMobileNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "name" -> {
                                binding?.fSpouseNameEn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseNameEn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_bn" -> {
                                binding?.fSpouseNameBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseNameBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "relation" -> {
                                binding?.fSpouseRelation?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseRelation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "division_id" -> {
                                binding?.fSpouseDivision?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseDivision?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "occupation_id" -> {
                                binding?.fSpouseOccupation?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseOccupation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "spouse_workstation_id" -> {
                                binding?.fSpouseOffice?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseOffice?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "spouse_job_type_id" -> {
                                binding?.fSpouseJobType?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseJobType?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "district_id" -> {
                                binding?.fSpouseDistrict?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseDistrict?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "phone_no" -> {
                                binding?.fSpousePhoneNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpousePhoneNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "mobile_no" -> {
                                binding?.fSpouseMobileNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fSpouseMobileNo?.tvError?.text =
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
        map.put("name", binding?.fSpouseNameEn?.etText?.text.toString())
        map.put("name_bn", binding?.fSpouseNameBn?.etText?.text.toString())
        map.put("relation", relation?.name)
        map.put("division_id", division?.id)
        map.put("occupation_id", occupation?.id)
        map.put("spouse_workstation_id", workStation?.id)
        map.put("spouse_job_type_id", jobType?.id)
        map.put("district_id", district?.id)
        map.put("phone_no", binding?.fSpousePhoneNo?.etText?.text.toString())
        map.put("mobile_no", binding?.fSpouseMobileNo?.etText?.text.toString())
        map.put("status", spouses?.status)
        return map
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fSpouseNameEn?.tvError?.visibility =
            View.GONE

        binding?.fSpouseNameBn?.tvError?.visibility =
            View.GONE

        binding?.fSpouseRelation?.tvError?.visibility =
            View.GONE

        binding?.fSpouseDivision?.tvError?.visibility =
            View.GONE

        binding?.fSpouseOccupation?.tvError?.visibility =
            View.GONE

        binding?.fSpouseOffice?.tvError?.visibility =
            View.GONE

        binding?.fSpouseJobType?.tvError?.visibility =
            View.GONE
        binding?.fSpouseDistrict?.tvError?.visibility =
            View.GONE

        binding?.fSpousePhoneNo?.tvError?.visibility =
            View.GONE
        binding?.fSpouseMobileNo?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }


    fun getDistrict(divisionId: Int?, districtId: Int?) {
        CommonRepo(MainActivity.appContext).getDistrict(divisionId,
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fSpouseDistrict?.spinner!!,
                            context,
                            list,
                            districtId,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    district = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
    }


    fun getRelationList(): List<SpinnerDataModel> {

        var rel = SpinnerDataModel()
        rel.apply {
            name = "Wife"
            name_bn = "স্ত্রী"

        }
        var rel1 = SpinnerDataModel()
        rel1.apply {
            name = "Husband"
            name_bn = "স্বামী"


        }
        return arrayListOf(rel, rel1)
    }
}