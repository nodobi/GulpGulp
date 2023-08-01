package com.dohyeok.gulpgulp.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity(tableName = "drink_records")
data class DrinkRecord constructor(
    @Embedded(prefix = "drink_") var drink: Drink,
    @ColumnInfo(name = "date") var date: LocalDate,
    @ColumnInfo(name = "time") var time: LocalTime
){
    @ColumnInfo(name = "recordId")
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
}