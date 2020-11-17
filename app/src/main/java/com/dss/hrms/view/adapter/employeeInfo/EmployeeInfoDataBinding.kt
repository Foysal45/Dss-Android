package com.dss.hrms.view.adapter.employeeInfo

import android.content.Context
import android.view.View
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelEmployeeInfoBinding
import com.dss.hrms.model.Employee
import com.namaztime.namaztime.database.MySharedPreparence

class EmployeeInfoDataBinding {

    fun bindPermanentAddressData(
        binding: ModelEmployeeInfoBinding,
        permanentAddresses: Employee.PermanentAddresses,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hAddress.tvTitle.setText(heading)
        binding.fAddressPostOffice.tvTitle.setText(context.getString(R.string.post_off))
        binding.fAddressRoadOrWordNo.tvTitle.setText(context.getString(R.string.road_word))
        binding.fAddressPoliceStation.tvTitle.setText(context.getString(R.string.police_station))
        binding.fAddressDivision.tvTitle.setText(context.getString(R.string.division))
        binding.fAddressDistrict.tvTitle.setText(context.getString(R.string.district))
        binding.fAddressPhoneOrMobileNo.tvTitle.setText(context.getString(R.string.phone_mobile))
        binding.fAddressVillageOrHouseNo.tvTitle.setText(context.getString(R.string.district))
        binding.fAddressEmailAddress.tvTitle.setText(context.getString(R.string.email))
        if (preparence.getLanguage()
                .equals("en")
        ) {
            permanentAddresses.village_house_no?.let {
                binding.fAddressVillageOrHouseNo.tvText.setText(it)
            }
            permanentAddresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            permanentAddresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
            permanentAddresses.police_station?.let { binding.fAddressPoliceStation.tvText.setText(it) }
            permanentAddresses.division?.name?.let { binding.fAddressDivision.tvText.setText(it) }
            permanentAddresses.district?.name?.let { binding.fAddressDistrict.tvText.setText(it) }
            permanentAddresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
        } else {
            permanentAddresses.district?.name_bn?.let {
                binding.fAddressDistrict.tvText.setText(it)

            }
            permanentAddresses.road_word_no_bn?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            permanentAddresses.post_office_bn?.let { binding.fAddressPostOffice.tvText.setText(it) }
            permanentAddresses.police_station_bn?.let {
                binding.fAddressPoliceStation.tvText.setText(
                    it
                )
            }
            permanentAddresses.division?.name_bn?.let { binding.fAddressDivision.tvText.setText(it) }
            permanentAddresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.setText(it) }
            permanentAddresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
        }
    }


    fun bindPrensentAddressData(
        binding: ModelEmployeeInfoBinding,
        permanentAddresses: Employee.PresentAddresses,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
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
            permanentAddresses.village_house_no?.let {
                binding.fAddressVillageOrHouseNo.tvText.setText(it)
            }
            permanentAddresses.road_word_no?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            permanentAddresses.post_office?.let { binding.fAddressPostOffice.tvText.setText(it) }
            permanentAddresses.police_station?.let { binding.fAddressPoliceStation.tvText.setText(it) }
            permanentAddresses.division?.name?.let { binding.fAddressDivision.tvText.setText(it) }
            permanentAddresses.district?.name?.let { binding.fAddressDistrict.tvText.setText(it) }
            permanentAddresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
        } else {
            permanentAddresses.district?.name_bn?.let {
                binding.fAddressDistrict.tvText.setText(it)

            }
            permanentAddresses.road_word_no_bn?.let { binding.fAddressRoadOrWordNo.tvText.setText(it) }
            permanentAddresses.post_office_bn?.let { binding.fAddressPostOffice.tvText.setText(it) }
            permanentAddresses.police_station_bn?.let {
                binding.fAddressPoliceStation.tvText.setText(
                    it
                )
            }
            permanentAddresses.division?.name_bn?.let { binding.fAddressDivision.tvText.setText(it) }
            permanentAddresses.district?.name_bn?.let { binding.fAddressDistrict.tvText.setText(it) }
            permanentAddresses.phone_no?.let { binding.fAddressPhoneOrMobileNo.tvText.setText(it) }
        }
    }


    fun bindEducationalQualificationData(
        binding: ModelEmployeeInfoBinding,
        qualifications: Employee.EducationalQualifications,
        context: Context,
        preparence: MySharedPreparence,
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


        qualifications.board?.let {
            binding.fEQBoardOrUniversity.llBody.visibility = View.VISIBLE
            binding.fEQNameOIn.llBody.visibility = View.GONE
        }
        qualifications.educational_institute?.let {
            binding.fEQBoardOrUniversity.llBody.visibility = View.GONE
            binding.fEQNameOIn.llBody.visibility = View.VISIBLE
        }

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
    }


    fun bindSpouseData(
        binding: ModelEmployeeInfoBinding,
        spouses: Employee.Spouses,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hSpouse.tvTitle.setText(heading)
        binding.fSpouseNameBn.tvTitle.setText(context.getString(R.string.name_b))
        binding.fSpouseNameEn.tvTitle.setText(context.getString(R.string.name))
        binding.fSpouseOffice.tvTitle.setText(context.getString(R.string.workstation))
        binding.fSpouseRelation.tvTitle.setText(context.getString(R.string.relation))
        binding.fSpouseDivision.tvTitle.setText(context.getString(R.string.division))
        binding.fSpouseDistrict.tvTitle.setText(context.getString(R.string.district))
        binding.fSpouseJobType.tvTitle.setText(context.getString(R.string.job_type))
        binding.fSpouseOccupation.tvTitle.setText(context.getString(R.string.occupation))
        binding.fSpousePhoneNo.tvTitle.setText(context.getString(R.string.phone))
        binding.fSpouseMobileNo.tvTitle.setText(context.getString(R.string.mobile))
        binding.fSpouseReligion.tvTitle.setText(context.getString(R.string.religion))
        binding.fSpouseReligion.llBody.visibility = View.GONE

        if (preparence.getLanguage()
                .equals("en")
        ) {

            spouses.name_bn?.let { binding.fSpouseNameBn.tvText.setText(it) }
            spouses.name?.let { binding.fSpouseNameEn.tvText.setText(it) }
            spouses.spouse_workstation?.name?.let { binding.fSpouseOffice.tvText.setText(it) }
            spouses.distric?.name?.let { binding.fSpouseDistrict.tvText.setText(it) }
            spouses.division?.name?.let { binding.fSpouseDivision.tvText.setText(it) }
            spouses.spouse_job_type?.name?.let { binding.fSpouseJobType.tvText.setText(it) }
            spouses.occupation?.name.let { binding.fSpouseOccupation.tvText.setText(it) }
            spouses.relation?.let { binding.fSpouseRelation.tvText.setText(it) }
            spouses.phone_no?.let { binding.fSpousePhoneNo.tvText.setText(it) }
            spouses.mobile_no?.let { binding.fSpouseMobileNo.tvText.setText(it) }
            // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }
        } else {
            spouses.name_bn?.let { binding.fSpouseNameBn.tvText.setText(it) }
            spouses.name?.let { binding.fSpouseNameEn.tvText.setText(it) }
            spouses.spouse_workstation?.name_bn?.let { binding.fSpouseOffice.tvText.setText(it) }
            spouses.distric?.name_bn?.let { binding.fSpouseDistrict.tvText.setText(it) }
            spouses.spouse_job_type?.name_bn?.let { binding.fSpouseJobType.tvText.setText(it) }
            spouses.occupation?.name_bn.let { binding.fSpouseOccupation.tvText.setText(it) }
            spouses.phone_no_bn?.let { binding.fSpousePhoneNo.tvText.setText(it) }
            spouses.mobile_no_bn?.let { binding.fSpouseMobileNo.tvText.setText(it) }
            spouses.relation?.let { binding.fSpouseRelation.tvText.setText(it) }
            spouses.division?.name_bn?.let { binding.fSpouseDivision.tvText.setText(it) }
            // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }
        }
    }

    fun bindJobjoningInfoData(
        binding: ModelEmployeeInfoBinding,
        jobjoinings: Employee.Jobjoinings,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hJobJoiningInformation.tvTitle.setText(heading)
        binding.fJobJoiningOffice.tvTitle.setText(context.getString(R.string.office))
        binding.fJobJoiningDesignation.tvTitle.setText(context.getString(R.string.designation))
        binding.fJobJoiningDepartment.tvTitle.setText(context.getString(R.string.depertment))
        binding.fJobJoiningJobType.tvTitle.setText(context.getString(R.string.job_type))
        binding.fJobJoiningJoiningDate.tvTitle.setText(context.getString(R.string.joning_date))
        binding.fJobJoiningPensionDate.tvTitle.setText(context.getString(R.string.pension))
        binding.fJobJoiningClass.tvTitle.setText(context.getString(R.string._class))
        binding.fJobJoiningGrade.tvTitle.setText(context.getString(R.string.grade))
        binding.fJobJoiningPrlDate.tvTitle.setText(context.getString(R.string.prl_date))

        if (preparence.getLanguage()
                .equals("en")
        ) {
            jobjoinings.office?.office_name?.let { binding.fJobJoiningOffice.tvText.setText(it) }
            jobjoinings.designation?.name?.let { binding.fJobJoiningDesignation.tvText.setText(it) }
            jobjoinings.department?.name?.let { binding.fJobJoiningDepartment.tvText.setText(it) }
            jobjoinings.job_type?.name?.let { binding.fJobJoiningJobType.tvText.setText(it) }
            jobjoinings.joining_date?.let { binding.fJobJoiningJoiningDate.tvText.setText(it) }
            jobjoinings.pension_date?.let { binding.fJobJoiningPensionDate.tvText.setText(it) }
            jobjoinings.employee_class?.name?.let { binding.fJobJoiningClass.tvText.setText(it) }
            jobjoinings.grade?.name?.let { binding.fJobJoiningGrade.tvText.setText(it) }
            jobjoinings.prl_date?.let { binding.fJobJoiningPrlDate.tvText.setText(it) }
            // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }
        } else {
            jobjoinings.office?.office_name_bn?.let { binding.fJobJoiningOffice.tvText.setText(it) }
            jobjoinings.designation?.name_bn?.let { binding.fJobJoiningDesignation.tvText.setText(it) }
            jobjoinings.department?.name_bn?.let { binding.fJobJoiningDepartment.tvText.setText(it) }
            jobjoinings.job_type?.name_bn?.let { binding.fJobJoiningJobType.tvText.setText(it) }
            jobjoinings.joining_date?.let { binding.fJobJoiningJoiningDate.tvText.setText(it) }
            jobjoinings.pension_date?.let { binding.fJobJoiningPensionDate.tvText.setText(it) }
            jobjoinings.employee_class?.name_bn?.let { binding.fJobJoiningClass.tvText.setText(it) }
            jobjoinings.grade?.name_bn?.let { binding.fJobJoiningGrade.tvText.setText(it) }
            jobjoinings.prl_date?.let { binding.fJobJoiningPrlDate.tvText.setText(it) }
            // spouses.address?.let { binding.fSpouseReligion.tvText.setText(it) }
        }
    }

    fun bindChildrenData(
        binding: ModelEmployeeInfoBinding,
        childs: Employee.Childs,
        context: Context,
        preparence: MySharedPreparence,
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

        if (preparence.getLanguage()
                .equals("en")
        ) {
            childs.name_of_children?.let { binding.fChildrenNameOChEn.tvText.setText(it) }
            childs.name_of_children_bn?.let { binding.fChildrenNameOChBn.tvText.setText(it) }
            childs.date_of_birth?.let { binding.fChildrenDOB.tvText.setText(it) }
            childs.gender?.name?.let { binding.fChildrenGenderOrSex.tvText.setText(it) }
            childs.birth_certificate?.let { binding.fChildrenBirthCertificateNo.tvText.setText(it) }
            childs.nid?.let { binding.fChildrenNidNo.tvText.setText(it) }
            childs.passport?.let { binding.fChildrenPassportNo.tvText.setText(it) }
        } else {
            childs.name_of_children?.let { binding.fChildrenNameOChEn.tvText.setText(it) }
            childs.name_of_children_bn?.let { binding.fChildrenNameOChBn.tvText.setText(it) }
            childs.date_of_birth?.let { binding.fChildrenDOB.tvText.setText(it) }
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
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hLanguage.tvTitle.setText(heading)
        binding.fLanguageNOLanguage.tvTitle.setText(context.getString(R.string.name_language))
        binding.fLanguageNOLanguageBn.tvTitle.setText(context.getString(R.string.name_language_bn))
        binding.fLanguageNOInstituteBn.tvTitle.setText(context.getString(R.string.name_institute_bn))
        binding.fLanguageNOInstitute.tvTitle.setText(context.getString(R.string.name_institute))
        binding.fLanguageExperienceLevel.tvTitle.setText(context.getString(R.string.exp_level))
        binding.fLanguageCertificationDate.tvTitle.setText(context.getString(R.string.certification_date))

        if (preparence.getLanguage()
                .equals("en")
        ) {
            languages.name_of_language?.let { binding.fLanguageNOLanguage.tvText.setText(it) }
            languages.name_of_language_bn?.let { binding.fLanguageNOLanguageBn.tvText.setText(it) }
            languages.name_of_institute?.let { binding.fLanguageNOInstitute.tvText.setText(it) }
            languages.name_of_institute_bn?.let { binding.fLanguageNOInstituteBn.tvText.setText(it) }
            languages.expertise_level?.let { binding.fLanguageExperienceLevel.tvText.setText(it) }
            languages.certification_date?.let { binding.fLanguageCertificationDate.tvText.setText(it) }

        } else {
            languages.name_of_language?.let { binding.fLanguageNOLanguage.tvText.setText(it) }
            languages.name_of_language_bn?.let { binding.fLanguageNOLanguageBn.tvText.setText(it) }
            languages.name_of_institute?.let { binding.fLanguageNOInstitute.tvText.setText(it) }
            languages.name_of_institute_bn?.let { binding.fLanguageNOInstituteBn.tvText.setText(it) }
            languages.expertise_level?.let { binding.fLanguageExperienceLevel.tvText.setText(it) }
            languages.certification_date?.let { binding.fLanguageCertificationDate.tvText.setText(it) }
        }
    }


    fun bindLocaltrainingData(
        binding: ModelEmployeeInfoBinding,
        localTrainings: Employee.LocalTrainings,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hLocaltraining.tvTitle.setText(heading)
        binding.fLocalTrainingCountry.llBody.visibility = View.GONE

        binding.fLocalTrainingCourseT.tvTitle.setText(context.getString(R.string.course_title))
        binding.fLocalTrainingCourseTBn.tvTitle.setText(context.getString(R.string.course_title_bn))
        binding.fLocalTrainingNOInst.tvTitle.setText(context.getString(R.string.name_institute))
        binding.fLocalTrainingNOInstBn.tvTitle.setText(context.getString(R.string.name_institute_bn))
        binding.fLocalTrainingLocation.tvTitle.setText(context.getString(R.string.location))
        binding.fLocalTrainingLocationBn.tvTitle.setText(context.getString(R.string.location_bn))
        binding.fLocalTrainingFromDate.tvTitle.setText(context.getString(R.string.from_date))
        binding.fLocalTrainingToDate.tvTitle.setText(context.getString(R.string.to_date))


        if (preparence.getLanguage()
                .equals("en")
        ) {
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

            localTrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.setText(it) }
            localTrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.setText(it) }


        } else {
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

            localTrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.setText(it) }
            localTrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.setText(it) }

        }
    }

    fun bindForeignTrainingData(
        binding: ModelEmployeeInfoBinding,
        foreigntrainings: Employee.Foreigntrainings,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hLocaltraining.tvTitle.setText(heading)
        binding.fLocalTrainingLocation.llBody.visibility = View.GONE
        binding.fLocalTrainingLocationBn.llBody.visibility = View.GONE

        binding.fLocalTrainingCourseT.tvTitle.setText(context.getString(R.string.course_title))
        binding.fLocalTrainingCourseTBn.tvTitle.setText(context.getString(R.string.course_title_bn))
        binding.fLocalTrainingNOInst.tvTitle.setText(context.getString(R.string.name_institute))
        binding.fLocalTrainingNOInstBn.tvTitle.setText(context.getString(R.string.name_institute_bn))
        binding.fLocalTrainingFromDate.tvTitle.setText(context.getString(R.string.from_date))
        binding.fLocalTrainingToDate.tvTitle.setText(context.getString(R.string.to_date))

        binding.fLocalTrainingCountry.tvTitle.setText(context.getString(R.string.country))

        if (preparence.getLanguage()
                .equals("en")
        ) {
            foreigntrainings.course_title?.let { binding.fLocalTrainingCourseT.tvText.setText(it) }
            foreigntrainings.course_title_bn?.let {
                binding.fLocalTrainingCourseTBn.tvText.setText(
                    it
                )
            }
            foreigntrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.setText(it) }
            foreigntrainings.name_of_institute_bn?.let {
                binding.fLocalTrainingNOInstBn.tvText.setText(
                    it
                )
            }
            foreigntrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.setText(it) }
            foreigntrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.setText(it) }


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
            foreigntrainings.name_of_institute?.let { binding.fLocalTrainingNOInst.tvText.setText(it) }
            foreigntrainings.name_of_institute_bn?.let {
                binding.fLocalTrainingNOInstBn.tvText.setText(
                    it
                )
            }
            foreigntrainings.from_date?.let { binding.fLocalTrainingFromDate.tvText.setText(it) }
            foreigntrainings.to_date?.let { binding.fLocalTrainingToDate.tvText.setText(it) }


            foreigntrainings.country?.name?.let {
                binding.fLocalTrainingCountry.tvText.setText(
                    it
                )
            }

        }
    }


    fun bindOfficialResidentialInfoData(
        binding: ModelEmployeeInfoBinding,
        officialResidentials: Employee.OfficialResidentials,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
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
        binding.fORInfoLocation.tvTitle.setText(context.getString(R.string.location))
        binding.fORInfoQuarterName.tvTitle.setText(context.getString(R.string.quarter_name))
        binding.fORInfoFlatNo.tvTitle.setText(context.getString(R.string.flat_no))

        if (preparence.getLanguage()
                .equals("en")
        ) {
            officialResidentials.memo_no?.let { binding.fORInfoMemoNo.tvText.setText(it) }
            officialResidentials.memo_no_bn?.let { binding.fORInfoMemoNoBn.tvText.setText(it) }
            officialResidentials.memo_date?.let { binding.fORInfoMemoDate.tvText.setText(it) }
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


        } else {
            officialResidentials.memo_no?.let { binding.fORInfoMemoNo.tvText.setText(it) }
            officialResidentials.memo_no_bn?.let { binding.fORInfoMemoNoBn.tvText.setText(it) }
            officialResidentials.memo_date?.let { binding.fORInfoMemoDate.tvText.setText(it) }
            officialResidentials.office_zone?.let { binding.fORInfoOfficeZone.tvText.setText(it) }

            officialResidentials.designation?.name_bn?.let {
                binding.fORInfoDesignation.tvText.setText(
                    it
                )
            }
            officialResidentials.division?.name_bn?.let { binding.fORInfoDivision.tvText.setText(it) }
            officialResidentials.district?.name_bn?.let { binding.fORInfoDistrict.tvText.setText(it) }
            officialResidentials.upazila?.name_bn?.let { binding.fORInfoUpazila.tvText.setText(it) }
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

        }
    }

    fun bindForeignTravelInfoData(
        binding: ModelEmployeeInfoBinding,
        foreignTravels: Employee.ForeignTravels,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hForeignTravelInfo.tvTitle.setText(heading)
        binding.fForeignTravelCountry.tvTitle.setText(context.getString(R.string.country))
        binding.fForeignTravelPurpose.tvTitle.setText(context.getString(R.string.purpose))
        binding.fForeignTravelFromDate.tvTitle.setText(context.getString(R.string.from_date))
        binding.fForeignTravelToDate.tvTitle.setText(context.getString(R.string.to_date))


        if (preparence.getLanguage()
                .equals("en")
        ) {
            foreignTravels.country?.name?.let { binding.fForeignTravelCountry.tvText.setText(it) }
            foreignTravels.purpose?.let { binding.fForeignTravelPurpose.tvText.setText(it) }
            foreignTravels.from_date?.let { binding.fForeignTravelFromDate.tvText.setText(it) }
            foreignTravels.to_date?.let { binding.fForeignTravelToDate.tvText.setText(it) }


        } else {
            foreignTravels.country?.name_bn?.let { binding.fForeignTravelCountry.tvText.setText(it) }
            foreignTravels.purpose_bn?.let { binding.fForeignTravelPurpose.tvText.setText(it) }
            foreignTravels.from_date?.let { binding.fForeignTravelFromDate.tvText.setText(it) }
            foreignTravels.to_date?.let { binding.fForeignTravelToDate.tvText.setText(it) }

        }
    }

    fun bindAdditionalQualificationData(
        binding: ModelEmployeeInfoBinding,
        additionalQualifications: Employee.AdditionalQualifications,
        context: Context,
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hAdditionalQualification.tvTitle.setText(heading)
        binding.fAQName.tvTitle.setText(context.getString(R.string.qualification_name))
        binding.fAQNameBn.tvTitle.setText(context.getString(R.string.qualification_name_bangla))
        binding.fAQDetails.tvTitle.setText(context.getString(R.string.qualification_details))
        binding.fAQDetailsBn.tvTitle.setText(context.getString(R.string.qualification_details_bangla))



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
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hPublication.tvTitle.setText(heading)
        binding.fPublicationType.tvTitle.setText(context.getString(R.string.type_of_pub))
        binding.fPublicationDetails.tvTitle.setText(context.getString(R.string.public_details))
        binding.fPublicationDetailsBn.tvTitle.setText(context.getString(R.string.public_details_bn))
        binding.fPublicationNameEn.tvTitle.setText(context.getString(R.string.pub_name))
        binding.fPublicationNameBn.tvTitle.setText(context.getString(R.string.pub_name_bn))



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
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hHonoursAndAward.tvTitle.setText(heading)
        binding.fAwardTitle.tvTitle.setText(context.getString(R.string.aware_t))
        binding.fAwardTitleBn.tvTitle.setText(context.getString(R.string.aware_t_bn))
        binding.fAwardDetailsDetails.tvTitle.setText(context.getString(R.string.award_details))
        binding.fAwardDetailsDetailsBn.tvTitle.setText(context.getString(R.string.award_details_bn))
        binding.fAwardDate.tvTitle.setText(context.getString(R.string.award_date))

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
                    it
                )
            }


        } else {
            honoursAwards.award_title_bn?.let {
                binding.fAwardTitle.tvText.setText(
                    it
                )
            }
            honoursAwards.award_details_bn?.let {
                binding.fAwardDetailsDetails.tvText.setText(
                    it
                )
            }
            honoursAwards.award_date?.let {
                binding.fAwardDate.tvText.setText(
                    it
                )
            }

        }
    }

    fun bindPostingRecordData(
        binding: ModelEmployeeInfoBinding,
        postingRecords: Employee.PostingRecords,
        context: Context,
        preparence: MySharedPreparence,
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
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hDiscipilinaryAction.tvEdit.visibility = View.GONE
        binding.hDiscipilinaryAction.tvTitle.setText(heading)
        binding.fDACategory.tvTitle.setText(context.getString(R.string.catagory))
        binding.fDAPresentStatus.tvTitle.setText(context.getString(R.string.present_status))
        binding.fDADescription.tvTitle.setText(context.getString(R.string.description))
        binding.fDAJudgment.tvTitle.setText(context.getString(R.string.judgment))
        binding.fDAFinalJud.tvTitle.setText(context.getString(R.string.final_judment))
        binding.fDARemarks.tvTitle.setText(context.getString(R.string.remakrs))
        binding.fDADate.tvTitle.setText(context.getString(R.string.date))

        if (preparence.getLanguage()
                .equals("en")
        ) {
            disciplinaryAction.disciplinaryActionCategory?.name?.let {
                binding.fDACategory.tvText.setText(
                    it
                )
            }
            disciplinaryAction.present_status?.let {
                binding.fDAPresentStatus.tvText.setText(
                    if (disciplinaryAction.present_status == 0) "" + context.getString(R.string.pending) else context.getText(
                        R.string.dismiss
                    )
                )
            }
            disciplinaryAction.disciplinary_action_details?.let {
                binding.fDADescription.tvText.setText(
                    it
                )
            }
            disciplinaryAction.judgment?.let { binding.fDAJudgment.tvText.setText(it) }
            disciplinaryAction.final_judgment?.let { binding.fDAFinalJud.tvText.setText(it) }
            disciplinaryAction.remarks?.let { binding.fDARemarks.tvText.setText(it) }
            disciplinaryAction.date?.let { binding.fDADate.tvText.setText(it) }
        } else {
            disciplinaryAction.disciplinaryActionCategory?.name?.let {
                binding.fDACategory.tvText.setText(
                    it
                )
            }
            disciplinaryAction.present_status?.let {
                binding.fDAPresentStatus.tvText.setText(
                    if (disciplinaryAction.present_status == 0) "" + context.getString(R.string.pending) else context.getText(
                        R.string.dismiss
                    )
                )
            }
            disciplinaryAction.disciplinary_action_details_bn?.let {
                binding.fDADescription.tvText.setText(
                    it
                )
            }
            disciplinaryAction.judgment_bn?.let { binding.fDAJudgment.tvText.setText(it) }
            disciplinaryAction.final_judgment_bn?.let { binding.fDAFinalJud.tvText.setText(it) }
            disciplinaryAction.remarks_bn?.let { binding.fDARemarks.tvText.setText(it) }
            disciplinaryAction.date?.let { binding.fDADate.tvText.setText(it) }
        }
    }

    fun bindReferencesData(
        binding: ModelEmployeeInfoBinding,
        references: Employee.References,
        context: Context,
        preparence: MySharedPreparence,
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
        preparence: MySharedPreparence,
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
        preparence: MySharedPreparence,
        heading: String
    ) {
        binding.hQuota.tvTitle.setText(heading)
        binding.fQuotaName.tvTitle.setText(context.getString(R.string.quota_name))
        binding.fQuotaType.tvTitle.setText(context.getString(R.string.quota_type))
        binding.fQuotaDescription.tvTitle.setText(context.getString(R.string.description))
        binding.fQuotaDescriptionBn.tvTitle.setText(context.getString(R.string.description_bn))
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
    }
}