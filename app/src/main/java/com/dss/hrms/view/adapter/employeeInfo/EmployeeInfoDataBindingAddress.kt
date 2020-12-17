package com.dss.hrms.view.adapter.employeeInfo

import android.content.Context
import android.view.View
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelEmployeeInfoBinding
import com.dss.hrms.model.Employee
import com.namaztime.namaztime.database.MySharedPreparence

class EmployeeInfoDataBindingAddress {

    fun bindPermanentAddressData(
        binding: ModelEmployeeInfoBinding,
        addresses: Employee.PermanentAddresses,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {

        binding.fAddressEmailAddress.llBody.visibility = View.GONE
        binding.fAddressPhoneOrMobileNo.llBody.visibility = View.GONE
        binding.hAddress.tvTitle.setText(heading)
        binding.fAddressPostOffice.tvTitle.setText(context.getString(R.string.post_off))
        binding.fAddressPostOfficeBn.tvTitle.setText(context.getString(R.string.post_off_bn))
        binding.fAddressRoadOrWordNo.tvTitle.setText(context.getString(R.string.road_word))
        binding.fAddressRoadOrWordNoBn.tvTitle.setText(context.getString(R.string.road_word_bn))
        binding.fAddressPoliceStation.tvTitle.setText(context.getString(R.string.police_station))
        binding.fAddressDivision.tvTitle.setText(context.getString(R.string.division))
        binding.fAddressDistrict.tvTitle.setText(context.getString(R.string.district))
        binding.fAddressPhoneOrMobileNo.tvTitle.setText(context.getString(R.string.phone_mobile))
        binding.fAddressVillageOrHouseNo.tvTitle.setText(context.getString(R.string.vill_house))
        binding.fAddressVillageOrHouseNoBn.tvTitle.setText(context.getString(R.string.vill_house_bn))
        binding.fAddressEmailAddress.tvTitle.setText(context.getString(R.string.email))


        if (preparence.getLanguage()
                .equals("en")
        ) {
            addresses.village_house_no?.let {
                binding.fAddressVillageOrHouseNo.tvText.setText(it)
            }
            addresses.village_house_no?.let {
                binding.fAddressVillageOrHouseNo.tvText.setText(
                    it
                )
            }
            addresses.village_house_no_bn?.let {
                binding.fAddressVillageOrHouseNoBn.tvText.setText(
                    it
                )
            }
            addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            addresses.road_word_no_bn?.let {
                binding.fAddressRoadOrWordNoBn.tvText.setText(
                    it
                )
            }
            addresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
            addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.setText(it) }
            addresses.police_station?.let { binding.fAddressPoliceStation.tvText.setText(it) }
            addresses.division?.name?.let { binding.fAddressDivision.tvText.setText(it) }
            addresses.district?.name?.let { binding.fAddressDistrict.tvText.setText(it) }
            addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
        } else {
            addresses.district?.name_bn?.let {
                binding.fAddressDistrict.tvText.setText(it)

            }
            addresses.village_house_no?.let {
                binding.fAddressVillageOrHouseNo.tvText.setText(
                    it
                )
            }
            addresses.village_house_no_bn?.let {
                binding.fAddressVillageOrHouseNoBn.tvText.setText(
                    it
                )
            }
            addresses.road_word_no_bn?.let {
                binding.fAddressRoadOrWordNoBn.tvText.setText(
                    it
                )
            }
            addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            addresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
            addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.setText(it) }
            addresses.police_station_bn?.let {
                binding.fAddressPoliceStation.tvText.setText(
                    it
                )
            }
            addresses.division?.name_bn?.let { binding.fAddressDivision.tvText.setText(it) }
            addresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.setText(it) }
            addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
        }
    }


    fun bindPrensentAddressData(
        binding: ModelEmployeeInfoBinding,
        addresses: Employee.PresentAddresses,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {

        binding.hAddress.tvTitle.setText(heading)
        binding.fAddressEmailAddress.llBody.visibility = View.GONE
        binding.fAddressPhoneOrMobileNo.llBody.visibility = View.GONE
        binding.fAddressPostOffice.tvTitle.setText(context.getString(R.string.post_off))
        binding.fAddressPostOfficeBn.tvTitle.setText(context.getString(R.string.post_off_bn))
        binding.fAddressRoadOrWordNo.tvTitle.setText(context.getString(R.string.road_word))
        binding.fAddressRoadOrWordNoBn.tvTitle.setText(context.getString(R.string.road_word_bn))
        binding.fAddressPoliceStation.tvTitle.setText(context.getString(R.string.police_station))
        binding.fAddressDivision.tvTitle.setText(context.getString(R.string.division))
        binding.fAddressDistrict.tvTitle.setText(context.getString(R.string.district))
        binding.fAddressPhoneOrMobileNo.tvTitle.setText(context.getString(R.string.phone_mobile))
        binding.fAddressVillageOrHouseNo.tvTitle.setText(context.getString(R.string.vill_house))
        binding.fAddressVillageOrHouseNoBn.tvTitle.setText(context.getString(R.string.vill_house_bn))
        binding.fAddressEmailAddress.tvTitle.setText(context.getString(R.string.email))
        if (preparence.getLanguage()
                .equals("en")
        ) {
            addresses.village_house_no?.let {
                binding.fAddressVillageOrHouseNo.tvText.setText(it)
            }
            addresses.village_house_no?.let {
                binding.fAddressVillageOrHouseNo.tvText.setText(
                    it
                )
            }
            addresses.village_house_no_bn?.let {
                binding.fAddressVillageOrHouseNoBn.tvText.setText(
                    it
                )
            }
            addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            addresses.road_word_no_bn?.let {
                binding.fAddressRoadOrWordNoBn.tvText.setText(
                    it
                )
            }
            addresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
            addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.setText(it) }
            addresses.police_station?.let { binding.fAddressPoliceStation.tvText.setText(it) }
            addresses.division?.name?.let { binding.fAddressDivision.tvText.setText(it) }
            addresses.district?.name?.let { binding.fAddressDistrict.tvText.setText(it) }
            addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
        } else {
            addresses.district?.name_bn?.let {
                binding.fAddressDistrict.tvText.setText(it)

            }
            addresses.village_house_no?.let {
                binding.fAddressVillageOrHouseNo.tvText.setText(
                    it
                )
            }
            addresses.village_house_no_bn?.let {
                binding.fAddressVillageOrHouseNoBn.tvText.setText(
                    it
                )
            }
            addresses.road_word_no_bn?.let {
                binding.fAddressRoadOrWordNoBn.tvText.setText(
                    it
                )
            }
            addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            addresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
            addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.setText(it) }
            addresses.police_station_bn?.let {
                binding.fAddressPoliceStation.tvText.setText(
                    it
                )
            }
            addresses.division?.name_bn?.let { binding.fAddressDivision.tvText.setText(it) }
            addresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.setText(it) }
            addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
        }
    }
}