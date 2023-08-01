package com.dohyeok.gulpgulp.view.calendar.adapter

import com.dohyeok.gulpgulp.data.DrinkRecord

interface CalendarDetailAdapterContract {
    interface View {
        fun notifyDataChanged()
        fun notifyItemDraw(position: Int)
        fun notifyItemChanged(idx: Int)
    }

    interface Model {
        var onDrinkDetailClick: (DrinkRecord, Int) -> Unit

        fun updateDrinkData(loadDrinks: List<DrinkRecord>)
        fun removeItem(position: Int)
        fun restoreItem(item: DrinkRecord, position: Int)
        fun getRecordData(pos: Int): DrinkRecord
        fun updateItem(newRecord: DrinkRecord): Int
    }
}