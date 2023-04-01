package com.dohyeok.gulpgulp.view.calendar.adapter

import java.time.LocalDate

interface CalendarAdapterContract {
    interface View {
        fun notifyAdapter()
    }

    interface Model {
        var currentDate: LocalDate

        fun updateData(data: List<LocalDate>)
        fun updateSize(column: Int, row: Int)
        var onDateClicked: ((LocalDate) -> Unit)
    }
}