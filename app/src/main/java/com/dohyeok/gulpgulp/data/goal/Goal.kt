package com.dohyeok.gulpgulp.data.goal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "user_goal")
data class Goal(
    @PrimaryKey
    @ColumnInfo("goal_date") var date: LocalDate,
    @ColumnInfo("goal_amount") var amount: Int,
    @ColumnInfo("goal_complete") var isComplete: Boolean = false
) {
}