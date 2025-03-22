package com.ahmed.taskmanager.presentation.create

import com.ahmed.taskmanager.domain.model.Task

sealed class CreateScreenEvent{
    data class UpsertTask(val task: Task): CreateScreenEvent()
}