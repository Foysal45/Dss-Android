package com.dss.hrms.util

import android.content.Context
import android.graphics.Color
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dss.hrms.R
import com.dss.hrms.databinding.PersonalInformationHeaderFieldBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.Paysacle
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.commonSpinnerDataLoad.CommonModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.pendingDataModel.*

class HelperClass {
    companion object {
        val PEDING_DATA = "pending_data_obj"
        val COMMON_DATA = "common_data_dropdown"


        fun addHeaderColor(
            view: PersonalInformationHeaderFieldBinding,
            ctx: Context,
            isPending: Boolean
        ) {
            if (isPending) {
                view.headerContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        ctx,
                        R.color.headerColor
                    )
                )
            } else {
                view.headerContainer.setBackgroundColor(Color.TRANSPARENT)
            }

        }


        fun SavePresentAddresssModel(obj: PresentAddressPendingModel): Employee.PresentAddresses {
            var model = Employee.PresentAddresses()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id

            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun SavePeramnentAddresssModel(obj: PermanentAddressPendingModel): Employee.PermanentAddresses {
            var model = Employee.PermanentAddresses()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveJobJoiningModel(obj: PendingDataModel.jobJoiningInformationPendingModel): Employee.Jobjoinings {
            var model = Employee.Jobjoinings()
            if (obj.data != null) {
                model = obj.data
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveQuotaModel(obj: QuotaInformationPendingModel): Employee.EmployeeQuotas {
            var model = Employee.EmployeeQuotas()
            if (obj.data != null) {
                model = obj.data
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveEducationQualityModel(obj: EducationalQualificationsPendingModel): Employee.EducationalQualifications {
            var model = Employee.EducationalQualifications()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveLanguageModel(obj: languageInfoPendingModel): Employee.Languages {
            var model = Employee.Languages()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveLocalTrainingsModel(obj: localTrainigPendingModel): Employee.LocalTrainings {
            var model = Employee.LocalTrainings()
            if (obj.data != null) {

                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveForeignTrainingsModel(obj: foreignTrainingPendingModel): Employee.Foreigntrainings {
            var model = Employee.Foreigntrainings()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveForeignTravelModel(obj: foreignTravelPendingModel): Employee.ForeignTravels {
            var model = Employee.ForeignTravels()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveOfficialResidentialModel(obj: officialResidentialInfoPendingModel): Employee.OfficialResidentials {
            var model = Employee.OfficialResidentials()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveAddiiontalQualificationModel(obj: addtionalProfeQualiPendingModel): Employee.AdditionalQualifications {
            var model = Employee.AdditionalQualifications()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun savePublication(obj: publicationsPendingModel): Employee.Publications {
            var model = Employee.Publications()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveHonoursAndAward(obj: honoursAndAwardPendingModel): Employee.HonoursAwards {
            var model = Employee.HonoursAwards()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun savDesciplinary(obj: desciplinaryActionPendingModel): Employee.DisciplinaryAction {
            var model = Employee.DisciplinaryAction()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveReference(obj: referencePendingModel): Employee.References {
            var model = Employee.References()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveNominne(obj: nominePendingModel): Employee.Nominee {
            var model = Employee.Nominee()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveChild(obj: childrenInfoPendingModel): Employee.Childs {
            var model = Employee.Childs()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun saveEmployee(obj: EmployeeModel): Employee {
            var model = Employee()
            if (obj.data != null) {
                model = obj.data
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        /*
           storePassword 'lovfresh123#'
            keyAlias 'lovfresh'
            keyPassword 'lovfresh123#'
         */
        fun saveSpouse(obj: spousePendingModel): Employee.Spouses {
            var model = Employee.Spouses()
            if (obj.data != null) {
                model = obj.data!!
                model.id = obj.id
            }
            model.isPendingData = true
            model.parent_id = obj.parent_id
            return model
        }

        fun SetHeaderText(heading: String, view: TextView) {

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

        // fill the  common data
        fun getCommonDataFilltered(id: Int, list: List<CommonModel>?, isBangla: Boolean): String {
            var selection = ""

            if (list != null) {
                for (item in list) {
                    if (item.id == id) {
                        selection = if (isBangla) {
                            item.name_bn
                        } else item.name

                        break
                    }
                }
            }

            return selection.toString()
        }

        fun getPayScallFilltered(id: Int, list: List<Paysacle>?, isBangla: Boolean): String {
            var selection = ""

            if (list != null) {
                for (item in list) {
                    if (item.id == id) {
                        selection = if (isBangla) {
                            item.amount + ""
                        } else item.amount + ""

                        break
                    }
                }
            }

            return selection.toString()
        }

    }


}