package com.dohyeok.gulpgulp.view.calendar.adapter

import android.view.View
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.CalendarItemBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate

class CalendarDayBinder: MonthDayBinder<CalendarDayBinder.DayViewContainer>, CalendarDayBinderContract
{
    override lateinit var onClickDayView: (selectedDate: LocalDate, oldDate: LocalDate?) -> Unit

    override var drinkResultMap: Map<LocalDate, Boolean>? = null
    override var selectedDate: LocalDate? = null

    override fun bind(container: DayViewContainer, data: CalendarDay) {
        container.day = data

        if(data.position == DayPosition.MonthDate) {
            container.setDayText(data.date.dayOfMonth.toString())
            if(data.date == selectedDate) {
                container.binding.root.setBackgroundResource(if (selectedDate == data.date) R.drawable.shape_selected_calendar else 0)
            } else {
                container.binding.root.setBackgroundResource(0)
            }

            drinkResultMap?.let {map ->
                if(map.getOrDefault(data.date, false)) {
                    container.binding.root.setBackgroundResource(R.drawable.shape_selected_calendar)
                    container.binding.textDate.text = ""
                }
            }

        } else {
            container.setDayText("")
        }


    }

    override fun create(view: View): DayViewContainer = DayViewContainer(view)


    inner class DayViewContainer(view: View): ViewContainer(view) {
        lateinit var day: CalendarDay
        var binding = CalendarItemBinding.bind(view)

        fun setDayText(text: String) {
            binding.textDate.text = text
        }

        init {
            view.setOnClickListener {
                if(day.position == DayPosition.MonthDate) {
                    onClickDayView.invoke(day.date, selectedDate)
                    selectedDate = day.date
                }
            }
        }
    }
}