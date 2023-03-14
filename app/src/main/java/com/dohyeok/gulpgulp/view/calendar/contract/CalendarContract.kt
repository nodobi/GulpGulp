package com.dohyeok.gulpgulp.view.calendar.contract

import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarAdapterContract
import java.time.LocalDate

interface CalendarContract {
    interface View: BaseContract.View {
        fun updateCalendarDates(date: LocalDate)
    }
    interface Presenter: BaseContract.Presenter<View> {
        fun updateDate()

        var adapterView: CalendarAdapterContract.View
        var adapterModel: CalendarAdapterContract.Model
        var date: LocalDate
    }
}