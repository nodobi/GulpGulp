package com.dohyeok.gulpgulp.data.source.drink

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkGoal
import com.dohyeok.gulpgulp.data.DrinkRecord
import java.time.LocalDate

interface DrinkDataSource {

    suspend fun insertDrinkRecord(drinkRecord: DrinkRecord)
    suspend fun loadDrinkRecords(): List<DrinkRecord>
    suspend fun loadDrinkRecords(date: LocalDate): List<DrinkRecord>
    suspend fun deleteDrinkRecord(drinkRecord: DrinkRecord)

    suspend fun insertDrink(drink: Drink)
    suspend fun deleteDrink(drink: Drink)
    suspend fun loadDrinks(): List<Drink>

    suspend fun loadDrinkAmount(date: LocalDate = LocalDate.now()): Int

    suspend fun insertDrinkGoal(drinkGoal: DrinkGoal)
    suspend fun loadDrinkGoal(date: LocalDate) : DrinkGoal?

    suspend fun updateDrinkGoal(date: LocalDate, isComplete: Boolean)
    suspend fun updateDrinkGoal(date: LocalDate, amount: Int, isComplete: Boolean)
    suspend fun upsertDrinkGoal(date: LocalDate, amount: Int, isComplete: Boolean)

}