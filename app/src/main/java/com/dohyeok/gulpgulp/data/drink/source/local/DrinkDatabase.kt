package com.dohyeok.gulpgulp.data.drink.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dohyeok.gulpgulp.data.converter.LocalDateConverter
import com.dohyeok.gulpgulp.data.converter.LocalTimeConverter
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.goal.Goal
import com.dohyeok.gulpgulp.data.goal.source.local.GoalsDao
import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.data.icon.sorce.local.IconDao
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.data.record.source.local.DrinkRecordsDao

@TypeConverters(LocalDateConverter::class, LocalTimeConverter::class)
@Database(entities = [DrinkItem::class, DrinkRecord::class, Goal::class, Icon::class], version = 1)
abstract class DrinkDatabase() : RoomDatabase() {
    abstract fun drinksDao(): DrinksDao
    abstract fun drinkRecordsDao(): DrinkRecordsDao
    abstract fun goalsDao(): GoalsDao
    abstract fun iconDao(): IconDao
}
