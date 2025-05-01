package com.ahmed.taskmanager.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.repository.AlarmScheduler
import com.ahmed.taskmanager.presentation.alarm.ReminderReceiver

class AlarmRepositoryImpl(private val context: Context) : AlarmScheduler {
    override fun schedule(taskId: Int, triggerAtMillis: Long, task: Task) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", task.title)
            putExtra("time", task.time.hour.toString() + ":" + task.time.minute.toString())
            putExtra("date", task.dueDate.dayOfMonth.toString() + " " + task.dueDate.month.name)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            triggerAtMillis.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Check permissions for exact alarm scheduling
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                val cintent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                context.startActivity(cintent)
                return
            }
        }

        // Set the alarm to trigger at the specified time
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )


    }
}