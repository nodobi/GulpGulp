package com.dohyeok.gulpgulp.view.editdrinkdetail.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.EditdrinkExistDrinkItemBinding

class ExistDrinkViewHolder(private val binding : EditdrinkExistDrinkItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun onBind(drink: Drink) {
        binding.textDrinkTitle.text = drink.name
        binding.textDrinkAmount.text = drink.amount.toString()
    }
}