package com.dohyeok.gulpgulp.data.converter

    import androidx.room.TypeConverter
    import com.dohyeok.gulpgulp.util.toLocalDate
    import com.dohyeok.gulpgulp.util.toTimeStamp
    import java.time.LocalDate
    import java.time.format.DateTimeFormatter


class LocalDateConverter {
    @TypeConverter
    fun fromTimeStamp(timeStamp: String): LocalDate = timeStamp.toLocalDate

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toTimeStamp
}