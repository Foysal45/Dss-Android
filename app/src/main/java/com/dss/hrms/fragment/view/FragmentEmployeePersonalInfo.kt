package com.dss.hrms.fragment.view

import BaseModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaadride.network.error.ApiError
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.R
import com.dss.hrms.fragment.model.Box
import com.dss.hrms.fragment.adapter.RecyclerAdapter_box
import com.dss.hrms.fragment.adapter.RecyclerAdapter_faq_p
import com.dss.hrms.fragment.model.BoxParent
import com.dss.hrms.fragment.viewModel.*
import com.dss.hrms.helper.LanguageChange
import com.dss.hrms.network.model.additional_profile_qualification.response.AdditionalProfileQualificationRes
import com.dss.hrms.network.model.educational_qualification.response.EducationalQualificationRes
import com.dss.hrms.network.model.foreign_traning.response.ForeignTraningRes
import com.dss.hrms.network.model.honours_award.response.HonorAwardRes
import com.dss.hrms.network.model.local_training.response.LocalTraningRes
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_red.view.*

class FragmentEmployeePersonalInfo : Fragment() {
    var boxList = mutableListOf<Box>()
    var recyclerAdapter_Box: RecyclerAdapter_box? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_red, container, false)
        LanguageChange.languageSet(view.context)
        val position = arguments!!.getInt("message", 0)
        recyclerAdapter_Box = RecyclerAdapter_box(view.context, boxList)
        view.rec_red.layoutManager = LinearLayoutManager(view.context)
        view.rec_red.adapter = recyclerAdapter_Box

        var sharedPref = SharedPref(view.context)
        val text: String = sharedPref.json!!
        val baseModel = Gson().fromJson(text, BaseModel::class.java)

        listShow(view, baseModel, position)

        return view;
    }

    private fun listShow(view: View, baseModel: BaseModel, position: Int) {

        boxList.clear()
        when (position) {

            0 -> {
                //personal info

                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            1 -> {
                //job join info
                if (baseModel.data.jobjoinings.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.designation),
                            getString(R.string.designation),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.depertment),
                            getString(R.string.depertment),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.job_type), getString(R.string.job_type), ""))
                    boxList.add(
                        Box(
                            getString(R.string.joning_date),
                            getString(R.string.joning_date),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.pension), getString(R.string.pension), ""))
                    boxList.add(Box(getString(R.string._class), getString(R.string._class), ""))
                    boxList.add(Box(getString(R.string.grade), getString(R.string.grade), ""))
                    boxList.add(Box(getString(R.string.prl_date), getString(R.string.prl_date), ""))
                } else {
                    for (n in baseModel.data.jobjoinings.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.designation),
                                    getString(R.string.designation),
                                    baseModel.data.jobjoinings[n].designation.g_desi_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.depertment),
                                    getString(R.string.depertment),
                                    baseModel.data.jobjoinings[n].department.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.job_type),
                                    getString(R.string.job_type),
                                    baseModel.data.jobjoinings[n].job_type.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.joning_date),
                                    getString(R.string.joning_date),
                                    baseModel.data.jobjoinings[n].g_joining_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.pension),
                                    getString(R.string.pension),
                                    baseModel.data.jobjoinings[n].g_pension_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string._class),
                                    getString(R.string._class),
                                    baseModel.data.jobjoinings[n].employee_class.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.grade),
                                    getString(R.string.grade),
                                    baseModel.data.jobjoinings[n].grade.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.prl_date),
                                    getString(R.string.prl_date),
                                    baseModel.data.jobjoinings[n].g_prl_date(), View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.designation),
                                    getString(R.string.designation),
                                    baseModel.data.jobjoinings[n].designation.g_desi_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.depertment),
                                    getString(R.string.depertment),
                                    baseModel.data.jobjoinings[n].department.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.job_type),
                                    getString(R.string.job_type),
                                    baseModel.data.jobjoinings[n].job_type.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.joning_date),
                                    getString(R.string.joning_date),
                                    baseModel.data.jobjoinings[n].g_joining_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.pension),
                                    getString(R.string.pension),
                                    baseModel.data.jobjoinings[n].g_pension_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string._class),
                                    getString(R.string._class),
                                    baseModel.data.jobjoinings[n].employee_class.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.grade),
                                    getString(R.string.grade),
                                    baseModel.data.jobjoinings[n].grade.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.prl_date),
                                    getString(R.string.prl_date),
                                    baseModel.data.jobjoinings[n].g_prl_date(), View.VISIBLE
                                )
                            )
                        }


                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()


            }
            2 -> {
                //quota info
                if (baseModel.data.employee_quotas.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.quota_type),
                            getString(R.string.quota_type),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.description),
                            getString(R.string.description),
                            ""
                        )
                    )
                } else {
                    for (n in baseModel.data.employee_quotas.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.quota_type),
                                    getString(R.string.quota_type),
                                    baseModel.data.employee_quotas[n].quota_information.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.description),
                                    getString(R.string.description),
                                    baseModel.data.employee_quotas[n].quota_information_details.g_name(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.quota_type),
                                    getString(R.string.quota_type),
                                    baseModel.data.employee_quotas[n].quota_information.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.description),
                                    getString(R.string.description),
                                    baseModel.data.employee_quotas[n].quota_information_details.g_name_bn(),View.VISIBLE
                                )
                            )
                        }
                    }
                }

                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            3 -> {
                //present address
                if (baseModel.data.present_addresses.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.vill_house),
                            getString(R.string.vill_house),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.road_word),
                            getString(R.string.road_word),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.post_off), getString(R.string.post_off), ""))
                    boxList.add(
                        Box(
                            getString(R.string.police_station),
                            getString(R.string.police_station),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.district), getString(R.string.district), ""))
                    boxList.add(Box(getString(R.string.division), getString(R.string.division), ""))
                    boxList.add(
                        Box(
                            getString(R.string.phone_mobile),
                            getString(R.string.phone_mobile),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.email), getString(R.string.email), ""))
                } else {
                    for (n in baseModel.data.present_addresses.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.vill_house),
                                    getString(R.string.vill_house),
                                    baseModel.data.present_addresses[n].g_village_house_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.road_word),
                                    getString(R.string.road_word),
                                    baseModel.data.present_addresses[n].g_road_word_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.post_off),
                                    getString(R.string.post_off),
                                    baseModel.data.present_addresses[n].g_post_office()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.police_station),
                                    getString(R.string.police_station),
                                    baseModel.data.present_addresses[n].g_police_station()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.district),
                                    getString(R.string.district),
                                    baseModel.data.present_addresses[n].district.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.division),
                                    getString(R.string.division),
                                    baseModel.data.present_addresses[n].division.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.phone_mobile),
                                    getString(R.string.phone_mobile),
                                    baseModel.data.present_addresses[n].g_phone_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.email),
                                    getString(R.string.email),
                                    "",View.VISIBLE
                                )
                            )

                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.vill_house),
                                    getString(R.string.vill_house),
                                    baseModel.data.present_addresses[n].g_village_house_no_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.road_word),
                                    getString(R.string.road_word),
                                    baseModel.data.present_addresses[n].g_road_word_no_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.post_off),
                                    getString(R.string.post_off),
                                    baseModel.data.present_addresses[n].g_post_office_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.police_station),
                                    getString(R.string.police_station),
                                    baseModel.data.present_addresses[n].g_police_station_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.district),
                                    getString(R.string.district),
                                    baseModel.data.present_addresses[n].district.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.division),
                                    getString(R.string.division),
                                    baseModel.data.present_addresses[n].division.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.phone_mobile),
                                    getString(R.string.phone_mobile),
                                    baseModel.data.present_addresses[n].g_phone_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.email),
                                    getString(R.string.email),
                                    "",View.VISIBLE
                                )
                            )
                        }


                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()

            }
            4 -> {
                //permanent address
                if (baseModel.data.permanent_addresses.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.vill_house),
                            getString(R.string.vill_house),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.road_word),
                            getString(R.string.road_word),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.post_off), getString(R.string.post_off), ""))
                    boxList.add(
                        Box(
                            getString(R.string.police_station),
                            getString(R.string.police_station),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.district), getString(R.string.district), ""))
                    boxList.add(Box(getString(R.string.division), getString(R.string.division), ""))
                    boxList.add(
                        Box(
                            getString(R.string.phone_mobile),
                            getString(R.string.phone_mobile),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.email), getString(R.string.email), ""))
                } else {
                    for (n in baseModel.data.permanent_addresses.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.vill_house),
                                    getString(R.string.vill_house),
                                    baseModel.data.permanent_addresses[n].g_village_house_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.road_word),
                                    getString(R.string.road_word),
                                    baseModel.data.permanent_addresses[n].g_road_word_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.post_off),
                                    getString(R.string.post_off),
                                    baseModel.data.permanent_addresses[n].g_post_office()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.police_station),
                                    getString(R.string.police_station),
                                    baseModel.data.permanent_addresses[n].g_police_station()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.district),
                                    getString(R.string.district),
                                    baseModel.data.permanent_addresses[n].district.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.division),
                                    getString(R.string.division),
                                    baseModel.data.permanent_addresses[n].division.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.phone_mobile),
                                    getString(R.string.phone_mobile),
                                    baseModel.data.permanent_addresses[n].g_phone_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.email),
                                    getString(R.string.email),
                                    "",View.VISIBLE
                                )
                            )

                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.vill_house),
                                    getString(R.string.vill_house),
                                    baseModel.data.permanent_addresses[n].g_village_house_no_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.road_word),
                                    getString(R.string.road_word),
                                    baseModel.data.permanent_addresses[n].g_road_word_no_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.post_off),
                                    getString(R.string.post_off),
                                    baseModel.data.permanent_addresses[n].g_post_office_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.police_station),
                                    getString(R.string.police_station),
                                    baseModel.data.permanent_addresses[n].g_police_station_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.district),
                                    getString(R.string.district),
                                    baseModel.data.permanent_addresses[n].district.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.division),
                                    getString(R.string.division),
                                    baseModel.data.permanent_addresses[n].division.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.phone_mobile),
                                    getString(R.string.phone_mobile),
                                    baseModel.data.permanent_addresses[n].g_phone_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.email),
                                    getString(R.string.email),
                                    "",View.VISIBLE
                                )
                            )
                        }


                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            5 -> {
                //education
                if (baseModel.data.educational_qualifications.size == 0) {
                    boxList.add(Box(getString(R.string.name_deg), getString(R.string.name_deg), ""))
                    boxList.add(Box(getString(R.string.name_ins), getString(R.string.name_deg), ""))
                    boxList.add(
                        Box(
                            getString(R.string.board_uni),
                            getString(R.string.board_uni),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.passing_year),
                            getString(R.string.passing_year),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.div_cgpa), getString(R.string.div_cgpa), ""))
                } else {
                    for (n in baseModel.data.educational_qualifications.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.name_deg),
                                    getString(R.string.name_deg),
                                    baseModel.data.educational_qualifications[n].g_name_of_degree()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_ins),
                                    getString(R.string.name_deg),
                                    baseModel.data.educational_qualifications[n].g_name_of_institute()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.board_uni),
                                    getString(R.string.board_uni),
                                    baseModel.data.educational_qualifications[n].g_board_university()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.passing_year),
                                    getString(R.string.passing_year),
                                    baseModel.data.educational_qualifications[n].g_passing_year()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.div_cgpa),
                                    getString(R.string.div_cgpa),
                                    baseModel.data.educational_qualifications[n].g_division_cgpa(),View.VISIBLE
                                )
                            )

                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.name_deg),
                                    getString(R.string.name_deg),
                                    baseModel.data.educational_qualifications[n].g_name_of_degree_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_ins),
                                    getString(R.string.name_deg),
                                    baseModel.data.educational_qualifications[n].g_name_of_institute_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.board_uni),
                                    getString(R.string.board_uni),
                                    baseModel.data.educational_qualifications[n].g_board_university()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.passing_year),
                                    getString(R.string.passing_year),
                                    baseModel.data.educational_qualifications[n].g_passing_year()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.div_cgpa),
                                    getString(R.string.div_cgpa),
                                    baseModel.data.educational_qualifications[n].g_division_cgpa(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()

            }
            6 -> {
                //spouse
                if (baseModel.data.spouses.size == 0) {
                    boxList.add(Box(getString(R.string.name), getString(R.string.name), ""))
                    boxList.add(Box(getString(R.string.name_b), getString(R.string.name_b), ""))
                    boxList.add(Box(getString(R.string.office), getString(R.string.office), ""))
                    boxList.add(Box(getString(R.string.district), getString(R.string.district), ""))
                    boxList.add(Box(getString(R.string.email), getString(R.string.email), ""))
                    boxList.add(
                        Box(
                            getString(R.string.occupation),
                            getString(R.string.occupation),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.phone), getString(R.string.phone), ""))
                    boxList.add(Box(getString(R.string.mobile), getString(R.string.mobile), ""))
                    boxList.add(Box(getString(R.string.religion), getString(R.string.religion), ""))
                } else {
                    for (n in baseModel.data.spouses.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.name),
                                    getString(R.string.name),
                                    baseModel.data.spouses[n].g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_b),
                                    getString(R.string.name_b),
                                    baseModel.data.spouses[n].g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.office),
                                    getString(R.string.office),
                                    ""
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.district),
                                    getString(R.string.district),
                                    baseModel.data.spouses[n].distric.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.email),
                                    getString(R.string.email),
                                    baseModel.data.spouses[n].g_email_address()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.occupation),
                                    getString(R.string.occupation),
                                    baseModel.data.spouses[n].g_occupation()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.phone),
                                    getString(R.string.phone),
                                    baseModel.data.spouses[n].g_phone_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.mobile),
                                    getString(R.string.mobile),
                                    baseModel.data.spouses[n].g_mobile_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.religion),
                                    getString(R.string.religion),
                                    baseModel.data.spouses[n].religion.g_name(),View.VISIBLE
                                )
                            )

                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.name),
                                    getString(R.string.name),
                                    baseModel.data.spouses[n].g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_b),
                                    getString(R.string.name_b),
                                    baseModel.data.spouses[n].g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.office),
                                    getString(R.string.office),
                                    ""
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.district),
                                    getString(R.string.district),
                                    baseModel.data.spouses[n].distric.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.email),
                                    getString(R.string.email),
                                    baseModel.data.spouses[n].g_email_address()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.occupation),
                                    getString(R.string.occupation),
                                    baseModel.data.spouses[n].g_occupation_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.phone),
                                    getString(R.string.phone),
                                    baseModel.data.spouses[n].g_phone_no_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.mobile),
                                    getString(R.string.mobile),
                                    baseModel.data.spouses[n].g_mobile_no_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.religion),
                                    getString(R.string.religion),
                                    baseModel.data.spouses[n].religion.g_name_bn(),View.VISIBLE
                                )
                            )
                        }


                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            7 -> {
                //child information
                if (baseModel.data.childs.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.name_child),
                            getString(R.string.name_child),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.name_child_bn),
                            getString(R.string.name_child_bn),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.child_bd), getString(R.string.child_bd), ""))
                    boxList.add(
                        Box(
                            getString(R.string.child_sex),
                            getString(R.string.child_sex),
                            ""
                        )
                    )
                } else {
                    for (n in baseModel.data.childs.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.name_child),
                                    getString(R.string.name_child),
                                    baseModel.data.childs[n].g_name_of_children()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_child_bn),
                                    getString(R.string.name_child_bn),
                                    baseModel.data.childs[n].g_name_of_children_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.child_bd),
                                    getString(R.string.child_bd),
                                    baseModel.data.childs[n].g_date_of_birth()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.child_sex),
                                    getString(R.string.child_sex),
                                    baseModel.data.childs[n].gender.g_name(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.name_child),
                                    getString(R.string.name_child),
                                    baseModel.data.childs[n].g_name_of_children()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_child_bn),
                                    getString(R.string.name_child_bn),
                                    baseModel.data.childs[n].g_name_of_children_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.child_bd),
                                    getString(R.string.child_bd),
                                    baseModel.data.childs[n].g_date_of_birth()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.child_sex),
                                    getString(R.string.child_sex),
                                    baseModel.data.childs[n].gender.g_name_bn(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()

            }
            8 -> {
                //language info
                if (baseModel.data.languages.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.name_language),
                            getString(R.string.name_language),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.name_institute),
                            getString(R.string.name_institute),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.exp_level),
                            getString(R.string.exp_level),
                            ""
                        )
                    )
                    // boxList.add(Box("Division/CGPA", "Division/CGPA", ""))
                } else {
                    for (n in baseModel.data.languages.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.name_language),
                                    getString(R.string.name_language),
                                    baseModel.data.languages[n].g_name_of_language()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_institute),
                                    getString(R.string.name_institute),
                                    baseModel.data.languages[n].g_name_of_institute()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.exp_level),
                                    getString(R.string.exp_level),
                                    baseModel.data.languages[n].g_expertise_level(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.name_language),
                                    getString(R.string.name_language),
                                    baseModel.data.languages[n].g_name_of_language_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_institute),
                                    getString(R.string.name_institute),
                                    baseModel.data.languages[n].g_name_of_institute_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.exp_level),
                                    getString(R.string.exp_level),
                                    baseModel.data.languages[n].g_expertise_level(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()


            }
            9 -> {
                //local training
                if (baseModel.data.local_trainings.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.course_title),
                            getString(R.string.course_title),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.name_institution),
                            getString(R.string.name_institution),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.from_date),
                            getString(R.string.from_date),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.to_date), getString(R.string.to_date), ""))
                    boxList.add(
                        Box(
                            getString(R.string.certificate),
                            getString(R.string.certificate),
                            ""
                        )
                    )
                } else {
                    for (n in baseModel.data.local_trainings.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.course_title),
                                    getString(R.string.course_title),
                                    baseModel.data.local_trainings[n].g_course_title()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_institution),
                                    getString(R.string.name_institution),
                                    baseModel.data.local_trainings[n].g_name_of_institute()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.from_date),
                                    getString(R.string.from_date),
                                    baseModel.data.local_trainings[n].g_from_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.to_date),
                                    getString(R.string.to_date),
                                    baseModel.data.local_trainings[n].g_to_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.certificate),
                                    getString(R.string.certificate),
                                    baseModel.data.local_trainings[n].g_certificate(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.course_title),
                                    getString(R.string.course_title),
                                    baseModel.data.local_trainings[n].g_course_title_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_institution),
                                    getString(R.string.name_institution),
                                    baseModel.data.local_trainings[n].g_name_of_institute_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.from_date),
                                    getString(R.string.from_date),
                                    baseModel.data.local_trainings[n].g_from_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.to_date),
                                    getString(R.string.to_date),
                                    baseModel.data.local_trainings[n].g_to_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.certificate),
                                    getString(R.string.certificate),
                                    baseModel.data.local_trainings[n].g_certificate(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            10 -> {
                //foreign training
                if (baseModel.data.foreigntrainings.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.course_title),
                            getString(R.string.course_title),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.name_institution),
                            getString(R.string.name_institution),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.from_date),
                            getString(R.string.from_date),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.to_date), getString(R.string.to_date), ""))
                    boxList.add(
                        Box(
                            getString(R.string.certificate),
                            getString(R.string.certificate),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.country), getString(R.string.country), ""))
                } else {
                    for (n in baseModel.data.foreigntrainings.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.course_title),
                                    getString(R.string.course_title),
                                    baseModel.data.foreigntrainings[n].g_course_title()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_institution),
                                    getString(R.string.name_institution),
                                    baseModel.data.foreigntrainings[n].g_name_of_institute()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.from_date),
                                    getString(R.string.from_date),
                                    baseModel.data.foreigntrainings[n].g_from_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.to_date),
                                    getString(R.string.to_date),
                                    baseModel.data.foreigntrainings[n].g_to_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.certificate),
                                    getString(R.string.certificate),
                                    baseModel.data.foreigntrainings[n].g_certificate()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.country),
                                    getString(R.string.country),
                                    baseModel.data.foreigntrainings[n].country.g_name(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.course_title),
                                    getString(R.string.course_title),
                                    baseModel.data.foreigntrainings[n].g_course_title_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_institution),
                                    getString(R.string.name_institution),
                                    baseModel.data.foreigntrainings[n].g_name_of_institute_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.from_date),
                                    getString(R.string.from_date),
                                    baseModel.data.foreigntrainings[n].g_from_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.to_date),
                                    getString(R.string.to_date),
                                    baseModel.data.foreigntrainings[n].g_to_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.certificate),
                                    getString(R.string.certificate),
                                    baseModel.data.foreigntrainings[n].g_certificate()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.country),
                                    getString(R.string.country),
                                    baseModel.data.foreigntrainings[n].country.g_name_bn(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()

            }
            11 -> {
                //official residential
                if (baseModel.data.official_residentials.size == 0) {
                    boxList.add(Box(getString(R.string.memo_no), getString(R.string.memo_no), ""))
                    boxList.add(
                        Box(
                            getString(R.string.memo_date),
                            getString(R.string.memo_date),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.desi), getString(R.string.desi), ""))
                    boxList.add(
                        Box(
                            getString(R.string.office_zoon),
                            getString(R.string.office_zoon),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.location), getString(R.string.location), ""))
                    boxList.add(
                        Box(
                            getString(R.string.quarter_name),
                            getString(R.string.quarter_name),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.flat_no), getString(R.string.flat_no), ""))
                } else {
                    for (n in baseModel.data.official_residentials.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.memo_no),
                                    getString(R.string.memo_no),
                                    baseModel.data.official_residentials[n].g_memo_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.memo_date),
                                    getString(R.string.memo_date),
                                    baseModel.data.official_residentials[n].g_memo_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.desi),
                                    getString(R.string.desi),
                                    baseModel.data.official_residentials[n].designation.g_desi_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.office_zoon),
                                    getString(R.string.office_zoon),
                                    baseModel.data.official_residentials[n].g_office_zone()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.location),
                                    getString(R.string.location),
                                    baseModel.data.official_residentials[n].g_location()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.quarter_name),
                                    getString(R.string.quarter_name),
                                    baseModel.data.official_residentials[n].g_quarter_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.flat_no),
                                    getString(R.string.flat_no),
                                    baseModel.data.official_residentials[n].g_flat_no_flat_type(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.memo_no),
                                    getString(R.string.memo_no),
                                    baseModel.data.official_residentials[n].g_memo_no_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.memo_date),
                                    getString(R.string.memo_date),
                                    baseModel.data.official_residentials[n].g_memo_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.desi),
                                    getString(R.string.desi),
                                    baseModel.data.official_residentials[n].designation.g_desi_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.office_zoon),
                                    getString(R.string.office_zoon),
                                    baseModel.data.official_residentials[n].g_office_zone()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.location),
                                    getString(R.string.location),
                                    baseModel.data.official_residentials[n].g_location()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.quarter_name),
                                    getString(R.string.quarter_name),
                                    baseModel.data.official_residentials[n].g_quarter_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.flat_no),
                                    getString(R.string.flat_no),
                                    baseModel.data.official_residentials[n].g_flat_no_flat_type(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()

            }
            12 -> {
                //foreign Travel
                if (baseModel.data.foreign_travels.size == 0) {
                    boxList.add(Box(getString(R.string.country), getString(R.string.country), ""))
                    boxList.add(Box(getString(R.string.purpose), getString(R.string.purpose), ""))
                    boxList.add(
                        Box(
                            getString(R.string.from_date),
                            getString(R.string.from_date),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.to_date), getString(R.string.to_date), ""))
                } else {
                    for (n in baseModel.data.foreign_travels.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.country),
                                    getString(R.string.country),
                                    baseModel.data.foreign_travels[n].country.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.purpose),
                                    getString(R.string.purpose),
                                    baseModel.data.foreign_travels[n].g_purpose()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.from_date),
                                    getString(R.string.from_date),
                                    baseModel.data.foreign_travels[n].g_from_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.to_date),
                                    getString(R.string.to_date),
                                    baseModel.data.foreign_travels[n].g_to_date(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.country),
                                    getString(R.string.country),
                                    baseModel.data.foreign_travels[n].country.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.purpose),
                                    getString(R.string.purpose),
                                    baseModel.data.foreign_travels[n].g_purpose_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.from_date),
                                    getString(R.string.from_date),
                                    baseModel.data.foreign_travels[n].g_from_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.to_date),
                                    getString(R.string.to_date),
                                    baseModel.data.foreign_travels[n].g_to_date(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()

            }
            13 -> {
                //Additional Profile Qualification
                if (baseModel.data.additional_qualifications.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.qualification_name),
                            getString(R.string.qualification_name),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.qualification_details),
                            getString(R.string.qualification_details),
                            ""
                        )
                    )
                } else {
                    for (n in baseModel.data.additional_qualifications.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.qualification_name),
                                    getString(R.string.qualification_name),
                                    baseModel.data.additional_qualifications[n].g_qualification_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.qualification_details),
                                    getString(R.string.qualification_details),
                                    baseModel.data.additional_qualifications[n].g_qualification_details(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.qualification_name),
                                    getString(R.string.qualification_name),
                                    baseModel.data.additional_qualifications[n].g_qualification_details_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.qualification_details),
                                    getString(R.string.qualification_details),
                                    baseModel.data.additional_qualifications[n].g_qualification_details_bn(),View.VISIBLE
                                )
                            )

                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            14 -> {
                //Publication
                if (baseModel.data.publications.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.type_of_pub),
                            getString(R.string.type_of_pub),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.public_details),
                            getString(R.string.public_details),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.pub_name), getString(R.string.pub_name), ""))
                    boxList.add(
                        Box(
                            getString(R.string.pub_name_bn),
                            getString(R.string.pub_name_bn),
                            ""
                        )
                    )
                } else {
                    for (n in baseModel.data.publications.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.type_of_pub),
                                    getString(R.string.type_of_pub),
                                    baseModel.data.publications[n].publication_type.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.public_details),
                                    getString(R.string.public_details),
                                    baseModel.data.publications[n].g_publication_details()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.pub_name),
                                    getString(R.string.pub_name),
                                    baseModel.data.publications[n].g_publication_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.pub_name_bn),
                                    getString(R.string.pub_name_bn),
                                    baseModel.data.publications[n].g_publication_name_bn(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.type_of_pub),
                                    getString(R.string.type_of_pub),
                                    baseModel.data.publications[n].publication_type.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.public_details),
                                    getString(R.string.public_details),
                                    baseModel.data.publications[n].g_publication_details_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.pub_name),
                                    getString(R.string.pub_name),
                                    baseModel.data.publications[n].g_publication_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.pub_name_bn),
                                    getString(R.string.pub_name_bn),
                                    baseModel.data.publications[n].g_publication_name_bn(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()

            }
            15 -> {
                //honor award
                if (baseModel.data.honours_awards.size == 0) {
                    boxList.add(Box(getString(R.string.aware_t), getString(R.string.aware_t), ""))
                    boxList.add(
                        Box(
                            getString(R.string.award_details),
                            getString(R.string.award_details),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.award_date),
                            getString(R.string.award_date),
                            ""
                        )
                    )

                }
                else {

                    var boxparent = mutableListOf<BoxParent>()
                    var recyclerAdapter_Box: RecyclerAdapter_faq_p=  RecyclerAdapter_faq_p(view.context,boxparent)
                    view.rec_red.layoutManager = LinearLayoutManager(view.context)
                    view.rec_red.adapter = recyclerAdapter_Box
                    for (n in baseModel.data.honours_awards.indices) {
                        var boxb = mutableListOf<Box>()
                        if (LanguageChange.langA == "en") {

                            boxb.add(
                                Box(
                                    getString(R.string.aware_t),
                                    getString(R.string.aware_t),
                                    baseModel.data.honours_awards[n].g_award_title()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.award_details),
                                    getString(R.string.award_details),
                                    baseModel.data.honours_awards[n].g_award_details()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.award_date),
                                    getString(R.string.award_date),
                                    baseModel.data.honours_awards[n].g_award_date(),View.VISIBLE
                                )
                            )

                        }
                        else {
                            boxb.add(
                                Box(
                                    getString(R.string.aware_t),
                                    getString(R.string.aware_t),
                                    baseModel.data.honours_awards[n].g_award_title_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.award_details),
                                    getString(R.string.award_details),
                                    baseModel.data.honours_awards[n].g_award_details_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.award_date),
                                    getString(R.string.award_date),
                                    baseModel.data.honours_awards[n].g_award_date(),View.VISIBLE
                                )
                            )

                        }
                        boxparent.add(BoxParent(boxb))
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            16 -> {
                //posting record
                if (baseModel.data.posting_records.size == 0) {
                    boxList.add(
                        Box(
                            getString(R.string.transfer_type),
                            getString(R.string.transfer_type),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.transfer_from),
                            getString(R.string.transfer_from),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.transfer_to),
                            getString(R.string.transfer_to),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.eff_date), getString(R.string.eff_date), ""))
                }
                else {
                    var boxparent = mutableListOf<BoxParent>()
                    var recyclerAdapter_Box: RecyclerAdapter_faq_p=  RecyclerAdapter_faq_p(view.context,boxparent)
                    view.rec_red.layoutManager = LinearLayoutManager(view.context)
                    view.rec_red.adapter = recyclerAdapter_Box
                    for (n in baseModel.data.posting_records.indices) {
                        var boxb = mutableListOf<Box>()
                        if (LanguageChange.langA == "en") {

                            boxb.add(
                                Box(
                                    getString(R.string.transfer_type),
                                    getString(R.string.transfer_type),
                                    baseModel.data.posting_records[n].transfer_type.g_name()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.transfer_from),
                                    getString(R.string.transfer_from),
                                    baseModel.data.posting_records[n].transfer_from.g_office_name()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.transfer_to),
                                    getString(R.string.transfer_to),
                                    baseModel.data.posting_records[n].transfer_to.g_office_name()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.eff_date),
                                    getString(R.string.eff_date),
                                    baseModel.data.posting_records[n].g_effective_date(),View.VISIBLE
                                )
                            )

                        }
                        else {
                            boxb.add(
                                Box(
                                    getString(R.string.transfer_type),
                                    getString(R.string.transfer_type),
                                    baseModel.data.posting_records[n].transfer_type.g_name_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.transfer_from),
                                    getString(R.string.transfer_from),
                                    baseModel.data.posting_records[n].transfer_from.g_office_name_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.transfer_to),
                                    getString(R.string.transfer_to),
                                    baseModel.data.posting_records[n].transfer_to.g_office_name_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.eff_date),
                                    getString(R.string.eff_date),
                                    baseModel.data.posting_records[n].g_effective_date(),View.VISIBLE
                                )
                            )

                        }
                        boxparent.add(BoxParent(boxb))
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()

            }
            17 -> {
                //disciplinary
                if (baseModel.data.disciplinary_actions.size == 0) {
                    boxList.add(Box(getString(R.string.catagory), getString(R.string.catagory), ""))
                    boxList.add(
                        Box(
                            getString(R.string.present_status),
                            getString(R.string.present_status),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.description),
                            getString(R.string.description),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.judgment), getString(R.string.judgment), ""))
                    boxList.add(
                        Box(
                            getString(R.string.final_judment),
                            getString(R.string.final_judment),
                            ""
                        )
                    )
                    boxList.add(Box(getString(R.string.remakrs), getString(R.string.remakrs), ""))
                    boxList.add(Box(getString(R.string.date), getString(R.string.date), ""))

                }
                else {
                    var boxparent = mutableListOf<BoxParent>()
                    var recyclerAdapter_Box: RecyclerAdapter_faq_p=  RecyclerAdapter_faq_p(view.context,boxparent)
                    view.rec_red.layoutManager = LinearLayoutManager(view.context)
                    view.rec_red.adapter = recyclerAdapter_Box
                    for (n in baseModel.data.disciplinary_actions.indices) {
                        var boxb = mutableListOf<Box>()
                        if (LanguageChange.langA == "en") {
                            boxb.add(
                                Box(
                                    getString(R.string.catagory),
                                    getString(R.string.catagory),
                                    baseModel.data.disciplinary_actions[n].disciplinary_action_category.g_name()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.present_status),
                                    getString(R.string.present_status),
                                    ""
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.description),
                                    getString(R.string.description),
                                    baseModel.data.disciplinary_actions[n].g_disciplinary_action_details()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.judgment),
                                    getString(R.string.judgment),
                                    baseModel.data.disciplinary_actions[n].g_judgment()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.final_judment),
                                    getString(R.string.final_judment),
                                    baseModel.data.disciplinary_actions[n].g_final_judgment()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.remakrs),
                                    getString(R.string.remakrs),
                                    baseModel.data.disciplinary_actions[n].g_remarks()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.date),
                                    getString(R.string.date),
                                    baseModel.data.disciplinary_actions[n].g_date(),View.VISIBLE
                                )
                            )

                        }
                        else {
                            boxb.add(
                                Box(
                                    getString(R.string.catagory),
                                    getString(R.string.catagory),
                                    baseModel.data.disciplinary_actions[n].disciplinary_action_category.g_name_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.present_status),
                                    getString(R.string.present_status),
                                    ""
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.description),
                                    getString(R.string.description),
                                    baseModel.data.disciplinary_actions[n].g_disciplinary_action_details_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.judgment),
                                    getString(R.string.judgment),
                                    baseModel.data.disciplinary_actions[n].g_judgment_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.final_judment),
                                    getString(R.string.final_judment),
                                    baseModel.data.disciplinary_actions[n].g_final_judgment_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.remakrs),
                                    getString(R.string.remakrs),
                                    baseModel.data.disciplinary_actions[n].g_remarks_bn()
                                )
                            )
                            boxb.add(
                                Box(
                                    getString(R.string.date),
                                    getString(R.string.date),
                                    baseModel.data.disciplinary_actions[n].g_date(),View.VISIBLE
                                )
                            )

                        }
                        boxparent.add(BoxParent(boxb))
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            /*18 -> {
                //leave
                var EmployeePersonalInfoViewModel_leave: EmployeePersonalInfoViewModel_leave? = null
                boxList.add(Box("Leave Title", "leave title", ""))
                boxList.add(Box("Description", "description", ""))
                boxList.add(Box("From Date", "from date", ""))
                boxList.add(Box("To Date", "to date", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            19 -> {
                //job info
                var EmployeePersonalInfoViewModel_jobInfo: EmployeePersonalInfoViewModel_jobInfo? =
                    null
                boxList.add(Box("Memo no & Date", "memo no date", ""))
                boxList.add(Box("Joining Date", "joining date", ""))
                boxList.add(Box("Designation", "designation", ""))
                boxList.add(Box("Office/Zone", "office zone", ""))
                boxList.add(Box("Pay Scale/Basic Pay", "pay scale basic pay", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }*/
            18 -> {
                //promotion
                if (baseModel.data.promotions.size == 0) {
                    boxList.add(Box(getString(R.string.memo_no), getString(R.string.memo_no), ""))
                    boxList.add(
                        Box(
                            getString(R.string.memo_date),
                            getString(R.string.memo_date),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.office_zoon),
                            getString(R.string.office_zoon),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.previous_post),
                            getString(R.string.previous_post),
                            ""
                        )
                    )
                    boxList.add(
                        Box(
                            getString(R.string.curent_post),
                            getString(R.string.curent_post),
                            ""
                        )
                    )
                } else {
                    for (n in baseModel.data.promotions.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.memo_no),
                                    getString(R.string.memo_no),
                                    baseModel.data.promotions[n].g_memo_no()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.memo_date),
                                    getString(R.string.memo_date),
                                    baseModel.data.promotions[n].g_memo_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.office_zoon),
                                    getString(R.string.office_zoon),
                                    ""
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.previous_post),
                                    getString(R.string.previous_post),
                                    baseModel.data.promotions[n].previous_posts.g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.curent_post),
                                    getString(R.string.curent_post),
                                    baseModel.data.promotions[n].current_position.g_name(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.memo_no),
                                    getString(R.string.memo_no),
                                    baseModel.data.promotions[n].g_memo_no_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.memo_date),
                                    getString(R.string.memo_date),
                                    baseModel.data.promotions[n].g_memo_date()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.office_zoon),
                                    getString(R.string.office_zoon),
                                    ""
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.previous_post),
                                    getString(R.string.previous_post),
                                    baseModel.data.promotions[n].previous_posts.g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.curent_post),
                                    getString(R.string.curent_post),
                                    baseModel.data.promotions[n].current_position.g_name_bn(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            19 -> {
                //reference
                if (baseModel.data.references.size == 0) {
                    boxList.add(Box(getString(R.string.name), getString(R.string.name), ""))
                    boxList.add(Box(getString(R.string.name_b), getString(R.string.name_b), ""))
                    boxList.add(Box(getString(R.string.relation), getString(R.string.relation), ""))
                    boxList.add(Box(getString(R.string.address), getString(R.string.address), ""))
                    boxList.add(Box(getString(R.string.contact), getString(R.string.contact), ""))
                } else {
                    for (n in baseModel.data.references.indices) {
                        if (LanguageChange.langA == "en") {
                            boxList.add(
                                Box(
                                    getString(R.string.name),
                                    getString(R.string.name),
                                    baseModel.data.references[n].g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_b),
                                    getString(R.string.name_b),
                                    baseModel.data.references[n].g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.relation),
                                    getString(R.string.relation),
                                    baseModel.data.references[n].g_relation()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.address),
                                    getString(R.string.address),
                                    baseModel.data.references[n].g_address()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.contact),
                                    getString(R.string.contact),
                                    baseModel.data.references[n].g_contact_no(),View.VISIBLE
                                )
                            )
                        } else {
                            boxList.add(
                                Box(
                                    getString(R.string.name),
                                    getString(R.string.name),
                                    baseModel.data.references[n].g_name()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.name_b),
                                    getString(R.string.name_b),
                                    baseModel.data.references[n].g_name_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.relation),
                                    getString(R.string.relation),
                                    baseModel.data.references[n].g_relation_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.address),
                                    getString(R.string.address),
                                    baseModel.data.references[n].g_address_bn()
                                )
                            )
                            boxList.add(
                                Box(
                                    getString(R.string.contact),
                                    getString(R.string.contact),
                                    baseModel.data.references[n].g_contact_no_bn(),View.VISIBLE
                                )
                            )
                        }
                    }
                }
                recyclerAdapter_Box!!.notifyDataSetChanged()


            }
        }
        recyclerAdapter_Box!!.notifyDataSetChanged()
    }

    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}