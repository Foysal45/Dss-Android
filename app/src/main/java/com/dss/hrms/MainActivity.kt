package com.dss.hrms

import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.aakira.expandablelayout.ExpandableRelativeLayout
import kotlinx.android.synthetic.main.nav_menu_layout.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu_profile_lay_expandableLayout.collapse()
        menu_profile_lay_head.setOnClickListener {
            menu_profile_lay_expandableLayout.toggle()
            if (!menu_profile_lay_expandableLayout.isExpanded)
            {   menu_profile_lay_head.setBackgroundColor(ContextCompat.getColor(this,R.color.colorLowGreen))
                menu_profile.setTextColor(ContextCompat.getColor(this,R.color.colorWhite))
                more_profile.setImageDrawable(getDrawable(R.drawable.ic_baseline_expand_more_white_24))
                more_profile.animate().setDuration(300).rotation(180.0f).start()
            }
            else{
                menu_profile_lay_head.setBackgroundColor(ContextCompat.getColor(this,R.color.colorLightGreen))
                menu_profile.setTextColor(ContextCompat.getColor(this,R.color.colorBlack))
                more_profile.setImageDrawable(getDrawable(R.drawable.ic_baseline_expand_more_24))
                more_profile.animate().setDuration(300).rotation(360.0f).start()
            }
        }

    }
}