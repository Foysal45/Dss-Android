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

class EditReferenceInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0
    var dialogCustome: Dialog? = null
    var references: Employee.References? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null

    lateinit var key: String

    fun showDialog(
        context: Context,
        position: Int?,
        key: String
    ) {
        this.position = position
        this.references = position?.let { employeeProfileData?.employee?.references?.get(it) }
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
        updateReferenceInfo(context)
        dialogCustome?.show()

    }


    fun updateReferenceInfo(
        context: Context
    ) {
        binding?.llReference?.visibility = View.VISIBLE
        binding?.hReference?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })
        binding?.fReferenceNameEn?.etText?.setText(references?.name)
        binding?.fReferenceNameBn?.etText?.setText(references?.name_bn)
        binding?.fReferenceRelation?.etText?.setText(references?.relation)
        binding?.fReferenceRelationBn?.etText?.setText(references?.relation_bn)
        binding?.fReferenceContactNo?.etText?.setText(references?.contact_no)
        binding?.fReferenceContactNoBn?.etText?.setText(references?.contact_no_bn)
        binding?.fReferenceAddress?.etText?.setText(references?.address)
        binding?.fReferenceAddressBn?.etText?.setText(references?.address_bn)

        binding?.referenceBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateReferenceInfo(
                        references?.id,
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
                    employeeInfoEditCreateRepo?.addReferenceInfo(getMapData())
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
            toast(EmployeeInfoActivity.context, "" + context?.getString(R.string.updated))
            MainActivity.selectedPosition = 17
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
                                binding?.fReferenceAddressBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceAddressBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "name" -> {
                                binding?.fReferenceNameEn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceNameEn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_bn" -> {
                                binding?.fReferenceNameBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceNameBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "relation" -> {
                                binding?.fReferenceRelation?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceRelation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "relation_bn" -> {
                                binding?.fReferenceRelationBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceRelationBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "contact_no" -> {
                                binding?.fReferenceContactNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceContactNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "contact_no_bn" -> {
                                binding?.fReferenceContactNoBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceContactNoBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "address" -> {
                                binding?.fReferenceAddress?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceAddress?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "address_bn" -> {
                                binding?.fReferenceAddressBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReferenceAddressBn?.tvError?.text =
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
        map.put("name", binding?.fReferenceNameEn?.etText?.text.toString())
        map.put("name_bn", binding?.fReferenceNameBn?.etText?.text.toString())
        map.put("relation", binding?.fReferenceRelation?.etText?.text.toString())
        map.put("relation_bn", binding?.fReferenceRelationBn?.etText?.text.toString())
        map.put("contact_no", binding?.fReferenceContactNo?.etText?.text.toString())
        map.put("contact_no_bn", binding?.fReferenceContactNoBn?.etText?.text.toString())
        map.put("address", binding?.fReferenceAddress?.etText?.text.toString())
        map.put("address_bn", binding?.fReferenceAddressBn?.etText?.text.toString())
        map.put("status", references?.status)
        return map
    }

    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fReferenceNameEn?.tvError?.visibility =
            View.GONE

        binding?.fReferenceNameBn?.tvError?.visibility =
            View.GONE

        binding?.fReferenceRelation?.tvError?.visibility =
            View.GONE

        binding?.fReferenceRelationBn?.tvError?.visibility =
            View.GONE

        binding?.fReferenceContactNo?.tvError?.visibility =
            View.GONE

        binding?.fReferenceContactNoBn?.tvError?.visibility =
            View.GONE

        binding?.fReferenceAddress?.tvError?.visibility =
            View.GONE
        binding?.fReferenceAddressBn?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

}