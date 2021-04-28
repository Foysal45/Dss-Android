package com.dss.hrms.view.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelTrainingDashboardLayoutBinding
import com.dss.hrms.util.TrainingModule
import com.dss.hrms.view.training.model.TrainingDashBoard
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class TrainingDashboardAdapter @Inject constructor() :
    RecyclerView.Adapter<TrainingDashboardAdapter.ViewHolder>() {
    var dashBoard: TrainingDashBoard.Dashboard? = null
    lateinit var context: Context
    lateinit var preparence: MySharedPreparence
    lateinit var trainingModule: TrainingModule
    lateinit var onTrainingComponentClickListener: OnTrainingComponentClickListener


    fun setInitialData(
        dashBoard: TrainingDashBoard.Dashboard?,
        context: Context,
        trainingModule: TrainingModule,
        onTrainingComponentClickListener: OnTrainingComponentClickListener
    ) {
        this.dashBoard = dashBoard
        this.context = context
        this.trainingModule = trainingModule
        this.onTrainingComponentClickListener = onTrainingComponentClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelTrainingDashboardLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_training_dashboard_layout, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (trainingModule == TrainingModule.COURSE_SCHEDULE) {
            holder.binding.llCourseSchedule.visibility = View.VISIBLE
            holder.binding.llBatchSchedule.visibility = View.GONE
            holder.binding.llFaq.visibility = View.GONE
            var courseSchedule = dashBoard?.course_shedules?.get(position)
            holder.binding.courseSchedule = courseSchedule
            holder.binding.llCourseSchedule.setOnClickListener {
                onTrainingComponentClickListener.onShortClick(trainingModule)
            }
        } else if (trainingModule == TrainingModule.BATCH_SCHEDULE) {
            holder.binding.llCourseSchedule.visibility = View.GONE
            holder.binding.llBatchSchedule.visibility = View.VISIBLE
            holder.binding.llFaq.visibility = View.GONE
            var batch_shedules = dashBoard?.batch_shedules?.get(position)
            holder.binding.batchSchedule = batch_shedules
            holder.binding.llBatchSchedule.setOnClickListener {
                onTrainingComponentClickListener.onShortClick(trainingModule)
            }
        } else if (trainingModule == TrainingModule.FAQ) {
            holder.binding.llCourseSchedule.visibility = View.GONE
            holder.binding.llBatchSchedule.visibility = View.GONE
            holder.binding.llFaq.visibility = View.VISIBLE
            var faqs = dashBoard?.faqs?.get(position)
            holder.binding.faqs = faqs
            holder.binding.llFaq.setOnClickListener {
                onTrainingComponentClickListener.onShortClick(trainingModule)
            }
        }
        holder.binding.language = preparence.getLanguage()
        holder.binding.position = position+1
    }


    class ViewHolder(binding: ModelTrainingDashboardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ModelTrainingDashboardLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dashBoard?.course_shedules?.size!!
    }
}