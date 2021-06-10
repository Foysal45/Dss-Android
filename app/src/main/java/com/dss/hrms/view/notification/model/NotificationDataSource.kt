package com.dss.hrms.view.notification.model

import android.content.Context
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.dss.hrms.retrofit.NotificationApiService
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.view.notification.NotificationActivity
import com.dss.hrms.view.notification.model.Utility.showProgressBar
import com.google.firebase.messaging.RemoteMessage
import com.namaztime.namaztime.database.MySharedPreparence
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationDataSource(private val ctx: Context) :
    PageKeyedDataSource<Int, NotificationResponse.Notification>() {

    private val platform = "mobile"
    var page_size = 5
    var first_page = 1
    lateinit var preparence: MySharedPreparence
    lateinit var context: Context


    init {
        this.context = ctx
        preparence = NotificationActivity.context?.let { MySharedPreparence(it) }!!
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NotificationResponse.Notification>
    ) {
        getUsers(callback)
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NotificationResponse.Notification>
    ) {
        val nextPageNo = params.key + 1
        getMoreUsers(params.key, nextPageNo, callback)
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NotificationResponse.Notification>
    ) {
        val previousPageNo = if (params.key > 1) params.key - 1 else 0
        getMoreUsers(params.key, previousPageNo, callback)
    }

    private fun getUsers(callback: LoadInitialCallback<Int, NotificationResponse.Notification>) {
        context.showProgressBar()
        var notificationApiService: NotificationApiService? =
            RetrofitInstance.retrofitInstance?.create(NotificationApiService::class.java)
        val call: Call<NotificationResponse?>? =
            notificationApiService?.getAllNotificationsWithoutCorotuins1(
                preparence.getLanguage()!!,
                "Bearer ${preparence.getToken()}",
                platform,
                first_page,
                page_size
            )
        call?.enqueue(object : Callback<NotificationResponse?> {
            override fun onResponse(
                call: Call<NotificationResponse?>,
                response: Response<NotificationResponse?>
            ) {
                Utility.hideProgressBar()
                val res = response.body()

                val data = res?.data?.data
                Log.e(
                    "message",
                    "response.............................................. : " + data
                )
                data?.let { callback.onResult(it, null, 2) }
            }

            override fun onFailure(call: Call<NotificationResponse?>, t: Throwable) {

            }

        })

    }

    private fun getMoreUsers(
        pageNo: Int,
        previousOrNextPageNo: Int,
        callback: LoadCallback<Int, NotificationResponse.Notification>
    ) {
      //   context.showProgressBar()
        var notificationApiService: NotificationApiService? =
            RetrofitInstance.retrofitInstance?.create(NotificationApiService::class.java)
        val call: Call<NotificationResponse?>? =
            notificationApiService?.getAllNotificationsWithoutCorotuins1(
                preparence.getLanguage()!!,
                "Bearer ${preparence.getToken()}",
                platform,
                pageNo,
                page_size
            )
        call?.enqueue(object : Callback<NotificationResponse?> {
            override fun onResponse(
                call: Call<NotificationResponse?>,
                response: Response<NotificationResponse?>
            ) {
                val res = response.body()
                val listUsers = res?.data?.data
                listUsers?.let { callback.onResult(it, previousOrNextPageNo) }
            }

            override fun onFailure(call: Call<NotificationResponse?>, t: Throwable) {

            }

//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//
//            }

        })

    }

}