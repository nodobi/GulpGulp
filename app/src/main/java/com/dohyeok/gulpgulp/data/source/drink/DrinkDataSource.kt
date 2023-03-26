package com.dohyeok.gulpgulp.data.source.drink

import com.dohyeok.gulpgulp.data.Drink
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

    suspend fun loadTodayDrinkAmount(): Int
}