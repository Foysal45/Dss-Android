package com.dss.hrms.view.personalinfo.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
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
import com.dss.hrms.model.*
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.error.ApiError
import com.dss.hrms.model.error.ErrorUtils2
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.allInterface.SpecificDistrictValueListener
import com.dss.hrms.view.allInterface.UnionDataValueListener
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.personalinfo.adapter.*
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.personal_info_update_button.view.*
import javax.inject.Inject

class EditPermanentAddressInfo @Inject constructor() {
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var position: Int? = 0
    var dialogCustome: Dialog? = null
    var division: SpinnerDataModel? = null
    var district: SpinnerDataModel? = null
    var municipality: Municipalities? = null
    var localGovernmentType: SpinnerDataModel? = null
    var cityCorporations: CityCorporations? = null
    var specificDistrictModel: SpecificDistrictModel? = null

    //var upazila: SpinnerDataModel? = null
    var upazila: Upazilas? = null
    var union: Union? = null
    var permanentAddresses: Employee.PermanentAddresses? = null
    var binding: DialogPersonalInfoBinding? = null
    var context: Context? = null
    var isSameAsPresentAddress = false

    lateinit var key: String
    fun showDialog(
        context: Context,
        position: Int?,
        key: String
    ) {
        this.position = position
        this.permanentAddresses =
            position?.let { employeeProfileData?.employee?.permanentAddresses?.get(it) }
        this.context = context
        this.key = key
        dialogCustome = Dialog(context)
        dialogCustome?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_personal_info,
            null,
            false
        )
        binding?.getRoot()?.let { dialogCustome?.setContentView(it) }
        var window: Window? = dialogCustome?.getWindow()
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        updatePresentAddressInfo(context, permanentAddresses)
        dialogCustome?.show()

    }

    fun updatePresentAddressInfo(
        context: Context,
        permanentAddresses1: Employee.PermanentAddresses?
    ) {

        binding?.llAddress?.visibility = View.VISIBLE
        binding?.hAddress?.title = context.getString(R.string.permanent_address)
        Log.e("address", "permanent ${permanentAddresses1?.localGovernmentType?.name_bn}")
        //  binding?.hAddress?.tvTitle?.setText(context?.getString(R.string.permanent_address))
        binding?.hAddress?.tvClose?.setOnClickListener({
            dialogCustome?.dismiss()
        })

        permanentAddresses = permanentAddresses1
        binding?.cbSameAsPresentAddress?.setOnClickListener {
            if (binding?.cbSameAsPresentAddress?.isChecked == true) {
                employeeProfileData?.employee?.presentAddresses?.let {
                    if (it.size > 0) {
                        var presentAddressJson = Gson().toJson(it.get(0))
                        Log.e("TAG", "updatePresentAddressInfo:  $presentAddressJson")
                        var pmAddresses = Gson().fromJson(
                            presentAddressJson.toString(),
                            Employee.PermanentAddresses::class.java
                        )
                        updatePresentAddressInfo(context, pmAddresses)
                    }
                }
            } else {
                updatePresentAddressInfo(context, permanentAddresses)
            }
        }


        if (key.equals(StaticKey.CREATE)) {
            binding?.addressBtnUpdate?.btnUpdate?.setText("" + context.getString(R.string.submit))
        } else {
            binding?.addressBtnUpdate?.btnUpdate?.setText("" + context.getString(R.string.update))
        }

        binding?.fAddressPoliceStation?.etText?.setText(permanentAddresses1?.police_station)
        binding?.fAddressPoliceStationBn?.etText?.setText(permanentAddresses1?.police_station_bn)
        binding?.fAddressPostOffice?.etText?.setText(permanentAddresses1?.post_office)
        binding?.fAddressPostOfficeBn?.etText?.setText(permanentAddresses1?.post_office_bn)
        binding?.fAddressPostCode?.etText?.setText(permanentAddresses1?.post_code)
        binding?.fAddressRoadOrWordNo?.etText?.setText(permanentAddresses1?.road_word_no)
        binding?.fAddressRoadOrWordNoBn?.etText?.setText(permanentAddresses1?.road_word_no_bn)
        binding?.fAddressVillageOrHouseNo?.etText?.setText(permanentAddresses1?.village_house_no)
        binding?.fAddressVillageOrHouseNoBn?.etText?.setText(permanentAddresses1?.village_house_no_bn)


        commonRepo.getCommonData("/api/auth/division/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //   Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fAddressDivision?.spinner!!,
                            context,
                            list,
                            permanentAddresses1?.division_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    division = any as SpinnerDataModel
                                    getDistrict(division?.id, permanentAddresses1?.district_id)
                                }

                            }
                        )
                    }
                }
            })
        getLocalGovernmentType()

        binding?.addressBtnUpdate?.btnUpdate?.setOnClickListener {
            val employeeInfoEditCreateRepo = ViewModelProviders.of(MainActivity.context!!, viewModelProviderFactory).get(EmployeeInfoEditCreateViewModel::class.java)


            invisiableAllError(binding)
            val dialog = CustomLoadingDialog().createLoadingDialog(EmployeeInfoActivity.context)
            key.let {
                if (it == StaticKey.EDIT) {
                    var id: Int? = 0;
                    if (key == StaticKey.EDIT && permanentAddresses1?.isPendingData == false) {
                        id = permanentAddresses1.id
                    } else if (key == StaticKey.EDIT && permanentAddresses1?.isPendingData == true) {
                        id = permanentAddresses1.parent_id
                    }


                    employeeInfoEditCreateRepo.updatePermanentInfo(
                        id,
                        getMapData()
                    )
                        ?.observe(
                            EmployeeInfoActivity.context!!,
                            { any ->
                                dialog?.dismiss()

                                showResponse(any)

                            })
                } else {
                    employeeInfoEditCreateRepo.addPermanentInfo(getMapData())
                        ?.observe(
                            EmployeeInfoActivity.context!!,
                            Observer { any ->
                                dialog?.dismiss()
                                Log.e("yousuf", "error : " + Gson().toJson(any))
                                showResponse(any)

                            })
                }
            }
        }

    }

    fun showResponse(any: Any) {

        when (any) {
            is String -> {
                toast(EmployeeInfoActivity.context, "" + context?.getString(R.string.updated))

                MainActivity.selectedPosition = 4
                EmployeeInfoActivity.refreshEmployeeInfo()
                dialogCustome?.dismiss()
            }
            is ApiError -> {
                try {
                    if (any.getError().isEmpty()) {
                        toast(EmployeeInfoActivity.context, any.getMessage())
                    } else {
                        for (n in any.getError().indices) {
                            val error = any.getError()[n].getField()
                            val message = any.getError()[n].getMessage()
                            if (TextUtils.isEmpty(error)) {
                                message?.let {
                                    binding?.fAddressVillageOrHouseNoBn?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressVillageOrHouseNoBn?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }

                            }
                            when (error) {
                                "division_id" -> {
                                    binding?.fAddressDivision?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressDivision?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "district_id" -> {
                                    binding?.fAddressDistrict?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressDistrict?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }

                                "police_station" -> {
                                    binding?.fAddressPoliceStation?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressPoliceStation?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }

                                "police_station_bn" -> {
                                    binding?.fAddressPoliceStationBn?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressPoliceStationBn?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "post_office" -> {
                                    binding?.fAddressPostOffice?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressPostOffice?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "post_office_bn" -> {
                                    binding?.fAddressPostOfficeBn?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressPostOfficeBn?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "post_code" -> {
                                    binding?.fAddressPostCode?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressPostCode?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "road_word_no" -> {
                                    binding?.fAddressRoadOrWordNo?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressRoadOrWordNo?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }

                                "road_word_no_bn" -> {
                                    binding?.fAddressRoadOrWordNoBn?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressRoadOrWordNoBn?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "village_house_no" -> {
                                    binding?.fAddressVillageOrHouseNo?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressVillageOrHouseNo?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "village_house_no_bn" -> {
                                    binding?.fAddressVillageOrHouseNoBn?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressVillageOrHouseNoBn?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "local_government_type_id" -> {
                                    binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "city_corporation_id" -> {
                                    binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "municipality_id" -> {
                                    binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "upazila_id" -> {
                                    binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.text =
                                        ErrorUtils2.mainError(message)
                                }
                                "union_id" -> {
                                    binding?.fAddressUnio?.tvError?.visibility =
                                        View.VISIBLE
                                    binding?.fAddressUnio?.tvError?.text =
                                        ErrorUtils2.mainError(message)
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
                EmployeeInfoActivity?.context?.getString(R.string.failed)?.let {
                    toast(EmployeeInfoActivity.context, it)
                }
            }
        }
    }

    fun getMapData(): HashMap<Any, Any?> {
        var map = HashMap<Any, Any?>()
        map.put("employee_id", employeeProfileData?.employee?.user?.employee_id)
        map.put("division_id", division?.id)
        map.put("district_id", district?.id)
        localGovernmentType?.id?.let { map.put("local_government_type_id", it) }
        municipality?.id?.let { map.put("municipality_id", it) }
        cityCorporations?.id?.let { map.put("city_corporation_id", it) }
        upazila?.id?.let { map.put("upazila_id", it) }
        union?.id?.let { map.put("union_id", it) }
        map.put("police_station", binding?.fAddressPoliceStation?.etText?.text.toString())
        map.put("police_station_bn", binding?.fAddressPoliceStationBn?.etText?.text.toString())
        map.put("post_office", binding?.fAddressPostOffice?.etText?.text.toString())
        map.put("post_office_bn", binding?.fAddressPostOfficeBn?.etText?.text.toString())
        map.put("post_code", binding?.fAddressPostCode?.etText?.text.toString())
        map.put("road_word_no", binding?.fAddressRoadOrWordNo?.etText?.text.toString())
        try {
            if (key == StaticKey.EDIT && permanentAddresses?.isPendingData == false) {
                map.put("parent_id", permanentAddresses?.id)
            } else if (key == StaticKey.EDIT && permanentAddresses?.isPendingData == true) {
                map.put("parent_id", permanentAddresses?.parent_id)
            }
        } catch (Ex: java.lang.Exception) {

        }

        map.put("road_word_no_bn", binding?.fAddressRoadOrWordNoBn?.etText?.text.toString())
        map.put("village_house_no", binding?.fAddressVillageOrHouseNo?.etText?.text.toString())
        map.put("village_house_no_bn", binding?.fAddressVillageOrHouseNoBn?.etText?.text.toString())
        if (permanentAddresses?.status != null) {
            map.put("status", permanentAddresses?.status)
        } else map.put("status", 0)
        Log.d("TAG", "getMapData: ${map.toString()}")
        return map
    }


    fun getDistrict(divisionId: Int?, districtId: Int?) {
        commonRepo.getDistrict(divisionId,
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fAddressDistrict?.spinner!!,
                            context,
                            list,
                            districtId,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    district = any as SpinnerDataModel
                                    // getUpazila(district?.id, presentAddress?.upazila_id)
                                    getUpazilaWithMunicipalities(
                                        district?.id,
                                        permanentAddresses?.upazila_id
                                    )
                                }

                            }
                        )
                    }
                }
            })
    }


    fun setUpazila(upazilaId: Int?) {
        UpazilaAdapter().setUpazilaSpinner(
            binding?.fAddressUpazila?.spinner!!,
            context,
            specificDistrictModel?.upazilas,
            upazilaId,
            object : CommonSpinnerSelectedItemListener {
                override fun selectedItem(any: Any?) {
                    //upazila = any as SpinnerDataModel
                    upazila = null
                    any?.let {
                        upazila = any as Upazilas
                        setUnion(upazila?.id, permanentAddresses?.union_id)
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
                            binding?.fAddressUnio?.spinner!!,
                            context,
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

    fun getLocalGovernmentType() {
        commonRepo.getCommonData("/api/auth/local-government-type/list",
            object : CommonDataValueListener {
                override fun valueChange(list: List<SpinnerDataModel>?) {
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    list?.let {
                        SpinnerAdapter().setSpinner(
                            binding?.fAddresstypeCityCorpUpazilaMunicipality?.spinner!!,
                            context,
                            list,
                            permanentAddresses?.local_government_type_id,
                            object : CommonSpinnerSelectedItemListener {
                                override fun selectedItem(any: Any?) {
                                    localGovernmentType = any as SpinnerDataModel
                                    setCityMuniUpailaAccordingToType()
                                }
                            }
                        )
                    }
                }
            })
    }

    fun getUpazilaWithMunicipalities(districtId: Int?, upazilaId: Int?) {
        commonRepo.getUpazilaWithMunicipalities(districtId,
            object : SpecificDistrictValueListener {
                override fun valueChange(data: SpecificDistrictModel?) {
                    specificDistrictModel = data
                    //    Log.e("gender", "gender message " + Gson().toJson(list))
                    setCityMuniUpailaAccordingToType()
                }
            })
    }

    fun setMunicipalities(list: List<Upazilas>?, id: Int?) {
        list?.let {
            MunicipalitiesAdapter().setMunicipalitiesSpinner(
                binding?.fAddressMunicipalities?.spinner!!,
                context,
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

    fun setCityCorporation(list: List<CityCorporations>?, id: Int?) {
        list?.let {
            CityCorporationsAdapter().setCityCorporationsSpinner(
                binding?.fAddressCityCorporations?.spinner!!,
                context,
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

    fun getPoulatedMunicipalities(
        list: List<Upazilas>?
    ): List<Municipalities> {
        var municipalitiesList: List<Municipalities> = arrayListOf()
        list?.let {
            it.forEach({ uList ->
                uList?.municipalities?.let { uListElement ->
                    municipalitiesList += uListElement
                }
            })
        }
        return municipalitiesList
    }

    fun setCityMuniUpailaAccordingToType() {
        if (localGovernmentType?.id == 1) {
            upazila = null
            union = null
            municipality = null
            binding?.fAddressCityCorporations?.llBody?.visibility = View.VISIBLE
            binding?.fAddressMunicipalities?.llBody?.visibility = View.GONE
            binding?.fAddressUpazila?.llBody?.visibility = View.GONE
            binding?.fAddressUnio?.llBody?.visibility = View.GONE
            setCityCorporation(
                specificDistrictModel?.city_corporations,
                permanentAddresses?.city_corporation_id
            )
        } else if (localGovernmentType?.id == 2) {
            upazila = null
            union = null
            cityCorporations = null
            binding?.fAddressMunicipalities?.llBody?.visibility = View.VISIBLE
            binding?.fAddressCityCorporations?.llBody?.visibility = View.GONE
            binding?.fAddressUpazila?.llBody?.visibility = View.GONE
            binding?.fAddressUnio?.llBody?.visibility = View.GONE
            setMunicipalities(specificDistrictModel?.upazilas, permanentAddresses?.municipality_id)
        } else if (localGovernmentType?.id == 3) {
            cityCorporations = null
            municipality = null
            binding?.fAddressUpazila?.llBody?.visibility = View.VISIBLE
            binding?.fAddressUnio?.llBody?.visibility = View.VISIBLE
            binding?.fAddressMunicipalities?.llBody?.visibility = View.GONE
            binding?.fAddressCityCorporations?.llBody?.visibility = View.GONE
            setUpazila(permanentAddresses?.upazila_id)
        } else {
            cityCorporations = null
            municipality = null
            upazila = null
            union = null
            binding?.fAddressMunicipalities?.llBody?.visibility = View.GONE
            binding?.fAddressCityCorporations?.llBody?.visibility = View.GONE
            binding?.fAddressUpazila?.llBody?.visibility = View.GONE
            binding?.fAddressUnio?.llBody?.visibility = View.GONE
        }
    }

    fun invisiableAllError(binding: DialogPersonalInfoBinding??) {
        binding?.fAddressDivision?.tvError?.visibility =
            View.GONE

        binding?.fAddressDistrict?.tvError?.visibility =
            View.GONE

        binding?.fAddressUpazila?.tvError?.visibility =
            View.GONE

        binding?.fAddressPoliceStation?.tvError?.visibility =
            View.GONE
        binding?.fAddressPoliceStationBn?.tvError?.visibility =
            View.GONE
        binding?.fAddressPostOffice?.tvError?.visibility =
            View.GONE
        binding?.fAddressPostOfficeBn?.tvError?.visibility =
            View.GONE
        binding?.fAddressPostCode?.tvError?.visibility =
            View.GONE
        binding?.fAddressRoadOrWordNo?.tvError?.visibility =
            View.GONE
        binding?.fAddressRoadOrWordNoBn?.tvError?.visibility =
            View.GONE
        binding?.fAddressVillageOrHouseNo?.tvError?.visibility =
            View.GONE
        binding?.fAddressVillageOrHouseNoBn?.tvError?.visibility =
            View.GONE
        binding?.fAddresstypeCityCorpUpazilaMunicipality?.tvError?.visibility =
            View.GONE
        binding?.fAddressCityCorporations?.tvError?.visibility =
            View.GONE
        binding?.fAddressMunicipalities?.tvError?.visibility =
            View.GONE
    }

    fun toast(context: Context?, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
    }
}