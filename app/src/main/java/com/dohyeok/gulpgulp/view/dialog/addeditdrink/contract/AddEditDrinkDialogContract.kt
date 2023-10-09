package com.dohyeok.gulpgulp.view.dialog.addeditdrink.contract

import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.view.base.BaseContract

interface AddEditDrinkDialogContract {
    interface View: BaseContract.View {
        fun updateDrinkDetails(drinkItem: DrinkItem, iconResId: Int, withHints: Boolean)
        fun updateDrinkIcons(newIcon: Icon)
        fun getDrinkAmount(): String
        fun getDrinkName(): String
    }

    interface Presenter: BaseContract.Presenter<View> {
        var onIconDialogCommit: (Icon) -> Unit

        fun initDrink(editedDrink: DrinkItem?)
        fun getDrink(): DrinkItem
        fun refreshDrink()
    }
}