package com.dss.hrms.view.messaging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelEmployeeListLayoutBinding
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.Operation
import com.dss.hrms.view.messaging.`interface`.OnEmployeeClickListener
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import javax.inject.Inject

class EmployeeAdapter @Inject constructor() :
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    lateinit var dataList: List<RoleWiseEmployeeResponseClass.RoleWiseEmployee?>
    lateinit var context: Context
    lateinit var onEmployeeClickListener: OnEmployeeClickListener
    lateinit var preparence: MySharedPreparence

    @Inject
    lateinit var requestManager: RequestManager

    fun setInitialData(
        dataList: List<RoleWiseEmployeeResponseClass.RoleWiseEmployee?>,
        context: Context,
        onEmployeeClickListener: OnEmployeeClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onEmployeeClickListener = onEmployeeClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelEmployeeListLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_employee_list_layout, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var employee = dataList.get(position)
        holder.binding.employee = employee
        holder.binding.language = preparence.getLanguage()

        Glide.with(context).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_baseline_image_24)
        ).load(RetrofitInstance.BASE_URL + employee?.photo)
            .into(holder.binding.imageView)


        holder.binding.checkBox.setOnClickListener {
            employee?.let { it1 ->
                onEmployeeClickListener.onClick(
                    it1,
                    position,
                    holder.binding.checkBox.isChecked
                )
            }
        }
    }

    class ViewHolder(binding: ModelEmployeeListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ModelEmployeeListLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}