package com.dss.hrms.view.notification.model

import com.dss.hrms.model.RoleWiseEmployeeResponseClass

class NotificationResponse {
    val status: String? = null
    val message: String? = null
    val code: Int? = null
    val data: NotificationData? = null


    data class NotificationData(
        val data: List<Notification>? = null
    )

    data class Notification(
        val id: Int? = null,
        val notification_text: String? = null,
        val message: String? = null,
        val module: String? = null,
        val sub_module: String? = null,
        val item_id: Int? = null,
        val is_read: Int? = null,
        val notifier_id: Int? = null,
        val link: String? = null,
        val created_at: String? = null,
        val time_diff: String? = null,
        val notified_by: RoleWiseEmployeeResponseClass.RoleWiseEmployee? = null

    )
}


