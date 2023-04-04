package com.dohyeok.gulpgulp.view.calendar.adapter

import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.databinding.CalendarPagerItemBinding
import java.time.LocalDate

class CalendarPagerViewHolder(val binding: CalendarPagerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        data: List<LocalDate>,
        selectedDate: LocalDate,
        viewWidth: Int,
        viewHeight: Int,
        row: Int,
        column: Int,
        onDateClicked: (View, LocalDate) -> Unit
    ) {
        binding.gridCalendar.apply {
            layoutParams.apply {
                columnCount = column
            }
        }
        for (i in 0 until (row * column)) {
            (binding.gridCalendar.getChildAt(i) as TextView).apply {
                text = "${data[i].dayOfMonth}"
                isSelected = (data[i] == selectedDate)
                layoutParams = GridLayout.LayoutParams().apply {
                    width = viewWidth
                    height = viewHeight
                }
                setOnClickListener {
                    onDateClicked.invoke(it, data[i])
                }
            }
        }
    }
}