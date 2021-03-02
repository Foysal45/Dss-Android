package com.dss.hrms.view.training.fragment

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaadride.network.error.ApiError
import com.chaadride.network.error.ErrorUtils2
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogTrainingLoyeoutBinding
import com.dss.hrms.databinding.FragmentResourcePersonBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.FilePath
import com.dss.hrms.util.Operation
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.bottomsheet.SelectImageBottomSheet
import com.dss.hrms.view.training.`interface`.OnResourcePersonClickListener
import com.dss.hrms.view.training.adaoter.ResourceAdapter
import com.dss.hrms.view.training.viewmodel.ContentManagementViewModel
import com.dss.hrms.view.training.viewmodel.TrainingManagementViewModel
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_resource_person.*
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ResourcePersonFragment : DaggerFragment(), SelectImageBottomSheet.BottomSheetListener {
    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val REQUEST_TAKE_PHOTO = 1
    private val REQUEST_SELECT_PHOTO = 2
    private var selectImageBottomSheet: SelectImageBottomSheet? = null
    private var imageFile: File? = null
    private var currentPhotoPath: String? = null


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var trainingManagementViewModel: TrainingManagementViewModel
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ResourceAdapter
    lateinit var binding: FragmentResourcePersonBinding
    lateinit var dataLIst: List<TrainingResponse.ResourcePerson>
    lateinit var dialogTrainingLoyeoutBinding: DialogTrainingLoyeoutBinding

    var imageUrl: String? = null
    var loadingDialog: Dialog? = null

    var dialogCustome: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResourcePersonBinding.inflate(inflater, container, false)

        init()
        var dialog = CustomLoadingDialog().createLoadingDialog(activity)
        trainingManagementViewModel.apply {
            getResourcePerson()
            resourcePerson.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                dialog?.dismiss()
                it?.let {
                    dataLIst = it
                    dataLIst += it
                    dataLIst += it
                    dataLIst += it
                    dataLIst += it
                    Log.e("resourceperson", "hello ${dataLIst.size}")
                    prepareRecycleView()
                }

            })
        }
        activity?.let { verifyStoragePermissions(it) }
        return binding.root
    }

    private fun init() {

        trainingManagementViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(TrainingManagementViewModel::class.java)
    }

    fun prepareRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = ResourceAdapter()
        activity?.let {
            adapter.setInitialData(dataLIst, it, object : OnResourcePersonClickListener {
                override fun onClick(
                    resourcePerson: TrainingResponse.ResourcePerson,
                    position: Int,
                    operation: Operation
                ) {
                    when (operation) {
                        Operation.EDIT ->
                            showEditCreateDialog(operation, resourcePerson)
                    }
                    // Toast.makeText(activity, "${operation}", Toast.LENGTH_LONG).show()
                }

            })
        }

        binding.recyclerView.adapter = adapter
    }

    fun showEditCreateDialog(
        operation: Operation,
        resourcePerson: TrainingResponse.ResourcePerson?
    ) {

        dialogCustome = activity?.let { Dialog(it) }
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogTrainingLoyeoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_training_loyeout,
            null,
            false
        )
        dialogTrainingLoyeoutBinding?.getRoot()?.let { dialogCustome?.setContentView(it) }
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogTrainingLoyeoutBinding.llAddResourcePerson.visibility = View.VISIBLE
        dialogTrainingLoyeoutBinding.resourcePersonName.etText.setText(resourcePerson?.person_name)
        dialogTrainingLoyeoutBinding.resourcePersonShortName.etText.setText(resourcePerson?.short_name)
        dialogTrainingLoyeoutBinding.resourcePersonEmail.etText.setText(resourcePerson?.first_email)
        dialogTrainingLoyeoutBinding.resourcePersonMobile.etText.setText(resourcePerson?.first_mobile)
        dialogTrainingLoyeoutBinding.resourcePersonTinNo.etText.setText(resourcePerson?.tin_no)
        dialogTrainingLoyeoutBinding.resourcePersonNidNo.etText.setText(resourcePerson?.nid_no)
        dialogTrainingLoyeoutBinding.resourcePersonOptionalEmail.etText.setText(resourcePerson?.optional_email)
        dialogTrainingLoyeoutBinding.resourcePersonOptionalMobile.etText.setText(resourcePerson?.optional_mobile)
        dialogTrainingLoyeoutBinding.resourcePersonHeader.tvClose.setOnClickListener { dialogCustome?.dismiss() }
        dialogTrainingLoyeoutBinding.resourcePersonHeader.tvTitle.setText(getString(R.string.update_resource_perosn))

        dialogTrainingLoyeoutBinding.ivResourcePerson.setOnClickListener {
            openSelectImageBottomSheet()
        }

        dialogTrainingLoyeoutBinding.resourcePersonUpdateButton.btnUpdate.setText(getString(R.string.update))
        dialogTrainingLoyeoutBinding.resourcePersonUpdateButton.btnUpdate.setOnClickListener {
            invisiableAllError()
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)


            if (imageFile != null) {
                imageFile?.let { it1 -> uploadImage(it1, operation, resourcePerson) }
            } else {
                uploadData(operation, resourcePerson)
            }
        }

        dialogCustome?.show()
    }

    fun uploadData(key: Operation, resourcePerson: TrainingResponse.ResourcePerson?) {
        key?.let {
            if (it.equals(Operation.EDIT)) {
                activity?.let { it1 ->
                    trainingManagementViewModel?.updateResourcePerson(
                        getMapData(resourcePerson),
                        resourcePerson?.id!!
                    )?.observe(it1, androidx.lifecycle.Observer { any ->
                        dialogCustome?.dismiss()
                        loadingDialog?.dismiss()
                        Log.e("yousuf", "error : " + any)
                        showResponse(any)
                    })
                }
            } else {
            }
        }
    }

    fun getMapData(resourcePerson: TrainingResponse.ResourcePerson?): HashMap<Any, Any?> {
        var map = HashMap<Any, Any?>()
        map.put(
            "person_name",
            dialogTrainingLoyeoutBinding.categoryNameEn.etText.text.trim().toString()
        )
        map.put(
            "short_name",
            dialogTrainingLoyeoutBinding.resourcePersonShortName.etText.text.trim().toString()
        )
        map.put(
            "first_email",
            dialogTrainingLoyeoutBinding.resourcePersonEmail.etText.text.trim().toString()
        )
        map.put(
            "first_mobile",
            dialogTrainingLoyeoutBinding.resourcePersonMobile.etText.text.trim().toString()
        )
        map.put("honorarium_head_id", resourcePerson?.honorarium_head_id)
        map.put("designation_id", resourcePerson?.designation_id)
        map.put("field_of_expertise_id", resourcePerson?.field_of_expertise_id)
        map.put(
            "tin_no",
            dialogTrainingLoyeoutBinding.resourcePersonTinNo.etText.text.trim().toString()
        )
        map.put(
            "nid_no",
            dialogTrainingLoyeoutBinding.resourcePersonNidNo.etText.text.trim().toString()
        )
        map.put(
            "optional_email",
            dialogTrainingLoyeoutBinding.resourcePersonOptionalEmail.etText.text.trim().toString()
        )
        map.put(
            "optional_mobile",
            dialogTrainingLoyeoutBinding.resourcePersonOptionalMobile.etText.text.trim().toString()
        )
        map.put("office_address", resourcePerson?.office_address)
        map.put("mailing_address", resourcePerson?.mailing_address)
        map.put(
            "resource_person_image_path",
            imageUrl
        )
      //  if (imageUrl != null) else resourcePerson?.resource_person_image_path
        map.put("cv_upload_path", resourcePerson?.cv_upload_path)
        map.put("youtube_url", resourcePerson?.youtube_url)
        map.put("is_youtube_public", resourcePerson?.is_youtube_public)
        map.put("facebook_url", resourcePerson?.facebook_url)
        map.put("is_facebook_public", resourcePerson?.is_facebook_public)
        map.put("linkedin_url", resourcePerson?.linkedin_url)
        map.put("is_linkedin_public", resourcePerson?.is_linkedin_public)
        map.put("instagram_url", resourcePerson?.instagram_url)
        map.put("is_instagram_public", resourcePerson?.is_instagram_public)
        map.put("status", 1)
        return map
    }


    fun showResponse(any: Any) {
        if (any is String) {
            toast(EmployeeInfoActivity.context, any)
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
                                dialogTrainingLoyeoutBinding?.categoryNameBn?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.categoryNameBn?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                        }
                        Log.d("ok", "error ${ErrorUtils2.mainError(message)}")
                        when (error) {
                            "person_name" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonName?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "short_name" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonShortName?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonShortName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "designation_id" -> {
//                                dialogTrainingLoyeoutBinding?.d?.tvError?.visibility =
//                                    View.VISIBLE
//                                dialogTrainingLoyeoutBinding?.categoryNameBn?.tvError?.text =
//                                    ErrorUtils2.mainError(message)
                            }
                            "first_email" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonEmail?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonEmail?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "first_mobile" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonMobile?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonMobile?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "honorarium_head_id" -> {
                                dialogTrainingLoyeoutBinding?.resourceHonorariumHead?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourceHonorariumHead?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "field_of_expertise_id" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonFieldOfExpertise?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonFieldOfExpertise?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "tin_no" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonTinNo?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonTinNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nid_no" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonNidNo?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonNidNo?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "optional_email" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonOptionalEmail?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonOptionalEmail?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "optional_mobile" -> {
                                dialogTrainingLoyeoutBinding?.resourcePersonOptionalMobile?.tvError?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.resourcePersonOptionalMobile?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "resource_person_image_path" -> {
                                dialogTrainingLoyeoutBinding?.imageValidation?.visibility =
                                    View.VISIBLE
                                dialogTrainingLoyeoutBinding?.imageValidation?.text =
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


    fun invisiableAllError() {
        dialogTrainingLoyeoutBinding.resourcePersonName?.tvError?.visibility =
            View.GONE

        dialogTrainingLoyeoutBinding.resourcePersonShortName?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.resourcePersonEmail?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.resourcePersonMobile?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.resourceHonorariumHead?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.resourcePersonTinNo?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.resourcePersonFieldOfExpertise?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.resourcePersonNidNo?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.resourcePersonOptionalEmail?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.resourcePersonOptionalMobile?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.categoryNameBn?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.categoryNameBn?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.categoryNameBn?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.categoryNameBn?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.categoryNameBn?.tvError?.visibility =
            View.GONE
        dialogTrainingLoyeoutBinding.imageValidation?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }


    fun uploadImage(
        imageFile: File,
        key: Operation,
        resourcePerson: TrainingResponse.ResourcePerson?
    ) {
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
                    ViewModelProvider(it, viewModelProviderFactory)
                        .get(EmployeeInfoEditCreateViewModel::class.java)
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
                                imageUrl = any as String
                                uploadData(key, resourcePerson)
                            } else {
                                loadingDialog?.dismiss()
                            }

                        })
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
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryIntent.setType("image/*");
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

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
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
            val bitmap =
                MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), resultUri)
            dialogTrainingLoyeoutBinding.ivResourcePerson.setImageBitmap(bitmap)
        } else if (requestCode == REQUEST_SELECT_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
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

            dialogTrainingLoyeoutBinding.ivResourcePerson.setImageBitmap(bitmap)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

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


}