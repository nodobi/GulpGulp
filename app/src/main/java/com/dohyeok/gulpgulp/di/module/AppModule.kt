package com.dohyeok.gulpgulp.di.module

import android.content.Context
import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.drink.source.DrinkDataSource
import com.dohyeok.gulpgulp.data.drink.source.local.DrinkDatabase
import com.dohyeok.gulpgulp.data.drink.source.local.LocalDrinkDataSource
import com.dohyeok.gulpgulp.data.goal.GoalRepository
import com.dohyeok.gulpgulp.data.goal.source.local.LocalGoalDataSource
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.data.icon.sorce.IconDataSource
import com.dohyeok.gulpgulp.data.icon.sorce.local.LocalIconDataSource
import com.dohyeok.gulpgulp.data.record.source.DrinkRecordDataSource
import com.dohyeok.gulpgulp.data.record.source.local.LocalDrinkRecordDataSource
import com.dohyeok.gulpgulp.util.SPUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*
     * 현재는 LocalDataSource 밖에 없어서 상관이 없으나, 만약 RemoteDataSource가 생긴다면,
     * 아래와 같이 어노테이션을 새로 정의하여 어떤 인스턴스가 들어왔는지 알려주어야 한다.
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDrinkDataSource

    @Singleton
    @Provides
    @LocalDrinkDataSource
    fun provideLocalDrinkDataSource(
        database: DrinkDatabase,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): DrinkDataSource {
        return LocalDrinkDataSource(database.drinksDao(), ioDispatcher)
    }


    @Singleton
    @Provides
    fun provideDrinkRepository(
        localDrinkDataSource: com.dohyeok.gulpgulp.data.drink.source.local.LocalDrinkDataSource
    ): DrinkRepository {
        return DrinkRepository(localDrinkDataSource)
    }

    @Singleton
    @Provides
    fun provideLocalGoalSource(
        database: DrinkDatabase,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): LocalGoalDataSource {
        return LocalGoalDataSource(database.goalsDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideLocalDrinkRecordSource(
        database: DrinkDatabase,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): DrinkRecordDataSource {
        return LocalDrinkRecordDataSource(database.drinkRecordsDao(), ioDispatcher)
    }


    // SPUtil 또한 GoalRepository 의 필드에 속하므로 Application Context 사용
    @Provides
    fun provideSPUtil(
        @ApplicationContext context: Context
    ): SPUtil {
        return SPUtil(context)
    }

    @Singleton
    @Provides
    fun provideGoalRepository(
        localGoalDataSource: LocalGoalDataSource,
        localDrinkRecordDataSource: LocalDrinkRecordDataSource,
        spUtil: SPUtil
    ): GoalRepository {
        return GoalRepository(localGoalDataSource, localDrinkRecordDataSource, spUtil)
    }

    @Singleton
    @Provides
    fun provideLocalIconDataSource(
        database: DrinkDatabase,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): IconDataSource {
        return LocalIconDataSource(database.iconDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideIconRepository(
        @ApplicationContext context: Context,
        localIconDataSource: IconDataSource
    ): IconRepository {
        return IconRepository(context, localIconDataSource)
    }

}