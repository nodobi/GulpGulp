package com.dohyeok.gulpgulp.data.source.drink

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource

object DrinkRepository : DrinkDataSource {
    lateinit var drinkLocalDataSource: DrinkLocalDataSource
    override suspend fun insertDrink(drink: Drink) {
        drinkLocalDataSource.insertDrink(drink)
    }
}