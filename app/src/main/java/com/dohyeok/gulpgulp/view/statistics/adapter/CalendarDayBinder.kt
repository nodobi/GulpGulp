package com.dohyeok.gulpgulp.view.statistics.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.goal.Goal
import com.dohyeok.gulpgulp.databinding.ItemStatisticsCalendarBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import java.time.LocalDate
import javax.inject.Inject

class CalendarDayBinder @Inject constructor() : MonthDayBinder<CalendarDayView>,
    CalendarDayBinderContract {
    private var goalsData: LinkedHashMap<LocalDate, Goal>? = null
    override var selectedDate: LocalDate = LocalDate.now()
    override lateinit var onClickCalendarDate: (LocalDate) -> Unit

    @SuppressLint("ResourceAsColor")
    override fun bind(container: CalendarDayView, data: CalendarDay) {
        container.onBind(data)

        if(data.position == DayPosition.MonthDate) {
            container.binding.textDay.setTextColor(container.binding.root.context.getColor(R.color.calendar_monthDate))
        } else {
            container.binding.textDay.setTextColor(container.binding.root.context.getColor(R.color.calendar_out))
        }

        if (data.date == selectedDate) {
            container.binding.imgDay.setImageResource(R.drawable.shape_background_round)
        } else if (goalsData?.get(data.date)?.isComplete == true) {
            Log.d("CalendarDayBinder", "GoalIsComplete ${goalsData!![data.date]}")
            container.binding.imgDay.setImageResource(R.drawable.shape_calendar_complete)
        } else {
            container.binding.imgDay.setImageDrawable(null)
        }

    }

    override fun create(view: View): CalendarDayView =
        CalendarDayView(ItemStatisticsCalendarBinding.bind(view)).apply {
            binding.root.setOnClickListener {
                onClickCalendarDate(calendarDay.date)
            }
        }

    override fun updateGoalsData(data: Map<LocalDate, Goal>) {
        goalsData = data as LinkedHashMap<LocalDate, Goal>
    }

    override fun updateGoalData(data: Goal) {
        goalsData?.put(data.date, data)
    }
}