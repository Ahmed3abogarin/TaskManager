package com.ahmed.taskmanager.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun LocalTime.convertToTime(): List<String> {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return this.format(formatter).split(" ")
//            val date = Date(time)
//            val format = SimpleDateFormat("dd MMM yyyy")
//            return format.format(date)
}

