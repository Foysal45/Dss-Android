package com.dss.hrms.view.training.adaoter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.FragmentContentsContentBinding
import com.dss.hrms.databinding.ModelContentsContentBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.view.training.`interface`.OnContentsContentClickListener
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class ContentsContentAdapter @Inject constructor() :
    RecyclerView.Adapter<ContentsContentAdapter.ViewHolder>() {
    var dataList: List<TrainingResponse.Content>? = null
    var context: Context? = null
    var onContentsContentClickListener: OnContentsContentClickListener? = null
    var preparence: MySharedPreparence? = null


    fun setInitialData(
        dataList: List<TrainingResponse.Content>,
        context: Context,
        onContentsContentClickListener: OnContentsContentClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onContentsContentClickListener = onContentsContentClickListener
        preparence = MySharedPreparence(context)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var binding: ModelContentsContentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_contents_content,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentsContentAdapter.ViewHolder, position: Int) {
        var content = dataList?.get(position)

        holder.binding?.tvContentName?.setText(content?.content_title)
        holder.binding?.tvContentNameBn?.setText(content?.content_title_bn)
        holder.binding?.tvCategory?.setText( context?.getString(R.string.catagory) + ": " +


            if (preparence?.getLanguage()
                    .equals("en")
            ) content?.category?.category_name else content?.category?.category_name_bn
        )



        holder.binding?.tvIsPublished?.setText(
            context?.getString(R.string.is_published) + ": " +
                    if (content?.is_published == 1) "" + context?.getString(
                        R.string.yes
                    ) else context?.getString(R.string.no)
        )

        holder.binding?.tvDes?.setText("https://translate.google.com/?sl=en&tl=bn&op=translate")

    }

    class ViewHolder(binding: ModelContentsContentBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelContentsContentBinding? = null

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }
}