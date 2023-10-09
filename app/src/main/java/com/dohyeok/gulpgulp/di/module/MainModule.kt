package com.dohyeok.gulpgulp.di.module

import android.content.Context
import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.goal.GoalRepository
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.view.main.MainFragment
import com.dohyeok.gulpgulp.view.main.adapter.DrinkListAdapter
import com.dohyeok.gulpgulp.view.main.contract.MainContract
import com.dohyeok.gulpgulp.view.main.contract.MainPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityComponent::class)
object MainModule {
    @Provides
    fun provideMainPresenter(
        drinkListAdapter: DrinkListAdapter,
        drinkRepository: DrinkRepository,
        iconRepository: IconRepository,
        goalRepository: GoalRepository,
        @UIDispatcher uiContext: CoroutineDispatcher
    ): MainContract.Presenter {
        return MainPresenter(drinkListAdapter, drinkListAdapter, drinkRepository, iconRepository, goalRepository, uiContext)
    }

    @Provides
    fun provideMainFragment(): MainContract.View {
        return MainFragment()
    }

    @Provides
    fun provideDrinkListAdapter(@ActivityContext context: Context): DrinkListAdapter {
        return DrinkListAdapter(context)
    }
}
