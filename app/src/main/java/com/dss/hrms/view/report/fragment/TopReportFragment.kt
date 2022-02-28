package com.dss.hrms.view.report.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentLeaveTopBinding
import com.dss.hrms.databinding.FragmentTopReportBinding


class TopReportFragment : Fragment() {
    lateinit var binding: FragmentTopReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopReportBinding.inflate(inflater, container, false)

        binding.cardVacantPosition.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_topReportFragment_to_vacantPositionFragment)
        }

        binding.cardWorkingEmployee.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_topReportFragment_to_workingEmployeeListFragment)
        }

        return binding.root
    }

}