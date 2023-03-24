package com.dohyeok.gulpgulp.data.source.drink

import com.dohyeok.gulpgulp.data.Drink
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
}