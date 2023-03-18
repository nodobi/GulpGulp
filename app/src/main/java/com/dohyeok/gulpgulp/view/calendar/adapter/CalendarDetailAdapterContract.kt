package com.dohyeok.gulpgulp.view.calendar.adapter

import com.dohyeok.gulpgulp.data.Drink

interface CalendarDetailAdapterContract {
    interface View {
        fun notifyDataChanged()
    }

    interface Model {
        fun updateDrinkData(loadDrinks: List<Drink>)

    }
}