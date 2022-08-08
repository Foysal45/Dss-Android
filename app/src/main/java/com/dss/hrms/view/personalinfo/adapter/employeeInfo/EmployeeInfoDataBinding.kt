package com.dss.hrms.view.personalinfo.adapter.employeeInfo

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelEmployeeInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.commonSpinnerDataLoad.CommonData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.ConvertNumber
import com.dss.hrms.util.DateConverter
import com.dss.hrms.util.HelperClass
import com.dss.hrms.view.personalinfo.adapter.name_view_row_adapter
import com.dss.hrms.view.personalinfo.dialog.EditOfficialResidentialIInfo
import com.namaztime.namaztime.database.MySharedPreparence
import java.util.*
import javax.inject.Inject


class EmployeeInfoDataBinding @Inject constructor() {
    //
    @Inject
    lateinit var commonRepo: CommonRepo

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    @Inject
    lateinit var preparence: MySharedPreparence

    var employee: Employee? = null


    fun bindPermanentAddressData(
        binding: ModelEmployeeInfoBinding,
        addresses: Employee.PermanentAddresses,
        context: Context,
        heading: String
    ) {
        binding.fAddressUpazila.llBody.visibility = View.GONE
        binding.fAddressEmailAddress.llBody.visibility = View.GONE
        binding.fAddressPhoneOrMobileNo.llBody.visibility = View.GONE
        binding.hAddress.tvTitle.text = heading
        binding.fAddressPostOffice.tvTitle.text = context.getString(R.string.post_off)
        binding.fAddressPostOfficeBn.tvTitle.text = context.getString(R.string.post_off_bn)
        binding.fAddressRoadOrWordNo.tvTitle.text = context.getString(R.string.road_word)
        binding.fAddressRoadOrWordNoBn.tvTitle.text = context.getString(R.string.road_word_bn)
        binding.fAddressPoliceStation.tvTitle.text = context.getString(R.string.police_station)
        binding.fAddressPostCode.tvTitle.text = context.getString(R.string.post_code)
        binding.fAddressDivision.tvTitle.text = context.getString(R.string.division)
        binding.fAddressDistrict.tvTitle.text = context.getString(R.string.district)
        binding.fAddressUpazila.tvTitle.text = context.getString(R.string.upazila)
        binding.fAddressUnion.tvTitle.text = context.getString(R.string.union_or_municiplity)
        binding.fAddressPoliceStationBn.tvTitle.text = context.getString(R.string.police_station_bn)
        binding.fAddressPhoneOrMobileNo.tvTitle.text = context.getString(R.string.phone_mobile)
        binding.fAddressVillageOrHouseNo.tvTitle.text = context.getString(R.string.vill_house)
        binding.fAddressVillageOrHouseNoBn.tvTitle.text = context.getString(R.string.vill_house_bn)
        binding.fAddressEmailAddress.tvTitle.text = context.getString(R.string.email)

        HelperClass.addHeaderColor(
            binding.hAddress,
            context,
            addresses.isPendingData
        )

        if (!addresses.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
                addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
                addresses.village_house_no_bn?.let { binding.fAddressVillageOrHouseNoBn.tvText.text = it }
                addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.text = it }
                addresses.road_word_no_bn?.let { binding.fAddressRoadOrWordNoBn.tvText.text = it }
                addresses.localGovernmentType?.name?.let { binding.fAddressUnion.tvText.text = it }
                addresses.post_office?.let { binding.fAddressPostOffice.tvText.text = it }
                addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.text = it }
                addresses.post_code?.let { binding.fAddressPostCode.tvText.text = it }
                addresses.division?.name?.let { binding.fAddressDivision.tvText.text = it }
                addresses.district?.name?.let { binding.fAddressDistrict.tvText.text = it }
                addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.text = it }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
                addresses.police_station_bn?.let { binding.fAddressPoliceStationBn.tvText.text = it }
//            addresses.upazila?.name?.let { binding.fAddressUpazila.tvText.setText(it) }

                when (addresses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        addresses.cityCorporation?.name?.let { binding.fAddressLocalName.tvText.text = it }
                        //binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.name
                        binding.fAddressUnionName.llBody.visibility = View.GONE

                    }
                    2 -> {
                        // municipility
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)
                        binding.fAddressLocalName.tvText.text = addresses.municipality?.name
                        binding.fAddressUnionName.llBody.visibility = View.GONE

                    }
                    3 -> {
                        // upzilla
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fAddressLocalName.tvText.text = addresses.upazila?.name
                        binding.fAddressUnionName.tvTitle.text = context.getString(R.string.union)
                        binding.fAddressUnionName.tvText.text = addresses.union?.name
                        binding.fAddressUnionName.llBody.visibility = View.VISIBLE

                    }
                }
            } else {
                addresses.localGovernmentType?.name_bn?.let { binding.fAddressUnion.tvText.text = it }
                addresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.text = it }
                addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
                addresses.village_house_no_bn?.let { binding.fAddressVillageOrHouseNoBn.tvText.text = it }
                addresses.road_word_no_bn?.let { binding.fAddressRoadOrWordNoBn.tvText.text = it }
                addresses.post_code?.let { binding.fAddressPostCode.tvText.text = it }
                addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.text = it }
                addresses.post_office?.let { binding.fAddressPostOffice.tvText.text = it }
                addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.text = it }
                addresses.police_station_bn?.let { binding.fAddressPoliceStationBn.tvText.text = it }
                addresses.division?.name_bn?.let { binding.fAddressDivision.tvText.text = it }
                addresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.text = it }
                addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.text = it }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
                addresses.upazila?.name_bn?.let { binding.fAddressUpazila.tvText.text = it }

                when (addresses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        addresses.cityCorporation?.name?.let {
                            binding.fAddressLocalName.tvText.text = it
                        }
                        //binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.nameBn
                        binding.fAddressUnionName.llBody.visibility = View.GONE
                    }

                    2 -> {
                        // municipility
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)
                        binding.fAddressLocalName.tvText.text = addresses.municipality?.name_bn
                        binding.fAddressUnionName.llBody.visibility = View.GONE
                    }

                    3 -> {
                        // upzilla
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fAddressLocalName.tvText.text = addresses.upazila?.name_bn
                        binding.fAddressUnionName.tvTitle.text = context.getString(R.string.union)
                        binding.fAddressUnionName.tvText.text = addresses.union?.name_bn
                        binding.fAddressUnionName.llBody.visibility = View.VISIBLE

                    }
                }

            }
        } else {

            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)

            binding.hAddress.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"

            addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
            addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
            addresses.village_house_no_bn?.let { binding.fAddressVillageOrHouseNoBn.tvText.text = it }
            addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.text = it }
            addresses.road_word_no_bn?.let { binding.fAddressRoadOrWordNoBn.tvText.text = it }
            binding.fAddressUnion.tvText.text = "${addresses.local_government_type_id}"
            addresses.post_office?.let { binding.fAddressPostOffice.tvText.text = it }
            addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.text = it }
            addresses.post_code?.let { binding.fAddressPostCode.tvText.text = it }

            binding.fAddressDistrict.tvText.text = HelperClass.getCommonDataFilltered(
                addresses.district_id,
                commonData?.districts,
                !preparence.getLanguage().equals("en")

            )


            binding.fAddressDivision.tvText.text = HelperClass.getCommonDataFilltered(
                addresses.division_id,
                commonData?.divisions,
                !preparence.getLanguage().equals("en")
            )

            addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.text = it }
            addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
            addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
            addresses.police_station_bn?.let { binding.fAddressPoliceStationBn.tvText.text = it }
//            addresses.upazila?.name?.let { binding.fAddressUpazila.tvText.setText(it) }

            when (addresses.local_government_type_id) {
                1 -> {
                    // city corpo
                    binding.fAddressLocalName.tvText.text = "${
                        addresses.city_corporation_id?.let {
                            HelperClass.getCommonDataFilltered(
                                it,
                                commonData?.city_corporations, !preparence.getLanguage().equals("en")
                            )
                        }
                    }"

                    //binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                    binding.fAddressUnion.tvText.text = context.getString(R.string.citycorporation)
                    binding.fAddressUnionName.llBody.visibility = View.GONE
                }

                2 -> {
                    // municipility


                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)
                    binding.fAddressLocalName.tvText.text = "${
                        addresses.municipality_id?.let {
                            HelperClass.getCommonDataFilltered(
                                it, commonData?.upazilla_municipalities, !preparence.getLanguage().equals("en")
                            )
                        }
                    }"
                    binding.fAddressUnionName.llBody.visibility = View.GONE
                    binding.fAddressUnion.tvText.text = context.getString(R.string.municipalities)
                }

                3 -> {
                    // upzilla
                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                    binding.fAddressUnion.tvTitle.text = context.getString(R.string.union)
                    binding.fAddressUnionName.tvTitle.text = context.getString(R.string.union)

                    binding.fAddressLocalName.tvText.text = addresses.upazila_id.let {
                        HelperClass.getCommonDataFilltered(
                            it, commonData?.upazilas, !preparence.getLanguage().equals("en")
                        )
                    }

                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                    binding.fAddressUnion.tvTitle.text = context.getString(R.string.union)
                    binding.fAddressUnion.llBody.visibility = View.GONE

                    binding.fAddressUnionName.tvText.text = "${
                        addresses.union_id?.let {
                            HelperClass.getCommonDataFilltered(
                                it, commonData?.unions, !preparence.getLanguage().equals("en")
                            )
                        }
                    }"
                    binding.fAddressUnionName.llBody.visibility = View.VISIBLE
                }
            }
        }

    }


    fun bindPrensentAddressData(
        binding: ModelEmployeeInfoBinding,
        addresses: Employee.PresentAddresses,
        context: Context,
        heading: String
    ) {

        binding.hAddress.tvTitle.text = heading
        binding.fAddressUpazila.llBody.visibility = View.GONE
        binding.fAddressEmailAddress.llBody.visibility = View.GONE
        binding.fAddressPhoneOrMobileNo.llBody.visibility = View.GONE

        HelperClass.addHeaderColor(
            binding.hAddress,
            context,
            addresses.isPendingData
        )
        binding.fAddressPostOffice.tvTitle.text = context.getString(R.string.post_off)
        binding.fAddressPostOfficeBn.tvTitle.text = context.getString(R.string.post_off_bn)
        binding.fAddressPostCode.tvTitle.text = context.getString(R.string.post_code)
        binding.fAddressRoadOrWordNo.tvTitle.text = context.getString(R.string.road_word)
        binding.fAddressRoadOrWordNoBn.tvTitle.text = context.getString(R.string.road_word_bn)
        binding.fAddressPoliceStation.tvTitle.text = context.getString(R.string.police_station)
        binding.fAddressDivision.tvTitle.text = context.getString(R.string.division)
        binding.fAddressDistrict.tvTitle.text = context.getString(R.string.district)
        binding.fAddressUpazila.tvTitle.text = context.getString(R.string.upazila)
        binding.fAddressUnion.tvTitle.text = context.getString(R.string.union_or_municiplity)
        binding.fAddressPoliceStationBn.tvTitle.text = context.getString(R.string.police_station_bn)
        binding.fAddressPhoneOrMobileNo.tvTitle.text = context.getString(R.string.phone_mobile)
        binding.fAddressVillageOrHouseNo.tvTitle.text = context.getString(R.string.vill_house)
        binding.fAddressVillageOrHouseNoBn.tvTitle.text = context.getString(R.string.vill_house_bn)
        binding.fAddressEmailAddress.tvTitle.text = context.getString(R.string.email)

        if (!addresses.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
                addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
                addresses.village_house_no_bn?.let { binding.fAddressVillageOrHouseNoBn.tvText.text = it }
                addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.text = it }
                addresses.road_word_no_bn?.let { binding.fAddressRoadOrWordNoBn.tvText.text = it }
                addresses.post_office?.let { binding.fAddressPostOffice.tvText.text = it }
                addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.text = it }
                addresses.post_code?.let { binding.fAddressPostCode.tvText.text = it }
                addresses.division?.name?.let { binding.fAddressDivision.tvText.text = it }
                addresses.district?.name?.let { binding.fAddressDistrict.tvText.text = it }
                addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.text = it }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
                addresses.upazila?.name?.let { binding.fAddressUpazila.tvText.text = it }
                addresses.police_station_bn?.let { binding.fAddressPoliceStationBn.tvText.text = it }
                addresses.localGovernmentType?.name?.let { binding.fAddressUnion.tvText.text = it }

                // adding check for  cilty , municipility  or union

                when (addresses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        addresses.cityCorporation?.name?.let { binding.fAddressLocalName.tvText.text = it }
                        //binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.name
                        binding.fAddressUnionName.llBody.visibility = View.GONE

                    }
                    2 -> {
                        // municipility
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)
                        binding.fAddressLocalName.tvText.text = addresses.municipality?.name
                        binding.fAddressUnionName.llBody.visibility = View.GONE

                    }
                    3 -> {
                        // upzilla
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fAddressLocalName.tvText.text = addresses.upazila?.name
                        binding.fAddressUnionName.tvTitle.text = context.getString(R.string.union)
                        binding.fAddressUnionName.tvText.text = addresses.Union?.name
                        binding.fAddressUnionName.llBody.visibility = View.VISIBLE

                    }
                }


            } else {

                addresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.text = it }
                addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
                addresses.village_house_no_bn?.let { binding.fAddressVillageOrHouseNoBn.tvText.text = it }
                addresses.road_word_no_bn?.let { binding.fAddressRoadOrWordNoBn.tvText.text = it }
                addresses.post_code?.let { binding.fAddressPostCode.tvText.text = it }
                addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.text = it }
                addresses.post_office?.let { binding.fAddressPostOffice.tvText.text = it }
                addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.text = it }
                addresses.police_station_bn?.let { binding.fAddressPoliceStationBn.tvText.text = it }
                addresses.division?.name_bn?.let { binding.fAddressDivision.tvText.text = it }
                addresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.text = it }
                addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.text = it }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
                addresses.upazila?.name_bn?.let { binding.fAddressUpazila.tvText.text = it }
                addresses.localGovernmentType?.name_bn?.let { binding.fAddressUnion.tvText.text = it }

                when (addresses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        addresses.cityCorporation?.name?.let { binding.fAddressLocalName.tvText.text = it }
                        //binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.nameBn
                        binding.fAddressUnionName.llBody.visibility = View.GONE
                    }

                    2 -> {
                        //municipility
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)
                        binding.fAddressLocalName.tvText.text = addresses.municipality?.name_bn
                        binding.fAddressUnionName.llBody.visibility = View.GONE
                    }

                    3 -> {
                        //upzilla
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fAddressLocalName.tvText.text = addresses.upazila?.name_bn
                        binding.fAddressUnionName.tvTitle.text = context.getString(R.string.union)
                        binding.fAddressUnionName.tvText.text = addresses.Union?.name_bn
                        binding.fAddressUnionName.llBody.visibility = View.VISIBLE

                    }
                }
            }
        } else if (addresses.isPendingData) {
            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)
            binding.hAddress.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"

            addresses.village_house_no?.let { binding.fAddressVillageOrHouseNo.tvText.text = it }
            addresses.village_house_no_bn?.let { binding.fAddressVillageOrHouseNoBn.tvText.text = it }
            addresses.road_word_no_bn?.let { binding.fAddressRoadOrWordNoBn.tvText.text = it }
            addresses.post_code?.let { binding.fAddressPostCode.tvText.text = it }
            addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.text = it }
            addresses.post_office?.let { binding.fAddressPostOffice.tvText.text = it }
            addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.text = it }
            binding.fAddressPoliceStationBn.tvText.text = addresses.police_station_bn + ""

            binding.fAddressDistrict.tvText.text = HelperClass.getCommonDataFilltered(
                addresses.district_id,
                commonData?.districts,
                !preparence.getLanguage().equals("en"))


            binding.fAddressDivision.tvText.text = HelperClass.getCommonDataFilltered(
                addresses.division_id,
                commonData?.divisions,
                !preparence.getLanguage().equals("en"))

//            addresses.division_id?.let { binding.fAddressDivision.tvText.setText(it.toString()) }
//            addresses.district_id?.let { binding.fAddressDistrict.tvText.text = it.toString() }
            addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.text = it }
            addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
            addresses.upazila_id.let { binding.fAddressUpazila.tvText.text = it.toString() }

//            addresses.localGovernmentType?.name_bn?.let {
//                binding.fAddressUnion.tvText.text = it
//            }


            when (addresses.local_government_type_id) {
                1 -> {
                    // city corpo

                    /*
                     addresses.cityCorporation?.name?.let {
                            binding.fAddressLocalName.tvText.text = it
                        }

                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.nameBn

                        binding.fAddressUnionName.llBody.visibility = View.GONE
                     */
                    binding.fAddressUnion.tvText.text =  context.getString(R.string.citycorporation)
                    binding.fAddressLocalName.tvText.text = "${
                        addresses.city_corporation_id?.let {
                            HelperClass.getCommonDataFilltered(
                                it, commonData?.city_corporations, !preparence.getLanguage().equals("en")
                            )
                        }
                    }"
                    //binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                    // binding.fAddressUnion.tvText.text = context.getString(R.string.citycorporation)
                    binding.fAddressUnionName.llBody.visibility = View.GONE
                }

                2 -> {
                    // municipility
                    /*
                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.municipalities)
                        binding.fAddressLocalName.tvText.text = addresses.municipality?.name_bn
                        binding.fAddressUnionName.llBody.visibility = View.GONE
                     */
                    binding.fAddressUnion.tvText.text =  context.getString(R.string.municipality)
                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)

                    binding.fAddressLocalName.tvText.text = "${
                        addresses.municipality_id?.let {
                            HelperClass.getCommonDataFilltered(
                                it, commonData?.upazilla_municipalities, !preparence.getLanguage().equals("en")
                            )
                        }
                    }"
                    binding.fAddressUnionName.llBody.visibility = View.GONE
                    //  binding.fAddressUnion.tvText.text = context.getString(R.string.municipalities)
                }

                3 -> {
                    // upzilla
                    /*
                         binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fAddressLocalName.tvText.text = addresses.upazila?.name_bn
                        binding.fAddressUnionName.tvTitle.text = context.getString(R.string.union)
                        binding.fAddressUnionName.tvText.text = addresses.Union?.name_bn
                        binding.fAddressUnionName.llBody.visibility = View.VISIBLE
                     */
                    binding.fAddressUnion.tvText.text =  context.getString(R.string.upazila)
                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                    binding.fAddressLocalName.tvText.text = addresses.upazila_id.let {
                        HelperClass.getCommonDataFilltered(
                            it, commonData?.upazilas, !preparence.getLanguage().equals("en")
                        )
                    }

                    binding.fAddressUnionName.tvTitle.text = context.getString(R.string.union)
                    binding.fAddressUnionName.tvText.text = "${
                        addresses.union_id?.let {
                            HelperClass.getCommonDataFilltered(
                                it, commonData?.unions, !preparence.getLanguage().equals("en")
                            )
                        }
                    }"
                    binding.fAddressUnionName.llBody.visibility = View.VISIBLE
                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
//                    binding.fAddressUnion.tvTitle.text = context.getString(R.string.union)
//                    binding.fAddressUnion.llBody.visibility = View.GONE
//                    binding.fAddressUnionName.llBody.visibility = View.VISIBLE

                }
            }


        }

    }


    fun bindEducationalQualificationData(
        binding: ModelEmployeeInfoBinding,
        qualifications: Employee.EducationalQualifications,
        context: Context,
        heading: String
    ) {

        binding.fEQBoardOrUniversity.llBody.visibility = View.GONE
        binding.fEQNameOIn.llBody.visibility = View.GONE
        binding.hEducationQualification.tvTitle.text = heading
        binding.fEQNameOfD.tvTitle.text = context.getString(R.string.name_deg)
        binding.fEQNameOIn.tvTitle.text = context.getString(R.string.name_institution)
        binding.fEQBoardOrUniversity.tvTitle.text = context.getString(R.string.name_of_board)
        binding.fEQPassingYear.tvTitle.text = context.getString(R.string.passing_year)
        binding.fEQDivisionOrCgpa.tvTitle.text = context.getString(R.string.div_cgpa)
        binding.fEQAttachment.tvTitle.text = context.getString(R.string.attachment)

        HelperClass.addHeaderColor(
            binding.hEducationQualification,
            context,
            qualifications.isPendingData
        )
        // check what type of attachment we are getting

        ConvertNumber.setIconOnTextView(
            binding.fEQAttachment.icon,
            binding.fEQAttachment.tvText,
            qualifications.documentPath,
            context
        )

//        val extentions = ConvertNumber.getTheFileExtention(qualifications.documentPath)
//        binding.fEQAttachment.tvText.text = (context.getString(R.string.tap_to_view))
//
//        if (extentions.isEmpty()) {
//            binding.fEQAttachment.tvText.text = "No Attachment"
//        }
//        if (extentions.contains("jpeg") || extentions.contains("jpg") || extentions.contains("gif")) {
//
//            binding.fEQAttachment.icon.setImageResource(R.drawable.ic_picture)
//
//        } else {
//            binding.fEQAttachment.icon.setImageResource(R.drawable.ic_pdf)
//        }

        binding.fEQAttachment.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(context, qualifications.documentPath)
        }

        qualifications.board?.let {
            binding.fEQBoardOrUniversity.llBody.visibility = View.VISIBLE
            binding.fEQNameOIn.llBody.visibility = View.GONE
        }
        qualifications.educational_institute?.let {
            binding.fEQBoardOrUniversity.llBody.visibility = View.GONE
            binding.fEQNameOIn.llBody.visibility = View.VISIBLE
        }


        if (!qualifications.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                qualifications.examination?.name?.let { binding.fEQNameOfD.tvText.text = it }
                qualifications.educational_institute?.name.let { binding.fEQNameOIn.tvText.text = it }
                qualifications.board?.name.let { binding.fEQBoardOrUniversity.tvText.text = it }
                qualifications.passing_year?.let { binding.fEQPassingYear.tvText.text = it }
                qualifications.division_cgpa?.let { binding.fEQDivisionOrCgpa.tvText.text = it }

            } else {

                qualifications.examination?.name_bn?.let { binding.fEQNameOfD.tvText.text = it }
                qualifications.educational_institute?.name_bn.let { binding.fEQNameOIn.tvText.text = it }
                qualifications.board?.name_bn.let { binding.fEQBoardOrUniversity.tvText.text = it }
                qualifications.passing_year?.let { binding.fEQPassingYear.tvText.text = it }
                qualifications.division_cgpa?.let { binding.fEQDivisionOrCgpa.tvText.text = it }

            }

        } else {

            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)
            binding.hEducationQualification.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            //    binding.hEducationQualification.tvEdit.visibility = View.GONE
            binding.fEQNameOfD.tvText.text = "${
                HelperClass.getCommonDataFilltered(
                    qualifications.examination_id, commonData?.examinations, false
                )
            }}"

            binding.fEQNameOIn.tvText.text = "${qualifications.educational_institute_id}"
            binding.fEQBoardOrUniversity.tvText.text = HelperClass.getCommonDataFilltered(
                qualifications.board_id,
                commonData?.boards,
                false
            )

            qualifications.passing_year?.let { binding.fEQPassingYear.tvText.text = it }
            qualifications.division_cgpa?.let { binding.fEQDivisionOrCgpa.tvText.text = it }

        }

    }


    fun bindNomineeData(
        binding: ModelEmployeeInfoBinding,
        nominee: Employee.Nominee,
        context: Context,
        heading: String
    ) {

        binding.hNominee.tvTitle.text = heading
        binding.fNName.tvTitle.text = context.getString(R.string.name_of_nominee)
        binding.fNDOB.tvTitle.text = context.getString(R.string.birth)
        binding.fNRelation.tvTitle.text = context.getString(R.string.nominee_relation)
        binding.fNAllocatedPercentage.tvTitle.text = context.getString(R.string.nominee_allocated_percentage)
        binding.fNGender.tvTitle.text = context.getString(R.string.nominee_gender)
        binding.fNMaritalStatus.tvTitle.text = context.getString(R.string.nominee_marital_status)
        binding.fNHasDisavility.tvTitle.text = context.getString(R.string.nominee_has_disability)
        binding.tvNSignatureTitle.text = context.getString(R.string.nominee_signature)
        binding.fNAddressDivision.tvTitle.text = context.getString(R.string.division)
        binding.fNAddressDistrict.tvTitle.text = context.getString(R.string.district)
        //    binding.fNAddressUpazila.tvTitle.text = context.getString(R.string.upazila)
        binding.fNAddressUnion.tvTitle.text = context.getString(R.string.union_or_municiplity)

        nominee.localGovernmentType?.name?.let { binding.fNAddressUnion.tvText.text = it }

        HelperClass.addHeaderColor(binding.hNominee, context, nominee.isPendingData)

        nominee.name?.let { binding.fNName.tvText.text = it }
        nominee.date_of_birth?.let { binding.fNDOB.tvText.text = (DateConverter.changeDateFormateForShowing(it)) }
        nominee.relation?.let { binding.fNRelation.tvText.text = it }
        nominee.allocated_percentage?.let { binding.fNAllocatedPercentage.tvText.text = it }

        // check what type of attachment we are getting
        // setting icon on the view

        ConvertNumber.setIconOnTextView(
            binding.icon,
            binding.tvText,
            nominee.nominee_document_path,
            context
        )

        binding.tvText.setOnClickListener {
            val link = nominee.nominee_document_path
            ConvertNumber.viewFileInShareIntent(context, nominee.nominee_document_path.toString())
        }



        nominee.has_disability?.let {
            if (it){
                binding.fNHasDisavility.tvText.text = context.getString(R.string.yes)
            } else {
                binding.fNHasDisavility.tvText.text = context.getString(R.string.no)
            }
        }

        context.let {
            Glide.with(it).applyDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_baseline_image_24))
                .load(RetrofitInstance.BASE_URL + nominee.nominee_signature)
                .into(binding.ivNSignature)
        }

        val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)
        //setting address data
        binding.fNAddressDistrict.tvText.text = nominee.district_id?.let {
            HelperClass.getCommonDataFilltered(
                it, commonData?.districts, !preparence.getLanguage().equals("en")
            )
        }


        binding.fNAddressDivision.tvText.text = nominee.division_id?.let {
            HelperClass.getCommonDataFilltered(
                it, commonData?.divisions, !preparence.getLanguage().equals("en"))
        }

        binding.fNAddressUpazila.llBody.visibility = View.GONE

        when (nominee.local_government_type_id) {

            1 -> {
                // city corpo
                binding.fNAddressLocalName.tvText.text = "${
                    nominee.city_corporation_id?.let {
                        HelperClass.getCommonDataFilltered(
                            it, commonData?.city_corporations, !preparence.getLanguage().equals("en")
                        )
                    }
                }"
                // binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                binding.fNAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                binding.fNAddressUnion.tvText.text = context.getString(R.string.citycorporation)
                binding.fNAddressUnionName.llBody.visibility = View.GONE
            }

            2 -> {
                // municipility
                binding.fNAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)
                binding.fNAddressLocalName.tvText.text = "${
                    nominee.municipality_id?.let {
                        HelperClass.getCommonDataFilltered(
                            it, commonData?.upazilla_municipalities, !preparence.getLanguage().equals("en")
                        )
                    }
                }"
                binding.fNAddressUnionName.llBody.visibility = View.GONE
                binding.fNAddressUnion.tvText.text = context.getString(R.string.municipalities)
            }

            3 -> {
                // upzilla
                binding.fNAddressUpazila.llBody.visibility = View.GONE
                binding.fNAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                binding.fNAddressLocalName.tvText.text = nominee.upazila_id.let {
                    it?.let { it1 ->
                        HelperClass.getCommonDataFilltered(
                            it1, commonData?.upazilas, !preparence.getLanguage().equals("en")
                        )
                    }
                }

                binding.fNAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                binding.fNAddressUnion.tvText.text = context.getString(R.string.upazila)
                binding.fNAddressUnionName.tvTitle.text = context.getString(R.string.union)

                binding.fNAddressUnionName.tvText.text = nominee.union_id?.let {
                    HelperClass.getCommonDataFilltered(it,
                        commonData?.unions, !preparence.getLanguage().equals("en")
                    )
                }

                binding.fNAddressUnionName.llBody.visibility = View.VISIBLE

            }
        }

        if (!nominee.isPendingData) {
            if (preparence.getLanguage().equals("en")) {
                nominee.marital_status?.name?.let { binding.fNMaritalStatus.tvText.text = it }
                nominee.gender?.name?.let { binding.fNGender.tvText.text = it }
            } else {
                nominee.marital_status?.name_bn?.let { binding.fNMaritalStatus.tvText.text = it }
                nominee.gender?.name_bn?.let { binding.fNGender.tvText.text = it }
            }


        } else {

            binding.hNominee.tvTitle.text =
                " $heading (${context.getString(R.string.pending_data)})"


            binding.fNMaritalStatus.tvText.text = "${
                nominee.marital_status_id?.let {
                    commonData?.marital_status?.let { it1 ->
                        HelperClass.getCommonDataFilltered(
                            it, it1, !preparence.getLanguage().equals("en")
                        )
                    }
                }
            }"
            binding.fNGender.tvText.text = "${
                nominee.gender_id?.let {
                    commonData?.genders?.let { it1 ->
                        HelperClass.getCommonDataFilltered(
                            it, it1, !preparence.getLanguage().equals("en")
                        )
                    }
                }
            }"


        }
    }


    fun bindSpouseData(
        binding: ModelEmployeeInfoBinding,
        spouses: Employee.Spouses,
        context: Context,
        heading: String
    ) {
        binding.fSpouseAddressUpazila.llBody.visibility = View.GONE
        binding.hSpouse.tvTitle.text = heading
        binding.fSpouseNameBn.tvTitle.text = context.getString(R.string.name_b)
        binding.fSpouseNameEn.tvTitle.text = context.getString(R.string.name)
        binding.fSpouseOffice.tvTitle.text = context.getString(R.string.workstation)
        binding.fSpouseRelation.tvTitle.text = context.getString(R.string.relation)
        binding.fSpouseDivision.tvTitle.text = context.getString(R.string.division)
        binding.fSpouseDistrict.tvTitle.text = context.getString(R.string.district)
        binding.fSpouseThana.tvTitle.text = context.getString(R.string.upazila)
        binding.fSpouseJobType.tvTitle.text = context.getString(R.string.job_type)
        binding.fSpouseOccupation.tvTitle.text = context.getString(R.string.occupation)
        binding.fSpousePhoneNo.tvTitle.text = context.getString(R.string.phone)
        binding.fSpouseMobileNo.tvTitle.text = context.getString(R.string.mobile)
        binding.fSpouseReligion.tvTitle.text = context.getString(R.string.religion)
        binding.fSpouseAddressUpazila.tvTitle.text = context.getString(R.string.upazila)
        binding.fSpouseAddressUnion.tvTitle.text = context.getString(R.string.union_or_municiplity)
        binding.fSpouseAddressPoliceStation.tvTitle.text = context.getString(R.string.police_station)
        binding.fSpouseAddressPoliceStationBn.tvTitle.text = context.getString(R.string.police_station_bn)
        binding.fSpouseAddressPostOffice.tvTitle.text = context.getString(R.string.post_off)
        binding.fSpouseAddressPostOfficeBn.tvTitle.text = context.getString(R.string.post_off_bn)
        binding.fSpouseAddressPostCode.tvTitle.text = context.getString(R.string.post_code)
        binding.fSpouseAddressRoadOrWordNo.tvTitle.text = context.getString(R.string.road_word)
        binding.fSpouseAddressRoadOrWordNoBn.tvTitle.text = context.getString(R.string.road_word_bn)


        HelperClass.addHeaderColor(binding.hSpouse, context, spouses.isPendingData)

        binding.fSpousePhoneNo.tvTitle.text = context.getString(R.string.phone_mobile)
        binding.fSpouseAddressVillageOrHouseNo.tvTitle.text = context.getString(R.string.vill_house)
        binding.fSpouseAddressVillageOrHouseNoBn.tvTitle.text = context.getString(R.string.vill_house_bn)
        binding.fSpouseReligion.llBody.visibility = View.GONE

        binding.fSpouseAddressPoliceStation.tvText.text = spouses.police_station
        binding.fSpouseAddressPoliceStationBn.tvText.text = spouses.police_station_bn
        binding.fSpouseAddressPostOffice.tvText.text = spouses.post_office
        binding.fSpouseAddressPostOfficeBn.tvText.text = spouses.post_office_bn
        binding.fSpouseAddressPostCode.tvText.text = spouses.post_code
        binding.fSpouseAddressRoadOrWordNo.tvText.text = spouses.road_word_no
        binding.fSpouseAddressRoadOrWordNoBn.tvText.text = spouses.road_word_no_bn
        binding.fSpouseAddressVillageOrHouseNo.tvText.text = spouses.village_house_no
        binding.fSpouseAddressVillageOrHouseNoBn.tvText.text = spouses.village_house_no_bn

        if (!spouses.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                spouses.name_bn?.let {
                    binding.fSpouseNameBn.tvText.text = it
                }

                spouses.name?.let {
                    binding.fSpouseNameEn.tvText.text = it
                }

                spouses.spouse_workstation?.name?.let {
                    binding.fSpouseOffice.tvText.text = it
                }

                spouses.upazila?.name?.let {
                    binding.fSpouseThana.tvText.text = it
                }

                spouses.distric?.name?.let {
                    binding.fSpouseDistrict.tvText.text = it
                }

                spouses.division?.name?.let {
                    binding.fSpouseDivision.tvText.text = it
                }

                spouses.spouse_job_type?.name?.let {
                    binding.fSpouseJobType.tvText.text = it
                }

                spouses.occupation?.name.let {
                    binding.fSpouseOccupation.tvText.text = it
                }

                spouses.relation?.let {
                    binding.fSpouseRelation.tvText.text = it
                }

                spouses.phone_no?.let {
                    binding.fSpousePhoneNo.tvText.text = it
                }

                spouses.mobile_no?.let {
                    binding.fSpouseMobileNo.tvText.text = it
                }
                // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }
                spouses.village_house_no?.let {
                    binding.fSpouseAddressVillageOrHouseNo.tvText.text = it
                }

                spouses.village_house_no?.let {
                    binding.fSpouseAddressVillageOrHouseNo.tvText.text = it
                }
                spouses.village_house_no_bn?.let {
                    binding.fSpouseAddressVillageOrHouseNoBn.tvText.text = it
                }

                spouses.road_word_no?.let {
                    binding.fSpouseAddressRoadOrWordNo.tvText.text = it
                }

                spouses.road_word_no_bn?.let {
                    binding.fSpouseAddressRoadOrWordNoBn.tvText.text = it
                }
                spouses.post_office?.let {
                    binding.fSpouseAddressPostOffice.tvText.text = it
                }

                spouses.post_office_bn?.let {
                    binding.fSpouseAddressPostOfficeBn.tvText.text = it
                }

                spouses.post_code?.let {
                    binding.fSpouseAddressPostCode.tvText.text = it
                }

                spouses.division?.name?.let {
                    binding.fSpouseDivision.tvText.text = it
                }

                spouses.distric?.name?.let {
                    binding.fSpouseDistrict.tvText.text = it
                }

                spouses.phone_no?.let {
                    binding.fSpousePhoneNo.tvText.text = it
                }

                spouses.police_station?.let {
                    binding.fSpouseAddressPoliceStation.tvText.text = it
                }

                spouses.police_station_bn?.let {
                    binding.fSpouseAddressPoliceStationBn.tvText.text = it
                }
                spouses.upazila?.name?.let {
                    binding.fSpouseAddressUpazila.tvText.text = it
                }

                spouses.localGovernmentType?.name?.let {
                    binding.fSpouseAddressUnion.tvText.text = it
                }

                // adding check for  cilty , municipility  or union


                when (spouses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        spouses.cityCorporation?.name?.let {
                            binding.fSpouseAddressUnion.tvText.text = it
                        }
                        //    binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fSpouseAddressUnion.tvText.text = context.getString(R.string.citycorporation)
                        binding.fSpouseAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                        binding.fSpouseAddressLocalName.tvText.text = spouses.cityCorporation?.name
                        binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                        binding.fSpouseAddressUpazila.llBody.visibility = View.GONE

                    }
                    2 -> {
                        // municipility

                        binding.fSpouseAddressUnion.tvText.text = context.getString(R.string.municipalities)
                        binding.fSpouseAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)
                        binding.fSpouseAddressLocalName.tvText.text = spouses.municipality?.name
                        binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                        binding.fSpouseAddressUpazila.llBody.visibility = View.GONE
                    }
                    3 -> {
                        // upzilla
                        binding.fSpouseAddressUnion.tvText.text = context.getString(R.string.upazila)
                        binding.fSpouseAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fSpouseAddressLocalName.tvText.text = spouses.upazila?.name
                        binding.fSpouseAddressUnionName.tvTitle.text = context.getString(R.string.union)
                        binding.fSpouseAddressUnionName.tvText.text = spouses.union?.name
                        // binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                    }
                }


            } else {
                spouses.name_bn?.let {
                    binding.fSpouseNameBn.tvText.text = it
                }

                spouses.name?.let {
                    binding.fSpouseNameEn.tvText.text = it
                }

                spouses.spouse_workstation?.name_bn?.let {
                    binding.fSpouseOffice.tvText.text = it
                }

                spouses.upazila?.name_bn?.let {
                    binding.fSpouseThana.tvText.text = it
                }

                spouses.distric?.name_bn?.let {
                    binding.fSpouseDistrict.tvText.text = it
                }

                spouses.spouse_job_type?.name_bn?.let {
                    binding.fSpouseJobType.tvText.text = it
                }

                spouses.occupation?.name_bn.let {
                    binding.fSpouseOccupation.tvText.text = it
                }

                spouses.phone_no?.let {
                    binding.fSpousePhoneNo.tvText.text = it
                }

                spouses.mobile_no?.let {
                    binding.fSpouseMobileNo.tvText.text = it
                }
                spouses.relation?.let {
                    if (it.lowercase(Locale.getDefault()) == "wife") binding.fSpouseRelation.tvText.text = "স্ত্রী" else binding.fSpouseRelation.tvText.text = "স্বামী"
                }
                spouses.division?.name_bn?.let { binding.fSpouseDivision.tvText.text = it }
                // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }

                when (spouses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        spouses.cityCorporation?.name?.let {
                            binding.fSpouseAddressLocalName.tvText.text = it
                        }
                        //    binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fSpouseAddressLocalName.tvTitle.text = context.getString(R.string.citycorporation)
                        binding.fSpouseAddressUnion.tvText.text = context.getString(R.string.citycorporation)
                        binding.fSpouseAddressLocalName.tvText.text = spouses.cityCorporation?.nameBn
                        binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                        binding.fSpouseAddressUpazila.llBody.visibility = View.GONE

                    }
                    2 -> {
                        // municipility
                        binding.fSpouseAddressLocalName.tvTitle.text = context.getString(R.string.municipalities)
                        binding.fSpouseAddressUnion.tvText.text = context.getString(R.string.municipalities)
                        binding.fSpouseAddressLocalName.tvText.text = spouses.municipality?.name_bn
                        binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                        binding.fSpouseAddressUpazila.llBody.visibility = View.GONE
                    }
                    3 -> {
                        ///upzilaa

                        binding.fSpouseAddressUnion.tvText.text = context.getString(R.string.upazila)
                        binding.fSpouseAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fSpouseAddressLocalName.tvText.text = spouses.upazila?.name_bn
                        binding.fSpouseAddressUnionName.tvTitle.text = context.getString(R.string.union)
                        binding.fSpouseAddressUnionName.tvText.text = spouses.union?.name_bn

                        // binding.fSpouseAddressUnionName.llBody.visibility = View.GONElBody.visibility = View.GONE

                    }
                }


            }

        } else {

            binding.hSpouse.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"

            val obj: CommonData? = preparence.get(HelperClass.COMMON_DATA)


            spouses.name_bn?.let { binding.fSpouseNameBn.tvText.text = it }
            spouses.name?.let { binding.fSpouseNameEn.tvText.text = it }

            binding.fSpouseOffice.tvText.text = "${
                obj?.spouse_workstations?.let {
                    HelperClass.getCommonDataFilltered(
                        spouses.spouse_workstation_id,
                        it, false
                    )
                }
            }"

            binding.fSpouseThana.tvText.text = "${
                obj?.upazilas?.let {
                    spouses.upazila_id?.let { it1 ->
                        HelperClass.getCommonDataFilltered(
                            it1,
                            it, false
                        )
                    }
                }
            }"

            binding.fSpouseDistrict.tvText.text = "${
                obj?.districts?.let {
                    spouses.district_id.let { it1 ->
                        HelperClass.getCommonDataFilltered(
                            it1,
                            it, false
                        )
                    }
                }
            }"

            binding.fSpouseJobType.tvText.text = "${
                obj?.job_types?.let {
                    spouses.spouse_job_type_id?.let { it1 ->
                        HelperClass.getCommonDataFilltered(
                            it1,
                            it, false
                        )
                    }
                }
            }"

            binding.fSpouseOccupation.tvText.text = "${
                obj?.occupations?.let {
                    spouses.occupation_id.let { it1 ->
                        HelperClass.getCommonDataFilltered(
                            it1,
                            it, false
                        )
                    }
                }
            }"

            binding.fSpouseDivision.tvText.text = "${
                obj?.divisions?.let {
                    spouses.division_id.let { it1 ->
                        HelperClass.getCommonDataFilltered(
                            it1,
                            it, false
                        )
                    }
                }
            }"



            spouses.phone_no?.let { binding.fSpousePhoneNo.tvText.text = it }
            spouses.mobile_no?.let { binding.fSpouseMobileNo.tvText.text = it }
            spouses.relation?.let {
                if (it.lowercase(Locale.getDefault()) == "wife") binding.fSpouseRelation.tvText.text = "স্ত্রী" else binding.fSpouseRelation.tvText.text = "স্বামী"
            }

            // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }

            when (spouses.local_government_type_id) {
                1 -> {
                    // city corpo

                    binding.fSpouseAddressLocalName.tvText.text = "${
                        obj?.city_corporations?.let {
                            spouses.city_corporation_id?.let { it1 ->
                                HelperClass.getCommonDataFilltered(
                                    it1,
                                    it, false
                                )
                            }
                        }
                    }"


                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.citycorporation)
                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.citycorporation)

                    binding.fSpouseAddressLocalName.tvText.text =
                        binding.fSpouseAddressLocalName.tvText.text.toString()


                    binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                    binding.fSpouseAddressUpazila.llBody.visibility = View.GONE

                }
                2 -> {
                    // municipility
                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.municipalities)

                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.municipalities)


                    binding.fSpouseAddressLocalName.tvText.text = "${
                        obj?.upazilla_municipalities?.let {
                            spouses.municipality_id?.let { it1 ->
                                HelperClass.getCommonDataFilltered(
                                    it1,
                                    it, false
                                )
                            }
                        }
                    }"

                    binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                    binding.fSpouseAddressUpazila.llBody.visibility = View.GONE
                }
                3 -> {
                    ///upzilaa

                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.upazila)

                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.upazila)

                    binding.fSpouseAddressLocalName.tvText.text = "${
                        obj?.upazilas?.let {
                            spouses.upazila_id?.let { it1 ->
                                HelperClass.getCommonDataFilltered(
                                    it1,
                                    it, false
                                )
                            }
                        }
                    }"

                    binding.fSpouseAddressUnionName.tvText.text = "${
                        obj?.unions?.let {
                            spouses.union_id?.let { it1 ->
                                HelperClass.getCommonDataFilltered(
                                    it1,
                                    it, false
                                )
                            }
                        }
                    }"


                    binding.fSpouseAddressUnionName.tvTitle.text = context.getString(R.string.union)

                    // binding.fSpouseAddressUnionName.llBody.visibility = View.GONElBody.visibility = View.GONE

                }
            }


        }


    }

    fun bindJobjoningInfoData(
        binding: ModelEmployeeInfoBinding,
        jobjoinings: Employee.Jobjoinings,
        context: Context,
        heading: String
    ) {


        employee = employeeProfileData.employee
        HelperClass.addHeaderColor(binding.hJobJoiningInformation,context, jobjoinings.isPendingData)

        binding.hJobJoiningInformation.tvTitle.text = heading
        binding.fJobJoiningOffice.tvTitle.text = context.getString(R.string.office_type)
        binding.fJobJoiningDesignation.tvTitle.text = context.getString(R.string.current_designation)
        binding.fJobJoiningOtherServiceParticulars.tvTitle.text = context.getString(R.string.other_service_particular)
        binding.fJobJoiningJoiningDate.tvTitle.text = context.getString(R.string.joning_date)
        binding.fJobJoiningClass.tvTitle.text = context.getString(R.string._class)
        binding.fJobJoiningPayScale.tvTitle.text = context.getString(R.string.basic_pay)
        binding.fJobJoiningCurrentPayScaleGrade.tvTitle.text = context.getString(R.string.pay_scale_grade)
        binding.tvLanguageCertificate.text = context.getString(R.string.certificate)
        binding.fJobJoiningAdditionalChargeDesignation.llBody.visibility=View.GONE

        context.let {
            Glide.with(it).applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_person_24))
                    .load(employee?.photo)
                    .into(binding.ivLanguageCertificate)
        }


        if (!jobjoinings.isPendingData) {
            if (preparence.getLanguage().equals("en")) {

                jobjoinings.office_type.let {
                    if (jobjoinings.office_type == "divisional_office"){
                        binding.fJobJoiningOffice.tvText.text = context.getString(R.string.divisional) //"Divisional" //"বিভাগীয়"
                        binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = context.getString(R.string.office_division) //"Division" // "বিভাগ"
                        binding.fJobJoiningDepartmentWiseSection.tvTitle.text = context.getString(R.string.division_district) //District // জেলা
                        binding.fJobJoiningSectionWiseSubSection.tvTitle.text = context.getString(R.string.current_office) //Current Office // বর্তমান অফিস


                        jobjoinings.division?.name?.let {
                            binding.fJobJoiningOfficeDeptAndDivision.tvText.text = it
                        }

                        jobjoinings.district?.name?.let {
                            binding.fJobJoiningDepartmentWiseSection.tvText.text = it
                        }

                        jobjoinings.office?.office_name?.let {
                            binding.fJobJoiningSectionWiseSubSection.tvText.text = it
                        }


                        jobjoinings.employment_status_description?.let {
                            binding.fJobJoiningWhereToLien.tvText.text = it
                        }

                        val employeementstatusId = jobjoinings.employeementstatus?.id
                        when(employeementstatusId){
                            1->{
                                binding.fJobJoiningWhereToLien.tvTitle.text =  "Where to ${jobjoinings.employeementstatus.name}"//context.getString(R.string.where_to_lien)
                            }
                            2->{
                                binding.fJobJoiningWhereToLien.tvTitle.text = "Where to ${jobjoinings.employeementstatus.name}" //context.getString(R.string.where_to_deputed)
                            }
                            4->{
                                binding.fJobJoiningWhereToLien.tvTitle.text = "${jobjoinings.employeementstatus.name} Office"
                                binding.fJobJoiningWhereToLien.tvText.text = "${jobjoinings.additional_office?.office_name}"
                                binding.fJobJoiningAdditionalChargeDesignation.llBody.visibility=View.VISIBLE
                                binding.fJobJoiningAdditionalChargeDesignation.tvTitle.text = "${jobjoinings.employeementstatus.name} Designation"
                                binding.fJobJoiningAdditionalChargeDesignation.tvText.text = "${jobjoinings.additional_designation?.name}"
                            }
                        }


                      /*  //Division List  for Divisional Office Type
                        val divisioinId= jobjoinings.office?.division_id

                        //division_id wise Division setting
                        when(divisioinId){
                           0->{
                               binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_barisal)
                           }
                            1->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_chattogram)
                            }
                            2->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_dhaka)
                            }
                            3->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_khulna)
                            }
                            4->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_mymensing)
                            }
                            5->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_rajshahi)
                            }
                            6->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_rangpur)
                            }
                            7->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_sylhet)
                            }
                        }
*/
                        //this else logic for HeadOffice Type
                    }else{
                        binding.fJobJoiningOffice.tvText.text = context.getString(R.string.head_office)//"Head Office" // "েড অফিস"
                        binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = context.getString(R.string.head_office_department)//"Head Office Department" //"হেড অফিস বিভাগ"
                        binding.fJobJoiningDepartmentWiseSection.tvTitle.text = context.getString(R.string.department_wise_section) // Department Wise Section //বিভাগীয় বিভাগ
                        binding.fJobJoiningSectionWiseSubSection.tvTitle.text = context.getString(R.string.section_wise_sub_section) //Section Wise Sub Section //সেকশন ওয়াইজ সাব সেকশন


                        jobjoinings.head_office_section?.name?.let {
                            binding.fJobJoiningDepartmentWiseSection.tvText.text = it
                        }

                        jobjoinings.head_office_sub_section?.name?.let {
                            binding.fJobJoiningSectionWiseSubSection.tvText.text = it
                        }

                        jobjoinings.employment_status_description?.let {
                            binding.fJobJoiningWhereToLien.tvText.text = it
                        }

                        jobjoinings.office?.office_name?.let{
                            binding.fJobJoiningOfficeDeptAndDivision.tvText.text = it
                        }

                        val employeementstatusId = jobjoinings.employeementstatus?.id
                        when(employeementstatusId){
                            1->{
                                binding.fJobJoiningWhereToLien.tvTitle.text =  "Where to ${jobjoinings.employeementstatus.name}"//context.getString(R.string.where_to_lien)
                            }
                            2->{
                                binding.fJobJoiningWhereToLien.tvTitle.text = "Where to ${jobjoinings.employeementstatus.name}" //context.getString(R.string.where_to_deputed)
                            }
                            4->{
                                binding.fJobJoiningWhereToLien.tvTitle.text = "${jobjoinings.employeementstatus.name} Office"
                                binding.fJobJoiningWhereToLien.tvText.text = "${jobjoinings.additional_office?.office_name}"
                                binding.fJobJoiningAdditionalChargeDesignation.llBody.visibility=View.VISIBLE
                                binding.fJobJoiningAdditionalChargeDesignation.tvTitle.text = "${jobjoinings.employeementstatus.name} Designation"
                                binding.fJobJoiningAdditionalChargeDesignation.tvText.text = "${jobjoinings.additional_designation?.name}"
                            }
                        }

                        if (jobjoinings.office?.office_name == "DG Office" || jobjoinings.office?.office_name == "Somaj Seba Bhabon"){
                            binding.fJobJoiningDepartmentWiseSection.llBody.visibility=View.GONE
                            binding.fJobJoiningSectionWiseSubSection.llBody.visibility=View.GONE
                        }

                        if (jobjoinings.office?.office_name == "ADMINISTRATION & FINANCE" || jobjoinings.office?.office_name == "NATIONAL INSTITUTION"){
                            binding.fJobJoiningSectionWiseSubSection.llBody.visibility=View.GONE
                        }else if ((jobjoinings.office?.office_name == "ADMINISTRATION & FINANCE" && jobjoinings.head_office_section?.name == "PLANNING DEVELOPMENT RESEARCH & EVALUATION") || (jobjoinings.office?.office_name == "NATIONAL INSTITUTION" && jobjoinings.head_office_section?.name == "BRAILLE PRESS ARTIFICIAL PRODUCTION CENTRE FOR PHYSICAL HANDICAPPED")){
                            binding.fJobJoiningSectionWiseSubSection.llBody.visibility=View.VISIBLE
                        }

                       /* val headOfficeDepartmentId= jobjoinings.office?.head_office_department_id
                        when(headOfficeDepartmentId){   //headOfficeDepartmentId wise Department setting
                            0->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_dg_office)
                            }
                            1->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_administration_finance)
                            }
                            2->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_communiutey_service)
                            }
                            3->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_institute)
                            }
                            4->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_social_safety_net)
                            }
                            5->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_national_institution)
                            }
                            6->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_somaj_seba_bhaban)
                            }

                        }*/
                    }

                }

                // Out of if else , here is the Data Common for HeadOffice & DivisionalOffice
                val idForJoiningAndReleaseDate = jobjoinings.employeementstatus?.id
                when(idForJoiningAndReleaseDate){
                    1->{
                        binding.fJobJoiningDateOf.tvTitle.text = "Joining Date Of ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningReleaseDateOf.tvTitle.text = "Release Date from ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_effective_date}")
                        binding.fJobJoiningReleaseDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_release_date}")
                    }
                    2->{
                        binding.fJobJoiningDateOf.tvTitle.text = "Joining Date Of ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningReleaseDateOf.tvTitle.text = "Release Date from ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_effective_date}")
                        binding.fJobJoiningReleaseDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_release_date}")
                    }
                    4->{
                        binding.fJobJoiningDateOf.tvTitle.text = "Joining Date Of ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningReleaseDateOf.tvTitle.text = "Release Date from ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_effective_date}")
                        binding.fJobJoiningReleaseDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_release_date}")
                    }
                    5->{
                        binding.fJobJoiningWhereToLien.llBody.visibility = View.GONE
                        binding.fJobJoiningDateOf.tvTitle.text = "Suspension Date"
                        binding.fJobJoiningReleaseDateOf.tvTitle.text = "Released Date from Suspension"
                        binding.fJobJoiningDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_effective_date}")
                        binding.fJobJoiningReleaseDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_release_date}")
                    }
                }

                jobjoinings.pay_scale?.let {
                    binding.fJobJoiningPayScale.tvText.text = it
                }

                jobjoinings.grade?.name.let {
                    binding.fJobJoiningCurrentPayScaleGrade.tvText.text = it
                }

                //OtherServiceParticulars common for Head Office & Divisional Office
                jobjoinings.employeementstatus?.name?.let {
                    binding.fJobJoiningOtherServiceParticulars.tvText.text = it
                }

                //Designation common for Head Office & Divisional Office
                jobjoinings.designation?.name?.let {
                    binding.fJobJoiningDesignation.tvText.text = it
                }

                jobjoinings.joining_date?.let {
                    binding.fJobJoiningJoiningDate.tvText.text = DateConverter.changeDateFormateForShowing(it)
                }

                jobjoinings.employee_class?.name?.let {
                    binding.fJobJoiningClass.tvText.text = it
                }
  //...............................................................//
   //................This else Is for BANGLA part.................//
  //................................................................//
            } else {

                jobjoinings.office_type.let {
                    if (jobjoinings.office_type == "divisional_office"){
                        binding.fJobJoiningOffice.tvText.text = context.getString(R.string.divisional) //"Divisional" //"বিভাগীয়"
                        binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = context.getString(R.string.office_division) //"Division" // "বিভাগ"
                        binding.fJobJoiningDepartmentWiseSection.tvTitle.text = context.getString(R.string.division_district) //District // জেলা
                        binding.fJobJoiningSectionWiseSubSection.tvTitle.text = context.getString(R.string.current_office) //Current Office // বর্তমান অফিস


                        jobjoinings.division?.name_bn?.let {
                            binding.fJobJoiningOfficeDeptAndDivision.tvText.text = it
                        }

                        jobjoinings.district?.name_bn?.let {
                            binding.fJobJoiningDepartmentWiseSection.tvText.text = it
                        }

                        jobjoinings.office?.office_name_bn?.let {
                            binding.fJobJoiningSectionWiseSubSection.tvText.text = it
                        }

                        jobjoinings.employment_status_description?.let {
                            binding.fJobJoiningWhereToLien.tvText.text = it
                        }

                        val employeementstatusId = jobjoinings.employeementstatus?.id
                        when(employeementstatusId){
                            1->{
                                binding.fJobJoiningWhereToLien.tvTitle.text =  "Where to ${jobjoinings.employeementstatus.name}"//context.getString(R.string.where_to_lien)
                            }
                            2->{
                                binding.fJobJoiningWhereToLien.tvTitle.text = "Where to ${jobjoinings.employeementstatus.name}" //context.getString(R.string.where_to_deputed)
                            }
                            4->{
                                binding.fJobJoiningWhereToLien.tvTitle.text = "${jobjoinings.employeementstatus.name} Office"
                                binding.fJobJoiningWhereToLien.tvText.text = "${jobjoinings.additional_office?.office_name_bn}"
                                binding.fJobJoiningAdditionalChargeDesignation.llBody.visibility=View.VISIBLE
                                binding.fJobJoiningAdditionalChargeDesignation.tvTitle.text = "${jobjoinings.employeementstatus.name} Designation"
                                binding.fJobJoiningAdditionalChargeDesignation.tvText.text = "${jobjoinings.additional_designation?.name_bn}"
                            }
                        }
                        /*val divisioinId= jobjoinings.office?.division_id
                        when(divisioinId){  //division_id wise Division setting
                            0->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_barisal)
                            }
                            1->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_chattogram)
                            }
                            2->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_dhaka)
                            }
                            3->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_khulna)
                            }
                            4->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_mymensing)
                            }
                            5->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_rajshahi)
                            }
                            6->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_rangpur)
                            }
                            7->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.division_sylhet)
                            }
                        }*/

                            //this else is for HeadOffice Type
                    }else{
                        binding.fJobJoiningOffice.tvText.text = context.getString(R.string.head_office)//"Head Office" // "সদর দফতর"
                        binding.fJobJoiningOfficeDeptAndDivision.tvTitle.text = context.getString(R.string.head_office_department)//"Head Office Department" //"হেড অফিস বিভাগ"
                        binding.fJobJoiningDepartmentWiseSection.tvTitle.text = context.getString(R.string.department_wise_section) // Department Wise Section //বিভাগীয় বিভাগ
                        binding.fJobJoiningSectionWiseSubSection.tvTitle.text = context.getString(R.string.section_wise_sub_section) //Section Wise Sub Section //সেকশন ওয়াইজ সাব সেকশন

                        jobjoinings.head_office_section?.name_bn?.let {
                            binding.fJobJoiningDepartmentWiseSection.tvText.text = it
                        }

                        jobjoinings.head_office_sub_section?.name_bn?.let {
                            binding.fJobJoiningSectionWiseSubSection.tvText.text = it
                        }

                        jobjoinings.employment_status_description?.let {
                            binding.fJobJoiningWhereToLien.tvText.text = it
                        }

                        jobjoinings.office?.office_name_bn?.let{
                            binding.fJobJoiningOfficeDeptAndDivision.tvText.text = it
                        }

                        val employeementstatusId = jobjoinings.employeementstatus?.id
                        when(employeementstatusId){
                            1->{
                                binding.fJobJoiningWhereToLien.tvTitle.text =  "Where to ${jobjoinings.employeementstatus.name}"//context.getString(R.string.where_to_lien)
                            }
                            2->{
                                binding.fJobJoiningWhereToLien.tvTitle.text = "Where to ${jobjoinings.employeementstatus.name}" //context.getString(R.string.where_to_deputed)
                            }
                            4->{
                                binding.fJobJoiningWhereToLien.tvTitle.text = "${jobjoinings.employeementstatus.name} Office"
                                binding.fJobJoiningWhereToLien.tvText.text = "${jobjoinings.additional_office?.office_name_bn}"
                                binding.fJobJoiningAdditionalChargeDesignation.llBody.visibility=View.VISIBLE
                                binding.fJobJoiningAdditionalChargeDesignation.tvTitle.text = "${jobjoinings.employeementstatus.name} Designation"
                                binding.fJobJoiningAdditionalChargeDesignation.tvText.text = "${jobjoinings.additional_designation?.name_bn}"
                            }
                        }

                        if (jobjoinings.office?.office_name_bn == "ডিজি অফিস" || jobjoinings.office?.office_name_bn == "সমাজ সেবা ভবন"){
                            binding.fJobJoiningDepartmentWiseSection.llBody.visibility=View.GONE
                            binding.fJobJoiningSectionWiseSubSection.llBody.visibility=View.GONE
                        }

                        if (jobjoinings.office?.office_name == "প্রশাসন ও অর্থ অধিশাখা" || jobjoinings.office?.office_name == "জাতীয় অফিস অধিশাখা"){
                            binding.fJobJoiningSectionWiseSubSection.llBody.visibility=View.GONE
                        }else if ((jobjoinings.office?.office_name == "প্রশাসন ও অর্থ অধিশাখা" && jobjoinings.head_office_section?.name == "পরিকল্পনা উন্নয়ন গবেষণা এবং মূল্যায়ন") || (jobjoinings.office?.office_name == "জাতীয় অফিস অধিশাখা" && jobjoinings.head_office_section?.name == "শারীরিক হ্যান্ডিক্যাপেপডের জন্য ব্রালে প্রেস আর্টিফিশিয়া লিম্বস প্রোডাকশন সেন্টার")){
                            binding.fJobJoiningSectionWiseSubSection.llBody.visibility=View.VISIBLE
                        }


                       /* val headOfficeDepartmentId= jobjoinings.office?.head_office_department_id
                        when(headOfficeDepartmentId){   //headOfficeDepartmentId wise Department setting
                            0->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_dg_office)
                            }
                            1->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_administration_finance)
                            }
                            2->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_communiutey_service)
                            }
                            3->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_institute)
                            }
                            4->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_social_safety_net)
                            }
                            5->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_national_institution)
                            }
                            6->{
                                binding.fJobJoiningOfficeDeptAndDivision.tvText.text = context.getString(R.string.dept_somaj_seba_bhaban)
                            }

                        }*/
                    }

                }

                // Out of if else , here is the Data Common for HeadOffice & DivisionalOffice
                val idForJoiningAndReleaseDate = jobjoinings.employeementstatus?.id
                when(idForJoiningAndReleaseDate){
                    1->{
                        binding.fJobJoiningDateOf.tvTitle.text = "Joining Date Of ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningReleaseDateOf.tvTitle.text = "Release Date from ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_effective_date}")
                        binding.fJobJoiningReleaseDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_release_date}")
                    }
                    2->{
                        binding.fJobJoiningDateOf.tvTitle.text = "Joining Date Of ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningReleaseDateOf.tvTitle.text = "Release Date from ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_effective_date}")
                        binding.fJobJoiningReleaseDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_release_date}")
                    }
                    4->{
                        binding.fJobJoiningDateOf.tvTitle.text = "Joining Date Of ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningReleaseDateOf.tvTitle.text = "Release Date from ${jobjoinings.employeementstatus.name}"
                        binding.fJobJoiningDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_effective_date}")
                        binding.fJobJoiningReleaseDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_release_date}")
                    }
                    5->{
                        binding.fJobJoiningWhereToLien.llBody.visibility = View.GONE
                        binding.fJobJoiningDateOf.tvTitle.text = "Suspension Date"
                        binding.fJobJoiningReleaseDateOf.tvTitle.text = "Released Date from Suspension"
                        binding.fJobJoiningDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_effective_date}")
                        binding.fJobJoiningReleaseDateOf.tvText.text = DateConverter.changeDateFormateForShowing("${jobjoinings.employment_status_release_date}")
                    }
                }

                jobjoinings.pay_scale?.let {
                    binding.fJobJoiningPayScale.tvText.text = it
                }

                jobjoinings.grade?.name.let {
                    binding.fJobJoiningCurrentPayScaleGrade.tvText.text = it
                }

                jobjoinings.employeementstatus?.name_bn?.let {
                    binding.fJobJoiningOtherServiceParticulars.tvText.text = it
                }

                jobjoinings.designation?.name_bn?.let {
                    binding.fJobJoiningDesignation.tvText.text = it
                }

                jobjoinings.joining_date?.let {
                    binding.fJobJoiningJoiningDate.tvText.text = DateConverter.changeDateFormateForShowing(it)
                }

                jobjoinings.employee_class?.name_bn?.let {
                    binding.fJobJoiningClass.tvText.text = it
                }

            }

        } else {

            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)

            binding.hJobJoiningInformation.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"

            binding.fJobJoiningOffice.tvText.text = HelperClass.getCommonDataFilltered(
                jobjoinings.office_id,
                commonData?.offices,
                false
            )

            binding.fJobJoiningDesignation.tvText.text = HelperClass.getCommonDataFilltered(
                jobjoinings.designation_id,
                commonData?.designations,
                false
            )


          /*  binding.fJobJoiningAdditionalDesignation.tvText.text = HelperClass.getCommonDataFilltered(
                jobjoinings.additional_designation_id,
                commonData?.designations,
                false
            )*/

            //  jobjoinings.department?.name?.let { binding.fJobJoiningDepartment.tvText.setText(it) }
          /*  binding.fJobJoiningJobType.tvText.text = HelperClass.getCommonDataFilltered(
                jobjoinings.job_type_id,
                commonData?.job_types,
                false
            )*/

            jobjoinings.joining_date?.let {
                binding.fJobJoiningJoiningDate.tvText.text = DateConverter.changeDateFormateForShowing(it)
            }

            binding.fJobJoiningClass.tvText.text = HelperClass.getCommonDataFilltered(
                jobjoinings.employee_class_id, commonData?.employee_classes, false
            )

        }


    }

    fun bindChildrenData(
        binding: ModelEmployeeInfoBinding,
        childs: Employee.Childs,
        context: Context,
        heading: String
    ) {
        binding.hChildren.tvTitle.text = heading
        binding.fChildrenNameOChEn.tvTitle.text = context.getString(R.string.name_child)
        binding.fChildrenNameOChBn.tvTitle.text = context.getString(R.string.name_child_bn)
        binding.fChildrenDOB.tvTitle.text = context.getString(R.string.birth)
        binding.fChildrenBirthCertificateNo.tvTitle.text = context.getString(R.string.birth_certificate_no)
        binding.fChildrenNidNo.tvTitle.text = context.getString(R.string.nid_no)
        binding.fChildrenPassportNo.tvTitle.text = context.getString(R.string.passport_no)
        binding.fChildrenGenderOrSex.tvTitle.text = context.getString(R.string.gender)
        HelperClass.addHeaderColor(binding.hChildren, context, childs.isPendingData)

        binding.fChildNid.tvTitle.text = context.getString(R.string.nid_attachment)
        binding.fChildPassport.tvTitle.text = context.getString(R.string.passport_attachment)
        binding.fChildbirthCertificate.tvTitle.text = context.getString(R.string.birth_certificate_attachment)

        ConvertNumber.setIconOnTextView(
            binding.fChildbirthCertificate.icon,
            binding.fChildbirthCertificate.tvText,
            childs.birth_certificate_document_path,
            context
        )
        ConvertNumber.setIconOnTextView(
            binding.fChildNid.icon,
            binding.fChildNid.tvText,
            childs.nid_document_path,
            context
        )

        ConvertNumber.setIconOnTextView(
            binding.fChildPassport.icon,
            binding.fChildPassport.tvText,
            childs.passport_document_path,
            context
        )

        binding.fChildbirthCertificate.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(context, childs.birth_certificate_document_path)
        }
        binding.fChildPassport.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(context, childs.passport_document_path)
        }
        binding.fChildNid.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(context, childs.nid_document_path)
        }

        if (!childs.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                childs.name_of_children?.let { binding.fChildrenNameOChEn.tvText.text = it }
                childs.name_of_children_bn?.let { binding.fChildrenNameOChBn.tvText.text = it }
                childs.date_of_birth?.let { binding.fChildrenDOB.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                childs.gender?.name?.let { binding.fChildrenGenderOrSex.tvText.text = it }
                childs.birth_certificate?.let { binding.fChildrenBirthCertificateNo.tvText.text = it }
                childs.nid?.let { binding.fChildrenNidNo.tvText.text = it }
                childs.passport?.let { binding.fChildrenPassportNo.tvText.text = it }

            } else {
                childs.name_of_children?.let { binding.fChildrenNameOChEn.tvText.text = it }
                childs.name_of_children_bn?.let { binding.fChildrenNameOChBn.tvText.text = it }
                childs.date_of_birth?.let { binding.fChildrenDOB.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                childs.gender?.name_bn?.let { binding.fChildrenGenderOrSex.tvText.text = it }
                childs.birth_certificate?.let { binding.fChildrenBirthCertificateNo.tvText.text = it }
                childs.nid?.let { binding.fChildrenNidNo.tvText.text = it }
                childs.passport?.let { binding.fChildrenPassportNo.tvText.text = it }
            }
        } else {
            binding.hChildren.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            childs.name_of_children?.let { binding.fChildrenNameOChEn.tvText.text = it }
            childs.name_of_children_bn?.let { binding.fChildrenNameOChBn.tvText.text = it }
            childs.date_of_birth?.let { binding.fChildrenDOB.tvText.text = DateConverter.changeDateFormateForShowing(it) }

            val obj: CommonData? = preparence.get(HelperClass.COMMON_DATA)

            binding.fChildrenGenderOrSex.tvText.text = "${
                childs.gender_id.let {
                    obj?.genders?.let { it1 ->
                        HelperClass.getCommonDataFilltered(it, it1, false)
                    }
                }
            }"
            childs.birth_certificate?.let { binding.fChildrenBirthCertificateNo.tvText.text = it }
            childs.nid?.let { binding.fChildrenNidNo.tvText.text = it }
            childs.passport?.let { binding.fChildrenPassportNo.tvText.text = it }

        }


    }


    fun bindLanguageData(
        binding: ModelEmployeeInfoBinding,
        languages: Employee.Languages,
        context: Context,
        heading: String
    ) {
        binding.llLanguageCertificate.visibility = View.GONE
        binding.hLanguage.tvTitle.text = heading

        HelperClass.addHeaderColor(
            binding.hLanguage,
            context,
            languages.isPendingData
        )

        binding.fLanguageNOLanguage.tvTitle.text = context.getString(R.string.name_language)
        binding.fLanguageNOLanguageBn.tvTitle.text = context.getString(R.string.name_language_bn)
        binding.fLanguageNOInstituteBn.tvTitle.text = context.getString(R.string.name_institute_bn)
        binding.fLanguageNOInstitute.tvTitle.text = context.getString(R.string.name_institute)
        binding.fLanguageExperienceLevel.tvTitle.text = context.getString(R.string.exp_level)
        binding.fLanguageCertificationDate.tvTitle.text = context.getString(R.string.certification_date)

        binding.tvLanguageCertificate.text = context.getString(R.string.certificate)


        ConvertNumber.setIconOnTextView(
            binding.fLanguageCertificationAttachment.icon,
            binding.fLanguageCertificationAttachment.tvText,
            languages.certificate_document_path,
            context
        )


        binding.fLanguageCertificationAttachment.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(
                context,
                languages.certificate_document_path
            )
        }

        context.let {
            binding.ivLanguageCertificate.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions().placeholder(R.drawable.ic_baseline_image_24))
                    .load(languages.certificate)
                    .into(it1)
            }
        }
        if (!languages.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                languages.name_of_language?.let { binding.fLanguageNOLanguage.tvText.text = it }
                languages.name_of_language_bn?.let { binding.fLanguageNOLanguageBn.tvText.text = it }
                languages.name_of_institute?.let { binding.fLanguageNOInstitute.tvText.text = it }
                languages.name_of_institute_bn?.let { binding.fLanguageNOInstituteBn.tvText.text = it }
                languages.expertise_level?.let { binding.fLanguageExperienceLevel.tvText.text = it }
                languages.certification_date?.let { binding.fLanguageCertificationDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }

            } else {
                languages.name_of_language?.let { binding.fLanguageNOLanguage.tvText.text = it }
                languages.name_of_language_bn?.let { binding.fLanguageNOLanguageBn.tvText.text = it }
                languages.name_of_institute?.let { binding.fLanguageNOInstitute.tvText.text = it }
                languages.name_of_institute_bn?.let { binding.fLanguageNOInstituteBn.tvText.text = it }
                languages.expertise_level?.let {
                    when (it.lowercase(Locale.getDefault())) {
                        "medium" -> binding.fLanguageExperienceLevel.tvText.text = "মধ্যম"
                        "expert" -> binding.fLanguageExperienceLevel.tvText.text = "বিশেষজ্ঞ"
                        "average" -> binding.fLanguageExperienceLevel.tvText.text = "গড়"
                        else -> binding.fLanguageExperienceLevel.tvText.text = "কম"
                    }
                }
                languages.certification_date?.let { binding.fLanguageCertificationDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
            }
        } else {

            binding.hLanguage.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"

            languages.name_of_language?.let { binding.fLanguageNOLanguage.tvText.text = it }
            languages.name_of_language_bn?.let { binding.fLanguageNOLanguageBn.tvText.text = it }
            languages.name_of_institute?.let { binding.fLanguageNOInstitute.tvText.text = it }
            languages.name_of_institute_bn?.let { binding.fLanguageNOInstituteBn.tvText.text = it }
            languages.expertise_level?.let { binding.fLanguageExperienceLevel.tvText.text = it }
            languages.certification_date?.let { binding.fLanguageCertificationDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }

        }


    }


    fun bindLocaltrainingData(
        binding: ModelEmployeeInfoBinding,
        localTrainings: Employee.LocalTrainings,
        context: Context,
        heading: String
    ) {
        binding.hLocaltraining.tvTitle.text = heading
        binding.fLocalTrainingCountry.llBody.visibility = View.GONE
        binding.llTrainingCertification.visibility = View.GONE
        HelperClass.addHeaderColor(
            binding.hLocaltraining,
            context,
            localTrainings.isPendingData
        )

        binding.fLocalTrainingCatName.tvTitle.text = context.getString(R.string.training_category)
        binding.fLocalTrainingCourseT.tvTitle.text = context.getString(R.string.course_title)
        binding.fLocalTrainingCourseTBn.tvTitle.text = context.getString(R.string.course_title_bn)
        binding.fLocalTrainingNOInst.tvTitle.text = context.getString(R.string.name_institute)
        binding.fLocalTrainingNOInstBn.tvTitle.text = context.getString(R.string.name_institute_bn)
        binding.fLocalTrainingLocation.tvTitle.text = context.getString(R.string.location)
        binding.fLocalTrainingLocationBn.tvTitle.text = context.getString(R.string.location_bn)
        binding.fLocalTrainingFromDate.tvTitle.text = context.getString(R.string.from_date)
        binding.fLocalTrainingToDate.tvTitle.text = context.getString(R.string.to_date)

        // decide what to show in document
        ConvertNumber.setIconOnTextView(
            binding.fLocalTrainingDocument.icon,
            binding.fLocalTrainingDocument.tvText,
            localTrainings.local_training_document_path,
            context
        )

        binding.fLocalTrainingCatName.tvText.text = "${localTrainings.hrm_training_category?.name}"

//       val list =  commonRepo.getAllHrTraining()
//
//        if(list.isEmpty()){
//
//        }else {
//            for(item in list){
//                Log.d(|, "bindLocaltrainingData: ")
//                if(item.id == localTrainings?.hrm_training_category_id ){
//                    binding.fLocalTrainingCatName.tvText.text =  "${ item.name}"
//                    break ;
//                }
//            }
//        }

        binding.fLocalTrainingDocument.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(context,
                localTrainings.local_training_document_path
            )
        }

        binding.tvTrainingTitle.text = context.getString(R.string.certificate)

        context.let {
            binding.ivTraining.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions().placeholder(R.drawable.ic_baseline_image_24))
                    .load(localTrainings.certificate)
                    .into(it1)
            }
        }


        if (!localTrainings.isPendingData) {

            if (preparence.getLanguage().equals("en"))
            {
                localTrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.text = it }
                localTrainings.course_title_bn?.let { binding.fLocalTrainingCourseTBn.tvText.text = it }
                localTrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.text = it }
                localTrainings.name_of_institute_bn?.let { binding.fLocalTrainingNOInstBn.tvText.text = it }
                localTrainings.location?.let { binding.fLocalTrainingLocation.tvText.text = it }
                localTrainings.location_bn?.let { binding.fLocalTrainingLocationBn.tvText.text = it }

                localTrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                localTrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }


            } else {
                localTrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.text = it }
                localTrainings.course_title_bn?.let { binding.fLocalTrainingCourseTBn.tvText.text = it }
                localTrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.text = it }
                localTrainings.name_of_institute_bn?.let { binding.fLocalTrainingNOInstBn.tvText.text = it }
                localTrainings.location?.let { binding.fLocalTrainingLocation.tvText.text = it }
                localTrainings.location_bn?.let { binding.fLocalTrainingLocationBn.tvText.text = it }
                localTrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                localTrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }

            }

        } else {
            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)

            binding.hLocaltraining.tvTitle.text = heading + " (" + context.getString(R.string.pending_data) + ")"
            //binding.hLocaltraining.tvEdit.visibility = View.GONE
            binding.fLocalTrainingCatName.tvText.text = HelperClass.getCommonDataFilltered(localTrainings.hrm_training_category_id,
                commonData?.training_categories, false
            )

            localTrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.text = it }
            localTrainings.course_title_bn?.let { binding.fLocalTrainingCourseTBn.tvText.text = it }
            localTrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.text = it }
            localTrainings.name_of_institute_bn?.let { binding.fLocalTrainingNOInstBn.tvText.text = it }
            localTrainings.location?.let { binding.fLocalTrainingLocation.tvText.text = it }
            localTrainings.location_bn?.let { binding.fLocalTrainingLocationBn.tvText.text = it }
            localTrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
            localTrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
        }


    }

    fun bindForeignTrainingData(
        binding: ModelEmployeeInfoBinding,
        foreigntrainings: Employee.Foreigntrainings,
        context: Context,
        heading: String
    ) {
        binding.hLocaltraining.tvTitle.text = heading
        binding.fLocalTrainingLocation.llBody.visibility = View.GONE
        binding.fLocalTrainingLocationBn.llBody.visibility = View.GONE
        binding.llTrainingCertification.visibility = View.GONE

        HelperClass.addHeaderColor(
            binding.hLocaltraining,
            context,
            foreigntrainings.isPendingData
        )

        binding.fLocalTrainingCatName.tvTitle.text = context.getString(R.string.training_category)
        binding.fLocalTrainingCourseT.tvTitle.text = context.getString(R.string.course_title)
        binding.fLocalTrainingCourseTBn.tvTitle.text = context.getString(R.string.course_title_bn)
        binding.fLocalTrainingNOInst.tvTitle.text = context.getString(R.string.name_institute)
        binding.fLocalTrainingNOInstBn.tvTitle.text = context.getString(R.string.name_institute_bn)
        binding.fLocalTrainingFromDate.tvTitle.text = context.getString(R.string.from_date)
        binding.fLocalTrainingToDate.tvTitle.text = context.getString(R.string.to_date)
        binding.fLocalTrainingCountry.tvTitle.text = context.getString(R.string.country)
        binding.tvTrainingTitle.text = context.getString(R.string.certificate)

        binding.fLocalTrainingCatName.tvText.text = "${foreigntrainings.hrm_training_category?.name}"


        // decide what to show in document
        ConvertNumber.setIconOnTextView(
            binding.fLocalTrainingDocument.icon,
            binding.fLocalTrainingDocument.tvText,
            foreigntrainings.foreign_training_document_path,
            context
        )

        binding.fLocalTrainingDocument.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(
                context,
                foreigntrainings.foreign_training_document_path
            )
        }

        context.let {
            binding.ivTraining.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions().placeholder(R.drawable.ic_baseline_image_24))
                    .load(foreigntrainings.certificate)
                    .into(it1)
            }
        }

        if (!foreigntrainings.isPendingData) {

            if (preparence.getLanguage().equals("en"))
            {
                foreigntrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.text = it }
                foreigntrainings.course_title_bn?.let { binding.fLocalTrainingCourseTBn.tvText.text = it }
                foreigntrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.text = it }
                foreigntrainings.name_of_institute_bn?.let { binding.fLocalTrainingNOInstBn.tvText.text = it }
                foreigntrainings.country?.name?.let { binding.fLocalTrainingCountry.tvText.text = it }

                foreigntrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                foreigntrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }


            } else {

                foreigntrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.text = it }
                foreigntrainings.course_title_bn?.let { binding.fLocalTrainingCourseTBn.tvText.text = it }
                foreigntrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.text = it }
                foreigntrainings.name_of_institute_bn?.let { binding.fLocalTrainingNOInstBn.tvText.text = it }
                foreigntrainings.country?.name_bn?.let { binding.fLocalTrainingCountry.tvText.text = it }

                foreigntrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                foreigntrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
            }


        } else {

            val dataobject: CommonData? = preparence.get(HelperClass.COMMON_DATA)
            if (dataobject != null) {

                binding.fLocalTrainingCatName.tvText.text =
                    HelperClass.getCommonDataFilltered(
                        foreigntrainings.hrm_training_category_id,
                        dataobject.training_categories,
                        false
                    )

                binding.fLocalTrainingCountry.tvText.text = HelperClass.getCommonDataFilltered(
                    foreigntrainings.country_id,
                    dataobject.countries,
                    false
                )

            }
            binding.hLocaltraining.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            // binding.hLocaltraining.tvEdit.visibility = View.GONE
            foreigntrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.text = it }
            foreigntrainings.course_title_bn?.let { binding.fLocalTrainingCourseTBn.tvText.text = it }
            foreigntrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.text = it }
            foreigntrainings.name_of_institute_bn?.let { binding.fLocalTrainingNOInstBn.tvText.text = it }

            foreigntrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
            foreigntrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
            
        }

    }


    fun bindOfficialResidentialInfoData(
        binding: ModelEmployeeInfoBinding,
        officialResidentials: Employee.OfficialResidentials,
        context: Context,
        heading: String
    ) {
        binding.fORInfoDesignation.llBody.visibility = View.GONE
        binding.hOfficialResidentialInfo.tvTitle.text = heading
        binding.fORInfoMemoNo.tvTitle.text = context.getString(R.string.memo_no)
        binding.fORInfoMemoNoBn.tvTitle.text = context.getString(R.string.memo_no_bn)
        binding.fORInfoMemoDate.tvTitle.text = context.getString(R.string.memo_date)
        binding.fORInfoOfficeZone.tvTitle.text = context.getString(R.string.office_zoon)
        binding.fORInfoDesignation.tvTitle.text = context.getString(R.string.designation)
        binding.fORInfoDivision.tvTitle.text = context.getString(R.string.division)
        binding.fORInfoDistrict.tvTitle.text = context.getString(R.string.district)
        binding.fORInfoUpazila.tvTitle.text = context.getString(R.string.upazila)
        binding.fORInfoOfficeZone.tvTitle.text = context.getString(R.string.office_zoon)
        binding.fORInfoLocation.tvTitle.text = context.getString(R.string.address)
        binding.fORInfoQuarterName.tvTitle.text = context.getString(R.string.quarter_name)
        binding.fORInfoFlatNo.tvTitle.text = context.getString(R.string.flat_no)
        binding.fORInfoStatus.tvTitle.text = context.getString(R.string.status)

        HelperClass.addHeaderColor(
            binding.hOfficialResidentialInfo, context,
            officialResidentials.isPendingData
        )


        if (!officialResidentials.isPendingData) {

            if (preparence.getLanguage().equals("en"))
            {
                officialResidentials.memo_no?.let { binding.fORInfoMemoNo.tvText.text = it }
                officialResidentials.memo_no_bn?.let { binding.fORInfoMemoNoBn.tvText.text = it }
                officialResidentials.memo_date?.let { binding.fORInfoMemoDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                officialResidentials.office_zone?.let { binding.fORInfoOfficeZone.tvText.text = it }
                officialResidentials.designation?.name?.let { binding.fORInfoDesignation.tvText.text = it }
                officialResidentials.division?.name?.let { binding.fORInfoDivision.tvText.text = it }
                officialResidentials.district?.name?.let { binding.fORInfoDistrict.tvText.text = it }
                officialResidentials.upazila?.name?.let { binding.fORInfoUpazila.tvText.text = it }
                officialResidentials.location?.let { binding.fORInfoLocation.tvText.text = it }
                officialResidentials.quarter_name?.let { binding.fORInfoQuarterName.tvText.text = it }
                officialResidentials.flat_no_flat_type?.let { binding.fORInfoFlatNo.tvText.text = it }

                officialResidentials.status.let {
                    if (it == 1) {
                        binding.fORInfoStatus.tvText.text =
                            EditOfficialResidentialIInfo().getStatusList()[0].name
                    } else {
                        binding.fORInfoStatus.tvText.text =
                            EditOfficialResidentialIInfo().getStatusList()[1].name
                    }

                }


            } else {
                officialResidentials.memo_no?.let { binding.fORInfoMemoNo.tvText.text = it }
                officialResidentials.memo_no_bn?.let { binding.fORInfoMemoNoBn.tvText.text = it }
                officialResidentials.memo_date?.let { binding.fORInfoMemoDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                officialResidentials.office_zone?.let { binding.fORInfoOfficeZone.tvText.text = it }
                officialResidentials.designation?.name_bn?.let { binding.fORInfoDesignation.tvText.text = it }
                officialResidentials.division?.name_bn?.let { binding.fORInfoDivision.tvText.text = it }
                officialResidentials.district?.name_bn?.let { binding.fORInfoDistrict.tvText.text = it }
                officialResidentials.upazila?.name_bn?.let { binding.fORInfoUpazila.tvText.text = it }
                officialResidentials.location?.let { binding.fORInfoLocation.tvText.text = it }
                officialResidentials.quarter_name?.let { binding.fORInfoQuarterName.tvText.text = it }
                officialResidentials.flat_no_flat_type?.let { binding.fORInfoFlatNo.tvText.text = it }

                officialResidentials.status.let {
                    if (it == 1) {
                        binding.fORInfoStatus.tvText.text = EditOfficialResidentialIInfo().getStatusList()[0].name_bn
                    } else {
                        binding.fORInfoStatus.tvText.text = EditOfficialResidentialIInfo().getStatusList()[1].name_bn
                    }

                }

            }

        } else {
            binding.hOfficialResidentialInfo.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            //  binding.hOfficialResidentialInfo.tvEdit.visibility = View.GONE
            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)

            officialResidentials.memo_no?.let { binding.fORInfoMemoNo.tvText.text = it }
            officialResidentials.memo_no_bn?.let { binding.fORInfoMemoNoBn.tvText.text = it }
            officialResidentials.memo_date?.let { binding.fORInfoMemoDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
            officialResidentials.office_zone?.let { binding.fORInfoOfficeZone.tvText.text = it }

            binding.fORInfoDesignation.tvText.text = HelperClass.getCommonDataFilltered(
                officialResidentials.designation_id,
                commonData?.designations,
                false
            )

            binding.fORInfoDivision.tvText.text = officialResidentials.division_id?.let {
                HelperClass.getCommonDataFilltered(
                    it, commonData?.divisions,
                    false
                )
            }
            binding.fORInfoDistrict.tvText.text = officialResidentials.district_id?.let {
                HelperClass.getCommonDataFilltered(
                    it, commonData?.districts,
                    false
                )
            }
            binding.fORInfoUpazila.tvText.text = officialResidentials.upazila_id?.let {
                HelperClass.getCommonDataFilltered(
                    it, commonData?.upazilas,
                    false
                )
            }
            officialResidentials.location?.let { binding.fORInfoLocation.tvText.text = it }
            officialResidentials.quarter_name?.let { binding.fORInfoQuarterName.tvText.text = it }
            officialResidentials.flat_no_flat_type?.let { binding.fORInfoFlatNo.tvText.text = it }

            officialResidentials.status.let {
                if (it == 1) {
                    binding.fORInfoStatus.tvText.text = EditOfficialResidentialIInfo().getStatusList()[0].name
                } else {
                    binding.fORInfoStatus.tvText.text = EditOfficialResidentialIInfo().getStatusList()[1].name
                }

            }
        }

    }

    fun bindForeignTravelInfoData(
        binding: ModelEmployeeInfoBinding,
        foreignTravels: Employee.ForeignTravels,
        context: Context,
        heading: String
    ) {
        binding.fForeignTravelPurposeBn.llBody.visibility = View.GONE
        binding.hForeignTravelInfo.tvTitle.text = heading
        binding.fForeignTravelCountry.tvTitle.text = context.getString(R.string.country)
        binding.fForeignTravelPurpose.tvTitle.text = context.getString(R.string.purpose)
        binding.fForeignTravelPurposeBn.tvTitle.text = context.getString(R.string.purpose_bangla)
        binding.fForeignTravelDetailsEn.tvTitle.text = context.getString(R.string.details_en)
        binding.fForeignTravelDetailsBn.tvTitle.text = context.getString(R.string.details_bn)
        binding.fForeignTravelFromDate.tvTitle.text = context.getString(R.string.from_date)
        binding.fForeignTravelToDate.tvTitle.text = context.getString(R.string.to_date)

        HelperClass.addHeaderColor(
            binding.hForeignTravelInfo,
            context,
            foreignTravels.isPendingData
        )

        // decide what to show in document
        ConvertNumber.setIconOnTextView(
            binding.fForeignAttachment.icon,
            binding.fForeignAttachment.tvText,
            foreignTravels.document_path,
            context
        )

        binding.fForeignAttachment.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(
                context,
                foreignTravels.document_path
            )
        }


        if (!foreignTravels.isPendingData) {

            if (preparence.getLanguage().equals("en"))
            {
                foreignTravels.country?.name?.let { binding.fForeignTravelCountry.tvText.text = it }
                foreignTravels.purpose?.let { binding.fForeignTravelPurpose.tvText.text = it }
                foreignTravels.purpose_bn?.let { binding.fForeignTravelPurposeBn.tvText.text = it }
                foreignTravels.details?.let { binding.fForeignTravelDetailsEn.tvText.text = it }
                foreignTravels.details_bn?.let { binding.fForeignTravelDetailsBn.tvText.text = it }
                foreignTravels.from_date?.let {
                    binding.fForeignTravelFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it)
                }
                foreignTravels.to_date?.let {
                    binding.fForeignTravelToDate.tvText.text = DateConverter.changeDateFormateForShowing(it)
                }


            } else {
                foreignTravels.country?.name_bn?.let { binding.fForeignTravelCountry.tvText.text = it }
                foreignTravels.purpose_bn?.let { binding.fForeignTravelPurpose.tvText.text = it }
                //  foreignTravels.purpose_bn?.let { binding.fForeignTravelPurposeBn.tvText.setText(it) }
                foreignTravels.details?.let { binding.fForeignTravelDetailsEn.tvText.text = it }
                foreignTravels.details_bn?.let { binding.fForeignTravelDetailsBn.tvText.text = it }

                foreignTravels.from_date?.let { binding.fForeignTravelFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
                foreignTravels.to_date?.let { binding.fForeignTravelToDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }

            }
        } else {
            binding.hForeignTravelInfo.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            // binding.hForeignTravelInfo.tvEdit.visibility = View.GONE
            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)

            binding.fForeignTravelCountry.tvText.text = HelperClass.getCommonDataFilltered(
                foreignTravels.country_id,
                commonData?.countries,
                false
            )
            foreignTravels.purpose?.let { binding.fForeignTravelPurpose.tvText.text = it }
            foreignTravels.purpose_bn?.let { binding.fForeignTravelPurposeBn.tvText.text = it }
            foreignTravels.details?.let { binding.fForeignTravelDetailsEn.tvText.text = it }
            foreignTravels.details_bn?.let { binding.fForeignTravelDetailsBn.tvText.text = it }

            foreignTravels.from_date?.let { binding.fForeignTravelFromDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
            foreignTravels.to_date?.let { binding.fForeignTravelToDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
        }

    }

    fun bindAdditionalQualificationData(
        binding: ModelEmployeeInfoBinding,
        additionalQualifications: Employee.AdditionalQualifications,
        context: Context,
        heading: String
    ) {
        binding.hAdditionalQualification.tvTitle.text = heading
        binding.fAQName.tvTitle.text = context.getString(R.string.qualification_name)
        binding.fAQNameBn.tvTitle.text = context.getString(R.string.qualification_name_bangla)
        binding.fAQDetails.tvTitle.text = context.getString(R.string.qualification_details)
        binding.fAQDetailsBn.tvTitle.text = context.getString(R.string.qualification_details_bangla)

        HelperClass.addHeaderColor(
            binding.hAdditionalQualification,
            context,
            additionalQualifications.isPendingData
        )

        ConvertNumber.setIconOnTextView(
            binding.fAQDocument.icon,
            binding.fAQDocument.tvText,
            additionalQualifications.additional_professional_qualification_document_path,
            context
        )

        binding.fAQDocument.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(
                context,
                additionalQualifications.additional_professional_qualification_document_path
            )

        }

        if (!additionalQualifications.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                additionalQualifications.qualification_name?.let { binding.fAQName.tvText.text = it }
                additionalQualifications.qualification_name_bn?.let { binding.fAQNameBn.tvText.text = it }
                additionalQualifications.qualification_details?.let { binding.fAQDetails.tvText.text = it }
                additionalQualifications.qualification_details_bn?.let { binding.fAQDetailsBn.tvText.text = it }

            } else {

                additionalQualifications.qualification_name?.let { binding.fAQName.tvText.text = it }
                additionalQualifications.qualification_name_bn?.let { binding.fAQNameBn.tvText.text = it }
                additionalQualifications.qualification_details?.let { binding.fAQDetails.tvText.text = it }
                additionalQualifications.qualification_details_bn?.let { binding.fAQDetailsBn.tvText.text = it }
            }
        } else {

            binding.hAdditionalQualification.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            //binding.hAdditionalQualification.tvEdit.visibility = View.GONE
            additionalQualifications.qualification_name?.let { binding.fAQName.tvText.text = it }
            additionalQualifications.qualification_name_bn?.let { binding.fAQNameBn.tvText.text = it }
            additionalQualifications.qualification_details?.let { binding.fAQDetails.tvText.text = it }
            additionalQualifications.qualification_details_bn?.let { binding.fAQDetailsBn.tvText.text = it }
        }
    }

    fun bindPublicationsData(
        binding: ModelEmployeeInfoBinding,
        qualifications: Employee.Publications,
        context: Context,
        heading: String
    ) {
        binding.hPublication.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
        binding.fPublicationType.tvTitle.text = context.getString(R.string.type_of_pub)
        binding.fPublicationDetails.tvTitle.text = context.getString(R.string.public_details)
        binding.fPublicationDetailsBn.tvTitle.text = context.getString(R.string.public_details_bn)
        binding.fPublicationNameEn.tvTitle.text = context.getString(R.string.pub_name)
        binding.fPublicationNameBn.tvTitle.text = context.getString(R.string.pub_name_bn)

        HelperClass.addHeaderColor(
            binding.hPublication,
            context,
            qualifications.isPendingData
        )


        ConvertNumber.setIconOnTextView(
            binding.fPublicationAttachment.icon,
            binding.fPublicationAttachment.tvText,
            qualifications.document_path,
            context
        )

        binding.fPublicationAttachment.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(
                context,
                qualifications.document_path
            )

        }

        if (!qualifications.isPendingData) {
            binding.hPublication.tvTitle.text = heading

            if (preparence.getLanguage().equals("en"))
            {
                qualifications.type?.name?.let { binding.fPublicationType.tvText.text = it }
                qualifications.publication_details?.let { binding.fPublicationDetails.tvText.text = it }
                qualifications.publication_details_bn?.let { binding.fPublicationDetailsBn.tvText.text = it }
                qualifications.publication_name?.let { binding.fPublicationNameEn.tvText.text = it }
                qualifications.publication_name_bn?.let { binding.fPublicationNameBn.tvText.text = it }

            } else {

                qualifications.publication_details_bn?.let { binding.fPublicationDetailsBn.tvText.text = it }
                qualifications.type?.name_bn?.let { binding.fPublicationType.tvText.text = it }
                qualifications.publication_details_bn?.let { binding.fPublicationDetails.tvText.text = it }
                qualifications.publication_name?.let { binding.fPublicationNameEn.tvText.text = it }
                qualifications.publication_name_bn?.let { binding.fPublicationNameBn.tvText.text = it }
            }

        } else {

            binding.hPublication.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"

            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)

            binding.hPublication.tvEdit.visibility = View.GONE
            binding.fPublicationType.tvText.text = qualifications.publication_type?.let {
                HelperClass.getCommonDataFilltered(
                    it, commonData?.publication_types,
                    false
                )
            }

            qualifications.publication_details?.let { binding.fPublicationDetails.tvText.text = it }
            qualifications.publication_details_bn?.let { binding.fPublicationDetailsBn.tvText.text = it }
            qualifications.publication_name?.let { binding.fPublicationNameEn.tvText.text = it }
            qualifications.publication_name_bn?.let { binding.fPublicationNameBn.tvText.text = it }
        }


    }

    fun bindHonoursAndAwardData(
        binding: ModelEmployeeInfoBinding,
        honoursAwards: Employee.HonoursAwards,
        context: Context,
        heading: String
    ) {
        binding.hHonoursAndAward.tvTitle.text = heading
        binding.fAwardTitle.tvTitle.text = context.getString(R.string.aware_t)
        binding.fAwardTitleBn.tvTitle.text = context.getString(R.string.aware_t_bn)
        binding.fAwardDetailsDetails.tvTitle.text = context.getString(R.string.award_details)
        binding.fAwardDetailsDetailsBn.tvTitle.text = context.getString(R.string.award_details_bn)
        binding.fAwardDate.tvTitle.text = context.getString(R.string.award_date)

        HelperClass.addHeaderColor(
            binding.hHonoursAndAward,
            context, honoursAwards.isPendingData
        )


        ConvertNumber.setIconOnTextView(
            binding.fAwardAttachment.icon,
            binding.fAwardAttachment.tvText,
            honoursAwards.honours_awards_document_path,
            context
        )

        binding.fAwardAttachment.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(
                context, honoursAwards.honours_awards_document_path
            )

        }

        if (!honoursAwards.isPendingData) {

            if (preparence.getLanguage().equals("en"))
            {
                honoursAwards.award_title?.let { binding.fAwardTitle.tvText.text = it }
                honoursAwards.award_title_bn?.let { binding.fAwardTitleBn.tvText.text = it }
                honoursAwards.award_details?.let { binding.fAwardDetailsDetails.tvText.text = it }
                honoursAwards.award_details_bn?.let { binding.fAwardDetailsDetailsBn.tvText.text = it }
                honoursAwards.award_date?.let { binding.fAwardDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }

            } else {

                honoursAwards.award_title?.let { binding.fAwardTitle.tvText.text = it }
                honoursAwards.award_title_bn?.let { binding.fAwardTitleBn.tvText.text = it }
                honoursAwards.award_details?.let { binding.fAwardDetailsDetails.tvText.text = it }
                honoursAwards.award_details_bn?.let { binding.fAwardDetailsDetailsBn.tvText.text = it }
                honoursAwards.award_date?.let { binding.fAwardDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
            }

        } else {

            binding.hHonoursAndAward.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            //binding.hHonoursAndAward.tvEdit.visibility = View.GONE
            honoursAwards.award_title?.let { binding.fAwardTitle.tvText.text = it }
            honoursAwards.award_title_bn?.let { binding.fAwardTitleBn.tvText.text = it }
            honoursAwards.award_details?.let { binding.fAwardDetailsDetails.tvText.text = it }
            honoursAwards.award_details_bn?.let { binding.fAwardDetailsDetailsBn.tvText.text = it }
            honoursAwards.award_date?.let { binding.fAwardDate.tvText.text = DateConverter.changeDateFormateForShowing(it) }
        }
    }

    fun bindPostingRecordData(
        binding: ModelEmployeeInfoBinding,
        postingRecords: Employee.PostingRecords,
        context: Context,
        heading: String
    ) {
        binding.hPostingRecord.tvTitle.text = heading
        binding.fPostingRType.tvTitle.text = context.getString(R.string.transfer_type)
        binding.fPostingRFrom.tvTitle.text = context.getString(R.string.transfer_from)
        binding.fPostingRTo.tvTitle.text = context.getString(R.string.transfer_to)
        binding.fPostingREffectiveDate.tvTitle.text = context.getString(R.string.eff_date)

        if (preparence.getLanguage().equals("en"))
        {
            postingRecords.transfer_type?.name?.let { binding.fPostingRType.tvText.text = it }
            postingRecords.transfer_from?.office_name?.let { binding.fPostingRFrom.tvText.text = it }
            postingRecords.transfer_to?.office_name?.let { binding.fPostingRTo.tvText.text = it }
            postingRecords.effective_date?.let { binding.fPostingREffectiveDate.tvText.text = it }

        } else {

            postingRecords.transfer_type?.name_bn?.let { binding.fPostingRType.tvText.text = it }
            postingRecords.transfer_from?.office_name_bn?.let { binding.fPostingRFrom.tvText.text = it }
            postingRecords.transfer_to?.office_name_bn?.let { binding.fPostingRTo.tvText.text = it }
            postingRecords.effective_date?.let { binding.fPostingREffectiveDate.tvText.text = it }
        }
    }

    fun bindDisciplinaryActionsData(
        binding: ModelEmployeeInfoBinding,
        disciplinaryAction: Employee.DisciplinaryAction,
        context: Context,
        heading: String
    ) {
        binding.hDiscipilinaryAction.tvEdit.visibility = View.GONE
        binding.hDiscipilinaryAction.tvTitle.text = heading
        binding.fDACategory.tvTitle.text = context.getString(R.string.disciplinary_action_category)
        binding.fDAPresentStatus.tvTitle.text = context.getString(R.string.present_status)
        binding.fDAJud.tvTitle.text = context.getString(R.string.judgment)
        binding.fDAJudBn.tvTitle.text = context.getString(R.string.judgment_bn)
        binding.fDAFinalJud.tvTitle.text = context.getString(R.string.final_judment)
        binding.fDAFinalJudBn.tvTitle.text = context.getString(R.string.final_judment_bn)
        binding.fDADescription.tvTitle.text = context.getString(R.string.disciplinary_action_details)
        binding.fDADescriptionBn.tvTitle.text = context.getString(R.string.disciplinary_action_details_bn)
        binding.fDARemarks.tvTitle.text = context.getString(R.string.remakrs)
        binding.fDARemarksBn.tvTitle.text = context.getString(R.string.remakrs_bn)
        binding.fDADate.tvTitle.text = context.getString(R.string.date)

        HelperClass.addHeaderColor(
            binding.hDiscipilinaryAction,
            context,
            disciplinaryAction.isPendingData
        )

        disciplinaryAction.present_status.let {
            binding.fDAPresentStatus.tvText.text =
                if (disciplinaryAction.present_status == 0) "" + context.getString(R.string.pending) else context.getText(R.string.close)
        }
        disciplinaryAction.disciplinary_action_details?.let { binding.fDADescription.tvText.text = it }
        disciplinaryAction.disciplinary_action_details_bn?.let { binding.fDADescriptionBn.tvText.text = it }
        disciplinaryAction.judgment?.let { binding.fDAJud.tvText.text = it }
        disciplinaryAction.judgment_bn?.let { binding.fDAJudBn.tvText.text = it }
        disciplinaryAction.final_judgment?.let { binding.fDAFinalJud.tvText.text = it }
        disciplinaryAction.final_judgment_bn?.let { binding.fDAFinalJudBn.tvText.text = it }
        disciplinaryAction.remarks?.let { binding.fDARemarks.tvText.text = it }
        disciplinaryAction.remarks_bn?.let { binding.fDARemarksBn.tvText.text = it }
        disciplinaryAction.date?.let { binding.fDADate.tvText.text = DateConverter.changeDateFormateForShowing(it) }

        if (!disciplinaryAction.isPendingData) {
            disciplinaryAction.disciplinaryActionCategory?.name?.let {
                binding.fDACategory.tvText.text = it
            }
        } else {

            binding.hDiscipilinaryAction.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)

            binding.fDACategory.tvText.text = HelperClass.getCommonDataFilltered(
                disciplinaryAction.disciplinary_action_category_id,
                commonData?.disciplinary_action_category,
                false
            )

        }

        //  }
    }

    fun bindReferencesData(
        binding: ModelEmployeeInfoBinding,
        references: Employee.References,
        context: Context,
        heading: String
    ) {
        binding.hReference.tvTitle.text = heading
        binding.fReferenceNameEn.tvTitle.text = context.getString(R.string.name)
        binding.fReferenceNameBn.tvTitle.text = context.getString(R.string.name_b)
        binding.fReferenceRelation.tvTitle.text = context.getString(R.string.relation)
        binding.fReferenceRelationBn.tvTitle.text = context.getString(R.string.relation_bn)
        binding.fReferenceAddress.tvTitle.text = context.getString(R.string.address)
        binding.fReferenceAddressBn.tvTitle.text = context.getString(R.string.address_bn)
        binding.fReferenceContactNo.tvTitle.text = context.getString(R.string.contact)
        binding.fReferenceContactNoBn.tvTitle.text = context.getString(R.string.contact_bn)

        HelperClass.addHeaderColor(binding.hReference, context, references.isPendingData)

        if (!references.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                references.name?.let { binding.fReferenceNameEn.tvText.text = it }
                references.name_bn?.let { binding.fReferenceNameBn.tvText.text = it }
                references.relation?.let { binding.fReferenceRelation.tvText.text = it }
                references.relation_bn?.let { binding.fReferenceRelationBn.tvText.text = it }
                references.address?.let { binding.fReferenceAddress.tvText.text = it }
                references.address_bn?.let { binding.fReferenceAddressBn.tvText.text = it }
                references.contact_no?.let { binding.fReferenceContactNo.tvText.text = it }
                references.contact_no_bn?.let { binding.fReferenceContactNoBn.tvText.text = it }

            } else {

                references.name?.let { binding.fReferenceNameEn.tvText.text = it }
                references.name_bn?.let { binding.fReferenceNameBn.tvText.text = it }
                references.relation?.let { binding.fReferenceRelation.tvText.text = it }
                references.relation_bn?.let { binding.fReferenceRelationBn.tvText.text = it }
                references.address?.let { binding.fReferenceAddress.tvText.text = it }
                references.address_bn?.let { binding.fReferenceAddressBn.tvText.text = it }
                references.contact_no?.let { binding.fReferenceContactNo.tvText.text = it }
                references.contact_no_bn?.let { binding.fReferenceContactNoBn.tvText.text = it }
            }

        } else {

            binding.hReference.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            //binding.hReference.tvEdit.visibility = View.GONE
            references.name?.let { binding.fReferenceNameEn.tvText.text = it }
            references.name_bn?.let { binding.fReferenceNameBn.tvText.text = it }
            references.relation?.let { binding.fReferenceRelation.tvText.text = it }
            references.relation_bn?.let { binding.fReferenceRelationBn.tvText.text = it }
            references.address?.let { binding.fReferenceAddress.tvText.text = it }
            references.address_bn?.let { binding.fReferenceAddressBn.tvText.text = it }
            references.contact_no?.let { binding.fReferenceContactNo.tvText.text = it }
            references.contact_no_bn?.let { binding.fReferenceContactNoBn.tvText.text = it }
        }
    }

    fun bindPromotionData(
        binding: ModelEmployeeInfoBinding,
        promotions: Employee.Promotions,
        context: Context,
        heading: String
    ) {
        binding.hPromotion.tvEdit.visibility = View.GONE
        binding.hPromotion.tvTitle.text = heading
        binding.fPromotionMemoNo.tvTitle.text = context.getString(R.string.memo_no)
        binding.fPromotionMemoDate.tvTitle.text = context.getString(R.string.memo_date)
        binding.fPromotionOfficeZone.tvTitle.text = context.getString(R.string.office_zoon)
        binding.fPromotionPreviousPost.tvTitle.text = context.getString(R.string.previous_post)
        binding.fPromotionCurrentPosition.tvTitle.text = context.getString(R.string.curent_post)

        if (preparence.getLanguage().equals("en"))
        {
            promotions.memo_no?.let { binding.fPromotionMemoNo.tvText.text = it }
            promotions.memo_date?.let { binding.fPromotionMemoDate.tvText.text = it }
            // promotions?.let { binding.fPromotionOfficeZone.tvText.setText(it) }
            promotions.previous_posts?.name?.let { binding.fPromotionPreviousPost.tvText.text = it }
            promotions.current_position?.name?.let { binding.fPromotionCurrentPosition.tvText.text = it }

        } else {

            promotions.memo_no_bn?.let { binding.fPromotionMemoNo.tvText.text = it }
            promotions.memo_date?.let { binding.fPromotionMemoDate.tvText.text = it }
            //promotions.off?.let { binding.fPromotionOfficeZone.tvText.setText(it) }
            promotions.previous_posts?.name_bn?.let { binding.fPromotionPreviousPost.tvText.text = it }
            promotions.current_position?.name_bn?.let { binding.fPromotionCurrentPosition.tvText.text = it }
        }
    }

    fun bindQuotaData(
        binding: ModelEmployeeInfoBinding,
        quotas: Employee.EmployeeQuotas,
        context: Context,
        heading: String
    ) {
        binding.hQuota.tvTitle.text = heading
        binding.fQuotaName.tvTitle.text = context.getString(R.string.quota_name)
        binding.fQuotaType.tvTitle.text = context.getString(R.string.quota_type)
        binding.fQuotaDescription.tvTitle.text = context.getString(R.string.description)
        binding.fQuotaDescriptionBn.tvTitle.text = context.getString(R.string.description_bn)

        HelperClass.addHeaderColor(binding.hQuota, context, quotas.isPendingData)

        val itemOnClick: (Int) -> Unit = {
            try {
                ConvertNumber.viewFileInShareIntent(context, quotas.quota_documnets?.get(it))
            } catch (Ex: java.lang.Exception) {
                Toast.makeText(context, Ex.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        val mAdapter = name_view_row_adapter(quotas.quota_documnets.orEmpty(), itemOnClick)

        binding.nameList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        Log.d("TAG", "LENGTH: ${mAdapter.itemCount} ")

        if (!quotas.isPendingData) {
            if (preparence.getLanguage().equals("en"))
            {
                quotas.description?.let { binding.fQuotaDescription.tvText.text = it }
                quotas.description_bn?.let { binding.fQuotaDescriptionBn.tvText.text = it }
                quotas.quotaInformation?.name?.let { binding.fQuotaName.tvText.text = it }
                quotas.quotaInformationDetails?.name.let { binding.fQuotaType.tvText.text = it }

            } else {

                quotas.description?.let { binding.fQuotaDescription.tvText.text = it }
                quotas.description_bn?.let { binding.fQuotaDescriptionBn.tvText.text = it }
                quotas.quotaInformation?.name_bn?.let { binding.fQuotaName.tvText.text = it }
                quotas.quotaInformationDetails?.name_bn.let { binding.fQuotaType.tvText.text = it }
            }

        } else {

            binding.hQuota.tvTitle.text = "$heading (${context.getString(R.string.pending_data)})"
            val commonData: CommonData? = preparence.get(HelperClass.COMMON_DATA)
            //binding.hQuota.tvEdit.visibility = View.GONE
            quotas.description?.let { binding.fQuotaDescription.tvText.text = it }
            quotas.description_bn?.let { binding.fQuotaDescriptionBn.tvText.text = it }

            binding.fQuotaName.tvText.text = HelperClass.getCommonDataFilltered(
                quotas.quota_information_id,
                commonData?.quota_information,
                false
            )


            binding.fQuotaType.tvText.text = HelperClass.getCommonDataFilltered(
                quotas.quota_information_detail_id,
                commonData?.quota_types,
                false
            )


        }


    }
}
