package com.dohyeok.gulpgulp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "drink_goals")
class DrinkGoal constructor(
    @PrimaryKey @ColumnInfo("date") var date: LocalDate,
    @ColumnInfo("amount") var amount: Int,
    @ColumnInfo("isComplete") var isComplete: Boolean = false
) {
}