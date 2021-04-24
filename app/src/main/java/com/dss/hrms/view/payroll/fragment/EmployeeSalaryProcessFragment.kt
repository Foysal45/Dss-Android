package com.dss.hrms.view.payroll.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.databinding.FragmentEmployeeSalaryProcessBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.allInterface.OnDateListener
import com.dss.hrms.view.allInterface.OnYearMonthListener
import com.dss.hrms.view.fragment.MonthYearPickerDialog
import com.dss.hrms.view.payroll.adapter.SalaryGenerateAdapter
import com.dss.hrms.view.payroll.model.Row
import com.dss.hrms.view.payroll.viewmodel.SalaryGenerateViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import dagger.android.support.DaggerFragment
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate
import java.util.*
import javax.inject.Inject


class EmployeeSalaryProcessFragment : DaggerFragment() {
    @Inject
    lateinit var viewmodelProviderFactory: ViewModelProviderFactory

    lateinit var salaryGenerateViewModel: SalaryGenerateViewModel

    val cal: Calendar = Calendar.getInstance().apply { time = Date() }

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    @Inject
    lateinit var preparence: MySharedPreparence

    var employee: Employee? = null

    var salaryRowDataList: List<Row>? = null

    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var binding: FragmentEmployeeSalaryProcessBinding

    lateinit var adapter: SalaryGenerateAdapter

    var year: String = ""
    var month: String = "";
    var loadingDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeSalaryProcessBinding.inflate(inflater, container, false)
        init()
        year = cal.get(Calendar.YEAR).toString()
        month = (cal.get(Calendar.MONTH) + 1).toString()
        binding.includeDate.tvText.setText("${month}-${year}")
        Log.e("date", "year : ${year} month : ${month}")
        employee = employeeProfileData.employee
        binding.name =
            if (preparence.getLanguage().equals("en")) employee?.name else employee?.name_bn


        binding.includeDate.llBody.setOnClickListener {
            activity?.let {
                val string = "1900-01-01"
                val date =
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        LocalDate.parse(string, DateTimeFormatter.ISO_DATE)
                    } else {
                        null
                    }

                val date1 =
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        Date.from(date?.atStartOfDay(ZoneId.systemDefault())?.toInstant())
                    } else {
                        null
                    }
                date1?.let { it1 ->
                    MonthYearPickerDialog(it1, object : OnYearMonthListener {
                        override fun onYearMonth(y: String, m: String) {
                            year = y
                            month = m
                            Log.e("date", "year : ${year} month : ${month}")
                            binding.includeDate.tvText.setText("${month}-${year}")
                        }

                    }).show(
                        childFragmentManager.beginTransaction(),
                        ""
                    )
                }
//                MonthYearPickerDialog().apply {
////                    setListener {
//////                            view, year, month, dayOfMonth ->
//////                        Toast.makeText(
//////                            requireContext(),
//////                            "Set date: $year/$month/$dayOfMonth",
//////                            Toast.LENGTH_LONG
//////                        ).show()
//////                    }
//                 fragmentManager?.let { it1 -> show(it1, "MonthYearPickerDialog") }
//               }
            }
        }
        binding.ivSearch.setOnClickListener {
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            salaryGenerateViewModel.getSalaryData(
                (employee?.user?.employee_id).toString(),
                employee?.office_id.toString(),
                year,
                month
            )
        }

        salaryGenerateViewModel.apply {
            loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            Log.e(
                "salaryprocess",
                "salary process employee_id : ${employee?.user?.employee_id} office id :  ${employee?.office_id} "
            )
            getSalaryData(
                (employee?.user?.employee_id).toString(),
                employee?.office_id.toString(),
                year,
                month
            )

            salaryRow.observe(viewLifecycleOwner, Observer { salaryRow ->
                loadingDialog?.dismiss()
                salaryRowDataList = salaryRow

                if (salaryRowDataList == null)
                    salaryRowDataList = arrayListOf()
                Log.e("salaryprocessresponse", "salary process header : ${salaryRowDataList?.size}")
                prepareRecyleView()
                salaryRow?.let {
                    //     salaryRowDataList = it
                    //  prepareRecyleView()

                }
            })
        }
        return binding.root
    }


    fun prepareRecyleView() {
        linearLayoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = SalaryGenerateAdapter()
        activity?.let { adapter.setInitialData(salaryRowDataList, it) }
        binding.recyclerView.adapter = adapter

    }

    private fun init() {
        salaryGenerateViewModel = ViewModelProvider(
            this,
            viewmodelProviderFactory
        ).get(SalaryGenerateViewModel::class.java)
    }
}