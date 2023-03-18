package com.dohyeok.gulpgulp.view.calendar.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.CalendarDetailItemBinding
import com.dohyeok.gulpgulp.util.toDatabaseFormat

class CalendarDetailViewHolder(private val binding: CalendarDetailItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ResourceType")
    fun onBind(item: Drink) {
        binding.imageDrink.setImageResource(item.iconId)
        binding.textDrink.text = item.name
        binding.textDrinkAmount.text = item.amount.toString()
        binding.textDrinkTime.text = item.time.toDatabaseFormat
    }
}
