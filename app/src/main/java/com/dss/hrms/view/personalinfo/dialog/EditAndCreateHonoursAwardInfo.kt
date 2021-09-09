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
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.*
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.FileClickListener
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.allInterface.OnFilevalueReceiveListener
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class EditAndCreateHonoursAwardInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0
    var fileClickListener: FileClickListener? = null
    var dialogCustome: Dialog? = null
    var honoursAward: Employee.HonoursAwards? = null
    private lateinit var binding: DialogPersonalInfoBinding
    var context: Context? = null
    lateinit var key: String
    var honours_awards_document_path: String? = null

    fun showDialog(
        context: Context,
        position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.position = position
        this.honoursAward = position?.let { employeeProfileData?.employee?.honours_awards?.get(it) }
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

        binding?.fAwardDate?.tvText?.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fAwardDate?.tvText?.setText("" + it) }
                }
            })
        }

        binding.fAwardAttachment.Attachment.setOnClickListener {

            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding.fAwardAttachment.fAttachmentFileName.text =
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


        binding?.honoursAwardBtnAddUpdate?.btnUpdate?.setOnClickListener {
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
        }


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
        map.put("honours_awards_document_path", honours_awards_document_path)
        map.put("award_date", award_date)
        map.put("status", honoursAward?.status)
        return map
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
                        honours_awards_document_path = fileUrl
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