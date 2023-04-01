package com.dohyeok.gulpgulp.view.calendar.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.databinding.CalendarItemBinding
import java.time.LocalDate

class CalendarAdapter(private val context: Context) : RecyclerView.Adapter<CalendarViewHolder>(),
    CalendarAdapterContract.View, CalendarAdapterContract.Model {
    override var currentDate: LocalDate = LocalDate.now()
    override lateinit var onDateClicked: (LocalDate) -> Unit

    private var selectedDate: LocalDate = LocalDate.now()
    private lateinit var selectedHolder: CalendarViewHolder
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
        if(selectedDate == data[position]) {
            selectedHolder = holder
        }
        holder.itemView.isSelected = (selectedDate == data[position])

        holder.onBind(data[position], width, height) { date, selected ->
            selected.itemView.isSelected = true

            selectedDate = date
            selectedHolder.itemView.isSelected = false
            selectedHolder = selected

            onDateClicked.invoke(date)
        }
    }

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun updateData(data: List<LocalDate>) {
        this.data = data
    }

    override fun updateSize(column: Int, row: Int) {
        this.column = column
        this.row = row
    }
}