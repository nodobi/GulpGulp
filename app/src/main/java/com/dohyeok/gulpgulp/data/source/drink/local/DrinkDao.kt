package com.dohyeok.gulpgulp.data.source.drink.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dohyeok.gulpgulp.data.Drink

@Dao
interface DrinkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDrink(drink: Drink)

}