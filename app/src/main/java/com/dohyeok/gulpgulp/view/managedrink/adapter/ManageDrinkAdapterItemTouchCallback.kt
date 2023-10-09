package com.dohyeok.gulpgulp.view.managedrink.adapter

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ManageDrinkAdapterItemTouchCallback(
    private var onItemMove: (ManageDrinkViewHolder, ManageDrinkViewHolder) -> Unit,
    private var onItemSwiped: (ManageDrinkViewHolder, Int) -> Unit,
    private var onItemSelectedChanged: (ManageDrinkViewHolder?, Int) -> Unit
) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(
            (ItemTouchHelper.UP or ItemTouchHelper.DOWN),
            ItemTouchHelper.LEFT
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        onItemMove(
            viewHolder as ManageDrinkViewHolder,
            target as ManageDrinkViewHolder
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemSwiped(viewHolder as ManageDrinkViewHolder, direction)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        onItemSelectedChanged(viewHolder as ManageDrinkViewHolder?, actionState)
        super.onSelectedChanged(viewHolder, actionState)
    }
}
