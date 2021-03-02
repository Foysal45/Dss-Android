package com.dss.hrms.view.training.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.databinding.FragmentBatchScheduleBinding
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.`interface`.OnBatchScheduleClickListener
import com.dss.hrms.view.training.adaoter.BatchScheduleAdapter
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class BatchScheduleFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var budgetAndScheduleViewModel: BudgetAndScheduleViewModel

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataList: List<BudgetAndSchedule.BatchSchedule?>
    lateinit var batchScheduleAdapter: BatchScheduleAdapter

    lateinit var binding: FragmentBatchScheduleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBatchScheduleBinding.inflate(inflater, container, false)

        init()
        budgetAndScheduleViewModel.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(activity)
            getBatchSchedule()
            batchSchedule.observe(viewLifecycleOwner, Observer {
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
        batchScheduleAdapter = BatchScheduleAdapter()
        activity?.let {
            batchScheduleAdapter.setInitialData(
                dataList,
                it,
                object : OnBatchScheduleClickListener {
                    override fun onClick(
                        batchSchedule: BudgetAndSchedule.BatchSchedule?,
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