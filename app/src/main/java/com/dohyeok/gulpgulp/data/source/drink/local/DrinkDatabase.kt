package com.dohyeok.gulpgulp.data.source.drink.local

import android.content.Context
import androidx.room.*
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkGoal
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.converter.LocalDateConverter
import com.dohyeok.gulpgulp.data.converter.LocalTimeConverter

@Database(entities = [Drink::class, DrinkRecord::class, DrinkGoal::class], version = 2)
@TypeConverters(LocalDateConverter::class, LocalTimeConverter::class)
abstract class DrinkDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao

    companion object {
        private var INSTANCE: DrinkDatabase? = null

        fun getInstance(context: Context): DrinkDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, DrinkDatabase::class.java, "Drinks.db"
                ).build()
            }
            return INSTANCE!!
        }
    }
}