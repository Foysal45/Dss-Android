package com.dss.hrms.activity.ac_profile.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPageAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentList: MutableList <Fragment> = ArrayList()
    private val fragmentListTitle: MutableList <String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentListTitle.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentListTitle.get(position)
    }

    fun addFragment(fragment: Fragment,title:String){
        fragmentList.add(fragment)
        fragmentListTitle.add(title)
    }


}