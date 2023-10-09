package com.dohyeok.gulpgulp.data.drink

import android.annotation.SuppressLint
import android.content.Context
import com.dohyeok.gulpgulp.data.drink.source.DrinkDataSource
import javax.inject.Inject


class DrinkRepository @Inject constructor(
    private val localDrinkDataSource: DrinkDataSource
) : DrinkDataSource {
    // key: DrinkItem.id, value: DrinkItem
    private val cachedDrinkItems: LinkedHashMap<String, DrinkItem> = LinkedHashMap()

    override suspend fun saveDrinkItem(item: DrinkItem) {
        localDrinkDataSource.saveDrinkItem(item)
        cacheDrinkItem(item)
    }

    override suspend fun deleteDrinkItem(item: DrinkItem) {
        localDrinkDataSource.deleteDrinkItem(item)
        cachedDrinkItems.remove(item.id)
    }

    override suspend fun getAllDrinkItems(): List<DrinkItem> {
        if (!cachedDrinkItems.isEmpty()) {
            return cachedDrinkItems.values.toList()
        } else {
            refreshDrinkItemsCache(localDrinkDataSource.getAllDrinkItems())
            return cachedDrinkItems.values.toList()
        }
    }

    override suspend fun getLastDrinkItemOrder(): Int {
        return localDrinkDataSource.getLastDrinkItemOrder()
    }

    override suspend fun updateDrinksOrder(drinkWithOrder: List<Pair<DrinkItem, Int>>) {
        localDrinkDataSource.updateDrinksOrder(drinkWithOrder)

        drinkWithOrder.forEach {
            cacheDrinkItem(it.first.apply { order = it.second })
        }
    }


    override suspend fun updateDrinkItem(itemId: String, newItem: DrinkItem) {
        localDrinkDataSource.updateDrinkItem(itemId, newItem)
        cacheDrinkItem(newItem)
    }

    private fun refreshDrinkItemsCache(drinkItems: List<DrinkItem>) {
        cachedDrinkItems.clear()
        drinkItems.forEach {
            cacheDrinkItem(it)
        }
    }

    private fun cacheDrinkItem(drinkItem: DrinkItem): DrinkItem {
        val cachedDrinkItem =
            DrinkItem(drinkItem.icon, drinkItem.name, drinkItem.amount, drinkItem.id).apply {
                order = drinkItem.order
            }
        cachedDrinkItems.put(cachedDrinkItem.id, cachedDrinkItem)
        return cachedDrinkItem
    }
}