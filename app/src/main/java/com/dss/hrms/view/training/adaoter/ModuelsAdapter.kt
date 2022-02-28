package com.dss.hrms.view.training.adaoter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelModuleLayoutBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.`interface`.OnResourcePersonClickListener
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class ModuelsAdapter @Inject constructor() : RecyclerView.Adapter<ModuelsAdapter.ViewHolder>() {
    lateinit var dataList: List<TrainingResponse.TrainingModules>
    lateinit var context: Context
    lateinit var operation: Operation
    lateinit var onResourcePersonClickListener: OnResourcePersonClickListener
    lateinit var preparence: MySharedPreparence


    fun setInitialData(
        dataList: List<TrainingResponse.TrainingModules>,
        context: Context,
        onResourcePersonClickListener: OnResourcePersonClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onResourcePersonClickListener = onResourcePersonClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ModelModuleLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_module_layout, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var module = dataList[position]
        holder.binding.module = module
        holder.binding.language = preparence.getLanguage()

        holder.binding.tvobjective.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(module.objective, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(module.objective)
        }
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


    class ViewHolder(binding: ModelModuleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelModuleLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}