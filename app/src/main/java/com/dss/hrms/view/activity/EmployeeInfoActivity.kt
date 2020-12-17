package com.dss.hrms.view.activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.dss.hrms.R
import com.dss.hrms.model.Employee
import com.dss.hrms.model.LoginInfo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.adapter.EmployeeViewPagerAdapter
import com.dss.hrms.view.fragment.BasicInformationFragment
import com.dss.hrms.view.fragment.FragmentPermanentAddresses
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.activity_employee_info.*


class EmployeeInfoActivity : AppCompatActivity() {
    //private val viewPager: ViewPager? = null
    private var adapter: EmployeeViewPagerAdapter? = null
    private var pos = 0
    lateinit var employeeViewModel: EmployeeViewModel
    var loginInfo: LoginInfo? = null
    var preparence: MySharedPreparence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_info)
        //  viewPager = findViewById<ViewPager>(R.id.viewpager_go)
        appContext = application
        context = this
        back.setOnClickListener({
            onBackPressed()
        })
        init()

        adapter = EmployeeViewPagerAdapter(supportFragmentManager)


        var personal = Bundle()
        personal.putString("key", StaticKey.PersonalInformation)
        personal.putBoolean("addWillAppear", false)
        var personalFrg = BasicInformationFragment()
        personalFrg.arguments = personal
        adapter!!.addFragment(personalFrg, "")


        var spouse = Bundle()
        spouse.putString("key", StaticKey.Spouse)
        spouse.putBoolean("addWillAppear", true)
        var spouseFrg = FragmentPermanentAddresses()
        spouseFrg.arguments = spouse
        adapter!!.addFragment(spouseFrg, getString(R.string.spouse))


        var childreanBundle = Bundle()
        childreanBundle.putString("key", StaticKey.Children)
        childreanBundle.putBoolean("addWillAppear", true)
        var childreanFrg2 = FragmentPermanentAddresses()
        childreanFrg2.arguments = childreanBundle
        adapter!!.addFragment(childreanFrg2, getString(R.string.child_information))


        var presentAddressBundle = Bundle()
        presentAddressBundle.putString("key", StaticKey.PRESENT_ADDRESS)
        presentAddressBundle.putBoolean("addWillAppear", true)
        var presentAddressFrg = FragmentPermanentAddresses()
        presentAddressFrg.arguments = presentAddressBundle
        adapter!!.addFragment(presentAddressFrg, "")

        var permanentAddressBundle = Bundle()
        permanentAddressBundle.putString("key", StaticKey.PERMANENT_ADDRESS)
        permanentAddressBundle.putBoolean("addWillAppear", true)
        var permanentAddressFrg = FragmentPermanentAddresses()
        permanentAddressFrg.arguments = permanentAddressBundle
        adapter!!.addFragment(permanentAddressFrg, "")

        var educationQuaBdl = Bundle()
        educationQuaBdl.putString("key", StaticKey.EducationalQualifications)
        educationQuaBdl.putBoolean("addWillAppear", true)
        var educationBundleFrg = FragmentPermanentAddresses()
        educationBundleFrg.arguments = educationQuaBdl
        adapter!!.addFragment(educationBundleFrg, "")

        var languageBundle = Bundle()
        languageBundle.putString("key", StaticKey.Language)
        languageBundle.putBoolean("addWillAppear", true)
        var languageFrg = FragmentPermanentAddresses()
        languageFrg.arguments = languageBundle
        adapter!!.addFragment(languageFrg, getString(R.string.language_information))


        var jobJoiInBundle = Bundle()
        jobJoiInBundle.putString("key", StaticKey.Jobjoining)
        jobJoiInBundle.putBoolean("addWillAppear", false)
        var jonJoiInfoFrg = FragmentPermanentAddresses()
        jonJoiInfoFrg.arguments = jobJoiInBundle
        adapter!!.addFragment(jonJoiInfoFrg, getString(R.string.job_joining_information))


        var quotaInfoBundle = Bundle()
        quotaInfoBundle.putString("key", StaticKey.Quota)
        quotaInfoBundle.putBoolean("addWillAppear", false)
        var quotaFrg = FragmentPermanentAddresses()
        quotaFrg.arguments = quotaInfoBundle
        adapter!!.addFragment(quotaFrg, getString(R.string.quota_information))


        var localTrainingBundle = Bundle()
        localTrainingBundle.putString("key", StaticKey.LocalTraining)
        localTrainingBundle.putBoolean("addWillAppear", true)
        var localTrainingFrg = FragmentPermanentAddresses()
        localTrainingFrg.arguments = localTrainingBundle
        adapter!!.addFragment(localTrainingFrg, getString(R.string.local_training))


        var foreignBundle = Bundle()
        foreignBundle.putString("key", StaticKey.ForeingTraining)
        foreignBundle.putBoolean("addWillAppear", true)
        var foreignFrg = FragmentPermanentAddresses()
        foreignFrg.arguments = foreignBundle
        adapter!!.addFragment(foreignFrg, getString(R.string.foreign_training))


        var officialResiInfoBundle = Bundle()
        officialResiInfoBundle.putString("key", StaticKey.OfficialResidentials)
        officialResiInfoBundle.putBoolean("addWillAppear", false)
        var officialResiInfoFrg = FragmentPermanentAddresses()
        officialResiInfoFrg.arguments = officialResiInfoBundle
        adapter!!.addFragment(
            officialResiInfoFrg,
            getString(R.string.official_residential_information)
        )


        var foreignTravelBundle = Bundle()
        foreignTravelBundle.putString("key", StaticKey.ForeignTravel)
        foreignTravelBundle.putBoolean("addWillAppear", true)
        var foreignTravelFrg = FragmentPermanentAddresses()
        foreignTravelFrg.arguments = foreignTravelBundle
        adapter!!.addFragment(foreignTravelFrg, getString(R.string.foreign_travel))


        var additionalProfessionalQualificationBundle = Bundle()
        additionalProfessionalQualificationBundle.putString(
            "key",
            StaticKey.AdditionalQualifications
        )
        additionalProfessionalQualificationBundle.putBoolean("addWillAppear", true)
        var addProfeQualificationFrg = FragmentPermanentAddresses()
        addProfeQualificationFrg.arguments = additionalProfessionalQualificationBundle
        adapter!!.addFragment(
            addProfeQualificationFrg,
            getString(R.string.additional_professional_qualification)
        )

        var publicationBundle = Bundle()
        publicationBundle.putString("key", StaticKey.Publication)
        publicationBundle.putBoolean("addWillAppear", true)
        var publicationFrg = FragmentPermanentAddresses()
        publicationFrg.arguments = publicationBundle
        adapter!!.addFragment(publicationFrg, getString(R.string.publication))


        var honoursAndAwardBundle = Bundle()
        honoursAndAwardBundle.putString("key", StaticKey.HonoursAwards)
        honoursAndAwardBundle.putBoolean("addWillAppear", false)
        var honoursAwardFrg = FragmentPermanentAddresses()
        honoursAwardFrg.arguments = honoursAndAwardBundle
        adapter!!.addFragment(honoursAwardFrg, getString(R.string.honours_and_award))

//
        var disciplinaryActionBundle = Bundle()
        disciplinaryActionBundle.putString("key", StaticKey.DisciplinaryAction)
        disciplinaryActionBundle.putBoolean("addWillAppear", false)
        var disciplinaryActionFrg = FragmentPermanentAddresses()
        disciplinaryActionFrg.arguments = disciplinaryActionBundle
        adapter!!.addFragment(disciplinaryActionFrg, getString(R.string.disciplinary_action))

//
        var promotionBundle = Bundle()
        promotionBundle.putString("key", StaticKey.Promotion)
        promotionBundle.putBoolean("addWillAppear", false)
        var promotionFrg = FragmentPermanentAddresses()
        promotionFrg.arguments = promotionBundle
        adapter!!.addFragment(promotionFrg, getString(R.string.promotion))

//
        var referenceBundle = Bundle()
        referenceBundle.putString("key", StaticKey.References)
        referenceBundle.putBoolean("addWillAppear", false)
        var referenceFrg = FragmentPermanentAddresses()
        referenceFrg.arguments = referenceBundle
        adapter!!.addFragment(referenceFrg, getString(R.string.reference))


//        Thread(Runnable {
//            Thread.sleep(1000)
//
//        }).start()
        viewpager_go.adapter = adapter
        viewpager_go.setPageTransformer(true, RotateUpTransformer())

        viewpager_go.currentItem = MainActivity.selectedPosition

        Log.e("position", "selected position ; " + MainActivity.selectedPosition)

    }

//    override fun onResume() {
//        super.onResume()
//        Thread(Runnable {
//            kotlin.run {
//
//            }
//        }).start()
//
//    }

    fun init() {
        preparence = MySharedPreparence(this)
        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel::class.java)

        loginInfo = Gson().fromJson(preparence?.getLoginInfo(), LoginInfo::class.java)

    }

    fun restartActivity() {
        finish()
        startActivity(Intent(this, EmployeeInfoActivity::class.java))
    }

    companion object {
        var appContext: Application? = null
        var context: EmployeeInfoActivity? = null


        fun refreshEmployeeInfo() {
            var dialog = CustomLoadingDialog().createLoadingDialog(context)
            context?.employeeViewModel?.getEmployeeInfo(context?.loginInfo?.employee_id)
                ?.observe(context!!, Observer { any ->
                    dialog?.dismiss()
                    //   Log.e("LoginActivity", "response : " + Gson().toJson(any))
                    if (any is Employee) {
                        MainActivity.employee = any
                        context?.restartActivity()
                    }
                })
        }
    }
}