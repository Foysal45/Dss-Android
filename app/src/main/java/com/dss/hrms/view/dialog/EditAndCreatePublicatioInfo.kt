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
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject

class EditAndCreatePublicatioInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    var dialogCustome: Dialog? = null
    var publications: Employee.Publications? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var publicationType: SpinnerDataModel? = null

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0

    fun showDialog(
        context: Context,
        position: Int?,
        key: String
    ) {
        this.position = position
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
        updatePublicationInfo(context)
        dialogCustome?.show()

    }

    fun updatePublicationInfo(
        context: Context
    ) {

        binding?.llPublication?.visibility = View.VISIBLE
        binding?.hPublication?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.publicationBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.publicationBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fPublicationNameEn?.etText?.setText(publications?.publication_name)
        binding?.fPublicationNameBn?.etText?.setText(publications?.publication_name_bn)
        binding?.fPublicationDetails?.etText?.setText(publications?.publication_details)
        binding?.fPublicationDetailsBn?.etText?.setText(publications?.publication_details_bn)



        commonRepo.getCommonData("/api/auth/publication-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fPublicationType?.spinner!!,
                            context,
                            list,
                            publications?.publication_type,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    publicationType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        binding?.publicationBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updatePublicationInfo(
                        publications?.id,
                        getMapData()
                    )
                        ?.observe(EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                } else {
                    employeeInfoEditCreateRepo?.addPublicationInfo(getMapData())
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
            MainActivity.selectedPosition = 14
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
                                binding?.fPublicationDetailsBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPublicationDetailsBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "publication_type" -> {
                                binding?.fPublicationType?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPublicationType?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "publication_name" -> {
                                binding?.fPublicationNameEn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPublicationNameEn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "publication_name_bn" -> {
                                binding?.fPublicationNameBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPublicationNameBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "publication_details" -> {
                                binding?.fPublicationDetails?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPublicationDetails?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "publication_details_bn" -> {
                                binding?.fPublicationDetailsBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPublicationDetailsBn?.tvError?.text =
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
        map.put("publication_type", publicationType?.id)
        map.put("publication_name", binding?.fPublicationNameEn?.etText?.text.toString())
        map.put("publication_name_bn", binding?.fPublicationNameBn?.etText?.text.toString())
        map.put("publication_details", binding?.fPublicationDetails?.etText?.text.toString())
        map.put("publication_details_bn", binding?.fPublicationDetailsBn?.etText?.text.toString())
        map.put("status", publications?.status)
        return map
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fPublicationType?.tvError?.visibility =
            View.GONE

        binding?.fPublicationNameEn?.tvError?.visibility =
            View.GONE

        binding?.fPublicationNameBn?.tvError?.visibility =
            View.GONE

        binding?.fPublicationDetails?.tvError?.visibility =
            View.GONE

        binding?.fPublicationDetailsBn?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

}