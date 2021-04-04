package com.dss.hrms.view.messaging.fragment

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentEmailBinding
import com.dss.hrms.model.Office
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.FilePath
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.OfficeDataValueListener
import com.dss.hrms.view.messaging.viewmodel.MessagingViewModel
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject

class EmailFragment : DaggerFragment() {
    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val REQUEST_SELECT_PHOTO = 2
    private var imageFile: File? = null

    var uploadImageUrl: String? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var commonRepo: CommonRepo

    lateinit var messageViewModel: MessagingViewModel
    var office: Office? = null
    lateinit var binding: FragmentEmailBinding
    var selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()

    var officeList = arrayListOf<Office>()
    var loadingDialog: Dialog? = null
    var isAlreadyViewCreated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("onCreate", "onCreate")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!isAlreadyViewCreated) {
            isAlreadyViewCreated = true
            // Inflate the layout for this fragment
            binding = FragmentEmailBinding.inflate(inflater, container, false)
            Log.e("onCreateView", "onCreateView")
            init()

            binding?.llAttachment.setOnClickListener {
                galleryButtonClicked()
            }

            binding.llEmployee.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_emailFragment_to_searchEmployeeFragment)
            }


            commonRepo.getOffice("/api/auth/office/list/basic",
                object : OfficeDataValueListener {
                    override fun valueChange(list: List<Office>?) {
                        //   Log.e("gender", "gender message " + Gson().toJson(list))
                        list?.let {
                            SpinnerAdapter().setOfficeSpinner(
                                binding?.spinner!!,
                                context,
                                list,
                                0,
                                object : CommonSpinnerSelectedItemListener {
                                    override fun selectedItem(any: Any?) {
                                        office = any as Office
                                        office?.name?.let {
                                            officeList?.add(office!!)
                                            binding.tvOffice.append(
                                                if (preparence.getLanguage().equals("en")) {
                                                    "${office?.name},"
                                                } else "${office?.name_bn},"
                                            )
                                        }

                                        Log.e("selected item", " item : " + office)
                                    }

                                }
                            )
                        }
                    }
                })


            binding.send.setOnClickListener {
                loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
                if (imageFile != null) {
                    uploadImage(imageFile!!)
                } else {
                    uploadData()
                }
            }


        }




        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(
            "key",
            selectedDataList
        )?.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                Log.e("emailfragment", "employee lsit : ${result?.size}")
                result?.let {

                    if (it.size > 0) {
                        selectedDataList.addAll(it)
                        Log.e("emailfragment", "employee lsit : ${selectedDataList.size}")
                        binding.tvOffice.text = ""

                        selectedDataList?.forEach { element ->
                            binding.tvEmployee.append(
                                if (preparence.getLanguage().equals("en")) {
                                    "${element?.name},"
                                } else "${element?.name_bn},"
                            )
                        }


                        //selectedEmployeeList = it
                    }
                }
            })



        return binding.root
    }


    fun getMapData(): HashMap<Any, Any?> {
        var localOfficeList = arrayListOf<Int>()
        var localEmployeeList = arrayListOf<Int>()
        var localImageList = arrayListOf<String?>()
        officeList?.forEach { element ->
            element?.id?.let { localOfficeList.add(it) }
        }
        selectedDataList?.forEach { element ->
            element?.id?.let { localEmployeeList.add(it) }
        }
        localImageList.add(uploadImageUrl)

        var map = HashMap<Any, Any?>()
        map.put(
            "office_id",
            localOfficeList
        )
        map.put(
            "employee_id",
            localEmployeeList
        )
        map.put(
            "message_body",
            binding.etBody?.text?.trim().toString()
        )
        map.put("subject", binding.etSubject.text?.trim().toString())
        uploadImageUrl?.let { map.put("attachments", localImageList) }
        return map
    }


    private fun init() {
        messageViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(MessagingViewModel::class.java)

    }


    fun uploadData() {
        messageViewModel.sendEmail(getMapData())
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                loadingDialog?.dismiss()
                showResponse(it)
            })
    }

    fun showResponse(any: Any?) {
        if (any is String) {
            toast(activity, any)

        } else if (any is ApiError) {
            try {
                if (any.getError().isEmpty()) {
                    toast(activity, any.getMessage())
                    Log.e("ok", "error")
                } else {
                    for (n in any.getError().indices) {
                        val error = any.getError()[n].getField()
                        val message = any.getError()[n].getMessage()
                        if (TextUtils.isEmpty(error)) {
                            message?.let {
                                binding?.tvBodyError?.visibility =
                                    View.VISIBLE
                                binding?.tvBodyError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        Log.e("ok", "error ${message}")
                        when (error) {
                            "employee_id" -> {
                                binding?.tvEmployeeError?.visibility =
                                    View.VISIBLE
                                binding?.tvEmployeeError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "office_id" -> {
                                binding?.tvOfficeError?.visibility =
                                    View.VISIBLE
                                binding?.tvOfficeError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "message_body" -> {
                                binding?.tvBodyError?.visibility =
                                    View.VISIBLE
                                binding?.tvBodyError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "subject" -> {
                                binding?.tvSubjectError?.visibility =
                                    View.VISIBLE
                                binding?.tvSubjectError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "attachments" -> {
                                binding?.tvAttachmentError?.visibility =
                                    View.VISIBLE
                                binding?.tvAttachmentError?.text =
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
            }
        }
    }

    fun invisiableAllError() {
        binding?.tvSubjectError?.visibility =
            View.GONE
        binding?.tvBodyError?.visibility =
            View.GONE
        binding?.tvOfficeError?.visibility =
            View.GONE
        binding?.tvEmployeeError?.visibility =
            View.GONE
        binding?.tvAttachmentError?.visibility =
            View.GONE
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
            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        }
    }


    private fun getImageFile(photoPath: String) {
        imageFile = File(photoPath)

    }


    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}