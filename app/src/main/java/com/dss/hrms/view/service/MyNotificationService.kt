package com.dss.hrms.view.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color.RED
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import com.dss.hrms.BuildConfig
import com.dss.hrms.R
import com.dss.hrms.view.login.LoginActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        displayNotification("Dss", "test notification")

    }


    fun displayNotification(title: String, body: String) {
        val soundUri =
            Uri.parse("android.resource://" + applicationContext.packageName + "/" + android.R.anim.accelerate_decelerate_interpolator)
        val v = longArrayOf(500, 1000)

        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //  val bitmap = applicationContext.vectorToBitmap(R.drawable.ic_schedule_black_24dp)
        val titleNotification = title
        val subtitleNotification = body
        val pendingIntent = getActivity(applicationContext, 1, intent, 0)
        val notification = NotificationCompat.Builder(
            applicationContext,
            getString(R.string.default_notification_channel_id)
        )
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(titleNotification)
            .setContentText(subtitleNotification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(v)
            .setSound(soundUri)

        notification.priority = PRIORITY_MAX


        if (SDK_INT >= O) {
            val channel =
                NotificationChannel(
                    getString(R.string.default_notification_channel_id),
                    "Default channel",
                    IMPORTANCE_HIGH
                )


            channel.description = "Default channel"
            // val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(CONTENT_TYPE_SONIFICATION)
                .build()

            channel.importance = IMPORTANCE_HIGH
            channel.enableLights(true)
            channel.lightColor = RED
            channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            channel.enableVibration(true)
            channel.vibrationPattern = v
            channel.setSound(soundUri, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(5, notification.build())

    }

    companion object {
        const val NOTIFICATION_ID = "namaztime_notification_id"
        const val NOTIFICATION_NAME = "namaztime"
        const val NOTIFICATION_CHANNEL = BuildConfig.APPLICATION_ID
        const val NOTIFICATION_WORK = "namaztime_notification_work"
    }
}