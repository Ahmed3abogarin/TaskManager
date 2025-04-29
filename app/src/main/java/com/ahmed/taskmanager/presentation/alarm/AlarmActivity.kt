package com.ahmed.taskmanager.presentation.alarm

import android.app.KeyguardManager
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class AlarmActivity: ComponentActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setShowWhenLocked(true)
        setTurnScreenOn(true)

        // Optional: dismiss keyguard (needs extra permissions on newer versions)
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (keyguardManager.isKeyguardSecure || keyguardManager.isDeviceLocked) {
            keyguardManager.requestDismissKeyguard(this, null)
        }

        // Start alarm sound
        mediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        mediaPlayer.isLooping = true
        mediaPlayer.start()


        setContent {
            AlarmScreen(onDismiss = {
                mediaPlayer.stop()
                mediaPlayer.release()
                finish()

            })
        }
    }
}


@Composable
fun AlarmScreen(onDismiss: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Red
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("⏰ Alarm!", fontSize = 32.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onDismiss) {
                Text("Stop Alarm")
            }
        }
    }
}
