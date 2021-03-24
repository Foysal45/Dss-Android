package com.dss.hrms.view.payroll.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentPayrollTopBinding
import dagger.android.support.DaggerFragment


class PayrollTopFragment : DaggerFragment() {
    lateinit var binding: FragmentPayrollTopBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPayrollTopBinding.inflate(inflater, container, false)


        binding.cardEmployeeBankinformation.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_payrollTopFragment_to_employeeBankInformationFragment)
        }

        binding.cardSalaryProcess.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_payrollTopFragment_to_employeeSalaryProcessFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}