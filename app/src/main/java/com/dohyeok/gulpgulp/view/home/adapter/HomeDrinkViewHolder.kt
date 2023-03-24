package com.dohyeok.gulpgulp.view.home.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.HomeDrinkItemBinding

class HomeDrinkViewHolder(private val binding: HomeDrinkItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ResourceType")
    fun onBind(drinkRecord: Drink, action: (Drink) -> Unit) {
        binding.imageHomeDrink.setImageResource(drinkRecord.iconId)
        binding.textHomeDrink.text = drinkRecord.name
        binding.root.setOnClickListener {
            action.invoke(drinkRecord)
        }
    }

}
