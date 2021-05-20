package com.dss.hrms.view.training

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityTrainingBinding
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity

class TrainingActivity : BaseActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training)
        context = this
        navController = this.findNavController(R.id.fragment)
        drawerLayout = binding.drawerLayout
        binding?.navMenu.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        var context: TrainingActivity? = null
    }

}