package com.dohyeok.gulpgulp.di.module

import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.goal.GoalRepository
import com.dohyeok.gulpgulp.view.setting.SettingFragment
import com.dohyeok.gulpgulp.view.setting.contract.SettingContract
import com.dohyeok.gulpgulp.view.setting.contract.SettingPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.CoroutineDispatcher


@Module
@InstallIn(ActivityComponent::class)
object SettingModule {

    @Provides
    fun provideSettingFragment(): SettingContract.View {
        return SettingFragment()
    }

    @Provides
    fun provideSettingPresenter(
        drinkRepository: DrinkRepository,
        goalRepository: GoalRepository,
        @UIDispatcher uiDispatcher: CoroutineDispatcher
    ): SettingContract.Presenter {
        return SettingPresenter(drinkRepository, goalRepository, uiDispatcher)
    }
}