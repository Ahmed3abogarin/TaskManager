package com.ahmed.taskmanager.domain.model

import androidx.compose.ui.graphics.Color
import com.ahmed.taskmanager.ui.theme.HighPriorityColor
import com.ahmed.taskmanager.ui.theme.LowPriorityColor
import com.ahmed.taskmanager.ui.theme.MediumPriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(Color.Transparent)
}