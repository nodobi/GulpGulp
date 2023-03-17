package com.dohyeok.gulpgulp.data

import androidx.annotation.IdRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(tableName = "drinks")
class Drink constructor(
    @ColumnInfo(name = "iconres") @IdRes var iconId: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "date") var date: LocalDate,
    @ColumnInfo(name = "time") var time: LocalTime
) {
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

}