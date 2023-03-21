package com.dohyeok.gulpgulp.data.source.drink.local

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.source.drink.DrinkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

object DrinkLocalDataSource : DrinkDataSource {
    lateinit var drinkDao: DrinkDao
    override suspend fun insertDrink(drink: Drink) = withContext(Dispatchers.IO) {
        drinkDao.insertDrink(drink)
    }

    override suspend fun loadDrinks(): List<Drink> = withContext(Dispatchers.IO) {
        drinkDao.loadDrinks()
    }

    override suspend fun loadDrinks(date: LocalDate): List<Drink> = withContext(Dispatchers.IO) {
        drinkDao.loadDrinks(date)
    }

    override suspend fun deleteDrink(drink: Drink) = withContext(Dispatchers.IO) {
        drinkDao.deleteDrink(drink)
    }
}