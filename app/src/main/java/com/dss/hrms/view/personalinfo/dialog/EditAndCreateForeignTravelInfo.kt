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

class EditAndCreateForeignTravelInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0
    var fileClickListener: FileClickListener? = null
    var dialogCustome: Dialog? = null
    var foreignTravels1: Employee.ForeignTravels? = null
    private lateinit var binding: DialogPersonalInfoBinding
    var context: Context? = null
    lateinit var key: String
    var country: SpinnerDataModel? = null
    var purpose: SpinnerDataModel? = null
    var documnet_path: String? = null


    fun showDialog(
        context: Context,
        position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.foreignTravels1 =
            position?.let { employeeProfileData?.employee?.foreign_travels?.get(it) }
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
        updateForeignTravel(context)
        dialogCustome?.show()

    }

    fun updateForeignTravel(
        context: Context
    ) {

        binding?.llForeingTravelInfo?.visibility = View.VISIBLE
        binding?.hForeignTravelInfo?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.CREATE)) {
            binding?.foreignTravelBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.foreignTravelBtnAddUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }
        //  binding?.fForeignTravelPurpose?.etText?.setText(foreignTravels1?.purpose)
        //  binding?.fForeignTravelPurposeBn?.etText?.setText(foreignTravels1?.purpose_bn)
        binding?.fForeignTravelDetailsEn?.etText?.setText(foreignTravels1?.details)
        binding?.fForeignTravelDetailsBn?.etText?.setText(foreignTravels1?.details_bn)
        binding?.fForeignTravelToDate?.tvText?.setText(foreignTravels1?.to_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })

        binding?.fForeignTravelFromDate?.tvText?.setText(foreignTravels1?.from_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        })

        binding?.fForeignTravelFromDate?.tvText?.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fForeignTravelFromDate?.tvText?.setText("" + it) }
                }
            })
        }
        binding?.fForeignTravelToDate?.tvText?.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fForeignTravelToDate?.tvText?.setText("" + it) }
                }
            })
        }
        binding.fForeignTravelAttachment.Attachment.setOnClickListener {

            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding.fForeignTravelAttachment.fAttachmentFileName.text =
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


        commonRepo.getCommonData("/api/auth/country/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fForeignTravelCountry?.spinner!!,
                            context,
                            list,
                            foreignTravels1?.country_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    country = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        foreignTravels1?.purpose?.let {
            if (it == "Personal" || it == "ব্যক্তিগত") {
                purpose = getPurposeList().get(0)
            } else {
                purpose = getPurposeList().get(1)
            }
        }

        getPurposeList()?.let {
            SpinnerAdapter().setSpinner(
                binding?.fForeignTravelPurpose?.spinner!!,
                context,
                it,
                purpose?.id,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        purpose = any as SpinnerDataModel
                    }

                }
            )
        }

        binding?.foreignTravelBtnAddUpdate?.btnUpdate?.setOnClickListener {
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key?.let {
                if (it.equals(StaticKey.EDIT)) {
                    employeeInfoEditCreateRepo?.updateForeignTravelInfo(
                        foreignTravels1?.id,
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
                    employeeInfoEditCreateRepo?.addForeignTravelInfo(getMapData())
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
            MainActivity.selectedPosition = 12
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
                                binding?.fForeignTravelToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelToDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "country_id" -> {
                                binding?.fForeignTravelCountry?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelCountry?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "purpose" -> {
                                binding?.fForeignTravelPurpose?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelPurpose?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "details" -> {
                                binding?.fForeignTravelDetailsEn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelDetailsEn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "details_bn" -> {
                                binding?.fForeignTravelDetailsBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelDetailsBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "from_date" -> {
                                binding?.fForeignTravelFromDate.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelFromDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "to_date" -> {
                                binding?.fForeignTravelToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fForeignTravelToDate?.tvError?.text =
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
            DateConverter.changeDateFormateForSending(binding?.fForeignTravelFromDate?.tvText?.text.toString())
        var toDate =
            DateConverter.changeDateFormateForSending(binding?.fForeignTravelToDate?.tvText?.text.toString())
        var map = HashMap<Any, Any?>()
        map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
        map.put("country_id", country?.id)
        purpose?.id?.let {
            map.put("purpose", purpose?.name)
            map.put("purpose_bn", purpose?.name_bn)
        }
        map.put("details", binding?.fForeignTravelDetailsEn?.etText?.text.toString())
        map.put("details_bn", binding?.fForeignTravelDetailsBn?.etText?.text.toString())
        map.put("from_date", fromDate)
        map.put("to_date", toDate)
        map.put("document_path", documnet_path)
        map.put("status", foreignTravels1?.status)
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
                        documnet_path = fileUrl
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

    fun getPurposeList(): List<SpinnerDataModel> {

        var rel = SpinnerDataModel()
        rel.apply {
            id = 1
            name = "Personal"
            name_bn = "ব্যক্তিগত"

        }
        var rel1 = SpinnerDataModel()
        rel1.apply {
            id = 2
            name = "Official"
            name_bn = "দাপ্তরিক"
        }
        return arrayListOf(rel, rel1)
    }

    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fForeignTravelCountry?.tvError?.visibility =
            View.GONE

        binding?.fForeignTravelPurpose?.tvError?.visibility =
            View.GONE

        binding?.fForeignTravelDetailsEn?.tvError?.visibility =
            View.GONE
        binding?.fForeignTravelDetailsBn?.tvError?.visibility =
            View.GONE

        binding?.fForeignTravelFromDate?.tvError?.visibility =
            View.GONE

        binding?.fForeignTravelToDate?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}