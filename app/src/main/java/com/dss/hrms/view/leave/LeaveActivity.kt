package com.dss.hrms.view.leave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityLeaveBinding
import com.dss.hrms.view.activity.BaseActivity
import dagger.android.support.DaggerAppCompatActivity

class LeaveActivity : BaseActivity() {


    lateinit var binding: ActivityLeaveBinding
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_leave)
        navController = findNavController(R.id.leaveFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }
    override fun onSupportNavigateUp(): Boolean {
        // Handle the back button event and return true to override
        // the default behavior the same way as the OnBackPressedCallback.
        // TODO(reason: handle custom back behavior here if desired.)

        // If no custom behavior was handled perform the default action.
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}