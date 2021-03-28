package com.dss.hrms.view.leave.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelLeaveApplicationLayoutBinding
import com.dss.hrms.util.Operation
import com.dss.hrms.view.leave.`interface`.OnLeaveApplicationClickListener
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class LeaveApplicationAdapter @Inject constructor() :
    RecyclerView.Adapter<LeaveApplicationAdapter.ViewHolder>() {
    var dataList: List<LeaveApplicationApiResponse.LeaveApplication>? = null
    lateinit var context: Context
    lateinit var preparensce: MySharedPreparence
    lateinit var onLeaveApplicationClickListener: OnLeaveApplicationClickListener


    fun setInitialData(
        dataList: List<LeaveApplicationApiResponse.LeaveApplication>?,
        context: Context, onLeaveApplicationClickListener: OnLeaveApplicationClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onLeaveApplicationClickListener = onLeaveApplicationClickListener
        preparensce = MySharedPreparence(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelLeaveApplicationLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_leave_application_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var leaveApplication = dataList?.get(position)

        holder.binding.leaveApplication = leaveApplication
        holder.binding.language = preparensce.getLanguage()
        holder.binding.imgEdit.setOnClickListener {
            onLeaveApplicationClickListener.onClick(leaveApplication, position, Operation.EDIT)
        }


    }


    class ViewHolder(binding: ModelLeaveApplicationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: ModelLeaveApplicationLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

}