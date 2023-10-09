package com.dohyeok.gulpgulp.view.statistics.adapter

import com.dohyeok.gulpgulp.data.goal.Goal
import java.time.LocalDate

// DayBinder 는 Adapter 와 달리 notify~ () 메소드들이 존재하지 않기 때문에, 별도로 Model, View 로 구분하지 않았다.
interface CalendarDayBinderContract {
    var selectedDate: LocalDate
    var onClickCalendarDate: (LocalDate) -> Unit

    fun updateGoalsData(data: Map<LocalDate, Goal>)
    fun updateGoalData(data: Goal)
}