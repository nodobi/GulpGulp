package com.dohyeok.gulpgulp.di.module

import android.content.Context
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.view.dialog.iconselection.adapter.IconListAdapter
import com.dohyeok.gulpgulp.view.dialog.iconselection.contract.IconSelectionDialogContract
import com.dohyeok.gulpgulp.view.dialog.iconselection.contract.IconSelectionDialogPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(FragmentComponent::class)
object IconSelectionDialogModule {

    @Provides
    fun provideIconSelectionDialogPresenter(
        iconListAdapter: IconListAdapter,
        iconRepository: IconRepository,
        @UIDispatcher uiDispatcher: CoroutineDispatcher
    ): IconSelectionDialogContract.Presenter {
        return IconSelectionDialogPresenter(iconListAdapter, iconListAdapter, iconRepository, uiDispatcher)
    }

    @Provides
    fun provideIconListAdapter(
        @ActivityContext context: Context
    ): IconListAdapter {
        return IconListAdapter(context)
    }
}