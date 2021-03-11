package com.dss.hrms.view.messaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityMessagingBinding
import com.dss.hrms.view.activity.BaseActivity

class MessagingActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    lateinit var binding: ActivityMessagingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messaging)
        navController = findNavController(R.id.messagingNavHost)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        // This line is only necessary if using the default action bar.
        setupActionBarWithNavController(navController, appBarConfiguration)

//
//        // This remaining block is only necessary if using a Toolbar from your layout.
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        toolbar.setupWithNavController(navController, appBarConfiguration)
//        // This will handle back actions initiated by the the back arrow
//        // at the start of the toolbar.
//        toolbar.setNavigationOnClickListener {
//            // Handle the back button event and return to override
//            // the default behavior the same way as the OnBackPressedCallback.
//            // TODO(reason: handle custom back behavior here if desired.)
//
//            // If no custom behavior was handled perform the default action.
//            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//        }

    }

    /**
     * If using the default action bar this must be overridden.
     * This will handle back actions initiated by the the back arrow
     * at the start of the action bar.
     */
    override fun onSupportNavigateUp(): Boolean {
        // Handle the back button event and return true to override
        // the default behavior the same way as the OnBackPressedCallback.
        // TODO(reason: handle custom back behavior here if desired.)

        // If no custom behavior was handled perform the default action.
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}