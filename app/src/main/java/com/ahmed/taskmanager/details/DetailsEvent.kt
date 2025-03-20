package com.ahmed.taskmanager.details

import com.ahmed.taskmanager.domain.model.Task

sealed class DetailsEvent {
    data class UpsertTask(val task: Task): DetailsEvent()
    data class DeleteTask(val task: Task): DetailsEvent()
}