package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.text.InputType
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
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.*
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.*
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class EditAndCreateChildInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData


    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var child: Employee.Childs? = null
    private lateinit var binding: DialogPersonalInfoBinding
    var context: Context? = null
    lateinit var key: String
    var gender: SpinnerDataModel? = null
    var nid_document_path: String? = null
    var passport_documnet_path: String? = null
    var birth_certificate_document_path: String? = null

    var fileClickListener: FileClickListener? = null
    fun showDialog(
        context: Context,
        position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.child = position?.let { employeeProfileData.employee?.childs?.get(it) }
        this.position = position
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
        binding.root.let { dialogCustome?.setContentView(it) }
        val window: Window? = dialogCustome?.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateEducationQualification(context)
        // HelperClass
//
        dialogCustome?.show()

    }

    fun updateEducationQualification(
        context: Context

    ) {
        binding?.fChildrenNidNo?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fChildrenBirthCertificateNo?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.llChildrenInfo?.visibility = View.VISIBLE
        binding?.hChildren?.tvClose?.setOnClickListener {
            dialogCustome?.dismiss()
        }

        if (key.equals(StaticKey.CREATE)) {
            binding.childrenBtnAddUpdate.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding.childrenBtnAddUpdate.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fChildrenNameOChEn?.etText?.setText(child?.name_of_children)
        binding?.fChildrenNameOChBn?.etText?.setText(child?.name_of_children_bn)
        binding?.fChildrenBirthCertificateNo?.etText?.setText(child?.birth_certificate)
        binding?.fChildrenNidNo?.etText?.setText(child?.nid)
        binding?.fChildrenPassportNo?.etText?.setText(child?.passport)
        binding?.fChildrenDOB?.tvText?.setText(
            child?.date_of_birth?.let {
                DateConverter.changeDateFormateForShowing(
                    it
                )
            }
        )

        binding?.fChildrenDOB?.tvText?.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fChildrenDOB.tvText.text = "" + it }
                }
            })
        }
        commonRepo?.getCommonData("/api/auth/gender/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fChildrenGenderOrSex?.spinner!!,
                            context,
                            list,
                            child?.gender_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    gender = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        binding.fChildNidADD.ftvAttachment.text = context.getString(R.string.nid_attachment)
        binding.fChildPassportADD.ftvAttachment.text =
            context.getString(R.string.passport_attachment)
        binding.fChildbirthCertificateADD.ftvAttachment.text =
            context.getString(R.string.birth_certificate_attachment)

        /*

         1 = nid
         2 = passport
         3 = birth certificate
        */

        if (child?.nid_document_path.toString()
                .toLowerCase() != "null" || !child?.nid_document_path.isNullOrEmpty()
        ) {
            binding.fChildNidADD.ftvAttachment.text =
                context.getString(R.string.nid_attachment) + "\n" + child?.nid_document_path
        }

        if (child?.passport_document_path.toString()
                .toLowerCase() != "null" || !child?.passport_document_path.isNullOrEmpty()
        ) {

            binding.fChildPassportADD.ftvAttachment.text =
                context.getString(R.string.passport_attachment) + "\n" + child?.passport_document_path
        }
        if (child?.birth_certificate_document_path.toString()
                .toLowerCase() != "null" || !child?.birth_certificate_document_path.isNullOrEmpty()
        ) {
            binding.fChildbirthCertificateADD.ftvAttachment.text =
                context.getString(R.string.birth_certificate_attachment) + "\n" +
                        child?.birth_certificate_document_path
        }

        // need reDesign 

        binding.fChildNidADD.Attachment.setOnClickListener {

            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding.fChildNidADD.fAttachmentFileName.text =
                                imgFile.name
                            uploadFile(imgFile, context, 1)
                        } else {

                            ConvertNumber.errorDialogueWithProgressBar(
                                context,
                                context.getString(R.string.error_file_size)
                            )

                        }
                    } catch (e: Exception) {
                        toast(context, "ERROR : ${e.localizedMessage} . Try again")
                    }


                }
            })

        }
        binding.fChildPassportADD.Attachment.setOnClickListener {

            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding.fChildPassportADD.fAttachmentFileName.text =
                                imgFile.name
                            uploadFile(imgFile, context, 2)
                        } else {

                            ConvertNumber.errorDialogueWithProgressBar(
                                context,
                                context.getString(R.string.error_file_size)
                            )

                        }
                    } catch (e: Exception) {
                        toast(context, "ERROR : ${e.localizedMessage} . Try again")
                    }


                }
            })

        }
        binding.fChildbirthCertificateADD.Attachment.setOnClickListener {

            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding.fChildbirthCertificateADD.fAttachmentFileName.text =
                                imgFile.name
                            uploadFile(imgFile, context, 3)
                        } else {

                            ConvertNumber.errorDialogueWithProgressBar(
                                context,
                                context.getString(R.string.error_file_size)
                            )

                        }
                    } catch (e: Exception) {
                        toast(context, "ERROR : ${e.localizedMessage} . Try again")
                    }


                }
            })

        }

        binding.childrenBtnAddUpdate.btnUpdate?.setOnClickListener {
            val employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateChildInfo(
                        child?.id,
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
                    employeeInfoEditCreateRepo?.addChildInfo(getMapData())
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


    }

    fun showResponse(any: Any) {
        if (any is String) {
            toast(EmployeeInfoActivity.context, "" + context?.getString(R.string.updated))
            MainActivity.selectedPosition = 2
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
                            "name_of_children" -> {
                                binding.fChildrenNameOChEn.tvError?.visibility =
                                    View.VISIBLE
                                binding.fChildrenNameOChEn.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_of_children_bn" -> {
                                binding?.fChildrenNameOChBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fChildrenNameOChBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "date_of_birth" -> {
                                binding?.fChildrenDOB?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fChildrenDOB?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "gender_id" -> {
                                binding?.fChildrenGenderOrSex?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fChildrenGenderOrSex?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "birth_certificate" -> {
                                binding?.fChildrenBirthCertificateNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fChildrenBirthCertificateNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nid" -> {
                                binding?.fChildrenNidNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fChildrenNidNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "passport" -> {
                                binding?.fChildrenPassportNo?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fChildrenPassportNo?.tvError?.text =
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
            DateConverter.changeDateFormateForSending(binding?.fChildrenDOB?.tvText?.text.toString())
        map.put("employee_id", employeeProfileData.employee?.user?.employee_id)
        map.put("name_of_children", binding?.fChildrenNameOChEn?.etText?.text.toString())
        map.put("name_of_children_bn", binding?.fChildrenNameOChBn?.etText?.text.toString())
        map.put("date_of_birth", date)
        map.put("gender_id", gender?.id)
        map.put("birth_certificate", binding?.fChildrenBirthCertificateNo?.etText?.text.toString())
        map.put("nid", binding?.fChildrenNidNo?.etText?.text.toString())
        map.put("passport", binding?.fChildrenPassportNo?.etText?.text.toString())
        try {

            if (key == StaticKey.EDIT && child?.isPendingData == false) {
                map.put("parent_id", child?.id)
            } else if (key == StaticKey.EDIT && child?.isPendingData == true) {
                map.put("parent_id", child?.parent_id)
            }

        } catch (ex: java.lang.Exception) {

        }
        map.put(
            "birth_certificate_document_path",
            birth_certificate_document_path
        )
        map.put("passport_document_path", passport_documnet_path)
        map.put("nid_document_path", nid_document_path)
        map.put("status", child?.status)
        return map
    }


    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fChildrenNameOChEn?.tvError?.visibility =
            View.GONE

        binding?.fChildrenNameOChBn?.tvError?.visibility =
            View.GONE

        binding?.fChildrenDOB?.tvError?.visibility =
            View.GONE

        binding?.fChildrenGenderOrSex?.tvError?.visibility =
            View.GONE

        binding?.fChildrenBirthCertificateNo?.tvError?.visibility =
            View.GONE

        binding?.fChildrenNidNo?.tvError?.visibility =
            View.GONE
        binding?.fChildrenPassportNo?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
    }


    fun getRelationList(): List<SpinnerDataModel> {

        var rel = SpinnerDataModel()
        rel.apply {
            name = "Wife"
            name_bn = "??????????????????"

        }
        var rel1 = SpinnerDataModel()
        rel1.apply {
            name = "Husband"
            name_bn = "??????????????????"


        }
        return arrayListOf(rel, rel1)
    }

    fun uploadFile(file: File, context: Context, value: Int) {
        val dialouge = ProgressDialog(EmployeeInfoActivity.context)
        dialouge.setMessage("uploading...")
        dialouge.setCancelable(false)
        dialouge.show()

        var profilePic: MultipartBody.Part?

        var filePart: MultipartBody.Part?

        val requestFile: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        profilePic =
            MultipartBody.Part.createFormData("filenames[]", "${file.name}", requestFile)
//        profilePic =
//            MultipartBody.Part.createFormData("filenames[]", "filenames[${file.name}]", requestFile)

        val profile_photo: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), "profile_ph")

        val employeeInfoEditCreateRepo =
            ViewModelProviders.of(EmployeeInfoActivity.context!!, viewModelProviderFactory)
                .get(EmployeeInfoEditCreateViewModel::class.java)
        employeeInfoEditCreateRepo.uploadProfilePic(
            profilePic,
            file.name,
            profile_photo
        )
            ?.observe(
                EmployeeInfoActivity.context!!,
                { any ->
                    Log.e("yousuf", "profile pic : " + any)
                    //  showResponse(any)
                    dialouge?.dismiss()
                    if (any != null) {
                        val fileUrl = any as String
                        /*
                         1 = nid
                         2 = passport
                         3 = birth certificate
                         */
                        when (value) {
                            1 -> {
                                nid_document_path = fileUrl
                            }
                            2 -> {
                                passport_documnet_path = fileUrl
                            }
                            3 -> {
                                birth_certificate_document_path = fileUrl
                            }
                        }
                        Toast.makeText(
                            context,
                            context.getString(R.string.successMsg),
                            Toast.LENGTH_LONG
                        )
                            .show()

                    } else {

                        Toast.makeText(
                            context,
                            context.getString(R.string.uploadFailed),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                })


    }
}