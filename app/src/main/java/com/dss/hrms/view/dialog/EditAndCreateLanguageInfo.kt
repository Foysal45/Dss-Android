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
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DatePicker
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.`interface`.OnDateListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.personal_info_update_button.view.*

class EditAndCreateLanguageInfo {
    var dialogCustome: Dialog? = null
    var languages: Employee.Languages? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var expertLevel: SpinnerDataModel? = null


    fun showDialog(
        context: Context,
        languages1: Employee.Languages,
        key: String
    ) {
        this.languages = languages1
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
        updateEducationQualification(context, languages1)
        dialogCustome?.show()

    }

    fun updateEducationQualification(
        context: Context,
        languages1: Employee.Languages?
    ) {

        binding?.llLanguageInfo?.visibility = View.VISIBLE
        binding?.hLanguage?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.languageBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.languageBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fLanguageNOLanguage?.etText?.setText(languages1?.name_of_language)
        binding?.fLanguageNOLanguageBn?.etText?.setText(languages1?.name_of_language_bn)
        binding?.fLanguageNOInstitute?.etText?.setText(languages1?.name_of_institute)
        binding?.fLanguageNOInstituteBn?.etText?.setText(languages1?.name_of_institute_bn)
        binding?.fLanguageCertificationDate?.tvText?.setText(languages1?.certification_date)

        binding?.fLanguageCertificationDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fLanguageCertificationDate?.tvText?.setText("" + it) }
                }
            })
        })

        //   Log.e("gender", "gender message " + Gson().toJson(list))
        getExpertiseLevel()?.let {
            SpinnerAdapter().setSpinnerForStringMatch(
                binding?.fLanguageExperienceLevel?.spinner!!,
                context,
                getExpertiseLevel(),
                languages1?.expertise_level,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        expertLevel = any as SpinnerDataModel
                    }

                }
            )
        }

        binding?.languageBtnAddUpdate?.btnUpdate?.setOnClickListener({
            var employeeInfoEditCreateRepo = ViewModelProviders.of(MainActivity.context!!)
                .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog =
                CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateLanguageInfo(
                        languages1?.id,
                        getMapData()
                    )
                        ?.observe(EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                } else {
                    employeeInfoEditCreateRepo?.addLanguageInfo(getMapData())
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
            MainActivity.selectedPosition = 6
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
                                binding?.fChildrenPassportNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fChildrenPassportNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "name_of_language" -> {
                                binding?.fLanguageNOLanguage?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLanguageNOLanguage?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_of_language_bn" -> {
                                binding?.fLanguageNOLanguageBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLanguageNOLanguageBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_of_institute" -> {
                                binding?.fLanguageNOInstitute?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLanguageNOInstitute?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_of_institute_bn" -> {
                                binding?.fLanguageNOInstituteBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLanguageNOInstituteBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "expertise_level" -> {
                                binding?.fLanguageExperienceLevel?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLanguageExperienceLevel?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "certification_date" -> {
                                binding?.fLanguageCertificationDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLanguageCertificationDate?.tvError?.text =
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
        map.put("name_of_language", binding?.fLanguageNOLanguage?.etText?.text.toString())
        map.put("name_of_language_bn", binding?.fLanguageNOLanguageBn?.etText?.text.toString())
        map.put("name_of_institute", binding?.fLanguageNOInstitute?.etText?.text.toString())
        map.put("name_of_institute_bn", binding?.fLanguageNOInstituteBn?.etText?.text.toString())
        map.put(
            "expertise_level",
            if (context?.let {
                    MySharedPreparence(it).getLanguage().equals("en")
                }!!) expertLevel?.name else expertLevel?.name_bn
        )
        map.put("certification_date", binding?.fLanguageCertificationDate?.tvText?.text.toString())
        map.put("status", languages?.status)
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


    fun getExpertiseLevel(): List<SpinnerDataModel> {
        var rel = SpinnerDataModel()
        rel.apply {
            name = "Expert"
            name_bn = "দক্ষ"

        }
        var rel1 = SpinnerDataModel()
        rel1.apply {
            name = "Medium"
            name_bn = "মধ্যম"


        }

        var rel2 = SpinnerDataModel()
        rel2.apply {
            name = "Average"
            name_bn = "গড়"


        }
        var rel3 = SpinnerDataModel()
        rel3.apply {
            name = "Low"
            name_bn = "কম"


        }
        return arrayListOf(rel, rel1, rel2, rel3)
    }

}