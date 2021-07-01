package com.jumei.foreground

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val CHANNEL_ID = "my notification channel id"

class ForeGroundService : LifecycleService() {
    val numberLiveData = MutableLiveData(0)
    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return MyBinder()
    }

    override fun onCreate() {
        super.onCreate()

        lifecycleScope.launch {
            while (true) {
                delay(1_000)
                numberLiveData.value = numberLiveData.value?.plus(1)
            }
        }
        createChannel()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("this is a title...")
            .setContentText("this is a notification...")
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
    }

    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Notification_CHANNEL_NO.1"
            val descriptionText = "Notification_TEXT"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

    }

    inner class MyBinder : Binder() {
        val service = this@ForeGroundService
    }
}