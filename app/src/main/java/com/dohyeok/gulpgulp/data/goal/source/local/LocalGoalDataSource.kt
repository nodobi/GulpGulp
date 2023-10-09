package com.dohyeok.gulpgulp.data.goal.source.local

import com.dohyeok.gulpgulp.data.goal.Goal
import com.dohyeok.gulpgulp.data.goal.source.GoalDataSource
import com.dohyeok.gulpgulp.di.module.IODispatcher
import com.dohyeok.gulpgulp.util.toTimeStamp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class LocalGoalDataSource @Inject constructor(
    private val goalsDao: GoalsDao,
    @IODispatcher private val ioContext: CoroutineDispatcher
) : GoalDataSource {
    override suspend fun saveGoal(goal: Goal, isUpdateSP: Boolean) = withContext(ioContext) {
        goalsDao.insertGoal(goal)
    }

    override suspend fun getGoal(date: LocalDate, isUseSP: Boolean): Goal? =
        withContext(ioContext) {
            goalsDao.selectGoal(date)
        }

    override suspend fun getAllGoals(): List<Goal> = withContext(ioContext) {
        goalsDao.selectAllGoals()
    }

    override suspend fun getMonthGoals(year: Int, monthValue: Int): List<Goal> =
        withContext(ioContext) {
            goalsDao.selectGoalsWithYearMonth(StringBuilder().apply {
                append(

                    LocalDate.of(
                        year, monthValue, 1
                    ).toTimeStamp

                )
                delete(length - 2, length)
                append("%")
            }.toString())
        }

    override suspend fun completeGoal(goal: Goal) = withContext(ioContext) {
        goalsDao.updateComplete(goal.date, true)
    }

    override suspend fun inCompleteGoal(goal: Goal) = withContext(ioContext) {
        goalsDao.updateComplete(goal.date, false)
    }
}