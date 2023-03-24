package com.dohyeok.gulpgulp.view.calendar.adapter

import com.dohyeok.gulpgulp.data.DrinkRecord

interface CalendarDetailAdapterContract {
    interface View {
        fun notifyDataChanged()
        fun notifyItemDraw(position: Int)
    }

    interface Model {
        var recordData: ArrayList<DrinkRecord>

        fun updateDrinkData(loadDrinks: List<DrinkRecord>)
        fun removeItem(position: Int)
        fun restoreItem(item: DrinkRecord, position: Int)
    }
}