package com.dss.hrms.activity.ac_main.view

import BaseLogout
import BaseModel
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.chaadride.fragment.fg_hubList.adapter.RecyclerAdapter_dashboard
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.R
import com.dss.hrms.activity.ac_login_signup.view.LoginSignupActivity
import com.dss.hrms.activity.ac_main.model.Dashboard
import com.dss.hrms.activity.ac_main.viewModel.MainActivityViewModel
import com.dss.hrms.activity.ac_main.viewModel.MainActivityViewModel_logout
import com.dss.hrms.activity.ac_profile.view.ProfileActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_header.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_menu_layout.*


class MainActivity : AppCompatActivity() {
    var sharedPref: SharedPref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = SharedPref(this)
     //   nav_menu.alpha = 0f
        menu()
        var dashboardList = mutableListOf<Dashboard>()
        var recyclerAdapter_dashboard = RecyclerAdapter_dashboard(this, dashboardList)
        rec_dashboard.layoutManager = GridLayoutManager(this, 3)
        rec_dashboard.adapter = recyclerAdapter_dashboard
        loading_lay.setOnClickListener { }

        dashboardList.add(Dashboard("Home", R.drawable.ic_dashboard))

        recyclerAdapter_dashboard.notifyDataSetChanged()

        var mainActivityViewModel =
            ViewModelProvider(this)[MainActivityViewModel::class.java]
        mainActivityViewModel.baseData(this,this).observe(this, Observer<Any> { any ->
            if (any is BaseModel) {
                sharedPref!!.json = Gson().toJson(any)
                Handler().postDelayed(Runnable {
                    //nav_menu.alpha = 1f
                    loading_lay.alpha = 0f
                    loading_lay.visibility = View.GONE
                  //  drawer_menu.isEnabled = true
                }, 1000)
            } else {
                loading_lay.alpha = 0f
                loading_lay.visibility = View.GONE
            }

        })

        this.menu_dashboard_signout.setOnClickListener {
            var mainActivityViewModel_logout =
                ViewModelProvider(this)[MainActivityViewModel_logout::class.java]
            mainActivityViewModel_logout.logout(this,this).observe(this, Observer<Any> { any ->
            })
        }

    }

    private fun menu() {
        menu_profile_lay_expandableLayout.collapse()
        menu_dashboard_lay_head.setOnClickListener {
            drawer_menu.closeDrawer(GravityCompat.START)
        }
        menu_profile_lay_head.setOnClickListener {
            menu_profile_lay_expandableLayout.toggle()
            if (!menu_profile_lay_expandableLayout.isExpanded) {
                menu_profile_lay_head.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.colorLowGreen
                    )
                )
                menu_profile.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                more_profile.setImageDrawable(getDrawable(R.drawable.ic_baseline_expand_more_white_24))
                more_profile.animate().setDuration(300).rotation(180.0f).start()
            } else {
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
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
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    18
                )
            )
        }
        menu_profile_reference.setOnClickListener {
            startActivity(
                Intent(
                    this, ProfileActivity
                    ::class.java
                ).putExtra("page", "personalInfo").putExtra(
                    "title",
                    resources.getString(R.string.profile)
                ).putExtra(
                    "position",
                    19
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