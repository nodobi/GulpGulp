package com.dohyeok.gulpgulp.data.goal

import android.util.Log
import com.dohyeok.gulpgulp.data.goal.source.GoalDataSource
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.data.record.source.DrinkRecordDataSource
import com.dohyeok.gulpgulp.util.SPUtil
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class GoalRepository @Inject constructor(
    private var localGoalDataSource: GoalDataSource,
    private var localDrinkRecordDataSource: DrinkRecordDataSource,
    private var spUtil: SPUtil
) : GoalDataSource, DrinkRecordDataSource {

    // key: goal_date, value: Goal
    private val cachedGoals: LinkedHashMap<LocalDate, Goal> = linkedMapOf()

    // TODO("아직 Key 값을 LocalDate 으로 해도 될지 잘 모르겠음
    //      LocalDate가 Key 값이라면, recordId를 사용하여 DrinkRecord 를 찾을 때는 어떤 방식으로 캐시를 사용해야 할지.. 혹은 사용하지 않아야할지")
    // key: record_date, value: List<DrinkRecord>
    private val cachedRecords: LinkedHashMap<LocalDate, MutableList<DrinkRecord>> = linkedMapOf()

    override suspend fun saveGoal(goal: Goal, isUpdateSP: Boolean) {
        if (isUpdateSP) spUtil.putInt(SPUtil.KEY_GOAL_AMOUNT, goal.amount)

        val cachedGoal = cacheGoal(goal)
        localGoalDataSource.saveGoal(cachedGoal)
    }

    // isUseSP == true 인 경우, SP에 저장된 값을 기반으로 저장 후 반환
    // cachedGoal 에 없으면, 아얘 등록이 되지 않은 Goal 이기 때문에, isUseSP 값을 보고 반환값 결정
    override suspend fun getGoal(date: LocalDate, isUseSP: Boolean): Goal? {
        if (cachedGoals.isEmpty()) {
            refreshGoalsCache(localGoalDataSource.getAllGoals())
        }
        var cachedGoal = cachedGoals[date]
        if (cachedGoal == null && isUseSP) {
            cachedGoal = cacheGoal(Goal(date, spUtil.getInt(SPUtil.KEY_GOAL_AMOUNT)))
            saveGoal(cachedGoal)
        }
        return cachedGoal
    }

    override suspend fun getAllGoals(): List<Goal> {
        if (cachedGoals.isEmpty()) {
            refreshGoalsCache(localGoalDataSource.getAllGoals())
        }
        return cachedGoals.values.toList()
    }

    override suspend fun getMonthGoals(year: Int, monthValue: Int): List<Goal> {
        if(cachedGoals.isEmpty()) {
            refreshGoalsCache(localGoalDataSource.getAllGoals())
        }


        return cachedGoals.filter { it.key.year == year && it.key.monthValue == monthValue }.values.toList()

    }

    override suspend fun completeGoal(goal: Goal) {
        cacheGoal(goal).let {
            it.isComplete = true
            localGoalDataSource.completeGoal(goal)
        }
    }

    override suspend fun inCompleteGoal(goal: Goal) {
        cacheGoal(goal).let {
            it.isComplete = false
            localGoalDataSource.inCompleteGoal(goal)
        }
    }

    // ====== Records ======

    override suspend fun saveRecord(record: DrinkRecord) {
        val cachedRecord = cacheRecord(
            DrinkRecord(
                record.recordDate,
                record.recordTime,
                record.drink,
                record.recordId
            )
        )
        localDrinkRecordDataSource.saveRecord(cachedRecord)

        // 목표가 등록되어 있지 않던 날에 레코드를 추가할 수도 있음
        if(!cachedGoals.containsKey(record.recordDate)) {
            saveGoal(Goal(record.recordDate, spUtil.getInt(SPUtil.KEY_GOAL_AMOUNT)))
        }

        cachedGoals[record.recordDate]?.let { goal ->
            if (getDrinkAmountSum(goal.date) >= goal.amount) {
                completeGoal(goal)
            }
        }
    }

    override suspend fun deleteRecord(record: DrinkRecord) {
        cachedRecords[record.recordDate]?.remove(record)
        localDrinkRecordDataSource.deleteRecord(record)

        record.recordDate.let { date ->
            val goal = getGoal(date)!!
            if (goal.amount > localDrinkRecordDataSource.getDrinkAmountSum(date)) {
                inCompleteGoal(goal)
            }
        }
    }

    override suspend fun updateRecordDrink(record: DrinkRecord) {
        cachedRecords[record.recordDate]?.forEachIndexed { idx, rec ->
            if(rec.recordId == record.recordId) {
                cachedRecords[record.recordDate]?.set(idx, record)
            }
        }
        localDrinkRecordDataSource.updateRecordDrink(record)

        record.recordDate.let { date ->
            val goal = getGoal(date)!!
            if (goal.amount > localDrinkRecordDataSource.getDrinkAmountSum(date)) {
                inCompleteGoal(goal)
            } else {
                completeGoal(goal)
            }
        }
    }

    override suspend fun getAllRecords(): List<DrinkRecord> {
        if (cachedRecords.isEmpty()) {
            refreshRecordsCache(localDrinkRecordDataSource.getAllRecords())
        }

        return mutableListOf<DrinkRecord>().apply {
            cachedRecords.values.forEach {
                addAll(it)
            }
        }
    }

    override suspend fun getRecordsWithDate(date: LocalDate): List<DrinkRecord> {
        if (cachedRecords.isEmpty()) {
            refreshRecordsCache(localDrinkRecordDataSource.getAllRecords())
        }

        return localDrinkRecordDataSource.getRecordsWithDate(date)
    }

    override suspend fun getDrinkAmountSum(date: LocalDate): Int {
        if (cachedRecords.isEmpty()) {
            refreshRecordsCache(localDrinkRecordDataSource.getAllRecords())
        }
        var drinkAmount = 0
        cachedRecords[date]?.forEach {
            drinkAmount += it.drink.amount
        }
        return drinkAmount
    }

    private fun refreshGoalsCache(goals: List<Goal>) {
        cachedGoals.clear()
        goals.forEach {
            cachedGoals.put(it.date, it)
        }
    }

    private fun cacheGoal(goal: Goal): Goal {
        val cachedGoal = Goal(goal.date, goal.amount, goal.isComplete)
        cachedGoals.put(cachedGoal.date, cachedGoal)
        return cachedGoal
    }

    private fun refreshRecordsCache(drinkRecords: List<DrinkRecord>) {
        cachedRecords.clear()
        drinkRecords.forEach {
            cachedRecords.getOrPut(it.recordDate) { mutableListOf() }.apply {
                add(it)
            }
        }
    }

    private fun cacheRecord(drinkRecord: DrinkRecord): DrinkRecord {
        val cachedRecord = DrinkRecord(
            drinkRecord.recordDate,
            drinkRecord.recordTime,
            drinkRecord.drink,
            drinkRecord.recordId
        )
        cachedRecords.getOrPut(cachedRecord.recordDate) { mutableListOf() }.apply {
            add(cachedRecord)
        }
        return cachedRecord
    }
}