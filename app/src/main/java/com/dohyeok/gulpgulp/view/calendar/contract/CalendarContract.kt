package com.dohyeok.gulpgulp.view.calendar.contract

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.calendar.ItemTouchCallback
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDayBinderContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapterContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarHeaderBinderContract
import com.kizitonwose.calendar.core.CalendarMonth
import java.time.LocalDate
import java.time.YearMonth

interface CalendarContract {
    interface View : BaseContract.View {
        fun updateCalendarDate(date: LocalDate)
        fun attachItemTouchHelper(itemTouchCallback: ItemTouchCallback)
        fun showDialog(onPositive: ((Unit) -> Unit), onNegative: ((Unit) -> Unit))
        fun changeProgressPercent(percent: Int)
        fun changeDetailProgressPercent(percent: Int)
        fun changeDetailDrinkAmount(amount: Int)
        fun setCalendarEvents(onPrev: () -> Unit, onNext: () -> Unit)
        fun updateCalendarDetailDate(date: LocalDate)

        fun moveCalendar(date: LocalDate)
        fun notifyCalendarDateChanged(date: LocalDate)
        fun notifyCalendarMonthChanged(yearMonth: YearMonth)

        fun showEditDrinkBottomSheetDialog(drinkRecord: DrinkRecord, iconResId: Int)
    }

    interface Presenter : BaseContract.Presenter<View> {
        var calendarDayBinder: CalendarDayBinderContract
        var calendarHeaderBinder: CalendarHeaderBinderContract
        var detailAdapterView: CalendarDetailAdapterContract.View
        var detailAdapterModel: CalendarDetailAdapterContract.Model
        var drinkRepository: DrinkRepository

        var onCalendarScroll: (CalendarMonth) -> Unit
        var onDrinkRecordEdit: (DrinkRecord, Drink) -> Unit

        fun setAdapterEvents()

        fun updateDetailAdapterData()
        fun updateDetails()

        fun updateDate()
        fun updateProgress()
    }
}