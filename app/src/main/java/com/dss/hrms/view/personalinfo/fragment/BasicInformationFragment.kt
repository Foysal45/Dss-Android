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
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.bottomsheet.SelectImageBottomSheet
import com.dss.hrms.view.personalinfo.dialog.EditEmployeeBasicInfoDialog
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import kotlinx.android.synthetic.main.fragment_basic_information.view.fBloodGroup
import kotlinx.android.synthetic.main.fragment_basic_information.view.fDOB
import kotlinx.android.synthetic.main.fragment_basic_information.view.fDisability
import kotlinx.android.synthetic.main.fragment_basic_information.view.fDisabilityDegree
import kotlinx.android.synthetic.main.fragment_basic_information.view.fDisabilityType
import kotlinx.android.synthetic.main.fragment_basic_information.view.fDisabledPersonId
import kotlinx.android.synthetic.main.fragment_basic_information.view.fEmail
import kotlinx.android.synthetic.main.fragment_basic_information.view.fEmployeeType
import kotlinx.android.synthetic.main.fragment_basic_information.view.fFatherNameBangla
import kotlinx.android.synthetic.main.fragment_basic_information.view.fFatherNameEng
import kotlinx.android.synthetic.main.fragment_basic_information.view.fGender
import kotlinx.android.synthetic.main.fragment_basic_information.view.fMaritalStatus
import kotlinx.android.synthetic.main.fragment_basic_information.view.fMotherNameBangla
import kotlinx.android.synthetic.main.fragment_basic_information.view.fMotherNameEng
import kotlinx.android.synthetic.main.fragment_basic_information.view.fNameBangla
import kotlinx.android.synthetic.main.fragment_basic_information.view.fNameEng
import kotlinx.android.synthetic.main.fragment_basic_information.view.fNid
import kotlinx.android.synthetic.main.fragment_basic_information.view.fPhone
import kotlinx.android.synthetic.main.fragment_basic_information.view.fPresentBasicSalary
import kotlinx.android.synthetic.main.fragment_basic_information.view.fPresentGrossSalary
import kotlinx.android.synthetic.main.fragment_basic_information.view.fPunchId
import kotlinx.android.synthetic.main.fragment_basic_information.view.fReligion
import kotlinx.android.synthetic.main.fragment_basic_information.view.fTIN
import kotlinx.android.synthetic.main.fragment_basic_information.view.fUserName
import kotlinx.android.synthetic.main.fragment_basic_information.view.hBasicInformation
import kotlinx.android.synthetic.main.fragment_basic_information.view.ivEmployee
import kotlinx.android.synthetic.main.fragment_basic_information.view.tvImageTitle
import kotlinx.android.synthetic.main.personal_information_header_field.view.*
import kotlinx.android.synthetic.main.personel_information_view_field.view.llBody
import kotlinx.android.synthetic.main.personel_information_view_field.view.tvText
import kotlinx.android.synthetic.main.personel_information_view_field.view.tvTitle
import kotlinx.android.synthetic.main.personel_information_view_field_with_icon.view.*
import java.io.File
import java.io.IOException
import java.lang.Exception
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
            pendingEmployee[0].data?.employment_job_status?.employeementstatus =
                employee?.employment_job_status?.employeementstatus
            pendingEmployee[0].data?.user = employee?.user

            setPendingData(
                pendingEmployee[0].data,
                employee?.employment_job_status?.employeementstatus?.name
            )
        }



        EmployeeInfoActivity.context?.let { verifyStoragePermissions(it) }

        return v
    }

    fun setData() {

        v.fPresentBasicSalary.llBody.visibility = View.GONE
        v.fPresentGrossSalary.llBody.visibility = View.GONE

        v.hBasicInformation.tvTitle.setText(getString(R.string.personal_information))
        v.fEmployeeId.tvTitle.setText(getString(R.string.employee_id))

        v.fNameEng.tvTitle.setText(getString(R.string.name))

        v.fNameBangla.tvTitle.setText(getString(R.string.name_b))

        v.fDOB.tvTitle.setText(getString(R.string.birth))
        v.fGender.tvTitle.setText(getString(R.string.gender))
        v.fMaritalStatus.tvTitle.setText(getString(R.string.marital_status))
        v.fReligion.tvTitle.setText(getString(R.string.religion))
        v.fBloodGroup.tvTitle.setText(getString(R.string.blood_group))
        v.fPresentBasicSalary.tvTitle.setText(getString(R.string.present_basic_salary))
        v.fPresentGrossSalary.tvTitle.setText(getString(R.string.present_gross_salary))
        v.fTIN.tvTitle.setText(getString(R.string.tin_no))
        v.fPunchId.tvTitle.setText(getString(R.string.punch_id))

        v.fFatherNameEng.tvTitle.setText(getString(R.string.f_name))
        v.fFatherNameBangla.tvTitle.setText(getString(R.string.f_name_b))
        v.fMotherNameEng.tvTitle.setText(getString(R.string.m_name))
        v.fMotherNameBangla.tvTitle.setText(getString(R.string.m_name_b))
        v.fEmail.tvTitle.setText(getString(R.string.email))
        v.fUserName.tvTitle.setText(getString(R.string.user_name))
        v.fPhone.tvTitle.setText(getString(R.string.phone))
        v.fEmployeeType.tvTitle.setText(getString(R.string.employee_type))
        v.fEmployeeJobJoiningDate.tvTitle.setText(getString(R.string.employment_job_joining_date))
        v.fEmployeeStatusType.tvTitle.setText(getString(R.string.employee_status_type))
        v.fEmployeeStatusDate.tvTitle.setText(getString(R.string.employee_status_date))
        v.fEmployeeFreedomFighterquota.tvTitle.setText(getString(R.string.has_freedom_fighter_quota))
        v.fDisability.tvTitle.setText(getString(R.string.has_disability))
        v.tvImageTitle.setText(getString(R.string.photo))
        v.fEmployeeRole.tvTitle.setText(getString(R.string.employee_role))
        v.fDisabilityType.tvTitle.setText(getString(R.string.disability_type))
        v.fDisabilityDegree.tvTitle.setText(getString(R.string.disability_degree))
        v.fDisabledPersonId.tvTitle.setText(getString(R.string.disabled_person_id))
        v.fNid.tvTitle.setText(getString(R.string.nid_no))



        v.fEmployeeId.tvText.setText("" + employee?.profile_id)
        employee?.name?.let { v.fNameEng.tvText.setText(it) }
        employee?.name_bn?.let { v.fNameBangla.tvText.setText(it) }
        employee?.date_of_birth?.let {
            v.fDOB.tvText.setText(
                (DateConverter.changeDateFormateForShowing(
                    it
                ))
            )
        }
        employee?.job_joining_date?.let {
            v.fEmployeeJobJoiningDate.tvText.setText(
                (DateConverter.changeDateFormateForShowing(
                    it
                ))
            )
        }
        employee?.fathers_name?.let { v.fFatherNameEng.tvText.setText(it) }
        employee?.fathers_name_bn?.let { v.fFatherNameBangla.tvText.setText(it) }
        employee?.mothers_name?.let { v.fMotherNameEng.tvText.setText(it) }
        employee?.mothers_name_bn?.let { v.fMotherNameBangla.tvText.setText(it) }
        employee?.user?.email?.let { v.fEmail.tvText.setText(it) }
        employee?.user?.username?.let { v.fUserName.tvText.setText(it) }
        employee?.user?.phone_number?.let { v.fPhone.tvText.setText(it) }
        employee?.nid_no?.let { v.fNid.tvText.setText(it) }
        employee?.tin_no?.let { v.fTIN.tvText.setText(it) }
        employee?.punch_id?.let { v.fPunchId.tvText.setText(it) }
        employee?.present_basic_salary?.let { v.fPresentBasicSalary.tvText.setText(it) }
        employee?.present_gross_salary?.let { v.fPresentGrossSalary.tvText.setText(it) }
        employee?.employment_job_status?.status_date?.let {
            v.fEmployeeStatusDate.tvText.setText(
                (DateConverter.changeDateFormateForShowing(
                    it
                ))
            )
        }
        employee?.user?.roles?.let {
            if (it.size > 0) {
                v.fEmployeeRole.tvText.setText(it.get(0).name)
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
                        .toLowerCase()


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
            v.fDisability.tvText.setText("" + context?.getString(R.string.no))
            v.fDisabilityDegree.llBody.visibility = View.GONE
            v.fDisabilityType?.llBody?.visibility = View.GONE
            v.fDisabledPersonId?.llBody?.visibility = View.GONE
            v.fDisabilityAttachment.llBody.visibility = View.GONE

        } else {
            v.fDisability.tvText.setText("" + context?.getString(R.string.yes))
            v.fDisabilityDegree?.llBody?.visibility = View.VISIBLE
            v.fDisabilityType?.llBody?.visibility = View.VISIBLE
            v.fDisabledPersonId?.llBody?.visibility = View.VISIBLE
            v.fDisabledPersonId.tvText.setText(employee?.disabled_person_id)

            v.fDisabilityAttachment.llBody.visibility = View.VISIBLE
            v.fDisabilityAttachment.tvTitle.text = context?.getString(R.string.attachment)
            v.fDisabilityAttachment.tvText.setTextColor(ContextCompat.getColor(ctx, R.color.green))
            v.fDisabilityAttachment.tvText.text = " Tap To View"

            val fileExtentions =
                ConvertNumber.getTheFileExtention(employee?.disability_document_path)
                    .toLowerCase()


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

            if (preparence?.getLanguage()
                    .equals("en")
            ) {


                v.fEmployeeType.tvText.setText(employee?.employee_type?.employee_type)
                v.fDisabilityDegree.tvText.setText(employee?.disability_degree?.disability_degree)
                v.fDisabilityType.tvText.setText(employee?.disability_type?.disability_type)
                employee?.employment_job_status?.employeementstatus?.name?.let {
                    v.fPresentGrossSalary.tvText.setText(
                        it
                    )
                }
            } else {
                v.fEmployeeType.tvText.setText(employee?.employee_type?.employee_type_bn)
                v.fDisabilityDegree.tvText.setText(employee?.disability_degree?.disability_degree_bn)
                v.fDisabilityType.tvText.setText(employee?.disability_type?.disability_type_bn)
                employee?.employment_job_status?.employeementstatus?.name_bn?.let {
                    v.fPresentGrossSalary.tvText.setText(
                        it
                    )
                }
            }

        }
        if (preparence?.getLanguage()
                .equals("en")
        ) {
            employee?.gender?.name.let { v.fGender.tvText.setText(it) }
            employee?.marital_status?.name.let { v.fMaritalStatus.tvText.setText(it) }
            employee?.employee_type?.employee_type.let {
                v.fEmployeeType.tvText.setText(
                    it
                )
            }

            v.fBloodGroup.tvText.setText(employee?.blood_group?.name)
            v.fReligion.tvText.setText(employee?.religion?.name)
            v.fEmployeeStatusType.tvText.setText(employee?.employment_job_status?.employeementstatus?.name)

        } else {
            employee?.gender?.name_bn.let { v.fGender.tvText.setText(it) }
            employee?.marital_status?.name_bn.let { v.fMaritalStatus.tvText.setText(it) }
            employee?.employee_type?.employee_type_bn.let {
                v.fEmployeeType.tvText.setText(
                    it
                )
            }
            v.fBloodGroup.tvText.setText(employee?.blood_group?.name_bn)
            v.fReligion.tvText.setText(employee?.religion?.name_bn)
            v.fEmployeeStatusType.tvText.setText(employee?.employment_job_status?.employeementstatus?.name_bn)
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

    fun setPendingData(employee1: Employee?, name: String?) {

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
        v.fEmployeeId1.tvTitle.setText(getString(R.string.employee_id))

        v.fNameEng1.tvTitle.setText(getString(R.string.name))

        v.fNameBangla1.tvTitle.setText(getString(R.string.name_b))

        v.fDOB1.tvTitle.setText(getString(R.string.birth))
        v.fGender1.tvTitle.setText(getString(R.string.gender))
        v.fMaritalStatus1.tvTitle.setText(getString(R.string.marital_status))
        v.fReligion1.tvTitle.setText(getString(R.string.religion))
        v.fBloodGroup1.tvTitle.setText(getString(R.string.blood_group))
        v.fPresentBasicSalary1.tvTitle.setText(getString(R.string.present_basic_salary))
        v.fPresentGrossSalary1.tvTitle.setText(getString(R.string.present_gross_salary))
        v.fTIN1.tvTitle.setText(getString(R.string.tin_no))
        v.fPunchId1.tvTitle.setText(getString(R.string.punch_id))

        v.fFatherNameEng1.tvTitle.setText(getString(R.string.f_name))
        v.fFatherNameBangla1.tvTitle.setText(getString(R.string.f_name_b))
        v.fMotherNameEng1.tvTitle.setText(getString(R.string.m_name))
        v.fMotherNameBangla1.tvTitle.setText(getString(R.string.m_name_b))
        v.fEmail1.tvTitle.setText(getString(R.string.email))
        v.fUserName1.tvTitle.setText(getString(R.string.user_name))
        v.fPhone1.tvTitle.setText(getString(R.string.phone))
        v.fEmployeeType1.tvTitle.setText(getString(R.string.employee_type))
        v.fEmployeeJobJoiningDate1.tvTitle.setText(getString(R.string.employment_job_joining_date))
        v.fEmployeeStatusType1.tvTitle.setText(getString(R.string.employee_status_type))
        v.fEmployeeStatusDate1.tvTitle.setText(getString(R.string.employee_status_date))
        v.fEmployeeFreedomFighterquota1.tvTitle.setText(getString(R.string.has_freedom_fighter_quota))
        v.fDisability1.tvTitle.setText(getString(R.string.has_disability))
        v.tvImageTitle1.setText(getString(R.string.photo))
        v.fEmployeeRole1.tvTitle.setText(getString(R.string.employee_role))
        v.fDisabilityType1.tvTitle.setText(getString(R.string.disability_type))
        v.fDisabilityDegree1.tvTitle.setText(getString(R.string.disability_degree))
        v.fDisabledPersonId1.tvTitle.setText(getString(R.string.disabled_person_id))
        v.fNid1.tvTitle.setText(getString(R.string.nid_no))

        v.fEmployeeId1.tvText.setText("" + employee1?.profile_id)
        employee1?.name?.let { v.fNameEng1.tvText.setText(it) }
        employee1?.name_bn?.let { v.fNameBangla1.tvText.setText(it) }
        employee1?.date_of_birth?.let {
            v.fDOB1.tvText.setText(
                (DateConverter.changeDateFormateForShowing(
                    it
                ))
            )
        }
        employee1?.job_joining_date?.let {
            v.fEmployeeJobJoiningDate1.tvText.setText(
                (DateConverter.changeDateFormateForShowing(
                    it
                ))
            )
        }
        employee1?.fathers_name?.let { v.fFatherNameEng1.tvText.setText(it) }
        employee1?.fathers_name_bn?.let { v.fFatherNameBangla1.tvText.setText(it) }
        employee1?.mothers_name?.let { v.fMotherNameEng1.tvText.setText(it) }
        employee1?.mothers_name_bn?.let { v.fMotherNameBangla1.tvText.setText(it) }
        employee1?.user?.email?.let { v.fEmail1.tvText.setText(it) }
        employee1?.user?.username?.let { v.fUserName1.tvText.setText(it) }
        employee1?.phone_number?.let { v.fPhone1.tvText.setText(it) }
        employee1?.nid_no?.let { v.fNid1.tvText.setText(it) }
        employee1?.tin_no?.let { v.fTIN1.tvText.setText(it) }
        employee1?.punch_id?.let { v.fPunchId1.tvText.setText(it) }
        employee1?.present_basic_salary?.let { v.fPresentBasicSalary1.tvText.setText(it) }
        employee1?.present_gross_salary?.let { v.fPresentGrossSalary1.tvText.setText(it) }

        employee1?.employment_job_status?.status_date?.let {
            v.fEmployeeStatusDate1.tvText.setText(
                (DateConverter.changeDateFormateForShowing(
                    it
                ))
            )
        }
        employee1?.user?.roles?.let {
            if (it.size > 0) {
                v.fEmployeeRole1.tvText.setText(it.get(0).name)
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
                    ConvertNumber.getTheFileExtention(employee1?.freedom_fighter_document_path)
                        .toLowerCase()


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
            Log.e("hasdisability", "" + employee1?.has_disability)
            v.fDisability1.tvText.setText("" + context?.getString(R.string.no))
            v.fDisabilityDegree1.llBody.visibility = View.GONE
            v.fDisabilityType1?.llBody?.visibility = View.GONE
            v.fDisabledPersonId1?.llBody?.visibility = View.GONE
            v.fDisabilityAttachment1.llBody.visibility = View.GONE

        } else {
            v.fDisability1.tvText.setText("" + context?.getString(R.string.yes))
            v.fDisabilityDegree1?.llBody?.visibility = View.VISIBLE
            v.fDisabilityType1?.llBody?.visibility = View.VISIBLE
            v.fDisabledPersonId1?.llBody?.visibility = View.VISIBLE
            v.fDisabledPersonId1.tvText.setText(employee1?.disabled_person_id)

            v.fDisabilityAttachment1.llBody.visibility = View.VISIBLE
            v.fDisabilityAttachment1.tvTitle.text = context?.getString(R.string.attachment)
            v.fDisabilityAttachment1.tvText.setTextColor(ContextCompat.getColor(ctx, R.color.green))
            v.fDisabilityAttachment1.tvText.text = " Tap To View"

            val fileExtentions =
                ConvertNumber.getTheFileExtention(employee1?.disability_document_path)
                    .toLowerCase()


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

            if (preparence?.getLanguage()
                    .equals("en")
            ) {


                v.fEmployeeType1.tvText.setText(employee1?.employee_type?.employee_type)
                v.fDisabilityDegree1.tvText.setText(employee1?.disability_degree?.disability_degree)
                v.fDisabilityType1.tvText.setText(employee1?.disability_type?.disability_type)
//                employee1?.employment_job_status?.employeementstatus?.name?.let {
//                    v.fEmployeeStatusType1.tvText.setText(
//                        it
//
                //  }
            } else {

                v.fEmployeeType1.tvText.setText(employee1?.employee_type?.employee_type_bn)
                v.fDisabilityDegree1.tvText.setText(employee1?.disability_degree?.disability_degree_bn)
                v.fDisabilityType1.tvText.setText(employee1?.disability_type?.disability_type_bn)

            }

        }


        if (preparence?.getLanguage()
                .equals("en")
        ) {

            v.fEmployeeType1.tvText.setText(employee1?.employee_type?.employee_type)
            v.fEmployeeStatusType1.tvText.setText(employee1?.employment_job_status?.employeementstatus?.name)
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



            v.fBloodGroup1.tvText.setText(
                "${
                    employee1?.blood_group_id?.let {
                        HelperClass.getCommonDataFilltered(
                            it,
                            commonData?.blood_group,
                            false
                        )
                    }
                }"
            )

            v.fReligion1.tvText.text = "${
                employee1?.religion_id?.let {
                    HelperClass.getCommonDataFilltered(it, commonData?.religions, false)
                }
            }"

//            v.fEmployeeStatusType1.tvText.setText(employee1?.employment_job_status?.employee1mentstatus?.name)

        } else {
            v.fEmployeeType1.tvText.setText(employee1?.employee_type?.employee_type)
            v.fEmployeeStatusType1.tvText.setText(employee1?.employment_job_status?.employeementstatus?.name)
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
        activity?.getSupportFragmentManager()
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
        galleryIntent.setType("*/*");
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
            onCameraButtonClicked();
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
                };
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var bitmap: Bitmap? = null
            try {
                bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), resultUri)
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
                };
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val bitmap =
                MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), resultUri)

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
        if (activity?.getPackageManager()?.let { takePictureIntent.resolveActivity(it) } != null) {
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
            uri?.let { activity?.getContentResolver()?.query(it, projection, null, null, null) }
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