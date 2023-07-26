package com.dohyeok.gulpgulp.view.calendar.adapter

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import com.dohyeok.gulpgulp.databinding.CalendarHeaderBinding
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.format.TextStyle
import java.util.*

class CalendarHeaderBinder: MonthHeaderFooterBinder<CalendarHeaderBinder.MonthViewContainer>, CalendarHeaderBinderContract {
    override fun bind(container: MonthViewContainer, data: CalendarMonth) {
        if (container.headerLayout.tag == null) {
            container.headerLayout.tag = data.yearMonth
            container.headerLayout.children.map { it as TextView }
                .forEachIndexed { index, tv ->
                    tv.text = daysOfWeek()[index].getDisplayName(TextStyle.SHORT, Locale.US)
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
//                            tv.typeface = typeFace
                }
        }
    }

    override fun create(view: View): MonthViewContainer = MonthViewContainer(view)

    inner class MonthViewContainer(view: View): ViewContainer(view) {
        val headerLayout = CalendarHeaderBinding.bind(view).root
    }
}