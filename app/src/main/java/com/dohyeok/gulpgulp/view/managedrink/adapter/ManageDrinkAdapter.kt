package com.dohyeok.gulpgulp.view.managedrink.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.databinding.ItemManagedrinkDrinkBinding
import java.util.*

class ManageDrinkAdapter(private val context: Context) :
    RecyclerView.Adapter<ManageDrinkViewHolder>(),
    ManageDrinkAdapterContract.View,
    ManageDrinkAdapterContract.Model {
    private val drinkDataList = mutableListOf<DrinkItem>()
    private val drinkIconMap = mutableMapOf<String, Int>()
    override lateinit var onDrinkLongClick: (DrinkItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageDrinkViewHolder {
        return ManageDrinkViewHolder(
            ItemManagedrinkDrinkBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: ManageDrinkViewHolder, position: Int) {
        holder.onBind(drinkDataList[position], drinkIconMap[drinkDataList[position].icon])
    }

    override fun getItemCount(): Int {
        return drinkDataList.size
    }

    override fun notifyItemsChanged() {
        notifyDataSetChanged()
    }

    override fun refreshDrinkData(
        items: List<DrinkItem>,
        icons: Map<String, Int>
    ) {
        drinkDataList.clear()
        drinkDataList.addAll(items)
        drinkIconMap.clear()
        drinkIconMap.putAll(icons)
    }

    override fun addDrinkItem(drinkItem: DrinkItem, iconResId: Int, isEnd: Boolean) {
        if (isEnd) {
            drinkDataList.add(drinkItem)
            notifyItemInserted(drinkDataList.size - 1)
        }
    }

    override fun editDrinkItem(newItem: DrinkItem, iconResId: Int) {
        drinkIconMap.put(newItem.icon, iconResId)
        drinkDataList.forEachIndexed { idx, item ->
            if (item.id == newItem.id) {
                item.icon = newItem.icon
                item.name = newItem.name
                item.amount = newItem.amount
                notifyItemChanged(idx)
                return
            }
        }
    }

    override fun notifyItemMove(from: Int, to: Int) {
        notifyItemMoved(from, to)
        notifyItemRangeChanged(from, to)
    }

    override fun swapDrinkItem(from: Int, to: Int) {
        Collections.swap(drinkDataList, from, to)
    }

    override fun removeDrinkItem(target: Int) {
        drinkDataList.removeAt(target)
    }

    override fun notifyItemRemove(target: ManageDrinkViewHolder) {
        notifyItemRemoved(target.adapterPosition)
    }

    override fun getDrinksOrder(): Map<DrinkItem, Int> {
        val drinkOrderMap = mutableMapOf<DrinkItem, Int>()
        drinkDataList.forEachIndexed { index, drinkItem ->
            drinkOrderMap.put(drinkItem, index)
        }

        return drinkOrderMap
    }
}