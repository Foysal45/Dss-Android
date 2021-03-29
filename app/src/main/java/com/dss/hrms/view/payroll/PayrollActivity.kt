package com.dss.hrms.view.payroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityPayrollBinding
import com.dss.hrms.view.activity.BaseActivity

class PayrollActivity : BaseActivity() {
    lateinit var binding: ActivityPayrollBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payroll)

       navController = findNavController(R.id.payrollFragment)
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