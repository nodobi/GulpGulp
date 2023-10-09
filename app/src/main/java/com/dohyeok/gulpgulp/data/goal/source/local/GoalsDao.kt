package com.dohyeok.gulpgulp.data.goal.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dohyeok.gulpgulp.data.goal.Goal
import com.google.android.material.circularreveal.CircularRevealHelper.Strategy
import java.time.LocalDate

@Dao
interface GoalsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoal(goal: Goal)

    @Query("SELECT * FROM user_goal WHERE goal_date = :date")
    fun selectGoal(date: LocalDate): Goal

    @Query("SELECT * FROM user_goal")
    fun selectAllGoals(): List<Goal>

    @Query("SELECT * FROM user_goal WHERE goal_date LIKE :yearMonthTimeStamp")
    fun selectGoalsWithYearMonth(yearMonthTimeStamp: String): List<Goal>

    @Query("UPDATE user_goal SET goal_complete = :goal_complete WHERE goal_date = :goal_date")
    fun updateComplete(goal_date: LocalDate, goal_complete: Boolean)
}