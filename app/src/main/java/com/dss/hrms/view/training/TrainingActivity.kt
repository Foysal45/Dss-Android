package com.dss.hrms.view.training

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityTrainingBinding
import com.dss.hrms.view.activity.BaseActivity
import com.google.android.material.navigation.NavigationView


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
        setSupportActionBar(binding.toolBar)
     //   getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        drawerLayout = binding.drawerLayout
       binding?.navMenu.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
       setupActionBarWithNavController(navController, appBarConfiguration)
        setupToolbar()

        binding.drawerIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        }



        binding.navMenu.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->

            Log.e(
                "drawer",
                "...............................................................item clicked............................................"
            )
            val id = menuItem.itemId
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            //This is for closing the drawer after acting on it
            drawerLayout.closeDrawer(Gravity.RIGHT);
            true
        })
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
        val navController = findNavController(R.id.fragment)
      //  return true
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        var context: TrainingActivity? = null
    }

}