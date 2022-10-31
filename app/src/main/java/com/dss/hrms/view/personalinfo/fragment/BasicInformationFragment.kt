package com.dss.hrms.view.personalinfo.fragment

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.dss.hrms.R
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.commonSpinnerDataLoad.CommonData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.pendingDataModel.PendingDataModel
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.*
import com.dss.hrms.view.allInterface.FileClickListener
import com.dss.hrms.view.allInterface.OnFilevalueReceiveListener
import com.dss.hrms.view.bottomsheet.SelectImageBottomSheet
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.dialog.EditEmployeeBasicInfoDialog
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import kotlinx.android.synthetic.main.personal_information_header_field.view.*
import kotlinx.android.synthetic.main.personel_information_view_field.view.llBody
import kotlinx.android.synthetic.main.personel_information_view_field.view.tvText
import kotlinx.android.synthetic.main.personel_information_view_field.view.tvTitle
import kotlinx.android.synthetic.main.personel_information_view_field_with_icon.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BasicInformationFragment : DaggerFragment(), SelectImageBottomSheet.BottomSheetListener {

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var editEmployeeBasicInfoDialog: EditEmployeeBasicInfoDialog

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var employee: Employee? = null

    private val REQUEST_TAKE_PHOTO = 1
    private val REQUEST_SELECT_PHOTO = 2
    private var selectImageBottomSheet: SelectImageBottomSheet? = null
    private var imageFile: File? = null
    private var currentPhotoPath: String? = null
    private var onFilevalueReceiveListener: OnFilevalueReceiveListener? = null


    private var param1: String? = null
    private var param2: String? = null
    lateinit var v: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var ctx: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_basic_information, container, false)
        this.employee = employeeProfileData.employee
        ctx = v.context

        v.llJobjoningInfo1.visibility = View.GONE
        setData()
        val pendingOBj: PendingDataModel? = preparence.get(HelperClass.PEDING_DATA)
        val pendingEmployee = pendingOBj?.employee

        if (!pendingEmployee.isNullOrEmpty()) {
            v.llJobjoningInfo1.visibility = View.VISIBLE

            pendingEmployee[0].data?.employment_job_status = employee?.employment_job_status
            pendingEmployee[0].data?.employment_job_status?.employeementstatus = employee?.employment_job_status?.employeementstatus
            pendingEmployee[0].data?.user = employee?.user

            setPendingData(pendingEmployee[0].data, employee?.employment_job_status?.employeementstatus?.name)
        }



        EmployeeInfoActivity.context?.let { verifyStoragePermissions(it) }

        return v
    }


    /*....................................................
     ................NORMAL DATA SETTING.................
     ....................................................*/
    private fun setData() {

        v.fPresentBasicSalary.llBody.visibility = View.GONE
        v.fPresentGrossSalary.llBody.visibility = View.GONE

        v.hBasicInformation.tvTitle.text = getString(R.string.personal_information)
        v.fEmployeeId.tvTitle.text = getString(R.string.employee_id)

        v.fNameEng.tvTitle.text = getString(R.string.name)

        v.fNameBangla.tvTitle.text = getString(R.string.name_b)

        v.fDOB.tvTitle.text = getString(R.string.birth)
        v.fGender.tvTitle.text = getString(R.string.gender)
        v.fMaritalStatus.tvTitle.text = getString(R.string.marital_status)
        v.fReligion.tvTitle.text = getString(R.string.religion)
        v.fBloodGroup.tvTitle.text = getString(R.string.blood_group)
        v.fPresentBasicSalary.tvTitle.text = getString(R.string.present_basic_salary)
        v.fPresentGrossSalary.tvTitle.text = getString(R.string.present_gross_salary)
        v.fTIN.tvTitle.text = getString(R.string.tin_no)
        v.fPunchId.tvTitle.text = getString(R.string.punch_id)

        v.fFatherNameEng.tvTitle.text = getString(R.string.f_name)
        v.fFatherNameBangla.tvTitle.text = getString(R.string.f_name_b)
        v.fMotherNameEng.tvTitle.text = getString(R.string.m_name)
        v.fMotherNameBangla.tvTitle.text = getString(R.string.m_name_b)
        v.fEmail.tvTitle.text = getString(R.string.email)
        v.fUserName.tvTitle.text = getString(R.string.user_name)
        v.fPhone.tvTitle.text = getString(R.string.phone)
        v.fHomeDistrict.tvTitle.text = getString(R.string.home_district)
        v.fEmployeeType.tvTitle.text = getString(R.string.employee_type)
        v.fEmployeeJobJoiningDate.tvTitle.text = getString(R.string.employment_job_joining_date)
        v.fPRLDate.tvTitle.text = getString(R.string.prl_date)
        v.fPension_date.tvTitle.text = getString(R.string.pension_date)
        v.fJobType.tvTitle.text = getString(R.string.job_type)

        v.fEmployeePermanentConfirmDate.tvTitle.text = getString(R.string.permanent_confirm_date)
        v.fEmployeePermanentDocument.tvTitle.text = getString(R.string.permanent_document)


        v.fTemporaryRevenueType.tvTitle.text = getString(R.string.temporary_revenue_type)
        v.fTemporaryRevenueTransferDate.tvTitle.text = getString(R.string.temporary_revenue_transfer_date)
        v.fTemporaryRevenueTransferDoc.tvTitle.text = getString(R.string.temporary_revenue_transfer_doc)
        v.fRegularizationDate.tvTitle.text = getString(R.string.regularization_date)
        v.fRegularizationDateDoc.tvTitle.text = getString(R.string.regularization_date_doc)

        v.fEmployeeStatusType.tvTitle.text = getString(R.string.employee_status_type)
        v.fEmployeeStatusDate.tvTitle.text = getString(R.string.employee_status_date)

        v.fEmployeeFreedomFighterquota.tvTitle.text = getString(R.string.has_freedom_fighter_quota)
        v.fDisability.tvTitle.text = getString(R.string.has_disability)
        v.tvImageTitle.text = getString(R.string.photo)
        v.fEmployeeRole.tvTitle.text = getString(R.string.employee_role)
        v.fDisabilityType.tvTitle.text = getString(R.string.disability_type)
        v.fDisabilityDegree.tvTitle.text = getString(R.string.disability_degree)
        v.fDisabledPersonId.tvTitle.text = getString(R.string.disabled_person_id)
        v.fNid.tvTitle.text = getString(R.string.nid_no)


        if (employee?.profile_id != null)
        {
            v.fEmployeeId.tvText.text = employee?.profile_id
        }else{
            v.fEmployeeId.tvText.text = " "
        }

       // v.fEmployeeId.tvText.text = "" + employee?.profile_id
        employee?.name?.let { v.fNameEng.tvText.text = it }
        employee?.name_bn?.let { v.fNameBangla.tvText.text = it }
        employee?.date_of_birth?.let {
            v.fDOB.tvText.text = (DateConverter.changeDateFormateForShowing(
                it
            ))
        }

        employee?.job_joining_date?.let {
            v.fEmployeeJobJoiningDate.tvText.text = (DateConverter.changeDateFormateForShowing(
                it
            ))
        }

        employee?.prl_date?.let { v.fPRLDate.tvText.text = it }
        employee?.pension_date?.let { v.fPension_date.tvText.text = it }

        employee?.fathers_name?.let { v.fFatherNameEng.tvText.text = it }
        employee?.fathers_name_bn?.let { v.fFatherNameBangla.tvText.text = it }
        employee?.mothers_name?.let { v.fMotherNameEng.tvText.text = it }
        employee?.mothers_name_bn?.let { v.fMotherNameBangla.tvText.text = it }
        employee?.user?.email?.let { v.fEmail.tvText.text = it }
        employee?.user?.username?.let { v.fUserName.tvText.text = it }
        employee?.user?.phone_number?.let { v.fPhone.tvText.text = it }
        employee?.nid_no?.let { v.fNid.tvText.text = it }
        employee?.tin_no?.let { v.fTIN.tvText.text = it }
        employee?.punch_id?.let { v.fPunchId.tvText.text = it }
        employee?.present_basic_salary?.let { v.fPresentBasicSalary.tvText.text = it }
        employee?.present_gross_salary?.let { v.fPresentGrossSalary.tvText.text = it }
        employee?.employment_job_status?.status_date?.let {
            v.fEmployeeStatusDate.tvText.text = (DateConverter.changeDateFormateForShowing(
                it
            ))
        }
        employee?.user?.roles?.let {
            if (it.size > 0) {
                v.fEmployeeRole.tvText.text = it.get(0).name
            }
        }

        employee?.has_freedom_fighter_quota?.let {
            if (it) {
                v.fEmployeeFreedomFighterquota.tvText.text = context?.getString(R.string.yes)
                // emp has the fredom fihter quta
                // decide what to do
                v.fEmployeeFreedomFighterAttachment.llBody.visibility = View.VISIBLE
                // assgin text to the view
                v.fEmployeeFreedomFighterAttachment.tvTitle.text =
                    context?.getString(R.string.attachment)
                v.fEmployeeFreedomFighterAttachment.tvText.setTextColor(
                    ContextCompat.getColor(
                        ctx,
                        R.color.green
                    )
                )
                v.fEmployeeFreedomFighterAttachment.tvText.text = " Tap To View"
                val fileExtentions =
                    ConvertNumber.getTheFileExtention(employee?.freedom_fighter_document_path)
                        .lowercase(Locale.getDefault())


                if (fileExtentions.isEmpty()) {
                    v.fEmployeeFreedomFighterAttachment.tvText.text = " No Attachment"
                } else if (fileExtentions.contains("png") || fileExtentions.contains("jpeg") || fileExtentions.contains(
                        "jpg"
                    )
                ) {
                    v.fEmployeeFreedomFighterAttachment.icon.background =
                        ContextCompat.getDrawable(ctx, R.drawable.picture)
                } else {
                    v.fEmployeeFreedomFighterAttachment.icon.background =
                        ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
                }

            } else {
                v.fEmployeeFreedomFighterquota.tvText.text = context?.getString(R.string.no)
                v.fEmployeeFreedomFighterAttachment.llBody.visibility = View.GONE
            }

        }

        if (employee?.has_disability == false) {
            Log.e("hasdisability", "" + employee?.has_disability)
            v.fDisability.tvText.text = "" + context?.getString(R.string.no)
            v.fDisabilityDegree.llBody.visibility = View.GONE
            v.fDisabilityType?.llBody?.visibility = View.GONE
            v.fDisabledPersonId?.llBody?.visibility = View.GONE
            v.fDisabilityAttachment.llBody.visibility = View.GONE

        } else {
            v.fDisability.tvText.text = "" + context?.getString(R.string.yes)
            v.fDisabilityDegree?.llBody?.visibility = View.VISIBLE
            v.fDisabilityType?.llBody?.visibility = View.VISIBLE
            v.fDisabledPersonId?.llBody?.visibility = View.VISIBLE
            v.fDisabledPersonId.tvText.text = employee?.disabled_person_id

            v.fDisabilityAttachment.llBody.visibility = View.VISIBLE
            v.fDisabilityAttachment.tvTitle.text = context?.getString(R.string.attachment)
            v.fDisabilityAttachment.tvText.setTextColor(ContextCompat.getColor(ctx, R.color.green))
            v.fDisabilityAttachment.tvText.text = " Tap To View"

            val fileExtentions =
                ConvertNumber.getTheFileExtention(employee?.disability_document_path)
                    .lowercase(Locale.getDefault())


            if (fileExtentions.isEmpty()) {
                v.fEmployeeFreedomFighterAttachment.tvText.text = " No Attachment"
            } else if (fileExtentions.contains("png") || fileExtentions.contains("jpeg") || fileExtentions.contains(
                    "jpg"
                )
            ) {
                v.fDisabilityAttachment.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.picture)
            } else {
                v.fDisabilityAttachment.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
            }

            if (preparence.getLanguage()
                    .equals("en")
            ) {


                v.fEmployeeType.tvText.text = employee?.employee_type?.employee_type

                v.fDisabilityDegree.tvText.text = employee?.disability_degree?.disability_degree
                v.fDisabilityType.tvText.text = employee?.disability_type?.disability_type
                employee?.employment_job_status?.employeementstatus?.name?.let {
                    v.fPresentGrossSalary.tvText.text = it
                }
            } else {

                v.fEmployeeType.tvText.text = employee?.employee_type?.employee_type_bn
                v.fDisabilityDegree.tvText.text = employee?.disability_degree?.disability_degree_bn
                v.fDisabilityType.tvText.text = employee?.disability_type?.disability_type_bn
                employee?.employment_job_status?.employeementstatus?.name_bn?.let {
                    v.fPresentGrossSalary.tvText.text = it
                }
            }

        }

        if ( employee?.employee_job_type_revenue?.job_type_id == 2){
            v.fEmployeePermanentConfirmDate.visibility = View.VISIBLE
            v.fEmployeePermanentDocument.visibility = View.VISIBLE
            employee?.employee_job_type_revenue?.permanent_confirmation_date.let { v.fEmployeePermanentConfirmDate.tvText.text =
                it }
            v.fEmployeePermanentDocument.tvText.setTextColor(
                ContextCompat.getColor(
                    ctx,
                    R.color.green
                )
            )
            v.fEmployeePermanentDocument.tvText.text = " Tap To View"
            val fileExtentions =
                ConvertNumber.getTheFileExtention( employee?.employee_job_type_revenue?.permanent_confirmation_attachment)
                    .lowercase(Locale.getDefault())


            if (fileExtentions.isEmpty()) {
                v.fEmployeePermanentDocument.tvText.text = " No Attachment"
            } else if (fileExtentions.contains("png") || fileExtentions.contains("jpeg") || fileExtentions.contains(
                    "jpg"
                )
            ) {
                v.fEmployeePermanentDocument.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.picture)
            } else {
                v.fEmployeePermanentDocument.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
            }
            v.fEmployeePermanentDocument.tvText.setOnClickListener {

                if (employee?.employee_job_type_revenue?.permanent_confirmation_attachment.isNullOrBlank()) {
                    Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
                } else {
                    // check action
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.BASE_URL + employee?.employee_job_type_revenue?.permanent_confirmation_attachment.toString())
                    )
                    startActivity(browserIntent)
                }
            }
        }else{
            v.fTemporaryRevenueType.visibility = View.VISIBLE
            v.fTemporaryRevenueTransferDate.visibility = View.VISIBLE
            v.fTemporaryRevenueTransferDoc.visibility = View.VISIBLE
            v.fRegularizationDate.visibility = View.VISIBLE
            v.fRegularizationDateDoc.visibility = View.VISIBLE

            if (preparence.getLanguage()
                    .equals("en")
            ) {
                employee?.employee_job_type_revenue?.temporary_revenue_type?.name.let {
                    v.fTemporaryRevenueType.tvText.text = it
                }
            }else{
                employee?.employee_job_type_revenue?.temporary_revenue_type?.name_bn.let {
                    v.fTemporaryRevenueType.tvText.text = it
                }
            }
            employee?.employee_job_type_revenue?.temporary_revenue_transfer_date.let {
                v.fTemporaryRevenueTransferDate.tvText.text = it
            }

            // Temporary Revenue TransferDate attachment
            v.fTemporaryRevenueTransferDoc.tvText.setTextColor(
                ContextCompat.getColor(
                    ctx,
                    R.color.green
                )
            )
            v.fTemporaryRevenueTransferDoc.tvText.text = " Tap To View"
            val fileExtentions =
                ConvertNumber.getTheFileExtention( employee?.employee_job_type_revenue?.temporary_revenue_transfer_attachment)
                    .lowercase(Locale.getDefault())


            if (fileExtentions.isEmpty()) {
                v.fTemporaryRevenueTransferDoc.tvText.text = " No Attachment"
            } else if (fileExtentions.contains("png") || fileExtentions.contains("jpeg") || fileExtentions.contains(
                    "jpg"
                )
            ) {
                v.fTemporaryRevenueTransferDoc.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.picture)
            } else {
                v.fTemporaryRevenueTransferDoc.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
            }
            v.fTemporaryRevenueTransferDoc.tvText.setOnClickListener {

                if (employee?.employee_job_type_revenue?.temporary_revenue_transfer_attachment.isNullOrBlank()) {
                    Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
                } else {
                    // check action
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.BASE_URL + employee?.employee_job_type_revenue?.temporary_revenue_transfer_attachment.toString())
                    )
                    startActivity(browserIntent)
                }
            }

            employee?.employee_job_type_revenue?.regularization_date.let {
                v.fRegularizationDate.tvText.text = it
            }

            // RegularizationDate attachment
            v.fRegularizationDateDoc.tvText.setTextColor(
                ContextCompat.getColor(
                    ctx,
                    R.color.green
                )
            )
            v.fRegularizationDateDoc.tvText.text = " Tap To View"
            val fileExtentions1 =
                ConvertNumber.getTheFileExtention( employee?.employee_job_type_revenue?.regularization_attachment)
                    .lowercase(Locale.getDefault())


            if (fileExtentions1.isEmpty()) {
                v.fRegularizationDateDoc.tvText.text = " No Attachment"
            } else if (fileExtentions1.contains("png") || fileExtentions1.contains("jpeg") || fileExtentions.contains(
                    "jpg"
                )
            ) {
                v.fRegularizationDateDoc.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.picture)
            } else {
                v.fRegularizationDateDoc.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
            }
            v.fRegularizationDateDoc.tvText.setOnClickListener {

                if (employee?.employee_job_type_revenue?.regularization_attachment.isNullOrBlank()) {
                    Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
                } else {
                    // check action
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.BASE_URL + employee?.employee_job_type_revenue?.regularization_attachment.toString())
                    )
                    startActivity(browserIntent)
                }
            }

        }

        if (preparence.getLanguage()
                .equals("en")
        ) {
            employee?.birth_place?.name.let { v.fHomeDistrict.tvText.text = it }
            employee?.employee_job_type_revenue?.job_type?.name.let { v.fJobType.tvText.text = it }
            employee?.gender?.name.let { v.fGender.tvText.text = it }
            employee?.marital_status?.name.let { v.fMaritalStatus.tvText.text = it }
            employee?.employee_type?.employee_type.let {
                v.fEmployeeType.tvText.text = it
            }

            v.fBloodGroup.tvText.text = employee?.blood_group?.name
            v.fReligion.tvText.text = employee?.religion?.name
            v.fEmployeeStatusType.tvText.text = employee?.employment_job_status?.employeementstatus?.name

        } else {
            employee?.birth_place?.name_bn.let { v.fHomeDistrict.tvText.text = it }
            employee?.employee_job_type_revenue?.job_type?.name_bn.let { v.fJobType.tvText.text = it }
            employee?.gender?.name_bn.let { v.fGender.tvText.text = it }
            employee?.marital_status?.name_bn.let { v.fMaritalStatus.tvText.text = it }
            employee?.employee_type?.employee_type_bn.let {
                v.fEmployeeType.tvText.text = it
            }
            v.fBloodGroup.tvText.text = employee?.blood_group?.name_bn
            v.fReligion.tvText.text = employee?.religion?.name_bn
            v.fEmployeeStatusType.tvText.text = employee?.employment_job_status?.employeementstatus?.name_bn
        }

//        activity?.let {
//            Glide.with(it).applyDefaultRequestOptions(
//                RequestOptions()
//                    .placeholder(R.drawable.ic_baseline_image_24)
//            ).load(RetrofitInstance.BASE_URL + employee?.photo)
//                .into(v.ivEmployee)
//        }

        Glide.with(ctx)
            .load(RetrofitInstance.BASE_URL + employee?.photo)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(v.ivEmployee)

        val page = activity

        //
        v.fEmployeeFreedomFighterAttachment.tvText
            .setOnClickListener {

                if (employee?.freedom_fighter_document_path.isNullOrBlank()) {
                    Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
                } else {
                    // check action
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.BASE_URL + employee?.freedom_fighter_document_path.toString())
                    )
                    startActivity(browserIntent)
                }
            }

        v.fDisabilityAttachment.tvText.setOnClickListener {
            // get the extention oof the file

            if (employee?.disability_document_path.isNullOrBlank()) {
                Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
            } else {

                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(RetrofitInstance.BASE_URL + employee?.disability_document_path.toString())
                )
                startActivity(browserIntent)

            }
        }



        v.hBasicInformation.tvEdit.setOnClickListener {
            employee?.let { it1 ->
                activity?.let { it2 ->
                    if (page != null) {
                        editEmployeeBasicInfoDialog.showDialog(
                            it1,
                            it2,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT,
                            page
                        )
                    }
                }
            }
        }

    }


   /*..................................................
   ................PENDING DATA SETTING................
   ....................................................*/
    private fun setPendingData(employee1: Employee?, name: String?) {

        context?.let {
            ContextCompat.getColor(
                it,
                R.color.headerColor
            )
        }?.let {
            v.hBasicInformation1.setBackgroundColor(
                it
            )
        }

        val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)

        v.fPresentBasicSalary1.llBody.visibility = View.GONE
        v.fPresentGrossSalary1.llBody.visibility = View.GONE

        v.hBasicInformation1.tvTitle.text = " ${getString(R.string.personal_information)}(${
            getString(
                R.string.pending_data
            )
        })"
        v.fEmployeeId1.tvTitle.text = getString(R.string.employee_id)

        v.fNameEng1.tvTitle.text = getString(R.string.name)

        v.fNameBangla1.tvTitle.text = getString(R.string.name_b)

        v.fDOB1.tvTitle.text = getString(R.string.birth)
        v.fGender1.tvTitle.text = getString(R.string.gender)
        v.fMaritalStatus1.tvTitle.text = getString(R.string.marital_status)
        v.fReligion1.tvTitle.text = getString(R.string.religion)
        v.fBloodGroup1.tvTitle.text = getString(R.string.blood_group)
        v.fPresentBasicSalary1.tvTitle.text = getString(R.string.present_basic_salary)
        v.fPresentGrossSalary1.tvTitle.text = getString(R.string.present_gross_salary)
        v.fTIN1.tvTitle.text = getString(R.string.tin_no)
        v.fPunchId1.tvTitle.text = getString(R.string.punch_id)

        v.fFatherNameEng1.tvTitle.text = getString(R.string.f_name)
        v.fFatherNameBangla1.tvTitle.text = getString(R.string.f_name_b)
        v.fMotherNameEng1.tvTitle.text = getString(R.string.m_name)
        v.fMotherNameBangla1.tvTitle.text = getString(R.string.m_name_b)
        v.fEmail1.tvTitle.text = getString(R.string.email)
        v.fUserName1.tvTitle.text = getString(R.string.user_name)
        v.fPhone1.tvTitle.text = getString(R.string.phone)
        v.fHomeDistrict1.tvTitle.text = getString(R.string.home_district)
        v.fEmployeeType1.tvTitle.text = getString(R.string.employee_type)
        v.fEmployeeJobJoiningDate1.tvTitle.text = getString(R.string.employment_job_joining_date)
        v.fPRLDate1.tvTitle.text = getString(R.string.prl_date)
        v.fPension_date1.tvTitle.text = getString(R.string.pension_date)
        v.fJobType1.tvTitle.text = getString(R.string.job_type)

        v.fEmployeePermanentConfirmDate1.tvTitle.text = getString(R.string.permanent_confirm_date)
        v.fEmployeePermanentDocument1.tvTitle.text = getString(R.string.permanent_document)


        v.fTemporaryRevenueType1.tvTitle.text = getString(R.string.temporary_revenue_type)
        v.fTemporaryRevenueTransferDate1.tvTitle.text = getString(R.string.temporary_revenue_transfer_date)
        v.fTemporaryRevenueTransferDoc1.tvTitle.text = getString(R.string.temporary_revenue_transfer_doc)
        v.fRegularizationDate1.tvTitle.text = getString(R.string.regularization_date)
        v.fRegularizationDateDoc1.tvTitle.text = getString(R.string.regularization_date_doc)


        v.fEmployeeStatusType1.tvTitle.text = getString(R.string.employee_status_type)
        v.fEmployeeStatusDate1.tvTitle.text = getString(R.string.employee_status_date)
        v.fEmployeeFreedomFighterquota1.tvTitle.text = getString(R.string.has_freedom_fighter_quota)
        v.fDisability1.tvTitle.text = getString(R.string.has_disability)
        v.tvImageTitle1.text = getString(R.string.photo)
        v.fEmployeeRole1.tvTitle.text = getString(R.string.employee_role)
        v.fDisabilityType1.tvTitle.text = getString(R.string.disability_type)
        v.fDisabilityDegree1.tvTitle.text = getString(R.string.disability_degree)
        v.fDisabledPersonId1.tvTitle.text = getString(R.string.disabled_person_id)
        v.fNid1.tvTitle.text = getString(R.string.nid_no)

        v.fEmployeeId1.tvText.text = "" + employee1?.profile_id
        employee1?.name?.let { v.fNameEng1.tvText.text = it }
        employee1?.name_bn?.let { v.fNameBangla1.tvText.text = it }
        employee1?.date_of_birth?.let {
            v.fDOB1.tvText.text = (DateConverter.changeDateFormateForShowing(
                it
            ))
        }

        employee1?.job_joining_date?.let {
            v.fEmployeeJobJoiningDate1.tvText.text = (DateConverter.changeDateFormateForShowing(
                it
            ))
        }
        employee1?.prl_date?.let { v.fPRLDate1.tvText.text = it }
        employee1?.pension_date?.let { v.fPension_date1.tvText.text = it }
        employee1?.fathers_name?.let { v.fFatherNameEng1.tvText.text = it }
        employee1?.fathers_name_bn?.let { v.fFatherNameBangla1.tvText.text = it }
        employee1?.mothers_name?.let { v.fMotherNameEng1.tvText.text = it }
        employee1?.mothers_name_bn?.let { v.fMotherNameBangla1.tvText.text = it }
        employee1?.user?.email?.let { v.fEmail1.tvText.text = it }
        employee1?.user?.username?.let { v.fUserName1.tvText.text = it }
        employee1?.phone_number?.let { v.fPhone1.tvText.text = it }
        employee1?.nid_no?.let { v.fNid1.tvText.text = it }
        employee1?.tin_no?.let { v.fTIN1.tvText.text = it }
        employee1?.punch_id?.let { v.fPunchId1.tvText.text = it }


        employee1?.present_basic_salary?.let { v.fPresentBasicSalary1.tvText.text = it }
        employee1?.present_gross_salary?.let { v.fPresentGrossSalary1.tvText.text = it }

        employee1?.employment_job_status?.status_date?.let {
            v.fEmployeeStatusDate1.tvText.text = (DateConverter.changeDateFormateForShowing(
                it
            ))
        }
        employee1?.user?.roles?.let {
            if (it.size > 0) {
                v.fEmployeeRole1.tvText.text = it.get(0).name
            }
        }

        employee1?.has_freedom_fighter_quota?.let {
            if (it) {
                v.fEmployeeFreedomFighterquota1.tvText.text = context?.getString(R.string.yes)
                // emp has the fredom fihter quta
                // decide what to do
                v.fEmployeeFreedomFighterAttachment1.llBody.visibility = View.VISIBLE
                // assgin text to the view
                v.fEmployeeFreedomFighterAttachment1.tvTitle.text =
                    context?.getString(R.string.attachment)
                v.fEmployeeFreedomFighterAttachment1.tvText.setTextColor(
                    ContextCompat.getColor(
                        ctx,
                        R.color.green
                    )
                )
                v.fEmployeeFreedomFighterAttachment1.tvText.text = " Tap To View"
                val fileExtentions =
                    ConvertNumber.getTheFileExtention(employee1.freedom_fighter_document_path)
                        .lowercase(Locale.getDefault())


                if (fileExtentions.isEmpty()) {
                    v.fEmployeeFreedomFighterAttachment1.tvText.text = " No Attachment"
                } else if (fileExtentions.contains("png") || fileExtentions.contains("jpeg") || fileExtentions.contains(
                        "jpg"
                    )
                ) {
                    v.fEmployeeFreedomFighterAttachment1.icon.background =
                        ContextCompat.getDrawable(ctx, R.drawable.picture)
                } else {
                    v.fEmployeeFreedomFighterAttachment1.icon.background =
                        ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
                }

            } else {
                v.fEmployeeFreedomFighterquota1.tvText.text = context?.getString(R.string.no)
                v.fEmployeeFreedomFighterAttachment1.llBody.visibility = View.GONE
            }

        }


        if (employee1?.has_disability == false) {

            v.fDisability1.tvText.text = "" + context?.getString(R.string.no)
            v.fDisabilityDegree1.llBody.visibility = View.GONE
            v.fDisabilityType1?.llBody?.visibility = View.GONE
            v.fDisabledPersonId1?.llBody?.visibility = View.GONE
            v.fDisabilityAttachment1.llBody.visibility = View.GONE

        } else {
            v.fDisability1.tvText.text = "" + context?.getString(R.string.yes)
            v.fDisabilityDegree1?.llBody?.visibility = View.VISIBLE
            v.fDisabilityType1?.llBody?.visibility = View.VISIBLE
            v.fDisabledPersonId1?.llBody?.visibility = View.VISIBLE
            v.fDisabledPersonId1.tvText.text = employee1?.disabled_person_id

            v.fDisabilityAttachment1.llBody.visibility = View.VISIBLE
            v.fDisabilityAttachment1.tvTitle.text = context?.getString(R.string.attachment)
            v.fDisabilityAttachment1.tvText.setTextColor(ContextCompat.getColor(ctx, R.color.green))
            v.fDisabilityAttachment1.tvText.text = " Tap To View"

            val fileExtentions =
                ConvertNumber.getTheFileExtention(employee1?.disability_document_path)
                    .lowercase(Locale.getDefault())


            if (fileExtentions.isEmpty()) {
                v.fEmployeeFreedomFighterAttachment1.tvText.text = " No Attachment"
            } else if (fileExtentions.contains("png") || fileExtentions.contains("jpeg") || fileExtentions.contains(
                    "jpg"
                )
            ) {
                v.fDisabilityAttachment1.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.picture)
            } else {
                v.fDisabilityAttachment1.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
            }

            if (preparence.getLanguage()
                    .equals("en")
            ) {

                val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)




                v.fEmployeeType1.tvText.text = employee1?.employee_type?.employee_type
                v.fDisabilityDegree1.tvText.text = employee1?.disability_degree_id?.let {
                    HelperClass.getCommonDataFilltered(
                        it,
                        commonData?.disability_degrees,
                        false
                    )
                }

                v.fDisabilityType1.tvText.text = employee1?.disability_type_id?.let {
                    HelperClass.getCommonDataFilltered(
                        it,
                        commonData?.disability_types,
                        false
                    )
                }
//                employee1?.employment_job_status?.employeementstatus?.name?.let {
//                    v.fEmployeeStatusType1.tvText.setText(
//                        it
//
                //  }
            } else {

                v.fEmployeeType1.tvText.text = employee1?.employee_type?.employee_type_bn

                v.fDisabilityDegree1.tvText.text = employee1?.disability_degree_id?.let {
                    HelperClass.getCommonDataFilltered(
                        it,
                        commonData?.disability_degrees,
                        true
                    )
                }

                v.fDisabilityType1.tvText.text = employee1?.disability_type_id?.let {
                    HelperClass.getCommonDataFilltered(
                        it,
                        commonData?.disability_types,
                        true
                    )
                }

            }

        }

        if ( employee1?.employee_job_type_revenue?.job_type_id == 2){
            v.fEmployeePermanentConfirmDate1.visibility = View.VISIBLE
            v.fEmployeePermanentDocument1.visibility = View.VISIBLE
            employee1.employee_job_type_revenue.permanent_confirmation_date.let { v.fEmployeePermanentConfirmDate1.tvText.text =
                it }
            v.fEmployeePermanentDocument1.tvText.setTextColor(
                ContextCompat.getColor(
                    ctx,
                    R.color.green
                )
            )
            v.fEmployeePermanentDocument1.tvText.text = " Tap To View"
            val fileExtentions =
                ConvertNumber.getTheFileExtention( employee?.employee_job_type_revenue?.permanent_confirmation_attachment)
                    .lowercase(Locale.getDefault())


            if (fileExtentions.isEmpty()) {
                v.fEmployeePermanentDocument1.tvText.text = " No Attachment"
            } else if (fileExtentions.contains("png") || fileExtentions.contains("jpeg") || fileExtentions.contains(
                    "jpg"
                )
            ) {
                v.fEmployeePermanentDocument1.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.picture)
            } else {
                v.fEmployeePermanentDocument1.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
            }

        }else{
            v.fTemporaryRevenueType1.visibility = View.VISIBLE
            v.fTemporaryRevenueTransferDate1.visibility = View.VISIBLE
            v.fTemporaryRevenueTransferDoc1.visibility = View.VISIBLE
            v.fEmployeePermanentDocument1.visibility = View.VISIBLE
            v.fRegularizationDate1.visibility = View.VISIBLE
            v.fRegularizationDateDoc1.visibility = View.VISIBLE


            if (preparence.getLanguage()
                    .equals("en")
            ) {
                employee1?.employee_job_type_revenue?.temporary_revenue_type?.name.let {
                    v.fTemporaryRevenueType1.tvText.text = it
                }
            }else{
                employee1?.employee_job_type_revenue?.temporary_revenue_type?.name_bn.let {
                    v.fTemporaryRevenueType1.tvText.text = it
                }
            }
            employee1?.employee_job_type_revenue?.temporary_revenue_transfer_date.let {
                v.fTemporaryRevenueTransferDate1.tvText.text = it
            }

            // Temporary Revenue TransferDate attachment
            v.fTemporaryRevenueTransferDoc1.tvText.setTextColor(
                ContextCompat.getColor(
                    ctx,
                    R.color.green
                )
            )
            v.fTemporaryRevenueTransferDoc1.tvText.text = " Tap To View"
            val fileExtentions =
                ConvertNumber.getTheFileExtention( employee1?.employee_job_type_revenue?.temporary_revenue_transfer_attachment)
                    .lowercase(Locale.getDefault())


            if (fileExtentions.isEmpty()) {
                v.fTemporaryRevenueTransferDoc1.tvText.text = " No Attachment"
            } else if (fileExtentions.contains("png") || fileExtentions.contains("jpeg") || fileExtentions.contains(
                    "jpg"
                )
            ) {
                v.fTemporaryRevenueTransferDoc1.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.picture)
            } else {
                v.fTemporaryRevenueTransferDoc1.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
            }
            v.fTemporaryRevenueTransferDoc1.tvText.setOnClickListener {

                if (employee1?.employee_job_type_revenue?.temporary_revenue_transfer_attachment.isNullOrBlank()) {
                    Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
                } else {
                    // check action
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.BASE_URL + employee1?.employee_job_type_revenue?.temporary_revenue_transfer_attachment.toString())
                    )
                    startActivity(browserIntent)
                }
            }

            employee1?.employee_job_type_revenue?.regularization_date.let {
                v.fRegularizationDate1.tvText.text = it
            }

            // RegularizationDate attachment
            v.fRegularizationDateDoc1.tvText.setTextColor(
                ContextCompat.getColor(
                    ctx,
                    R.color.green
                )
            )
            v.fRegularizationDateDoc1.tvText.text = " Tap To View"
            val fileExtentions1 =
                ConvertNumber.getTheFileExtention( employee1?.employee_job_type_revenue?.regularization_attachment)
                    .lowercase(Locale.getDefault())


            if (fileExtentions1.isEmpty()) {
                v.fRegularizationDateDoc1.tvText.text = " No Attachment"
            } else if (fileExtentions1.contains("png") || fileExtentions1.contains("jpeg") || fileExtentions.contains(
                    "jpg"
                )
            ) {
                v.fRegularizationDateDoc1.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.picture)
            } else {
                v.fRegularizationDateDoc1.icon.background =
                    ContextCompat.getDrawable(ctx, R.drawable.ic_pdf)
            }
            v.fRegularizationDateDoc1.tvText.setOnClickListener {

                if (employee1?.employee_job_type_revenue?.regularization_attachment.isNullOrBlank()) {
                    Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
                } else {
                    // check action
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.BASE_URL + employee?.employee_job_type_revenue?.regularization_attachment.toString())
                    )
                    startActivity(browserIntent)
                }
            }

        }

        if (preparence.getLanguage()
                .equals("en")
        ) {
            employee1?.birth_place?.name.let { v.fHomeDistrict1.tvText.text = it }
            v.fEmployeeType1.tvText.text = employee1?.employee_type?.employee_type
            employee1?.employee_job_type_revenue?.job_type?.name.let { v.fJobType1.tvText.text = it }
            v.fEmployeeStatusType1.tvText.text = employee1?.employment_job_status?.employeementstatus?.name
            v.fGender1.tvText.text = "${
                employee1?.gender_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.genders, false)
                }
            }"


            v.fMaritalStatus1.tvText.text = "${
                employee1?.marital_status_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.marital_status, false)
                }
            }"

            v.fEmployeeType1.tvText.text = "${
                employee1?.employee_type_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.employee_types, false)
                }
            }"



            v.fBloodGroup1.tvText.text = "${
                employee1?.blood_group_id?.let {
                    HelperClass.getCommonDataFilltered(
                        it,
                        commonData?.blood_group,
                        false
                    )
                }
            }"

            v.fReligion1.tvText.text = "${
                employee1?.religion_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.religions, false)
                }
            }"

//            v.fEmployeeStatusType1.tvText.setText(employee1?.employment_job_status?.employee1mentstatus?.name)

        } else {

            employee1?.employee_job_type_revenue?.job_type?.name_bn.let { v.fJobType1.tvText.text =
                it }
            employee1?.birth_place?.name_bn.let { v.fHomeDistrict1.tvText.text = it }
            v.fEmployeeType1.tvText.text = employee1?.employee_type?.employee_type
            v.fEmployeeStatusType1.tvText.text = employee1?.employment_job_status?.employeementstatus?.name
            v.fGender1.tvText.text = "${
                employee1?.gender_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.genders, true)
                }
            }"


            v.fMaritalStatus1.tvText.text = "${
                employee1?.marital_status_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.marital_status, true)
                }
            }"

            v.fEmployeeType1.tvText.text = "${
                employee1?.employee_type_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.employee_types, true)
                }
            }"




            v.fBloodGroup1.tvText.text = "${
                employee1?.blood_group_id?.let {
                    HelperClass.getCommonDataFilltered(
                        it,
                        commonData?.blood_group,
                        true
                    )
                }
            }"
            v.fReligion1.tvText.text = "${
                employee1?.religion_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.religions, true)
                }
            }"

        }

//        activity?.let {
//            Glide.with(it).applyDefaultRequestOptions(
//                RequestOptions()
//                    .placeholder(R.drawable.ic_baseline_image_24)
//            ).load(RetrofitInstance.BASE_URL + employee1?.photo)
//                .into(v.ivEmployee)
//        }

        Glide.with(ctx)
            .load(RetrofitInstance.BASE_URL + employee1?.photo)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(v.ivEmployee1)

        val page = activity

        //
        v.fEmployeeFreedomFighterAttachment1.tvText
            .setOnClickListener {

                if (employee1?.freedom_fighter_document_path.isNullOrBlank()) {
                    Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
                } else {
                    // check action
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.BASE_URL + employee1?.freedom_fighter_document_path.toString())
                    )
                    startActivity(browserIntent)
                }
            }

        v.fDisabilityAttachment1.tvText.setOnClickListener {
            // get the extention oof the file

            if (employee1?.disability_document_path.isNullOrBlank()) {
                Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
            } else {

                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(RetrofitInstance.BASE_URL + employee1?.disability_document_path.toString())
                )
                startActivity(browserIntent)

            }
        }



        v.hBasicInformation1.tvEdit.setOnClickListener {
            employee1?.let { it1 ->
                activity?.let { it2 ->
                    if (page != null) {
                        editEmployeeBasicInfoDialog.showDialog(
                            it1,
                            it2,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT,
                            page
                        )
                    }
                }
            }
        }


    }

    fun verifyStoragePermissions(context: Context) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                context as Activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    private fun openSelectImageBottomSheet() {
        selectImageBottomSheet = SelectImageBottomSheet(this)
        activity?.supportFragmentManager
            ?.let { selectImageBottomSheet?.show(it, "selectImage") }
    }

    override fun onCameraButtonClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) }
                == PackageManager.PERMISSION_GRANTED
            ) {
                dispatchTakePictureIntent()
            } else {
                activity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(Manifest.permission.CAMERA),
                        1030
                    )
                }
            }
        } else {
            dispatchTakePictureIntent()
        }
        selectImageBottomSheet!!.dismiss()
    }

    override fun onGalleryButtonClicked() {

        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryIntent.type = "*/*"
        val mimetypes = arrayOf("image/*", "application/pdf", "application/msword")
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        startActivityForResult(galleryIntent, REQUEST_SELECT_PHOTO)
        selectImageBottomSheet!!.dismiss()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1030) {
            onCameraButtonClicked()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val resultUri: Uri? = Uri.fromFile(File(currentPhotoPath))
            try {
                activity?.let {
                    resultUri?.let { it1 ->
                        FilePath().getPath(
                            it,
                            it1
                        )?.let { getImageFile(it) }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var bitmap: Bitmap? = null
            try {
                bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, resultUri)
            } catch (e: Exception) {
                bitmap = null

            }


            imageFile?.let { onFilevalueReceiveListener?.onFileValue(it, bitmap) }
//            imageFile?.let {
//                bitmap?.let { it1 ->
//                    resultUri?.let { it2 ->
//                        onFilevalueReceiveListener?.onFileValue(it, it1)
//                    }
//                }
//            }
        } else if (requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Log.e("url", "uri .......................................................${data.data}")
            val resultUri: Uri? = data.data
            try {
                activity?.let {
                    resultUri?.let { it1 ->
                        FilePath().getPath(
                            it,
                            it1
                        )?.let { getImageFile(it) }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val bitmap =
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, resultUri)

            imageFile?.let {
                bitmap?.let { it1 ->
                    resultUri?.let { it2 ->
                        onFilevalueReceiveListener?.onFileValue(it, it1)
                    }
                }
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

        }
    }


    private fun getImageFile(photoPath: String) {
        imageFile = File(photoPath)

    }

    private fun setImageToView(bitmap: Bitmap) {
        // headerBinding.profileImageCIV.setImageBitmap(bitmap)
    }


    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        currentPhotoPath = image.absolutePath
        return image
    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (activity?.packageManager?.let { takePictureIntent.resolveActivity(it) } != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ignored: IOException) {
            }
            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireActivity(),
                    "com.dss.hrms.customerfileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    fun getPath(uri: Uri?): String? {
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor =
            uri?.let { activity?.contentResolver?.query(it, projection, null, null, null) }
                ?: return null
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s: String = cursor.getString(column_index)
        cursor.close()
        return s
    }

    private fun compressImage() {
        val bitmap: Bitmap = BitmapFactory.decodeFile(currentPhotoPath)
        var ei: ExifInterface? = null
        try {
            ei = currentPhotoPath?.let { ExifInterface(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var orientation = 0
        if (ei != null) {
            orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
        }
        val rotatedBitmap: Bitmap
        rotatedBitmap = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
            ExifInterface.ORIENTATION_NORMAL -> bitmap
            else -> bitmap
        }
        setImageToView(rotatedBitmap)
    }


    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    fun passDataToDialogueFragment(imagePath: String) {
        editEmployeeBasicInfoDialog.uploadFile(imagePath)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BasicInformationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}