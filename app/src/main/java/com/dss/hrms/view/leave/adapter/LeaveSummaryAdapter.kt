package com.dss.hrms.view.leave.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelLeaveApplicationLayoutBinding
import com.dss.hrms.databinding.ModelLeaveSummaryLayoutBinding
import com.dss.hrms.view.leave.`interface`.OnLeaveApplicationClickListener
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.leave.model.LeaveSummaryApiResponse
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class LeaveSummaryAdapter @Inject constructor() :
    RecyclerView.Adapter<LeaveSummaryAdapter.ViewHolder>() {
    var dataList: List<LeaveSummaryApiResponse.LeaveSummaryModifiedModel>? = null
    lateinit var context: Context
    lateinit var preparensce: MySharedPreparence


    fun setInitialData(
        dataList: List<LeaveSummaryApiResponse.LeaveSummaryModifiedModel>?,
        context: Context
    ) {
        this.dataList = dataList
        this.context = context
        preparensce = MySharedPreparence(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelLeaveSummaryLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_leave_summary_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var leaveSummary = dataList?.get(position)

        holder.binding.leaveSummaray = leaveSummary
        holder.binding.language = preparensce.getLanguage()

        if (position >= 1) {
            holder.binding.llHeader.visibility = View.GONE

        }
        dataList?.let {
            if (position + 1 >= it.size) {
                holder.binding.horizontalView.visibility = View.VISIBLE
            }else{
                holder.binding.horizontalView.visibility = View.GONE
            }
        }
    }

    class ViewHolder(binding: ModelLeaveSummaryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: ModelLeaveSummaryLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }
}