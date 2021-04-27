package com.dss.hrms.view.report.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelLeaveSummaryLayoutBinding
import com.dss.hrms.databinding.ModelVacantPositionSummaryLayoutBinding
import com.dss.hrms.model.ReportResponse
import com.dss.hrms.view.leave.model.LeaveSummaryApiResponse
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class VacantPositionSummaryAdapter @Inject constructor() :
    RecyclerView.Adapter<VacantPositionSummaryAdapter.ViewHolder>() {
    var dataList: List<ReportResponse.VacantPositionSummary>? = null
    lateinit var context: Context
    lateinit var preparensce: MySharedPreparence


    fun setInitialData(
        dataList: List<ReportResponse.VacantPositionSummary>?,
        context: Context
    ) {
        this.dataList = dataList
        this.context = context
        preparensce = MySharedPreparence(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelVacantPositionSummaryLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_vacant_position_summary_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var vacantPositionSummary = dataList?.get(position)

        holder.binding.vacantPositionSummary = vacantPositionSummary
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

    class ViewHolder(binding: ModelVacantPositionSummaryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: ModelVacantPositionSummaryLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }
}