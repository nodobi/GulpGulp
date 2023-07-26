package com.dohyeok.gulpgulp.view.calendar.adapter

import java.time.LocalDate

interface CalendarDayBinderContract {
    var onClickDayView: (selectedDate: LocalDate, oldDate: LocalDate?) -> Unit
    var selectedDate: LocalDate?
    var drinkResultMap: Map<LocalDate, Boolean>?
}