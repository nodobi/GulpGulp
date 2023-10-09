package com.dohyeok.gulpgulp.data.drink.source.local

import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.drink.source.DrinkDataSource
import com.dohyeok.gulpgulp.di.module.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDrinkDataSource @Inject constructor(
    private val drinksDao: DrinksDao,
    @IODispatcher private val ioContext: CoroutineDispatcher
) : DrinkDataSource {
    override suspend fun saveDrinkItem(item: DrinkItem) = withContext(ioContext) {
        drinksDao.insertDrinkItem(item)
    }

    override suspend fun deleteDrinkItem(item: DrinkItem) = withContext(ioContext) {
        drinksDao.deleteDrinkItem(item)
    }

    override suspend fun getAllDrinkItems(): List<DrinkItem> = withContext(ioContext) {
        drinksDao.getAllDrinkItems()
    }

    override suspend fun getLastDrinkItemOrder(): Int = withContext(ioContext) {
        drinksDao.getLastDrinkItemOrder()
    }

    override suspend fun updateDrinksOrder(drinkWithOrder: List<Pair<DrinkItem, Int>>) = withContext(ioContext) {
        drinkWithOrder.forEach {
            drinksDao.updateDrinkItemOrder(it.first.id, it.second)
        }
    }

    override suspend fun updateDrinkItem(itemId: String, newItem: DrinkItem) = withContext(ioContext){
        with(newItem) {
            drinksDao.updateDrinkItem(itemId, icon, name, amount, order)
        }
    }
}