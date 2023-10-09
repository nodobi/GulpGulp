package com.dohyeok.gulpgulp.view.managedrink.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.databinding.ItemManagedrinkDrinkBinding

class ManageDrinkViewHolder(val binding: ItemManagedrinkDrinkBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {
    lateinit var drinkItem: DrinkItem

    fun onBind(drinkItem: DrinkItem, iconResId: Int?) {
        this.drinkItem = drinkItem
        binding.apply {
            iconResId?.let { imgDrinkIcon.setImageResource(it) }
            textDrinkName.text = drinkItem.name
            textDrinkAmount.text = context.getString(R.string.drink_format_ml, drinkItem.amount)

        }
    }
}