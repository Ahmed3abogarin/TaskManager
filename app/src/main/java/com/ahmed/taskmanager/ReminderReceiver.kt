package com.ahmed.taskmanager

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Task Reminder"
        val notificationManager = NotificationManagerCompat.from(context)

        val notification = NotificationCompat.Builder(context, "reminder_channel")
            .setSmallIcon(R.drawable.ic_filter_list) // Use your own icon
            .setContentTitle(title)
            .setContentText("It's time for your task.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Check for the POST_NOTIFICATIONS permission (required for Android 13+)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.v("TOOLL","Permission is not granted ")
            return
        }

        // Show the notification
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

