package com.dohyeok.gulpgulp.data.converter

import androidx.room.TypeConverter
import com.dohyeok.gulpgulp.util.toDatabaseFormat
import com.dohyeok.gulpgulp.util.toLocalTimeFormat
import java.time.LocalTime

class LocalTimeConverter {
    @TypeConverter
    fun fromTimestamp(timestamp: String): LocalTime {
        return timestamp.toLocalTimeFormat
    }

    @TypeConverter
    fun TimeToTimestamp(time: LocalTime): String {
        return time.toDatabaseFormat
    }
}