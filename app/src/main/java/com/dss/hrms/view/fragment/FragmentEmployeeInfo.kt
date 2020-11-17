package com.dss.hrms.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.model.Employee
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.`interface`.OnEmployeeInfoClickListener
import com.dss.hrms.view.adapter.EmployeeInfoAdapter
import com.dss.hrms.view.dialog.*
import kotlinx.android.synthetic.main.fragment_employee_info.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentPermanentAddresses : Fragment(), OnEmployeeInfoClickListener {
    // TODO: Rename and change types of parameters
    private var position: Int? = null
    private var addButtonWiilAppear = false
    private var key: String? = null
    lateinit var adapter: EmployeeInfoAdapter
    lateinit var v: View
    lateinit var binding: FragmentPermanentAddresses
    var dataList: List<Any>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position", 0)
            key = it.getString("key")
            addButtonWiilAppear = it.getBoolean("addWillAppear")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_info, container, false)
        v = inflater.inflate(R.layout.fragment_employee_info, container, false)
        Log.e("position", "position : " + position)
        initRV()
        v.fab.setOnClickListener({
            operation(key, null, StaticKey.CREATE)
        })

        if (addButtonWiilAppear) {
            v.fab.visibility = View.VISIBLE
        } else {
            v.fab.visibility = View.GONE
        }
        return v
    }

    override fun onStart() {
        super.onStart()


    }

    private fun initRV() {
        when (key) {
            StaticKey.PERMANENT_ADDRESS -> {
                dataList = MainActivity.employee?.permanentAddresses
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().PermanentAddresses())
                Log.e("list size", "list size :  " + dataList?.size)
            }
            StaticKey.PRESENT_ADDRESS -> {
                dataList = MainActivity.employee?.presentAddresses
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().PresentAddresses())
            }
            StaticKey.EducationalQualifications -> {
                dataList =
                    MainActivity.employee?.educationalQualifications
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().EducationalQualifications())
            }
            StaticKey.Jobjoining -> {
                dataList = MainActivity.employee?.jobjoinings
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().Jobjoinings())
            }
            StaticKey.Quota -> {
                dataList = MainActivity.employee?.employee_quotas
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().EmployeeQuotas())
            }
            StaticKey.Spouse -> {
                dataList = MainActivity.employee?.spouses
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().Spouses())
            }
            StaticKey.Children -> {
                dataList = MainActivity.employee?.childs
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().Childs())
            }

            StaticKey.Language -> {
                dataList = MainActivity.employee?.languages
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().Languages())
            }


            StaticKey.LocalTraining -> {
                dataList = MainActivity.employee?.local_trainings
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().LocalTrainings())
            }


            StaticKey.ForeingTraining -> {
                dataList = MainActivity.employee?.foreigntrainings
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().Foreigntrainings())
            }


            StaticKey.OfficialResidentials -> {
                dataList = MainActivity.employee?.official_residentials
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().OfficialResidentials())
            }


            StaticKey.ForeignTravel -> {
                dataList = MainActivity.employee?.foreign_travels
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().ForeignTravels())
            }


            StaticKey.AdditionalQualifications -> {
                dataList = MainActivity.employee?.additional_qualifications
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().AdditionalQualifications())
            }


            StaticKey.Publication -> {
                dataList = MainActivity.employee?.publications
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().Publications())
            }


            StaticKey.HonoursAwards -> {
                dataList = MainActivity.employee?.honours_awards
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().HonoursAwards())
            }


            StaticKey.DisciplinaryAction -> {
                dataList = MainActivity.employee?.disciplinaryActions
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().DisciplinaryAction())
            }


            StaticKey.Promotion -> {
                dataList = MainActivity.employee?.promotions
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().Promotions())
            }

            StaticKey.References -> {
                dataList = MainActivity.employee?.references
                if (dataList == null || dataList?.size == 0)
                    dataList = listOf(Employee().References())
            }
        }

        if (dataList != null) {
            adapter = dataList?.let {
                activity?.let { it1 ->
                    EmployeeInfoAdapter(
                        it1,
                        it, key!!, this
                    )
                }
            }!!
            v.recyclerView.setLayoutManager(
                LinearLayoutManager(
                    activity,
                    RecyclerView.VERTICAL,
                    false
                )
            )
            v.recyclerView.setAdapter(adapter)
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentPermanentAddresses().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(any: Any, key: String) {

        operation(key, any, StaticKey.EDIT)
    }


    private fun operation(key: String?, any: Any?, operation: String?) {
        when (key) {
            StaticKey.PERMANENT_ADDRESS -> {
                if (operation.equals(StaticKey.EDIT)) {
                    activity?.let { it2 ->
                        EditPermanentAddressInfo().showDialog(
                            it2,
                            any as Employee.PermanentAddresses,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation.equals(StaticKey.CREATE)) {
                    activity?.let { it2 ->
                        EditPermanentAddressInfo().showDialog(
                            it2,
                            Employee().PermanentAddresses(),
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.PRESENT_ADDRESS -> {
                if (operation.equals(StaticKey.EDIT)) {
                    activity?.let { it2 ->
                        EditPresentAddressInfo().showDialog(
                            it2,
                            any as Employee.PresentAddresses,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation.equals(StaticKey.CREATE)) {
                    activity?.let { it2 ->
                        EditPresentAddressInfo().showDialog(
                            it2,
                            Employee().PresentAddresses(),
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.EducationalQualifications -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditEducationQualificationInfo().showDialog(
                            it2,
                            any as Employee.EducationalQualifications,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditEducationQualificationInfo().showDialog(
                            it2,
                            Employee().EducationalQualifications(),
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.Jobjoining -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditJobJoiningInformation().showDialog(
                            it2,
                            any as Employee.Jobjoinings
                        )
                    }
                }
            }
            StaticKey.Quota -> {
                //  Toast.makeText(activity, "quota", Toast.LENGTH_SHORT).show()
                Log.e("quota", "quota : ")
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditQuotaInfo().showDialog(
                            it2,
                            any as Employee.EmployeeQuotas
                        )
                    }
                }
            }
            StaticKey.Spouse -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreateSpouseInfo().showDialog(
                            it2,
                            any as Employee.Spouses,
                            StaticKey.EDIT
                        )
                    }

                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreateSpouseInfo().showDialog(
                            it2,
                            Employee().Spouses(),
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.Children -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreateChildInfo().showDialog(
                            it2,
                            any as Employee.Childs,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreateChildInfo().showDialog(
                            it2,
                            Employee().Childs(),
                            StaticKey.CREATE
                        )
                    }
                }
            }
            StaticKey.Language -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreateLanguageInfo().showDialog(
                            it2,
                            any as Employee.Languages,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreateLanguageInfo().showDialog(
                            it2,
                            Employee().Languages(),
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.LocalTraining -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreateLocalTrainingInfo().showDialog(
                            it2,
                            any as Employee.LocalTrainings,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreateLocalTrainingInfo().showDialog(
                            it2,
                            Employee().LocalTrainings(),
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.ForeingTraining -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreateForeignTrainingInfo().showDialog(
                            it2,
                            any as Employee.Foreigntrainings,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreateForeignTrainingInfo().showDialog(
                            it2,
                            Employee().Foreigntrainings(),
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.OfficialResidentials -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditOfficialResidentialIInfo().showDialog(
                            it2,
                            any as Employee.OfficialResidentials,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditOfficialResidentialIInfo().showDialog(
                            it2,
                            Employee().OfficialResidentials(),
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.ForeignTravel -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreateForeignTravelInfo().showDialog(
                            it2,
                            any as Employee.ForeignTravels,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreateForeignTravelInfo().showDialog(
                            it2,
                            Employee().ForeignTravels(),
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.AdditionalQualifications -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreateAdditionalQualificationInfo().showDialog(
                            it2,
                            any as Employee.AdditionalQualifications,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreateAdditionalQualificationInfo().showDialog(
                            it2,
                            Employee().AdditionalQualifications(),
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.Publication -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreatePublicatioInfo().showDialog(
                            it2,
                            any as Employee.Publications,
                            StaticKey.EDIT
                        )
                    }

                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreatePublicatioInfo().showDialog(
                            it2,
                            Employee().Publications(),
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.HonoursAwards -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditAndCreateHonoursAwardInfo().showDialog(
                            it2,
                            any as Employee.HonoursAwards,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditAndCreateHonoursAwardInfo().showDialog(
                            it2,
                            Employee().HonoursAwards(),
                            StaticKey.CREATE
                        )
                    }
                }
            }

            StaticKey.PostingRecord -> {

            }

            StaticKey.DisciplinaryAction -> {

            }

            StaticKey.Promotion -> {

            }

            StaticKey.References -> {
                if (operation?.equals(StaticKey.EDIT)!!) {
                    activity?.let { it2 ->
                        EditReferenceInfo().showDialog(
                            it2,
                            any as Employee.References,
                            StaticKey.EDIT
                        )
                    }
                } else if (operation?.equals(StaticKey.CREATE)!!) {
                    activity?.let { it2 ->
                        EditReferenceInfo().showDialog(
                            it2,
                            Employee().References(),
                            StaticKey.CREATE
                        )
                    }
                }
            }
        }
    }
}