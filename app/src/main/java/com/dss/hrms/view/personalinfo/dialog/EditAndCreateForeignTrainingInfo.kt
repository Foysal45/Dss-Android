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
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.SpinnerDataModel
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class EditAndCreateForeignTrainingInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    @Inject
    lateinit var employeeProfileData: EmployeeProfileData


    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var foreigntraining: Employee.Foreigntrainings? = null
    var hrmTrainingId: Int? = 0
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var country: SpinnerDataModel? = null
    var fileClickListener: FileClickListener? = null
    var imageFile: File? = null
    var imageUrl: String? = null
    var dialog: Dialog? = null
    private var foreign_training_document_path: String? = null

    fun showDialog(
        context: Context,
        position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.foreigntraining = position?.let {
            employeeProfileData.employee?.foreigntrainings?.get(
                it
            )
        }
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
        val window: Window? = dialogCustome?.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateForeignTraining(context)
        dialogCustome?.show()

    }

    fun updateForeignTraining(
        context: Context
    ) {


        binding?.llLocalTrainingInfo?.visibility = View.VISIBLE
        binding?.llTraining?.visibility = View.GONE
        binding?.fLocalTrainingLocation?.llBody?.visibility = View.GONE
        binding?.fLocalTrainingLocationBn?.llBody?.visibility = View.GONE
        binding?.llLocalTrainingInfo?.visibility = View.VISIBLE
        binding?.hLocaltraining?.title = context.getString(R.string.foreign_training)
        binding?.hLocaltraining?.tvClose?.setOnClickListener {
            dialogCustome?.dismiss()
        }

        if (key.equals(StaticKey.CREATE)) {
            binding?.trainingBtnAddUpdate?.btnUpdate?.text = "" + context.getString(R.string.submit)
        } else {
            binding?.trainingBtnAddUpdate?.btnUpdate?.text = "" + context.getString(R.string.update)
        }

        binding?.fLocalTrainingCourseT?.etText?.setText(foreigntraining?.course_title)
        binding?.fLocalTrainingCourseTBn?.etText?.setText(foreigntraining?.course_title_bn)
        binding?.fLocalTrainingNOInst?.etText?.setText(foreigntraining?.name_of_institute)
        binding?.fLocalTrainingNOInstBn?.etText?.setText(foreigntraining?.name_of_institute_bn)
        binding?.fLocalTrainingFromDate?.tvText?.text = foreigntraining?.from_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        }
        binding?.fLocalTrainingToDate?.tvText?.text = foreigntraining?.to_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        }


        commonRepo.getAllHrTraining(
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    Log.e("HR", "HR message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fLocalTrainingCategory?.spinner!!,
                            context,
                            list,
                            foreigntraining?.hrm_training_category_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    val hrm = any as SpinnerDataModel
                                    hrmTrainingId = hrm.id
                                }

                            }
                        )
                    }
                }
            })


        binding?.tvTrainingCertificate?.text = context.getString(R.string.certificate)

        context?.let {
            binding?.ivTraining?.let { it1 ->
                foreigntraining?.certificate?.let { it2 ->
                    Glide.with(it).applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.drawable.ic_baseline_image_24)
                    ).load(it2)
                        .into(it1)
                }
            }
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


        binding?.ivTraining?.setOnClickListener {
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    imageFile = imgFile
                    binding?.ivTraining?.setImageBitmap(bitmap)
                    //   Toast.makeText(context, "image", Toast.LENGTH_LONG).show()
                    Log.e("image", "dialog imageFile  : $imageFile")
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
                    date?.let { binding?.fLocalTrainingFromDate?.tvText?.setText("" + it) }
                }
            })
        }

        commonRepo.getCommonData("/api/auth/country/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fLocalTrainingCountry?.spinner!!,
                            context,
                            list,
                            foreigntraining?.country_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    country = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

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
            MainActivity.selectedPosition = 10
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
                            "hrm_training_category_id" -> {
                                binding?.fLocalTrainingCategory?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingCategory?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "location_bn" -> {
                                binding?.fLocalTrainingLocationBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingLocationBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "country_id" -> {
                                binding?.fLocalTrainingCountry?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fLocalTrainingCountry?.tvError?.text =
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
                            "hrm_training_category_id" -> {
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
        map["employee_id"] = employeeProfileData.employee?.user?.employee_id
        map["course_title"] = binding?.fLocalTrainingCourseT?.etText?.text.toString()
        map["course_title_bn"] = binding?.fLocalTrainingCourseTBn?.etText?.text.toString()
        map["name_of_institute"] = binding?.fLocalTrainingNOInst?.etText?.text.toString()
        map["name_of_institute_bn"] = binding?.fLocalTrainingNOInstBn?.etText?.text.toString()
        map["country_id"] = country?.id
        map["from_date"] = fromDate
        map["to_date"] = toDate
        map["hrm_training_category_id"] = hrmTrainingId
        map["foreign_training_document_path"] = foreign_training_document_path
        imageUrl?.let { map.put("certificate", RetrofitInstance.BASE_URL + it) }
        map["status"] = foreigntraining?.status
        return map
    }


    private fun uploadData() {
        val employeeInfoEditCreateRepo =
            ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                .get(EmployeeInfoEditCreateViewModel::class.java)
        invisiableAllError(binding)
        key?.let {
            if (it.equals(StaticKey.EDIT)) {
                employeeInfoEditCreateRepo?.updateForeignTrainingInfo(
                    foreigntraining?.id,
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
                employeeInfoEditCreateRepo?.addForeignTrainingInfo(getMapData())
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
        val profilePic: MultipartBody.Part?
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
        binding?.fLocalTrainingCountry?.tvError?.visibility =
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

        val profilePic: MultipartBody.Part?

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
                        foreign_training_document_path = fileUrl
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