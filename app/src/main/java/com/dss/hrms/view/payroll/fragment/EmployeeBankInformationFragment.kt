package com.dss.hrms.view.payroll.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentEmployeeBankInformationBinding
import com.dss.hrms.di.mainScope.EmployeeProfileData
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.payroll.`interface`.OnPayRollInfoClickListener
import com.dss.hrms.view.payroll.adapter.BankInformationAdapter
import com.dss.hrms.view.payroll.model.PayRollBankInfo
import com.dss.hrms.view.payroll.viewmodel.PayRollBankInformationViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.example.dagger_android_practical.di.viewmodel.ViewModelFactoryModule
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject


class EmployeeBankInformationFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var employeeProfileData: EmployeeProfileData

    var employee: Employee? = null

    lateinit var binding: FragmentEmployeeBankInformationBinding
    lateinit var dataList: List<PayRollBankInfo?>
    lateinit var layoutManager: LinearLayoutManager
    lateinit var payrollBankInformationViewmodel: PayRollBankInformationViewModel

    lateinit var adapter: BankInformationAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeBankInformationBinding.inflate(inflater, container, false)
        employee = employeeProfileData.employee
        init()
        payrollBankInformationViewmodel.apply {
            var loadingDialog = CustomLoadingDialog().createLoadingDialog(activity)
            getBankInfo(employee?.user?.employee_id)

            bankInfo.observe(viewLifecycleOwner, androidx.lifecycle.Observer { data ->
                loadingDialog?.dismiss()
                data?.let {
                    dataList = it
                    prepareRecycleView()

                }
            })
        }

        return binding.root
    }


    fun prepareRecycleView() {
        layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        adapter = BankInformationAdapter()
        activity?.let {
            adapter.setInitialData(dataList, it, object : OnPayRollInfoClickListener {
                override fun onClick(any: Any?, position: Int, operation: Operation) {

                }

            })
        }

        binding.recyclerView.adapter = adapter

    }


    private fun init() {
        payrollBankInformationViewmodel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(PayRollBankInformationViewModel::class.java)
    }
}