package com.dss.hrms.view.leave.fragment

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentCreateEditLeaveApplicationBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.*
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.leave.adapter.spinner.LeavePolicySpinnerAdapter
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.leave.viewmodel.LeaveApplicationViewmodel
import com.dss.hrms.view.messaging.fragment.SearchEmployeeFragmentDirections
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateEditLeaveApplicationFragment : DaggerFragment() {
    private val args by navArgs<CreateEditLeaveApplicationFragmentArgs>()

    lateinit var employeeViewModel: EmployeeViewModel

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val REQUEST_SELECT_PHOTO = 2
    private var imageFile: File? = null

    var uploadImageUrl: String? = null

    var totalResposiblePersonList: List<RoleWiseEmployeeResponseClass.RoleWiseEmployee>? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfile: EmployeeProfileData


    @Inject
    lateinit var preparence: MySharedPreparence


    var employee: Employee? = null
    lateinit var binding: FragmentCreateEditLeaveApplicationBinding

    lateinit var leaveApplicationViewModel: LeaveApplicationViewmodel
    lateinit var operation: Operation
    var isAlreadyViewCreated = false

    var selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()

    var leaveApplication: LeaveApplicationApiResponse.LeaveApplication? = null

    var leaveType: LeaveApplicationApiResponse.LeavePolicy? = null
    var applyDate: String? = null
    var loadingDialog: Dialog? = null
    var isFromNotify = true
    var notifyPerson: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var responsiblePerson: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null
    var buttonClicked: String = StaticKey.CREATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (!isAlreadyViewCreated) {
            binding = FragmentCreateEditLeaveApplicationBinding.inflate(inflater, container, false)
            employee = employeeProfile?.employee
            isAlreadyViewCreated = true
            init()
            leaveApplication = args.leaveApplication
            if (args.operation.equals("create")) {
                binding.leaveApplicatoinBtnUpdate.setText(getString(R.string.create_leave_application))
                binding.leaveApplicatoinBtnDraft.visibility = View.VISIBLE
                operation = Operation.CREATE
            } else {
                binding.leaveApplicatoinBtnUpdate.setText(getString(R.string.create_leave_application))
                binding.leaveApplicatoinBtnDraft.visibility = View.VISIBLE
                operation = Operation.EDIT
            }
            setData()
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(
            "key",
            selectedDataList
        )?.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                result?.let {
                    if (it.size > 0) {
                        selectedDataList.addAll(it)
                        Log.e("editcreateApplication", "employee lsit : ${selectedDataList.size}")
                        selectedDataList?.forEach { element ->
                            Log.e("editcreateApplication", "employee lsit : ${element.name}")
                            if (isFromNotify) {
                                notifyPerson = element
                                setNotifyPersonData()
                            } else {
                                responsiblePerson = element
                                setResponsiblePersonData()
                            }
                        }
                    }
                }
            })
        return binding.root
    }

    fun setNotifyPersonData() {
        binding.tvNotifyText.text = ""
        notifyPerson?.name?.let {
            binding.tvNotifyText.append(
                if (preparence.getLanguage().equals("en")) {
                    "${notifyPerson?.name}"
                } else "${notifyPerson?.name_bn}"
            )
        }
    }

    fun setResponsiblePersonData() {
        binding.tvResponsibleText.text = ""
        responsiblePerson?.name?.let {
            binding.tvResponsibleText.append(
                if (preparence.getLanguage().equals("en")) {
                    "${responsiblePerson?.name}"
                } else "${responsiblePerson?.name_bn}"
            )
        }
    }

    fun setData() {
        binding.lEmergencyContantNo.etText.inputType = InputType.TYPE_CLASS_PHONE
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
        val currentDate = sdf.format(Date())
        if (operation == Operation.CREATE) applyDate = currentDate else applyDate =
            leaveApplication?.apply_date


        uploadImageUrl = leaveApplication?.document_path
        uploadImageUrl?.let {
            binding?.tvFileName?.setText(it)
        }

        binding.lLeaveRequestReference.etText.setText(leaveApplication?.leave_request_ref)
        binding.lEmergencyContantNo.etText.setText(
            leaveApplication?.leave_application_details?.get(
                0
            )?.emergency_contact_no
        )

        leaveApplication?.leave_application_note?.let {
            if (it?.size!! > 0) {
                notifyPerson = it.get(0).forword_to_employee
                setNotifyPersonData()
            }
            //
        }

        leaveApplication?.leave_application_details?.let {
            if (it.size > 0) {
                responsiblePerson = it.get(0).responsible_person
                setResponsiblePersonData()
            }
        }
        binding.lFromDate.tvText?.setText(
            leaveApplication?.leave_application_details?.get(
                0
            )?.date_form?.let {
                DateConverter.changeDateFormateForShowing(
                    it
                )
            }
        )
        binding.lToDate.tvText?.setText(
            leaveApplication?.leave_application_details?.get(
                0
            )?.date_to?.let {
                DateConverter.changeDateFormateForShowing(
                    it
                )
            }
        )


        activity?.let {
            Glide.with(it).applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_24)
            ).load(RetrofitInstance.BASE_URL + leaveApplication?.document_path)
                .into(binding.ivAttachement)
        }



        binding.hLeaveApplication.tvClose.setOnClickListener {
            //  dialogCustome.dismiss()
            findNavController().popBackStack()
        }


        binding?.lFromDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.lFromDate?.tvText?.setText("" + it) }
                }
            })
        })


        binding?.lToDate?.tvText?.setOnClickListener({
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date?.let { binding?.lToDate?.tvText?.setText("" + it) }
                }
            })
        })

        leaveApplicationViewModel.getLeavePolicyList()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                    LeavePolicySpinnerAdapter().setLeavePolicySpinner(
                        binding?.lLeaveType?.spinner!!,
                        context,
                        it,
                        leaveApplication?.leave_policy_id,
                        object : CommonSpinnerSelectedItemListener {
                            override fun selectedItem(any: Any?) {
                                any?.let {
                                    leaveType = any as LeaveApplicationApiResponse.LeavePolicy
                                }
                            }
                        }
                    )
                }
            })

        binding.llNotifyEmployee.setOnClickListener {
            Log.e(
                "leaveapplication",
                "notify person :........................................................"
            )
            isFromNotify = true
            selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_createEditLeaveApplicationFragment_to_searchEmployeeFragment3)
        }
        loadResponsiblepersonList()
        leaveApplication?.leave_application_details?.let {
            if (it.size > 0)
                binding.etBody.setText(it.get(0)?.reason)
        }

        binding.llResponsibleEmployee.setOnClickListener {
            Log.e(
                "leaveapplication",
                "ResponsibleEmployee :........................................................"
            )
            isFromNotify = false
            selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()
//            Navigation.findNavController(binding.root)
//                .navigate(R.id.action_createEditLeaveApplicationFragment_to_searchEmployeeFragment3)
//

            totalResposiblePersonList?.let {
                if (it.size > 0) {
                    val action =
                        totalResposiblePersonList?.toTypedArray()?.let { it1 ->
                            CreateEditLeaveApplicationFragmentDirections.actionCreateEditLeaveApplicationFragmentToEmployeeBottomSheetFragment(
                                it1
                            )
                        }
                    if (action != null) {
                        findNavController().navigate(action)
                    }
//                        Navigation.findNavController(binding.root)
//                            .navigate(R.id.action_searchEmployeeFragment_to_employeeBottomSheetFragment)
                }
            }
            if (totalResposiblePersonList == null || totalResposiblePersonList?.size!! == 0) {
                Toast.makeText(
                    activity,
                    getString(R.string.no_responsible_person_found),
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        binding.llNotifyEmployee.setOnClickListener {
            Log.e(
                "leaveapplication",
                "notify person :........................................................"
            )
            isFromNotify = true
            selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_createEditLeaveApplicationFragment_to_searchEmployeeFragment3)
        }

        binding.tvAttachment.setOnClickListener {
            galleryButtonClicked()
        }

        binding.leaveApplicatoinBtnUpdate.setOnClickListener {
            invisiableAllError()
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            if (imageFile != null) {
                imageFile?.let { uploadImage(it) }
            } else {
                uploadData()
            }

        }
        binding.leaveApplicatoinBtnDraft.setOnClickListener {
            buttonClicked = StaticKey.DRAFT
            invisiableAllError()
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            if (imageFile != null) {
                imageFile?.let { uploadImage(it) }
            } else {
                uploadData()
            }

        }

        binding.leaveApplicatoinBtnUpdate.setOnClickListener {
            buttonClicked = StaticKey.CREATE
            invisiableAllError()
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            if (imageFile != null) {
                imageFile?.let { uploadImage(it) }
            } else {
                uploadData()
            }

        }
    }

    fun loadResponsiblepersonList() {
        employeeViewModel?.apply {
            getEmployeeList(
                employeeProfile?.employee?.office_id?.let { it.toString() },
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
                //    "1", "1", "1", "1", "1", "Raju"
            ).observe(viewLifecycleOwner, Observer {
                Log.e("data", "datalist : ${it.size}")
                it?.let {
                    totalResposiblePersonList = it
                }


            })
        }
    }


    fun uploadData() {
        if (operation == Operation.CREATE) {
            leaveApplicationViewModel.createLeaveApplication(getMapData(leaveApplication))
                .observe(viewLifecycleOwner,
                    Observer {
                        loadingDialog?.dismiss()
                        showResponse(it)
                    })
        } else {
            Log.e("leaveapplication", "leave application ; ${getMapData(leaveApplication)}")

            leaveApplicationViewModel.updateLeaveApplication(
                leaveApplication?.id,
                getMapData(leaveApplication)
            )
                .observe(viewLifecycleOwner,
                    Observer {
                        loadingDialog?.dismiss()
                        showResponse(it)
                    })
        }
    }

    fun showResponse(any: Any?) {
        if (any is String) {
            toast(activity, any)
            // leaveApplicationViewModel.getLeaveApplication(employee?.user?.employee_id.toString())
            findNavController().popBackStack()

        } else if (any is ApiError) {
            try {
                if (any.getError().isEmpty()) {
                    toast(activity, any.getMessage())
                    Log.d("ok", "error")
                } else {
                    for (n in any.getError().indices) {
                        val error = any.getError()[n].getField()
                        val message = any.getError()[n].getMessage()
                        if (TextUtils.isEmpty(error)) {
                            message?.let {
                                binding?.tvAttachmentError?.visibility =
                                    View.VISIBLE
                                binding?.tvAttachmentError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        Log.e("ok", "error ${ErrorUtils2.mainError(message)}")
                        when (error) {
                            "leave_request_ref" -> {
                                binding?.lLeaveRequestReference?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.lLeaveRequestReference?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "leave_policy_id" -> {
                                binding?.lLeaveType?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.lLeaveType?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "approval_status" -> {
//                                leaveApplicationBinding?.lLeaveType?.tvError?.visibility =
//                                    View.VISIBLE
//                                leaveApplicationBinding?.lLeaveType?.tvError?.text =
//                                    ErrorUtils2.mainError(message)
                            }
                            "apply_date" -> {
//                                dialogTrainingLoyeoutBinding?.courseCourseId?.tvError?.visibility =
//                                    View.VISIBLE
//                                dialogTrainingLoyeoutBinding?.courseCourseId?.tvError?.text =
//                                    ErrorUtils2.mainError(message)
                            }
                            "forword_to_employee_id" -> {
                                binding?.tvNotifyEmployeeError?.visibility =
                                    View.VISIBLE
                                binding?.tvNotifyEmployeeError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "leave_application_details" -> {
                                binding?.lFromDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.lFromDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "leave_application_details.0.date_form" -> {
                                binding?.lFromDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.lFromDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "leave_application_details.0.date_to" -> {
                                binding?.lToDate?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.lToDate?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                toast(activity, e.toString())
            }

        } else if (any is Throwable) {
            toast(activity, any.toString())
        } else {
            activity?.getString(R.string.failed)?.let {
                toast(
                    activity,
                    it
                )
            }!!
        }
    }

    fun getMapData(leaveApplication: LeaveApplicationApiResponse.LeaveApplication?): HashMap<Any, Any?> {
        var map = HashMap<Any, Any?>()
        map.put("office_id", employee?.office_id.toString())
        map.put(
            "leave_request_ref",
            binding.lLeaveRequestReference?.etText?.text?.trim().toString()
        )
        map.put("leave_policy_id", leaveType?.id.toString())
        map.put(
            "approval_status",
            //   if (operation == Operation.CREATE) {
            if (buttonClicked.equals(StaticKey.DRAFT)) StaticKey.DRAFT else StaticKey.PENDING
            //  } else StaticKey.PENDING
        )
        map.put("forword_to_employee_id", notifyPerson?.id)
        map.put("apply_date", applyDate)

        uploadImageUrl?.let { map.put("document_path", it) }
        // map.put("note_leave", leaveApplication?.leave_application_details.note)
        map.put("note_date", applyDate)
        map.put(
            "leave_application_details",
            arrayListOf<HashMap<Any, Any?>>(leaveDeatails(leaveApplication))
        )
        if (operation != Operation.CREATE)
            map.put("status", leaveApplication?.status)
        return map
    }

    fun leaveDeatails(leaveApplication: LeaveApplicationApiResponse.LeaveApplication?): HashMap<Any, Any?> {
        var fromDate =
            DateConverter.changeDateFormateForSending(binding.lFromDate?.tvText?.text.toString())
        var toDate =
            DateConverter.changeDateFormateForSending(binding.lToDate?.tvText?.text.toString())
        var map = HashMap<Any, Any?>()
        map.put("date_form", fromDate)
        map.put("date_to", toDate)
        map.put("designation_id", employee?.designation_id)
        map.put("employee_id", employee?.user?.employee_id)
        map.put("reason", binding.etBody.text.trim().toString())
        map.put(
            "emergency_contact_no",
            binding.lEmergencyContantNo.etText.text.trim().toString()
        )
        map.put("leave_application_id", leaveApplication?.id)

        return map
    }


    fun invisiableAllError() {
        binding?.tvNotifyEmployeeError?.visibility =
            View.GONE
        binding?.tvNotifyEmployeeError?.visibility =
            View.GONE
        binding.lLeaveType?.tvError?.visibility =
            View.GONE
        binding.lFromDate?.tvError?.visibility =
            View.GONE
        binding.lToDate?.tvError?.visibility =
            View.GONE
        binding.lLeaveRequestReference?.tvError?.visibility =
            View.GONE
        binding.lEmergencyContantNo?.tvError?.visibility =
            View.GONE
        binding?.tvReasonDetailsError?.visibility =
            View.GONE
        binding.tvAttachmentError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
        leaveApplicationViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(LeaveApplicationViewmodel::class.java)
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
                activity?.let {
                    ViewModelProvider(
                        it,
                        viewModelProviderFactory
                    ).get(EmployeeInfoEditCreateViewModel::class.java)
                }
            activity?.let {
                employeeInfoEditCreateRepo?.uploadProfilePic(
                    profilePic,
                    imageFile.name,
                    profile_photo
                )
                    ?.observe(
                        it,
                        androidx.lifecycle.Observer { any ->
                            Log.e("yousuf", "profile pic : " + any)
                            //  showResponse(any)
                            if (any != null) {
                                uploadImageUrl = any as String
                                uploadData()
                            } else {
                                loadingDialog?.dismiss()
                                //  dialog?.dismiss()
                            }

                        })
            }
        }
    }


    fun galleryButtonClicked() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryIntent.setType("*/*");
        startActivityForResult(galleryIntent, REQUEST_SELECT_PHOTO)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
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
            Log.e("imagefile", "imagefile : $imageFile")
            var fileName: List<String>? = imageFile?.toString()?.split("/")

            fileName?.let {
                binding?.tvFileName?.setText(it.get(it.size - 1))

                activity?.let {
                    Glide.with(it).applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.drawable.ic_baseline_image_24)
                    ).load(imageFile)
                        .into(binding.ivAttachement)
                }



                binding.ivAttachement.setImageBitmap(bitmap)
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        }
    }


    private fun getImageFile(photoPath: String) {
        imageFile = File(photoPath)

    }


}