package com.dss.hrms.view.training.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentBatchScheduleBinding
import com.dss.hrms.databinding.FragmentCourseScheduleBinding
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.`interface`.OnBatchScheduleClickListener
import com.dss.hrms.view.training.`interface`.OnCourseScheduleClickListener
import com.dss.hrms.view.training.adaoter.BatchScheduleAdapter
import com.dss.hrms.view.training.adaoter.CourseScheduleAdapter
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class CourseScheduleFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var budgetAndScheduleViewModel: BudgetAndScheduleViewModel

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataList: List<BudgetAndSchedule.CourseSchedule?>
    lateinit var batchScheduleAdapter: CourseScheduleAdapter

    lateinit var binding: FragmentCourseScheduleBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCourseScheduleBinding.inflate(inflater, container, false)
        init()
        budgetAndScheduleViewModel.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            getCourseSchedule()
            courseSchedule.observe(viewLifecycleOwner, Observer {
                dialog?.dismiss()
                dataList = it
                dataList += it
                dataList += it
                dataList += it
                dataList += it
                prepareRecycleView()

            })
        }

        return binding.root
    }


    fun init() {
        budgetAndScheduleViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(BudgetAndScheduleViewModel::class.java)
    }

    fun prepareRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        batchScheduleAdapter = CourseScheduleAdapter()
        activity?.let {
            batchScheduleAdapter.setInitialData(
                dataList,
                it,
                object : OnCourseScheduleClickListener {
                    override fun onClick(
                        courseSchedule: BudgetAndSchedule.CourseSchedule?,
                        position: Int,
                        operation: Operation
                    ) {
                        when (operation) {
                            Operation.EDIT ->
                                Toast.makeText(activity, "" + operation, Toast.LENGTH_LONG).show()

                            Operation.CREATE ->
                                Toast.makeText(activity, "" + operation, Toast.LENGTH_LONG).show()

                        }
                    }

                })
        }
        binding.recyclerView.adapter = batchScheduleAdapter
    }
}