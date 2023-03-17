package com.dohyeok.gulpgulp.data.source.drink

import com.dohyeok.gulpgulp.data.Drink

interface DrinkDataSource {

    suspend fun insertDrink(drink: Drink)
}