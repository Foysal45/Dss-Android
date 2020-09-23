package com.dss.hrms.activity.ac_profile.view

import BaseModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.*
import com.chaadride.shared_pref.SharedPref
import com.dss.hrms.R
import com.dss.hrms.activity.ac_profile.adapter.viewpager.ViewPageAdapter
import com.dss.hrms.fragment.view.FragmentEmployeePersonalInfo
import com.dss.hrms.helper.LanguageChange
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    private var viewPager: ViewPager? = null
    private var adapter: ViewPageAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        LanguageChange.languageSet(this)
        menu_title.text = intent.getStringExtra("title")
        menu_topic.text = showTopic(intent.getIntExtra("position", 0))
        viewPager = findViewById<ViewPager>(R.id.viewpager_go)
        adapter = ViewPageAdapter(supportFragmentManager)
        //add fragment
        for (i in 0..21) {
            val bundle = Bundle()
            bundle.putInt("message", i)
            val fragobj = FragmentEmployeePersonalInfo()
            fragobj.arguments = bundle
            adapter!!.addFragment(fragobj, "Home")
        }
        // adding setup
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                menu_topic.text = showTopic(position)
                menu_next.setOnClickListener { viewPager!!.currentItem = position + 1 }
                menu_back.setOnClickListener { viewPager!!.currentItem = position - 1 }

            }
        })
        viewPager!!.currentItem = intent.getIntExtra("position", 0)
        back.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        finish()
    }

    fun showTopic(pos: Int): String {
        when (pos) {
            0 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = GONE
                return "Personal Information"
            }
            1 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Job Joining Information"
            }
            2 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Quota Information"
            }
            3 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Present Address"
            }
            4 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Permanent Address"
            }
            5 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Eduation Qualification"
            }
            6 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Spouse"
            }
            7 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Child Information"
            }
            8 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Language Information"
            }
            9 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Local Training"
            }
            10 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Foreign Training"
            }
            11 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Official Residential Information"
            }
            12 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Foreign Travel"
            }
            13 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Additional Professional Qualification"
            }
            14 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Publication"
            }
            15 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Honours and Award"
            }
            16 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Posting Record"
            }
            17 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Disciplinary Action"
            }
            18 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Leave"
            }
            19 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Job Information"
            }
            20 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return "Promotion"
            }
            21 -> {
                menu_next.visibility = GONE
                menu_back.visibility = VISIBLE
                return "Reference"
            }
            else -> {
                return ""
            }
        }
    }


}