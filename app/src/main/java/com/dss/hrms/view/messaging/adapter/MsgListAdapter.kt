package com.dss.hrms.view.messaging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelMsgLayoutBinding
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation
import com.dss.hrms.view.messaging.model.MessageModel
import com.dss.hrms.view.training.`interface`.OnResourcePersonClickListener
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class MsgListAdapter @Inject constructor() : RecyclerView.Adapter<MsgListAdapter.ViewHolder>() {
    lateinit var dataList: List<MessageModel>
    lateinit var context: Context
    lateinit var operation: Operation
    lateinit var onResourcePersonClickListener: msgListClickListener
    lateinit var preparence: MySharedPreparence


    fun setInitialData(
        dataList: List<MessageModel>,
        context: Context,
        onResourcePersonClickListener: msgListClickListener
    ) {
        this.dataList = dataList
        this.context = context
        this.onResourcePersonClickListener = onResourcePersonClickListener
        preparence = MySharedPreparence(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ModelMsgLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_msg_layout, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg = dataList[position]
        holder.binding.msg = msg
        holder.binding.language = preparence.getLanguage()


        holder.binding.constraintRootLayout.setOnClickListener {
            onResourcePersonClickListener.onClick(
                msg,
                position,
                Operation.EDIT
            )
        }
        holder.binding.imgDelete.setOnClickListener {
//            onResourcePersonClickListener.onClick(
//                resourcePerson,
//                position,
//                Operation.DELETE
//            )
        }
    }


    class ViewHolder(binding: ModelMsgLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ModelMsgLayoutBinding = binding

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface msgListClickListener {
        fun onClick(
            model: MessageModel,
            position: Int,
            operation: Operation
        )
    }


}