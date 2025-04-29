package com.ahmed.taskmanager.presentation.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed.taskmanager.presentation.navgraph.TaskNavGraph
import com.ahmed.taskmanager.presentation.settings.ThemeViewModel
import com.ahmed.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(this) // Call it once when the app launches
        //This ensures Android knows the notification channel exists before any notification is sent.

        requestNotificationPermission()




        enableEdgeToEdge()
        setContent {
            val viewmodel: ThemeViewModel = hiltViewModel()

            val theme = viewmodel.selectedTheme.value
            TaskManagerTheme(selectedTheme = theme) {
                TaskNavGraph()

            }
        }
    }
    private fun requestNotificationPermission() {
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



