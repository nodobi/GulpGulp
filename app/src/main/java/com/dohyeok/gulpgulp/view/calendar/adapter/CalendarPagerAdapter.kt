package com.dohyeok.gulpgulp.view.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dohyeok.gulpgulp.databinding.CalendarPagerItemBinding
import com.dohyeok.gulpgulp.util.CalendarUtil
import java.time.LocalDate

class CalendarPagerAdapter constructor(
    private val context: Context,
) : RecyclerView.Adapter<CalendarPagerViewHolder>(), CalendarPagerAdapterContract.View,
    CalendarPagerAdapterContract.Model {
    override var date: LocalDate = LocalDate.now()
    override var offset: Int = -1
    private var width = 0
    private var height = 0

    private var data: List<LocalDate> = listOf()

    override lateinit var onCalendarPageChanged: (LocalDate) -> Unit
    override lateinit var onDateClicked: (LocalDate) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarPagerViewHolder {
        if (width == 0 && height == 0) {
            width = parent.measuredWidth
            height = parent.measuredHeight
        }
        return CalendarPagerViewHolder(
            CalendarPagerItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CalendarPagerViewHolder, position: Int) {
        val pageDate = date.plusMonths((position - offset).toLong())
        data = CalendarUtil.getDateList(pageDate)
        holder.onBind(context, pageDate, data, width, height) {
            onDateClicked.invoke(it)
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