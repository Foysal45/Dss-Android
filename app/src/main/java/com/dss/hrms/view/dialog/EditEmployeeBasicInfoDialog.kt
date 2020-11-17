package com.dss.hrms.view.dialog


import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.model.Employee
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.DatePicker
import com.dss.hrms.util.FilePath
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.CommonDataValueListener
import com.dss.hrms.view.`interface`.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.`interface`.FileClickListener
import com.dss.hrms.view.`interface`.OnDateListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class EditEmployeeBasicInfoDialog {
    var dialogCustome: Dialog? = null
    var employee1: Employee? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var gender: SpinnerDataModel? = null
    var fileClickListener: FileClickListener? = null
    var imageFile: File? = null

    fun showDialog(context: Context, employee: Employee, fileClickListener: FileClickListener) {

        this.employee1 = employee1
        this.context = context
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
        editBasicInfo(context, binding, employee)
        dialogCustome?.show()

    }

    fun editBasicInfo(
        context: Context,
        binding: DialogPersonalInfoBinding?,
        employee: Employee?
    ) {
        binding?.llBasicInfo?.visibility = View.VISIBLE
        employee?.user?.employee_id?.let { binding?.fProfileId?.etText?.setText("" + it) }
        employee?.name?.let { binding?.fNameEng?.etText?.setText("" + it) }
        employee?.name_bn?.let { binding?.fNameBangla?.etText?.setText("" + it) }
        employee?.fathers_name?.let { binding?.fFatherNameEng?.etText?.setText("" + it) }
        employee?.fathers_name_bn?.let { binding?.fFatherNameBangla?.etText?.setText("" + it) }
        employee?.mothers_name?.let { binding?.fMotherNameEng?.etText?.setText("" + it) }
        employee?.mothers_name_bn?.let { binding?.fMotherNameBangla?.etText?.setText("" + it) }
        //  employee?.gender?.name?.let { binding.fGender.etText.setText("" + it) }
        employee?.date_of_birth?.let { binding?.fDOB?.tvText?.setText("" + it) }

        binding?.employee = employee
        binding?.hBasicInformation?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        binding?.basicUpdate?.btnUpdate?.setOnClickListener({
            Log.e("data", "binding data " + binding?.fNameEng?.etText?.toString())
        })




        CommonRepo(MainActivity.appContext).getCommonData2("/api/auth/gender",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fGender?.spinner!!,
                            context,
                            list,
                            2,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {

                                }

                            }
                        )
                    }
                }
            })
        binding?.fDOB?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fDOB?.tvText?.setText("" + it) }
                }
            })
        })



        binding?.ivEmployee?.setOnClickListener({
            fileClickListener?.onFileClick()
        })

//        binding?.honoursAwardBtnAddUpdate?.btnUpdate?.setOnClickListener({
//            var employeeInfoEditCreateRepo = ViewModelProviders.of(MainActivity.context!!)
//                .get(EmployeeInfoEditCreateViewModel::class.java)
//            invisiableAllError(binding)
//            var dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
//            key?.let {
//                if (it.equals(StaticKey.EDIT)) {
//                    employeeInfoEditCreateRepo?.updateHonoursAwardInfo(
//                        honoursAwards1?.id,
//                        getMapData()
//                    )
//                        ?.observe(
//                            EmployeeInfoActivity.context!!,
//                            Observer { any ->
//                                dialog?.dismiss()
//                                Log.e("yousuf", "error : " + Gson().toJson(any))
//                                showResponse(any)
//                            })
//                } else {
//                    employeeInfoEditCreateRepo?.addHonoursAwardInfo(getMapData())
//                        ?.observe(
//                            EmployeeInfoActivity.context!!,
//                            Observer { any ->
//                                dialog?.dismiss()
//                                Log.e("yousuf", "error : " + Gson().toJson(any))
//                                showResponse(any)
//                            })
//                }
//            }
//        })


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
                            "profile_id" -> {
                                binding?.fProfileId?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fProfileId?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name" -> {
                                binding?.fNameEng?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNameEng?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "name_bn" -> {
                                binding?.fNameBangla?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNameBangla?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "date_of_birth" -> {
                                binding?.fAwardDetailsDetailsBn?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDetailsDetailsBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "gender_id" -> {
                                binding?.fAwardDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "fathers_name" -> {
                                binding?.fAwardDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "fathers_name_bn" -> {
                                binding?.fAwardDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "mothers_name" -> {
                                binding?.fAwardDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fAwardDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "mothers_name_bn" -> {
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
        var map = HashMap<Any, Any?>()
        map.put("employee_id", MainActivity?.employee?.user?.employee_id)
        map.put("profile_id", binding?.fProfileId?.etText?.text.toString())
        map.put("name", binding?.fNameEng?.etText?.text.toString())
        map.put("name_bn", binding?.fNameBangla?.etText?.text.toString())
        map.put("date_of_birth", binding?.fDOB?.tvText?.text.toString())
        map.put("fathers_name", binding?.fFatherNameEng?.etText?.text.toString())
        map.put("fathers_name_bn", binding?.fFatherNameEng?.etText?.text.toString())
        map.put("mothers_name", binding?.fMotherNameEng?.etText?.text.toString())
        map.put("mothers_name_bn", binding?.fMotherNameBangla?.etText?.text.toString())
        map.put("gender_id", gender?.id)
        map.put("employee_type_id", employee1?.employee_type_id)
        map.put("has_disability", employee1?.has_disability)
        map.put("disability_type_id", employee1?.disability_type_id)
        map.put("disability_degree_id", employee1?.disability_degree_id)
        map.put("disabled_person_id", employee1?.disabled_person_id)
        map.put("marital_status_id", employee1?.marital_status_id)
        map.put("email", employee1?.user?.email)
        map.put("phone_number", employee1?.user?.phone_number)
        map.put("status", employee1?.status)
        return map
    }

    fun imagePath(imageFile: File, bitmap: Bitmap, fileUri: Uri) {
        this.imageFile = imageFile
        binding?.ivEmployee?.setImageBitmap(bitmap)
        Toast.makeText(context, "image", Toast.LENGTH_LONG).show()
        Log.e("image", "dialog imageFile  : " + imageFile)
        Log.e("image", "dialog fileUri : " + fileUri)
        Log.e("image", "dialog image : " + FilePath().getPath(context!!, fileUri))

        var profilePic: MultipartBody.Part? = null
        if (imageFile != null) {

            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
            profilePic =
                MultipartBody.Part.createFormData("filenames[]", "filenames[]", requestFile)

//            val requestFile: RequestBody = RequestBody.create(
//                "multipart/form-data",
//                imageFile
//            )
//            profilePic =
//                MultipartBody.Part.createFormData("filenames[]", imageFile.name, requestFile);
            var employeeInfoEditCreateRepo = ViewModelProviders.of(EmployeeInfoActivity.context!!)
                .get(EmployeeInfoEditCreateViewModel::class.java)
            employeeInfoEditCreateRepo?.uploadProfilePic(profilePic, imageFile.name)?.observe(
                EmployeeInfoActivity.context!!,
                androidx.lifecycle.Observer { any ->
                    Log.e("yousuf", "profile pic : " + any)
                    //  showResponse(any)
                })
        }

    }

    fun invisiableAllError(binding: DialogPersonalInfoBinding?) {
        binding?.fProfileId?.tvError?.visibility =
            View.GONE
        binding?.fNameEng?.tvError?.visibility =
            View.GONE
        binding?.fNameBangla?.tvError?.visibility =
            View.GONE

        binding?.fFatherNameEng?.tvError?.visibility =
            View.GONE

        binding?.fFatherNameBangla?.tvError?.visibility =
            View.GONE

        binding?.fMotherNameBangla?.tvError?.visibility =
            View.GONE

        binding?.fMotherNameEng?.tvError?.visibility =
            View.GONE

        binding?.fGender?.tvError?.visibility = View.GONE
        binding?.fDOB?.tvError?.visibility = View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}



