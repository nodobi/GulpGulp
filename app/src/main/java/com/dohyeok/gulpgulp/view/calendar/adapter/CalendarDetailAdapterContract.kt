package com.dohyeok.gulpgulp.view.calendar.adapter

import com.dohyeok.gulpgulp.data.Drink

interface CalendarDetailAdapterContract {
    interface View {
        fun notifyDataChanged()
        fun notifyItemDraw(position: Int)
    }

    interface Model {
        var drinkData: ArrayList<Drink>

        fun updateDrinkData(loadDrinks: List<Drink>)
        fun removeItem(position: Int)
        fun restoreItem(item: Drink, position: Int)
    }
}