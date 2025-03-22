package com.ahmed.taskmanager.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String, // Optional
    val priority: Priority,
    val dueDate: String,
    val done: Boolean = false,
): Parcelable
