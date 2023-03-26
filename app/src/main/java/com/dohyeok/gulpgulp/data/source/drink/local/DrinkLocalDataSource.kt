package com.dohyeok.gulpgulp.data.source.drink.local

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkGoal
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

object DrinkLocalDataSource : DrinkDataSource {
    lateinit var drinkDao: DrinkDao
    override suspend fun insertDrinkRecord(drinkRecord: DrinkRecord) = withContext(Dispatchers.IO) {
        drinkDao.insertDrinkRecord(drinkRecord)
    }

    override suspend fun loadDrinkRecords(): List<DrinkRecord> = withContext(Dispatchers.IO) {
        drinkDao.loadDrinkRecords()
    }

    override suspend fun loadDrinkRecords(date: LocalDate): List<DrinkRecord> =
        withContext(Dispatchers.IO) {
            drinkDao.loadDrinkRecords(date)
        }

    override suspend fun deleteDrinkRecord(drinkRecord: DrinkRecord) = withContext(Dispatchers.IO) {
        drinkDao.deleteDrinkRecord(drinkRecord)
    }

    override suspend fun insertDrink(drink: Drink) = withContext(Dispatchers.IO) {
        drinkDao.insertDrink(drink)
    }

    override suspend fun deleteDrink(drink: Drink) = withContext(Dispatchers.IO) {
        drinkDao.deleteDrink(drink)
    }

    override suspend fun loadDrinks(): List<Drink> = withContext(Dispatchers.IO) {
        drinkDao.loadDrinks()
    }

    override suspend fun loadTodayDrinkAmount(): Int = withContext(Dispatchers.IO) {
        drinkDao.loadTodayDrinkAmount(LocalDate.now())
    }

    override suspend fun insertDrinkGoal(drinkGoal: DrinkGoal) = withContext(Dispatchers.IO) {
        drinkDao.insertDrinkGoal(drinkGoal)
    }

    override suspend fun loadDrinkGoal(date: LocalDate): DrinkGoal? = withContext(Dispatchers.IO) {
        drinkDao.loadDrinkGoal(date)
    }
}