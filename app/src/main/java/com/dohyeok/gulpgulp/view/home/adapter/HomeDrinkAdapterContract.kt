package com.dohyeok.gulpgulp.view.home.adapter

import com.dohyeok.gulpgulp.data.Drink

interface HomeDrinkAdapterContract {
    interface View {
        fun notifyDataUpdate()

    }

    interface Model {
        fun updateDrinkList(drinkList: List<Drink>)

        var onDrinkClicked: (Drink) -> Unit
    }
}