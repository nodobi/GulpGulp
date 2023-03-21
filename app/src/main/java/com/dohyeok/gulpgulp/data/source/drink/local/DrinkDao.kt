package com.dohyeok.gulpgulp.data.source.drink.local

import androidx.room.*
import com.dohyeok.gulpgulp.data.Drink
import java.time.LocalDate

@Dao
interface DrinkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDrink(drink: Drink)

    @Query("SELECT * FROM drinks")
    fun loadDrinks(): List<Drink>

    @Query("SELECT * FROM drinks WHERE date = :date")
    fun loadDrinks(date: LocalDate): List<Drink>

    @Delete
    fun deleteDrink(drink: Drink)

}