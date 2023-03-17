package com.dohyeok.gulpgulp.data.converter

import androidx.room.TypeConverter
import com.dohyeok.gulpgulp.util.toDatabaseFormat
import com.dohyeok.gulpgulp.util.toLocalDateFormat
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun fromTimestamp(timestamp: String): LocalDate {
        return timestamp.toLocalDateFormat
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate): String {
        return date.toDatabaseFormat
    }
}