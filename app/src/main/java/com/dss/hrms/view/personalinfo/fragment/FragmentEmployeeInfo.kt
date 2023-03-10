package com.dss.hrms.view.personalinfo.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
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
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggrayudi.storage.SimpleStorageHelper
import com.anggrayudi.storage.file.*
import com.dss.hrms.R
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.employeeProfile.Employee.PresentAddresses
import com.dss.hrms.model.pendingDataModel.PendingDataModel
import com.dss.hrms.util.ConvertNumber
import com.dss.hrms.util.FilePath
import com.dss.hrms.util.HelperClass
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.allInterface.FileClickListener
import com.dss.hrms.view.allInterface.OnEmployeeInfoClickListener
import com.dss.hrms.view.allInterface.OnFilevalueReceiveListener
import com.dss.hrms.view.personalinfo.adapter.EmployeeInfoAdapter
import com.dss.hrms.view.bottomsheet.SelectImageBottomSheet
import com.dss.hrms.view.personalinfo.dialog.*
import com.dss.hrms.viewmodel.UtilViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_employee_info.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentEmployeeInfo : DaggerFragment(), OnEmployeeInfoClickListener,
    SelectImageBottomSheet.BottomSheetListener {

    @Inject
    lateinit var editAndCreateAdditionalQualificationInfo: EditAndCreateAdditionalQualificationInfo

    @Inject
    lateinit var editCreateChildInfo: EditAndCreateChildInfo

    @Inject
    lateinit var editAndCreateForeignTrainingInfo: EditAndCreateForeignTrainingInfo


    @Inject
    lateinit var editEducationQualificationInfo: EditEducationQualificationInfo


    @Inject
    lateinit var editAndCreateForeignTravelInfo: EditAndCreateForeignTravelInfo

    @Inject
    lateinit var editCreateHonoursAwardInfo: EditAndCreateHonoursAwardInfo

    @Inject
    lateinit var editAndCreateLanguageInfo: EditAndCreateLanguageInfo

    @Inject
    lateinit var editCreateLocalTrainingInfo: EditAndCreateLocalTrainingInfo

    @Inject
    lateinit var editAndCreatePublicatioInfo: EditAndCreatePublicatioInfo

    @Inject
    lateinit var editAndCreateSpouseInfo: EditAndCreateSpouseInfo

    @Inject
    lateinit var editCreateQualificationInfo: EditEducationQualificationInfo

    @Inject
    lateinit var editJobJoiningInformation: EditJobJoiningInformation

    @Inject
    lateinit var editOfficialResidentialIInfo: EditOfficialResidentialIInfo

    @Inject
    lateinit var editPermanentAddressInfo: EditPermanentAddressInfo

    @Inject
    lateinit var editPresentAddressInfo: EditPresentAddressInfo

    @Inject
    lateinit var editQuotaInfo: EditQuotaInfo

    @Inject
    lateinit var editReferenceInfo: EditReferenceInfo

    @Inject
    lateinit var editCreateNomineeInfo: EditCreateNomineeInfo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    @Inject
    lateinit var adapter: EmployeeInfoAdapter

    var employee: Employee? = null

    lateinit var utilViewmodel: UtilViewModel

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1

    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private lateinit var ctx: Context
    private val REQUEST_TAKE_PHOTO = 1
    private val REQUEST_SELECT_PHOTO = 2
    private var selectImageBottomSheet: SelectImageBottomSheet? = null
    private var imageFile: File? = null
    private var currentPhotoPath: String? = null
    private var onFilevalueReceiveListener: OnFilevalueReceiveListener? = null


    private val storageHelper = SimpleStorageHelper(this)
    private var position: Int? = null
    private var addButtonWiilAppear = false
    private var key: String? = null

    lateinit var v: View
    lateinit var binding: FragmentEmployeeInfo
    var dataList: List<Any>? = null
    var title: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position", 0)
            key = it.getString("key")
            addButtonWiilAppear = it.getBoolean("addWillAppear")

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_info, container, false)
        v = inflater.inflate(R.layout.fragment_employee_info, container, false)
        Log.e("position", "position : " + position)

        ctx = v.context

        storageHelper.onFolderSelected = { requestCode, folder ->
            // do stuff
        }
        storageHelper.onFileSelected = { requestCode, files ->
            // do stuff

            val resultUri: Uri = files[0].uri
            try {
                val media = files[0].toMediaFile(ctx)
                val path = media?.absolutePath
                if (path != null) {
                    getImageFile(path)
                }

            } catch (e: IOException) {
                Log.e("TAGG", "onActivityResult: ${e.localizedMessage}")
                e.printStackTrace()

            }

            var bitmap = createBitmap(1, 1)

            bitmap = try {
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, resultUri)
            } catch (
                ex: Exception
            ) {
                bitmap
            }
//            val bitmap =
//                MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), resultUri)


            val file: File? = files[0].toRawFile(ctx)


            file?.let {
                onFilevalueReceiveListener?.onFileValue(it, bitmap)
            }
        }


        utilViewmodel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(UtilViewModel::class.java)

        this.employee = employeeProfileData.employee


        v.fab.setOnClickListener {
            operation(key, null, StaticKey.CREATE, null)
        }

        if (addButtonWiilAppear) {
            v.fab.visibility = View.VISIBLE
        } else {
            v.fab.visibility = View.GONE
        }
        initRV()
        return v
    }


    fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }

    private fun initRV() {
        when (key) {
            StaticKey.PERMANENT_ADDRESS -> {
                employee?.permanentAddresses?.let {
                    if (it.size > 0) {
                        dataList = it
                    }
                }

                this.title = getString(R.string.permanent_address)
                if (dataList != null && dataList?.size!! > 0) {
                    v.fab.visibility = View.GONE
                } else {
                    v.fab.visibility = View.VISIBLE
                }
//                    dataList = listOf(Employee().PermanentAddresses())
            }
            StaticKey.PRESENT_ADDRESS -> {
                employee?.presentAddresses?.let {
                    if (it.size > 0) {
                        dataList = it
//                        val pedingDataObj: PendingDataModel? =
//                            preparence.get(HelperClass.PEDING_DATA)
//                        if (pedingDataObj != null && !pedingDataObj.presentAddress.isNullOrEmpty()) {
//                            // concate the model here
//                            for (item in pedingDataObj.presentAddress!!) {
//                                item.data?.isPendingData = true
//                                val newObj: PresentAddresses? = HelperClass.SavePresentAddresssModel(item)
//                                if (newObj != null) {
//                                    newObj.isPendingData = true
//                                    dataList?.add(newObj)
//                                }
//                            }
//                        }
                    }
                }

                this.title = getString(R.string.present_address)
                if (dataList != null && dataList?.size!! > 0) {
                    v.fab.visibility = View.GONE
                } else {
                    v.fab.visibility = View.VISIBLE
                }

//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().PresentAddresses())
            }
            StaticKey.EducationalQualifications -> {
                dataList = employee?.educationalQualifications?.toMutableList()
                this.title = getString(R.string.educational_qualification)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().EducationalQualifications())
            }
            StaticKey.Jobjoining -> {
                dataList = employee?.jobjoinings?.toMutableList()
                this.title = getString(R.string.job_joining_information)

//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().Jobjoinings())
            }
            StaticKey.Quota -> {
                dataList = employee?.employee_quotas?.toMutableList()
                this.title = getString(R.string.quota_information)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().EmployeeQuotas())
            }
            StaticKey.Spouse -> {
                dataList = employee?.spouses?.toMutableList()
                this.title = getString(R.string.spouse)
                //   if (dataList == null || dataList?.size == 0)
                //    v.fab.visibility = View.VISIBLE
                //    else
                //     v.fab.visibility = View.GONE
            }
            StaticKey.Children -> {
                dataList = employee?.childs?.toMutableList()
                this.title = getString(R.string.child_information)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().Childs())
            }

            StaticKey.Language -> {
                dataList = employee?.languages?.toMutableList()
                this.title = getString(R.string.language_information)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().Languages())
            }


            StaticKey.LocalTraining -> {
                dataList = employee?.local_trainings?.toMutableList()
                this.title = getString(R.string.local_training)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().LocalTrainings())
            }


            StaticKey.ForeingTraining -> {
                dataList = employee?.foreigntrainings?.toMutableList()
                this.title = getString(R.string.foreign_training)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().Foreigntrainings())
            }


            StaticKey.OfficialResidentials -> {
                dataList = employee?.official_residentials?.toMutableList()
                this.title = getString(R.string.official_residential_information)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().OfficialResidentials())
            }


            StaticKey.ForeignTravel -> {
                dataList = employee?.foreign_travels?.toMutableList()
                this.title = getString(R.string.foreign_travel)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().ForeignTravels())
            }


            StaticKey.AdditionalQualifications -> {
                dataList = employee?.additional_qualifications?.toMutableList()
                this.title = getString(R.string.additional_professional_qualification)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().AdditionalQualifications())
            }


            StaticKey.Publication -> {
                dataList = employee?.publications?.toMutableList()
                this.title = getString(R.string.publication)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().Publications())
            }


            StaticKey.HonoursAwards -> {
                dataList = employee?.honours_awards?.toMutableList()
                this.title = getString(R.string.honours_and_award)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().HonoursAwards())
            }


            StaticKey.DisciplinaryAction -> {
                dataList = employee?.disciplinaryActions?.toMutableList()
                this.title = getString(R.string.disciplinary_action)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().DisciplinaryAction())
            }


            StaticKey.Promotion -> {
                dataList = employee?.promotions?.toMutableList()
                this.title = getString(R.string.promotion)
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().Promotions())
            }

            StaticKey.References -> {
                dataList = employee?.references?.toMutableList()
                this.title = getString(R.string.reference)
                if (dataList != null && dataList?.size!! > 0) {
                    v.fab.visibility = View.GONE
                } else {
                    v.fab.visibility = View.VISIBLE
                }
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().References())
            }
            StaticKey.Nominee -> {
                dataList = employee?.nominees?.toMutableList()
                this.title = getString(R.string.nominee_info)
                if (dataList != null && dataList?.size!! > 0) {
                    v.fab.visibility = View.VISIBLE
                } else {
                    v.fab.visibility = View.VISIBLE
                }
//                if (dataList == null || dataList?.size == 0)
//                    dataList = listOf(Employee().References())
            }
        }

        if (dataList != null && dataList?.size!! > 0) {
            v.recyclerView.visibility = View.VISIBLE

            dataList?.let {
                key?.let { it1 ->
                    activity?.let { it2 ->
                        adapter.setRequiredData(
                            it, it1,
                            it2, this
                        )
                    }
                }
            }
//            adapter = dataList?.let {
//                activity?.let { it1 ->
//                    EmployeeInfoAdapter(
//                        it1,
//                        it, key!!,this
//                    )
//                }
//            }!!
            v.recyclerView.layoutManager = LinearLayoutManager(activity)
         //   v.recyclerView.setHasFixedSize(true)
            v.recyclerView.adapter = adapter


        } else {
            v.rlEmptyView.visibility = View.VISIBLE
            title?.let { v.tvTitle?.setText(it) }

        }


    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentEmployeeInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(any: Any, key: String, position: Int?) {

        operation(key, any, StaticKey.EDIT, position)
    }


    private fun operation(key: String?, any: Any?, operation: String?, position: Int?) {
        when (key) {
            StaticKey.PERMANENT_ADDRESS -> {
                if (operation.equals(StaticKey.EDIT)) {
                    activity?.let { it2 ->
                        editPermanentAddressInfo.showDialog(
                            it2,
                            position,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation.equals(StaticKey.CREATE)) {
                    activity?.let { it2 ->
                        editPermanentAddressInfo.showDialog(
                            it2,
                            position,
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.PRESENT_ADDRESS -> {
                if (operation.equals(StaticKey.EDIT)) {
                    activity?.let { it2 ->
                        editPresentAddressInfo.showDialog(
                            it2,
                            position,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation.equals(StaticKey.CREATE)) {
                    activity?.let { it2 ->
                        editPresentAddressInfo.showDialog(
                            it2,
                            position,
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.EducationalQualifications -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editEducationQualificationInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editEducationQualificationInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.Jobjoining -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editJobJoiningInformation.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            utilViewmodel
                        )
                    }
                }
            }
            StaticKey.Quota -> {
                //  Toast.makeText(activity, "quota", Toast.LENGTH_SHORT).show()
                Log.e("quota", "quota : ")
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editQuotaInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                        )
                    }
                }
            }
            StaticKey.Spouse -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editAndCreateSpouseInfo.showDialog(
                            it2,
                            position,
                            StaticKey.EDIT
                        )
                    }

                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editAndCreateSpouseInfo.showDialog(
                            it2,
                            position,
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.Nominee -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editCreateNomineeInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }

                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editCreateNomineeInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.Children -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editCreateChildInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editCreateChildInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.Language -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editAndCreateLanguageInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editAndCreateLanguageInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.LocalTraining -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editCreateLocalTrainingInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editCreateLocalTrainingInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.ForeingTraining -> {

                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editAndCreateForeignTrainingInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editAndCreateForeignTrainingInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.OfficialResidentials -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editOfficialResidentialIInfo.showDialog(
                            it2,
                            position,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editOfficialResidentialIInfo.showDialog(
                            it2,
                            position,
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.ForeignTravel -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editAndCreateForeignTravelInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editAndCreateForeignTravelInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.AdditionalQualifications -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editAndCreateAdditionalQualificationInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editAndCreateAdditionalQualificationInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.Publication -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editAndCreatePublicatioInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }

                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editAndCreatePublicatioInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.HonoursAwards -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editCreateHonoursAwardInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editCreateHonoursAwardInfo.showDialog(
                            it2,
                            position,
                            object : FileClickListener {
                                override fun onFileClick(onFilevalueReceiveListener1: OnFilevalueReceiveListener) {
                                    onFilevalueReceiveListener = onFilevalueReceiveListener1
                                    openSelectImageBottomSheet()
                                }
                            },
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.PostingRecord -> {

            }

            StaticKey.DisciplinaryAction -> {

            }

            StaticKey.Promotion -> {

            }

            StaticKey.References -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        editReferenceInfo.showDialog(
                            it2,
                            position,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        editReferenceInfo.showDialog(
                            it2,
                            position,
                            StaticKey.CREATE
                        )
                    }
                }
            }
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

        if (Build.VERSION.SDK_INT >= 30) {
            val galleryIntent = Intent(
                Intent.ACTION_OPEN_DOCUMENT
            )
            //   galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
            galleryIntent.setType("*/*");
            val mimetypes = arrayOf(
                "image/*",
                "application/pdf",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/msword"
            )
            galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
            galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
            startActivityForResult(galleryIntent, REQUEST_SELECT_PHOTO)
            selectImageBottomSheet!!.dismiss()
        } else {
            val galleryIntent = Intent(
                Intent.ACTION_PICK
            )
            //   galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
            galleryIntent.type = "*/*";
            val mimetypes = arrayOf(
                "image/*",
                "application/pdf",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/msword"
            )
            galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
            startActivityForResult(galleryIntent, REQUEST_SELECT_PHOTO)
            selectImageBottomSheet!!.dismiss()
        }

    }

    private fun openFilePickewr() {

        storageHelper.openFilePicker()
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
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var bitmap = createBitmap(1, 1)

            bitmap = try {
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, resultUri)
            } catch (
                ex: Exception
            ) {
                bitmap
            }
            imageFile?.let {
                bitmap.let { it1 ->
                    resultUri?.let { it2 ->
                        onFilevalueReceiveListener?.onFileValue(it, it1)
                    }
                }
            }


        } else if (requestCode == REQUEST_SELECT_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            val resultUri: Uri? = data.data
            Log.d("ACTION_OPEN_DOCUMENT", "onActivityResult: " + resultUri)

            try {

                if (resultUri != null) {

                    var path = FilePath().getPath(ctx, resultUri)

                    if (path.isNullOrBlank() || path.isNullOrEmpty() || path.length < 5) {
                        path = ConvertNumber.makeFileCopyInCacheDir(resultUri, ctx)
                    }
                    Log.d("ACTION_OPEN_DOCUMENT", "onActivityResult: " + path)
                    if (path != null) {
                        getImageFile(path)
                    }

                } else {
                    Log.d("ACTION_OPEN_DOCUMENT", "onActivityResult: " + resultUri)
                }

            } catch (e: IOException) {
                Log.e("TAGG", "onActivityResult: ${e.localizedMessage}")
                e.printStackTrace()
            }

            var bitmap = createBitmap(1, 1)

            bitmap = try {
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, resultUri)
            } catch (
                ex: Exception
            ) {
                bitmap
            }
//            val bitmap =
//                MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), resultUri)

            //      Toast.makeText(context , "FILE -> "  + imageFile?.name  , Toast.LENGTH_SHORT).show()
            imageFile?.let {
                bitmap.let { it1 ->

                    onFilevalueReceiveListener?.onFileValue(it, it1)

                }
            }
        }
//        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//
//        }
        else {
            storageHelper.storage.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        storageHelper.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun getImageFile(photoPath: String) {

        Log.d("TAGGGED", "getImageFile:  $photoPath")
        imageFile = File(photoPath)

    }

    private fun setImageToView(bitmap: Bitmap) {
        // headerBinding.profileImageCIV.setImageBitmap(bitmap)
    }


    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
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
            uri?.let { activity?.getContentResolver()?.query(it, projection, null, null, null) }
                ?: return null
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s: String = cursor.getString(column_index)
        cursor.close()
        return s
    }


}