package com.dohyeok.gulpgulp.view.main.adapter

import com.dohyeok.gulpgulp.data.drink.DrinkItem

interface DrinkListContract {
    interface View {
        var onClickDrinkItem: (DrinkItem) -> Unit
        fun notifyItemsChanged()
    }

    interface Model {
        fun refreshDrinkItems(items: List<DrinkItem>, icons: Map<String, Int>)

    }
}