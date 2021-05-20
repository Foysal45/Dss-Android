package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.*
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.*
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import kotlinx.android.synthetic.main.personel_information_edittext.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class EditCreateNomineeInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData
    var employee: Employee? = null
    var fileClickListener: FileClickListener? = null
    var position: Int? = 0
    var dialogCustome: Dialog? = null
    var nominee: Employee.Nominee? = null
    var gender: SpinnerDataModel? = null
    var maritalStatus: SpinnerDataModel? = null
    var hasDisabaility: SpinnerDataModel? = null
    open var imageFile: File? = null
    var imageUrl: String? = null
    var dialog: Dialog? = null
    lateinit var binding: DialogPersonalInfoBinding
    lateinit var key: String
    fun showDialog(
        context: Context, position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {

        this.position = position
        this.key = key
        this.fileClickListener = fileClickListener
        this.nominee =
            position?.let { employeeProfileData?.employee?.nominees?.get(it) }
        employee = employeeProfileData?.employee
        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info,
            null,
            false
        )
        dialogCustome?.setContentView(binding.getRoot())
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateNomineeInfo(context)
        dialogCustome?.show()

    }

    fun updateNomineeInfo(
        context: Context

    ) {
        binding.llNomineeInfo.visibility = View.VISIBLE
        binding.hNominee.tvClose.setOnClickListener({
            dialogCustome?.dismiss()
        })

        if (key.equals(StaticKey.EDIT)) {
            binding.nomineeUpdate.btnUpdate.setText(context.getString(R.string.update))
        }else{
            binding.nomineeUpdate.btnUpdate.setText(context.getString(R.string.update))
        }

        binding.fNomineeName.etText.setText(nominee?.name)
        binding.fNomineeRelation.etText.setText(nominee?.relation)
        binding.fNomineeAllocatedPercentage.etText.setText(nominee?.allocated_percentage)

        binding?.fNomineeAllocatedPercentage?.llBody?.etText?.inputType =
            InputType.TYPE_CLASS_NUMBER

        imageUrl = nominee?.nominee_signature

        nominee?.date_of_birth?.let {
            binding?.fNomineeDob?.tvText?.setText(
                "" + DateConverter.changeDateFormateForShowing(
                    it
                )
            )
        }

        hasDisabilityData()?.let {
            SpinnerAdapter().setSpinner(
                binding?.fNomineeHasDisability?.spinner!!,
                context,
                it,
                nominee?.has_disability,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        hasDisabaility = any as SpinnerDataModel
                    }
                }
            )
        }
        binding?.fNomineeDob?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fNomineeDob?.tvText?.setText("" + it) }
                }
            })
        })


        commonRepo.getCommonData("/api/auth/marital-status/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fNomineeMaritalStatus?.spinner!!,
                            context,
                            list,
                            nominee?.marital_status_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    maritalStatus = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo.getCommonData("/api/auth/gender/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fNomineeGender?.spinner!!,
                            context,
                            list,
                            nominee?.gender_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    gender = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        context?.let {
            binding?.ivNomineeEmployee?.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_image_24)
                ).load(RetrofitInstance.BASE_URL + nominee?.nominee_signature)
                    .into(it1)
            }
        }


        binding?.ivNomineeEmployee?.setOnClickListener({
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap) {
                    imageFile = imgFile
                    binding?.ivNomineeEmployee?.setImageBitmap(bitmap)
                    //   Toast.makeText(context, "image", Toast.LENGTH_LONG).show()
                    Log.e("image", "dialog imageFile  : " + bitmap)
                }
            })
        })



        binding?.nomineeUpdate?.btnUpdate?.setOnClickListener({
            dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            if (imageFile != null) {
                imageFile?.let { it1 -> uploadImage(it1) }
            } else {
                uploadData()
            }
        })
    }

    fun getMapData(): java.util.HashMap<Any, Any?> {
        var dob =
            DateConverter.changeDateFormateForSending(binding.fNomineeDob?.tvText?.text.toString())
        var map = java.util.HashMap<Any, Any?>()
        if (nominee?.id == null) map.put("id", 0) else nominee?.id?.let { map.put("id", it) }
        map.put("employee_id", employee?.user?.employee_id)
        map.put("name", binding?.fNomineeName.etText.text.toString().trim())
        map.put(
            "date_of_birth",
            dob
        )
        map.put("relation", binding?.fNomineeRelation.etText.text.toString().trim())
        map.put("allocated_percentage", binding?.fNomineeAllocatedPercentage.etText.text.toString())
        maritalStatus?.id?.let {
            map.put("marital_status_id", it.toString())
        }
        gender?.id?.let {
            map.put("gender_id", it.toString())
        }
        hasDisabaility?.id?.let {
            map.put("has_disability", it.toString())
        }
        imageUrl?.let { map.put("nominee_signature", it) }

        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        var finalMap = java.util.HashMap<Any, Any?>()
        finalMap.put("nominees", arr)
        return finalMap
    }


    fun uploadData() {
        var employeeInfoEditCreateRepo =
            ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                .get(EmployeeInfoEditCreateViewModel::class.java)
        invisiableAllError(binding)
        key?.let {
            if (it.equals(StaticKey.EDIT)) {
                employeeInfoEditCreateRepo?.updateNomineeInfo(
                    getMapData()
                )?.observe(EmployeeInfoActivity.context!!, androidx.lifecycle.Observer { any ->
                    dialog?.dismiss()
                    Log.e("yousuf", "error : " + any)
                    showResponse(any)
                })
            } else {
                employeeInfoEditCreateRepo?.updateNomineeInfo(
                    getMapData()
                )?.observe(EmployeeInfoActivity.context!!, androidx.lifecycle.Observer { any ->
                    dialog?.dismiss()
                    Log.e("yousuf", "error : " + any)
                    showResponse(any)
                })
            }
        }
    }

    fun showResponse(any: Any) {
        if (any is String) {
            toast(EmployeeInfoActivity.context, any)
            MainActivity.selectedPosition = 18
            EmployeeInfoActivity.refreshEmployeeInfo()
            dialogCustome?.dismiss()
        } else if (any is ApiError) {
            //  Log.e("editcreatebasicinfo","edit create : error ${Gson().toJson(any.getError())}")
            // Log.e("editcreatebasicinfo","edit create : error "+any?.getError())
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
                                binding?.fNomineeName?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "nominees.0.name" -> {
                                binding?.fNomineeName?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.date_of_birth" -> {
                                binding?.fNomineeDob?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeDob?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.relation" -> {
                                binding?.fNomineeRelation?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeRelation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.allocated_percentage" -> {
                                binding?.fNomineeAllocatedPercentage?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeAllocatedPercentage?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "nominees.0.gender_id" -> {
                                binding?.fNomineeGender?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeGender?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.marital_status_id" -> {
                                binding?.fNomineeMaritalStatus?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeMaritalStatus?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.has_disability" -> {
                                binding?.fNomineeHasDisability?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeHasDisability?.tvError?.text =
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

    fun uploadImage(imageFile: File) {
        var profilePic: MultipartBody.Part?
        if (imageFile != null) {

            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
            profilePic =
                MultipartBody.Part.createFormData("filenames[]", "filenames[]", requestFile)

            val profile_photo: RequestBody =
                RequestBody.create(MediaType.parse("text/plain"), "profile_photo")

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

    fun hasDisabilityData(): List<SpinnerDataModel> {

        var rel = SpinnerDataModel()
        rel.apply {
            id = 1
            name = "Yes"
            name_bn = "হ্যাঁ"

        }
        var rel1 = SpinnerDataModel()
        rel1.apply {
            id = 0
            name = "No"
            name_bn = "না"


        }
        return arrayListOf(rel, rel1)
    }

    fun invisiableAllError(binding: DialogPersonalInfoBinding) {
        binding.fNomineeName.tvError.visibility =
            View.GONE

        binding.fNomineeDob.tvError.visibility =
            View.GONE

        binding.fNomineeRelation.tvError.visibility =
            View.GONE

        binding.fNomineeAllocatedPercentage.tvError.visibility =
            View.GONE
        binding.fNomineeMaritalStatus.tvError.visibility =
            View.GONE
        binding.fNomineeGender.tvError.visibility =
            View.GONE
        binding.fNomineeHasDisability.tvError.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}