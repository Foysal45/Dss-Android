package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
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
import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.Office
import com.dss.hrms.model.Paysacle
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.ConvertNumber
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.DateConverter
import com.dss.hrms.util.DatePicker
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.*
import com.dss.hrms.view.dialog.OfficeSearchingDialog
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.spinner.CommonSpinnerAdapter
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class EditJobJoiningInformation @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var officeSearchingDialog: OfficeSearchingDialog

    lateinit var utilViewmodel: UtilViewModel
    var officeList = arrayListOf<Office>()
    var mainOfficeList: List<Office>? = null

    var position: Int? = 0

    var dialogCustome: Dialog? = null
    var payScale: Paysacle? = null
    var jobjoining: Employee.Jobjoinings? = null

    lateinit var binding: DialogPersonalInfoBinding
    var office: Office? = null
    var desingNation: SpinnerDataModel? = null
    var designation: SpinnerDataModel? = null
    var serviceParticulars: SpinnerDataModel? = null
    var headOfficeBranches: HeadOfficeDepartmentApiResponse.HeadOfficeBranch? = null

    var additionalDesingNation: SpinnerDataModel? = null
    var department: SpinnerDataModel? = null
    var jobType: SpinnerDataModel? = null
    var currentJob: SpinnerDataModel? = null
    var currentOffice: SpinnerDataModel? = null
    var currentOfficeType: SpinnerDataModel? = null
    var _class: SpinnerDataModel? = null
    var grade: SpinnerDataModel? = null
    lateinit var context: Context
    var empolyInfoPage: EmployeeInfoActivity? = null
    private var isServiceParticularsAttachment: Boolean = false
    var serviceParticularsAttachmentUrl = ""
    var fileClickListener: FileClickListener? = null
    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null

    fun showDialog(
        context: Context,
        position: Int?,
        fileClickListener: FileClickListener,
        utilViewmodel: UtilViewModel
    ) {

        this.position = position
        this.jobjoining = position?.let { employeeProfileData?.employee?.jobjoinings?.get(it) }
        this.utilViewmodel = utilViewmodel
        this.context = context
        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info,
            null,
            false
        )
        this.empolyInfoPage = context as EmployeeInfoActivity
        this.fileClickListener = fileClickListener
        dialogCustome?.setContentView(binding.root)
        val window: Window? = dialogCustome?.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateJobjoiningInfo(context, binding)
        dialogCustome?.show()
    }

    fun updateJobjoiningInfo(
        context: Context,
        binding: DialogPersonalInfoBinding
    ) {
        binding.llJobjoningInfo.visibility = View.VISIBLE
        binding.hJobJoiningInformation.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }


        //for Office Type
        Log.d("office_type ",""+employeeProfileData.employee?.jobjoinings?.get(0)?.office_type)
        if (employeeProfileData.employee?.jobjoinings?.get(0)?.office_type == "head_office")
        {
            currentOfficeType?.id = 1
            loadHeadOfficeDepartment()
        } else {
            currentOfficeType?.id = 4
            setDivision()

        }

        //for Joining Date
        binding.fJobJoiningJoiningDate.tvText.text = jobjoining?.joining_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        }

        //setting joining Date and Release Date according the employeementstatus?.id
        val idForJoiningAndReleaseDate = jobjoining?.employeementstatus?.id
        when(idForJoiningAndReleaseDate){
            1->{
                binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_effective_date}}")
                binding.fJobJoiningEditReleaseDateFrom.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_release_date}")
            }
            2->{
                binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_effective_date}}")
                binding.fJobJoiningEditReleaseDateFrom.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_release_date}")
            }
            4->{
                binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${jobjoining?.employeementstatus?.name}"
                binding.fJobJoiningEditDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_effective_date}}")
                binding.fJobJoiningEditReleaseDateFrom.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_release_date}")
            }
            5->{
                //binding.fJobJoiningWhereToLien.llBody.visibility = View.GONE
                binding.fJobJoiningEditDateOf.tvTitle.text = "Suspension Date"
                binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Released Date from Suspension"
                binding.fJobJoiningEditDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_effective_date}")
                binding.fJobJoiningEditReleaseDateFrom.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoining?.employment_status_release_date}")
            }
        }



        // head office department start from
        binding.fDepartmentWiseSubSection.llBody.visibility = View.GONE

        commonRepo.getCommonData2("/api/auth/employee-class",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditClass.spinner,
                            context,
                            list,
                            jobjoining?.employee_class_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    _class = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        // load Other Service Particulars start from
        binding.fWhereToParticular.llBody.visibility = View.GONE
        binding.fAttachmentDesignation.llBody.visibility = View.GONE
        binding.llOffice.visibility = View.GONE

        commonRepo.getCommonData("/api/auth/employment-status-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                       Log.e("Particulars", "Particulars message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditOtherServiceParticulars.spinner,
                            context, list,
                            null,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                 //   _class = any as SpinnerDataModel
                                    serviceParticulars = any as SpinnerDataModel
                                    if (serviceParticulars?.id != null){
                                               when(serviceParticulars?.id) {
                                        1, 2 -> {
                                            binding.fWhereToParticular.llBody.visibility =
                                                View.VISIBLE
                                            binding.fWhereToParticular.title =
                                                "Where to ${serviceParticulars?.name}"

                                            binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${serviceParticulars?.name}"
                                            binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${serviceParticulars?.name}"
                                            binding.fAttachmentDesignation.llBody.visibility = View.GONE
                                            binding.llOffice.visibility = View.GONE
                                        }

                                        3, 4 -> {
                                            binding.fWhereToParticular.llBody.visibility = View.GONE
                                            binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${serviceParticulars?.name}"
                                            binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${serviceParticulars?.name}"
                                            binding.fAttachmentDesignation.llBody.visibility = View.VISIBLE
                                            binding.llOffice.visibility = View.VISIBLE

                                        }

                                        5 -> {
                                            binding.fWhereToParticular.llBody.visibility = View.GONE
                                            binding.fJobJoiningEditDateOf.tvTitle.text = "Suspension Date"
                                            binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Released Date from Suspension"
                                            binding.fAttachmentDesignation.llBody.visibility = View.GONE
                                            binding.llOffice.visibility = View.GONE

                                        }
                                      }
                                    }
                                }

                            }
                        )
                    }
                }
            })

        // Other Service Particulars Attachment common for all

        if (jobjoining?.employment_status_attachment.toString()
                .toLowerCase() != "null" || jobjoining?.employment_status_attachment.isNullOrEmpty()
        ) {
            binding?.tvParticularAttachment?.text =
                context.getString(R.string.attachment) + "\n" +
                        jobjoining?.employment_status_attachment
        }

        if (jobjoining?.employment_status_attachment.toString()
                .toLowerCase() != "null" || !jobjoining?.employment_status_attachment.isNullOrEmpty()
        ) {
            binding?.tvParticularAttachment?.text =
                context.getString(R.string.attachment) + "\n" +
                        jobjoining?.employment_status_attachment
        }


        binding.fParticularAttachment.setOnClickListener {
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding.tvParticularAttachment.text =
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
            }
            )
        }


        // attachment office and attachment designation

        binding.ivSearch.setOnClickListener {
            officeSearchingDialog.showOfficeSearchDialog(
                context,
                utilViewmodel,
                object : OfficeDataValueListener {
                    override fun valueChange(officeList: List<Office>?) {
                        mainOfficeList = officeList
                        setOffice(context, binding)
                    }
                })
        }

        //OfficeList - Current Office
    /*    commonRepo.getOffice("/api/auth/office/list/basic",
            object : OfficeDataValueListener {
                override fun valueChange(spinnerDataModel: List<Office>?) {
                    spinnerDataModel.let {
                        SpinnerAdapter().setOfficeSpinner(
                            binding?.fJobJoiningEditCurrentOffice?.spinner!!,
                            context,
                            spinnerDataModel,
                            0,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    office = any as Office
                                    office?.id?.let {
                                       // officeList.add(office!!)

                                    }

                                }

                            }
                        )
                    }

                }
            })*/

        commonRepo.getCommonData("/api/auth/salary-grade/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    Log.e("grade", "grade message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditGrade.spinner,
                            context,
                            list,
                            jobjoining?.grade_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    grade = any as SpinnerDataModel

                                    grade?.paysacle?.let {
                                        setPayScale(
                                            grade?.id,
                                            jobjoining?.pay_scale
                                        )
                                    }
                                }

                            }
                        )
                    }
                }
            })


        //set spinner for the Office Type Divisional/Head Office
        SpinnerAdapter().setSpinner(
            binding.fJobJoiningOfficeType.spinner, context,
            currentOfficeTypeData(), employeeProfileData.employee?.jobjoinings?.get(0)?.office?.office_type_id,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    currentOfficeType = any as SpinnerDataModel
                    if (currentOfficeType?.id == 1){
                        binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = "Head Office Department"
                        loadHeadOfficeDepartment()
                    }else{
                        binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = "Division"
                        setDivision()
                    }
                }
            }
        )


        //for Joining Date
        binding.fJobJoiningJoiningDate.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fJobJoiningJoiningDate.tvText.text = "" + it }
                }
            })
        }

        //for Joining Date Of
        binding.fJobJoiningEditDateOf.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fJobJoiningEditDateOf.tvText.text = "" + it }
                }
            })
        }

        //for Joining Release Date from
        binding.fJobJoiningEditReleaseDateFrom.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fJobJoiningEditReleaseDateFrom.tvText.text = "" + it }
                }
            })
        }

     //#################### [UPDATE EDITED DATA] #########################################//
        binding.jobjoiningUpdateButton.btnUpdate.setOnClickListener {
            val employeeInfoEditCreateRepo = ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)[EmployeeInfoEditCreateViewModel::class.java]

            val joiningDate = DateConverter.changeDateFormateForSending(binding.fJobJoiningJoiningDate.tvText.text.toString())
            val joiningDateOf = DateConverter.changeDateFormateForSending(binding.fJobJoiningEditDateOf.tvText.text.toString())
            val joiningReleaseDateFrom = DateConverter.changeDateFormateForSending(binding.fJobJoiningEditReleaseDateFrom.tvText.text.toString())


            val map = HashMap<Any, Any?>()

            map["employee_id"] = employeeProfileData.employee?.id
            map["office_id"] = office?.id
            map["designation_id"] = desingNation?.id
            map["additional_designation_id"] = additionalDesingNation?.id
            map["department_id"] = department?.id
            map["job_type_id"] = jobType?.id
            map["employee_class_id"] = _class?.id
            map["other_service_particulars"] = _class?.id
            map["grade_id"] = grade?.id
            map["pay_scale_id"] = payScale?.id

            try {
                if (jobjoining?.isPendingData == true) {
                    val a = jobjoining!!.parent_id
                    map["parent_id"] = a
                }
            } catch (Ex: java.lang.Exception) {

            }
            //Other Service Particulars save
            map["employeementstatus"] = Gson().toJson(serviceParticulars)
            map["employment_status_attachment"] = serviceParticularsAttachmentUrl
            map["employment_status_description"] = binding.fWhereToParticular.etText.text.toString()
            map["employment_status_effective_date"] = joiningDateOf
            map["employment_status_release_date"] = joiningReleaseDateFrom
            map["parent_id"] = jobjoining?.id
            map["pay_scale"] = payScale?.amount
            map["current"] = currentJob?.id == 1
            map["joining_date"] = joiningDate
            map["joining_date_of"] = joiningDateOf
            map["joining_release_date_from"] = joiningReleaseDateFrom
            map["status"] = jobjoining?.status

            Log.d("requestData", map.toString())
            invisiableAllError(binding)
            val dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            employeeInfoEditCreateRepo.updateJobJoiningInfo(jobjoining?.id, map)
                ?.observe(
                    EmployeeInfoActivity.context!!,
                    Observer { any ->
                        dialog?.dismiss()
                        Log.e("yousuf", "error : " + Gson().toJson(any))

                        when (any) {
                            is String -> {
                                toast(
                                    EmployeeInfoActivity.context,
                                    "" + context.getString(R.string.updated)
                                )
                                MainActivity.selectedPosition = 7
                                EmployeeInfoActivity.refreshEmployeeInfo()
                                dialogCustome?.dismiss()
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

                                            when (error) {

                                                "designation_id" -> {
                                                    binding.fJobJoiningEditDesignation.tvError.visibility =
                                                        View.VISIBLE
                                                    binding.fJobJoiningEditDesignation.tvError.text =
                                                        ErrorUtils2.mainError(message)
                                                }

                                                "employee_class_id" -> {
                                                    binding.fJobJoiningEditClass.tvError.visibility =
                                                        View.VISIBLE
                                                    binding.fJobJoiningEditClass.tvError.text =
                                                        ErrorUtils2.mainError(message)
                                                }

                                                "other_service_particulars" -> {
                                                    binding.fJobJoiningEditOtherServiceParticulars.tvError.visibility =
                                                        View.VISIBLE
                                                    binding.fJobJoiningEditOtherServiceParticulars.tvError.text =
                                                        ErrorUtils2.mainError(message)
                                                }

                                                "grade_id" -> {
                                                    binding.fJobJoiningEditGrade.tvError.visibility =
                                                        View.VISIBLE
                                                    binding.fJobJoiningEditGrade.tvError.text =
                                                        ErrorUtils2.mainError(message)
                                                }
                                                "pay_scale_id" -> {
                                                    binding.fJobJoiningEditPayScale.tvError.visibility =
                                                        View.VISIBLE
                                                    binding.fJobJoiningEditPayScale.tvError.text =
                                                        ErrorUtils2.mainError(message)
                                                }
                                                "pay_scale" -> {
                                                    binding.fJobJoiningEditGrade.tvError.visibility =
                                                        View.VISIBLE
                                                    binding.fJobJoiningEditGrade.tvError.text =
                                                        ErrorUtils2.mainError(message)
                                                }
                                                "joining_date" -> {
                                                    binding.fJobJoiningJoiningDate.tvError.visibility =
                                                        View.VISIBLE
                                                    binding.fJobJoiningJoiningDate.tvError.text =
                                                        ErrorUtils2.mainError(message)
                                                }

                                                "joining_date_of" -> {
                                                    binding.fJobJoiningEditDateOf.tvError.visibility = View.VISIBLE
                                                    binding.fJobJoiningEditDateOf.tvError.text = ErrorUtils2.mainError(message)
                                                }

                                                "joining_release_date_from" -> {
                                                    binding.fJobJoiningEditReleaseDateFrom.tvError.visibility = View.VISIBLE
                                                    binding.fJobJoiningEditReleaseDateFrom.tvError.text = ErrorUtils2.mainError(message)
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
                                EmployeeInfoActivity.context?.getString(R.string.failed)?.let {
                                    toast(
                                        EmployeeInfoActivity.context,
                                        it
                                    )
                                }
                            }
                        }
                    })
        }

    }

    fun loadHeadOfficeDepartment(){
        utilViewmodel.getHeadOfficeDepartment().observe(EmployeeInfoActivity.context!!
        ) { branches ->
            branches?.let {
                CommonSpinnerAdapter().setBranchSpinner(
                    binding.fJobJoiningOfficeDeptAndDivision.spinner,
                    context,
                    branches,
                    null,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {
                            any?.let {
                                headOfficeBranches =
                                    any as HeadOfficeDepartmentApiResponse.HeadOfficeBranch
                                headOfficeBranches?.let {
                                    loadSection(it)
                                    //loadDesignation(it.id, context, binding)
                                }
                            }
                        }

                    }
                )

            }
        }

    }

    fun setDivision() {
        commonRepo.getCommonData("/api/auth/division/list")?.observe(EmployeeInfoActivity.context!!,
            Observer { list ->
                list?.let {
                    SpinnerAdapter().setSpinner(
                        binding.fJobJoiningOfficeDeptAndDivision.spinner,
                        context,
                        list,
                        null,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {

                                division = any as SpinnerDataModel
                                division?.id?.let {
                                    district = null
                                    searchOffice()
                                }
                                getDistrict(if (division?.id == null) 1 else division?.id, null)
                                editCurrentOffice(if (division?.id == null) 1 else division?.id, null)
                            }

                        }
                    )
                }
            })

    }

    fun getDistrict(divisionId: Int?, districtId: Int?) {
        binding.fDepartmentWiseSection.tvTitle.text = "District"
        commonRepo.getDistrict(divisionId)?.observe(EmployeeInfoActivity.context!!, Observer { list ->
            list?.let {
                SpinnerAdapter().setSpinner(
                    binding.fDepartmentWiseSection.spinner,
                    context,
                    list,
                    districtId,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {
                            district = any as SpinnerDataModel
                            /*district?.id?.let {
                                //searchOffice()
                                editCurrentOffice(if (division?.id == null) 1 else division?.id, null)
                            }*/
                            editCurrentOffice(if (district?.id == null) 1 else district?.id, null)
                        }

                    }
                )
            }
        })
    }

    /*fun editCurrentOffice(divisionId: Int?, districtId: Int?) {
        binding.fDepartmentWiseSection.tvTitle.text = "District"
        commonRepo.getOffice("/api/auth/office/${divisionId}")?.observe(EmployeeInfoActivity.context!!, Observer { list ->
            list?.let {
                SpinnerAdapter().setSpinner(
                    binding.fJobJoiningEditCurrentOffice.spinner,
                    context,
                    null,
                    districtId,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {
                            district = any as SpinnerDataModel
                            district?.id?.let {

                            }

                        }

                    }
                )
            }
        })
    }
*/
    //EditCurrentOffice
    fun editCurrentOffice(divisionId: Int?, districtId: Int?) {
        commonRepo.getOffice("/api/auth/office/list",
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    list?.let {
                        SpinnerAdapter().setOfficeSpinner(
                            binding?.fJobJoiningEditCurrentOffice?.spinner!!,
                            context,
                            list,
                            districtId,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    office = any as Office
                                    office?.id?.let {
                                        officeList.add(office!!)

                                    }

                                }

                            }
                        )
                    }
                }
            })
    }


    // Department Wise Section for head office department with out DJ office and somaj sheba bhabon
    private fun loadSection(selectedBranch: HeadOfficeDepartmentApiResponse.HeadOfficeBranch) {

        binding.fDepartmentWiseSection.tvTitle.text = context.getString(R.string.department_wise_section)
        CommonSpinnerAdapter().setSectionSpinner(
            binding.fDepartmentWiseSection.spinner,
            context,
            selectedBranch.sections,
            null,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    any?.let {
                      //  headOfficeBranches =
                      //      any as HeadOfficeDepartmentApiResponse.HeadOfficeBranch
                        if ((any as HeadOfficeDepartmentApiResponse.Section).subsections?.isEmpty() != true){
                            binding.fDepartmentWiseSubSection.llBody.visibility = View.VISIBLE
                            loadSectionWiseSubSection(any)
                        }else{
                            binding.fDepartmentWiseSubSection.llBody.visibility = View.GONE
                        }
                    }
                }

            }
        )


    }

    private fun loadSectionWiseSubSection(selectedSection: HeadOfficeDepartmentApiResponse.Section) {
        CommonSpinnerAdapter().setSubSectionSpinner(
            binding.fDepartmentWiseSubSection.spinner,
            context,
            selectedSection.subsections,
            null,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    any?.let {

                    }
                }

            }
        )
    }

    fun setPayScale(gradeId: Int?, payScaleAmount: String?) {
        commonRepo.getSpecificSalaryGrade("/api/auth/salary-grade/${gradeId}",
            object : PayScaleValueListener {
                override fun valueChange(spinnerDataModel: SpinnerDataModel?) {
                    spinnerDataModel?.paysacle?.let {
                        SpinnerAdapter().setPayscale(
                            binding.fJobJoiningEditPayScale.spinner,
                            context,
                            it,
                            jobjoining?.pay_scale_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    payScale = any as Paysacle
                                }
                            }
                        )
                    }
                }
            })

    }

    fun setOffice(context: Context, binding: DialogPersonalInfoBinding) {
        mainOfficeList?.let {
            SpinnerAdapter().setOfficeSpinner(
                binding.spinner,
                context,
                it,
                jobjoining?.office_id,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        loadDesignation(office?.id, context, binding)
                        //loadAdditionalDesignation(office?.id, context, binding)
                        Log.e("selected item", " item : " + office?.name)
                    }

                }
            )
        }
    }

        fun loadDesignation(officeId: Int?, context: Context, binding: DialogPersonalInfoBinding) {
        Log.d("OK11", " officeId: "+ officeId)
        commonRepo.getDesignationData("/api/auth/office/${officeId}",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditDesignation.spinner,
                            context,
                            list,
                            jobjoining?.designation_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    desingNation = any as SpinnerDataModel
                                }
                            }
                        )
                    }
                }
            })
    }

    //For OfficeType
    private fun currentOfficeTypeData(): List<SpinnerDataModel> {

        val headOffice = SpinnerDataModel()
        headOffice.apply {
            id = 1
            name = "Head Office"
            name_bn = "হেড অফিস"

        }
        val divisionalOffice = SpinnerDataModel()
        divisionalOffice.apply {
            id = 4
            name = "Divisional"
            name_bn = "বিভাগীয়"


        }
        return arrayListOf(headOffice, divisionalOffice)
    }

    //For Hiding the Error
    fun invisiableAllError(binding: DialogPersonalInfoBinding) {

        binding.fJobJoiningEditDesignation.tvError.visibility = View.GONE

        binding.fJobJoiningEditCurrentOffice.tvError.visibility = View.GONE

        binding.fJobJoiningEditClass.tvError.visibility = View.GONE

        binding.fJobJoiningEditOtherServiceParticulars.tvError.visibility = View.GONE

        binding.fJobJoiningEditGrade.tvError.visibility = View.GONE

        binding.fJobJoiningEditPayScale.tvError.visibility = View.GONE

        binding.fJobJoiningJoiningDate.tvError.visibility = View.GONE

        binding.fJobJoiningEditDateOf.tvError.visibility = View.GONE

        binding.fJobJoiningEditReleaseDateFrom.tvError.visibility = View.GONE
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
                EmployeeInfoActivity.context!!
            ) { any ->
                Log.e("yousuf", "profile pic : " + any)
                //  showResponse(any)
                dialouge?.dismiss()
                if (any != null) {
                    val fileUrl = any as String
                    Log.d("TESTUPLOAD", "uploadFile: $fileUrl ")
                    serviceParticularsAttachmentUrl = fileUrl
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

            }


    }

    fun searchOffice() {
        Log.e("myofficemapdata", "officemap data : ${getMapData()}")
        commonRepo.getOfficeWithWhereClause(
            getMapData(),
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    Log.e("officelist", " list : " + list?.size)
                    setOffice(list)
                }
            })
    }


    fun setOffice(list: List<Office>?) {
        //isOfficeAlreadySet = true
        list?.let {
            SpinnerAdapter().setOfficeSpinner(
                binding.fAttachmentDesignation.spinner,
                context,
                list,
                0,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        office = any as Office

                       /* if (office?.id != null) {
                            office?.id?.let { loadDesignation(office?.id) }
                        } else {
                            loadDesignationList()
                        }*/

                        Log.e("selected item", " item : " + office?.name)
                    }

                }
            )
        }
    }

    fun loadDesignationList() {
        commonRepo.getCommonData("/api/auth/designation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditDesignation.spinner,
                            context,
                            list,
                            0,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    designation = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

    }

    fun getMapData(): java.util.HashMap<Any, Any?> {
        var map = java.util.HashMap<Any, Any?>()

        headOfficeBranches?.id?.let {
            map.put("head_office_department_id", it)
        }
        division?.id?.let {
            map.put("division_id", it)
        }
        district?.id?.let {
            map.put("district_id", it)
        }
        desingNation?.id?.let {
            map.put("designation_id", it)
        }
        return map
    }
}

