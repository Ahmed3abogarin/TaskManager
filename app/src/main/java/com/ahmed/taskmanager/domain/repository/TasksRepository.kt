package com.ahmed.taskmanager.domain.repository

import com.ahmed.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun upsertTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getTasksList(filter: Int, sortOrder: Int): Flow<List<Task>>
}