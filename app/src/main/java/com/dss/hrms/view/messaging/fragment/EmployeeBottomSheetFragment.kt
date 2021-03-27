package com.dss.hrms.view.messaging.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentEmployeeBottomSheetBinding
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.view.messaging.`interface`.OnEmployeeClickListener
import com.dss.hrms.view.messaging.adapter.EmployeeAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class EmployeeBottomSheetFragment : BottomSheetDialogFragment() {
    private val args by navArgs<EmployeeBottomSheetFragmentArgs>()


    var selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()
    lateinit var binding: FragmentEmployeeBottomSheetBinding
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: EmployeeAdapter
    lateinit var dataList: List<RoleWiseEmployeeResponseClass.RoleWiseEmployee>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeBottomSheetBinding.inflate(inflater, container, false)
        selectedDataList = arrayListOf<RoleWiseEmployeeResponseClass.RoleWiseEmployee>()
        dataList = args.employee.toList()
        dataList?.let {
            prepareRecycleView()
        }
        Log.e("EmployeeBottomSheet", "EmployeeBottomSheetFragment : ${args.employee.size}")
        binding.tvOk.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "key",
                selectedDataList
            )
            findNavController().popBackStack()
        }
        return binding?.root
    }

    fun prepareRecycleView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recycleView.layoutManager = linearLayoutManager
        adapter = EmployeeAdapter()
        activity?.let {
            adapter.setInitialData(
                dataList,
                it,
                object : OnEmployeeClickListener {
                    override fun onClick(
                        roleWiseEmployee: RoleWiseEmployeeResponseClass.RoleWiseEmployee,
                        position: Int,
                        isChecked: Boolean
                    ) {
                        if (isChecked) {
                            roleWiseEmployee.isSelected = true
                            selectedDataList.add(roleWiseEmployee)
                        } else {
                            roleWiseEmployee.isSelected = false
                            selectedDataList.remove(roleWiseEmployee)
                        }
                        Toast.makeText(activity, "isChecked ${isChecked}", Toast.LENGTH_LONG).show()
                    }

                })
        }
        binding.recycleView.adapter = adapter
    }


}