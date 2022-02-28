package com.dss.hrms.view.training.adaoter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelBatchScheduleLayoutBinding
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.`interface`.OnBatchScheduleClickListener
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class BatchScheduleAdapter @Inject constructor() :
    RecyclerView.Adapter<BatchScheduleAdapter.ViewHolder>() {
    lateinit var dataList: List<BudgetAndSchedule.BatchSchedule?>
    lateinit var context: Context
    lateinit var operation: Operation
    lateinit var onBatchScheduleClickListener: OnBatchScheduleClickListener
    lateinit var preparence: MySharedPreparence


    fun setInitialData(
        dataList: List<BudgetAndSchedule.BatchSchedule?>,
        context: Context,
        onBatchScheduleClickListener: OnBatchScheduleClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onBatchScheduleClickListener = onBatchScheduleClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelBatchScheduleLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_batch_schedule_layout, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var batchSchedule = dataList.get(position)
        holder.binding.batchSchedule = batchSchedule
        holder.binding.language = preparence.getLanguage()
        holder.binding.imgEdit.setOnClickListener {
            onBatchScheduleClickListener.onClick(
                batchSchedule,
                position,
                Operation.EDIT
            )
        }
        holder.binding.imgDelete.setOnClickListener {
            onBatchScheduleClickListener.onClick(
                batchSchedule,
                position,
                Operation.DELETE
            )
        }
    }


    class ViewHolder(binding: ModelBatchScheduleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelBatchScheduleLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}