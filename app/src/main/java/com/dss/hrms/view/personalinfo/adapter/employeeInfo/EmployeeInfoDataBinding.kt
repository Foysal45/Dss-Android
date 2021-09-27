package com.dss.hrms.view.personalinfo.adapter.employeeInfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelEmployeeInfoBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.repository.CommonRepo
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.ConvertNumber
import com.dss.hrms.util.DateConverter
import com.dss.hrms.view.allInterface.CommonDataValueListener
import com.dss.hrms.view.allInterface.CommonSpinnerSelectedItemListener
import com.dss.hrms.view.personalinfo.adapter.SpinnerAdapter
import com.dss.hrms.view.personalinfo.adapter.name_view_row_adapter
import com.dss.hrms.view.personalinfo.dialog.EditOfficialResidentialIInfo
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import java.lang.Exception
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


        if (addresses.isPendingData == false) {

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
                addresses.localGovernmentType?.name?.let {
                    binding.fAddressUnion.tvText.text = it
                }
                addresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
                addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.setText(it) }
                addresses.post_code?.let { binding.fAddressPostCode.tvText.setText(it) }
                addresses.division?.name?.let { binding.fAddressDivision.tvText.setText(it) }
                addresses.district?.name?.let { binding.fAddressDistrict.tvText.setText(it) }
                addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.setText(it) }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.setText(it) }
                addresses.police_station_bn?.let { binding.fAddressPoliceStationBn.tvText.setText(it) }
//            addresses.upazila?.name?.let { binding.fAddressUpazila.tvText.setText(it) }

                when (addresses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        addresses.cityCorporation?.name?.let {
                            binding.fAddressLocalName.tvText.text = it
                        }
                        //    binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.name
                        binding.fAddressUnionName.llBody.visibility = View.GONE

                    }
                    2 -> {
                        // municipility

                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.municipalities)
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
                addresses.localGovernmentType?.name_bn?.let {
                    binding.fAddressUnion.tvText.text = it
                }
                addresses.district?.name_bn?.let {
                    binding.fAddressDistrict.tvText.text = it

                }
                addresses.village_house_no?.let {
                    binding.fAddressVillageOrHouseNo.tvText.text = it
                }
                addresses.village_house_no_bn?.let {
                    binding.fAddressVillageOrHouseNoBn.tvText.text = it
                }
                addresses.road_word_no_bn?.let {
                    binding.fAddressRoadOrWordNoBn.tvText.text = it
                }
                addresses.post_code?.let { binding.fAddressPostCode.tvText.text = it }
                addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.text = it }
                addresses.post_office?.let { binding.fAddressPostOffice.tvText.text = it }
                addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.text = it }
                addresses.police_station_bn?.let {
                    binding.fAddressPoliceStationBn.tvText.text = it
                }
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
                        //    binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.nameBn

                        binding.fAddressUnionName.llBody.visibility = View.GONE
                    }
                    2 -> {
                        // municipility

                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.municipalities)
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

            binding.hAddress.tvTitle.text = context.getString(R.string.pending_data)
            binding.hAddress.tvEdit.visibility = View.GONE

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

            binding.fAddressUnion.tvText.text = "${addresses.local_government_type_id}"

            addresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
            addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.setText(it) }
            addresses.post_code?.let { binding.fAddressPostCode.tvText.setText(it) }
            binding.fAddressDivision.tvText.setText("${addresses.division_id}")
            binding.fAddressDistrict.tvText.setText("${addresses.district_id}")
            addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
            addresses.police_station?.let { binding.fAddressPoliceStation.tvText.setText(it) }
            addresses.police_station?.let { binding.fAddressPoliceStation.tvText.setText(it) }
            addresses.police_station_bn?.let { binding.fAddressPoliceStationBn.tvText.setText(it) }
//            addresses.upazila?.name?.let { binding.fAddressUpazila.tvText.setText(it) }
        }

    }


    fun bindPrensentAddressData(
        binding: ModelEmployeeInfoBinding,
        addresses: Employee.PresentAddresses,
        context: Context,
        heading: String
    ) {
        binding.hAddress.tvTitle.setText(heading)

        binding.fAddressUpazila.llBody.visibility = View.GONE
        binding.fAddressEmailAddress.llBody.visibility = View.GONE
        binding.fAddressPhoneOrMobileNo.llBody.visibility = View.GONE


        binding.fAddressPostOffice.tvTitle.setText(context.getString(R.string.post_off))
        binding.fAddressPostOfficeBn.tvTitle.setText(context.getString(R.string.post_off_bn))
        binding.fAddressPostCode.tvTitle.setText(context.getString(R.string.post_code))
        binding.fAddressRoadOrWordNo.tvTitle.setText(context.getString(R.string.road_word))
        binding.fAddressRoadOrWordNoBn.tvTitle.setText(context.getString(R.string.road_word_bn))
        binding.fAddressPoliceStation.tvTitle.setText(context.getString(R.string.police_station))
        binding.fAddressDivision.tvTitle.setText(context.getString(R.string.division))
        binding.fAddressDistrict.tvTitle.setText(context.getString(R.string.district))
        binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
        binding.fAddressUnion.tvTitle.setText(context.getString(R.string.union_or_municiplity))
        binding.fAddressPoliceStationBn.tvTitle.setText(context.getString(R.string.police_station_bn))
        binding.fAddressPhoneOrMobileNo.tvTitle.setText(context.getString(R.string.phone_mobile))
        binding.fAddressVillageOrHouseNo.tvTitle.setText(context.getString(R.string.vill_house))
        binding.fAddressVillageOrHouseNoBn.tvTitle.setText(context.getString(R.string.vill_house_bn))
        binding.fAddressEmailAddress.tvTitle.setText(context.getString(R.string.email))

        if (addresses.isPendingData == false) {
            if (preparence.getLanguage()
                    .equals("en")
            ) {
                addresses.village_house_no?.let {
                    binding.fAddressVillageOrHouseNo.tvText.text = it
                }
                addresses.village_house_no?.let {
                    binding.fAddressVillageOrHouseNo.tvText.text = it
                }
                addresses.village_house_no_bn?.let {
                    binding.fAddressVillageOrHouseNoBn.tvText.text = it
                }
                addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.text = it }
                addresses.road_word_no_bn?.let {
                    binding.fAddressRoadOrWordNoBn.tvText.text = it
                }
                addresses.post_office?.let { binding.fAddressPostOffice.tvText.text = it }
                addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.text = it }
                addresses.post_code?.let { binding.fAddressPostCode.tvText.text = it }

                addresses.division?.name?.let { binding.fAddressDivision.tvText.setText(it) }
                addresses.district?.name?.let { binding.fAddressDistrict.tvText.setText(it) }
                addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
                addresses.upazila?.name?.let { binding.fAddressUpazila.tvText.text = it }
                addresses.police_station_bn?.let {
                    binding.fAddressPoliceStationBn.tvText.text = it
                }

                addresses.localGovernmentType?.name?.let {
                    binding.fAddressUnion.tvText.text = it
                }

                // adding check for  cilty , municipility  or union


                when (addresses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        addresses.cityCorporation?.name?.let {
                            binding.fAddressLocalName.tvText.text = it
                        }
                        //    binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.name
                        binding.fAddressUnionName.llBody.visibility = View.GONE

                    }
                    2 -> {
                        // municipility

                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.municipalities)
                        binding.fAddressLocalName.tvText.text = addresses.municipality?.name
                        binding.fAddressUnionName.llBody.visibility = View.GONE

                    }
                    3 -> {
                        // upzilla
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fAddressLocalName.tvText.text = addresses.upazila?.name

                        binding.fAddressUnion.tvTitle.text = context.getString(R.string.union)
                        binding.fAddressUnion.tvText.text = addresses.Union?.name
                        binding.fAddressUnionName.llBody.visibility = View.GONE

                    }
                }


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
                addresses.post_code?.let { binding.fAddressPostCode.tvText.setText(it) }
                addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
                addresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
                addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.setText(it) }
                addresses.police_station_bn?.let {
                    binding.fAddressPoliceStationBn.tvText.setText(
                        it
                    )
                }
                addresses.division?.name_bn?.let { binding.fAddressDivision.tvText.setText(it) }
                addresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.text = it }
                addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.text = it }
                addresses.police_station?.let { binding.fAddressPoliceStation.tvText.text = it }
                addresses.upazila?.name_bn?.let { binding.fAddressUpazila.tvText.setText(it) }

                addresses.localGovernmentType?.name_bn?.let {
                    binding.fAddressUnion.tvText.text = it
                }

                when (addresses.local_government_type_id) {
                    1 -> {
                        // city corpo
                        addresses.cityCorporation?.name?.let {
                            binding.fAddressLocalName.tvText.text = it
                        }
                        //    binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.citycorporation)
                        binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.nameBn

                        binding.fAddressUnionName.llBody.visibility = View.GONE
                    }
                    2 -> {
                        // municipility

                        binding.fAddressLocalName.tvTitle.text =
                            context.getString(R.string.municipalities)
                        binding.fAddressLocalName.tvText.text = addresses.municipality?.name_bn
                        binding.fAddressUnionName.llBody.visibility = View.GONE
                    }
                    3 -> {
                        // upzilla
                        binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
                        binding.fAddressLocalName.tvText.text = addresses.upazila?.name_bn

                        binding.fAddressUnion.tvTitle.text = context.getString(R.string.union)
                        binding.fAddressUnion.tvText.text = addresses.Union?.name_bn

                        binding.fAddressUnionName.llBody.visibility = View.VISIBLE

                    }
                }

            }
        } else if (addresses.isPendingData) {

            binding.hAddress.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hAddress.tvEdit.visibility = View.GONE

            binding.fAddressDistrict.tvText.setText(addresses.district_id.toString())

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
            addresses.post_code?.let { binding.fAddressPostCode.tvText.setText(it) }
            addresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            addresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
            addresses.post_office_bn?.let { binding.fAddressPostOfficeBn.tvText.setText(it) }
            binding.fAddressPoliceStationBn.tvText.setText(
                addresses.police_station_bn + ""
            )

            addresses.division_id?.let { binding.fAddressDivision.tvText.setText(it.toString()) }
            addresses.district_id?.let { binding.fAddressDistrict.tvText.text = it.toString() }
            addresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.text = it }
            addresses.police_station?.let {
                binding.fAddressPoliceStation.tvText.text = it.toString()
            }
            addresses.upazila_id?.let { binding.fAddressUpazila.tvText.setText(it.toString()) }

//            addresses.localGovernmentType?.name_bn?.let {
//                binding.fAddressUnion.tvText.text = it
//            }

//            when (addresses.local_government_type_id) {
//                1 -> {
//                    // city corpo
//                    addresses.cityCorporation?.name?.let {
//                        binding.fAddressLocalName.tvText.text = it
//                    }
//                    //    binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
//                    binding.fAddressLocalName.tvTitle.text =
//                        context.getString(R.string.citycorporation)
//                    binding.fAddressLocalName.tvText.text = addresses.cityCorporation?.nameBn
//
//                    binding.fAddressUnionName.llBody.visibility = View.GONE
//                }
//                2 -> {
//                    // municipility
//
//                    binding.fAddressLocalName.tvTitle.text =
//                        context.getString(R.string.municipalities)
//                    binding.fAddressLocalName.tvText.text = addresses.municipality?.name_bn
//                    binding.fAddressUnionName.llBody.visibility = View.GONE
//                }
//                3 -> {
//                    // upzilla
//                    binding.fAddressLocalName.tvTitle.text = context.getString(R.string.upazila)
//                    binding.fAddressLocalName.tvText.text = addresses.upazila?.name_bn
//
//                    binding.fAddressUnion.tvTitle.text = context.getString(R.string.union)
//                    binding.fAddressUnion.tvText.text = addresses.Union?.name_bn
//
//                    binding.fAddressUnionName.llBody.visibility = View.VISIBLE
//
//                }
//            }
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
        binding.hEducationQualification.tvTitle.setText(heading)
        binding.fEQNameOfD.tvTitle.setText(context.getString(R.string.name_deg))
        binding.fEQNameOIn.tvTitle.setText(context.getString(R.string.name_institution))
        binding.fEQBoardOrUniversity.tvTitle.setText(context.getString(R.string.name_of_board))
        binding.fEQPassingYear.tvTitle.setText(context.getString(R.string.passing_year))
        binding.fEQDivisionOrCgpa.tvTitle.setText(context.getString(R.string.div_cgpa))
        binding.fEQAttachment.tvTitle.setText(context.getString(R.string.attachment))


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
            if (preparence.getLanguage()
                    .equals("en")
            ) {
                qualifications.examination?.name?.let { binding.fEQNameOfD.tvText.setText(it) }
                qualifications.educational_institute?.name.let {
                    binding.fEQNameOIn.tvText.setText(
                        it
                    )
                }
                qualifications.board?.name.let { binding.fEQBoardOrUniversity.tvText.setText(it) }
                qualifications.passing_year?.let { binding.fEQPassingYear.tvText.setText(it) }
                qualifications.division_cgpa?.let { binding.fEQDivisionOrCgpa.tvText.setText(it) }
            } else {

                qualifications.examination?.name_bn?.let { binding.fEQNameOfD.tvText.setText(it) }
                qualifications.educational_institute?.name_bn.let {
                    binding.fEQNameOIn.tvText.setText(
                        it
                    )
                }
                qualifications.board?.name_bn.let { binding.fEQBoardOrUniversity.tvText.setText(it) }
                qualifications.passing_year?.let { binding.fEQPassingYear.tvText.setText(it) }
                qualifications.division_cgpa?.let { binding.fEQDivisionOrCgpa.tvText.setText(it) }

            }

        } else {
            binding.hEducationQualification.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hEducationQualification.tvEdit.visibility = View.GONE

            binding.fEQNameOfD.tvText.setText("${qualifications.examination_id}")

            binding.fEQNameOIn.tvText.setText(
                "${qualifications.educational_institute_id}"
            )
            binding.fEQBoardOrUniversity.tvText.setText("${qualifications.board_id}")
            qualifications.passing_year?.let { binding.fEQPassingYear.tvText.setText(it) }
            qualifications.division_cgpa?.let { binding.fEQDivisionOrCgpa.tvText.setText(it) }

        }

    }


    fun bindNomineeData(
        binding: ModelEmployeeInfoBinding,
        nominee: Employee.Nominee,
        context: Context,
        heading: String
    ) {

        binding.hNominee.tvTitle.setText(heading)
        binding.fNName.tvTitle.setText(context.getString(R.string.name_of_nominee))
        binding.fNDOB.tvTitle.setText(context.getString(R.string.birth))
        binding.fNRelation.tvTitle.setText(context.getString(R.string.nominee_relation))
        binding.fNAllocatedPercentage.tvTitle.setText(context.getString(R.string.nominee_allocated_percentage))
        binding.fNGender.tvTitle.setText(context.getString(R.string.nominee_gender))
        binding.fNMaritalStatus.tvTitle.setText(context.getString(R.string.nominee_marital_status))
        binding.fNHasDisavility.tvTitle.setText(context.getString(R.string.nominee_has_disability))
        binding.tvNSignatureTitle.setText(context.getString(R.string.nominee_signature))


        nominee.name?.let { binding.fNName.tvText.setText(it) }
        nominee?.date_of_birth?.let {
            binding.fNDOB.tvText.setText(
                (DateConverter.changeDateFormateForShowing(
                    it
                ))
            )
        }
        nominee.relation?.let { binding.fNRelation.tvText.setText(it) }
        nominee.allocated_percentage?.let { binding.fNAllocatedPercentage.tvText.setText(it) }


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



        nominee?.has_disability?.let {
            if (it == 1)
                binding.fNHasDisavility.tvText.setText(context?.getString(R.string.yes)) else
                binding.fNHasDisavility.tvText.setText(context?.getString(R.string.no))
        }

        context?.let {
            Glide.with(it).applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_24)
            ).load(RetrofitInstance.BASE_URL + nominee?.nominee_signature)
                .into(binding.ivNSignature)
        }

        if (preparence.getLanguage()
                .equals("en")
        ) {
            nominee.marital_status?.name?.let { binding.fNMaritalStatus.tvText.setText(it) }
            nominee.gender?.name?.let { binding.fNGender.tvText.setText(it) }
        } else {
            nominee.marital_status?.name_bn?.let { binding.fNMaritalStatus.tvText.setText(it) }
            nominee.gender?.name_bn?.let { binding.fNGender.tvText.setText(it) }
        }
    }


    fun bindSpouseData(
        binding: ModelEmployeeInfoBinding,
        spouses: Employee.Spouses,
        context: Context,
        heading: String
    ) {
        binding.fSpouseAddressUpazila.llBody.visibility = View.GONE
        binding.hSpouse.tvTitle.setText(heading)
        binding.fSpouseNameBn.tvTitle.setText(context.getString(R.string.name_b))
        binding.fSpouseNameEn.tvTitle.setText(context.getString(R.string.name))
        binding.fSpouseOffice.tvTitle.setText(context.getString(R.string.workstation))
        binding.fSpouseRelation.tvTitle.setText(context.getString(R.string.relation))
        binding.fSpouseDivision.tvTitle.setText(context.getString(R.string.division))
        binding.fSpouseDistrict.tvTitle.setText(context.getString(R.string.district))
        binding.fSpouseThana.tvTitle.setText(context.getString(R.string.upazila))
        binding.fSpouseJobType.tvTitle.setText(context.getString(R.string.job_type))
        binding.fSpouseOccupation.tvTitle.setText(context.getString(R.string.occupation))
        binding.fSpousePhoneNo.tvTitle.setText(context.getString(R.string.phone))
        binding.fSpouseMobileNo.tvTitle.setText(context.getString(R.string.mobile))
        binding.fSpouseReligion.tvTitle.setText(context.getString(R.string.religion))
        binding.fSpouseAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
        binding.fSpouseAddressUnion.tvTitle.setText(context.getString(R.string.union_or_municiplity))
        binding.fSpouseAddressPoliceStation.tvTitle.setText(context.getString(R.string.police_station))
        binding.fSpouseAddressPoliceStationBn.tvTitle.setText(context.getString(R.string.police_station_bn))
        binding.fSpouseAddressPostOffice.tvTitle.setText(context.getString(R.string.post_off))
        binding.fSpouseAddressPostOfficeBn.tvTitle.setText(context.getString(R.string.post_off_bn))
        binding.fSpouseAddressPostCode.tvTitle.setText(context.getString(R.string.post_code))
        binding.fSpouseAddressRoadOrWordNo.tvTitle.setText(context.getString(R.string.road_word))
        binding.fSpouseAddressRoadOrWordNoBn.tvTitle.setText(context.getString(R.string.road_word_bn))



        binding.fSpousePhoneNo.tvTitle.setText(context.getString(R.string.phone_mobile))
        binding.fSpouseAddressVillageOrHouseNo.tvTitle.setText(context.getString(R.string.vill_house))
        binding.fSpouseAddressVillageOrHouseNoBn.tvTitle.setText(context.getString(R.string.vill_house_bn))
        binding.fSpouseReligion.llBody.visibility = View.GONE

        binding?.fSpouseAddressPoliceStation?.tvText?.setText(spouses?.police_station)
        binding?.fSpouseAddressPoliceStationBn?.tvText?.setText(spouses?.police_station_bn)
        binding.fSpouseAddressPostOffice?.tvText?.setText(spouses?.post_office)
        binding.fSpouseAddressPostOfficeBn?.tvText?.setText(spouses?.post_office_bn)
        binding.fSpouseAddressPostCode?.tvText?.setText(spouses?.post_code)
        binding.fSpouseAddressRoadOrWordNo?.tvText?.setText(spouses?.road_word_no)
        binding.fSpouseAddressRoadOrWordNoBn?.tvText?.setText(spouses?.road_word_no_bn)
        binding.fSpouseAddressVillageOrHouseNo?.tvText?.setText(spouses?.village_house_no)
        binding.fSpouseAddressVillageOrHouseNoBn?.tvText?.setText(spouses?.village_house_no_bn)

        if (preparence.getLanguage()
                .equals("en")
        ) {

            spouses.name_bn?.let { binding.fSpouseNameBn.tvText.setText(it) }
            spouses.name?.let { binding.fSpouseNameEn.tvText.setText(it) }
            spouses.spouse_workstation?.name?.let { binding.fSpouseOffice.tvText.setText(it) }
            spouses.upazila?.name?.let { binding.fSpouseThana.tvText.setText(it) }
            spouses.distric?.name?.let { binding.fSpouseDistrict.tvText.setText(it) }
            spouses.division?.name?.let { binding.fSpouseDivision.tvText.setText(it) }
            spouses.spouse_job_type?.name?.let { binding.fSpouseJobType.tvText.setText(it) }
            spouses.occupation?.name.let { binding.fSpouseOccupation.tvText.setText(it) }
            spouses.relation?.let { binding.fSpouseRelation.tvText.setText(it) }
            spouses.phone_no?.let { binding.fSpousePhoneNo.tvText.setText(it) }
            spouses.mobile_no?.let { binding.fSpouseMobileNo.tvText.setText(it) }
            // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }
            spouses.village_house_no?.let {
                binding.fSpouseAddressVillageOrHouseNo.tvText.setText(it)
            }
            spouses.village_house_no?.let {
                binding.fSpouseAddressVillageOrHouseNo.tvText.setText(
                    it
                )
            }
            spouses.village_house_no_bn?.let {
                binding.fSpouseAddressVillageOrHouseNoBn.tvText.setText(
                    it
                )
            }
            spouses.road_word_no?.let { binding.fSpouseAddressRoadOrWordNo.tvText.setText(it) }
            spouses.road_word_no_bn?.let {
                binding.fSpouseAddressRoadOrWordNoBn.tvText.setText(
                    it
                )
            }
            spouses.post_office?.let { binding.fSpouseAddressPostOffice.tvText.setText(it) }
            spouses.post_office_bn?.let { binding.fSpouseAddressPostOfficeBn.tvText.setText(it) }
            spouses.post_code?.let { binding.fSpouseAddressPostCode.tvText.setText(it) }
            spouses.division?.name?.let { binding.fSpouseDivision.tvText.setText(it) }
            spouses.distric?.name?.let { binding.fSpouseDistrict.tvText.setText(it) }
            spouses.phone_no?.let { binding.fSpousePhoneNo.tvText.setText(it) }
            spouses.police_station?.let { binding.fSpouseAddressPoliceStation.tvText.setText(it) }
            spouses.police_station_bn?.let { binding.fSpouseAddressPoliceStationBn.tvText.setText(it) }
            spouses.upazila?.name?.let { binding.fSpouseAddressUpazila.tvText.setText(it) }
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
                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.citycorporation)
                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.citycorporation)
                    binding.fSpouseAddressLocalName.tvText.text = spouses.cityCorporation?.name
                    binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                    binding.fSpouseAddressUpazila.llBody.visibility = View.GONE

                }
                2 -> {
                    // municipility

                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.municipalities)
                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.municipalities)

                    binding.fSpouseAddressLocalName.tvText.text = spouses.municipality?.name
                    binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                    binding.fSpouseAddressUpazila.llBody.visibility = View.GONE
                }
                3 -> {
                    // upzilla
                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.upazila)
                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.municipalities)
                    binding.fSpouseAddressLocalName.tvText.text = spouses.upazila?.name
                    //    binding.fSpouseAddressUpazila.llBody.visibility = View.VISIBLE
                    binding.fSpouseAddressUnion.tvTitle.text = context.getString(R.string.union)
                    binding.fSpouseAddressUnion.tvText.text = spouses.union?.name
                    binding.fSpouseAddressUnionName.llBody.visibility = View.GONE

                }
            }


        } else {
            spouses.name_bn?.let { binding.fSpouseNameBn.tvText.setText(it) }
            spouses.name?.let { binding.fSpouseNameEn.tvText.setText(it) }
            spouses.spouse_workstation?.name_bn?.let { binding.fSpouseOffice.tvText.setText(it) }
            spouses.upazila?.name_bn?.let { binding.fSpouseThana.tvText.setText(it) }
            spouses.distric?.name_bn?.let { binding.fSpouseDistrict.tvText.setText(it) }
            spouses.spouse_job_type?.name_bn?.let { binding.fSpouseJobType.tvText.setText(it) }
            spouses.occupation?.name_bn.let { binding.fSpouseOccupation.tvText.setText(it) }
            spouses.phone_no?.let { binding.fSpousePhoneNo.tvText.setText(it) }
            spouses.mobile_no?.let { binding.fSpouseMobileNo.tvText.setText(it) }
            spouses.relation?.let {
                if (it.toLowerCase()
                        .equals("wife")
                ) binding.fSpouseRelation.tvText.setText("স্ত্রী") else binding.fSpouseRelation.tvText.setText(
                    "স্বামী"
                )
            }
            spouses.division?.name_bn?.let { binding.fSpouseDivision.tvText.setText(it) }
            // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }

            when (spouses.local_government_type_id) {
                1 -> {
                    // city corpo
                    spouses.cityCorporation?.name?.let {
                        binding.fSpouseAddressLocalName.tvText.text = it
                    }
                    //    binding.fAddressUpazila.tvTitle.setText(context.getString(R.string.upazila))
                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.citycorporation)
                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.citycorporation)
                    binding.fSpouseAddressLocalName.tvText.text = spouses.cityCorporation?.nameBn
                    binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                    binding.fSpouseAddressUpazila.llBody.visibility = View.GONE

                }
                2 -> {
                    // municipility
                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.municipalities)
                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.municipalities)

                    binding.fSpouseAddressLocalName.tvText.text = spouses.municipality?.name_bn
                    binding.fSpouseAddressUnionName.llBody.visibility = View.GONE
                    binding.fSpouseAddressUpazila.llBody.visibility = View.GONE
                }
                3 -> {
                    // upzilla
                    binding.fSpouseAddressUnion.tvText.text =
                        context.getString(R.string.upazila)
                    binding.fSpouseAddressLocalName.tvTitle.text =
                        context.getString(R.string.upazila)
                    binding.fSpouseAddressLocalName.tvText.text = spouses.upazila?.name_bn
                    //    binding.fSpouseAddressUpazila.llBody.visibility = View.VISIBLE
                    binding.fSpouseAddressUnion.tvTitle.text = context.getString(R.string.union)
                    binding.fSpouseAddressUnion.tvText.text = spouses.union?.name_bn
                    binding.fSpouseAddressUnionName.llBody.visibility = View.GONE

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

        binding.hJobJoiningInformation.tvTitle.setText(heading)
        binding.fJobJoiningOffice.tvTitle.setText(context.getString(R.string.office))
        binding.fJobJoiningDesignation.tvTitle.setText(context.getString(R.string.designation))
        binding.fJobJoiningAdditionalDesignation.tvTitle.setText(context.getString(R.string.additional_designation))
        // binding.fJobJoiningDepartment.tvTitle.setText(context.getString(R.string.depertment))
        binding.fJobJoiningJobType.tvTitle.setText(context.getString(R.string.job_type))
        binding.fJobJoiningJoiningDate.tvTitle.setText(context.getString(R.string.joning_date))
        binding.fJobJoiningConfirmationDate.tvTitle.setText(context.getString(R.string.confirmation_date))
        binding.fJobJoiningPensionDate.tvTitle.setText(context.getString(R.string.pension))
        binding.fJobJoiningClass.tvTitle.setText(context.getString(R.string._class))
        binding.fJobJoiningGrade.tvTitle.setText(context.getString(R.string.pay_scale_grade))
        binding.fJobJoiningPayScale.tvTitle.setText(context.getString(R.string.basic_pay))
        binding.fJobJoiningPrlDate.tvTitle.setText(context.getString(R.string.prl_date))
        binding.fJobJoiningCurrentJob.tvTitle.setText(context.getString(R.string.current_job))
        binding.tvLanguageCertificate.setText(context.getString(R.string.certificate))

        context.let {
            Glide.with(it).applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_person_24)
            ).load(employee?.photo)
                .into(binding.ivLanguageCertificate)
        }

        jobjoinings.pay_scale?.let { binding.fJobJoiningPayScale.tvText.setText(it) }

        if (employeeProfileData?.employee?.designation_id == jobjoinings?.designation_id &&
            employeeProfileData?.employee?.office_id == jobjoinings?.office_id
        ) {
            binding?.fJobJoiningCurrentJob?.tvText.setText(context.getString(R.string.yes))
        } else {
            binding?.fJobJoiningCurrentJob?.tvText.setText(context.getString(R.string.no))
        }

        if (!jobjoinings.isPendingData) {
            if (preparence.getLanguage()
                    .equals("en")
            ) {
                jobjoinings.office?.office_name?.let { binding.fJobJoiningOffice.tvText.setText(it) }
                jobjoinings.designation?.name?.let {
                    binding.fJobJoiningDesignation.tvText.setText(
                        it
                    )
                }
                jobjoinings.additional_designation?.name?.let {
                    binding.fJobJoiningAdditionalDesignation.tvText.setText(
                        it
                    )
                }
                //  jobjoinings.department?.name?.let { binding.fJobJoiningDepartment.tvText.setText(it) }
                jobjoinings.job_type?.name?.let { binding.fJobJoiningJobType.tvText.setText(it) }
                jobjoinings.joining_date?.let {
                    binding.fJobJoiningJoiningDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                jobjoinings.confirmation_date?.let {
                    binding.fJobJoiningConfirmationDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                jobjoinings.pension_date?.let {
                    binding.fJobJoiningPensionDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                jobjoinings.employee_class?.name?.let { binding.fJobJoiningClass.tvText.setText(it) }
                jobjoinings.grade?.name?.let { binding.fJobJoiningGrade.tvText.setText(it) }
                jobjoinings.prl_date?.let {
                    binding.fJobJoiningPrlDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(
                            it
                        )
                    )
                }


                // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }
            } else {
                jobjoinings.office?.office_name_bn?.let {
                    binding.fJobJoiningOffice.tvText.setText(
                        it
                    )
                }
                jobjoinings.designation?.name_bn?.let {
                    binding.fJobJoiningDesignation.tvText.setText(
                        it
                    )
                }
                jobjoinings.additional_designation?.name_bn?.let {
                    binding.fJobJoiningAdditionalDesignation.tvText.setText(
                        it
                    )
                }
                // jobjoinings.department?.name_bn?.let { binding.fJobJoiningDepartment.tvText.setText(it) }
                jobjoinings.job_type?.name_bn?.let { binding.fJobJoiningJobType.tvText.setText(it) }
                jobjoinings.joining_date?.let {
                    binding.fJobJoiningJoiningDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                jobjoinings.confirmation_date?.let {
                    binding.fJobJoiningConfirmationDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }

                jobjoinings.pension_date?.let {
                    binding.fJobJoiningPensionDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                jobjoinings.employee_class?.name_bn?.let {
                    binding.fJobJoiningClass.tvText.setText(
                        it
                    )
                }
                jobjoinings.grade?.name_bn?.let { binding.fJobJoiningGrade.tvText.setText(it) }
                jobjoinings.prl_date?.let {
                    binding.fJobJoiningPrlDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(
                            it
                        )
                    )
                }
                // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }
            }
        } else {

            binding.hJobJoiningInformation.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hJobJoiningInformation.tvEdit.visibility = View.GONE

            binding.fJobJoiningOffice.tvText.setText("${jobjoinings.office_id}")

            binding.fJobJoiningDesignation.tvText.setText("${jobjoinings.designation_id}")


            binding.fJobJoiningAdditionalDesignation.tvText.setText(
                "${jobjoinings.additional_designation_id}"
            )

            //  jobjoinings.department?.name?.let { binding.fJobJoiningDepartment.tvText.setText(it) }
            binding.fJobJoiningJobType.tvText.setText("${jobjoinings.job_type_id}")

            jobjoinings.joining_date?.let {
                binding.fJobJoiningJoiningDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }
            jobjoinings.confirmation_date?.let {
                binding.fJobJoiningConfirmationDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }
            jobjoinings.pension_date?.let {
                binding.fJobJoiningPensionDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }
            binding.fJobJoiningClass.tvText.setText("${jobjoinings.employee_class_id}")

            binding.fJobJoiningGrade.tvText.setText("${jobjoinings.grade_id}")

            jobjoinings.prl_date?.let {
                binding.fJobJoiningPrlDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(
                        it
                    )
                )
            }


            // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }

        }


    }

    fun bindChildrenData(
        binding: ModelEmployeeInfoBinding,
        childs: Employee.Childs,
        context: Context,
        heading: String
    ) {
        binding.hChildren.tvTitle.setText(heading)
        binding.fChildrenNameOChEn.tvTitle.setText(context.getString(R.string.name_child))
        binding.fChildrenNameOChBn.tvTitle.setText(context.getString(R.string.name_child_bn))
        binding.fChildrenDOB.tvTitle.setText(context.getString(R.string.birth))
        binding.fChildrenBirthCertificateNo.tvTitle.setText(context.getString(R.string.birth_certificate_no))
        binding.fChildrenNidNo.tvTitle.setText(context.getString(R.string.nid_no))
        binding.fChildrenPassportNo.tvTitle.setText(context.getString(R.string.passport_no))
        binding.fChildrenGenderOrSex.tvTitle.setText(context.getString(R.string.gender))

        binding.fChildNid.tvTitle.text = context.getString(R.string.nid_attachment)
        binding.fChildPassport.tvTitle.text = context.getString(R.string.passport_attachment)
        binding.fChildbirthCertificate.tvTitle.text =
            context.getString(R.string.birth_certificate_attachment)


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


        if (preparence.getLanguage()
                .equals("en")
        ) {
            childs.name_of_children?.let { binding.fChildrenNameOChEn.tvText.setText(it) }
            childs.name_of_children_bn?.let { binding.fChildrenNameOChBn.tvText.setText(it) }
            childs.date_of_birth?.let {
                binding.fChildrenDOB.tvText.setText(
                    DateConverter.changeDateFormateForShowing(
                        it
                    )
                )
            }
            childs.gender?.name?.let { binding.fChildrenGenderOrSex.tvText.setText(it) }
            childs.birth_certificate?.let { binding.fChildrenBirthCertificateNo.tvText.setText(it) }
            childs.nid?.let { binding.fChildrenNidNo.tvText.setText(it) }
            childs.passport?.let { binding.fChildrenPassportNo.tvText.setText(it) }
        } else {
            childs.name_of_children?.let { binding.fChildrenNameOChEn.tvText.setText(it) }
            childs.name_of_children_bn?.let { binding.fChildrenNameOChBn.tvText.setText(it) }
            childs.date_of_birth?.let {
                binding.fChildrenDOB.tvText.setText(
                    DateConverter.changeDateFormateForShowing(
                        it
                    )
                )
            }
            childs.gender?.name_bn?.let { binding.fChildrenGenderOrSex.tvText.setText(it) }
            childs.birth_certificate?.let { binding.fChildrenBirthCertificateNo.tvText.setText(it) }
            childs.nid?.let { binding.fChildrenNidNo.tvText.setText(it) }
            childs.passport?.let { binding.fChildrenPassportNo.tvText.setText(it) }
        }
    }


    fun bindLanguageData(
        binding: ModelEmployeeInfoBinding,
        languages: Employee.Languages,
        context: Context,
        heading: String
    ) {
        binding.llLanguageCertificate.visibility = View.GONE
        binding.hLanguage.tvTitle.setText(heading)
        binding.fLanguageNOLanguage.tvTitle.setText(context.getString(R.string.name_language))
        binding.fLanguageNOLanguageBn.tvTitle.setText(context.getString(R.string.name_language_bn))
        binding.fLanguageNOInstituteBn.tvTitle.setText(context.getString(R.string.name_institute_bn))
        binding.fLanguageNOInstitute.tvTitle.setText(context.getString(R.string.name_institute))
        binding.fLanguageExperienceLevel.tvTitle.setText(context.getString(R.string.exp_level))
        binding.fLanguageCertificationDate.tvTitle.setText(context.getString(R.string.certification_date))

        binding?.tvLanguageCertificate?.setText(context.getString(R.string.certificate))


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

        context?.let {
            binding?.ivLanguageCertificate?.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_image_24)
                ).load(languages?.certificate)
                    .into(it1)
            }
        }
        if (!languages.isPendingData) {
            if (preparence.getLanguage()
                    .equals("en")
            ) {
                languages.name_of_language?.let { binding.fLanguageNOLanguage.tvText.setText(it) }
                languages.name_of_language_bn?.let { binding.fLanguageNOLanguageBn.tvText.setText(it) }
                languages.name_of_institute?.let { binding.fLanguageNOInstitute.tvText.setText(it) }
                languages.name_of_institute_bn?.let {
                    binding.fLanguageNOInstituteBn.tvText.setText(
                        it
                    )
                }
                languages.expertise_level?.let { binding.fLanguageExperienceLevel.tvText.setText(it) }
                languages.certification_date?.let {
                    binding.fLanguageCertificationDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }

            } else {
                languages.name_of_language?.let { binding.fLanguageNOLanguage.tvText.setText(it) }
                languages.name_of_language_bn?.let { binding.fLanguageNOLanguageBn.tvText.setText(it) }
                languages.name_of_institute?.let { binding.fLanguageNOInstitute.tvText.setText(it) }
                languages.name_of_institute_bn?.let {
                    binding.fLanguageNOInstituteBn.tvText.setText(
                        it
                    )
                }
                languages.expertise_level?.let {
                    when (it.toLowerCase()) {
                        "medium" -> binding.fLanguageExperienceLevel.tvText.setText("মধ্যম")
                        "expert" -> binding.fLanguageExperienceLevel.tvText.setText("বিশেষজ্ঞ")
                        "average" -> binding.fLanguageExperienceLevel.tvText.setText("গড়")
                        else -> binding.fLanguageExperienceLevel.tvText.setText("কম")
                    }
                }
                languages.certification_date?.let {
                    binding.fLanguageCertificationDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
            }
        } else {
            binding.hLanguage.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hLanguage.tvEdit.visibility = View.GONE


            languages.name_of_language?.let { binding.fLanguageNOLanguage.tvText.setText(it) }
            languages.name_of_language_bn?.let { binding.fLanguageNOLanguageBn.tvText.setText(it) }
            languages.name_of_institute?.let { binding.fLanguageNOInstitute.tvText.setText(it) }
            languages.name_of_institute_bn?.let { binding.fLanguageNOInstituteBn.tvText.setText(it) }
            languages.expertise_level?.let { binding.fLanguageExperienceLevel.tvText.setText(it) }
            languages.certification_date?.let {
                binding.fLanguageCertificationDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }

        }


    }


    fun bindLocaltrainingData(
        binding: ModelEmployeeInfoBinding,
        localTrainings: Employee.LocalTrainings,
        context: Context,
        heading: String
    ) {
        binding.hLocaltraining.tvTitle.setText(heading)
        binding.fLocalTrainingCountry.llBody.visibility = View.GONE
        binding.llTrainingCertification.visibility = View.GONE

        binding.fLocalTrainingCatName.tvTitle.setText(context.getString(R.string.training_category))
        binding.fLocalTrainingCourseT.tvTitle.setText(context.getString(R.string.course_title))
        binding.fLocalTrainingCourseTBn.tvTitle.setText(context.getString(R.string.course_title_bn))
        binding.fLocalTrainingNOInst.tvTitle.setText(context.getString(R.string.name_institute))
        binding.fLocalTrainingNOInstBn.tvTitle.setText(context.getString(R.string.name_institute_bn))
        binding.fLocalTrainingLocation.tvTitle.setText(context.getString(R.string.location))
        binding.fLocalTrainingLocationBn.tvTitle.setText(context.getString(R.string.location_bn))
        binding.fLocalTrainingFromDate.tvTitle.setText(context.getString(R.string.from_date))
        binding.fLocalTrainingToDate.tvTitle.setText(context.getString(R.string.to_date))

        // decide what to show in document
        ConvertNumber.setIconOnTextView(
            binding.fLocalTrainingDocument.icon,
            binding.fLocalTrainingDocument.tvText,
            localTrainings.local_training_document_path,
            context
        )


        binding.fLocalTrainingCatName.tvText.text = "${localTrainings.hrm_training_category?.name}"


//
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
            ConvertNumber.viewFileInShareIntent(
                context,
                localTrainings.local_training_document_path
            )
        }

        binding.tvTrainingTitle.text = context.getString(R.string.certificate)

        context.let {
            binding.ivTraining.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_image_24)
                ).load(localTrainings?.certificate)
                    .into(it1)
            }
        }


        if (!localTrainings.isPendingData) {

            if (preparence.getLanguage()
                    .equals("en")
            ) {
                localTrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.setText(it) }
                localTrainings.course_title_bn?.let {
                    binding.fLocalTrainingCourseTBn.tvText.setText(
                        it
                    )
                }
                localTrainings.name_of_institute?.let {
                    binding.fLocalTrainingNOInst.tvText.setText(
                        it
                    )
                }
                localTrainings.name_of_institute_bn?.let {
                    binding.fLocalTrainingNOInstBn.tvText.setText(
                        it
                    )
                }
                localTrainings.location?.let { binding.fLocalTrainingLocation.tvText.setText(it) }
                localTrainings.location_bn?.let { binding.fLocalTrainingLocationBn.tvText.setText(it) }

                localTrainings.from_date?.let {
                    binding.fLocalTrainingFromDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                localTrainings.to_date?.let {
                    binding.fLocalTrainingToDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(
                            it
                        )
                    )
                }


            } else {
                localTrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.setText(it) }
                localTrainings.course_title_bn?.let {
                    binding.fLocalTrainingCourseTBn.tvText.setText(
                        it
                    )
                }
                localTrainings.name_of_institute?.let {
                    binding.fLocalTrainingNOInst.tvText.setText(
                        it
                    )
                }
                localTrainings.name_of_institute_bn?.let {
                    binding.fLocalTrainingNOInstBn.tvText.setText(
                        it
                    )
                }
                localTrainings.location?.let { binding.fLocalTrainingLocation.tvText.setText(it) }
                localTrainings.location_bn?.let { binding.fLocalTrainingLocationBn.tvText.setText(it) }

                localTrainings.from_date?.let {
                    binding.fLocalTrainingFromDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                localTrainings.to_date?.let {
                    binding.fLocalTrainingToDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(
                            it
                        )
                    )
                }

            }

        } else {
            binding.hLocaltraining.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hLocaltraining.tvEdit.visibility = View.GONE

            localTrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.setText(it) }
            localTrainings.course_title_bn?.let { binding.fLocalTrainingCourseTBn.tvText.setText(it) }
            localTrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.setText(it) }
            localTrainings.name_of_institute_bn?.let {
                binding.fLocalTrainingNOInstBn.tvText.setText(
                    it
                )
            }
            localTrainings.location?.let { binding.fLocalTrainingLocation.tvText.setText(it) }
            localTrainings.location_bn?.let { binding.fLocalTrainingLocationBn.tvText.setText(it) }

            localTrainings.from_date?.let {
                binding.fLocalTrainingFromDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }
            localTrainings.to_date?.let {
                binding.fLocalTrainingToDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(
                        it
                    )
                )
            }


        }


    }

    fun bindForeignTrainingData(
        binding: ModelEmployeeInfoBinding,
        foreigntrainings: Employee.Foreigntrainings,
        context: Context,
        heading: String
    ) {
        binding.hLocaltraining.tvTitle.setText(heading)
        binding.fLocalTrainingLocation.llBody.visibility = View.GONE
        binding.fLocalTrainingLocationBn.llBody.visibility = View.GONE
        binding.llTrainingCertification.visibility = View.GONE

        binding.fLocalTrainingCatName.tvTitle.text = context.getString(R.string.training_category)
        binding.fLocalTrainingCourseT.tvTitle.text = context.getString(R.string.course_title)
        binding.fLocalTrainingCourseTBn.tvTitle.text = context.getString(R.string.course_title_bn)
        binding.fLocalTrainingNOInst.tvTitle.text = context.getString(R.string.name_institute)
        binding.fLocalTrainingNOInstBn.tvTitle.text = context.getString(R.string.name_institute_bn)
        binding.fLocalTrainingFromDate.tvTitle.setText(context.getString(R.string.from_date))
        binding.fLocalTrainingToDate.tvTitle.text = context.getString(R.string.to_date)

        binding.fLocalTrainingCountry.tvTitle.setText(context.getString(R.string.country))
        binding.tvTrainingTitle.setText(context.getString(R.string.certificate))

        binding.fLocalTrainingCatName.tvText.text =
            "${foreigntrainings.hrm_training_category?.name}"


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

        context?.let {
            binding?.ivTraining?.let { it1 ->
                Glide.with(it).applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_image_24)
                ).load(foreigntrainings?.certificate)
                    .into(it1)
            }
        }

        if (!foreigntrainings.isPendingData) {

            if (preparence.getLanguage()
                    .equals("en")
            ) {
                foreigntrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.setText(it) }
                foreigntrainings.course_title_bn?.let {
                    binding.fLocalTrainingCourseTBn.tvText.setText(
                        it
                    )
                }
                foreigntrainings.name_of_institute?.let {
                    binding.fLocalTrainingNOInst.tvText.setText(
                        it
                    )
                }
                foreigntrainings.name_of_institute_bn?.let {
                    binding.fLocalTrainingNOInstBn.tvText.setText(
                        it
                    )
                }
                foreigntrainings.from_date?.let {
                    binding.fLocalTrainingFromDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                foreigntrainings.to_date?.let {
                    binding.fLocalTrainingToDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }


                foreigntrainings.country?.name?.let {
                    binding.fLocalTrainingCountry.tvText.setText(
                        it
                    )
                }


            } else {
                foreigntrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.setText(it) }
                foreigntrainings.course_title_bn?.let {
                    binding.fLocalTrainingCourseTBn.tvText.setText(
                        it
                    )
                }
                foreigntrainings.name_of_institute?.let {
                    binding.fLocalTrainingNOInst.tvText.setText(
                        it
                    )
                }
                foreigntrainings.name_of_institute_bn?.let {
                    binding.fLocalTrainingNOInstBn.tvText.setText(
                        it
                    )
                }
                foreigntrainings.from_date?.let {
                    binding.fLocalTrainingFromDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                foreigntrainings.to_date?.let {
                    binding.fLocalTrainingToDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }


                foreigntrainings.country?.name_bn?.let {
                    binding.fLocalTrainingCountry.tvText.setText(
                        it
                    )
                }

            }

        } else {
            binding.hLocaltraining.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hLocaltraining.tvEdit.visibility = View.GONE

            foreigntrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.setText(it) }
            foreigntrainings.course_title_bn?.let {
                binding.fLocalTrainingCourseTBn.tvText.setText(
                    it
                )
            }
            foreigntrainings.name_of_institute?.let {
                binding.fLocalTrainingNOInst.tvText.setText(
                    it
                )
            }
            foreigntrainings.name_of_institute_bn?.let {
                binding.fLocalTrainingNOInstBn.tvText.setText(
                    it
                )
            }
            foreigntrainings.from_date?.let {
                binding.fLocalTrainingFromDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }
            foreigntrainings.to_date?.let {
                binding.fLocalTrainingToDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }


            binding.fLocalTrainingCountry.tvText.setText(
                "${foreigntrainings.country_id}"
            )

        }

    }


    fun bindOfficialResidentialInfoData(
        binding: ModelEmployeeInfoBinding,
        officialResidentials: Employee.OfficialResidentials,
        context: Context,
        heading: String
    ) {
        binding.fORInfoDesignation.llBody.visibility = View.GONE
        binding.hOfficialResidentialInfo.tvTitle.setText(heading)
        binding.fORInfoMemoNo.tvTitle.setText(context.getString(R.string.memo_no))
        binding.fORInfoMemoNoBn.tvTitle.setText(context.getString(R.string.memo_no_bn))
        binding.fORInfoMemoDate.tvTitle.setText(context.getString(R.string.memo_date))
        binding.fORInfoOfficeZone.tvTitle.setText(context.getString(R.string.office_zoon))
        binding.fORInfoDesignation.tvTitle.setText(context.getString(R.string.designation))
        binding.fORInfoDivision.tvTitle.setText(context.getString(R.string.division))
        binding.fORInfoDistrict.tvTitle.setText(context.getString(R.string.district))
        binding.fORInfoUpazila.tvTitle.setText(context.getString(R.string.upazila))
        binding.fORInfoOfficeZone.tvTitle.setText(context.getString(R.string.office_zoon))
        binding.fORInfoLocation.tvTitle.setText(context.getString(R.string.address))
        binding.fORInfoQuarterName.tvTitle.setText(context.getString(R.string.quarter_name))
        binding.fORInfoFlatNo.tvTitle.setText(context.getString(R.string.flat_no))
        binding.fORInfoStatus.tvTitle.setText(context.getString(R.string.status))

        if (!officialResidentials.isPendingData) {

            if (preparence.getLanguage()
                    .equals("en")
            ) {
                officialResidentials.memo_no?.let { binding.fORInfoMemoNo.tvText.setText(it) }
                officialResidentials.memo_no_bn?.let { binding.fORInfoMemoNoBn.tvText.setText(it) }
                officialResidentials.memo_date?.let {
                    binding.fORInfoMemoDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                officialResidentials.office_zone?.let { binding.fORInfoOfficeZone.tvText.setText(it) }

                officialResidentials.designation?.name?.let {
                    binding.fORInfoDesignation.tvText.setText(
                        it
                    )
                }
                officialResidentials.division?.name?.let { binding.fORInfoDivision.tvText.setText(it) }
                officialResidentials.district?.name?.let { binding.fORInfoDistrict.tvText.setText(it) }
                officialResidentials.upazila?.name?.let { binding.fORInfoUpazila.tvText.setText(it) }
                officialResidentials.location?.let {
                    binding.fORInfoLocation.tvText.setText(
                        it
                    )
                }

                officialResidentials.quarter_name?.let {
                    binding.fORInfoQuarterName.tvText.setText(
                        it
                    )
                }

                officialResidentials.flat_no_flat_type?.let {
                    binding.fORInfoFlatNo.tvText.setText(
                        it
                    )
                }

                officialResidentials.status?.let {
                    if (it == 1) {
                        binding.fORInfoStatus.tvText.setText(
                            EditOfficialResidentialIInfo().getStatusList().get(0).name
                        )
                    } else {
                        binding.fORInfoStatus.tvText.setText(
                            EditOfficialResidentialIInfo().getStatusList().get(1).name
                        )
                    }

                }


            } else {
                officialResidentials.memo_no?.let { binding.fORInfoMemoNo.tvText.setText(it) }
                officialResidentials.memo_no_bn?.let { binding.fORInfoMemoNoBn.tvText.setText(it) }
                officialResidentials.memo_date?.let {
                    binding.fORInfoMemoDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                officialResidentials.office_zone?.let { binding.fORInfoOfficeZone.tvText.setText(it) }

                officialResidentials.designation?.name_bn?.let {
                    binding.fORInfoDesignation.tvText.setText(
                        it
                    )
                }
                officialResidentials.division?.name_bn?.let {
                    binding.fORInfoDivision.tvText.setText(
                        it
                    )
                }
                officialResidentials.district?.name_bn?.let {
                    binding.fORInfoDistrict.tvText.setText(
                        it
                    )
                }
                officialResidentials.upazila?.name_bn?.let {
                    binding.fORInfoUpazila.tvText.setText(
                        it
                    )
                }
                officialResidentials.location?.let {
                    binding.fORInfoLocation.tvText.setText(
                        it
                    )
                }

                officialResidentials.quarter_name?.let {
                    binding.fORInfoQuarterName.tvText.setText(
                        it
                    )
                }

                officialResidentials.flat_no_flat_type?.let {
                    binding.fORInfoFlatNo.tvText.setText(
                        it
                    )
                }

                officialResidentials.status?.let {
                    if (it == 1) {
                        binding.fORInfoStatus.tvText.setText(
                            EditOfficialResidentialIInfo().getStatusList().get(0).name_bn
                        )
                    } else {
                        binding.fORInfoStatus.tvText.setText(
                            EditOfficialResidentialIInfo().getStatusList().get(1).name_bn
                        )
                    }

                }

            }

        } else {
            binding.hOfficialResidentialInfo.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hOfficialResidentialInfo.tvEdit.visibility = View.GONE

            officialResidentials.memo_no?.let { binding.fORInfoMemoNo.tvText.setText(it) }
            officialResidentials.memo_no_bn?.let { binding.fORInfoMemoNoBn.tvText.setText(it) }
            officialResidentials.memo_date?.let {
                binding.fORInfoMemoDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }
            officialResidentials.office_zone?.let { binding.fORInfoOfficeZone.tvText.setText(it) }

            binding.fORInfoDesignation.tvText.setText(
                "${officialResidentials.designation_id}"
            )

            binding.fORInfoDivision.tvText.setText(
                "${officialResidentials.division_id}"
            )
            binding.fORInfoDistrict.tvText.setText(
                "${officialResidentials.district_id}"
            )
            binding.fORInfoUpazila.tvText.setText(
                "${officialResidentials.upazila_id}"
            )
            officialResidentials.location?.let {
                binding.fORInfoLocation.tvText.setText(
                    it
                )
            }

            officialResidentials.quarter_name?.let {
                binding.fORInfoQuarterName.tvText.setText(
                    it
                )
            }

            officialResidentials.flat_no_flat_type?.let {
                binding.fORInfoFlatNo.tvText.setText(
                    it
                )
            }

            officialResidentials.status?.let {
                if (it == 1) {
                    binding.fORInfoStatus.tvText.setText(
                        EditOfficialResidentialIInfo().getStatusList().get(0).name
                    )
                } else {
                    binding.fORInfoStatus.tvText.setText(
                        EditOfficialResidentialIInfo().getStatusList().get(1).name
                    )
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
        binding.hForeignTravelInfo.tvTitle.setText(heading)
        binding.fForeignTravelCountry.tvTitle.setText(context.getString(R.string.country))
        binding.fForeignTravelPurpose.tvTitle.setText(context.getString(R.string.purpose))
        binding.fForeignTravelPurposeBn.tvTitle.setText(context.getString(R.string.purpose_bangla))
        binding.fForeignTravelDetailsEn.tvTitle.setText(context.getString(R.string.details_en))
        binding.fForeignTravelDetailsBn.tvTitle.setText(context.getString(R.string.details_bn))
        binding.fForeignTravelFromDate.tvTitle.setText(context.getString(R.string.from_date))
        binding.fForeignTravelToDate.tvTitle.setText(context.getString(R.string.to_date))


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

            if (preparence.getLanguage()
                    .equals("en")
            ) {
                foreignTravels.country?.name?.let { binding.fForeignTravelCountry.tvText.setText(it) }
                foreignTravels.purpose?.let { binding.fForeignTravelPurpose.tvText.setText(it) }
                foreignTravels.purpose_bn?.let { binding.fForeignTravelPurposeBn.tvText.setText(it) }
                foreignTravels.details?.let { binding.fForeignTravelDetailsEn.tvText.setText(it) }
                foreignTravels.details_bn?.let { binding.fForeignTravelDetailsBn.tvText.setText(it) }
                foreignTravels.from_date?.let {
                    binding.fForeignTravelFromDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                foreignTravels.to_date?.let {
                    binding.fForeignTravelToDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(
                            it
                        )
                    )
                }


            } else {
                foreignTravels.country?.name_bn?.let {
                    binding.fForeignTravelCountry.tvText.setText(
                        it
                    )
                }
                foreignTravels.purpose_bn?.let { binding.fForeignTravelPurpose.tvText.setText(it) }
                //  foreignTravels.purpose_bn?.let { binding.fForeignTravelPurposeBn.tvText.setText(it) }
                foreignTravels.details?.let { binding.fForeignTravelDetailsEn.tvText.setText(it) }
                foreignTravels.details_bn?.let { binding.fForeignTravelDetailsBn.tvText.setText(it) }
                foreignTravels.from_date?.let {
                    binding.fForeignTravelFromDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(it)
                    )
                }
                foreignTravels.to_date?.let {
                    binding.fForeignTravelToDate.tvText.setText(
                        DateConverter.changeDateFormateForShowing(
                            it
                        )
                    )
                }

            }
        } else {
            binding.hForeignTravelInfo.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hForeignTravelInfo.tvEdit.visibility = View.GONE

            binding.fForeignTravelCountry.tvText.setText("${foreignTravels.country_id}")
            foreignTravels.purpose?.let { binding.fForeignTravelPurpose.tvText.setText(it) }
            foreignTravels.purpose_bn?.let { binding.fForeignTravelPurposeBn.tvText.setText(it) }
            foreignTravels.details?.let { binding.fForeignTravelDetailsEn.tvText.setText(it) }
            foreignTravels.details_bn?.let { binding.fForeignTravelDetailsBn.tvText.setText(it) }
            foreignTravels.from_date?.let {
                binding.fForeignTravelFromDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }
            foreignTravels.to_date?.let {
                binding.fForeignTravelToDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(
                        it
                    )
                )
            }
        }

    }

    fun bindAdditionalQualificationData(
        binding: ModelEmployeeInfoBinding,
        additionalQualifications: Employee.AdditionalQualifications,
        context: Context,
        heading: String
    ) {
        binding.hAdditionalQualification.tvTitle.setText(heading)
        binding.fAQName.tvTitle.setText(context.getString(R.string.qualification_name))
        binding.fAQNameBn.tvTitle.setText(context.getString(R.string.qualification_name_bangla))
        binding.fAQDetails.tvTitle.setText(context.getString(R.string.qualification_details))
        binding.fAQDetailsBn.tvTitle.setText(context.getString(R.string.qualification_details_bangla))


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


        if (preparence.getLanguage()
                .equals("en")
        ) {
            additionalQualifications.qualification_name?.let { binding.fAQName.tvText.setText(it) }
            additionalQualifications.qualification_name_bn?.let {
                binding.fAQNameBn.tvText.setText(
                    it
                )
            }
            additionalQualifications.qualification_details?.let {
                binding.fAQDetails.tvText.setText(
                    it
                )
            }
            additionalQualifications.qualification_details_bn?.let {
                binding.fAQDetailsBn.tvText.setText(
                    it
                )
            }


        } else {
            additionalQualifications.qualification_name?.let { binding.fAQName.tvText.setText(it) }
            additionalQualifications.qualification_name_bn?.let {
                binding.fAQNameBn.tvText.setText(
                    it
                )
            }
            additionalQualifications.qualification_details?.let {
                binding.fAQDetails.tvText.setText(
                    it
                )
            }
            additionalQualifications.qualification_details_bn?.let {
                binding.fAQDetailsBn.tvText.setText(
                    it
                )
            }
        }
    }

    fun bindPublicationsData(
        binding: ModelEmployeeInfoBinding,
        qualifications: Employee.Publications,
        context: Context,
        heading: String
    ) {
        binding.hPublication.tvTitle.setText(heading)
        binding.fPublicationType.tvTitle.setText(context.getString(R.string.type_of_pub))
        binding.fPublicationDetails.tvTitle.setText(context.getString(R.string.public_details))
        binding.fPublicationDetailsBn.tvTitle.setText(context.getString(R.string.public_details_bn))
        binding.fPublicationNameEn.tvTitle.setText(context.getString(R.string.pub_name))
        binding.fPublicationNameBn.tvTitle.setText(context.getString(R.string.pub_name_bn))


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


        if (preparence.getLanguage()
                .equals("en")
        ) {
            qualifications.type?.name?.let {
                binding.fPublicationType.tvText.setText(
                    it
                )
            }
            qualifications.publication_details?.let {
                binding.fPublicationDetails.tvText.setText(
                    it
                )
            }
            qualifications.publication_details_bn?.let {
                binding.fPublicationDetailsBn.tvText.setText(
                    it
                )
            }
            qualifications.publication_name?.let {
                binding.fPublicationNameEn.tvText.setText(
                    it
                )
            }
            qualifications.publication_name_bn?.let {
                binding.fPublicationNameBn.tvText.setText(
                    it
                )
            }


        } else {
            qualifications.publication_details_bn?.let {
                binding.fPublicationDetailsBn.tvText.setText(
                    it
                )
            }
            qualifications.type?.name_bn?.let {
                binding.fPublicationType.tvText.setText(
                    it
                )
            }
            qualifications.publication_details_bn?.let {
                binding.fPublicationDetails.tvText.setText(
                    it
                )
            }
            qualifications.publication_name?.let {
                binding.fPublicationNameEn.tvText.setText(
                    it
                )
            }
            qualifications.publication_name_bn?.let {
                binding.fPublicationNameBn.tvText.setText(
                    it
                )
            }
        }
    }

    fun bindHonoursAndAwardData(
        binding: ModelEmployeeInfoBinding,
        honoursAwards: Employee.HonoursAwards,
        context: Context,
        heading: String
    ) {
        binding.hHonoursAndAward.tvTitle.setText(heading)
        binding.fAwardTitle.tvTitle.setText(context.getString(R.string.aware_t))
        binding.fAwardTitleBn.tvTitle.setText(context.getString(R.string.aware_t_bn))
        binding.fAwardDetailsDetails.tvTitle.setText(context.getString(R.string.award_details))
        binding.fAwardDetailsDetailsBn.tvTitle.setText(context.getString(R.string.award_details_bn))
        binding.fAwardDate.tvTitle.setText(context.getString(R.string.award_date))

        ConvertNumber.setIconOnTextView(
            binding.fAwardAttachment.icon,
            binding.fAwardAttachment.tvText,
            honoursAwards.honours_awards_document_path,
            context
        )

        binding.fAwardAttachment.tvText.setOnClickListener {
            ConvertNumber.viewFileInShareIntent(
                context,
                honoursAwards.honours_awards_document_path
            )

        }

        if (preparence.getLanguage()
                .equals("en")
        ) {
            honoursAwards.award_title?.let {
                binding.fAwardTitle.tvText.setText(
                    it
                )
            }
            honoursAwards.award_title_bn?.let {
                binding.fAwardTitleBn.tvText.setText(
                    it
                )
            }
            honoursAwards.award_details?.let {
                binding.fAwardDetailsDetails.tvText.setText(
                    it
                )
            }
            honoursAwards.award_details_bn?.let {
                binding.fAwardDetailsDetailsBn.tvText.setText(
                    it
                )
            }
            honoursAwards.award_date?.let {
                binding.fAwardDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }


        } else {


            honoursAwards.award_title?.let {
                binding.fAwardTitle.tvText.setText(
                    it
                )
            }
            honoursAwards.award_title_bn?.let {
                binding.fAwardTitleBn.tvText.setText(
                    it
                )
            }
            honoursAwards.award_details?.let {
                binding.fAwardDetailsDetails.tvText.setText(
                    it
                )
            }
            honoursAwards.award_details_bn?.let {
                binding.fAwardDetailsDetailsBn.tvText.setText(
                    it
                )
            }
            honoursAwards.award_date?.let {
                binding.fAwardDate.tvText.setText(
                    DateConverter.changeDateFormateForShowing(it)
                )
            }

        }
    }

    fun bindPostingRecordData(
        binding: ModelEmployeeInfoBinding,
        postingRecords: Employee.PostingRecords,
        context: Context,
        heading: String
    ) {
        binding.hPostingRecord.tvTitle.setText(heading)
        binding.fPostingRType.tvTitle.setText(context.getString(R.string.transfer_type))
        binding.fPostingRFrom.tvTitle.setText(context.getString(R.string.transfer_from))
        binding.fPostingRTo.tvTitle.setText(context.getString(R.string.transfer_to))
        binding.fPostingREffectiveDate.tvTitle.setText(context.getString(R.string.eff_date))

        if (preparence.getLanguage()
                .equals("en")
        ) {
            postingRecords.transfer_type?.name?.let {
                binding.fPostingRType.tvText.setText(
                    it
                )
            }
            postingRecords.transfer_from?.office_name?.let {
                binding.fPostingRFrom.tvText.setText(
                    it
                )
            }
            postingRecords.transfer_to?.office_name?.let {
                binding.fPostingRTo.tvText.setText(
                    it
                )
            }

            postingRecords.effective_date?.let {
                binding.fPostingREffectiveDate.tvText.setText(
                    it
                )
            }


        } else {
            postingRecords.transfer_type?.name_bn?.let {
                binding.fPostingRType.tvText.setText(
                    it
                )
            }
            postingRecords.transfer_from?.office_name_bn?.let {
                binding.fPostingRFrom.tvText.setText(
                    it
                )
            }
            postingRecords.transfer_to?.office_name_bn?.let {
                binding.fPostingRTo.tvText.setText(
                    it
                )
            }

            postingRecords.effective_date?.let {
                binding.fPostingREffectiveDate.tvText.setText(
                    it
                )
            }

        }
    }

    fun bindDisciplinaryActionsData(
        binding: ModelEmployeeInfoBinding,
        disciplinaryAction: Employee.DisciplinaryAction,
        context: Context,
        heading: String
    ) {
        binding.hDiscipilinaryAction.tvEdit.visibility = View.GONE
        binding.hDiscipilinaryAction.tvTitle.setText(heading)
        binding.fDACategory.tvTitle.setText(context.getString(R.string.disciplinary_action_category))
        binding.fDAPresentStatus.tvTitle.setText(context.getString(R.string.present_status))
        binding.fDAJud.tvTitle.setText(context.getString(R.string.judgment))
        binding.fDAJudBn.tvTitle.setText(context.getString(R.string.judgment_bn))
        binding.fDAFinalJud.tvTitle.setText(context.getString(R.string.final_judment))
        binding.fDAFinalJudBn.tvTitle.setText(context.getString(R.string.final_judment_bn))
        binding.fDADescription.tvTitle.setText(context.getString(R.string.disciplinary_action_details))
        binding.fDADescriptionBn.tvTitle.setText(context.getString(R.string.disciplinary_action_details_bn))
        binding.fDARemarks.tvTitle.setText(context.getString(R.string.remakrs))
        binding.fDARemarksBn.tvTitle.setText(context.getString(R.string.remakrs_bn))
        binding.fDADate.tvTitle.setText(context.getString(R.string.date))

//        if (preparence.getLanguage()
//                .equals("en")
//        ) {
        disciplinaryAction.disciplinaryActionCategory?.name?.let {
            binding.fDACategory.tvText.setText(
                it
            )
        }
        disciplinaryAction.present_status.let {
            binding.fDAPresentStatus.tvText.text =
                if (disciplinaryAction.present_status == 0) "" + context.getString(R.string.pending) else context.getText(
                    R.string.close
                )
        }
        disciplinaryAction.disciplinary_action_details?.let {
            binding.fDADescription.tvText.setText(
                it
            )
        }
        disciplinaryAction.disciplinary_action_details_bn?.let {
            binding.fDADescriptionBn.tvText.setText(
                it
            )
        }
        disciplinaryAction.judgment?.let { binding.fDAJud.tvText.setText(it) }
        disciplinaryAction.judgment_bn?.let { binding.fDAJudBn.tvText.setText(it) }
        disciplinaryAction.final_judgment?.let { binding.fDAFinalJud.tvText.setText(it) }
        disciplinaryAction.final_judgment_bn?.let { binding.fDAFinalJudBn.tvText.setText(it) }
        disciplinaryAction.remarks?.let { binding.fDARemarks.tvText.setText(it) }
        disciplinaryAction.remarks_bn?.let { binding.fDARemarksBn.tvText.setText(it) }
        disciplinaryAction.date?.let {
            binding.fDADate.tvText.setText(
                DateConverter.changeDateFormateForShowing(
                    it
                )
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
        binding.hReference.tvTitle.setText(heading)
        binding.fReferenceNameEn.tvTitle.setText(context.getString(R.string.name))
        binding.fReferenceNameBn.tvTitle.setText(context.getString(R.string.name_b))
        binding.fReferenceRelation.tvTitle.setText(context.getString(R.string.relation))
        binding.fReferenceRelationBn.tvTitle.setText(context.getString(R.string.relation_bn))
        binding.fReferenceAddress.tvTitle.setText(context.getString(R.string.address))
        binding.fReferenceAddressBn.tvTitle.setText(context.getString(R.string.address_bn))
        binding.fReferenceContactNo.tvTitle.setText(context.getString(R.string.contact))
        binding.fReferenceContactNoBn.tvTitle.setText(context.getString(R.string.contact_bn))

        if (preparence.getLanguage()
                .equals("en")
        ) {
            references.name?.let {
                binding.fReferenceNameEn.tvText.setText(
                    it
                )
            }
            references.name_bn?.let {
                binding.fReferenceNameBn.tvText.setText(
                    it
                )
            }
            references.relation?.let {
                binding.fReferenceRelation.tvText.setText(
                    it
                )
            }
            references.relation_bn?.let {
                binding.fReferenceRelationBn.tvText.setText(
                    it
                )
            }
            references.address?.let { binding.fReferenceAddress.tvText.setText(it) }
            references.address_bn?.let { binding.fReferenceAddressBn.tvText.setText(it) }
            references.contact_no?.let { binding.fReferenceContactNo.tvText.setText(it) }
            references.contact_no_bn?.let { binding.fReferenceContactNoBn.tvText.setText(it) }
        } else {
            references.name?.let {
                binding.fReferenceNameEn.tvText.setText(
                    it
                )
            }
            references.name_bn?.let {
                binding.fReferenceNameBn.tvText.setText(
                    it
                )
            }
            references.relation?.let {
                binding.fReferenceRelation.tvText.setText(
                    it
                )
            }
            references.relation_bn?.let {
                binding.fReferenceRelationBn.tvText.setText(
                    it
                )
            }
            references.address?.let { binding.fReferenceAddress.tvText.setText(it) }
            references.address_bn?.let { binding.fReferenceAddressBn.tvText.setText(it) }
            references.contact_no?.let { binding.fReferenceContactNo.tvText.setText(it) }
            references.contact_no_bn?.let { binding.fReferenceContactNoBn.tvText.setText(it) }
        }
    }

    fun bindPromotionData(
        binding: ModelEmployeeInfoBinding,
        promotions: Employee.Promotions,
        context: Context,
        heading: String
    ) {
        binding.hPromotion.tvEdit.visibility = View.GONE
        binding.hPromotion.tvTitle.setText(heading)
        binding.fPromotionMemoNo.tvTitle.setText(context.getString(R.string.memo_no))
        binding.fPromotionMemoDate.tvTitle.setText(context.getString(R.string.memo_date))
        binding.fPromotionOfficeZone.tvTitle.setText(context.getString(R.string.office_zoon))
        binding.fPromotionPreviousPost.tvTitle.setText(context.getString(R.string.previous_post))
        binding.fPromotionCurrentPosition.tvTitle.setText(context.getString(R.string.curent_post))

        if (preparence.getLanguage()
                .equals("en")
        ) {
            promotions.memo_no?.let {
                binding.fPromotionMemoNo.tvText.setText(
                    it
                )
            }
            promotions.memo_date?.let {
                binding.fPromotionMemoDate.tvText.setText(
                    it
                )
            }

            // promotions?.let { binding.fPromotionOfficeZone.tvText.setText(it) }
            promotions.previous_posts?.name?.let { binding.fPromotionPreviousPost.tvText.setText(it) }
            promotions.current_position?.name?.let {
                binding.fPromotionCurrentPosition.tvText.setText(
                    it
                )
            }
        } else {
            promotions.memo_no_bn?.let {
                binding.fPromotionMemoNo.tvText.setText(
                    it
                )
            }
            promotions.memo_date?.let {
                binding.fPromotionMemoDate.tvText.setText(
                    it
                )
            }

            //promotions.off?.let { binding.fPromotionOfficeZone.tvText.setText(it) }
            promotions.previous_posts?.name_bn?.let {
                binding.fPromotionPreviousPost.tvText.setText(
                    it
                )
            }
            promotions.current_position?.name_bn?.let {
                binding.fPromotionCurrentPosition.tvText.setText(
                    it
                )
            }
        }
    }

    fun bindQuotaData(
        binding: ModelEmployeeInfoBinding,
        quotas: Employee.EmployeeQuotas,
        context: Context,
        heading: String
    ) {
        binding.hQuota.tvTitle.setText(heading)
        binding.fQuotaName.tvTitle.setText(context.getString(R.string.quota_name))
        binding.fQuotaType.tvTitle.setText(context.getString(R.string.quota_type))
        binding.fQuotaDescription.tvTitle.setText(context.getString(R.string.description))
        binding.fQuotaDescriptionBn.tvTitle.setText(context.getString(R.string.description_bn))

        Log.d("TAGGGED", "updateJobjoiningInfo: ${quotas.quota_documnets}")

        val itemOnClick: (Int) -> Unit = {
            try {
                ConvertNumber.viewFileInShareIntent(context, quotas.quota_documnets?.get(it))
            } catch (Ex: java.lang.Exception) {
                Toast.makeText(context, "${Ex.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }

        val mAdapter: name_view_row_adapter =
            name_view_row_adapter(quotas.quota_documnets.orEmpty(), itemOnClick)

        binding.nameList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        Log.d("TAG", "LENGTH: ${mAdapter.itemCount} ")

        if (!quotas.isPendingData) {
            if (preparence.getLanguage()
                    .equals("en")
            ) {


                quotas.description?.let {
                    binding.fQuotaDescription.tvText.setText(
                        it
                    )
                }
                quotas.description_bn?.let {
                    binding.fQuotaDescriptionBn.tvText.setText(
                        it
                    )
                }
                quotas.quotaInformation?.name?.let {
                    binding.fQuotaName.tvText.setText(
                        it
                    )
                }
                quotas.quotaInformationDetails?.name.let {
                    binding.fQuotaType.tvText.setText(
                        it
                    )
                }

            } else {
                quotas.description?.let {
                    binding.fQuotaDescription.tvText.setText(
                        it
                    )
                }
                quotas.description_bn?.let {
                    binding.fQuotaDescriptionBn.tvText.setText(
                        it
                    )
                }
                quotas.quotaInformation?.name_bn?.let {
                    binding.fQuotaName.tvText.setText(
                        it
                    )
                }
                quotas.quotaInformationDetails?.name_bn.let {
                    binding.fQuotaType.tvText.setText(
                        it
                    )
                }
            }

        } else {

            binding.hQuota.tvTitle.setText(context.getString(R.string.pending_data))
            binding.hQuota.tvEdit.visibility = View.GONE
            quotas.description?.let {
                binding.fQuotaDescription.tvText.setText(
                    it
                )
            }
            quotas.description_bn?.let {
                binding.fQuotaDescriptionBn.tvText.setText(
                    it
                )
            }

            binding.fQuotaName.tvText.setText(
                "${quotas.quota_information_id}"
            )


            binding.fQuotaType.tvText.setText(
                "${quotas.quota_information_detail_id}"
            )


        }


    }
}
