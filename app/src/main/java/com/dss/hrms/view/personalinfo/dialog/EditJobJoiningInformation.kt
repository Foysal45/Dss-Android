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
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogJobHistoryBinding
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
import com.dss.hrms.util.*
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
import kotlinx.android.synthetic.main.dialog_personal_info.view.*
import kotlinx.android.synthetic.main.fragment_email.view.*
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

    lateinit var binding: DialogJobHistoryBinding
    var office: Office? = null
    var designation: SpinnerDataModel? = null
    var serviceParticulars: SpinnerDataModel? = null
    var headOfficeBranches: HeadOfficeDepartmentApiResponse.HeadOfficeDepartmentResponse? = null

    var headOfficeDepartment: HeadOfficeDepartmentApiResponse.HeadOfficeBranch? = null
    var section: HeadOfficeDepartmentApiResponse.Section? = null
    var subSection: HeadOfficeDepartmentApiResponse.Subsection? = null
    var subSubSection: HeadOfficeDepartmentApiResponse.SubSubsection? = null


    var currentOfficeType: SpinnerDataModel? = null
    var recruitmentType: SpinnerDataModel? = null
    var currentJobType: SpinnerDataModel? = null
    var _class: SpinnerDataModel? = null
    var grade: SpinnerDataModel? = null
    lateinit var context: Context
    var empolyInfoPage: EmployeeInfoActivity? = null
    private var isServiceParticularsAttachment: Boolean = false
    var serviceParticularsAttachmentUrl = ""
    var fileClickListener: FileClickListener? = null
    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null

    fun showDialog(context: Context, position: Int?, fileClickListener: FileClickListener, utilViewmodel: UtilViewModel) {

        this.position = position
        this.jobjoining = position?.let { employeeProfileData?.employee?.jobjoinings?.get(it) }
        this.utilViewmodel = utilViewmodel
        this.context = context
        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_job_history,
            null,
            false
        )
        this.empolyInfoPage = context as EmployeeInfoActivity
        this.fileClickListener = fileClickListener
        dialogCustome?.setContentView(binding.root)
        val window: Window? = dialogCustome?.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        updateJobJoiningInfo(context, binding)
        dialogCustome?.show()
    }



    private fun updateJobJoiningInfo(context: Context, binding: DialogJobHistoryBinding) {
        binding.llJobjoningInfo.visibility = View.VISIBLE
        binding.hJobJoiningInformation.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }


        //for Joining Date
        binding.fJobJoiningJoiningDate.tvText.text = jobjoining?.joining_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        }

        //for ReleaseDate if Current Job type false
        binding.fJobJoiningReleaseDate.tvText.text = jobjoining?.joining_date?.let {
            DateConverter.changeDateFormateForShowing(
                it
            )
        }

        //setting joining Date and Release Date according the employeementstatus?.id
        when(jobjoining?.employeementstatus?.id){
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
        binding.fDepartmentWiseSection.llBody.visibility = View.GONE
        binding.fDepartmentWiseSubSection.llBody.visibility = View.GONE
        binding.fDepartmentWiseSubSubSection.llBody.visibility = View.GONE
        binding.fWhichBranch.llBody.visibility = View.GONE

        //employee class
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
        binding.additionalChargeOffice.additionalChargeOfficeLayout.visibility = View.GONE
        binding.llOffice.visibility = View.GONE
        binding.fAttachmentDesignation.llBody.visibility = View.GONE


        commonRepo.getCommonData("/api/auth/employment-status-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                       Log.e("Particulars", "Particulars message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditOtherServiceParticulars.spinner,
                            context, list,
                            serviceParticulars?.id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                 //   _class = any as SpinnerDataModel

                                    serviceParticulars = any as SpinnerDataModel

                                    Log.d("serviceParticulars  ",""+serviceParticulars?.id)


                                    if (serviceParticulars?.id != null){
                                               when(serviceParticulars?.id) {
                                        1, 2 -> {
                                            binding.fWhereToParticular.llBody.visibility = View.VISIBLE
                                            binding.additionalChargeOffice.additionalChargeOfficeLayout.visibility = View.GONE
                                            binding.fWhereToParticular.title = "Where to ${serviceParticulars?.name}"

                                            binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${serviceParticulars?.name}"
                                            binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${serviceParticulars?.name}"
                                            binding.fAttachmentDesignation.llBody.visibility = View.GONE
                                            binding.llOffice.visibility = View.GONE
                                        }

                                        3 -> {
                                            binding.fWhereToParticular.llBody.visibility = View.GONE
                                            binding.additionalChargeOffice.additionalChargeOfficeLayout.visibility = View.GONE
                                            binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${serviceParticulars?.name}"
                                            binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${serviceParticulars?.name}"
                                            binding.fAttachmentDesignation.llBody.visibility = View.VISIBLE
                                            binding.fAttachmentDesignation.title = "${serviceParticulars?.name} Designation"
                                            binding.llOffice.visibility = View.VISIBLE

                                        }
                                                   4 -> {
                                                       binding.fWhereToParticular.llBody.visibility = View.GONE
                                                       binding.additionalChargeOffice.additionalChargeOfficeLayout.visibility = View.VISIBLE
                                                       binding.fJobJoiningEditDateOf.tvTitle.text = "Joining Date Of ${serviceParticulars?.name}"
                                                       binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Release Date from ${serviceParticulars?.name}"
                                                       binding.fAttachmentDesignation.llBody.visibility = View.VISIBLE
                                                       binding.fAttachmentDesignation.title = "${serviceParticulars?.name} Designation"
                                                       binding.llOffice.visibility = View.GONE

                                                   }

                                        5 -> {
                                            binding.fWhereToParticular.llBody.visibility = View.GONE
                                            binding.additionalChargeOffice.additionalChargeOfficeLayout.visibility = View.GONE
                                            binding.fJobJoiningEditDateOf.tvTitle.text = "Suspension Date"
                                            binding.fJobJoiningEditReleaseDateFrom.tvTitle.text = "Released Date from Suspension"
                                            binding.fAttachmentDesignation.llBody.visibility = View.GONE
                                            binding.llOffice.visibility = View.GONE

                                        }
                                                   else ->{
                                                   binding.additionalChargeOffice.additionalChargeOfficeLayout.visibility = View.GONE
                                                   binding.llOffice.visibility = View.GONE
                                                       binding.fAttachmentDesignation.llBody.visibility = View.GONE
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
            binding.tvParticularAttachment.text = context.getString(R.string.attachment) + "\n" + jobjoining?.employment_status_attachment
        }

        if (jobjoining?.employment_status_attachment.toString()
                .toLowerCase() != "null" || !jobjoining?.employment_status_attachment.isNullOrEmpty()
        ) {
            binding.tvParticularAttachment.text = context.getString(R.string.attachment) + "\n" + jobjoining?.employment_status_attachment
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

    //basic pay scale
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
                                        setPayScale(grade?.id, jobjoining?.pay_scale)
                                    }

                                    Log.d("jobjoining  "," "+grade?.id)
                                    if (grade?.id in 1..8 || grade?.id == 20)
                                    {
                                        binding.fJobJoiningEditRecruitmentType.llBody.visibility=View.GONE
                                    }else{
                                        binding.fJobJoiningEditRecruitmentType.llBody.visibility=View.VISIBLE
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

                    when (currentOfficeType?.id) {
                        1 -> {
                            binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = "Head Office Department"
                            binding.fDepartmentWiseSection.tvTitle.text = "Department Wise Section"
                            binding.fJobJoiningEditCurrentOffice.llBody.visibility = View.GONE
                            loadHeadOfficeDepartment()
                        }
                        4 -> {
                            binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = "Division"
                            binding.fDepartmentWiseSection.tvTitle.text = "District"
                            binding.fDepartmentWiseSection.llBody.visibility = View.VISIBLE
                            binding.fJobJoiningEditCurrentOffice.llBody.visibility = View.VISIBLE
                            binding.fWhichBranch.llBody.visibility=View.GONE

                            if (jobjoining?.current == true){
                                binding.fJobJoiningEditCurrentOffice.tvTitle.text = "Current Office"
                            }else{
                                binding.fJobJoiningEditCurrentOffice.tvTitle.text = "Previous Office"
                            }

                            binding.fDepartmentWiseSubSection.llBody.visibility = View.GONE
                            binding.fDepartmentWiseSubSubSection.llBody.visibility = View.GONE
                            setDivision()
                        }

                    }

                }
            }
        )

        //Current JobType Edit Spinner
        SpinnerAdapter().setSpinner(
            binding.fJobJoiningEditCurrentJob.spinner, context,
            currentJobTypeData(), employeeProfileData.employee?.jobjoinings?.get(0)?.office?.office_type_id,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    currentJobType = any as SpinnerDataModel
                    when (currentJobType?.id) {
                        1 -> {
                            binding.fJobJoiningReleaseDate.llBody.visibility=View.GONE
                        }
                        2 -> {
                            binding.fJobJoiningReleaseDate.llBody.visibility=View.VISIBLE
                        }
                        else -> binding.fJobJoiningReleaseDate.llBody.visibility=View.GONE
                    }
                }
            }
        )

        //Recruitment Type for grade
        SpinnerAdapter().setSpinner(
            binding.fJobJoiningEditRecruitmentType.spinner, context,
            recruitmentTypeData(), employeeProfileData.employee?.jobjoinings?.get(0)?.office?.office_type_id,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    recruitmentType = any as SpinnerDataModel
                }
            }
        )

        // attachment office and attachment designation
        binding.ivSearch.setOnClickListener {
            officeSearchingDialog.showOfficeSearchDialog(
                context,
                utilViewmodel,
                object : OfficeDataValueListener {
                    override fun valueChange(officeList: List<Office>?) {
                        mainOfficeList = officeList
                        //setOffice(context, binding)
                        attachmentOffice(context, binding)
                    }
                })
        }

        //Additional Charge Office at Other service particulars
       binding.additionalChargeOffice.additionalChargeOfficeivSearch.setOnClickListener {
            officeSearchingDialog.showOfficeSearchDialog(
                context,
                utilViewmodel,
                object : OfficeDataValueListener {
                    override fun valueChange(officeList: List<Office>?) {
                       mainOfficeList = officeList
                       additionalChargeOffice(context, binding)

                    }
                })
        }

        //for JoiningDate
        binding.fJobJoiningJoiningDate.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fJobJoiningJoiningDate.tvText.text = "" + it }
                }
            })
        }

        //for ReleaseDate if Current Job Type false
        binding.fJobJoiningReleaseDate.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fJobJoiningReleaseDate.tvText.text = "" + it }
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

            invisiableAllError(binding)
            val dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)

            /*key.let {
                if (it == StaticKey.EDIT) {
                    var id: Int? = 0;
                    if (key == StaticKey.EDIT && jobjoining?.isPendingData == false) {
                        id = jobjoining!!.id
                    } else if (key == StaticKey.EDIT && jobjoining?.isPendingData == true) {
                        id = jobjoining!!.parent_id
                    }*/


                    employeeInfoEditCreateRepo.updateJobJoiningInfo(jobjoining?.id, updateData())?.observe(
                            EmployeeInfoActivity.context!!
                        ) { any ->
                            dialog?.dismiss()

                        if (any !=null){
                            showResponse(any)
                        }


                        }
               // }

            //}


           /* employeeInfoEditCreateRepo.updateJobJoiningInfo(jobjoining?.id,updateData())
                ?.observe(
                    EmployeeInfoActivity.context!!,
                    Observer { any ->
                        dialog?.dismiss()
                        showResponse(any)
                        Log.e("yousuf", "error : " + Gson().toJson(any))

                    })*/
        }

    }

    fun updateData(): HashMap<Any, Any?> {

        val joiningDate = DateConverter.changeDateFormateForSending(binding.fJobJoiningJoiningDate.tvText.text.trim().toString())
        val releaseDate = DateConverter.changeDateFormateForSending(binding.fJobJoiningReleaseDate.tvText.text.trim().toString())
        val joiningDateOf = DateConverter.changeDateFormateForSending(binding.fJobJoiningEditDateOf.tvText.text.trim().toString())
        val joiningReleaseDateFrom = DateConverter.changeDateFormateForSending(binding.fJobJoiningEditReleaseDateFrom.tvText.text.trim().toString())

        val map = HashMap<Any, Any?>()

        map["office_type"] = if(currentOfficeType?.id==1){"head_office"}else "divisional_office"
        //currentOfficeType?.name == "Head Office" ? "head_office": "divisional_office"
        headOfficeDepartment?.id?.let {
            map.put("head_office_department_id", it)
        }
        section?.id?.let {
            map.put("department_wise_section_id", it)
        }
        subSection?.id?.let {
            map.put("section_wise_sub_section_id", it)
        }
        subSubSection?.id?.let {
            map.put("section_wise_sub_sub_section_id", it)
        }
        division?.id?.let {
            map.put("division_id", it)
        }
        district?.id?.let {
            map.put("district_id", it)
        }

        map["department_text"] = binding.fWhichBranch.etText.text.toString()

        designation?.id.let {
            map.put("designation_id",it)
        }
        if (office?.id != null)  //this office means a under Divisional office type the Current/Previous office id
        {
            office?.id.let {
                map.put("office_id", it)
            }
        }else{
            //under Head Office each Department,section,subSection and subSubSection considered as a office
            headOfficeDepartment?.office?.id.let {
                map.put("office_id", it)
            }
        }

        office?.id.let {
            map.put("additional_office_id", it)
        }

        serviceParticulars?.id.let {
            map.put("employment_status_id",it)
        }
        office?.id.let {
            map.put("additional_attachment_charge_office_id",it)
        }
        designation?.id.let {
            map.put("additional_attachment_charge_designation_id",it)
        }
        serviceParticularsAttachmentUrl.let {
            map.put("employment_status_attachment",it)
        }
        map["employeementstatus"] = Gson().toJson(serviceParticulars)
        map["employment_status_description"] = binding.fWhereToParticular.etText.text.toString()

        map["employment_status_effective_date"] = joiningDateOf
        map["employment_status_release_date"] = joiningReleaseDateFrom
        map["employee_class_id"] = _class?.id
        map["grade_id"] = grade?.id   //grade?.id  //payScale?.id
        map["pay_scale_id"] = payScale?.id   //grade?.id  //payScale?.id
        map["pay_scale"] = payScale?.amount
        map["joining_date"] = joiningDate


        if (currentJobType?.name == "true")
        {
            map["current"] = true
        }else{
            map["current"] = false
            map["release_date"] = releaseDate
        }

        map["employee_id"] = employeeProfileData.employee?.id

        designation?.id.let {
            map.put("additional_designation_id",it)
        }
        headOfficeDepartment?.id?.let {
            map.put("department_id", it)
        }
        section?.id?.let {
            map.put("head_office_section_id", it)
        }
        subSection?.id?.let {
            map.put("head_office_sub_section_id", it)
        }
        subSubSection?.id?.let {
            map.put("head_office_sub_sub_section_id", it)
        }

        map["recruite_type"] = if(recruitmentType?.id==1){"Direct"} else "Promotion"

         try {
             if (jobjoining?.isPendingData == true) {
                 val a = jobjoining!!.parent_id
                 map["parent_id"] = a
             }
         } catch (_: java.lang.Exception) {

         }

        if (jobjoining?.status != null) {
            map["status"] = jobjoining?.status
        } else map["status"] = 0
        Log.d("TAG", "requestData: ${map.toString()}")
        return map
    }

    @Suppress("UNCHECKED_CAST")
    fun showResponse(any: Any){
        //Log.d("okke", "response" + (any as ApiError).getError().first().getMessage()  + " "+  (any).toString())
        when (any) {
            is String -> {
                toast(EmployeeInfoActivity.context, "" + context.getString(R.string.updated))

                MainActivity.selectedPosition = 7
                EmployeeInfoActivity.refreshEmployeeInfo()
                dialogCustome?.dismiss()
            }
            is ApiError -> {
                try {
                    if (any.getError().isEmpty()) {
                        toast(EmployeeInfoActivity.context, any.getMessage())
                       // Log.d("oky", "error")
                    } else {
                        for (n in any.getError().indices) {

                            val error = any.getError()[n].getField()
                            val message = any.getError()[n].getMessage()
                            //Log.d("tttttttt", "error:  " +error +"    message: " +message)
                            when (error) {

                                "office_type" -> {
                                    binding.fJobJoiningOfficeType.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningOfficeType.tvError.text = ErrorUtils2.mainError(message)
                                }
                                "head_office_department_id" ->{
                                    binding.fJobJoiningOfficeDeptAndDivision.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningOfficeDeptAndDivision.tvError.text = ErrorUtils2.mainError(message)
                                }
                                "department_wise_section_id" ->{
                                    binding.fDepartmentWiseSection.tvError.visibility = View.VISIBLE
                                    binding.fDepartmentWiseSection.tvError.text = ErrorUtils2.mainError(message)
                                }
                                "section_wise_sub_section_id" ->{
                                    binding.fDepartmentWiseSubSection.tvError.visibility = View.VISIBLE
                                    binding.fDepartmentWiseSubSection.tvError.text = ErrorUtils2.mainError(message)
                                }
                                "section_wise_sub_sub_section_id" ->{
                                    binding.fDepartmentWiseSubSubSection.tvError.visibility = View.VISIBLE
                                    binding.fDepartmentWiseSubSubSection.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "division_id" -> {
                                    binding.fJobJoiningOfficeDeptAndDivision.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningOfficeDeptAndDivision.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "district_id" -> {
                                    binding.fDepartmentWiseSection.tvError.visibility = View.VISIBLE
                                    binding.fDepartmentWiseSection.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "designation_id" -> {
                                    binding.fJobJoiningEditDesignation.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningEditDesignation.tvError.text = ErrorUtils2.mainError(message)
                                }

                                /*"office_id" -> {
                                    binding.fJobJoiningEditCurrentOffice.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningEditCurrentOffice.tvError.text = ErrorUtils2.mainError(message)
                                }*/

                                "other_service_particulars" -> {
                                    binding.fJobJoiningEditOtherServiceParticulars.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningEditOtherServiceParticulars.tvError.text = ErrorUtils2.mainError(message)
                                }

                                /*"additional_attachment_charge_office_id" ->{
                                    binding.llOffice.spinner.tvError.visibility = View.VISIBLE
                                    binding.llOffice.spinner.tvError.visibility = ErrorUtils2.mainError(message)
                                }*/

                                "additional_attachment_charge_designation_id" ->{
                                    binding.fAttachmentDesignation.tvError.visibility = View.VISIBLE
                                    binding.fAttachmentDesignation.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "employment_status_description" ->{
                                    binding.fWhereToParticular.tvError.visibility = View.VISIBLE
                                    binding.fWhereToParticular.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "employment_status_effective_date" -> {
                                    binding.fJobJoiningEditDateOf.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningEditDateOf.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "employment_status_release_date" ->{
                                    binding.fJobJoiningEditReleaseDateFrom.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningEditReleaseDateFrom.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "employee_class_id" -> {
                                    binding.fJobJoiningEditClass.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningEditClass.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "grade_id" -> {
                                    binding.fJobJoiningEditGrade.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningEditGrade.tvError.text = ErrorUtils2.mainError(message)
                                }
                                "pay_scale" -> {
                                    binding.fJobJoiningEditGrade.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningEditGrade.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "joining_date" ->{
                                    binding.fJobJoiningJoiningDate.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningJoiningDate.tvError.text = ErrorUtils2.mainError(message)
                                }

                                "release_date" ->{
                                    binding.fJobJoiningReleaseDate.tvError.visibility = View.VISIBLE
                                    binding.fJobJoiningReleaseDate.tvError.text = ErrorUtils2.mainError(message)
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
                    toast(EmployeeInfoActivity.context, it)
                }
            }
        }

    }


    //head office_department
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
                                headOfficeDepartment = any as HeadOfficeDepartmentApiResponse.HeadOfficeBranch
                                loadDesignationList()
                               // if headOfficeDepartment is SomajSebaBhabon than Which Branch EditText field will be visible
                                if (currentOfficeType?.id==1 && headOfficeDepartment?.id==6)
                                {
                                    binding.fWhichBranch.llBody.visibility=View.VISIBLE
                                }else{
                                    binding.fWhichBranch.llBody.visibility=View.GONE
                                }

                                if ((headOfficeDepartment)?.sections?.isEmpty() != true){
                                    binding.fDepartmentWiseSection.llBody.visibility = View.VISIBLE
                                    loadSection(any)
                                }else{
                                    binding.fDepartmentWiseSection.llBody.visibility = View.GONE
                                }
                            }
                        }

                    }
                )

            }
        }

    }

    // Department Wise Section
    private fun loadSection(selectedBranch: HeadOfficeDepartmentApiResponse.HeadOfficeBranch) {
        CommonSpinnerAdapter().setSectionSpinner(
            binding.fDepartmentWiseSection.spinner,
            context,
            selectedBranch.sections,
             null,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    any?.let {
                        section = any as HeadOfficeDepartmentApiResponse.Section
                        loadDesignationData(section?.office?.id)

                        if ((section)?.subsections?.isEmpty() != true){
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

    //section wise_sub_section
    private fun loadSectionWiseSubSection(selectedSection: HeadOfficeDepartmentApiResponse.Section) {
        CommonSpinnerAdapter().setSubSectionSpinner(
            binding.fDepartmentWiseSubSection.spinner,
            context,
            selectedSection.subsections,
            null,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    any?.let {
                        subSection = any as HeadOfficeDepartmentApiResponse.Subsection
                        loadDesignationData(subSection?.office?.id)

                        if ((subSection)?.sub_subsections?.isEmpty() != true){
                            binding.fDepartmentWiseSubSubSection.llBody.visibility = View.VISIBLE
                            loadSectionWiseSubSubSection(any)
                        }else{
                            binding.fDepartmentWiseSubSubSection.llBody.visibility = View.GONE
                        }
                    }
                }

            }
        )
    }

    //section wise_sub_sub_section
    private fun loadSectionWiseSubSubSection(selectedSection: HeadOfficeDepartmentApiResponse.Subsection) {
        CommonSpinnerAdapter().setSubSubSectionSpinner(
            binding.fDepartmentWiseSubSubSection.spinner,
            context,
            selectedSection.sub_subsections,
            null,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    any?.let {
                        subSubSection = any as HeadOfficeDepartmentApiResponse.SubSubsection
                            loadDesignationData(subSubSection?.office?.id)
                    }
                }

            }
        )
    }

    fun setDivision() {
        commonRepo.getCommonData("/api/auth/division/list")?.observe(EmployeeInfoActivity.context!!,
            Observer { list ->
                list?.let {
                    SpinnerAdapter().setSpinner(
                        binding.fJobJoiningOfficeDeptAndDivision.spinner,
                        context,
                        list,
                        division?.division_id,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {

                                division = any as SpinnerDataModel
                                division?.id?.let {
                                    getDistrict(if (division?.id == null) 1 else division?.id, null)
                                    editCurrentOffice(division?.id, null)
                                }

                            }

                        }
                    )
                }
            })

    }

    fun getDistrict(divisionId: Int?, districtId: Int?) {
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
                            editCurrentOffice(divisionId, district?.id)

                        }

                    }
                )
            }
        })
    }

    //EditCurrentOffice
    fun editCurrentOffice(divisionId: Int?, districtId: Int?) {
       // Toast.makeText(context,"data : $divisionId $districtId",Toast.LENGTH_SHORT).show()
     //${divisionId}&district_id=${districtId}
       val parameter = if (divisionId != null) divisionId else division?.id
       val parameter2 = if (districtId != null) districtId else ""
        commonRepo.getOffice("/api/auth/office/list/basic?division_id=${parameter}&district_id=${parameter2}",
            object : OfficeDataValueListener {
                override fun valueChange(list: List<Office>?) {
                    list?.let {
                        SpinnerAdapter().setOfficeSpinner(
                            binding?.fJobJoiningEditCurrentOffice?.spinner!!,
                            context,
                            it,
                            jobjoining?.office_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    office = any as Office
                                    office?.id?.let {
                                        mainOfficeList?.get(0)
                                        loadDesignationData(office?.id!!)

                                    }

                                }

                            }
                        )
                    }
                }
            })
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

    //function for set Attachment Office under OtherService Particles
    fun attachmentOffice(context: Context, binding: DialogJobHistoryBinding) {
        mainOfficeList?.let {
            SpinnerAdapter().setOfficeSpinner(
                binding.llOffice.spinner,
                context,
                it,
                0,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        office = any as Office
                        if (office?.id != null) {
                            office?.id?.let { attachmentAndAdditionalChargeDesignation(office?.id!!) }
                        }
                    }

                }
            )
        }
    }

    //function for set Additional charge Office under OtherServiceParticals
    fun additionalChargeOffice(context: Context, binding: DialogJobHistoryBinding) {
        mainOfficeList?.let {
            SpinnerAdapter().setOfficeSpinner(
                binding.additionalChargeOffice.additionalChargeOfficeSpinner,
                context,
                it,
                jobjoining?.office_id,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        office = any as Office
                        if (office?.id != null) {
                            office?.id?.let { attachmentAndAdditionalChargeDesignation(office?.id!!) }
                        }
                        //Log.e("selected item", " item : " + office?.name)
                    }

                }
            )
        }
    }

    //attachmentAndAdditionalChargeDesignation
    private fun attachmentAndAdditionalChargeDesignation(officeId:Int) {
        //Toast.makeText(context,"data : $officeId",Toast.LENGTH_SHORT).show()
        commonRepo.getDesignationData("/api/auth/office/${officeId}",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fAttachmentDesignation.spinner,
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

    //load office wise designation
    private fun loadDesignationData(officeId: Int?) {
        Toast.makeText(context,"data : $officeId",Toast.LENGTH_SHORT).show()
        commonRepo.getDesignationData("/api/auth/office/${officeId}",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditDesignation.spinner,
                            context,
                            list,
                           0,//jobjoining?.designation_id,
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

    private fun loadDesignationList() {
        commonRepo.getCommonData("/api/auth/designation/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fJobJoiningEditDesignation.spinner,
                            context,
                            list,
                            0, //jobjoining?.designation_id,
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

    //For currentJobType
    private fun currentJobTypeData(): List<SpinnerDataModel> {

        val yes = SpinnerDataModel()
        yes.apply {
            id = 1
            name = "true"

        }
        val no = SpinnerDataModel()
        no.apply {
            id = 2
            name = "false"

        }
        return arrayListOf(yes, no)
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

    private fun recruitmentTypeData(): List<SpinnerDataModel> {

        val direct = SpinnerDataModel()
        direct.apply {
            id = 1
            name = "Direct"
            name_bn = "সরাসরি"

        }
        val promotion = SpinnerDataModel()
        promotion.apply {
            id = 4
            name = "Promotion"
            name_bn = "পদোন্নতি"

        }
        return arrayListOf(direct, promotion)
    }

    //For Hiding the Error
    fun invisiableAllError(binding: DialogJobHistoryBinding) {

        binding.fJobJoiningEditDesignation.tvError.visibility = View.GONE

        binding.fJobJoiningEditCurrentOffice.tvError.visibility = View.GONE

        binding.fJobJoiningEditClass.tvError.visibility = View.GONE

        binding.fJobJoiningEditOtherServiceParticulars.tvError.visibility = View.GONE

        binding.fJobJoiningEditGrade.tvError.visibility = View.GONE

        binding.fJobJoiningEditPayScale.tvError.visibility = View.GONE

        binding.fJobJoiningJoiningDate.tvError.visibility = View.GONE

        binding.fJobJoiningReleaseDate.tvError.visibility = View.GONE

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
        profilePic = MultipartBody.Part.createFormData("filenames[]", "${file.name}", requestFile)
//        profilePic = MultipartBody.Part.createFormData("filenames[]", "filenames[${file.name}]", requestFile)

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
                //Log.e("yousuf", "profile pic : " + any)
                //  showResponse(any)
                dialouge.dismiss()
                if (any != null) {
                    val fileUrl = any as String
                   // Log.d("TESTUPLOAD", "uploadFile: $fileUrl ")
                    serviceParticularsAttachmentUrl = fileUrl
                    Toast.makeText(context, context.getString(R.string.successMsg), Toast.LENGTH_LONG).show()

                } else {

                    Toast.makeText(context, context.getString(R.string.uploadFailed), Toast.LENGTH_LONG).show()
                }

            }


    }

}

