package com.dohyeok.gulpgulp.data.source.drink.local

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.source.drink.DrinkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DrinkLocalDataSource : DrinkDataSource {
    lateinit var drinkDao: DrinkDao
    override suspend fun insertDrink(drink: Drink) = withContext(Dispatchers.IO) {
        drinkDao.insertDrink(drink)
    }
}