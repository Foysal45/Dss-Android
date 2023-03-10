package com.dss.hrms.view.personalinfo

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.dss.hrms.R
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.login.LoginInfo
import com.dss.hrms.model.pendingDataModel.PendingDataModel
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.FilePath
import com.dss.hrms.util.HelperClass
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.auth.LoginActivity
import com.dss.hrms.view.personalinfo.adapter.EmployeeViewPagerAdapter
import com.dss.hrms.view.personalinfo.fragment.BasicInformationFragment
import com.dss.hrms.view.personalinfo.fragment.FragmentEmployeeInfo
import com.dss.hrms.view.settings.SettingsActivity
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_employee_info.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


class EmployeeInfoActivity : BaseActivity() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    val REQUEST_SELECT_PHOTO: Int = 2

    @Inject
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var loginInfo: LoginInfo


    lateinit var adapter: EmployeeViewPagerAdapter
    var personalFrg = BasicInformationFragment()
    private var pos = 0
    lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalLanguage(preparence.getLanguage())
        setContentView(R.layout.activity_employee_info)

        employeeViewModel =
            ViewModelProviders.of(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
        // setSupportActionBar(toolBar)
        toolBar.post {
            toolBar.inflateMenu(R.menu.my_profile)
        }

        adapter = EmployeeViewPagerAdapter(supportFragmentManager)

        appContext = application
        context = this
        back.setOnClickListener {
            onBackPressed()
        }
        setSupportActionBar(toolBar)


        // viewpager_go.offscreenPageLimit = 0

        //  viewpager_go.setPageTransformer(null)


        var personal = Bundle()
        personal.putString("key", StaticKey.PersonalInformation)
        personal.putBoolean("addWillAppear", false)

        personalFrg.arguments = personal
        adapter!!.addFragment(personalFrg, "")


        var spouse = Bundle()
        spouse.putString("key", StaticKey.Spouse)
        spouse.putBoolean("addWillAppear", true)
        var spouseFrg = FragmentEmployeeInfo()
        spouseFrg.arguments = spouse
        adapter!!.addFragment(spouseFrg, getString(R.string.spouse))


        var childreanBundle = Bundle()
        childreanBundle.putString("key", StaticKey.Children)
        childreanBundle.putBoolean("addWillAppear", true)
        var childreanFrg2 = FragmentEmployeeInfo()
        childreanFrg2.arguments = childreanBundle
        adapter.addFragment(childreanFrg2, getString(R.string.child_information))


        var presentAddressBundle = Bundle()
        presentAddressBundle.putString("key", StaticKey.PRESENT_ADDRESS)
        presentAddressBundle.putBoolean("addWillAppear", true)
        var presentAddressFrg = FragmentEmployeeInfo()
        presentAddressFrg.arguments = presentAddressBundle
        adapter!!.addFragment(presentAddressFrg, "")

        var permanentAddressBundle = Bundle()
        permanentAddressBundle.putString("key", StaticKey.PERMANENT_ADDRESS)
        permanentAddressBundle.putBoolean("addWillAppear", true)
        var permanentAddressFrg = FragmentEmployeeInfo()
        permanentAddressFrg.arguments = permanentAddressBundle
        adapter!!.addFragment(permanentAddressFrg, "")

        var educationQuaBdl = Bundle()
        educationQuaBdl.putString("key", StaticKey.EducationalQualifications)
        educationQuaBdl.putBoolean("addWillAppear", true)
        var educationBundleFrg = FragmentEmployeeInfo()
        educationBundleFrg.arguments = educationQuaBdl
        adapter!!.addFragment(educationBundleFrg, "")

        var languageBundle = Bundle()
        languageBundle.putString("key", StaticKey.Language)
        languageBundle.putBoolean("addWillAppear", true)
        var languageFrg = FragmentEmployeeInfo()
        languageFrg.arguments = languageBundle
        adapter!!.addFragment(languageFrg, getString(R.string.language_information))


        var jobJoiInBundle = Bundle()
        jobJoiInBundle.putString("key", StaticKey.Jobjoining)
        jobJoiInBundle.putBoolean("addWillAppear", false)
        var jonJoiInfoFrg = FragmentEmployeeInfo()
        jonJoiInfoFrg.arguments = jobJoiInBundle
        adapter!!.addFragment(jonJoiInfoFrg, getString(R.string.job_joining_information))


        val quotaInfoBundle = Bundle()
        quotaInfoBundle.putString("key", StaticKey.Quota)
        quotaInfoBundle.putBoolean("addWillAppear", false)
        val quotaFrg = FragmentEmployeeInfo()
        quotaFrg.arguments = quotaInfoBundle
        adapter!!.addFragment(quotaFrg, getString(R.string.quota_information))


        var localTrainingBundle = Bundle()
        localTrainingBundle.putString("key", StaticKey.LocalTraining)
        localTrainingBundle.putBoolean("addWillAppear", true)
        var localTrainingFrg = FragmentEmployeeInfo()
        localTrainingFrg.arguments = localTrainingBundle
        adapter!!.addFragment(localTrainingFrg, getString(R.string.local_training))


        var foreignBundle = Bundle()
        foreignBundle.putString("key", StaticKey.ForeingTraining)
        foreignBundle.putBoolean("addWillAppear", true)
        var foreignFrg = FragmentEmployeeInfo()
        foreignFrg.arguments = foreignBundle
        adapter!!.addFragment(foreignFrg, getString(R.string.foreign_training))


        var officialResiInfoBundle = Bundle()
        officialResiInfoBundle.putString("key", StaticKey.OfficialResidentials)
        officialResiInfoBundle.putBoolean("addWillAppear", false)
        var officialResiInfoFrg = FragmentEmployeeInfo()
        officialResiInfoFrg.arguments = officialResiInfoBundle
        adapter!!.addFragment(
            officialResiInfoFrg,
            getString(R.string.official_residential_information)
        )


        var foreignTravelBundle = Bundle()
        foreignTravelBundle.putString("key", StaticKey.ForeignTravel)
        foreignTravelBundle.putBoolean("addWillAppear", true)
        var foreignTravelFrg = FragmentEmployeeInfo()
        foreignTravelFrg.arguments = foreignTravelBundle
        adapter!!.addFragment(foreignTravelFrg, getString(R.string.foreign_travel))


        var additionalProfessionalQualificationBundle = Bundle()
        additionalProfessionalQualificationBundle.putString(
            "key",
            StaticKey.AdditionalQualifications
        )
        additionalProfessionalQualificationBundle.putBoolean("addWillAppear", true)
        var addProfeQualificationFrg = FragmentEmployeeInfo()
        addProfeQualificationFrg.arguments = additionalProfessionalQualificationBundle
        adapter!!.addFragment(
            addProfeQualificationFrg,
            getString(R.string.additional_professional_qualification)
        )

        var publicationBundle = Bundle()
        publicationBundle.putString("key", StaticKey.Publication)
        publicationBundle.putBoolean("addWillAppear", true)
        var publicationFrg = FragmentEmployeeInfo()
        publicationFrg.arguments = publicationBundle
        adapter!!.addFragment(publicationFrg, getString(R.string.publication))


        var honoursAndAwardBundle = Bundle()
        honoursAndAwardBundle.putString("key", StaticKey.HonoursAwards)
        honoursAndAwardBundle.putBoolean("addWillAppear", false)
        var honoursAwardFrg = FragmentEmployeeInfo()
        honoursAwardFrg.arguments = honoursAndAwardBundle
        adapter!!.addFragment(honoursAwardFrg, getString(R.string.honours_and_award))

//
        var disciplinaryActionBundle = Bundle()
        disciplinaryActionBundle.putString("key", StaticKey.DisciplinaryAction)
        disciplinaryActionBundle.putBoolean("addWillAppear", false)
        var disciplinaryActionFrg = FragmentEmployeeInfo()
        disciplinaryActionFrg.arguments = disciplinaryActionBundle
        adapter!!.addFragment(disciplinaryActionFrg, getString(R.string.disciplinary_action))

//
//        var promotionBundle = Bundle()
//        promotionBundle.putString("key", StaticKey.Promotion)
//        promotionBundle.putBoolean("addWillAppear", false)
//        var promotionFrg = FragmentEmployeeInfo()
//        promotionFrg.arguments = promotionBundle
//        adapter!!.addFragment(promotionFrg, getString(R.string.promotion))

//
        var referenceBundle = Bundle()
        referenceBundle.putString("key", StaticKey.References)
        referenceBundle.putBoolean("addWillAppear", false)
        var referenceFrg = FragmentEmployeeInfo()
        referenceFrg.arguments = referenceBundle
        adapter!!.addFragment(referenceFrg, getString(R.string.reference))

        var nomineeBundle = Bundle()
        nomineeBundle.putString("key", StaticKey.Nominee)
        nomineeBundle.putBoolean("addWillAppear", true)
        var nomineeFrg = FragmentEmployeeInfo()
        nomineeFrg.arguments = nomineeBundle
        adapter.addFragment(nomineeFrg, getString(R.string.nominee_info))


        // viewpager_go.setCurrentItem(MainActivity.selectedPosition, false)
        Log.e("position", "selected position ; " + MainActivity.selectedPosition)

    }

    override fun onRestart() {
        super.onRestart()
//        finish()
//        startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 0))
    }

    override fun onResume() {
        super.onResume()



        if (MainActivity.isViewIntent == 0) {
            MainActivity.isViewIntent = 0

            //   viewpager_go.setPageTransformer(Pager2_FadeOutTransformer())

            //    viewpager_go.setCurrentItem(MainActivity.selectedPosition, false)

//            Handler(Looper.getMainLooper()).postDelayed(
//                { }, 200)
//            viewpager_go.post(Runnable {
//                viewpager_go.setCurrentItem(MainActivity.selectedPosition, false)
//            })
            Thread(Runnable {
                Thread.sleep(100)
                kotlin.run {
                    runOnUiThread(Runnable {

                        viewpager_go.adapter = adapter
                        viewpager_go.setPageTransformer(true, RotateUpTransformer())
                        viewpager_go.currentItem = MainActivity.selectedPosition
                    })
                }
            }).start()
        } else {
            MainActivity.isViewIntent = 0
            //  viewpager_go.adapter = adapter
            //

            viewpager_go.setPageTransformer(true, RotateUpTransformer())
            viewpager_go.currentItem = MainActivity.selectedPosition
        }

    }

//    fun init() {
//        employeeViewModel =
//            ViewModelProviders.of(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
//    }

    fun restartActivity() {
        finish()
        val intent = Intent(this, EmployeeInfoActivity::class.java)
        //  intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
    }

    companion object {
        var appContext: Application? = null
        var context: EmployeeInfoActivity? = null


        fun refreshEmployeeInfo() {
            var dialog = CustomLoadingDialog().createLoadingDialog(context)
            context?.lifecycleScope?.launch {

                try {

                    context?.employeeViewModel?.getPendingData(context?.loginInfo?.employee_id)
                        ?.collect {
                            if (it is PendingDataModel) {
                                val pendingData = it
                                var preparence: MySharedPreparence = MySharedPreparence(context!!)
                                preparence.put(pendingData, HelperClass.PEDING_DATA)
                                context?.employeeViewModel?.getEmployeeInfo(context?.loginInfo?.employee_id)
                                    ?.collect {
                                        dialog?.dismiss()
                                        if (it is Employee) {
                                            context?.restartActivity()
                                        } else {
                                            context?.restartActivity()
                                        }
                                    }
                            }

                        }
                } catch (
                    ex: Exception
                ) {
                    Toast.makeText(context, "Error : ${ex.localizedMessage}", Toast.LENGTH_LONG)
                        .show()
                }


            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_home -> {
                onBackPressed()
            }
            R.id.action_profile -> {
                finish()
                startActivity(
                    Intent(context, EmployeeInfoActivity::class.java).putExtra(
                        "position",
                        0
                    )
                )
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.action_settings -> {
                startActivity(Intent(context, SettingsActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.action_logout -> {
                preparence?.setLanguage(null)
                preparence?.setLoginStatus(false)
                finish()
                context?.startActivity(
                    Intent(context, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


    fun galleryButtonClicked() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryIntent.setType("*/*")
        val mimetypes = arrayOf(
            "image/*",
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/msword"
        )
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        startActivityForResult(galleryIntent, REQUEST_SELECT_PHOTO)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            val resultUri: Uri? = data.data
            try {

                val path = resultUri?.let {
                    FilePath().getPath(applicationContext, resultUri)
                }

                if (!path.isNullOrBlank() && viewpager_go.currentItem == 0) {

                    personalFrg.passDataToDialogueFragment(path)

                } else {
                    Toast.makeText(context, "Error : No File Was Picked", Toast.LENGTH_LONG).show()
                }

                // send the file  and starts trigger update


            }
//            activity?.let {
//                resultUri?.let { it1 ->
//                    FilePath().getPath(
//                        it,
//                        it1
//                    )?.let { getImageFile(it) }
//                }

            catch (e: IOException) {
                e.printStackTrace()
            }


//            val bitmap =
//                MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri)
//            Log.e("imagefile", "imagefile : $imageFile")
//            var fileName: List<String>? = imageFile?.toString()?.split("/")
//
//            fileName?.let {
//                binding?.tvFileName?.setText(it.get(it.size - 1))
//            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        }
    }

}

class ZoomOutPageTransformer : ViewPager2.PageTransformer {
    private val MIN_SCALE = 0.75f
    private val MIN_ALPHA = 0.5f
    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    alpha = 1f
                    translationX = 0f
                    translationZ = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0,1]
                    // Fade the page out.
                    alpha = 1 - position

                    // Counteract the default slide transition
                    translationX = pageWidth * -position
                    // Move it behind the left page
                    translationZ = -1f

                    // Scale the page down (between MIN_SCALE and 1)
                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}
