package com.ahmed.taskmanager.domain.usecases.tasks

import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.repository.AlarmScheduler

class SetAlarm(
    private val alarmScheduler: AlarmScheduler
) {
    operator fun invoke(triggerAtMillis: Long,task: Task){
        alarmScheduler.schedule(triggerAtMillis,task)
    }
}