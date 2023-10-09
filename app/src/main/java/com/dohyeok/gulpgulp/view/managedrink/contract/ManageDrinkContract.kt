package com.dohyeok.gulpgulp.view.managedrink.contract

import android.content.Context
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.managedrink.adapter.ManageDrinkAdapterContract
import com.dohyeok.gulpgulp.view.managedrink.adapter.ManageDrinkViewHolder

interface ManageDrinkContract {
    interface View: BaseContract.View {

    }
    interface Presenter: BaseContract.Presenter<View> {
        var adapterView: ManageDrinkAdapterContract.View
        var adapterModel: ManageDrinkAdapterContract.Model

        var onAddDrinkDialogCommit: (DrinkItem, Context) -> Unit
        var onEditDrinkDialogCommit: (DrinkItem, Context) -> Unit

        fun loadDrinkItemData(context: Context)
        fun onItemMove(fromHolder: ManageDrinkViewHolder, toHolder: ManageDrinkViewHolder)
        fun onItemSwiped(viewholder: ManageDrinkViewHolder, direction: Int)
        fun onItemSelectedChanged(viewHolder: ManageDrinkViewHolder?, actionState: Int)

    }
}