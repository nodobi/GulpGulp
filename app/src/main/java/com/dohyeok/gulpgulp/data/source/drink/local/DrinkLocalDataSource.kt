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

    override suspend fun loadDrinkAmount(date: LocalDate): Int = withContext(Dispatchers.IO) {
        drinkDao.loadDrinkAmount(date)
    }

    override suspend fun insertDrinkGoal(drinkGoal: DrinkGoal) = withContext(Dispatchers.IO) {
        drinkDao.insertDrinkGoal(drinkGoal)
    }

    override suspend fun loadDrinkGoal(date: LocalDate): DrinkGoal? = withContext(Dispatchers.IO) {
        drinkDao.loadDrinkGoal(date)
    }

    override suspend fun loadDrinkGoals(dates: List<LocalDate>): List<DrinkGoal?> = withContext(Dispatchers.IO){
        mutableListOf<DrinkGoal?>().apply {
            dates.forEach {date ->
                this.add(drinkDao.loadDrinkGoal(date))
            }
        }
    }

    override suspend fun updateDrinkGoal(date: LocalDate, isComplete: Boolean) {
        withContext(Dispatchers.IO) {
            drinkDao.updateDrinkGoal(date, isComplete)
        }
    }

    override suspend fun updateDrinkGoal(date: LocalDate, amount: Int, isComplete: Boolean) {
        drinkDao.updateDrinkGoal(date, amount, isComplete)
    }

    override suspend fun upsertDrinkGoal(date: LocalDate, amount: Int, isComplete: Boolean) = withContext(Dispatchers.IO) {
        drinkDao.upsertDrinkGoal(date, amount, isComplete)
    }
}