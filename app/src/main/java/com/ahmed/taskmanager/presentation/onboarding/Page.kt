package com.ahmed.taskmanager.presentation.onboarding

import androidx.annotation.RawRes
import com.ahmed.taskmanager.R

data class Page(
    @RawRes val animation: Int,
    val text: String,
)

val pages = listOf(
    Page(
        animation = R.raw.splash,
        text = "A smart application that help users manage their tasks in an organized manner."
    ),
    Page(
        animation = R.raw.onboarding_2,
        text = "Create task, modify existing ones, swipe to delete, filter by status, sort by priority."
    ),
    Page(
        animation = R.raw.onboarding_3,
        text = "Stay on top of every task with smart reminders that show the task title, even when your device is in sleep mode."
    )
)

