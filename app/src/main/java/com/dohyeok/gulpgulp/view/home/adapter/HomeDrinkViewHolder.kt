package com.dohyeok.gulpgulp.view.home.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.HomeDrinkItemBinding

class HomeDrinkViewHolder(private val binding: HomeDrinkItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var drink: Drink

    @SuppressLint("ResourceType")
    fun onBind(drink: Drink, resId: Int) {
        this.drink = drink
        binding.imageHomeDrink.setImageResource(resId)
        binding.textHomeDrink.text = drink.name
    }

}
