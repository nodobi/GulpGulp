package com.dohyeok.gulpgulp.di.module

import com.dohyeok.gulpgulp.util.SPUtil
import com.dohyeok.gulpgulp.view.dialog.goalpicker.contract.GoalPickerDialogContract
import com.dohyeok.gulpgulp.view.dialog.goalpicker.contract.GoalPickerDialogPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(FragmentComponent::class)
object GoalPickerDialogModule {

    @Provides
    fun provideGoalPickerDialogPresenter(
        spUtil: SPUtil, @UIDispatcher uiDispatcher: CoroutineDispatcher
    ): GoalPickerDialogContract.Presenter {
        return GoalPickerDialogPresenter(spUtil, uiDispatcher)
    }
}