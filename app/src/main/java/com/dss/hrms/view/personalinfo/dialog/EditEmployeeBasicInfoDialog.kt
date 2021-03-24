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
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DateConverter
import com.dss.hrms.util.DatePicker
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.*
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import kotlinx.android.synthetic.main.personel_information_edittext.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import javax.inject.Inject


class EditEmployeeBasicInfoDialog @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0

    var dialogCustome: Dialog? = null

    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    lateinit var key: String
    var bloodGroup: SpinnerDataModel? = null
    var religion: SpinnerDataModel? = null
    var gender: SpinnerDataModel? = null
    var disabilityType: SpinnerDataModel? = null
    var disabilityDegree: SpinnerDataModel? = null
    var maritalStatus: SpinnerDataModel? = null
    var hasDisability: SpinnerDataModel? = null
    var fileClickListener: FileClickListener? = null
    open var imageFile: File? = null
    var imageUrl: String? = null
    lateinit var employee: Employee
    var dialog: Dialog? = null


    fun showDialog(
        context: Context,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.employee = employeeProfileData?.employee!!
        this.context = context
        this.fileClickListener = fileClickListener
        this.key = key
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
        editBasicInfo(context, binding)
        dialogCustome?.show()

    }

    fun editBasicInfo(
        context: Context,
        binding: DialogPersonalInfoBinding?
    ) {

        binding?.fNid?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fTIN?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fPresentBasicSalary?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fPresentGrossSalary?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fPunchId?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fPhone?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.llBasicInfo?.visibility = View.VISIBLE
        binding?.fProfileId?.llBody?.visibility = View.GONE

        employee?.profile_id?.let { binding?.fProfileId?.etText?.setText("" + it) }
        employee?.nid_no?.let { binding?.fNid?.etText?.setText("" + it) }
        employee?.tin_no?.let { binding?.fTIN?.etText?.setText("" + it) }
        employee?.punch_id?.let { binding?.fPunchId?.etText?.setText("" + it) }
        employee?.present_basic_salary?.let { binding?.fPresentBasicSalary?.etText?.setText("" + it) }
        employee?.present_gross_salary?.let { binding?.fPresentGrossSalary?.etText?.setText("" + it) }
        employee?.name?.let { binding?.fNameEng?.etText?.setText("" + it) }
        employee?.name_bn?.let { binding?.fNameBangla?.etText?.setText("" + it) }
        employee?.fathers_name?.let { binding?.fFatherNameEng?.etText?.setText("" + it) }
        employee?.fathers_name_bn?.let { binding?.fFatherNameBangla?.etText?.setText("" + it) }
        employee?.mothers_name?.let { binding?.fMotherNameEng?.etText?.setText("" + it) }
        employee?.mothers_name_bn?.let { binding?.fMotherNameBangla?.etText?.setText("" + it) }
        employee?.user?.email?.let { binding?.fEmail?.etText?.setText("" + it) }
        employee?.date_of_birth?.let {
            binding?.fDOB?.tvText?.setText(
                "" + DateConverter.changeDateFormateForShowing(
                    it
                )
            )
        }
        employee?.user?.phone_number?.let { binding?.fPhone?.etText?.setText("" + it) }
        employee?.disabled_person_id?.let { binding?.fDisabledPersonId?.etText?.setText("" + it) }
        employee?.user?.username?.let { binding?.fUserName?.etText?.setText("" + it) }


        binding?.fNid?.llBody?.etText?.inputType = InputType.TYPE_CLASS_NUMBER

        context?.let {
            binding?.ivEmployee?.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_image_24)
                ).load(RetrofitInstance.BASE_URL + employeeProfileData.employee?.photo)
                    .into(it1)
            }
        }

        binding?.employee = employee
        binding?.hBasicInformation?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })



        commonRepo.getCommonData("/api/auth/gender/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fGender?.spinner!!,
                            context,
                            list,
                            employee.gender_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    gender = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo.getCommonData("/api/auth/blood-group/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fBloodGroup?.spinner!!,
                            context,
                            list,
                            employee.blood_group?.id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    bloodGroup = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        commonRepo.getCommonData("/api/auth/religion/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fReligion?.spinner!!,
                            context,
                            list,
                            employee.religion?.id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    religion = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })


        commonRepo.getCommonData("/api/auth/disability-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setDisabilityTypeSpinner(
                            binding?.fDisabilityType?.spinner!!,
                            context,
                            list,
                            employee.disability_type_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    disabilityType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })
        commonRepo.getCommonData("/api/auth/marital-status/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fMaritalStatus?.spinner!!,
                            context,
                            list,
                            employee.marital_status_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    maritalStatus = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo.getCommonData("/api/auth/disability-degree/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setDisabilityDegreeSpinner(
                            binding?.fDisabilityDegree?.spinner!!,
                            context,
                            list,
                            employee.disability_degree_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    disabilityDegree = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })



        hasDisabilityData()?.let {
            SpinnerAdapter().setSpinner(
                binding?.fDisability?.spinner!!,
                context,
                it,
                employee?.has_disability,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        hasDisability = any as SpinnerDataModel

                        showOrHideDisabilityView(hasDisability?.id)
                    }

                }
            )
        }
        binding?.fDOB?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fDOB?.tvText?.setText("" + it) }
                }
            })
        })



        binding?.ivEmployee?.setOnClickListener({
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap) {
                    imageFile = imgFile
                    binding?.ivEmployee?.setImageBitmap(bitmap)
                    //   Toast.makeText(context, "image", Toast.LENGTH_LONG).show()
                    Log.e("image", "dialog imageFile  : " + bitmap)
                }
            })
        })

        binding?.basicUpdate?.btnUpdate?.setOnClickListener({
            dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            if (imageFile != null) {
                imageFile?.let { it1 -> uploadImage(it1) }
            } else {
                uploadData()
            }
        })


    }

    fun showOrHideDisabilityView(id: Int?) {
        if (id == null || id == 0) {
            binding?.fDisabilityDegree?.llBody?.visibility = View.GONE
            binding?.fDisabledPersonId?.llBody?.visibility = View.GONE
            binding?.fDisabilityType?.llBody?.visibility = View.GONE
        } else {
            binding?.fDisabilityDegree?.llBody?.visibility = View.VISIBLE
            binding?.fDisabledPersonId?.llBody?.visibility = View.VISIBLE
            binding?.fDisabilityType?.llBody?.visibility = View.VISIBLE
        }
    }

    fun showResponse(any: Any) {
        if (any is String) {
            toast(EmployeeInfoActivity.context, any)
            MainActivity.selectedPosition = 0
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
                                binding?.fDOB?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fDOB?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "gender_id" -> {
                                binding?.fGender?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fGender?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "religion_id" -> {
                                binding?.fReligion?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fReligion?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "blood_group_id" -> {
                                binding?.fBloodGroup?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fBloodGroup?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "marital_status_id" -> {
                                binding?.fMaritalStatus?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fMaritalStatus?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "tin_no" -> {
                                binding?.fTIN?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fTIN?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "punch_id" -> {
                                binding?.fPunchId?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPunchId?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "present_basic_salary" -> {
                                binding?.fPresentBasicSalary?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPresentBasicSalary?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "present_gross_salary" -> {
                                binding?.fPresentGrossSalary?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPresentGrossSalary?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "fathers_name" -> {
                                binding?.fFatherNameEng?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fFatherNameEng?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "fathers_name_bn" -> {
                                binding?.fFatherNameBangla?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fFatherNameBangla?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "mothers_name" -> {
                                binding?.fMotherNameEng?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fMotherNameEng?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "mothers_name_bn" -> {
                                binding?.fMotherNameBangla?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fMotherNameBangla?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "email" -> {
                                binding?.fEmail?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fEmail?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "username" -> {
                                binding?.fUserName?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fUserName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "phone_number" -> {
                                binding?.fPhone?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fPhone?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "has_disability" -> {
                                binding?.fDisability?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fDisability?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "disability_type_id" -> {
                                binding?.fDisabilityType?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fDisabilityType?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "disability_degree_id" -> {
                                binding?.fDisabilityDegree?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fDisabilityDegree?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "disabled_person_id" -> {
                                binding?.fDisabledPersonId?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fDisabledPersonId?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nid_no" -> {
                                binding?.fNid?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNid?.tvError?.text =
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
        var date = DateConverter.changeDateFormateForSending(binding?.fDOB?.tvText?.text.toString())
        var map = HashMap<Any, Any?>()
        map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
        map.put("profile_id", binding?.fProfileId?.etText?.text.toString())
        map.put("name", binding?.fNameEng?.etText?.text.toString())
        map.put("name_bn", binding?.fNameBangla?.etText?.text.toString())
        Log.e("dob", "dob : ${date}")

        map.put("date_of_birth", date)
        map.put("fathers_name", binding?.fFatherNameEng?.etText?.text.toString())
        map.put("fathers_name_bn", binding?.fFatherNameBangla?.etText?.text.toString())
        map.put("mothers_name", binding?.fMotherNameEng?.etText?.text.toString())
        map.put("mothers_name_bn", binding?.fMotherNameBangla?.etText?.text.toString())
        //  map.put("gender_id", null)
        gender?.id?.let { map.put("gender_id", gender?.id) }
        map.put("blood_group_id", bloodGroup?.id)
        map.put("religion_id", religion?.id)
        map.put("employee_type_id", employee?.employee_type_id)
        map.put("has_disability", hasDisability?.id)
        map.put("disability_type_id", disabilityType?.id)
        map.put("disability_degree_id", disabilityDegree?.id)
        map.put("disabled_person_id", binding?.fDisabledPersonId?.etText?.text?.toString())
        maritalStatus?.id?.let { map.put("marital_status_id", maritalStatus?.id) }
        map.put("email", binding?.fEmail?.etText?.text.toString())
        imageUrl?.let { map.put("photo", it) }
        map.put("username", binding?.fUserName?.etText?.text?.toString())
        map.put("phone_number", binding?.fPhone?.etText?.text?.toString())
        map.put("nid_no", binding?.fNid?.etText?.text?.toString())
        map.put("tin_no", binding?.fTIN?.etText?.text?.toString())
        map.put("punch_id", binding?.fPunchId?.etText?.text?.toString())
        map.put("present_basic_salary", binding?.fPresentBasicSalary?.etText?.text?.toString())
        map.put("present_gross_salary", binding?.fPresentGrossSalary?.etText?.text?.toString())
        map.put("status", employee?.status)
        return map
    }


    fun uploadData() {
        var employeeInfoEditCreateRepo =
            ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                .get(EmployeeInfoEditCreateViewModel::class.java)
        invisiableAllError(binding)
        key?.let {
            if (it.equals(StaticKey.EDIT)) {
                employeeInfoEditCreateRepo?.updateBasicInfo(
                    employee?.id,
                    getMapData()
                )?.observe(EmployeeInfoActivity.context!!, androidx.lifecycle.Observer { any ->
                    dialog?.dismiss()
                    Log.e("yousuf", "error : " + any)
                    showResponse(any)
                })
            } else {
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

//    fun imagePath(imageFile: File, bitmap: Bitmap) {
//        this.imageFile = imageFile
//        binding?.ivEmployee?.setImageBitmap(bitmap)
//        //   Toast.makeText(context, "image", Toast.LENGTH_LONG).show()
//        Log.e("image", "dialog imageFile  : " + imageFile)
//
//        this.imageFile = imageFile
//
//    }

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
        binding?.fEmail?.tvError?.visibility = View.GONE

        binding?.fPhone?.tvError?.visibility = View.GONE
        binding?.fUserName?.tvError?.visibility = View.GONE
        binding?.fMaritalStatus?.tvError?.visibility = View.GONE
        binding?.fDisability?.tvError?.visibility = View.GONE
        binding?.fDisabilityType?.tvError?.visibility = View.GONE
        binding?.fDisabilityDegree?.tvError?.visibility = View.GONE
        binding?.fDisabledPersonId?.tvError?.visibility = View.GONE
        binding?.fNid?.tvError?.visibility = View.GONE

    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
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
}



