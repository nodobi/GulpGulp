package com.dohyeok.gulpgulp.view.calendar.contract

import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarAdapterContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapterContract
import java.time.LocalDate

interface CalendarContract {
    interface View : BaseContract.View {
        fun updateCalendarDates(date: LocalDate)
    }

    interface Presenter : BaseContract.Presenter<View> {
        var adapterView: CalendarAdapterContract.View
        var adapterModel: CalendarAdapterContract.Model
        var detailAdapterView: CalendarDetailAdapterContract.View
        var detailAdapterModel: CalendarDetailAdapterContract.Model
        var drinkRepository: DrinkRepository
        var date: LocalDate

        fun updateDetailData()
        fun updateDate()
    }
}