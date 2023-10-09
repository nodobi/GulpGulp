package com.dohyeok.gulpgulp.di.module

import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.view.dialog.addeditdrink.contract.AddEditDrinkDialogContract
import com.dohyeok.gulpgulp.view.dialog.addeditdrink.contract.AddEditDrinkDialogPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(FragmentComponent::class)
object AddEditDrinkDialogModule {

    @Provides
    fun provideAddEditDrinkDialogPresenter(
        iconRepository: IconRepository,
        @UIDispatcher uiDispatcher: CoroutineDispatcher
    ): AddEditDrinkDialogContract.Presenter {
        return AddEditDrinkDialogPresenter(iconRepository, uiDispatcher)
    }


}