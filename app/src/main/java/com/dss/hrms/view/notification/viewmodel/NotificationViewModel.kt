package com.dss.hrms.view.notification.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.dss.hrms.repository.EmployeeInfoRepo
import com.dss.hrms.view.notification.model.NotificationResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    var PAGE: Int = 1
    val LIMIT: Int = 10

    //val PLATFORM: String = "mobile"
    val PLATFORM: String = ""


    @Inject
    lateinit var employeeInfoRepo: EmployeeInfoRepo

    private var canLoadMore = true
    private val _showLoadingMore = MutableLiveData<Boolean>()
    val showLoadingMore: LiveData<Boolean> = _showLoadingMore

    protected val _listNotification = ArrayList<NotificationResponse.Notification?>()
    private var _notifications: MutableLiveData<List<NotificationResponse.Notification>>? =
        MutableLiveData()
    var notifications: LiveData<List<NotificationResponse.Notification>>? =
        _notifications
    private var _totalUnRead: MutableLiveData<Int>? =
        MutableLiveData()
    var totalUnRead: LiveData<Int>? = _totalUnRead


    fun getAllNotifications(
    ) {
        viewModelScope.launch {
            var response =
                employeeInfoRepo.getAllNotifications(PLATFORM, LIMIT, PAGE)

            if (response is NotificationResponse) {
                // Log.e("employeeviewmodel","response : ${response.data}")
                canLoadMore = response?.data?.data?.size!! >= LIMIT
                _listNotification.addAll(response?.data?.data!!)
                _totalUnRead?.postValue(response.data?.total_unread)
                _notifications?.postValue(response.data?.data)
            } else {
                _notifications?.postValue(null)
            }
        }
    }


    fun caculateLoadMoreItem(lastItem: Int, state: Int) {
        if (
            _listNotification.size >= LIMIT
            && lastItem == _listNotification.size - 1
            && state == RecyclerView.SCROLL_STATE_IDLE
        ) {
            loadMoreNotifications()
        }
    }


    private fun loadMoreNotifications(
    ) {

        if (_showLoadingMore.value == true || !canLoadMore) {
            return
        }
        _showLoadingMore.postValue(true)
        viewModelScope.launch {
            var response =
                employeeInfoRepo.getAllNotifications(PLATFORM, LIMIT, PAGE + 1)
            _showLoadingMore.postValue(false)
            if (response is NotificationResponse) {
                // Log.e("employeeviewmodel","response : ${response.data}")

                if (response?.data?.data?.size!! > 0) {
                    canLoadMore = response?.data?.data?.size!! >= LIMIT
                    PAGE = response?.data?.current_page!!
                    _listNotification.addAll(response?.data?.data!!)
                    _notifications?.postValue(response.data?.data)
                } else {

                }
            } else {
                _notifications?.postValue(null)
            }
        }
    }

}