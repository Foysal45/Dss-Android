package com.dss.hrms.view.leave.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentLeaveTopBinding
import dagger.android.support.DaggerFragment

class LeaveTopFragment : DaggerFragment() {


    lateinit var binding: FragmentLeaveTopBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeaveTopBinding.inflate(inflater, container, false)

        binding.cardLeaveSummaryDashboard.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_leaveTopFragment_to_leaveSummaryFragment)
        }

        binding.cardLeaveApplication.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_leaveTopFragment_to_leaveApplicationFragment)
        }

        return binding.root
    }

}