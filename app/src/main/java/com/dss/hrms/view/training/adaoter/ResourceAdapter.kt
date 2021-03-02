package com.dss.hrms.view.training.adaoter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelResourcePersonBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.`interface`.OnResourcePersonClickListener
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class ResourceAdapter @Inject constructor() : RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {
    lateinit var dataList: List<TrainingResponse.ResourcePerson>
    lateinit var context: Context
    lateinit var operation: Operation
    lateinit var onResourcePersonClickListener: OnResourcePersonClickListener
    lateinit var preparence: MySharedPreparence


    fun setInitialData(
        dataList: List<TrainingResponse.ResourcePerson>,
        context: Context,
        onResourcePersonClickListener: OnResourcePersonClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onResourcePersonClickListener = onResourcePersonClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelResourcePersonBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_resource_person, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var resourcePerson = dataList.get(position)
        holder.binding.resourcePerson = resourcePerson
        holder.binding.language = preparence.getLanguage()
        holder.binding.imgEdit.setOnClickListener {
            onResourcePersonClickListener.onClick(
                resourcePerson,
                position,
                Operation.EDIT
            )
        }
        holder.binding.imgDelete.setOnClickListener {
            onResourcePersonClickListener.onClick(
                resourcePerson,
                position,
                Operation.DELETE
            )
        }
    }


    class ViewHolder(binding: ModelResourcePersonBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelResourcePersonBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}