package com.dss.hrms.view.training.adaoter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelCourseLayoutBinding
import com.dss.hrms.model.CourseModel
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.`interface`.OnResourcePersonClickListener
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class CoursesAdapter @Inject constructor() : RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {
    lateinit var dataList: List<CourseModel>
    lateinit var context: Context
    lateinit var operation: Operation
    lateinit var onResourcePersonClickListener: OnResourcePersonClickListener
    lateinit var preparence: MySharedPreparence


    fun setInitialData(
        dataList: List<CourseModel>,
        context: Context,
        onResourcePersonClickListener: OnResourcePersonClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onResourcePersonClickListener = onResourcePersonClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ModelCourseLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_course_layout, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val module = dataList[position]
        holder.binding.course = module
        holder.binding.language = preparence.getLanguage()


        holder.binding.imgEdit.setOnClickListener {
//            onResourcePersonClickListener.onClick(
//                resourcePerson,
//                position,
//                Operation.EDIT
//            )
        }
        holder.binding.imgDelete.setOnClickListener {
//            onResourcePersonClickListener.onClick(
//                resourcePerson,
//                position,
//                Operation.DELETE
//            )
        }
    }


    class ViewHolder(binding: ModelCourseLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelCourseLayoutBinding = binding

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}