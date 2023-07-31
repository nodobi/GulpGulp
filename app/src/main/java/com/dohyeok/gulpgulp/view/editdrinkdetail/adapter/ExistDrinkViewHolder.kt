package com.dohyeok.gulpgulp.view.editdrinkdetail.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.EditdrinkExistDrinkItemBinding

class ExistDrinkViewHolder(private val binding: EditdrinkExistDrinkItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var drink: Drink

    @SuppressLint("ResourceType")
    fun onBind(drink: Drink, resId: Int) {
        this.drink = drink
        binding.imageDrinkImage.setImageResource(resId)
        binding.textDrinkTitle.text = drink.name
        binding.textDrinkAmount.text = drink.amount.toString()
    }
}