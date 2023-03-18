package com.dohyeok.gulpgulp.data.source.drink

import com.dohyeok.gulpgulp.data.Drink
import java.time.LocalDate

interface DrinkDataSource {

    suspend fun insertDrink(drink: Drink)
    suspend fun loadDrinks(): List<Drink>
    suspend fun loadDrinks(date: LocalDate): List<Drink>
}