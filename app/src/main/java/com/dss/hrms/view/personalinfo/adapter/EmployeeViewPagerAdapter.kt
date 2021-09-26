package com.dss.hrms.view.personalinfo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*
import javax.inject.Inject

class EmployeeViewPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val fragmentListTitle: MutableList<String> = ArrayList()


    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentListTitle.add(title)
    }

    override fun getItemCount(): Int {
        return fragmentListTitle.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}


