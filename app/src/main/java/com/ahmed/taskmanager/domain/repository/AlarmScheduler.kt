package com.ahmed.taskmanager.domain.repository

interface AlarmScheduler {
    fun schedule(taskId: Int,triggerAtMillis: Long,taskTitle: String)
}