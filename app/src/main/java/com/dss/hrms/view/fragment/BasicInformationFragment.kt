package com.dss.hrms.view.fragment

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
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.util.FilePath
import com.dss.hrms.util.FileUtil
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.FileClickListener
import com.dss.hrms.view.activity.EmployeeInfoActivity
import com.dss.hrms.view.bottomsheet.SelectImageBottomSheet
import com.dss.hrms.view.dialog.EditEmployeeBasicInfoDialog
import com.namaztime.namaztime.database.MySharedPreparence
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import kotlinx.android.synthetic.main.personal_information_header_field.view.*
import kotlinx.android.synthetic.main.personel_information_view_field.view.*
import kotlinx.android.synthetic.main.personel_information_view_field.view.tvTitle
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasicInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasicInformationFragment : Fragment(), SelectImageBottomSheet.BottomSheetListener {

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


    private var param1: String? = null
    private var param2: String? = null
    lateinit var v: View
    var preparence: MySharedPreparence? = null
    var editEmployeeBasicInfoDialog: EditEmployeeBasicInfoDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_basic_information, container, false)
        preparence = activity?.let { MySharedPreparence(it) }
        editEmployeeBasicInfoDialog = EditEmployeeBasicInfoDialog()
        setData()
        EmployeeInfoActivity.context?.let { verifyStoragePermissions(it) }

        return v
    }

    fun setData() {

        v.hBasicInformation.tvTitle.setText(getString(R.string.personal_information))
        v.fEmployeeId.tvTitle.setText(getString(R.string.employee_id))

        v.fNameEng.tvTitle.setText(getString(R.string.name))

        v.fNameBangla.tvTitle.setText(getString(R.string.name_b))

        v.fDOB.tvTitle.setText(getString(R.string.birth))

        v.fFatherNameEng.tvTitle.setText(getString(R.string.f_name))
        v.fFatherNameBangla.tvTitle.setText(getString(R.string.f_name_b))
        v.fMotherNameEng.tvTitle.setText(getString(R.string.m_name))
        v.fMotherNameBangla.tvTitle.setText(getString(R.string.m_name_b))
        v.fGender.tvTitle.setText(getString(R.string.gender))
        v.tvImageTitle.setText(getString(R.string.photo))



        v.fEmployeeId.tvText.setText("" + MainActivity.employee?.user?.employee_id)
        MainActivity.employee?.name?.let { v.fNameEng.tvText.setText(it) }
        MainActivity.employee?.name_bn?.let { v.fNameBangla.tvText.setText(it) }
        MainActivity.employee?.date_of_birth?.let { v.fDOB.tvText.setText(it) }
        MainActivity.employee?.fathers_name?.let { v.fFatherNameEng.tvText.setText(it) }
        MainActivity.employee?.fathers_name_bn?.let { v.fFatherNameBangla.tvText.setText(it) }
        MainActivity.employee?.mothers_name?.let { v.fMotherNameEng.tvText.setText(it) }
        MainActivity.employee?.mothers_name_bn?.let { v.fMotherNameBangla.tvText.setText(it) }
        if (preparence?.getLanguage()
                .equals("en")
        ) MainActivity.employee?.gender?.name.let { v.fGender.tvText.setText(it) }
        else {
            MainActivity.employee?.gender?.name_bn.let { v.fGender.tvText.setText(it) }
        }

        activity?.let {
            Glide.with(it).applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_person_24)
            ).load(MainActivity.employee?.photo)
                .into(v.ivEmployee)
        }

        v.hBasicInformation.tvEdit.setOnClickListener({
            MainActivity.employee?.let { it1 ->
                activity?.let { it2 ->
                    editEmployeeBasicInfoDialog?.showDialog(
                        it2,
                        it1,
                        object : FileClickListener {
                            override fun onFileClick() {
                                openSelectImageBottomSheet()
                            }
                        }
                    )
                }
            }
        })

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
        startActivityForResult(galleryIntent, REQUEST_SELECT_PHOTO)

//        if (Build.VERSION.SDK_INT < 19) {
//            var intent = Intent()
//            intent.type = "image/*"
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(
//                Intent.createChooser(intent, "Choose Pictures")
//                , REQUEST_SELECT_PHOTO
//            )
//        }
//        else { // For latest versions API LEVEL 19+
//            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            intent.addCategory(Intent.CATEGORY_OPENABLE)
//            intent.type = "image/*"
//            startActivityForResult(intent, REQUEST_SELECT_PHOTO);
//        }
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
                imageFile =
                    resultUri?.let { activity?.let { it1 -> FileUtil().from(it1, it) } }!!
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val bitmap =
                MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), resultUri)

            imageFile?.let {
                bitmap?.let { it1 ->
                    resultUri?.let { it2 ->
                        editEmployeeBasicInfoDialog?.imagePath(
                            it,
                            it1,
                            it2
                        )
                    }
                }
            }
        } else if (requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            val resultUri: Uri? = data.data

            if (data != null) {
                // Get the Image from data
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                val cursor = EmployeeInfoActivity.context!!.contentResolver.query(
                    selectedImage!!,
                    filePathColumn,
                    null,
                    null,
                    null
                )
                assert(cursor != null)
                cursor!!.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                var mediaPath = cursor.getString(columnIndex)
                Log.e(
                    "mediaPath",
                    "mediaPath " + FilePath().getPath(EmployeeInfoActivity.context!!, resultUri!!)
                )
                // Set the Image in ImageView for Previewing the Media
                //   imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                cursor.close()


                var postPath = mediaPath
                Log.e("postPath", "postPath " + mediaPath)
            }


//            try {
//                imageFile =
//                    resultUri?.let { activity?.let { it1 -> FileUtil().from(it1, it) } }!!
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }


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
                        editEmployeeBasicInfoDialog?.imagePath(
                            it,
                            it1,
                            it2
                        )
                    }
                }
            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            //  changeProfilePic()
        }
    }


    private fun getImageFile(photoPath: String) {
        imageFile = File(photoPath)
       // imageFile= activity?.getExternalFilesDir(photoPath)
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
                    activity!!,
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