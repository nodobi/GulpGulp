package com.dohyeok.gulpgulp.data.source.drink.local

import androidx.room.*
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkGoal
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

    @Query("SELECT SUM(amount) FROM drink_records WHERE date = :date")
    fun loadDrinkAmount(date: LocalDate): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrinkGoal(drinkGoal: DrinkGoal)

    @Query("SELECT * FROM drink_goals WHERE date = :date")
    fun loadDrinkGoal(date: LocalDate): DrinkGoal?

    @Query("UPDATE drink_goals SET isComplete = :isComplete WHERE date = :date")
    fun updateDrinkGoal(date: LocalDate, isComplete: Boolean)

    @Query("UPDATE drink_goals SET amount = :amount, isComplete = :isComplete WHERE date = :date")
    fun updateDrinkGoal(date: LocalDate, amount: Int, isComplete: Boolean)

    @Transaction
    fun upsertDrinkGoal(date: LocalDate, amount: Int, isComplete: Boolean) {
        if(loadDrinkGoal(date) == null) {
            insertDrinkGoal(DrinkGoal(date, amount, isComplete))
        } else {
            updateDrinkGoal(date, amount, isComplete)
        }
    }

    @Query("SELECT MAX([order]) FROM drinks")
    fun getLastDrinkOrder(): Int
}