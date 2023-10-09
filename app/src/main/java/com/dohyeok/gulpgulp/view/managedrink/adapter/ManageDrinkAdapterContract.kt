package com.dohyeok.gulpgulp.view.managedrink.adapter

import com.dohyeok.gulpgulp.data.drink.DrinkItem

interface ManageDrinkAdapterContract {
    interface View {
        fun notifyItemsChanged()
        fun notifyItemMove(from: Int, to: Int)
        fun notifyItemRemove(target: ManageDrinkViewHolder)
    }

    interface Model {

        var onDrinkLongClick: (DrinkItem) -> Unit
        fun refreshDrinkData(items: List<DrinkItem>, icons: Map<String, Int>)
        fun addDrinkItem(drinkItem: DrinkItem, iconResId: Int, isEnd: Boolean = true)
        fun swapDrinkItem(from: Int, to: Int)

        fun editDrinkItem(newItem: DrinkItem, iconResId: Int)
        fun removeDrinkItem(target: Int)

        fun getDrinksOrder(): Map<DrinkItem, Int>
    }
}