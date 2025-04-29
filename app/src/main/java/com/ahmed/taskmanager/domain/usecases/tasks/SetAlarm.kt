package com.ahmed.taskmanager.domain.usecases.tasks

import com.ahmed.taskmanager.domain.repository.AlarmScheduler

class SetAlarm(
    private val alarmScheduler: AlarmScheduler
) {
    operator fun invoke(taskId: Int,triggerAtMillis: Long,taskTitle: String){
        alarmScheduler.schedule(taskId,triggerAtMillis,taskTitle)
    }
}