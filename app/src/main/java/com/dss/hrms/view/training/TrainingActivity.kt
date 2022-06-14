package com.dss.hrms.view.training

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityTrainingBinding
import com.dss.hrms.model.JsonKeyReader
import com.dss.hrms.view.activity.BaseActivity
import com.google.android.material.navigation.NavigationView
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject


class TrainingActivity : BaseActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityTrainingBinding
    private lateinit var navView: NavigationView

    @Inject
    lateinit var preparence: MySharedPreparence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_training)
        context = this
        navController = this.findNavController(R.id.fragment)
        setSupportActionBar(binding.toolBar)
        navView = findViewById(R.id.nav_menu)
        //   getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        drawerLayout = binding.drawerLayout
        binding?.navMenu?.setupWithNavController(navController)
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


        // nav menu  manipulation based on user rules
        hideOrShowNavMenu()

        binding.navMenu.setNavigationItemSelectedListener { menuItem ->


            val id = menuItem.itemId
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            //This is for closing the drawer after acting on it
            drawerLayout.closeDrawer(Gravity.RIGHT);
            true
        }
    }

    private fun hideOrShowNavMenu() {
        val list = preparence.get("permissionList") as List<Any>?

        val nav_Menu: Menu = navView.menu

        nav_Menu.findItem(R.id.contentCategoryFragment).isVisible =
            JsonKeyReader.hasPermission("contentmanagementcategory", list)

        nav_Menu.findItem(R.id.webViewActivity).isVisible =
            JsonKeyReader.hasPermission("contentmanagementcontents", list)

        nav_Menu.findItem(R.id.training_management).isVisible =
            JsonKeyReader.hasPermission("coursemanagementresourceperson", list)

        nav_Menu.findItem(R.id.budget_schedule).isVisible =
            JsonKeyReader.hasPermission("budgetandschedule", list)

        nav_Menu.findItem(R.id.courseScheduleFragment).isVisible =
            JsonKeyReader.hasPermission("budgetandschedulecourseschedule", list)
        nav_Menu.findItem(R.id.batchScheduleFragment).isVisible =
            JsonKeyReader.hasPermission("budgetandschedulebatchschedule", list)

        nav_Menu.findItem(R.id.resourcePersonModule).isVisible =
            JsonKeyReader.hasPermission("coursemanagementmodules", list)

        nav_Menu.findItem(R.id.resourcePersonCourse).isVisible =
            JsonKeyReader.hasPermission("coursemanagementcourses", list)



        nav_Menu.findItem(R.id.contentFaqFragment).isVisible = true

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