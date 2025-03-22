package com.ahmed.taskmanager.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String, // Optional
    val priority: Priority,
    val dueDate: String,
    val time: String = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")),
    val done: Boolean = false,
): Parcelable
