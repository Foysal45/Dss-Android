package com.dss.hrms.view.payroll.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelSalaryProcessLayoutBinding
import com.dss.hrms.view.payroll.model.Row
import com.dss.hrms.view.payroll.model.SalaryGenerateResponse
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class SalaryGenerateAdapter @Inject constructor() :
    RecyclerView.Adapter<SalaryGenerateAdapter.ViewHolder>() {


    var dataList: List<Row>? = null
    lateinit var context: Context
    lateinit var preparence: MySharedPreparence


    fun setInitialData(dataList: List<Row>?, context: Context) {
        this.dataList = dataList
        this.context = context
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelSalaryProcessLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_salary_process_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var rowData = dataList?.get(position)
        holder.binding.language = preparence.getLanguage()
        holder.binding.salaryInfoRow = rowData
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class ViewHolder(binding: ModelSalaryProcessLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ModelSalaryProcessLayoutBinding

        init {
            this.binding = binding
        }

    }

}