package com.dohyeok.gulpgulp.view.calendar.adapter

import java.time.LocalDate

interface CalendarPagerAdapterContract {
    interface View {

    }

    interface Model {
        val date: LocalDate
        var offset: Int
        var onCalendarPageChanged: (LocalDate) -> Unit
        var onDateClicked: (LocalDate) -> Unit

    }
}