package com.dohyeok.gulpgulp.view.calendar.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.databinding.CalendarDetailItemBinding
import com.dohyeok.gulpgulp.util.toDatabaseFormat

class CalendarDetailViewHolder(private val binding: CalendarDetailItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ResourceType")
    fun onBind(item: DrinkRecord, iconResId: Int) {
        binding.imageDrink.setImageResource(iconResId)
        binding.textDrink.text = item.drink.name
        binding.textDrinkAmount.text = item.drink.amount.toString()
        binding.textDrinkTime.text = item.time.toDatabaseFormat
    }
}
