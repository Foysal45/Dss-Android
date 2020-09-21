package com.dss.hrms.fragment.view

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
import com.dss.hrms.R
import com.dss.hrms.fragment.model.Box
import com.dss.hrms.fragment.adapter.RecyclerAdapter_box
import com.dss.hrms.fragment.viewModel.*
import com.dss.hrms.network.model.additional_profile_qualification.response.AdditionalProfileQualificationRes
import com.dss.hrms.network.model.educational_qualification.response.EducationalQualificationRes
import com.dss.hrms.network.model.foreign_traning.response.ForeignTraningRes
import com.dss.hrms.network.model.honours_award.response.HonorAwardRes
import com.dss.hrms.network.model.local_training.response.LocalTraningRes
import kotlinx.android.synthetic.main.fragment_red.view.*

class FragmentEmployeePersonalInfo : Fragment() {
    var boxList = mutableListOf<Box>()
    var recyclerAdapter_Box: RecyclerAdapter_box?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_red, container, false)
        val position = arguments!!.getInt("message",0)
        recyclerAdapter_Box = RecyclerAdapter_box(view.context, boxList)
        view.rec_red.layoutManager = LinearLayoutManager(view.context)
        view.rec_red.adapter = recyclerAdapter_Box

        listShow(view,position)

        return view;
    }

    private fun listShow(view:View,position:Int) {

        boxList.clear()
        when(position)
        {

            0->{
                //personal info
                var EmployeePersonalInfoViewModel_personalInfo: EmployeePersonalInfoViewModel_personalInfo?= null
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            1->{
                //job join info
                var EmployeePersonalInfoViewModel_jobJoinInfo: EmployeePersonalInfoViewModel_jobJoinInfo?= null
                boxList.add(Box("Designation", "Designation", ""))
                boxList.add(Box("Department", "Department", ""))
                boxList.add(Box("Job Type", "Job Type", ""))
                boxList.add(Box("Joining Date", "Joining Date", ""))
                boxList.add(Box("Pension Date", "Pension Date", ""))
                boxList.add(Box("Class", "Class", ""))
                boxList.add(Box("Grade(Scale)", "Grade(Scale)", ""))
                boxList.add(Box("PRL Date", "PRL Date", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            2->{
                //quota info
                var EmployeePersonalInfoViewModel_quotaInfo: EmployeePersonalInfoViewModel_quotaInfo?= null
                boxList.add(Box("Quota Type", "Quota Type", ""))
                boxList.add(Box("Description", "Description", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            3->{
                //present address
                var employeePersonalInfoViewModel_presentAdderss:EmployeePersonalInfoViewModel_presentAdderss?=null
                boxList.add(Box("Village/House No.", "Village/House No", ""))
                boxList.add(Box("Road/Word No.", "Road/Word No", ""))
                boxList.add(Box("Post Office", "Post Office", ""))
                boxList.add(Box("Police Station", "Police Station", ""))
                boxList.add(Box("District", "District", ""))
                boxList.add(Box("Division", "Division", ""))
                boxList.add(Box("Phone & Mobile No.", "Phone & Mobile No", ""))
                boxList.add(Box("E-mail Address", "email address", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            4->{
                //permanent address
                var EmployeePersonalInfoViewModel_permanentAddress:EmployeePersonalInfoViewModel_permanentAddress?=null

                boxList.add(Box("Village/House No.", "Village/House No", ""))
                boxList.add(Box("Road/Word No.", "Road/Word No", ""))
                boxList.add(Box("Post Office", "Post Office", ""))
                boxList.add(Box("Police Station", "Police Station", ""))
                boxList.add(Box("District", "District", ""))
                boxList.add(Box("Division", "Division", ""))
                boxList.add(Box("Phone & Mobile No.", "Phone & Mobile No", ""))
                boxList.add(Box("E-mail Address", "email address", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            5->{
                //education
                var employeePersonalInfoViewModel_eduationInfo=ViewModelProvider(this)[EmployeePersonalInfoViewModel_eduationInfo::class.java]
                employeePersonalInfoViewModel_eduationInfo.educationalQualification(view.context).observe(activity!!, Observer<Any> { any ->

                    if (any is EducationalQualificationRes) {
                        for (n in any.getData().indices )
                        {
                            boxList.add(Box("Name of Degree", "Name of Degree", any.getData()[n].getNameDegree()))
                            boxList.add(Box("Name of Institute", "Name of Institute", any.getData()[n].getNameInstitute()))
                            boxList.add(Box("Board/University", "Board/University", any.getData()[n].getBoard()))
                            boxList.add(Box("Passing Year", "Passing Year", any.getData()[n].getYear()))
                            boxList.add(Box("Division / CGPA", "Division / CGPA", any.getData()[n].getCgpa()))
                        }
                        recyclerAdapter_Box!!.notifyDataSetChanged()

                    } else if (any is ApiError) {

                        toast(view.context, any.getMessage())


                    } else if (any is Throwable) {
                        toast(view.context, "Please check your internet connection")

                    }

                })


            }
            6->{
                //spouse
                var EmployeePersonalInfoViewModel_spouse: EmployeePersonalInfoViewModel_spouse?= null
                boxList.add(Box("Name", "Name", ""))
                boxList.add(Box("Name (Bangla)", "Name bangla", ""))
                boxList.add(Box("Office", "Office", ""))
                boxList.add(Box("District", "District", ""))
                boxList.add(Box("E-mail Address", "Email address", ""))
                boxList.add(Box("Occupation", "Occupation", ""))
                boxList.add(Box("Mobile No.", "Mobile no", ""))
                boxList.add(Box("Religion", "Religion name will goes here", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            7->{
                //child information
                var EmployeePersonalInfoViewModel_childInfo: EmployeePersonalInfoViewModel_childInfo?= null
                boxList.add(Box("Name of Children", "Name of Children", ""))
                boxList.add(Box("Name of Children (Bangla)", "Name of children bangla", ""))
                boxList.add(Box("Date of Birth", "Date of Birth", ""))
                boxList.add(Box("Gender/Sex", "Gender/Sex", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            8->{
                //language info
                var EmployeePersonalInfoViewModel_langInfo: EmployeePersonalInfoViewModel_langInfo?= null
                boxList.add(Box("Name of Language", "Name of Language", ""))
                boxList.add(Box("Name of Institute", "Name of Institute", ""))
                boxList.add(Box("Experties Level", "Experties Level", ""))
                boxList.add(Box("Division/CGPA", "Division/CGPA", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            9->{
                //local training
                var employeePersonalInfoViewModel_localTraning=ViewModelProvider(this)[EmployeePersonalInfoViewModel_localTraning::class.java]
                employeePersonalInfoViewModel_localTraning.localTraning(view.context).observe(activity!!, Observer<Any> { any ->

                    if (any is LocalTraningRes) {
                        for (n in any.getData().indices )
                        {
                            boxList.add(Box("Course Title", "Course Title", any.getData()[n].getCourse_title()))
                            boxList.add(Box("Name of institution", "Name of institution", any.getData()[n].getName_of_institute()))
                            boxList.add(Box("From Date", "From date", any.getData()[n].getFrom_date()))
                            boxList.add(Box("To Date", "To date", any.getData()[n].getTo_date()))
                            boxList.add(Box("Certificate", "Certificate", any.getData()[n].getCertificate()))
                        }
                        recyclerAdapter_Box!!.notifyDataSetChanged()

                    } else if (any is ApiError) {

                        toast(view.context, any.getMessage())


                    } else if (any is Throwable) {
                        toast(view.context, "Please check your internet connection")

                    }

                })
            }
            10->{
                //Foreign training
                var employeePersonalInfoViewModel_foreignTraning=ViewModelProvider(this)[EmployeePersonalInfoViewModel_foreignTraning::class.java]
                employeePersonalInfoViewModel_foreignTraning.foreignTraning(view.context).observe(activity!!, Observer<Any> { any ->

                    if (any is ForeignTraningRes) {
                        for (n in any.getData().indices )
                        {
                            boxList.add(Box("Course Title", "Course Title", any.getData()[n].getCourse_title()))
                            boxList.add(Box("Name of institution", "Name of institution", any.getData()[n].getName_of_institute()))
                            boxList.add(Box("From Date", "From date", any.getData()[n].getFrom_date()))
                            boxList.add(Box("To Date", "To date", any.getData()[n].getTo_date()))
                            boxList.add(Box("Certificate", "Certificate", any.getData()[n].getCertificate()))
                        }
                        recyclerAdapter_Box!!.notifyDataSetChanged()

                    } else if (any is ApiError) {

                        toast(view.context, any.getMessage())


                    } else if (any is Throwable) {
                        toast(view.context, "Please check your internet connection")

                    }

                })

            }
            11->{
                //official residential
                var EmployeePersonalInfoViewModel_officialResidentInfo: EmployeePersonalInfoViewModel_officialResidentInfo?= null
                boxList.add(Box("Memo no & Date", "Memo no & Date", ""))
                boxList.add(Box("Designation", "Designation", ""))
                boxList.add(Box("Office / Zone", "Office / Zone", ""))
                boxList.add(Box("Location", "Location", ""))
                boxList.add(Box("Quarter Name", "Quarter Name", ""))
                boxList.add(Box("Flat No & Flat Type", "Flat No & Flat Type", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            12->{
                //foreign Travel
                var EmployeePersonalInfoViewModel_foreignTravel: EmployeePersonalInfoViewModel_foreignTravel?= null
                boxList.add(Box("Employee ID", "Employee id", ""))
                boxList.add(Box("Country", "Country", ""))
                boxList.add(Box("Purpose", "Purpose", ""))
                boxList.add(Box("From Date", "Date will goes here", ""))
                boxList.add(Box("To Date", "Date will goes here", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            13->{
                //Additional Profile Qualification
                var employeePersonalInfoViewModel_additionalProfileQualification=ViewModelProvider(this)[EmployeePersonalInfoViewModel_additionalProfileQualification::class.java]
                employeePersonalInfoViewModel_additionalProfileQualification.additionalProfileQualification(view.context).observe(activity!!, Observer<Any> { any ->

                    if (any is AdditionalProfileQualificationRes) {
                        for (n in any.getData().indices )
                        {
                            boxList.add(Box("Qualification Name", "Qualification name", any.getData()[n].getQualificationName()))
                            boxList.add(Box("Qualification Details", "Qualification details", any.getData()[n].getQualificationDetails()))
                        }
                        recyclerAdapter_Box!!.notifyDataSetChanged()

                    } else if (any is ApiError) {

                        toast(view.context, any.getMessage())


                    } else if (any is Throwable) {
                        toast(view.context, "Please check your internet connection")

                    }

                })

            }
            14->{
                //Publication
                var EmployeePersonalInfoViewModel_publication: EmployeePersonalInfoViewModel_publication?= null
                boxList.add(Box("Type of Publication", "Type of publication", ""))
                boxList.add(Box("Publication Details", "Publication details", ""))
                boxList.add(Box("Publication Name", "Publication name", ""))
                boxList.add(Box("Publication Name (Bangla)", "Publication name in Bangla", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            15->{
                //Honours and awards
                var employeePersonalInfoViewModel_honourAward= ViewModelProvider(this)[EmployeePersonalInfoViewModel_honourAward::class.java]
                employeePersonalInfoViewModel_honourAward.honoursAward(view.context) .observe(activity!!, Observer<Any> { any ->

                    if (any is HonorAwardRes) {
                        for (n in any.getData().indices )
                        {
                            boxList.add(Box("Award Title", "Award title", any.getData()[n].getAwardTitle()))
                            boxList.add(Box("Award Details", "Award details", any.getData()[n].getAwardDetails()))
                            boxList.add(Box("Award Date", "Award details", any.getData()[n].getAwardDate()))
                        }
                        recyclerAdapter_Box!!.notifyDataSetChanged()

                    } else if (any is ApiError) {

                            toast(view.context, any.getMessage())


                    } else if (any is Throwable) {
                        toast(view.context, "Please check your internet connection")

                    }

                })
            }
            16->{
                //posting record
                var EmployeePersonalInfoViewModel_postingRecord: EmployeePersonalInfoViewModel_postingRecord?= null
                boxList.add(Box("Transfer From", "Transfer from", ""))
                boxList.add(Box("Transfer To", "Transfer to", ""))
                boxList.add(Box("Effective Date", "Effective date", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            17->{
                //disciplinary
                var EmployeePersonalInfoViewModel_disciplinaryAction: EmployeePersonalInfoViewModel_disciplinaryAction?= null
                boxList.add(Box("Category", "Category", ""))
                boxList.add(Box("Present Status", "Present status", ""))
                boxList.add(Box("Description", "Description", ""))
                boxList.add(Box("Judgement", "Judgement", ""))
                boxList.add(Box("Final Judgement", "Final Judgement", ""))
                boxList.add(Box("Remarks", "Remarks", ""))
                boxList.add(Box("Date", "Date", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            18->{
                //leave
                var EmployeePersonalInfoViewModel_leave: EmployeePersonalInfoViewModel_leave?= null
                boxList.add(Box("Leave Title", "leave title", ""))
                boxList.add(Box("Description", "description", ""))
                boxList.add(Box("From Date", "from date", ""))
                boxList.add(Box("To Date", "to date", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            19->{
                //job info
                var EmployeePersonalInfoViewModel_jobInfo: EmployeePersonalInfoViewModel_jobInfo?= null
                boxList.add(Box("Memo no & Date", "memo no date", ""))
                boxList.add(Box("Joining Date", "joining date", ""))
                boxList.add(Box("Designation", "designation", ""))
                boxList.add(Box("Office/Zone", "office zone", ""))
                boxList.add(Box("Pay Scale/Basic Pay", "pay scale basic pay", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            20->{
                //promotion
                var EmployeePersonalInfoViewModel_promotion: EmployeePersonalInfoViewModel_promotion?= null
                boxList.add(Box("Memo no & Date", "memo no date", ""))
                boxList.add(Box("Office/Zone", "office zone", ""))
                boxList.add(Box("Previous Post", "previous post", ""))
                boxList.add(Box("Current Position", "current position", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
            21->{
                //reference
                var EmployeePersonalInfoViewModel_refrence: EmployeePersonalInfoViewModel_refrence?= null
                boxList.add(Box("Name", "name", ""))
                boxList.add(Box("Name (Bangla)", "name bangla", ""))
                boxList.add(Box("Relation", "relation", ""))
                boxList.add(Box("Address", "address", ""))
                boxList.add(Box("Contact No.", "contact no", ""))
                recyclerAdapter_Box!!.notifyDataSetChanged()
            }
        }
        recyclerAdapter_Box!!.notifyDataSetChanged()
    }
    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}