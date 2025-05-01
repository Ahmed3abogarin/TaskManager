package com.ahmed.taskmanager.domain.repository

import com.ahmed.taskmanager.domain.model.Task

interface AlarmScheduler {
    fun schedule(taskId: Int,triggerAtMillis: Long,task: Task)

//    fun cancelTaskAlarm(context: Context, taskId: Int) {
//        val intent = Intent(context, ReminderReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            taskId,
//            intent,
//            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
//        )
//        pendingIntent?.let {
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.cancel(it)
//        }
//    }

}