package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
import android.app.ProgressDialog
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
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.ConvertNumber
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.FileClickListener
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

class EditAndCreatePublicatioInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    var dialogCustome: Dialog? = null
    var publications: Employee.Publications? = null
    lateinit var binding: DialogPersonalInfoBinding
    var context: Context? = null
    lateinit var key: String
    var document_path: String? = null
    var publicationType: SpinnerDataModel? = null
    var fileClickListener: FileClickListener? = null

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0

    fun showDialog(
        context: Context,
        position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.position = position
        this.context = context
        this.key = key
        this.fileClickListener = fileClickListener
        dialogCustome = Dialog(context)
        publications = position?.let { employeeProfileData.employee?.publications?.get(it) }
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

        binding?.fPublicationAttachment.Attachment.setOnClickListener {

            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding.fPublicationAttachment.fAttachmentFileName.text =
                                imgFile.name
                            uploadFile(imgFile, context)
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

        binding?.publicationBtnAddUpdate?.btnUpdate?.setOnClickListener {
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
                        ?.observe(
                            EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)
                            })
                } else {
                    employeeInfoEditCreateRepo?.addPublicationInfo(getMapData())
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
        try{
            if (key == StaticKey.EDIT && publications?.isPendingData == false  ) {
                map.put("parent_id", publications?.id)
            }

            else if (  key == StaticKey.EDIT && publications?.isPendingData == true) {
                map.put("parent_id", publications?.parent_id)
            }
        }catch (Ex : java.lang.Exception){

        }
        map.put("publication_details_bn", binding?.fPublicationDetailsBn?.etText?.text.toString())
        map.put("document_path",document_path)
        if (publications?.status != null) map.put(
            "status",
            publications?.status
        ) else map.put("status", 1)

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
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
    }

    fun uploadFile(file: File, context: Context) {
        val dialouge = ProgressDialog(EmployeeInfoActivity.context)
        dialouge.setMessage("uploading...")
        dialouge.setCancelable(false)
        dialouge.show()

        var profilePic: MultipartBody.Part?

        var filePart: MultipartBody.Part?

        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        profilePic =
            MultipartBody.Part.createFormData("filenames[]", "${file.name}", requestFile)
//        profilePic =
//            MultipartBody.Part.createFormData("filenames[]", "filenames[${file.name}]", requestFile)

        val profile_photo: RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), "profile_ph")

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
                        Log.d("TESTUPLOAD", "uploadFile: $fileUrl ")
                        document_path = fileUrl
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