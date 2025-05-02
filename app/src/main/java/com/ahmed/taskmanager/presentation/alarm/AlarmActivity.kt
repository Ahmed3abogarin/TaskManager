package com.ahmed.taskmanager.presentation.alarm

import android.app.KeyguardManager
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.taskmanager.presentation.components.PulsingCircle
import com.ahmed.taskmanager.presentation.components.animatedGradientBackground

class AlarmActivity : ComponentActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("title") ?: "It's time"
        val time = intent.getStringExtra("time") ?: "Not specify"
        val date = intent.getStringExtra("date") ?: "Not specify"


        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )


        setShowWhenLocked(true)
        setTurnScreenOn(true)

        // Optional: dismiss keyguard (needs extra permissions on newer versions)
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (keyguardManager.isKeyguardSecure || keyguardManager.isDeviceLocked) {
            keyguardManager.requestDismissKeyguard(this, null)
        }


        // Start alarm sound
        mediaPlayer =
            MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        mediaPlayer.isLooping = true
        mediaPlayer.start()


        setContent {
            AlarmScreen(
                title = title,
                time = time,
                date = date,
                onDismiss = {
                mediaPlayer.stop()
                mediaPlayer.release()
                finish()

            })
        }
    }
}

@Composable
fun AlarmScreen(onDismiss: () -> Unit,title: String,time: String,date: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedGradientBackground()),
        contentAlignment = Alignment.Center
    ) {

        PulsingCircle()

        // static circle (can be replaced with logo)
        Box(
            modifier = Modifier
                .size(140.dp)
                .shadow(shape = RoundedCornerShape(100.dp), elevation = 8.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color(0xFFA8E6CF))
                .clickable { onDismiss() }, contentAlignment = Alignment.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Task time", color = Color.White)
                Text(text = "Tap to cancel", fontSize = 12.sp, color = Color.White)
            }


        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = time, fontSize = 56.sp, color = Color.White)
            Text(text = date, fontSize = 12.sp, color = Color.White)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, color = Color.White)
        }


    }

}