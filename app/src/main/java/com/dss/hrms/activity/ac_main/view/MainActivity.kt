package com.dss.hrms.activity.ac_main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.chaadride.fragment.fg_hubList.adapter.RecyclerAdapter_dashboard
import com.dss.hrms.R
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
    }
    override fun onBackPressed() {
        if (drawer_menu.isDrawerOpen(GravityCompat.START)) {
            drawer_menu.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}