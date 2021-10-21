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
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.FileClickListener
import com.dss.hrms.view.allInterface.OnFilevalueReceiveListener
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.personalinfo.adapter.name_row_adapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import java.io.File
import javax.inject.Inject

class EditQuotaInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData


    var position: Int? = 0
    var dialogCustome: Dialog? = null
    var employeeQuotas: Employee.EmployeeQuotas? = null
    var fileClickListener: FileClickListener? = null
    var documentPaths = JSONArray()
    val documentNameList: MutableList<String> = ArrayList()
    private lateinit var nameRowAdapter: name_row_adapter

    fun showDialog(
        context: Context, position: Int?,
        fileClickListener: FileClickListener
    ) {
        this.fileClickListener = fileClickListener
        this.position = position
        this.employeeQuotas =
            position?.let { employeeProfileData?.employee?.employee_quotas?.get(it) }
        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        var dialogCustomeBinding: DialogPersonalInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info,
            null,
            false
        )
        dialogCustome?.setContentView(dialogCustomeBinding.getRoot())
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        documentNameList.clear()
        documentPaths = JSONArray()

        val itemOnClick: (Int) -> Unit = {
            try {
                documentNameList.removeAt(it)
                nameRowAdapter.notifyItemRemoved(it)
            } catch (Ex: java.lang.Exception) {
                toast(context, "${Ex.localizedMessage}")
            }
        }
        nameRowAdapter = name_row_adapter(documentNameList, itemOnClick)

        dialogCustomeBinding.NameList.apply {
            adapter = nameRowAdapter
            layoutManager = LinearLayoutManager(context)
        }
        updateJobjoiningInfo(context, dialogCustomeBinding)
        dialogCustome?.show()

    }

    fun updateJobjoiningInfo(
        context: Context,
        binding: DialogPersonalInfoBinding
    ) {
        binding.llQuotaInfo.visibility = View.VISIBLE
        binding.hQuota.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }

        var quotaName: SpinnerDataModel? = null
        var quotaType: SpinnerDataModel? = null

        binding.fQuotaDescription.etText.setText(employeeQuotas?.description)
        binding.fQuotaDescriptionBn.etText.setText(employeeQuotas?.description_bn)

        binding.fQuotaAttachment.Attachment.setOnClickListener {

            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
//                            binding.fQuotaAttachment.fAttachmentFileName.text =
//                                imgFile.name
                            documentNameList.add(imgFile.name)
                            nameRowAdapter.notifyDataSetChanged()
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


        commonRepo.getCommonData("/api/auth/quota-information/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fQuotaName.spinner,
                            context,
                            list,
                            employeeQuotas?.quota_information_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    quotaName = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })


        commonRepo.getCommonData("/api/auth/quota-information-details/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fQuotaType.spinner,
                            context,
                            list,
                            employeeQuotas?.quota_information_detail_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    quotaType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })


        binding.quotaBtnUpdate.btnUpdate.setOnClickListener {
            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)

            var map = HashMap<Any, Any?>()
            map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
            map.put("quota_information_id", quotaName?.id)
            map.put("quota_information_detail_id", quotaType?.id)
            try{
                    var a = employeeQuotas?.id
                    map.put("parent_id", a)

            }catch (Ex : java.lang.Exception){

            }
            map.put("description", binding.fQuotaDescription.etText.text.toString())
            map.put("description_bn", binding.fQuotaDescriptionBn.etText.text.toString())
            val gson = GsonBuilder().create()
            val packagesArray = gson.fromJson(documentNameList.toString() , Array<String>::class.java).toList()
            map.put("quota_documents", packagesArray)
            map.put("status", employeeQuotas?.status)

            Log.d("TAGGGED", "updateJobjoiningInfo: ${map.toString()}")
            invisiableAllError(binding)
            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            employeeInfoEditCreateRepo?.updateQuotaInfo(employeeQuotas?.id, map)
                ?.observe(
                    EmployeeInfoActivity.context!!,
                    Observer { any ->
                        dialog?.dismiss()
                        Log.e("yousuf", "error : " + Gson().toJson(any))

                        if (any is String) {
                            toast(EmployeeInfoActivity.context, "" + context.getString(R.string.updated))

                            MainActivity.selectedPosition = 8
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
                                                binding.fQuotaDescriptionBn.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaDescriptionBn.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }

                                        }
                                        when (error) {
                                            "quota_information_id" -> {
                                                binding.fQuotaName.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaName.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "quota_information_detail_id" -> {
                                                binding.fQuotaType.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaType.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "description" -> {
                                                binding.fQuotaDescription.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaDescription.tvError.text =
                                                    ErrorUtils2.mainError(message)
                                            }
                                            "description_bn" -> {
                                                binding.fQuotaDescriptionBn.tvError.visibility =
                                                    View.VISIBLE
                                                binding.fQuotaDescriptionBn.tvError.text =
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
                    })
        }

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
                        documentPaths.put(fileUrl)
//                        documentNameList.add(ConvertNumber.getTheFileNameFromTheLink(fileUrl))
                        Log.d("JSON", "uploadFile: " + documentPaths)


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

    fun invisiableAllError(binding: DialogPersonalInfoBinding) {
        binding.fQuotaName.tvError.visibility =
            View.GONE

        binding.fQuotaType.tvError.visibility =
            View.GONE

        binding.fQuotaDescription.tvError.visibility =
            View.GONE

        binding.fQuotaDescriptionBn.tvError.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
    }

}