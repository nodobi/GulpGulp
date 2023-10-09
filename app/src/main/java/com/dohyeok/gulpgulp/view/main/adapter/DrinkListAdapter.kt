package com.dohyeok.gulpgulp.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.databinding.ItemMainDrinkBinding

class DrinkListAdapter(private val context: Context): RecyclerView.Adapter<DrinkListViewHolder>(), DrinkListContract.View, DrinkListContract.Model {
    private val drinkItems: MutableList<DrinkItem> = mutableListOf()
    private val drinkIcons: MutableMap<String, Int> = mutableMapOf()
    override lateinit var onClickDrinkItem: (DrinkItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkListViewHolder {
        return DrinkListViewHolder(ItemMainDrinkBinding.inflate(LayoutInflater.from(context), parent, false)).apply {
            itemView.setOnClickListener {
                onClickDrinkItem.invoke(drinkItem)
            }
        }
    }

    override fun onBindViewHolder(holder: DrinkListViewHolder, position: Int) {
        drinkItems[position].let {
            holder.onBind(it, drinkIcons[it.icon]!!)
        }
    }

    override fun getItemCount(): Int {
        return drinkItems.size
    }

    override fun notifyItemsChanged() {
        notifyDataSetChanged()
    }

    override fun refreshDrinkItems(items: List<DrinkItem>, icons: Map<String, Int>) {
        drinkItems.clear()
        drinkIcons.clear()
        drinkItems.addAll(items)
        drinkIcons.putAll(icons)
    }
}