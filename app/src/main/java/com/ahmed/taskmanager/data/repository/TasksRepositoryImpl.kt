package com.ahmed.taskmanager.data.repository

import com.ahmed.taskmanager.data.TasksDao
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

class TasksRepositoryImpl(
    private val tasksDao: TasksDao,
) : TasksRepository {
    override suspend fun upsertTask(task: Task) {
        tasksDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task)
    }

    override  fun getTasks(): Flow<List<Task>> {
        return tasksDao.getTasks()
    }

}