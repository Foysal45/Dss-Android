package com.dss.hrms.view.training.adaoter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelCourseScheduleLayoutBinding
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.`interface`.OnCourseScheduleClickListener
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class CourseScheduleAdapter @Inject constructor() :
    RecyclerView.Adapter<CourseScheduleAdapter.ViewHolder>() {
    lateinit var dataList: List<BudgetAndSchedule.CourseSchedule?>
    lateinit var context: Context
    lateinit var operation: Operation
    lateinit var onCourseScheduleClickListener: OnCourseScheduleClickListener
    lateinit var preparence: MySharedPreparence


    fun setInitialData(
        dataList: List<BudgetAndSchedule.CourseSchedule?>,
        context: Context,
        onCourseScheduleClickListener: OnCourseScheduleClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onCourseScheduleClickListener = onCourseScheduleClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelCourseScheduleLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_course_schedule_layout, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var courseSchedule = dataList.get(position)
        holder.binding.courseSchedule = courseSchedule
        holder.binding.language = preparence.getLanguage()
        holder.binding.imgEdit.setOnClickListener {
            onCourseScheduleClickListener.onClick(
                courseSchedule,
                position,
                Operation.EDIT
            )
        }
        holder.binding.imgDelete.setOnClickListener {
            onCourseScheduleClickListener.onClick(
                courseSchedule,
                position,
                Operation.DELETE
            )
        }
    }


    class ViewHolder(binding: ModelCourseScheduleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelCourseScheduleLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}