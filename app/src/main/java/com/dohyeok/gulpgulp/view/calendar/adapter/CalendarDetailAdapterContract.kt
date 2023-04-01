package com.dohyeok.gulpgulp.view.calendar.adapter

import com.dohyeok.gulpgulp.data.DrinkRecord
import java.time.LocalDate

interface CalendarDetailAdapterContract {
    interface View {
        fun notifyDataChanged()
        fun notifyItemDraw(position: Int)
    }

    interface Model {
        var recordData: ArrayList<DrinkRecord>
        var currentDate: LocalDate

        fun updateDrinkData(loadDrinks: List<DrinkRecord>)
        fun removeItem(position: Int)
        fun restoreItem(item: DrinkRecord, position: Int)
    }
}