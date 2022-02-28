package com.dss.hrms.view.notification.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.databinding.ModelLeaveSummaryLayoutBinding
import com.dss.hrms.databinding.ModelNotificationLayoutBinding
import com.dss.hrms.databinding.ModelVacantPositionSummaryLayoutBinding
import com.dss.hrms.model.ReportResponse
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.view.leave.model.LeaveSummaryApiResponse
import com.dss.hrms.view.notification.model.NotificationResponse
import com.namaztime.namaztime.database.MySharedPreparence
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import javax.inject.Inject

class NotificationAdapter @Inject constructor() :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    var dataList: List<NotificationResponse.Notification>? = arrayListOf()
    lateinit var context: Context
    lateinit var preparensce: MySharedPreparence


    fun setInitialData(
        dataList: List<NotificationResponse.Notification>?,
        context: Context
    ) {
        this.dataList = dataList
        this.context = context
        preparensce = MySharedPreparence(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ModelNotificationLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_notification_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var notification = dataList?.get(position)
        //  var notification = getItem(position)
        holder.binding.notification = notification


        context?.let {
            Glide.with(it).applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_empty_user_icon)
            ).load(RetrofitInstance.BASE_URL + notification?.notified_by?.photo)
                .placeholder(R.drawable.ic_empty_user_icon)
                .into(holder.binding?.imageView)
        }


        if (notification?.is_read == 0) {
            //  if (position % 2 == 0) {
            holder.binding.llMain.setBackgroundResource(R.color.light_blue)
        } else {
            holder.binding.llMain.setBackgroundResource(R.color.white)
        }

    }

    class ViewHolder(binding: ModelNotificationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: ModelNotificationLayoutBinding

        init {
            this.binding = binding
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    fun setNewNotification(
        dataList: List<NotificationResponse.Notification>?
    ) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    companion object {
        private val USER_COMPARATOR =
            object : DiffUtil.ItemCallback<NotificationResponse.Notification>() {
                override fun areItemsTheSame(
                    oldItem: NotificationResponse.Notification,
                    newItem: NotificationResponse.Notification
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: NotificationResponse.Notification,
                    newItem: NotificationResponse.Notification
                ): Boolean =
                    newItem == oldItem
            }
    }

}