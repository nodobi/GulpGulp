package com.dohyeok.gulpgulp.data.source.drink

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkGoal
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource
import java.time.LocalDate

object DrinkRepository : DrinkDataSource {
    lateinit var drinkLocalDataSource: DrinkLocalDataSource
    override suspend fun insertDrinkRecord(drinkRecord: DrinkRecord) {
        drinkLocalDataSource.insertDrinkRecord(drinkRecord)
    }

    override suspend fun loadDrinkRecords(): List<DrinkRecord> {
        return drinkLocalDataSource.loadDrinkRecords()
    }

    override suspend fun loadDrinkRecords(date: LocalDate): List<DrinkRecord> {
        return drinkLocalDataSource.loadDrinkRecords(date)
    }

    override suspend fun deleteDrinkRecord(drinkRecord: DrinkRecord) {
        drinkLocalDataSource.deleteDrinkRecord(drinkRecord)
    }

    override suspend fun insertDrink(drink: Drink) {
        drinkLocalDataSource.insertDrink(drink)
    }

    override suspend fun deleteDrink(drink: Drink) {
        drinkLocalDataSource.deleteDrink(drink)
    }

    override suspend fun loadDrinks(): List<Drink> {
        return drinkLocalDataSource.loadDrinks()
    }

    override suspend fun loadDrinkAmount(date: LocalDate): Int {
        return drinkLocalDataSource.loadDrinkAmount(date)
    }

    override suspend fun insertDrinkGoal(drinkGoal: DrinkGoal) {
        return drinkLocalDataSource.insertDrinkGoal(drinkGoal)
    }

    override suspend fun loadDrinkGoal(date: LocalDate): DrinkGoal? {
        return drinkLocalDataSource.loadDrinkGoal(date)
    }

    override suspend fun loadDrinkGoals(dates: List<LocalDate>): List<DrinkGoal?> {
        return drinkLocalDataSource.loadDrinkGoals(dates)
    }

    override suspend fun updateDrinkGoal(date: LocalDate, isComplete: Boolean) {
        drinkLocalDataSource.updateDrinkGoal(date, isComplete)
    }

    override suspend fun updateDrinkGoal(date: LocalDate, amount: Int, isComplete: Boolean) {
        drinkLocalDataSource.updateDrinkGoal(date, amount, isComplete)
    }

    override suspend fun upsertDrinkGoal(date: LocalDate, amount: Int, isComplete: Boolean) {
        drinkLocalDataSource.upsertDrinkGoal(date, amount, isComplete)
    }
}