package com.ahmed.taskmanager.presentation.home

import com.ahmed.taskmanager.domain.model.Task

sealed class HomeEvent {
    data class UpsertTask(val task: Task): HomeEvent()
    data class DeleteTask(val task: Task): HomeEvent()
    data object GetTasks : HomeEvent()
    data class GetSortTasks(val sort: Int): HomeEvent()
}