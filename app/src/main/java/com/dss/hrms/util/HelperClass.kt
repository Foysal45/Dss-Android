package com.dss.hrms.util

import android.content.Context
import android.widget.Spinner
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.pendingDataModel.*
import com.google.gson.GsonBuilder

class HelperClass {
    companion object {
        val PEDING_DATA = "pending_data_obj"


        fun SavePresentAddresssModel(obj: PresentAddressPendingModel): Employee.PresentAddresses {
            var model = Employee.PresentAddresses()
            if (obj.data != null) {
                model = obj.data!!
            }
            return model
        }

        fun SavePeramnentAddresssModel(obj: PermanentAddressPendingModel): Employee.PermanentAddresses {
            var model = Employee.PermanentAddresses()
            if (obj.data != null) {
                model = obj.data!!
            }
            model.isPendingData = true
            return model
        }

        fun saveJobJoiningModel(obj: PendingDataModel.jobJoiningInformationPendingModel): Employee.Jobjoinings {
            var model = Employee.Jobjoinings()
            if (obj.data != null) {
                model = obj.data
            }
            model.isPendingData = true
            return model
        }
        fun saveQuotaModel(obj: QuotaInformationPendingModel): Employee.EmployeeQuotas {
            var model = Employee.EmployeeQuotas()
            if (obj.data != null) {
                model = obj.data
            }
            model.isPendingData = true
            return model
        }

        fun saveEducationQualityModel(obj: EducationalQualificationsPendingModel): Employee.EducationalQualifications {
            var model = Employee.EducationalQualifications()
            if (obj.data != null) {
                model = obj.data!!
            }
            model.isPendingData = true
            return model
        }
        fun saveLanguageModel(obj: languageInfoPendingModel): Employee.Languages {
            var model = Employee.Languages()
            if (obj.data != null) {
                model = obj.data!!
            }
            model.isPendingData = true
            return model
        }
        fun saveLocalTrainingsModel(obj: localTrainigPendingModel): Employee.LocalTrainings {
            var model = Employee.LocalTrainings()
            if (obj.data != null) {
                model = obj.data!!
            }
            model.isPendingData = true
            return model
        }
        fun saveForeignTrainingsModel(obj: foreignTrainingPendingModel): Employee.Foreigntrainings {
            var model = Employee.Foreigntrainings()
            if (obj.data != null) {
                model = obj.data!!
            }
            model.isPendingData = true
            return model
        }
        fun saveForeignTravelModel(obj: foreignTravelPendingModel): Employee.ForeignTravels {
            var model = Employee.ForeignTravels()
            if (obj.data != null) {
                model = obj.data!!
            }
            model.isPendingData = true
            return model
        }

        fun saveOfficialResidentialModel(obj: officialResidentialInfoPendingModel): Employee.OfficialResidentials {
            var model = Employee.OfficialResidentials()
            if (obj.data != null) {
                model = obj.data!!
            }
            model.isPendingData = true
            return model
        }



        fun decideWhatToLoadInNomineee(
            childSpinner: Spinner,
            indexNumber: Int,
            context: Context,
            employeeProfileData: EmployeeProfileData
        ) {

            // father  = 1
            // mother = 2
            // spouse = 3
            // child = 4
            //other = 5

        }


        fun getIndexOfSpinner(spinner: Spinner, myString: String): Int {
            for (i in 0 until spinner.count) {
                val compare: String =
                    (spinner.getItemAtPosition(i) as SpinnerDataModel).id.toString() + ""
                if (compare.equals(myString, ignoreCase = true)) {
                    return i
                }
            }
            return 0
        }

    }


}