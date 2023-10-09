package com.dohyeok.gulpgulp.view.statistics.adapter

import com.dohyeok.gulpgulp.databinding.ItemStatisticsCalendarBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.view.ViewContainer

class CalendarDayView(val binding: ItemStatisticsCalendarBinding): ViewContainer(binding.root) {
    lateinit var calendarDay: CalendarDay
    fun onBind(data: CalendarDay) {
        calendarDay = data
        binding.textDay.text = data.date.dayOfMonth.toString()

    }
}