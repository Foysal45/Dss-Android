package com.dss.hrms.activity.ac_main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.chaadride.fragment.fg_hubList.adapter.RecyclerAdapter_dashboard
import com.dss.hrms.R
import com.dss.hrms.activity.ac_profile.view.ProfileActivity
import com.dss.hrms.activity.ac_main.model.Dashboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_header.menu_icon
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_menu_layout.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu()
        var dashboardList = mutableListOf<Dashboard>()
        var recyclerAdapter_dashboard = RecyclerAdapter_dashboard(this, dashboardList)
        rec_dashboard.layoutManager = GridLayoutManager(this, 3)
        rec_dashboard.adapter = recyclerAdapter_dashboard

        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))
        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))

        recyclerAdapter_dashboard.notifyDataSetChanged()





    }

    private fun menu() {
        menu_profile_lay_expandableLayout.collapse()
        menu_profile_lay_head.setOnClickListener {
            menu_profile_lay_expandableLayout.toggle()
            if (!menu_profile_lay_expandableLayout.isExpanded)
            {   menu_profile_lay_head.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorLowGreen
                )
            )
                menu_profile.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                more_profile.setImageDrawable(getDrawable(R.drawable.ic_baseline_expand_more_white_24))
                more_profile.animate().setDuration(300).rotation(180.0f).start()
            }
            else{
                menu_profile_lay_head.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.colorLightGreen
                    )
                )
                menu_profile.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                more_profile.setImageDrawable(getDrawable(R.drawable.ic_baseline_expand_more_24))
                more_profile.animate().setDuration(300).rotation(360.0f).start()
            }
        }
        menu_icon.setOnClickListener { drawer_menu.openDrawer(GravityCompat.START) }
        menu_back.setOnClickListener { drawer_menu.closeDrawer(GravityCompat.START) }

        //profile sub menu
        menu_profile_personal_info.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    0
                )
            )
        }
        menu_profile_job_joining_information.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    1
                )
            )
        }
        menu_profile_quata_information.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    2
                )
            )
        }
        menu_profile_present_address.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    3
                )
            )
        }
        menu_profile_permanent_address.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    4
                )
            )
        }
        menu_profile_educational_qualification.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    5
                )
            )
        }
        menu_profile_spouse.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    6
                )
            )
        }
        menu_profile_child_information.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    7
                )
            )
        }
        menu_profile_language_information.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    8
                )
            )
        }
        menu_profile_local_training.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    9
                )
            )
        }
        menu_profile_foreign_training.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    10
                )
            )
        }
        menu_profile_Official_residential_information.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    11
                )
            )
        }
        menu_profile_foreign_travel.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    12
                )
            )
        }
        menu_profile_additional_professional_qualification.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    13
                )
            )
        }
        menu_profile_publication.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    14
                )
            )
        }
        menu_profile_honours_award.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    15
                )
            )
        }
        menu_profile_posting_record.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    16
                )
            )
        }
        menu_profile_disciplinary_action.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    17
                )
            )
        }
        menu_profile_leave.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    18
                )
            )
        }
        menu_profile_job_information.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    19
                )
            )
        }
        menu_profile_promotion.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    20
                )
            )
        }
        menu_profile_reference.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity
                ::class.java).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    21
                )
            )
        }




    }
    override fun onBackPressed() {
        if (drawer_menu.isDrawerOpen(GravityCompat.START)) {
            drawer_menu.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}