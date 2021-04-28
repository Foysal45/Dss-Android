package com.dss.hrms.view.training

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentTopReportBinding
import com.dss.hrms.databinding.FragmentTrainingBinding
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.util.TrainingModule
import com.dss.hrms.view.training.`interface`.OnBatchScheduleClickListener
import com.dss.hrms.view.training.adaoter.BatchScheduleAdapter
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.model.TrainingDashBoard
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.view.training.viewmodel.TrainingManagementViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_training.view.*
import kotlinx.android.synthetic.main.personal_information_create_field_header.view.*
import javax.inject.Inject


class TrainingFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var binding: FragmentTrainingBinding
    lateinit var trainingManagementViewModel: TrainingManagementViewModel

    var dashBoard: TrainingDashBoard.Dashboard? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrainingBinding.inflate(inflater, container, false)
        init()


        binding.hCourseSchedule.tvClose.visibility = View.GONE
        binding.hBatchSchedule.tvClose.visibility = View.GONE
        binding.hFaq.tvClose.visibility = View.GONE

        trainingManagementViewModel.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            getTrainingDashboard()
            trainingDashboard.observe(viewLifecycleOwner, Observer {
                dialog?.dismiss()

                it?.let {
                    Log.e("dashboard ", " dashboard : ${it}")
                    dashBoard = it
                    prepareBatchRecycleView()
                    prepareCourseRecycleView()
                    prepareFaqRecycleView()
                }
            })
        }

        binding.llCourseSchedule.setOnClickListener {
            gotoDestination(R.id.action_trainingFragment_to_courseScheduleFragment)
        }
        binding.llBatchSchedule.setOnClickListener {
            gotoDestination(R.id.action_trainingFragment_to_batchScheduleFragment)
        }
        binding.llFaq.setOnClickListener {
            gotoDestination(R.id.action_trainingFragment_to_contentFaqFragment)
        }
        return binding.root
    }


    fun init() {
        trainingManagementViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(TrainingManagementViewModel::class.java)

    }

    fun prepareCourseRecycleView() {
        var adapter = TrainingDashboardAdapter()
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerViewCourse.layoutManager = linearLayoutManager
        activity?.let {
            adapter.setInitialData(
                dashBoard,
                it,
                TrainingModule.COURSE_SCHEDULE,
                object : OnTrainingComponentClickListener {
                    override fun onShortClick(trainingModule: TrainingModule) {
                        gotoDestination(R.id.action_trainingFragment_to_courseScheduleFragment)
                    }

                }
            )
        }
        binding.recyclerViewCourse.adapter = adapter
    }

    fun prepareBatchRecycleView() {
        var adapter = TrainingDashboardAdapter()
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerViewBatch.layoutManager = linearLayoutManager
        activity?.let {
            adapter.setInitialData(
                dashBoard,
                it,
                TrainingModule.BATCH_SCHEDULE,
                object : OnTrainingComponentClickListener {
                    override fun onShortClick(trainingModule: TrainingModule) {
                        gotoDestination(R.id.action_trainingFragment_to_batchScheduleFragment)
                    }

                }
            )
        }
        binding.recyclerViewBatch.adapter = adapter
    }

    fun prepareFaqRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerViewFaq.layoutManager = linearLayoutManager
        var adapter = TrainingDashboardAdapter()
        activity?.let {
            adapter.setInitialData(
                dashBoard,
                it,
                TrainingModule.FAQ,
                object : OnTrainingComponentClickListener {
                    override fun onShortClick(trainingModule: TrainingModule) {
                        gotoDestination(R.id.action_trainingFragment_to_contentFaqFragment)
                    }

                }
            )
        }
        binding.recyclerViewFaq.adapter = adapter
    }


    fun gotoDestination(id: Int) {
        Navigation.findNavController(binding.root)
            .navigate(id)
    }

}