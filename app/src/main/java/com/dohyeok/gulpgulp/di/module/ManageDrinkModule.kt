package com.dohyeok.gulpgulp.di.module

import android.content.Context
import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.view.managedrink.ManageDrinkFragment
import com.dohyeok.gulpgulp.view.managedrink.adapter.ManageDrinkAdapter
import com.dohyeok.gulpgulp.view.managedrink.contract.ManageDrinkContract
import com.dohyeok.gulpgulp.view.managedrink.contract.ManageDrinkPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityComponent::class)
object ManageDrinkModule {

    @Provides
    fun provideManageDrinkFragment(): ManageDrinkContract.View {
        return ManageDrinkFragment()
    }

    @Provides
    fun provideManageDrinkPresenter(
        manageDrinkAdapter: ManageDrinkAdapter,
        drinkRepository: DrinkRepository,
        iconRepository: IconRepository,
        @UIDispatcher uiContext: CoroutineDispatcher
    ): ManageDrinkContract.Presenter {
        return ManageDrinkPresenter(
            manageDrinkAdapter, manageDrinkAdapter, drinkRepository, iconRepository, uiContext
        )
    }

    @Provides
    fun provideManageDrinkAdapter(
        @ActivityContext context: Context
    ): ManageDrinkAdapter {
        return ManageDrinkAdapter(context)
    }


}