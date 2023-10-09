package com.dohyeok.gulpgulp.data.drink.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dohyeok.gulpgulp.data.drink.DrinkItem

@Dao
interface DrinksDao {
    @Insert(DrinkItem::class)
    fun insertDrinkItem(item: DrinkItem)
    @Delete(DrinkItem::class)
    fun deleteDrinkItem(item: DrinkItem)
    @Query("SELECT * FROM drink_items ORDER BY list_order")
    fun getAllDrinkItems(): List<DrinkItem>
    @Query("SELECT Max(list_order) FROM drink_items")
    fun getLastDrinkItemOrder(): Int

    @Query("SELECT icon FROM drink_items")
    fun getAllIconName(): List<String>

    @Query("UPDATE drink_items SET " +
            "icon = :icon," +
            "name = :name," +
            "amount = :amount," +
            "list_order = :list_order " +
            "WHERE id = :targetId")
    fun updateDrinkItem(targetId: String, icon: String, name: String, amount: Int, list_order: Int)
    @Query("UPDATE drink_items SET list_order = :list_order WHERE id = :targetId")
    fun updateDrinkItemOrder(targetId: String, list_order: Int)
}