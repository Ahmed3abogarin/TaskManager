package com.ahmed.taskmanager.presentation.home

import com.ahmed.taskmanager.domain.model.Task

sealed class HomeEvent {
    data class UpsertTask(val task: Task): HomeEvent()
    data class DeleteTask(val task: Task): HomeEvent()


    data class UpdateTaskFilter(val filter: Int): HomeEvent()
    data class UpdateTaskSort(val sortOrder: Int): HomeEvent()

}