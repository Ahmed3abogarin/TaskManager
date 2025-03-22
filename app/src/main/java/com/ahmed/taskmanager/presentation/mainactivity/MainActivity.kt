package com.ahmed.taskmanager.presentation.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed.taskmanager.presentation.settings.ThemeViewModel
import com.ahmed.taskmanager.presentation.navgraph.TaskNavGraph
import com.ahmed.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewmodel: ThemeViewModel = hiltViewModel()
            TaskManagerTheme(selectedTheme = viewmodel.selectedTheme.value) {
                TaskNavGraph()

            }
        }
    }
}
