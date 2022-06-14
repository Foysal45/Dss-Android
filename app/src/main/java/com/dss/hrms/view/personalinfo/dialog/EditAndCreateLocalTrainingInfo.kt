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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.retrofit.RetrofitInstance
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

class EditAndCreateLocalTrainingInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var localTraining: Employee.LocalTrainings? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var gender: SpinnerDataModel? = null
    var fileClickListener: FileClickListener? = null
    var loacTrainingDocumnet: String? = null
    var imageFile: File? = null
    var imageUrl: String? = null
    var dialog: Dialog? = null
    var hrm_trainningId: Int? = 0

    fun showDialog(
        context: Context,
        position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.position = position
        this.localTraining =
            position?.let { employeeProfileData?.employee?.local_trainings?.get(it) }
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
        var window: Window? = dialogCustome?.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateLocalTraining(context)
        dialogCustome?.show()

    }

    private fun updateLocalTraining(
        context: Context
    ) {

        binding?.llLocalTrainingInfo?.visibility = View.VISIBLE
        binding?.llTraining?.visibility = View.GONE
        binding?.fLocalTrainingLocation?.llBody?.visibility = View.VISIBLE
        binding?.fLocalTrainingLocationBn?.llBody?.visibility = View.VISIBLE
        binding?.fLocalTrainingCountry?.llBody?.visibility = View.GONE
        binding?.hLocaltraining?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })
        binding?.hLocaltraining?.title = context.getString(R.string.local_training)
        if (key.equals(StaticKey.CREATE)) {
            binding?.trainingBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.trainingBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fLocalTrainingCourseT?.etText?.setText(localTraining?.course_title)
        binding?.fLocalTrainingCourseTBn?.etText?.setText(localTraining?.course_title_bn)
        binding?.fLocalTrainingNOInst?.etText?.setText(localTraining?.name_of_institute)
        binding?.fLocalTrainingNOInstBn?.etText?.setText(localTraining?.name_of_institute_bn)
        binding?.fLocalTrainingLocation?.etText?.setText(localTraining?.location)
        binding?.fLocalTrainingLocationBn?.etText?.setText(localTraining?.location_bn)
        binding?.fLocalTrainingFromDate?.tvText?.setText(localTraining?.from_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })



        commonRepo.getAllHrTraining(
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    Log.e("HR", "HR message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fLocalTrainingCategory?.spinner!!,
                            context,
                            list,
                            localTraining?.hrm_training_category_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    var hrm = any as SpinnerDataModel
                                    hrm_trainningId = hrm.id
                                }

                            }
                        )
                    }
                }
            })


        binding?.fLocalTrainingToDate?.tvText?.setText(localTraining?.to_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })

        binding?.tvTrainingCertificate?.setText(context.getString(R.string.certificate))
        context?.let {
            binding?.ivTraining?.let { it1 ->
                localTraining?.certificate?.let { it2 ->
                    Glide.with(it).applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.drawable.ic_baseline_image_24)
                    ).load(it2)
                        .into(it1)
                }
            }
        }
        binding?.ivTraining?.setOnClickListener {
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    imageFile = imgFile
                    binding?.ivTraining?.setImageBitmap(bitmap)
                    //   Toast.makeText(context, "image", Toast.LENGTH_LONG).show()
                    Log.e("image", "dialog imageFile  : " + imageFile)
                }
            })
        }

        if (localTraining?.local_training_document_path.toString()
                .toLowerCase() != "null" || !localTraining?.local_training_document_path.isNullOrEmpty()
        ) {
            binding?.fLocalTrainingAddAttachment?.ftvAttachment?.text =
                context.getString(R.string.attachment) + "\n" +
                        localTraining?.local_training_document_path
        }



        binding?.fLocalTrainingAddAttachment?.Attachment?.setOnClickListener {
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    // check for file size validation
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding?.fLocalTrainingAddAttachment?.fAttachmentFileName?.text =
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

        binding?.fLocalTrainingToDate?.tvText?.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fLocalTrainingToDate?.tvText?.setText("" + it) }
                }
            })
        }
        binding?.fLocalTrainingFromDate?.tvText?.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding?.fLocalTrainingFromDate?.tvText?.setText("" + it) }
                }
            })
        }


        binding?.trainingBtnAddUpdate?.btnUpdate?.setOnClickListener {
            dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            if (imageFile != null) {
                imageFile?.let { it1 -> uploadImage(it1) }
            } else {
                uploadData()
            }


        }


    }

    fun showResponse(any: Any) {
        if (any is String) {
            toast(EmployeeInfoActivity.context, "" + context?.getString(R.string.updated))
            MainActivity.selectedPosition = 9
            EmployeeInfoActivity.refreshEmployeeInfo()
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
                                binding?.fLocalTrainingToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingToDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "course_title" -> {
                                binding?.fLocalTrainingCourseT?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingCourseT?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "course_title_bn" -> {
                                binding?.fLocalTrainingCourseTBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingCourseTBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_of_institute" -> {
                                binding?.fLocalTrainingNOInst?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingNOInst?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_of_institute_bn" -> {
                                binding?.fLocalTrainingNOInstBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingNOInstBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "location" -> {
                                binding?.fLocalTrainingLocation?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingLocation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "location_bn" -> {
                                binding?.fLocalTrainingLocationBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingLocationBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "hrm_training_category_id" -> {
                                binding?.fLocalTrainingCategory?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingCategory?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "from_date" -> {
                                binding?.fLocalTrainingFromDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingFromDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "to_date" -> {
                                binding?.fLocalTrainingToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingToDate?.tvError?.text =
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

        var fromDate =
            DateConverter.changeDateFormateForSending(binding?.fLocalTrainingFromDate?.tvText?.text.toString())
        var toDate =
            DateConverter.changeDateFormateForSending(binding?.fLocalTrainingToDate?.tvText?.text.toString())
        var map = HashMap<Any, Any?>()
        map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
        map.put("course_title", binding?.fLocalTrainingCourseT?.etText?.text.toString())
        map.put("course_title_bn", binding?.fLocalTrainingCourseTBn?.etText?.text.toString())
        map.put("name_of_institute", binding?.fLocalTrainingNOInst?.etText?.text.toString())
        map.put("name_of_institute_bn", binding?.fLocalTrainingNOInstBn?.etText?.text.toString())
        map.put("location", binding?.fLocalTrainingLocation?.etText?.text.toString())
        map.put("location_bn", binding?.fLocalTrainingLocationBn?.etText?.text.toString())
        map.put("from_date", fromDate)
        map.put("to_date", toDate)
        try {
            if (key == StaticKey.EDIT && localTraining?.isPendingData == false) {
                map.put("parent_id", localTraining?.id)
            } else if (key == StaticKey.EDIT && localTraining?.isPendingData == true) {
                map.put("parent_id", localTraining?.parent_id)
            }
        } catch (Ex: java.lang.Exception) {

        }
        map.put("hrm_training_category_id", hrm_trainningId)
        map.put("local_training_document_path", loacTrainingDocumnet)
        imageUrl?.let { map.put("certificate", RetrofitInstance.BASE_URL + it) }
        if (localTraining?.status != null) map.put(
            "status",
            localTraining?.status
        ) else map.put("status", 1)
        return map
    }


    fun uploadData() {
        var employeeInfoEditCreateRepo =
            ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                .get(EmployeeInfoEditCreateViewModel::class.java)
        invisiableAllError(binding)
        key?.let {
            if (it.equals(StaticKey.EDIT)) {
                employeeInfoEditCreateRepo.updateLocalTrainingInfo(
                    localTraining?.id,
                    getMapData()
                )
                    ?.observe(
                        EmployeeInfoActivity.context!!
                    ) { any ->
                        dialog?.dismiss()
                        Log.e("yousuf", "error : " + Gson().toJson(any))
                        showResponse(any)
                    }
            } else {
                employeeInfoEditCreateRepo?.addLocalTrainingInfo(getMapData())
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
                RequestBody.create("text/plain".toMediaTypeOrNull(), "profile_photo")
            val requestFile: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), imageFile)
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
        binding?.fLocalTrainingCourseT?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingCourseTBn?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingNOInst?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingNOInstBn?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingLocation?.tvError?.visibility =
            View.GONE
        binding?.fLocalTrainingLocationBn?.tvError?.visibility =
            View.GONE

        binding?.fLocalTrainingFromDate?.tvError?.visibility =
            View.GONE
        binding?.fLocalTrainingToDate?.tvError?.visibility =
            View.GONE
        binding?.fLocalTrainingCategory?.tvError?.visibility =
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
                        Log.d("TESTUPLOAD", "uploadFile: $fileUrl ")
                        loacTrainingDocumnet = fileUrl
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