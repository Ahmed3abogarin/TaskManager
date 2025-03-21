package com.ahmed.taskmanager.domain.repository

import com.ahmed.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun upsertTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getTasks(): Flow<List<Task>>
    fun getByLowPriority(): Flow<List<Task>>
    fun getByHighPriority(): Flow<List<Task>>
}