package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dss.hrms.R
import com.dss.hrms.databinding.DialogPersonalInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.*
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.*
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.*
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.*
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import kotlinx.android.synthetic.main.personel_information_edittext.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class EditCreateNomineeInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData
    var employee: Employee? = null
    lateinit var ctx: Context
    var sposeModel: Employee.Spouses? = null
    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var municipality: Municipalities? = null
    var localGovernmentType: SpinnerDataModel? = null
    var cityCorporations: CityCorporations? = null
    var specificDistrictModel: SpecificDistrictModel? = null
    var fileClickListener: FileClickListener? = null
    var position: Int? = 0
    var dialogCustome: Dialog? = null
    var nominee: Employee.Nominee? = null
    var gender: SpinnerDataModel? = null
    var maritalStatus: SpinnerDataModel? = null
    var hasDisabaility: SpinnerDataModel? = null
    open var imageFile: File? = null
    var documentPath: String? = null

    var imageUrl: String? = null
    var dialog: Dialog? = null

    //var upazila: SpinnerDataModel? = null
    var upazila: Upazilas? = null
    var union: Union? = null
    lateinit var binding: DialogPersonalInfoBinding
    lateinit var key: String
    fun showDialog(
        context: Context, position: Int?,
        fileClickListener: FileClickListener,
        key: String
    ) {
        this.ctx = context
        this.position = position
        this.key = key
        this.fileClickListener = fileClickListener
        this.nominee = position?.let { employeeProfileData?.employee?.nominees?.get(it) }
        employee = employeeProfileData?.employee
        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info,
            null,
            false
        )


        dialogCustome?.setContentView(binding.getRoot())
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updateNomineeInfo(context, true)
        dialogCustome?.show()

    }

    fun getLocalGovernmentType(local_government_type_id: Int?, isRelation: Boolean) {
        commonRepo.getCommonData("/api/auth/local-government-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    Log.e("gender", "local govt  message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fNominneAddresstypeCityCorpUpazilaMunicipality?.spinner!!,
                            ctx,
                            list,
                            local_government_type_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    localGovernmentType = any as SpinnerDataModel
                                    if (!isRelation) {
                                        setCityMuniUpailaAccordingToType(
                                            nominee?.local_government_type_id,
                                            nominee?.city_corporation_id,
                                            nominee?.municipality_id,
                                            nominee?.upazila_id
                                        )
                                    } else {
                                        setCityMuniUpailaAccordingToType(
                                            sposeModel?.local_government_type_id,
                                            sposeModel?.city_corporation_id,
                                            sposeModel?.municipality_id,
                                            sposeModel?.upazila_id
                                        )

                                    }
                                }
                            }
                        )
                    }
                }
            })
    }


    fun setCityMuniUpailaAccordingToType(
        localGovtId: Int?, city_corporation_id: Int?,
        municipality_id: Int?, upazila_id: Int?
    ) {
        Log.d(
            "TAGGGED",
            "setCityMuniUpailaAccordingToType:  ${localGovernmentType?.id}  $city_corporation_id $municipality_id , $upazila_id"
        )
        //   Toast.makeText(ctx , "TYPE_ID ${localGovernmentType?.id}" , Toast.LENGTH_LONG).show()
        if (localGovernmentType?.id == 1) {
            upazila = null
            union = null
            municipality = null
            binding?.fNominneAddressCityCorporations?.llBody?.visibility = View.VISIBLE
            binding?.fNominneAddressMunicipalities?.llBody?.visibility = View.GONE
            binding?.fNominneAddressUpazila?.llBody?.visibility = View.GONE
            binding?.fNominneAddressUnio?.llBody?.visibility = View.GONE
            setCityCorporation(
                specificDistrictModel?.city_corporations,
                city_corporation_id
            )
        } else if (localGovernmentType?.id == 2) {

            upazila = null
            union = null
            cityCorporations = null
            binding?.fNominneAddressMunicipalities?.llBody?.visibility = View.VISIBLE
            binding?.fNominneAddressCityCorporations?.llBody?.visibility = View.GONE
            binding?.fNominneAddressUpazila?.llBody?.visibility = View.GONE
            binding?.fNominneAddressUnio?.llBody?.visibility = View.GONE
            setMunicipalities(specificDistrictModel?.upazilas, municipality_id)
        } else if (localGovernmentType?.id == 3) {
            cityCorporations = null
            municipality = null
            binding?.fNominneAddressUpazila?.llBody?.visibility = View.VISIBLE
            binding?.fNominneAddressUnio?.llBody?.visibility = View.VISIBLE
            binding?.fNominneAddressMunicipalities?.llBody?.visibility = View.GONE
            binding?.fNominneAddressCityCorporations?.llBody?.visibility = View.GONE
            setUpaila(upazila_id)
        } else {
            cityCorporations = null
            municipality = null
            upazila = null
            union = null
            binding?.fNominneAddressMunicipalities?.llBody?.visibility = View.GONE
            binding?.fNominneAddressCityCorporations?.llBody?.visibility = View.GONE
            binding?.fNominneAddressUpazila?.llBody?.visibility = View.GONE
            binding?.fNominneAddressUnio?.llBody?.visibility = View.GONE
        }
    }


    fun getUpazilaWithMunicipalities(districtId: Int?, upazilaId: Int?, isFromRelation: Boolean) {
        commonRepo.getUpazilaWithMunicipalities(districtId,
            object : SpecificDistrictValueListener {
                override fun valueChange(data: SpecificDistrictModel?) {
                    specificDistrictModel = data

                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    if (isFromRelation) {
                        setCityMuniUpailaAccordingToType(
                            sposeModel?.local_government_type_id, sposeModel?.city_corporation_id,
                            sposeModel?.municipality_id, sposeModel?.upazila_id
                        )
                    } else {
                        setCityMuniUpailaAccordingToType(
                            nominee?.local_government_type_id, nominee?.city_corporation_id,
                            nominee?.municipality_id, nominee?.upazila_id
                        )
                    }

                }
            })
    }

    fun setMunicipalities(list: List<Upazilas>?, id: Int?) {
        list?.let {
            MunicipalitiesAdapter().setMunicipalitiesSpinner(
                binding.fNominneAddressMunicipalities.spinner,
                ctx,
                getPoulatedMunicipalities(list),
                id,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        // upazila = any as Municipalities
                        municipality = null
                        any?.let {
                            municipality = any as Municipalities
                        }
                    }
                }
            )
        }
    }


    fun setUpaila(upazilaId: Int?) {
        UpazilaAdapter().setUpazilaSpinner(
            binding?.fNominneAddressUpazila.spinner,
            ctx,
            specificDistrictModel?.upazilas,
            upazilaId,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    //upazila = any as SpinnerDataModel
                    upazila = null
                    any?.let {
                        upazila = any as Upazilas
                        setUnion(upazila?.id, nominee?.union_id)
                    }
                }
            }
        )
    }

    fun setUnion(upazilaId: Int?, unionId: Int?) {
        commonRepo.getUnion(upazilaId,
            object : UnionDataValueListener {
                override fun valueChange(list: List<Union>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        UnionAdapter().setUnionSpinner(
                            binding?.fNominneAddressUnio?.spinner!!,
                            ctx,
                            list,
                            unionId,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    union = null
                                    // getUpazila(district?.id, presentAddress?.upazila_id)
                                    any?.let {
                                        union = any as Union
                                    }
                                }

                            }
                        )
                    }
                }
            })
    }


    fun getPoulatedMunicipalities(
        list: List<Upazilas>?
    ): List<Municipalities> {
        var municipalitiesList: List<Municipalities> = arrayListOf()
        list?.let {
            it.forEach { uList ->
                uList.municipalities?.let { uListElement ->
                    municipalitiesList += uListElement
                }
            }
        }
        Log.d("MUNILIST", "getPoulatedMunicipalities: ${municipalitiesList.toString()}")
        return municipalitiesList
    }

    fun setCityCorporation(list: List<CityCorporations>?, id: Int?) {
        list?.let {
            CityCorporationsAdapter().setCityCorporationsSpinner(
                binding?.fNominneAddressCityCorporations?.spinner!!,
                ctx,
                list,
                id,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        // upazila = any as Municipalities
                        cityCorporations = null
                        any?.let {
                            cityCorporations = any as CityCorporations
                        }
                    }
                }
            )
        }
    }


    fun updateNomineeInfo(
        context: Context, isLoad: Boolean

    ) {


////////////////////////
        binding.fNomineeNestedSelect.llBody.visibility = View.GONE
        binding.llNomineeInfo.visibility = View.VISIBLE
        binding.hNominee.tvClose.setOnClickListener {
            dialogCustome?.dismiss()
        }

        if (key == StaticKey.EDIT) {
            binding.nomineeUpdate.btnUpdate.setText(context.getString(R.string.update))
        } else {
            binding.nomineeUpdate.btnUpdate.setText(context.getString(R.string.update))
        }

        // setting addres et for multiple line setup

        binding.fNomineeFullAddress.etText.minLines = 6
        binding.fNomineeFullAddress.etText.maxLines = 12


        binding.fNomineeFullAddress.etText.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        binding.fNomineeFullAddress.etText.requestLayout()

        binding.fNomineeName.etText.setText(nominee?.name)
        binding.fNomineeRelation.etText.setText(nominee?.relation)
        binding.fNomineeAllocatedPercentage.etText.setText(nominee?.allocated_percentage)



        binding.fNomineeFullAddress.etText.setText(nominee?.address_details)


        binding?.fNomineeAllocatedPercentage?.llBody?.etText?.inputType =
            InputType.TYPE_CLASS_NUMBER

        imageUrl = nominee?.nominee_signature

        nominee?.date_of_birth?.let {
            binding?.fNomineeDob?.tvText?.setText(
                "" + DateConverter.changeDateFormateForShowing(
                    it
                )
            )
        }

        hasDisabilityData()?.let {
            SpinnerAdapter().setSpinner(
                binding?.fNomineeHasDisability?.spinner!!,
                context,
                it,
                nominee?.has_disability,
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        hasDisabaility = any as SpinnerDataModel
                    }
                }
            )
        }
        binding.fNomineeDob.tvText.setOnClickListener {
            DatePicker().showDatePicker(context, object : OnDateListener {
                override fun onDate(date: String) {
                    date.let { binding.fNomineeDob.tvText.text = "" + it }
                }
            })
        }


        binding.fNominneAttachment.Attachment.setOnClickListener {

            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    try {
                        if (ConvertNumber.isFileLessThan2MB(imgFile)) {
                            binding.fNominneAttachment.fAttachmentFileName.text =
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
            })

        }


        loadDivision(false, nominee?.division_id)


        getLocalGovernmentType(nominee?.local_government_type_id, false)

        commonRepo.getCommonData("/api/auth/marital-status/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fNomineeMaritalStatus?.spinner!!,
                            context,
                            list,
                            nominee?.marital_status_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    maritalStatus = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

        commonRepo.getCommonData("/api/auth/gender/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fNomineeGender?.spinner!!,
                            context,
                            list,
                            nominee?.gender_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    gender = any as SpinnerDataModel
                                }

                            }
                        )
                    }
                }
            })

//        context?.let {
//            binding?.ivNomineeEmployee?.let { it1 ->
//                Glide.with(it).load(RetrofitInstance.BASE_URL + nominee?.nominee_signature)
//                    .into(it1)
//            }
//        }

        Glide.with(context)
            .asBitmap()
            .placeholder(R.drawable.ic_baseline_image_24)
            .load(RetrofitInstance.BASE_URL + nominee?.nominee_signature)
            .into(binding.ivNomineeEmployee)


        binding?.ivNomineeEmployee?.setOnClickListener {
            fileClickListener?.onFileClick(object : OnFilevalueReceiveListener {
                override fun onFileValue(imgFile: File, bitmap: Bitmap?) {
                    imageFile = imgFile

                    Glide.with(context)
                        .asBitmap()
                        .load(bitmap)
                        .into(binding.ivNomineeEmployee)
                    //   Toast.makeText(context, "image", Toast.LENGTH_LONG).show()

                }
            })
        }



        binding.nomineeUpdate.btnUpdate?.setOnClickListener {
            dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            if (imageFile != null) {
                imageFile?.let { it1 -> uploadImage(it1) }
            } else {
                uploadData()
            }
        }

        if (isLoad) {
            SpinnerAdapter().setRelationToNominee(
                binding.fNomineeSelect.spinner,
                context,
                context.resources.getStringArray(R.array.nominee_list),
                object : CommonSpinnerSelectedItemListener {
                    override fun selectedItem(any: Any?) {
                        //TODO here neeed to get the index  number of the realation
                        // and make query of the selected realtion
                        val index = any as Int

                        when (index) {
                            0->{

                            }
                            1 -> {
                                // its  father
                                loadFather()
                                if (key == StaticKey.EDIT) {
                                    resetAllSpinner()
                                } else loadDivision(false, nominee?.division_id)
                            }
                            2 -> {
                                loadMother()
                                if (key == StaticKey.EDIT) {
                                    resetAllSpinner()
                                } else loadDivision(false, nominee?.division_id)
                            }
                            3 -> {
                                loadNestedSpinner(index)
                            }
                            4 -> {
                                loadNestedSpinner(index)
                                if (key == StaticKey.EDIT) {
                                    resetAllSpinner()
                                } else loadDivision(false, nominee?.division_id)
                            }
                            else -> {
                                binding.fNomineeNestedSelect.llBody.visibility = View.GONE
                                binding.fNomineeName.etText.setText("")
                                binding.fNomineeDob.tvText.text = ""
                                binding.fNomineeRelation.etText.setText("")
                                binding.fNomineeGender.spinner.setSelection(0)
                                binding.fNomineeMaritalStatus.spinner.setSelection(0)
                                binding.fNomineeHasDisability.spinner.setSelection(0)
                                if (key == StaticKey.EDIT) {
                                    resetAllSpinner()
                                } else {
                                    binding.fNomineeRelation.etText.setText("")
                                 //   binding.fNomineeNestedSelect.llBody.visibility = View.GONE
                                    binding.fNomineeName.etText.setText("")
//                                    binding.fNomineeGender.spinner.setSelection(0)
//                                    binding.fNomineeMaritalStatus.spinner.setSelection(0)
//                                    binding.fNomineeHasDisability.spinner.setSelection(0)
                                    loadDivision(false, 0)
                                }
                            }
                        }


                    }

                }
            )
        }


    }

    private fun resetAllSpinner() {

        binding.fNominneAddressDivision.spinner.setSelection(0)
        binding.fNominneAddressDistrict.spinner.setSelection(0)
        binding.fNominneAddresstypeCityCorpUpazilaMunicipality.spinner.setSelection(0)
        binding.fNominneAddressMunicipalities.spinner.setSelection(0)
        binding.fNominneAddressUnio.spinner.setSelection(0)
        binding.fNominneAddressUpazila.spinner.setSelection(0)
        binding.fNominneAddressCityCorporations.spinner.setSelection(0)
        binding.fNomineeAllocatedPercentage.etText.setText("")


    }

    private fun loadDivision(b: Boolean, divisionId: Int?) {

        commonRepo.getCommonData("/api/auth/division/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("DIVISION", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding.fNominneAddressDivision.spinner,
                            ctx,
                            list,
                            divisionId,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    division = any as SpinnerDataModel
                                    if (b && sposeModel != null) {
                                        // true means it came from realtion model
                                        getDistrict(division?.id, sposeModel?.district_id, b)

                                    } else {
                                        // normal
                                        getDistrict(division?.id, nominee?.district_id, b)
                                    }

                                }

                            }
                        )
                    }
                }
            })

    }

    private fun loadNestedSpinner(index: Int) {
        binding.fNomineeNestedSelect.llBody.visibility = View.VISIBLE
        if (index == 3) {
            // spouses
            binding.fNomineeNestedSelect.tvTitle.setText("Select Spouses")
            // get the spouses
            val lsit = employeeProfileData.employee?.spouses

            if (!lsit.isNullOrEmpty()) {
                SpinnerAdapter().setSpousesToSpinner(
                    binding.fNomineeNestedSelect.spinner,
                    ctx,
                    lsit,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {
                            val model = any as Employee.Spouses
                            loadSpouse(model)

                        }

                    }
                )
            }

        } else {
            // this if for child
            // populate the list of child
            binding.fNomineeNestedSelect.tvTitle.setText("Select Children")

            val childList = employeeProfileData.employee?.childs

            if (!childList.isNullOrEmpty()) {
                // set it to the the spinner
                SpinnerAdapter().setChildrenToSpinner(
                    binding.fNomineeNestedSelect.spinner,
                    ctx,
                    childList,
                    object : CommonSpinnerSelectedItemListener {
                        override fun selectedItem(any: Any?) {
                            val model = any as Employee.Childs
                            loadChild(model)

                        }

                    }
                )


            }

        }

    }

    private fun loadChild(model: Employee.Childs) {

        binding.fNomineeRelation.etText.setText("Children")

        binding.fNomineeName.etText.setText((model.name_of_children + ""))
        if (model.gender_id == 1) {
            // male
            binding.fNomineeGender.spinner.setSelection(1)
        } else {
            binding.fNomineeGender.spinner.setSelection(2)
        }
        binding.fNomineeHasDisability.spinner.setSelection(2)

    }

    private fun loadFather() {
        // male
        // name
        // disability  gone by default
        binding.fNomineeRelation.etText.setText("Father")
        binding.fNomineeNestedSelect.llBody.visibility = View.GONE
        binding.fNomineeName.etText.setText(employeeProfileData.employee?.fathers_name.toString() + "")
        binding.fNomineeGender.spinner.setSelection(1)
        binding.fNomineeMaritalStatus.spinner.setSelection(2)
        binding.fNomineeHasDisability.spinner.setSelection(2)

    }

    private fun loadMother() {
        // female
        // name
        // disability  gone by default
        binding.fNomineeRelation.etText.setText("Mother")
        binding.fNomineeNestedSelect.llBody.visibility = View.GONE
        binding.fNomineeName.etText.setText(employeeProfileData.employee?.mothers_name.toString() + "")
        binding.fNomineeGender.spinner.setSelection(2)
        binding.fNomineeMaritalStatus.spinner.setSelection(2)
        binding.fNomineeHasDisability.spinner.setSelection(2)

    }

    private fun loadSpouse(model: Employee.Spouses) {
        // male
        // name
        // disability  gone by default
        // lets check  officer gender male = 1
        if (employee?.gender_id == 1) {
            // officer is male
            // so spouse will be wife -> female
            binding.fNomineeRelation.etText.setText("Wife")
            binding.fNomineeName.etText.setText((model.name + ""))
            binding.fNomineeGender.spinner.setSelection(2)
            binding.fNomineeMaritalStatus.spinner.setSelection(2)
            binding.fNomineeHasDisability.spinner.setSelection(2)
        } else {
            // officer is female
            // so spouse will be husband  -> male
            binding.fNomineeRelation.etText.setText("Husband")
            binding.fNomineeName.etText.setText((model.name + ""))
            binding.fNomineeGender.spinner.setSelection(1)
            binding.fNomineeMaritalStatus.spinner.setSelection(2)
            binding.fNomineeHasDisability.spinner.setSelection(2)

        }
        loadAddress(model)


    }

    private fun loadAddress(spouses: Employee.Spouses) {

        //  division.=  spouses.divisioni
        //  district = null
        // load division
        sposeModel = spouses
        loadDivision(true, spouses.division_id)

        getLocalGovernmentType(spouses.local_government_type_id, true)
        //  localGovernmentType?.id = spouses.local_government_type_id


//        commonRepo.getCommonData("/api/auth/local-government-type/list",
//            object : CommonDataValueListener {
//                override fun valueChange(list: List<SpinnerDataModel>?) {
//                    Log.e("gender", "local govt  message " + Gson().toJson(list))
//                    list?.let {
//                        SpinnerAdapter().setSpinner(
//                            binding.fNominneAddresstypeCityCorpUpazilaMunicipality?.spinner!!,
//                            ctx,
//                            list,
//                            spouses.local_government_type_id,
//                            object : CommonSpinnerSelectedItemListener {
//                                override fun selectedItem(any: Any?) {
//                                    localGovernmentType = any as SpinnerDataModel
//                                    setCityMuniUpailaAccordingToType(
//                                        spouses.local_government_type_id,
//                                        spouses.city_corporation_id,
//                                        spouses.municipality_id,
//                                        spouses.upazila_id
//                                    )
//                                }
//                            }
//                        )
//                    }
//                }
//            })


    }


    fun getDistrict(divisionId: Int?, districtId: Int?, isFromRelation: Boolean) {
        commonRepo.getDistrict(divisionId,
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fNominneAddressDistrict?.spinner!!,
                            ctx,
                            list,
                            districtId,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    district = any as SpinnerDataModel
                                    // getUpazila(district?.id, presentAddress?.upazila_id)
                                    if (isFromRelation && sposeModel != null) {
                                        getUpazilaWithMunicipalities(
                                            district?.id,
                                            sposeModel?.upazila_id, isFromRelation
                                        )
                                    } else {
                                        getUpazilaWithMunicipalities(
                                            district?.id,
                                            nominee?.upazila_id, isFromRelation

                                        )
                                    }

                                }

                            }
                        )
                    }
                }
            })
    }

    fun uploadFile(file: File, context: Context) {
        val dialouge = ProgressDialog(EmployeeInfoActivity.context)
        dialouge.setMessage("uploading...")
        dialouge.setCancelable(false)
        dialouge.show()

        var profilePic: MultipartBody.Part?

        var filePart: MultipartBody.Part?

        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        profilePic =
            MultipartBody.Part.createFormData("filenames[]", "${file.name}", requestFile)
//        profilePic =
//            MultipartBody.Part.createFormData("filenames[]", "filenames[${file.name}]", requestFile)

        val profile_photo: RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), "profile_ph")

        val employeeInfoEditCreateRepo =
            ViewModelProviders.of(EmployeeInfoActivity.context!!, viewModelProviderFactory)
                .get(EmployeeInfoEditCreateViewModel::class.java)
        employeeInfoEditCreateRepo.uploadProfilePic(
            profilePic,
            file.name,
            profile_photo
        )
            ?.observe(
                EmployeeInfoActivity.context!!,
                { any ->
                    Log.e("yousuf", "profile pic : " + any)
                    //  showResponse(any)
                    dialouge?.dismiss()
                    if (any != null) {
                        val fileUrl = any as String
                        Log.d("TESTUPLOAD", "uploadFile: $fileUrl ")
                        documentPath = fileUrl
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

                })


    }

    fun getMapData(): java.util.HashMap<Any, Any?> {
        var dob =
            DateConverter.changeDateFormateForSending(binding.fNomineeDob?.tvText?.text.toString())
        var map = java.util.HashMap<Any, Any?>()
        if (nominee?.id == null) map.put("id", 0) else nominee?.id?.let { map.put("id", it) }
        map.put("employee_id", employee?.user?.employee_id)
        map.put("name", binding?.fNomineeName.etText.text.toString().trim())
        map.put(
            "date_of_birth",
            dob
        )
        map.put("relation", binding?.fNomineeRelation.etText.text.toString().trim())
        map.put("allocated_percentage", binding?.fNomineeAllocatedPercentage.etText.text.toString())
        /*
         adding address
         */
        try{
            if (key == StaticKey.EDIT && nominee?.isPendingData == false  ) {
                map.put("parent_id", nominee?.id)
            }

            else if (  key == StaticKey.EDIT && nominee?.isPendingData == true) {
                map.put("parent_id", nominee?.parent_id)
            }
        }catch (Ex : java.lang.Exception){

        }
        map.put("division_id", division?.id)
        map.put("district_id", district?.id)
        map.put("nominee_document_path", documentPath)
        localGovernmentType?.id?.let { map.put("local_government_type_id", it) }
        municipality?.id?.let { map.put("municipality_id", it) }
        cityCorporations?.id?.let { map.put("city_corporation_id", it) }
        upazila?.id?.let { map.put("upazila_id", it) }
        union?.id?.let { map.put("union_id", it) }

        map.put("address_details", binding.fNomineeFullAddress.etText.text.toString())
        maritalStatus?.id?.let {
            map.put("marital_status_id", it.toString())
        }
        gender?.id?.let {
            map.put("gender_id", it.toString())
        }
        hasDisabaility?.id?.let {
            map.put("has_disability", it.toString())
        }
        imageUrl?.let { map.put("nominee_signature", it) }

        var arr = arrayListOf<HashMap<Any, Any?>?>(map)
        var finalMap = java.util.HashMap<Any, Any?>()
        finalMap.put("nominees", arr)


        return finalMap
    }


    fun uploadData() {
        var employeeInfoEditCreateRepo =
            ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory)
                .get(EmployeeInfoEditCreateViewModel::class.java)
        invisiableAllError(binding)
        key?.let {
            if (it.equals(StaticKey.EDIT)) {
                employeeInfoEditCreateRepo?.updateNomineeInfo(
                    getMapData()
                )?.observe(EmployeeInfoActivity.context!!, androidx.lifecycle.Observer { any ->
                    dialog?.dismiss()
                    Log.e("yousuf", "error : " + any)
                    showResponse(any)
                })
            } else {
                employeeInfoEditCreateRepo?.updateNomineeInfo(
                    getMapData()
                )?.observe(EmployeeInfoActivity.context!!, androidx.lifecycle.Observer { any ->
                    dialog?.dismiss()
                    Log.e("yousuf", "error : " + any)
                    showResponse(any)
                })
            }
        }
    }

    fun showResponse(any: Any) {
        if (any is String) {
            toast(EmployeeInfoActivity.context,
                "" + ctx?.getString(R.string.updated))
            MainActivity.selectedPosition = 18
            EmployeeInfoActivity.refreshEmployeeInfo()
            dialogCustome?.dismiss()
        } else if (any is ApiError) {
            //  Log.e("editcreatebasicinfo","edit create : error ${Gson().toJson(any.getError())}")
            // Log.e("editcreatebasicinfo","edit create : error "+any?.getError())
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
                                binding?.fNomineeName?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                        }
                        when (error) {
                            "nominees.0.name" -> {
                                binding?.fNomineeName?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeName?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.date_of_birth" -> {
                                binding?.fNomineeDob?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeDob?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.relation" -> {
                                binding?.fNomineeRelation?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeRelation?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.local_government_type_id" -> {
                                binding?.fNominneAddresstypeCityCorpUpazilaMunicipality?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNominneAddresstypeCityCorpUpazilaMunicipality?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.district_id" -> {
                                binding?.fNominneAddressDistrict?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNominneAddressDistrict?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.division_id" -> {
                                binding?.fNominneAddressDivision?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNominneAddressDivision?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }


                            "nominees.0.allocated_percentage" -> {
                                binding?.fNomineeAllocatedPercentage?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeAllocatedPercentage?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }

                            "nominees.0.gender_id" -> {
                                binding?.fNomineeGender?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeGender?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.marital_status_id" -> {
                                binding?.fNomineeMaritalStatus?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeMaritalStatus?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.has_disability" -> {
                                binding?.fNomineeHasDisability?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeHasDisability?.tvError?.text =
                                    ErrorUtils2.mainError(message)
                            }
                            "nominees.0.address_details" -> {
                                binding?.fNomineeFullAddress?.tvError?.visibility =
                                    View.VISIBLE
                                binding?.fNomineeFullAddress?.tvError?.text =
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

    fun uploadImage(imageFile: File) {
        var profilePic: MultipartBody.Part?
        if (imageFile != null) {

            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
            profilePic =
                MultipartBody.Part.createFormData("filenames[]", "${imageFile.name}", requestFile)

            val profile_photo: RequestBody =
                RequestBody.create(MediaType.parse("text/plain"), "profile_ph")

            var employeeInfoEditCreateRepo =
                ViewModelProviders.of(EmployeeInfoActivity.context!!, viewModelProviderFactory)
                    .get(EmployeeInfoEditCreateViewModel::class.java)
            employeeInfoEditCreateRepo?.uploadProfilePic(profilePic, imageFile.name, profile_photo)
                ?.observe(
                    EmployeeInfoActivity.context!!,
                    androidx.lifecycle.Observer { any ->
                        Log.e("yousuf", "profile pic : " + any)
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

    fun hasDisabilityData(): List<SpinnerDataModel> {

        var rel = SpinnerDataModel()
        rel.apply {
            id = 1
            name = "Yes"
            name_bn = "হ্যাঁ"

        }
        var rel1 = SpinnerDataModel()
        rel1.apply {
            id = 0
            name = "No"
            name_bn = "না"


        }
        return arrayListOf(rel, rel1)
    }

    fun invisiableAllError(binding: DialogPersonalInfoBinding) {
        binding.fNomineeName.tvError.visibility =
            View.GONE

        binding.fNomineeDob.tvError.visibility =
            View.GONE

        binding.fNomineeRelation.tvError.visibility =
            View.GONE

        binding.fNomineeAllocatedPercentage.tvError.visibility =
            View.GONE
        binding.fNomineeMaritalStatus.tvError.visibility =
            View.GONE
        binding.fNomineeGender.tvError.visibility =
            View.GONE
        binding.fNomineeHasDisability.tvError.visibility =
            View.GONE
        binding.fNominneAddresstypeCityCorpUpazilaMunicipality.tvError.visibility =
            View.GONE
        binding.fNominneAddressDistrict.tvError.visibility = View.GONE

        binding.fNominneAddressDivision.tvError.visibility = View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
    }
}