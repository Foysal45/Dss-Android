package com.dss.hrms.view.notification

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityNotificationBinding
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.notification.adapter.NotificationAdapter
import com.dss.hrms.view.notification.model.NotificationResponse
import com.dss.hrms.view.notification.viewmodel.NotificationViewModel
import com.dss.hrms.view.receiver.NetworkChangeReceiver
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.ViewModelProviderFactory
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject

class NotificationActivity : BaseActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var preparence: MySharedPreparence


    lateinit var employeeViewModel: EmployeeViewModel

    lateinit var notificationViewModel: NotificationViewModel

    lateinit var binding: ActivityNotificationBinding

    lateinit var linearLayoutManager: LinearLayoutManager

    var dataList: List<NotificationResponse.Notification>? = null

    lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        context = this
        init()
        binding.backBtnIV.setOnClickListener {
            onBackPressed()
        }

        binding.viewModel = notificationViewModel

        notificationViewModel?.apply {
            var list = arrayListOf<NotificationResponse.Notification>()
            var dialog = CustomLoadingDialog().createLoadingDialog(this@NotificationActivity)
            getAllNotifications()
            notifications?.observe(this@NotificationActivity, Observer { notificatiosList ->
                dialog?.dismiss()

                notificatiosList?.let {
                    if (list.size > 0) {
                        list.addAll(it)
                        dataList = list
                        adapter.setNewNotification(dataList)
                    } else {
                        list.addAll(it)
                        dataList = list
                        prepareRecyleView()
                    }

                }
                Log.e(
                    "notificationacitivity",
                    "notification list .................................................: ${list?.size}"
                )
                if (list != null && list.size > 0) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.tvEmptyNotification.visibility = View.GONE
                } else {
                    binding.recyclerView.visibility = View.GONE
                    binding.tvEmptyNotification.visibility = View.VISIBLE
                }

            })
        }
    }

    fun prepareRecyleView() {
//        adapter = NotificationAdapter()
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = NotificationAdapter()
        adapter.setInitialData(
            dataList, this
        )
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.e(
                    "notificaiotnActivity",
                    "..........................................................................${linearLayoutManager.findFirstVisibleItemPosition()}"
                )
                notificationViewModel.caculateLoadMoreItem(
                    linearLayoutManager.findLastVisibleItemPosition(),
                    newState
                )
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }

    fun init() {
        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
        notificationViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NotificationViewModel::class.java)
    }

    companion object {
        lateinit var context: Context
    }

}