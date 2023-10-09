package com.dohyeok.gulpgulp.view.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.databinding.ItemMainDrinkBinding

class DrinkListViewHolder(private val binding: ItemMainDrinkBinding): RecyclerView.ViewHolder(binding.root) {
    lateinit var drinkItem: DrinkItem

    fun onBind(drinkItem: DrinkItem, iconResId: Int) {
        this.drinkItem = drinkItem
        binding.textDrinkName.setText(drinkItem.name)
        binding.imgDrink.setImageResource(R.drawable.ic_calendar_24dp)
        binding.imgDrink.setImageResource(iconResId)
    }
}