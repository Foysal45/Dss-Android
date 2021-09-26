package com.dss.hrms.util

import android.content.Context
import android.widget.Spinner
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.pendingDataModel.PresentAddressPendingModel
import com.google.gson.GsonBuilder

class HelperClass {
    companion object {
        val PEDING_DATA = "pending_data_obj"


        fun  SavePresentAddresssModel(obj: PresentAddressPendingModel): Employee.PresentAddresses {
            var model = Employee.PresentAddresses()
            if(obj.data != null){
                model = obj.data!!
            }
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