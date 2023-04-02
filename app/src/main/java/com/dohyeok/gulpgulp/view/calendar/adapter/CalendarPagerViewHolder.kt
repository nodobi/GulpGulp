package com.dohyeok.gulpgulp.view.calendar.adapter

import android.content.Context
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.databinding.CalendarPagerItemBinding
import com.dohyeok.gulpgulp.util.CalendarUtil
import java.time.LocalDate

class CalendarPagerViewHolder(private val binding: CalendarPagerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        context: Context,
        date: LocalDate,
        data: List<LocalDate>,
        parentWidth: Int,
        parentHeight: Int,
        onDateClicked: (LocalDate) -> Unit
    ) {
        val row = CalendarUtil.getCalendarWeekCnt(date)
        val column = 7

        binding.gridCalendar.apply {
            layoutParams.apply {
                columnCount = column
            }
        }

        for (i in 0 until (row * column)) {
            val view = TextView(context).apply {
                text = "${data[i].dayOfMonth}"
                layoutParams = GridLayout.LayoutParams().apply {
                    width = parentWidth / column
                    height = parentHeight / row
                    gravity = Gravity.CENTER
                }
            }
            view.setOnClickListener {
                onDateClicked.invoke(data[i])
            }
            binding.gridCalendar.addView(view)
        }
    }
}