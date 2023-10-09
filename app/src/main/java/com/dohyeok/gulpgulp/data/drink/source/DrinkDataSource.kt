package com.dohyeok.gulpgulp.data.drink.source

import com.dohyeok.gulpgulp.data.drink.DrinkItem

interface DrinkDataSource {
    suspend fun saveDrinkItem(item: DrinkItem)
    suspend fun deleteDrinkItem(item: DrinkItem)
    suspend fun getAllDrinkItems(): List<DrinkItem>
    suspend fun getLastDrinkItemOrder(): Int
    suspend fun updateDrinksOrder(drinkWithOrder: List<Pair<DrinkItem, Int>>)
    suspend fun updateDrinkItem(itemId: String, newItem: DrinkItem)
}