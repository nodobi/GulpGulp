package com.dohyeok.gulpgulp.data.source.drink.local

import androidx.room.*
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkRecord
import java.time.LocalDate

@Dao
interface DrinkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDrinkRecord(drinkRecord: DrinkRecord)

    @Query("SELECT * FROM drink_records")
    fun loadDrinkRecords(): List<DrinkRecord>

    @Query("SELECT * FROM drink_records WHERE date = :date")
    fun loadDrinkRecords(date: LocalDate): List<DrinkRecord>

    @Delete
    fun deleteDrinkRecord(drinkRecord: DrinkRecord)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDrink(drink: Drink)

    @Delete
    fun deleteDrink(drink: Drink)

    @Query("SELECT * FROM drinks")
    fun loadDrinks(): List<Drink>

}