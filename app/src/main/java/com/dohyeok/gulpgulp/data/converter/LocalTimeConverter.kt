package com.dohyeok.gulpgulp.data.converter

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeConverter {
    @TypeConverter
    fun fromTimeStamp(timeStamp: String): LocalTime {
        return LocalTime.parse(timeStamp, DateTimeFormatter.ofPattern("H:mm:ss"))
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String {
        return time.format(DateTimeFormatter.ofPattern("H:mm:ss"))
    }
}