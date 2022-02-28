package com.dss.hrms.view.training.adaoter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelContentsContentBinding
import com.dss.hrms.databinding.ModelFaqLayoutBinding
import com.dss.hrms.model.TrainingResponse
import javax.inject.Inject

class FaqAdapter @Inject constructor() : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {
    lateinit var context: Context
    lateinit var dataList: List<TrainingResponse.Faq>


    fun setInitialData(context: Context, dataList: List<TrainingResponse.Faq>) {
        this.context = context
        this.dataList = dataList
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(binding: ModelFaqLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelFaqLayoutBinding? = null

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelFaqLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_faq_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var faq = dataList.get(position)

        holder.binding?.tvNameEn?.setText(faq.faq_question)
        holder.binding?.tvCounter?.setText("${position + 1}")
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val decoded: String =
                Html.fromHtml(faq.faq_answer, Html.FROM_HTML_MODE_COMPACT)
                    .toString()
            holder.binding?.tvAnswer?.setText(decoded)
        } else {

        }
        if (position % 2 == 0)
            holder.binding?.mainLayout?.setBackgroundResource(R.drawable.round_corner_shape_bg_white)
        else {
            holder.binding?.mainLayout?.setBackgroundResource(R.drawable.round_corner_shape_bg_light_gray)
        }

    }
}