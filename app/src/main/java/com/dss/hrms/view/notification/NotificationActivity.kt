package com.dss.hrms.view.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityNotificationBinding
import com.dss.hrms.util.CustomLoadingDialog
import com.dss.hrms.view.activity.BaseActivity
import com.dss.hrms.view.notification.adapter.NotificationAdapter
import com.dss.hrms.view.notification.model.NotificationResponse
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

    lateinit var binding: ActivityNotificationBinding

    lateinit var linearLayoutManager: LinearLayoutManager

    var dataList: List<NotificationResponse.Notification>? = null

    lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        init()

        binding.backBtnIV.setOnClickListener {
            onBackPressed()
        }

        employeeViewModel?.apply {
            var dialog = CustomLoadingDialog().createLoadingDialog(this@NotificationActivity)
            getAllNotifications("mobile")
            notifications?.observe(this@NotificationActivity, Observer { notificatiosList ->
                dialog?.dismiss()



                notificatiosList?.let {
                    var list = arrayListOf<NotificationResponse.Notification>()
                    list.addAll(it)
//                    list.addAll(it)
//                    list.addAll(it)
//                    list.addAll(it)
//                    list.addAll(it)
                    dataList = list
                    prepareRecyleView()
                }

                if (notificatiosList != null && notificatiosList.size > 0) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.tvEmptyNotification.visibility = View.GONE
                } else {
                    binding.recyclerView.visibility = View.GONE
                    binding.tvEmptyNotification.visibility = View.VISIBLE
                }

            })
//            notifications?.value?.let {
//                dataList = it
//                prepareRecyleView()
//            }

        }
    }

    fun prepareRecyleView() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = NotificationAdapter()
        adapter.setInitialData(
            dataList, this
        )
        binding.recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }

    fun init() {
        employeeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
    }

}