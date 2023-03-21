package com.dohyeok.gulpgulp.data.source.drink

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource
import java.time.LocalDate

object DrinkRepository : DrinkDataSource {
    lateinit var drinkLocalDataSource: DrinkLocalDataSource
    override suspend fun insertDrink(drink: Drink) {
        drinkLocalDataSource.insertDrink(drink)
    }

    override suspend fun loadDrinks(): List<Drink> {
        return drinkLocalDataSource.loadDrinks()
    }

    override suspend fun loadDrinks(date: LocalDate): List<Drink> {
        return drinkLocalDataSource.loadDrinks(date)
    }

    override suspend fun deleteDrink(drink: Drink) {
        drinkLocalDataSource.deleteDrink(drink)
    }
}