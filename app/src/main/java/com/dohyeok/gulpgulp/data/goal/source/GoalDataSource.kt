package com.dohyeok.gulpgulp.data.goal.source

import com.dohyeok.gulpgulp.data.goal.Goal
import java.time.LocalDate

interface GoalDataSource {
    suspend fun saveGoal(goal: Goal, isUpdateSP: Boolean = false)
    suspend fun getGoal(date: LocalDate, isUseSP: Boolean = true): Goal?
    suspend fun getAllGoals(): List<Goal>
    suspend fun getMonthGoals(year: Int, monthValue: Int): List<Goal>

    suspend fun completeGoal(goal: Goal)
//    suspend fun completeGoal(date: LocalDate)
    suspend fun inCompleteGoal(goal: Goal)
//    suspend fun inCompleteGoal(date: LocalDate)
}