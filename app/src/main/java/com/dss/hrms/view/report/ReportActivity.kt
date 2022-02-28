package com.dss.hrms.view.report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityLeaveBinding
import com.dss.hrms.databinding.ActivityReportBinding
import com.dss.hrms.view.activity.BaseActivity



class ReportActivity : BaseActivity() {

    lateinit var binding: ActivityReportBinding
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    var id:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report)
        navController = findNavController(R.id.reportFragment)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
        setupToolbar()
    }

    private fun setupToolbar() {
        val appBarConfiguration =
            AppBarConfiguration.Builder()
                .setFallbackOnNavigateUpListener { onNavigateUp() }
                .build()

        binding.toolBar.apply {
            setupWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        // Handle the back button event and return true to override
        // the default behavior the same way as the OnBackPressedCallback.
        // TODO(reason: handle custom back behavior here if desired.)

        // If no custom behavior was handled perform the default action.
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}