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
        for (i in 0..19) {
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
                return resources.getString(R.string.personal_information)
            }
            1 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.job_joining_information)
            }
            2 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.quota_information)
            }
            3 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.present_address)
            }
            4 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.permanent_address)
            }
            5 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.educational_qualification)
            }
            6 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.spouse)
            }
            7 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.child_information)
            }
            8 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.language_information)
            }
            9 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.local_training)
            }
            10 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.foreign_training)
            }
            11 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.official_residential_information)
            }
            12 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.foreign_training)
            }
            13 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.additional_professional_qualification)
            }
            14 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.publication)
            }
            15 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.honours_and_award)
            }
            16 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.posting_record)
            }
            17 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.disciplinary_action)
            }
            18 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.promotion)
            }
            19 -> {
                menu_next.visibility = GONE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.reference)
            }
     /*       18 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.leave)
            }
            19 -> {
                menu_next.visibility = VISIBLE
                menu_back.visibility = VISIBLE
                return resources.getString(R.string.job_information)
            }*/

            else -> {
                return ""
            }
        }
    }


}