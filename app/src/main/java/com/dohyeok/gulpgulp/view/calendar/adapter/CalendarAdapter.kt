package com.dohyeok.gulpgulp.view.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.databinding.CalendarItemBinding
import java.time.LocalDate

class CalendarAdapter(private val context: Context) : RecyclerView.Adapter<CalendarViewHolder>(),
    CalendarAdapterContract.View, CalendarAdapterContract.Model {
    override lateinit var onDateClicked: (LocalDate) -> Unit
    private var data: List<LocalDate> = listOf()

    private var column: Int = 1
    private var row: Int = 1
    private var width: Int = -1
    private var height: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        if (column != 1 && row != 1) {
            width = parent.measuredWidth / column
            height = parent.measuredHeight / row
        }
        return CalendarViewHolder(
            CalendarItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.onBind(data[position], width, height, onDateClicked)
    }

    override fun notifyAdapter() {
        notifyAdapter()
    }

    override fun updateData(data: List<LocalDate>) {
        this.data = data
    }

    override fun updateSize(column: Int, row: Int) {
        this.column = column
        this.row = row
    }
}