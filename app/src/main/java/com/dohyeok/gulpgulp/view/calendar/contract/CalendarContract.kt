package com.dohyeok.gulpgulp.view.calendar.contract

import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.calendar.ItemTouchCallback
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapterContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarPagerAdapterContract
import java.time.LocalDate

interface CalendarContract {
    interface View : BaseContract.View {
        fun updateCalendarDate(date: LocalDate)
        fun attachItemTouchHelper(itemTouchCallback: ItemTouchCallback)
        fun showDialog(onPositive: ((Unit) -> Unit), onDismiss: ((Unit) -> Unit))
        fun changeProgressPercent(percent: Int)
        fun changeDetailProgressPercent(percent: Int)
        fun changeDetailDrinkAmount(amount: Int)
        fun updateCalendarDetailDate(date: LocalDate)
    }

    interface Presenter : BaseContract.Presenter<View> {
        var detailAdapterView: CalendarDetailAdapterContract.View
        var detailAdapterModel: CalendarDetailAdapterContract.Model
        var pagerAdapterView: CalendarPagerAdapterContract.View
        var pagerAdapterModel: CalendarPagerAdapterContract.Model
        var drinkRepository: DrinkRepository

        fun setAdapterEvents()

        fun updateDetailAdapterData()
        fun updateDetails()

        fun updateDate()
        fun updateProgress()
    }
}