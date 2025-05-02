package com.ahmed.taskmanager.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun LocalTime.convertToListTime(): List<String> {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return this.format(formatter).split(" ")
//            val date = Date(time)
//            val format = SimpleDateFormat("dd MMM yyyy")
//            return format.format(date)
}

fun LocalTime.convertToTime(): String{
    return this.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

fun LocalDate.convertDate(): String{
    return this.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy"))
}

