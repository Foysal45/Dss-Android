package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DateConverter
import com.dss.hrms.util.DatePicker
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.FileClickListener
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.allInterface.OnFilevalueReceiveListener
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class EditAndCreateLanguageInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var language: Employee.Languages? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var fileClickListener: FileClickListener? = null
    var expertLevel: SpinnerDataModel? = null
    var imageFile: File? = null
    var imageUrl: String? = null
    var dialog: Dialog? = null


    fun showDialog(
        context: Context,
        position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.position = position
        this.language = position?.let { employeeProfileData?.employee?.languages?.get(it) }
        this.context = context
        this.key = key
        this.fileClickListener = fileClickListener
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
        binding?.llLanguageInfo?.visibility = View.VISIBLE
        binding?.llLanguageCertificate?.visibility = View.GONE
        binding?.hLanguage?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.languageBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.languageBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fLanguageNOLanguage?.etText?.setText(language?.name_of_language)
        binding?.fLanguageNOLanguageBn?.etText?.setText(language?.name_of_language_bn)
        binding?.fLanguageNOInstitute?.etText?.setText(language?.name_of_institute)
        binding?.fLanguageNOInstituteBn?.etText?.setText(language?.name_of_institute_bn)
        binding?.fLanguageCertificationDate?.tvText?.setText(language?.certification_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })

        binding?.tvLanguageCertificate?.setText(context.getString(R.string.certificate))

        context?.let {
            binding?.ivLanguage?.let { it1 ->
                language?.certificate?.let { it2 ->
                    Glide.with(it).applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.drawable.ic_baseline_image_24)
                    ).load(it2)
                        .into(it1)
                }
            }
        }

        binding?.ivLanguage?.setOnClickListener({
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap) {
                    imageFile = imgFile
                    binding?.ivLanguage?.setImageBitmap(bitmap)
                    //   Toast.makeText(context, "image", Toast.LENGTH_LONG).show()
                    Log.e("image", "dialog imageFile  : " + imageFile)
                }
            })
        })

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
                language?.expertise_level,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        expertLevel = any as SpinnerDataModel
                    }

                }
            )
        }

        binding?.languageBtnAddUpdate?.btnUpdate?.setOnClickListener({
            dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            if (imageFile != null) {
                imageFile?.let { it1 -> uploadImage(it1) }
            } else {
                uploadData()
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
        var date =
            DateConverter.changeDateFormateForSending(binding?.fLanguageCertificationDate?.tvText?.text.toString())
        map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
        map.put("name_of_language", binding?.fLanguageNOLanguage?.etText?.text.toString())
        map.put("name_of_language_bn", binding?.fLanguageNOLanguageBn?.etText?.text.toString())
        map.put("name_of_institute", binding?.fLanguageNOInstitute?.etText?.text.toString())
        map.put("name_of_institute_bn", binding?.fLanguageNOInstituteBn?.etText?.text.toString())
//        map.put(
//            "expertise_level",
//            if (context?.let {
//                    MySharedPreparence(it).getLanguage().equals("en")
//                }!!) expertLevel?.name else expertLevel?.name_bn
//        )
        map.put("expertise_level", expertLevel?.name?.toLowerCase())
        // imageUrl?.let { map.put("certificate", RetrofitInstance.BASE_URL + it) }
        map.put("certification_date", date)
        map.put("status", language?.status)
        return map
    }


    fun uploadData() {
        var employeeInfoEditCreateRepo =
            ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                .get(EmployeeInfoEditCreateViewModel::class.java)
        invisiableAllError(binding)

        key?.let {
            if (it.equals(StaticKey.EDIT)) {
                employeeInfoEditCreateRepo?.updateLanguageInfo(
                    language?.id,
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
                employeeInfoEditCreateRepo?.addLanguageInfo(getMapData())
                    ?.observe(
                        EmployeeInfoActivity.context!!,
                        Observer { any ->
                            dialog?.dismiss()
                            Log.e("yousuf", "error : " + Gson().toJson(any))
                            showResponse(any)
                        })
            }
        }
    }


    fun uploadImage(imageFile: File) {
        var profilePic: MultipartBody.Part?
        if (imageFile != null) {
            val profile_photo: RequestBody =
                RequestBody.create(MediaType.parse("text/plain"), "profile_photo")
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
            profilePic =
                MultipartBody.Part.createFormData("filenames[]", "filenames[]", requestFile)

            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(EmployeeInfoActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            employeeInfoEditCreateRepo?.uploadProfilePic(profilePic, imageFile.name, profile_photo)
                ?.observe(
                    EmployeeInfoActivity.context!!,
                    androidx.lifecycle.Observer { any ->
                        Log.e("yousuf", "profile pic : " + any)
                        //  showResponse(any)
                        if (any != null) {
                            imageUrl = any as String
                            uploadData()
                        } else {
                            dialog?.dismiss()
                        }

                    })
        }
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
            name_bn = "বিশেষজ্ঞ"

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