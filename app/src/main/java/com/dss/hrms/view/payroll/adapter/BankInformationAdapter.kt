package com.dss.hrms.view.payroll.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelBankInformationLayoutBinding
import com.dss.hrms.util.Operation
import com.dss.hrms.view.payroll.`interface`.OnPayRollInfoClickListener
import com.dss.hrms.view.payroll.model.PayRollBankInfo
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class BankInformationAdapter @Inject constructor() :
    RecyclerView.Adapter<BankInformationAdapter.ViewHolder>() {

    lateinit var dataList: List<PayRollBankInfo?>
    lateinit var context: Context
    lateinit var onPayrollInfoClickListener: OnPayRollInfoClickListener
    lateinit var preparence: MySharedPreparence


    fun setInitialData(
        dataList: List<PayRollBankInfo?>,
        context: Context,
        onPayrollInfoClickListener: OnPayRollInfoClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onPayrollInfoClickListener = onPayrollInfoClickListener
        this.preparence = MySharedPreparence(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelBankInformationLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_bank_information_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var bankInfo = dataList?.get(position)
        holder.binding.bankInfo = bankInfo
        holder.binding.language = preparence.getLanguage()

        holder.binding.imgEdit.setOnClickListener {
            onPayrollInfoClickListener.onClick(bankInfo,position,Operation.EDIT)
        }


    }


    class ViewHolder(binding: ModelBankInformationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        lateinit var binding: ModelBankInformationLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size!!

    }
}