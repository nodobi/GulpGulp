package com.dohyeok.gulpgulp.view.statistics.contract

import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.statistics.adapter.CalendarDayBinderContract
import com.dohyeok.gulpgulp.view.statistics.adapter.RecordListAdapterContract
import com.dohyeok.gulpgulp.view.statistics.adapter.RecordListViewHolder
import com.kizitonwose.calendar.core.CalendarMonth
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth

interface StatisticsContract {
    interface View: BaseContract.View {
        fun updateCalendarHeader(yearMonth: YearMonth)
        fun notifyCalendarDateChanged(date: LocalDate)
        fun updateRecordHeader(date: LocalDate)

        fun showEditRecordDialog(record: DrinkRecord)
        fun showAddRecordDialog()
    }
    interface Presenter: BaseContract.Presenter<View> {
        var recordListAdapterView: RecordListAdapterContract.View
        var recordListAdapterModel: RecordListAdapterContract.Model
        var calendarDayBinder: CalendarDayBinderContract

        var onCommitAddDialog: (DrinkItem, LocalTime) -> Unit
        var onCommitEditDialog: (DrinkRecord) -> Unit
        var onItemSwiped: (RecordListViewHolder, Int) -> Unit

        fun onScrollCalendar(scrolledData: CalendarMonth)

        fun loadData()
        fun loadGoalData(yearMonth: YearMonth)
        fun loadRecordListData(date: LocalDate)

    }
}