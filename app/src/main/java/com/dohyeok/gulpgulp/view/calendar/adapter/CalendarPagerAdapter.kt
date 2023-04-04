package com.dohyeok.gulpgulp.view.calendar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.CalendarPagerItemBinding
import com.dohyeok.gulpgulp.util.CalendarUtil
import java.time.LocalDate

class CalendarPagerAdapter constructor(
    private val context: Context,
) : RecyclerView.Adapter<CalendarPagerViewHolder>(), CalendarPagerAdapterContract.View,
    CalendarPagerAdapterContract.Model {
    override var date: LocalDate = LocalDate.now()
    override var offset: Int = -1
    private var pagerWidth = 0
    private var pagerHeight = 0

    private var lastSelectedPos: Int = -1
    private var currSelectedPos: Int = -1
    private var selectedDate: LocalDate = LocalDate.now()

    override lateinit var onCalendarPageChanged: (LocalDate) -> Unit
    override lateinit var onDateClicked: (LocalDate) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarPagerViewHolder {
        val binding = CalendarPagerItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        if (pagerWidth == 0 && pagerHeight == 0) {
            pagerWidth = parent.measuredWidth
            pagerHeight = parent.measuredHeight
        }
        for (i in 0 until 42) {
            val dateView = TextView(context).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    gravity = Gravity.CENTER
                }
                background = ContextCompat.getDrawable(context, R.drawable.selector_calendar_item)
            }
            binding.gridCalendar.addView(dateView)
        }

        return CalendarPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarPagerViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val pageDate = date.plusMonths((position - offset).toLong())
        val data = CalendarUtil.getDateList(pageDate)
        val row = CalendarUtil.getCalendarWeekCnt(pageDate)
        val column = 7

        holder.onBind(
            data,
            selectedDate,
            pagerWidth / column,
            pagerHeight / row,
            row,
            column
        ) { clickedView, clickedDate ->
            selectedDate = clickedDate

            currSelectedPos = position
            notifyItemRangeChanged(currSelectedPos - 1, 3)

            if (lastSelectedPos != -1 && currSelectedPos != lastSelectedPos)
                notifyItemRangeChanged(lastSelectedPos - 1, 3)
            lastSelectedPos = currSelectedPos

            onDateClicked.invoke(clickedDate)
        }
    }

    override fun getItemCount(): Int {
        return offset * 2
    }

    inner class PageChangeCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            onCalendarPageChanged(date.plusMonths((position - offset).toLong()))
        }
    }
}