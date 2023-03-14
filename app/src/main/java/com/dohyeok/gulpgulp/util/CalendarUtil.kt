package com.dohyeok.gulpgulp.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

object CalendarUtil {
    fun getDateList(date: LocalDate): List<LocalDate> {
        val ret = arrayListOf<LocalDate>()

        var firstDayOfCalendar = date.withDayOfMonth(1)
        if(firstDayOfCalendar.dayOfWeek != DayOfWeek.SUNDAY) {
            firstDayOfCalendar = firstDayOfCalendar.minusDays(firstDayOfCalendar.dayOfWeek.value.toLong())
        }

        for (i in 0 until getDateListSize(date)) {
            ret.add(firstDayOfCalendar.plusDays(i.toLong()))
        }

        return ret.toList()
    }

    private fun getDateListSize(date: LocalDate): Int {
        return 7 * getCalendarWeekCnt(date)
    }

    fun getCalendarWeekCnt(date: LocalDate): Int {
        val firstWeekOfDate = date.withDayOfMonth(1).get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
        var lastWeekOfDate = date.withDayOfMonth(date.lengthOfMonth()).get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())

        if(lastWeekOfDate == 1) {
            lastWeekOfDate = date.withDayOfMonth(date.lengthOfMonth() - 7).get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) + 1
        }
        return lastWeekOfDate - firstWeekOfDate + 1
    }


}