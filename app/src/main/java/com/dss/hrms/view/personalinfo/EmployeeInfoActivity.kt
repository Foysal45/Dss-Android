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
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.dss.hrms.R
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.login.LoginInfo
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.FilePath
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.personalinfo.adapter.EmployeeViewPagerAdapter
import com.dss.hrms.view.personalinfo.fragment.BasicInformationFragment
import com.dss.hrms.view.personalinfo.fragment.FragmentEmployeeInfo
import com.dss.hrms.view.auth.LoginActivity
import com.dss.hrms.view.settings.SettingsActivity
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_employee_info.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
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

    @Inject
    lateinit var adapter: EmployeeViewPagerAdapter
    var personalFrg = BasicInformationFragment()
    private var pos = 0
    lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalLanguage(preparence.getLanguage())
        setContentView(R.layout.activity_employee_info)
        // setSupportActionBar(toolBar)
        toolBar.post {
            toolBar.inflateMenu(R.menu.my_profile)
        }

        appContext = application
        context = this
        back.setOnClickListener({
            onBackPressed()
        })
        setSupportActionBar(toolBar);
        init()

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
        adapter!!.addFragment(childreanFrg2, getString(R.string.child_information))


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


        var quotaInfoBundle = Bundle()
        quotaInfoBundle.putString("key", StaticKey.Quota)
        quotaInfoBundle.putBoolean("addWillAppear", false)
        var quotaFrg = FragmentEmployeeInfo()
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
        adapter!!.addFragment(nomineeFrg, getString(R.string.nominee_info))

        Log.e("position", "selected position ; " + MainActivity.selectedPosition)

    }

    override fun onRestart() {
        super.onRestart()
//        finish()
//        startActivity(Intent(this, EmployeeInfoActivity::class.java).putExtra("position", 0))
    }

    override fun onResume() {
        super.onResume()

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
    }

    fun init() {
        employeeViewModel =
            ViewModelProviders.of(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
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
            context?.lifecycleScope?.launch {
                context?.employeeViewModel?.getEmployeeInfo(context?.loginInfo?.employee_id)
                    ?.collect {
                        dialog?.dismiss()
                        if (it is Employee) {
                            context?.restartActivity()
                        }
                    }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
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
        val mimetypes = arrayOf("image/*", "application/pdf", "application/msword")
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        startActivityForResult(galleryIntent, REQUEST_SELECT_PHOTO)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
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