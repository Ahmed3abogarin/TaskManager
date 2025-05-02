package com.ahmed.taskmanager.domain.repository

import android.content.Context
import com.ahmed.taskmanager.domain.model.Task

interface AlarmScheduler {
    fun schedule(triggerAtMillis: Long,task: Task)
    fun cancelTaskAlarm(taskId: Int)

}