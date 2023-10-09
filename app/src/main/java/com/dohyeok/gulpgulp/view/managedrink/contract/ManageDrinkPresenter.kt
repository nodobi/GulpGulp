package com.dohyeok.gulpgulp.view.managedrink.contract

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.view.managedrink.adapter.ManageDrinkAdapterContract
import com.dohyeok.gulpgulp.view.managedrink.adapter.ManageDrinkViewHolder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManageDrinkPresenter(
    override var adapterView: ManageDrinkAdapterContract.View,
    override var adapterModel: ManageDrinkAdapterContract.Model,
    private var drinkRepository: DrinkRepository,
    private var iconRepository: IconRepository,
    private val uiContext: CoroutineDispatcher = Dispatchers.Main
) : ManageDrinkContract.Presenter {
    override var onAddDrinkDialogCommit: (DrinkItem, Context) -> Unit =
        { item, context -> onAddDrinkDialogCommitListener(item, context) }
    override var onEditDrinkDialogCommit: (DrinkItem, Context) -> Unit =
        { item, context -> onEditDrinkDialogCommitListener(item, context) }
    override var _view: ManageDrinkContract.View? = null

    private fun onEditDrinkDialogCommitListener(newItem: DrinkItem, context: Context) {
        CoroutineScope(uiContext).launch {
            drinkRepository.updateDrinkItem(newItem.id, newItem)
            adapterModel.editDrinkItem(newItem, iconRepository.getIconResId(newItem.icon))
        }
    }


    private fun onAddDrinkDialogCommitListener(unOrderedItem: DrinkItem, context: Context) {
        CoroutineScope(uiContext).launch {
            val drinkItem =
                unOrderedItem.apply { order = drinkRepository.getLastDrinkItemOrder() + 1 }
            drinkRepository.saveDrinkItem(drinkItem)
            val iconResId = iconRepository.getIconResId(drinkItem.icon)
            adapterModel.addDrinkItem(drinkItem, iconResId)
        }
    }

    override fun loadDrinkItemData(context: Context) {
        CoroutineScope(uiContext).launch {
            adapterModel.refreshDrinkData(
                drinkRepository.getAllDrinkItems(),
                iconRepository.getAllIconsResId()
            )
            adapterView.notifyItemsChanged()
        }
    }

    override fun onItemMove(fromHolder: ManageDrinkViewHolder, toHolder: ManageDrinkViewHolder) {
        val fromPosition = fromHolder.adapterPosition
        val toPosition = toHolder.adapterPosition
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                adapterModel.swapDrinkItem(i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                adapterModel.swapDrinkItem(i, i - 1)
            }
        }
        adapterView.notifyItemMove(fromHolder.adapterPosition, toHolder.adapterPosition)
    }

    override fun onItemSwiped(viewholder: ManageDrinkViewHolder, direction: Int) {
        CoroutineScope(uiContext).launch {
            adapterModel.removeDrinkItem(viewholder.adapterPosition)
            adapterView.notifyItemRemove(viewholder)
            drinkRepository.deleteDrinkItem(viewholder.drinkItem)
        }
    }

    override fun onItemSelectedChanged(viewHolder: ManageDrinkViewHolder?, actionState: Int) {
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_IDLE -> {
                CoroutineScope(uiContext).launch {
                    drinkRepository.updateDrinksOrder(adapterModel.getDrinksOrder().toList())
                }

            }
        }
    }
}