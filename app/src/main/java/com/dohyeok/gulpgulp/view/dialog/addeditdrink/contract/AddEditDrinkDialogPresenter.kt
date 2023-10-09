package com.dohyeok.gulpgulp.view.dialog.addeditdrink.contract

import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.di.module.UIDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddEditDrinkDialogPresenter @Inject constructor(
    private var iconRepository: IconRepository,
    @UIDispatcher private var uiDispatcher: CoroutineDispatcher
) : AddEditDrinkDialogContract.Presenter {
    override var _view: AddEditDrinkDialogContract.View? = null
    private var editedDrinkItem: DrinkItem = DrinkItem("ic_calendar_24dp", "water", 1000)

    override var onIconDialogCommit: (Icon) -> Unit = { onIconDialogCommitListener(it) }

    override fun initDrink(editedDrink: DrinkItem?) {
        CoroutineScope(uiDispatcher).launch {
            if (editedDrink != null) {
                editedDrinkItem = editedDrink
                view.updateDrinkDetails(
                    editedDrinkItem, iconRepository.getIconResId(
                        editedDrinkItem.icon
                    ), false
                )
            } else {
                view.updateDrinkDetails(
                    editedDrinkItem, iconRepository.getIconResId(
                        editedDrinkItem.icon
                    ), true
                )
            }
        }
    }

    override fun getDrink(): DrinkItem {
        return editedDrinkItem
    }

    override fun refreshDrink() {
        editedDrinkItem.let {
            it.name = if (view.getDrinkName() == "") "water" else it.name
            it.amount = view.getDrinkAmount().toIntOrNull() ?: it.amount
        }
    }

    private fun onIconDialogCommitListener(icon: Icon) {
        view.updateDrinkIcons(icon)
        editedDrinkItem.icon = icon.name
    }

}