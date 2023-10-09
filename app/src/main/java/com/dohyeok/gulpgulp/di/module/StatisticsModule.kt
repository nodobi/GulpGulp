package com.dohyeok.gulpgulp.di.module

import android.content.Context
import com.dohyeok.gulpgulp.data.goal.GoalRepository
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.view.statistics.StatisticsFragment
import com.dohyeok.gulpgulp.view.statistics.adapter.CalendarDayBinder
import com.dohyeok.gulpgulp.view.statistics.adapter.RecordListAdapter
import com.dohyeok.gulpgulp.view.statistics.contract.StatisticsContract
import com.dohyeok.gulpgulp.view.statistics.contract.StatisticsPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityComponent::class)
object StatisticsModule {

    @Provides
    fun provideStatisticsFragment(): StatisticsContract.View {
        return StatisticsFragment()
    }

    @Provides
    fun provideStatisticsPresenter(
        recordListAdapter: RecordListAdapter,
        calendarDayBinder: CalendarDayBinder,
        iconRepository: IconRepository,
        goalRepository: GoalRepository,
        @UIDispatcher uiContext: CoroutineDispatcher
    ): StatisticsContract.Presenter {
        return StatisticsPresenter(
            recordListAdapter,
            recordListAdapter,
            calendarDayBinder,
            iconRepository,
            goalRepository,
            uiContext
        )
    }

    @Provides
    fun provideRecordListAdapter(
        @ActivityContext context: Context
    ): RecordListAdapter {
        return RecordListAdapter(context)
    }

    @Provides
    fun provideCalendarDayBinder(): CalendarDayBinder {
        return CalendarDayBinder()
    }

}