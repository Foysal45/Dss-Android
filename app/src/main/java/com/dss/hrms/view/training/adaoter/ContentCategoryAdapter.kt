package com.dss.hrms.view.training.adaoter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelContentCategoryBinding
import com.dss.hrms.databinding.ModelEmployeeInfoBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.util.Operation
import com.dss.hrms.util.StaticKey
import com.dss.hrms.view.allInterface.OnEmployeeInfoClickListener
import com.dss.hrms.view.training.`interface`.OnContentCategoryClickListener
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class ContentCategoryAdapter @Inject constructor() :
    RecyclerView.Adapter<ContentCategoryAdapter.ViewHolder>() {


    lateinit var preparence: MySharedPreparence

    lateinit var context: Context

    private lateinit var dataList: List<TrainingResponse.Category>

    lateinit var onContentCategoryClickListener: OnContentCategoryClickListener


    fun setRequiredData(
        dataList: List<TrainingResponse.Category>,
        context: Context,
        onContentCategoryClickListener: OnContentCategoryClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onContentCategoryClickListener = onContentCategoryClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ModelContentCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_content_category,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   Log.e("adapter","adapter data  ; "+dataList.size)
        var category: TrainingResponse.Category = dataList.get(position)
        holder.binding?.tvCategoryName?.setText(
            if (preparence?.getLanguage()
                    .equals("en")
            ) category.category_name else category.category_name_bn
        )

        if (position % 2 == 0)
            holder.binding?.mainLayout?.setBackgroundResource(R.drawable.round_corner_shape_bg_white)
        else {
            holder.binding?.mainLayout?.setBackgroundResource(R.drawable.round_corner_shape_bg_light_gray)
        }

        holder.binding?.tvCategoryName?.setOnClickListener({
            onContentCategoryClickListener.onClick(category, Operation.VIEW, position)
        })
        holder.binding?.imgDelete?.setOnClickListener({
            onContentCategoryClickListener.onClick(category, Operation.DELETE, position)
        })
        holder.binding?.imgEdit?.setOnClickListener({
            onContentCategoryClickListener.onClick(category, Operation.EDIT, position)
        })

    }

    //the class is hodling the list view
    class ViewHolder(binding: ModelContentCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelContentCategoryBinding? = null

        init {
            this.binding = binding
        }
    }


    override fun getItemCount(): Int {
        return dataList?.size!!
    }
}
