package com.ahmed.taskmanager.data.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class DateTimeConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString() // e.g., "2025-04-29"
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.toString() // e.g., "14:30"
    }

    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it) }
    }
}