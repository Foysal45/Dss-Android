package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoEditBinding
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
import kotlinx.android.synthetic.main.personel_information_edittext.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import javax.inject.Inject


open class EditEmployeeBasicInfoDialog @Inject constructor() {

    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0
    private var isFreedomFighter: Boolean = false
    private var isPermanentConfirmation: Boolean = false
    private var isTemporaryRevenueAttachment: Boolean = false

    private var dialogCustom: Dialog? = null

    private var employeInfoPage: EmployeeInfoActivity? = null

    private var disabilityURL = ""
    private var freedomFighterUrl = ""
    private var permanentConfirmationUrl = ""
    private var temporaryRevenueAttachmentUrl = ""
    var binding: DialogPersonalInfoEditBinding? = null
    var context: Context? = null
    lateinit var key: String
    var bloodGroup: SpinnerDataModel? = null
    var religion: SpinnerDataModel? = null
    var gender: SpinnerDataModel? = null
    var disabilityType: SpinnerDataModel? = null
    var disabilityDegree: SpinnerDataModel? = null
    var employmentStatusType: SpinnerDataModel? = null
    var homeDistrict: SpinnerDataModel? = null
    var employeeType: SpinnerDataModel? = null
    var maritalStatus: SpinnerDataModel? = null
    var hasDisability: SpinnerDataModel? = null
    var roles: List<SpinnerDataModel>? = null
    var hasFreedomFighterQuota: SpinnerDataModel? = null
    var hasNotConfirmYet: SpinnerDataModel? = null
    var fileClickListener: FileClickListener? = null
    open var imageFile: File? = null
    private var imageUrl: String? = null
    lateinit var employee: Employee
    var dialog: Dialog? = null

    var jobType: SpinnerDataModel? = null
    var temporaryRevenueType: SpinnerDataModel? = null

    fun showDialog(
        model: Employee,
        context: FragmentActivity,
        fileClickListener: FileClickListener,
        key: String,
        activity: FragmentActivity
    ) {
        this.employee = model
        this.context = context
        this.fileClickListener = fileClickListener
        this.key = key
        this.employeInfoPage = activity as EmployeeInfoActivity
        dialogCustom = Dialog(context)
        dialogCustom?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info_edit,
            null,
            false
        )
        binding?.root?.let { dialogCustom?.setContentView(it) }
        val window: Window? = dialogCustom?.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        editBasicInfo(context, binding)
        dialogCustom?.show()


    }

    private fun editBasicInfo(
        context: Context,
        binding: DialogPersonalInfoEditBinding?
    ) {

        binding?.fNid?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fTIN?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fPresentBasicSalary?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fPresentGrossSalary?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fPunchId?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.fPhone?.etText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.llBasicInfo?.visibility = View.VISIBLE
        binding?.fProfileId?.llBody?.visibility = View.VISIBLE
        binding?.fPresentBasicSalary?.llBody?.visibility = View.GONE
        binding?.fPresentGrossSalary?.llBody?.visibility = View.GONE

        binding?.fNotConfirmYet?.llBody?.visibility =  View.GONE
        binding?.fNotConfirmYetAttachment?.visibility =  View.GONE
        binding?.temporaryRevenueType?.llBody?.visibility = View.GONE
        binding?.temporaryRevenueAttachment?.visibility = View.GONE

        employee.profile_id?.let { binding?.fProfileId?.etText?.setText("" + it) }
        employee.nid_no?.let { binding?.fNid?.etText?.setText("" + it) }
        employee.tin_no?.let { binding?.fTIN?.etText?.setText("" + it) }
        employee.punch_id?.let { binding?.fPunchId?.etText?.setText("" + it) }
        employee.present_basic_salary?.let { binding?.fPresentBasicSalary?.etText?.setText("" + it) }
        employee.present_gross_salary?.let { binding?.fPresentGrossSalary?.etText?.setText("" + it) }
        employee.name?.let { binding?.fNameEng?.etText?.setText("" + it) }
        employee.name_bn?.let { binding?.fNameBangla?.etText?.setText("" + it) }
        employee.fathers_name?.let { binding?.fFatherNameEng?.etText?.setText("" + it) }
        employee.fathers_name_bn?.let { binding?.fFatherNameBangla?.etText?.setText("" + it) }
        employee.mothers_name?.let { binding?.fMotherNameEng?.etText?.setText("" + it) }
        employee.mothers_name_bn?.let { binding?.fMotherNameBangla?.etText?.setText("" + it) }
        employee.user?.email?.let { binding?.fEmail?.etText?.setText("" + it) }


        employee.date_of_birth?.let {
            binding?.fDOB?.tvText?.setText(
                "" + DateConverter.changeDateFormateForShowing(
                    it
                )
            )
        }
        employee.job_joining_date?.let {
            binding?.fJobJoiningDate?.tvText?.setText(
                "" + DateConverter.changeDateFormateForShowing(
                    it
                )
            )
        }


        employee.prl_date?.let {
            binding?.fPRLDate?.tvText?.setText(
                "" + DateConverter.changeDateFormateForShowing(
                    it
                )
            )
        }
        employee.pension_date?.let {
            binding?.fPensionDate?.tvText?.setText(
                "" + DateConverter.changeDateFormateForShowing(
                    it
                )
            )
        }

        employee.employment_job_status?.status_date?.let {
            binding?.fEmploymentStatusDate?.tvText?.setText(
                "" + DateConverter.changeDateFormateForShowing(
                    it
                )
            )
        }

        if(employee.phone_number.isNullOrBlank()){
            employee.user?.phone_number?.let { binding?.fPhone?.etText?.setText("" + it) }
        }else {
            employee.phone_number?.let { binding?.fPhone?.etText?.setText("" + it) }
        }



        employee.disabled_person_id?.let { binding?.fDisabledPersonId?.etText?.setText("" + it) }
        employee.user?.username?.let { binding?.fUserName?.etText?.setText("" + it) }


        binding?.fNid?.llBody?.etText?.inputType = InputType.TYPE_CLASS_NUMBER

        context.let {
            binding?.ivEmployee?.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_image_24)
                ).load(RetrofitInstance.BASE_URL + employeeProfileData.employee?.photo)
                    .into(it1)
            }
        }

        binding?.employee = employee
        binding?.hBasicInformation?.tvClose?.setOnClickListener {
            dialogCustom?.dismiss()
        }



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

        commonRepo.getUserRole("/api/auth/employee/${employee.user?.employee_id}",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        roles = it
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
        commonRepo.getCommonData("/api/auth/employment-status-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fEmploymentStatusType?.spinner!!,
                            context,
                            list,
                            employee?.employment_job_status?.employment_status_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    employmentStatusType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo.getCommonData("/api/auth/employee-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setEmployeeTypeSpinner(
                            binding?.fEmployeeType?.spinner!!,
                            context,
                            list,
                            employee.employee_type_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    employeeType = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo.getAllDistrict(
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fHomeDistrict?.spinner!!,
                            context,
                            list,
                            employee.birth_place?.id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    homeDistrict = any as SpinnerDataModel
                                }
                            }
                        )
                    }
                }

            }
        )

//        if (  employee?.employee_job_type_revenue?.job_type?.id == 1){
//            binding?.fNotConfirmYet?.llBody?.visibility = View.GONE
//        }else{
//            binding?.fNotConfirmYet?.llBody?.visibility =  View.VISIBLE
//        }

        //Job Type full process
        commonRepo.getCommonData("/api/auth/job-type/list",
            object : CommonDataValueListener {
                override fun valueChange(jobTypeList: List<SpinnerDataModel>?) {
                    Log.d("temdata","temmp : " + jobTypeList?.first()?.id)
                    jobTypeList?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fJobType?.spinner!!,
                            context,
                            jobTypeList,
                            0,//employee.employee_job_type_revenue?.job_type?.id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    jobType = any as SpinnerDataModel

                                    when (jobType?.id) {
                                        1 -> {

                                            binding.temporaryRevenueType.llBody.visibility = View.VISIBLE
                                            binding.fNotConfirmYet.llBody.visibility = View.GONE
                                            binding.fNotConfirmYetAttachment.visibility=View.GONE
                                        }
                                        2 -> {
                                            binding.fNotConfirmYet.llBody.visibility =  View.VISIBLE
                                            binding.temporaryRevenueType.llBody.visibility = View.GONE
                                        }else ->{
                                           binding.temporaryRevenueType.llBody.visibility = View.GONE
                                        }

                                    }
                                }

                            }
                        )
                    }
                }
            })

         //Temporary Revenue Type
        commonRepo.getCommonData("/api/auth/temporary-revenue-type/list",
            object : CommonDataValueListener {
                override fun valueChange(temporaryRevenueTypeList: List<SpinnerDataModel>?) {
                    temporaryRevenueTypeList?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.temporaryRevenueType?.spinner!!,
                            context,
                            temporaryRevenueTypeList,
                            null,//employee?.employee_job_type_revenue?.temporary_revenue_type_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    temporaryRevenueType = any as SpinnerDataModel
                                    binding.fNotConfirmYet.llBody.visibility =  View.GONE
                                    when (temporaryRevenueType?.id) {
                                         //here Temporary Revenue type id == 2 means Project and 1 means Direct Recruit
                                        2-> {
                                            binding.fNotConfirmYet.llBody.visibility =  View.VISIBLE
                                            binding.fNotConfirmYet.tvTitle.text = "Not Regularized Yet ?"
                                            binding.temporaryRevenueAttachment.visibility = View.VISIBLE

                                        }
                                        1 -> {

                                            binding.fNotConfirmYet.llBody.visibility =  View.VISIBLE
                                            binding.temporaryRevenueAttachment.visibility = View.GONE
                                            binding.fNotConfirmYet.tvTitle.text = "Not Confirmed Yet ?"

                                        }

                                    }
                                }

                            }
                        )
                    }
                }
            })

        //checkNotConfirmOrNotRegularizedYet
        SpinnerAdapter().setSpinner(
            binding?.fNotConfirmYet!!.spinner, context,
            checkNotConfirmOrNotRegularizedYet(), null,//if (employee.employee_job_type_revenue?.job_type?.id == 1) 1 else 0,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    hasNotConfirmYet = any as SpinnerDataModel

                  if (jobType?.id == 2 && hasNotConfirmYet?.id == 0) {

                        binding.fNotConfirmYetAttachment.visibility = View.VISIBLE
                        binding.fPermanentConfirmationDate.tvTitle.text = "Date of Confirmation in Permanent Revenue"

                    }
                    else if (temporaryRevenueType?.id == 2 && hasNotConfirmYet?.id == 0) {

                        binding.fNotConfirmYetAttachment.visibility = View.VISIBLE
                        binding.fPermanentConfirmationDate.tvTitle.text = "Regularization Date"

                    } else if(temporaryRevenueType?.id == 1 && hasNotConfirmYet!!.id == 0) {

                        binding.fNotConfirmYetAttachment.visibility = View.VISIBLE

                        binding.fPermanentConfirmationDate.tvTitle.text = "Confirmation Date"
                    }else{
                      binding.fNotConfirmYetAttachment.visibility = View.GONE
                  }
                }
            }
        )


        checkHasDisability().let {
            SpinnerAdapter().setSpinner(
                binding.fDisability.spinner,
                context,
                it,
                if (employee.has_disability == true) 1 else 0,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        hasDisability = any as SpinnerDataModel

                        showOrHideDisabilityView(hasDisability?.id)
                    }

                }
            )
        }

     /*   checkNotConfirmOrNotRegularizedYet().let {
            SpinnerAdapter().setSpinner(binding?.fNotConfirmYet?.spinner!!, context, it, 0, //if (employee.employee_job_type_revenue?.job_type?.id == 1) 1 else 0,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        hasNotConfirmYet = any as SpinnerDataModel
                        //app:title="@{@string/regularization_date}"

                            if (temporaryRevenueType?.id == 2 && hasNotConfirmYet!!.id == 0) {

                                binding.fNotConfirmYetAttachment.visibility = View.VISIBLE
                                binding.fPermanentConfirmationDate.tvTitle.text = "Regularization Date"

                            } else if(temporaryRevenueType?.id == 1 && hasNotConfirmYet!!.id == 0) {

                                binding.fNotConfirmYetAttachment.visibility = View.VISIBLE

                                binding.fPermanentConfirmationDate.tvTitle.text = "Confirmation Date"
                            }


                    }
                }
            )
        }*/

        checkHasDisability().let {
            SpinnerAdapter().setSpinner(
                binding.fFreedomFighterQuota.spinner,
                context,
                it,
                if (employee.has_freedom_fighter_quota == true) 1 else 0,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        hasFreedomFighterQuota = any as SpinnerDataModel
                        try {

                            if (hasFreedomFighterQuota!!.id == 0) {

                                binding.llFreedomFighterAttachment.visibility = View.GONE

                            } else {

                                binding.llFreedomFighterAttachment.visibility = View.VISIBLE
                            }
                        } catch (_: Exception) {
                        }

                    }
                }
            )
        }

        binding.fDOB.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) { date.let { binding.fDOB.tvText.text = "" + it }
                }
            })
        }

        binding.fJobJoiningDate.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) { date.let { binding.fJobJoiningDate.tvText.text = "" + it }
                }
            })
        }

       /* binding?.fPRLDate?.tvText?.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fPRLDate?.tvText?.setText("" + it) }
                }
            })
        }*/

       /* binding?.fPensionDate?.tvText?.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.fPensionDate?.tvText?.setText("" + it) }
                }
            })
        }*/

        binding.fEmploymentStatusDate.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) { date.let { binding.fEmploymentStatusDate.tvText.text = "" + it }
                }
            })
        }



        binding.ivEmployee.setOnClickListener {
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    imageFile = imgFile

                    Glide.with(context)
                        .asBitmap()
                        .centerCrop()
                        .load(bitmap)
                        .into(binding.ivEmployee)

                    binding.ivEmployee?.setImageBitmap(bitmap)
                    //   Toast.makeText(context, "image", Toast.LENGTH_LONG).show()
                    Log.e("image", "dialog imageFile  : $bitmap")
                }
            })
        }

        // Basic Information Update Click for Updating the data
        binding.basicUpdate.btnUpdate.setOnClickListener {

            if (hasFreedomFighterQuota?.id == 1 && freedomFighterUrl.isEmpty()) {
                Toast.makeText(context, context.getString(R.string.error_freedom_fighter), Toast.LENGTH_LONG).show()

                return@setOnClickListener
            }
            if (hasDisability?.id == 1 && disabilityURL.isEmpty()) {
                Toast.makeText(context, context.getString(R.string.error_disaiblity_doc), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            if (imageFile != null) {

                // Get length of file in bytes

                // Get length of file in bytes
                val fileSizeInBytes: Long = imageFile!!.length()
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                val fileSizeInKB = fileSizeInBytes / 1024
                // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                //val fileSizeInMB = fileSizeInKB / 1024

                if (fileSizeInKB > (2 * 1024)) {
                    Toast.makeText(context, context.getString(R.string.error_file_size), Toast.LENGTH_LONG).show()
                    dialog?.dismiss()
                } else imageFile?.let { it1 -> uploadImage(it1) }

            } else {
                uploadData()
            }
        }



        employee.employee_job_type_revenue?.permanent_confirmation_date?.let {
            binding.fPermanentConfirmationDate.tvText.text = "" + DateConverter.changeDateFormateForShowing(it)
        }

        binding.fPermanentConfirmationDate.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) { date.let { binding.fPermanentConfirmationDate.tvText.text = "" + it }
                }
            })
        }


        // temporary revenue transfer date
        employee.employee_job_type_revenue?.temporary_revenue_transfer_date.let {
            binding.fDateOfTransferTemporaryRevenueDate.tvText.text = "" + DateConverter.changeDateFormateForShowing(it)
        }


        binding.fDateOfTransferTemporaryRevenueDate.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener { override fun onDate(date: String)
            { date.let { binding.fDateOfTransferTemporaryRevenueDate.tvText.text = "" + it } }
            })
        }



        //  Temporary Revenue Attachment file path
        if (employee.employee_job_type_revenue?.temporary_revenue_transfer_attachment.toString()
                .toLowerCase() != "null" || !employee.employee_job_type_revenue?.temporary_revenue_transfer_attachment.isNullOrEmpty()
        ) {
            binding.tvTemporaryRevenueAttachment.text = context.getString(R.string.attachment) + "\n" + employee.employee_job_type_revenue?.temporary_revenue_transfer_attachment
        }

        binding.llTemporaryRevenueAttachment.setOnClickListener {
            isTemporaryRevenueAttachment = true
            employeInfoPage?.galleryButtonClicked()
        }


            //  FreedomFighter Attachment file path
        if (employee.freedom_fighter_document_path.toString()
                .toLowerCase() != "null" || !employee.freedom_fighter_document_path.isNullOrEmpty()
        ) {
            binding.tvFreedomFighterAttachment.text = context.getString(R.string.attachment) + "\n" + employee.freedom_fighter_document_path
        }

        binding.llFreedomFighterAttachment.setOnClickListener {
            isFreedomFighter = true
            employeInfoPage?.galleryButtonClicked()
        }



        //  DiasAbility Attachment file path
        if (employee.disability_document_path.toString()
                .toLowerCase() != "null" || !employee.disability_document_path.isNullOrEmpty()
        ) {
            binding.tvAttachment.text = context.getString(R.string.attachment) + "\n" + employee.freedom_fighter_document_path
        }

        binding.llDiasabiltyAttachment.setOnClickListener {
            isFreedomFighter = false
            employeInfoPage?.galleryButtonClicked()

        }


        //  Permanent Confirmation Attachment file path
        if (employee.employee_job_type_revenue?.permanent_confirmation_attachment.toString()
                .toLowerCase() != "null" || !employee.employee_job_type_revenue?.permanent_confirmation_attachment.isNullOrEmpty()
        ) {
            binding.tvPermanentAttachment.text = context.getString(R.string.attachment) + "\n" + employee.employee_job_type_revenue?.permanent_confirmation_attachment
        }

        binding.llPermanentConfirmationAttachment.setOnClickListener {
            isPermanentConfirmation = true
            employeInfoPage?.galleryButtonClicked()

        }

    }



    fun showOrHideDisabilityView(id: Int?) {
        if (id == null || id == 0) {
            binding?.llDiasabiltyAttachment?.visibility = View.GONE
            binding?.fDisabilityDegree?.llBody?.visibility = View.GONE
            binding?.fDisabledPersonId?.llBody?.visibility = View.GONE
            binding?.fDisabilityType?.llBody?.visibility = View.GONE
        } else {
            binding?.llDiasabiltyAttachment?.visibility = View.VISIBLE
            binding?.fDisabilityDegree?.llBody?.visibility = View.VISIBLE
            binding?.fDisabledPersonId?.llBody?.visibility = View.VISIBLE
            binding?.fDisabilityType?.llBody?.visibility = View.VISIBLE
        }
    }

    //function for submitted update data
    fun uploadData() {
        val employeeInfoEditCreateRepo = ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)[EmployeeInfoEditCreateViewModel::class.java]
        invisibleAllError(binding)
        key.let {
            if (it == StaticKey.EDIT) {
                employeeInfoEditCreateRepo.updateBasicInfo(employee.id, getMapData())?.observe(EmployeeInfoActivity.context!!, androidx.lifecycle.Observer { any ->
                    dialog?.dismiss()
                    Log.e("yousuf", "error : $any")
                    showResponse(any)
                })
            }
        }
    }


    fun getMapData(): HashMap<Any, Any?> {
        val rolesList = arrayListOf<Int?>()
        roles?.forEach { element ->
            rolesList.add(element.id)
        }


        val date = DateConverter.changeDateFormateForSending(binding?.fDOB?.tvText?.text.toString())
        val jobJoiningDate = DateConverter.changeDateFormateForSending(binding?.fJobJoiningDate?.tvText?.text.toString())
        val pRLDate = DateConverter.changeDateFormateForSending(binding?.fPRLDate?.tvText?.text.toString())
        val pensionDate = DateConverter.changeDateFormateForSending(binding?.fPensionDate?.tvText?.text.toString())
        val employeeStatusTypeDate = DateConverter.changeDateFormateForSending(binding?.fEmploymentStatusDate?.tvText?.text.toString())
        val permanentRevenueConfirmationDate = DateConverter.changeDateFormateForSending(binding?.fPermanentConfirmationDate?.tvText?.text?.toString())
        val directRecruitConfirmationDate = DateConverter.changeDateFormateForSending(binding?.fPermanentConfirmationDate?.tvText?.text?.toString())
        val temporaryRevenueTransferDate = DateConverter.changeDateFormateForSending(binding?.fDateOfTransferTemporaryRevenueDate?.tvText?.text.toString())

        val map = HashMap<Any, Any?>()

        map["employee_id"] = employeeProfileData.employee?.user?.employee_id
        map["role"] = rolesList
        map["job_joining_date"] = employeeProfileData.employee?.job_joining_date
        map["profile_id"] = binding?.fProfileId?.etText?.text.toString()
        map["name"] = binding?.fNameEng?.etText?.text.toString()
        map["name_bn"] = binding?.fNameBangla?.etText?.text.toString()
        Log.e("dob", "dob : $date")

        map["date_of_birth"] = date
        map["status_date"] = employeeStatusTypeDate
        map["fathers_name"] = binding?.fFatherNameEng?.etText?.text.toString()
        map["fathers_name_bn"] = binding?.fFatherNameBangla?.etText?.text.toString()
        map["mothers_name"] = binding?.fMotherNameEng?.etText?.text.toString()
        map["mothers_name_bn"] = binding?.fMotherNameBangla?.etText?.text.toString()
//        try{
//            if (em?.isPendingData == true) {
//                var a = foreigntraining!!.parent_id
//                map.put("parent_id", a)
//            }
//        }catch (Ex : java.lang.Exception){
//
//        }
        //  map.put("gender_id", null)
        gender?.id?.let { map.put("gender_id", gender?.id) }
        map["blood_group_id"] = bloodGroup?.id
        map["religion_id"] = religion?.id
        map["employee_type_id"] = employee.employee_type_id
        map["has_disability"] = if (hasDisability?.id == 1) true else false
        homeDistrict?.id?.let { map.put("employee_type_id", it) }
        employeeType?.id?.let { map.put("birth_place_id", it) }
        employmentStatusType?.id?.let { map.put("employment_status_id", it) }
     //   hasFreedomFighterQuota?.id?.let { map.put("", it) }
        map["has_freedom_fighter_quota"] = if (hasFreedomFighterQuota?.id == 1) true else false
        map["disability_type_id"] = disabilityType?.id
        map["disability_degree_id"] = disabilityDegree?.id
        map["disabled_person_id"] = binding?.fDisabledPersonId?.etText?.text?.toString()
        maritalStatus?.id?.let { map.put("marital_status_id", maritalStatus?.id) }
        map["email"] = binding?.fEmail?.etText?.text.toString()
        imageUrl?.let { map.put("photo", it) }
        map["username"] = binding?.fUserName?.etText?.text?.toString()
        map["phone_number"] = binding?.fPhone?.etText?.text?.toString()
        map["nid_no"] = binding?.fNid?.etText?.text?.toString()
        map["tin_no"] = binding?.fTIN?.etText?.text?.toString()
        map["punch_id"] = binding?.fPunchId?.etText?.text?.toString()
        map["present_basic_salary"] = binding?.fPresentBasicSalary?.etText?.text?.toString()
        map["present_gross_salary"] = binding?.fPresentGrossSalary?.etText?.text?.toString()
        map["job_joining_date"] = jobJoiningDate

        map["job_type_id"] = jobType?.id
        map["is_permanent_confirmation"] = hasNotConfirmYet?.id
        map["permanent_confirmation_date"] = permanentRevenueConfirmationDate
        map["permanent_confirmation_attachment"] = permanentConfirmationUrl


        map["temporary_revenue_type_id"] = temporaryRevenueType?.id
        map["temporary_revenue_transfer_date"] = temporaryRevenueTransferDate
        map["temporary_revenue_transfer_attachment"] = temporaryRevenueAttachmentUrl

        map["is_regular_confirmation"] = hasNotConfirmYet?.id
        map["regularization_date"] = permanentRevenueConfirmationDate
        map["regularization_attachment"] = permanentConfirmationUrl

        map["direct_confirmation_date"] = directRecruitConfirmationDate
        map["direct_confirmation_attachment"] = permanentConfirmationUrl


        map["prl_date"] = pRLDate
        map["pension_date"] = pensionDate

        map["status"] = employee.status
        // adding document path
        map["freedom_fighter_document_path"] = freedomFighterUrl
        map["disability_document_path"] = disabilityURL

        Log.d("TAG", "requestDataBasicInfo: ${map.toString()}")
        return map

    }


    @Suppress("UNCHECKED_CAST")
    fun showResponse(any: Any) {
        Log.d("testBasicInfo", "response" + (any as ApiError).getError().first().getMessage() + " "+  (any).toString())

        when (any) {
            is String -> {
                toast(EmployeeInfoActivity.context, "" + context?.getString(R.string.updated))
                MainActivity.selectedPosition = 0
                EmployeeInfoActivity.refreshEmployeeInfo()
                dialogCustom?.dismiss()
            }
            is ApiError -> {

                try {
                    if (any.getError().isEmpty()) {
                        toast(EmployeeInfoActivity.context, any.getMessage())
                        Log.d("ok", "error")
                    } else {
                        for (n in any.getError().indices) {
                            val error = any.getError()[n].getField()
                            val message = any.getError()[n].getMessage()
                            Log.d("errorBasicInfo", "error:  " +error +"    message: " +message)
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
                                "employee_type_id" -> {
                                    binding?.fNid?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fNid?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "employment_status_id" -> {
                                    binding?.fEmploymentStatusType?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fEmploymentStatusType?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "has_freedom_fighter_quota" -> {
                                    binding?.fFreedomFighterQuota?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fFreedomFighterQuota?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    toast(EmployeeInfoActivity.context, e.toString())
                }


            }
            is Throwable -> {
                toast(EmployeeInfoActivity.context, any.toString())
            }
            else -> {
                EmployeeInfoActivity?.context?.getString(R.string.failed)?.let {
                    toast(
                        EmployeeInfoActivity.context,
                        it
                    )
                }
            }
        }
    }



    fun uploadImage(imageFile: File) {
        val profilePic: MultipartBody.Part?
        if (imageFile != null) {

            val requestFile: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), imageFile)
            profilePic =
                MultipartBody.Part.createFormData("filenames[]", "${imageFile.name}", requestFile)

            val profile_photo: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), "profilto")
            val employeeInfoEditCreateRepo =
                ViewModelProviders.of(EmployeeInfoActivity.context!!, viewModelProviderFactory)[EmployeeInfoEditCreateViewModel::class.java]
            employeeInfoEditCreateRepo.uploadProfilePic(profilePic, imageFile.name, profile_photo)
                ?.observe(
                    EmployeeInfoActivity.context!!,
                    androidx.lifecycle.Observer { any ->
                        Log.e("yousuf", "profile pic : $any")
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
//        this.imageFile = imageFile
//    }

    private fun invisibleAllError(binding: DialogPersonalInfoEditBinding?) {
        binding?.fProfileId?.tvError?.visibility = View.GONE
        binding?.fNameEng?.tvError?.visibility = View.GONE
        binding?.fNameBangla?.tvError?.visibility = View.GONE
        binding?.fFatherNameEng?.tvError?.visibility = View.GONE
        binding?.fFatherNameBangla?.tvError?.visibility = View.GONE
        binding?.fMotherNameBangla?.tvError?.visibility = View.GONE
        binding?.fMotherNameEng?.tvError?.visibility = View.GONE
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
        binding?.fHomeDistrict?.tvError?.visibility = View.GONE
        binding?.fEmployeeType?.tvError?.visibility = View.GONE
        binding?.fJobJoiningDate?.tvError?.visibility = View.GONE
        binding?.fPRLDate?.tvError?.visibility = View.GONE
        binding?.fPensionDate?.tvError?.visibility = View.GONE
        binding?.fJobType?.tvError?.visibility = View.GONE

        binding?.fEmploymentStatusType?.tvError?.visibility = View.GONE
        binding?.fFreedomFighterQuota?.tvError?.visibility = View.GONE

    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
    }


    private fun checkNotConfirmOrNotRegularizedYet(): List<SpinnerDataModel> {

        val notConfirmOrNotRegularizedYes = SpinnerDataModel()
        notConfirmOrNotRegularizedYes.apply {
            id = 1
            name = "Yes"
            name_bn = "হ্যাঁ"

        }
        val notConfirmOrNotRegularizedNo = SpinnerDataModel()
        notConfirmOrNotRegularizedNo.apply {
            id = 0
            name = "No"
            name_bn = "না"


        }
        return arrayListOf(notConfirmOrNotRegularizedYes, notConfirmOrNotRegularizedNo)
    }


    private fun checkHasDisability(): List<SpinnerDataModel> {

        val hasDisabilityYes = SpinnerDataModel()
        hasDisabilityYes.apply {
            id = 1
            name = "Yes"
            name_bn = "হ্যাঁ"

        }
        val hasDisabilityNo = SpinnerDataModel()
        hasDisabilityNo.apply {
            id = 0
            name = "No"
            name_bn = "না"

        }
        return arrayListOf(hasDisabilityYes, hasDisabilityNo)
    }


    //Upload Attachment
    fun uploadFile(filePath: String) {
        val dialogue = ProgressDialog(employeInfoPage)
        dialogue.setMessage("uploading...")
        dialogue.setCancelable(false)
        dialogue.show()

        val file = File(filePath)
        val profilePic: MultipartBody.Part?

        var filePart: MultipartBody.Part?

        val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        profilePic = MultipartBody.Part.createFormData("filenames[]", file.name, requestFile)

        val profile_photo: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "profile_ph")

        val employeeInfoEditCreateRepo =
            ViewModelProviders.of(EmployeeInfoActivity.context!!, viewModelProviderFactory)[EmployeeInfoEditCreateViewModel::class.java]
        employeeInfoEditCreateRepo.uploadProfilePic(
            profilePic,
            file.name,
            profile_photo
        )
            ?.observe(
                EmployeeInfoActivity.context!!,
                androidx.lifecycle.Observer { any ->
                    Log.e("yousuf", "profile pic : $any")
                    //  showResponse(any)
                    dialogue.dismiss()
                    if (any != null) {
                        val fileUrl = any as String
                        Log.d("TESTUPLOAD", "uploadFile: $fileUrl ")
                        if (isFreedomFighter) {
                            binding?.tvllFreedomFighterAttachmentFileName?.text = file.name
                            freedomFighterUrl = fileUrl
                        }else if (isPermanentConfirmation) {
                            binding?.tvllPermanentAttachmentFileName?.text = file.name
                            permanentConfirmationUrl = fileUrl
                        }
                        else if (isTemporaryRevenueAttachment) {
                            binding?.tvllTemporaryRevenueAttachmentFileName?.text = file.name
                            temporaryRevenueAttachmentUrl = fileUrl
                        }
                        else {
                            binding?.tvllDiasabiltyAttachmentFileName?.text = file.name
                            disabilityURL = fileUrl

                        }

                        Toast.makeText(context, "Message : File Upload Done !!!", Toast.LENGTH_LONG).show()

                    } else {

                        Toast.makeText(context, "Message : File Upload Failed !!!", Toast.LENGTH_LONG).show()
                    }

                })


    }


}



