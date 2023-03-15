package com.dohyeok.gulpgulp.view.calendar.adapter

import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.databinding.CalendarItemBinding
import com.dohyeok.gulpgulp.util.padZero
import java.time.LocalDate

class CalendarViewHolder(private val binding: CalendarItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(date: LocalDate, width: Int, height: Int) {
        binding.textDate.text = date.dayOfMonth.padZero()
        binding.root.layoutParams = RelativeLayout.LayoutParams(width, height)
        binding.root.requestLayout()
    }
}