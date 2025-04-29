package com.ahmed.taskmanager.presentation.mainactivity

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed.taskmanager.ReminderReceiver
import com.ahmed.taskmanager.presentation.home.HomeViewModel
import com.ahmed.taskmanager.presentation.navgraph.TaskNavGraph
import com.ahmed.taskmanager.presentation.settings.ThemeViewModel
import com.ahmed.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeVM by viewModels<HomeViewModel>()

        createNotificationChannel(this) // Call it once when the app launches
        //This ensures Android knows the notification channel exists before any notification is sent.

        requestNotificationPermission()

        val now = Calendar.getInstance()
        val timeInMillis = now.apply {
            add(Calendar.MINUTE, 3)
        }.timeInMillis

        scheduleReminder(this, timeInMillis, "Time for your task")

        enableEdgeToEdge()
        setContent {
            val viewmodel: ThemeViewModel = hiltViewModel()

            val theme = viewmodel.selectedTheme.value
            TaskManagerTheme(selectedTheme = theme) {
                TaskNavGraph()

            }
        }
    }
    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
    }
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Reminders"
        val descriptionText = "Channel for task reminders"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("reminder_channel", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}

// Keeping this function in ViewModel for logic purposes, but call it in the Activity
fun scheduleReminder(context: Context, timeInMillis: Long, title: String) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("title", title)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        timeInMillis.toInt(),
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
        timeInMillis,
        pendingIntent
    )
}



