package com.dss.hrms.view.notification.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList


class NotificationDataSourceFactory (private val context: Context) : DataSource.Factory<Int, NotificationResponse.Notification>() {

    val mutableLiveData = MutableLiveData<NotificationDataSource>()
    override fun create(): DataSource<Int, NotificationResponse.Notification> {
        val userDataSource = NotificationDataSource(context)
        mutableLiveData.postValue(userDataSource)
        return userDataSource
    }
}