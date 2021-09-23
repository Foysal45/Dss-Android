package com.dss.hrms.view.activity

import android.content.ActivityNotFoundException
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dss.hrms.R
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_help_desk_page.*
import kotlinx.android.synthetic.main.personel_information_view_field_with_right_icon.view.*
import java.util.*
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import com.dss.hrms.retrofit.RetrofitInstance


class HelpDeskPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setLocalLanguage()
        setContentView(R.layout.activity_help_desk_page)

        fName.tvTitle.text = applicationContext.getText(R.string.namee)
        fdesignnation.tvTitle.text = applicationContext.getText(R.string.desi)
        fdepartment.tvTitle.text = applicationContext.getText(R.string.office)
        femail.tvTitle.text = applicationContext.getText(R.string.email_text)
        fEmail.tvTitle.text = applicationContext.getText(R.string.email_text)
        fphone.tvTitle.text = applicationContext.getText(R.string.phone_office)
        fmobile.tvTitle.text = applicationContext.getText(R.string.moobile)
        ffax.tvTitle.text = applicationContext.getText(R.string.fax)
        fFax.tvTitle.text = applicationContext.getText(R.string.fax)

        fAddress.tvTitle.text = applicationContext.getText(R.string.only_address)
        fpobx.tvTitle.text = applicationContext.getText(R.string.pabx)

        // setting icon
        fpobx.icon.background = ContextCompat.getDrawable(this, R.drawable.folder)
        femail.icon.background = ContextCompat.getDrawable(this, R.drawable.envelope)
        fEmail.icon.background = ContextCompat.getDrawable(this, R.drawable.envelope)

        fphone.icon.background = ContextCompat.getDrawable(this, R.drawable.ic_phone_tool_variant)
        fmobile.icon.background =
            ContextCompat.getDrawable(this, R.drawable.ic_phone_digital_communication_tool)

        ffax.icon.background = ContextCompat.getDrawable(this, R.drawable.fax)
        fFax.icon.background = ContextCompat.getDrawable(this, R.drawable.fax)



        backBtn.setOnClickListener {
            finish()
        }




        fName.tvText.text = "Sheikh Rafiqul Islam"
        fdesignnation.tvText.text = "Director General"
        fdepartment.tvText.text = "DSS Building, Room # 201"
        femail.tvText.text = "dg@dss.gov.bd"
        fEmail.tvText.text = "inf@dss.gov.bd"
        fphone.tvText.text = "+880255007024"
        fmobile.tvText.text = "+8801708474001"
        ffax.tvText.text = "+880248118571"
        fFax.tvText.text = "+88029138375"
        fpobx.tvText.text = "+8802-55006595 / 55007020"
        fAddress.tvText.text =
            "Shamajseba Bhaban E-8/B-1, Agargaon, Sher-e-Bangla Nagar Dhaka-1207, Bangladesh"




        fphone.tvText.setOnClickListener {
            makeACall("+880255007024")
        }
        fmobile.tvText.setOnClickListener {
            makeACall("+8801708474001")
        }
        privacy_policy.setOnClickListener {
            openLink(RetrofitInstance.BASE_URL + "/public/content/privacy-policy")
        }
        rules.setOnClickListener {
            openLink(RetrofitInstance.BASE_URL + "/public/content/disclaimer")
        }


        femail.tvText.setOnClickListener {
            composeEmail(femail.tvText.text.toString())
        }
        fEmail.tvText.setOnClickListener {
            composeEmail(fEmail.tvText.text.toString())
        }


    }

    fun setLocalLanguage() {
        val locale = Locale(MySharedPreparence(this).getLanguage().toString())
        Locale.setDefault(locale)
        val configuration: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            val configuration = Configuration()
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
            configuration.setLocale(locale)
            createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            configuration.setLayoutDirection(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }

    }


    fun makeACall(ph: String) {
        val number: String = ph
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$number")
        startActivity(callIntent)
    }

    fun openLink(url: String) {
        try {
            val intent = Intent(ACTION_VIEW, Uri.parse(url)).apply {
                // The URL should either launch directly in a non-browser app
                // (if it’s the default), or in the disambiguation dialog
                addCategory(CATEGORY_BROWSABLE)
                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER or
                        FLAG_ACTIVITY_REQUIRE_DEFAULT
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Only browser apps are available, or a browser is the default app for this intent
            // This code executes in one of the following cases:
            // 1. Only browser apps can handle the intent.
            // 2. The user has set a browser app as the default app.
            // 3. The user hasn't set any app as the default for handling this URL.

        }
    }

    fun composeEmail(addresses: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, "SUBJECT")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }


}