package com.dohyeok.gulpgulp.di.module

import android.content.Context
import androidx.room.Room
import com.dohyeok.gulpgulp.data.drink.source.local.DrinkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,
            DrinkDatabase::class.java,
            "drinks-db"
        ).build()

    @Provides
    fun provideDrinksDao(appDatabase: DrinkDatabase) = appDatabase.drinksDao()
    @Provides
    fun provideGoalsDao(appDatabase: DrinkDatabase) = appDatabase.goalsDao()
    @Provides
    fun provideDrinkRecordsDao(appDatabase: DrinkDatabase) = appDatabase.drinkRecordsDao()
}