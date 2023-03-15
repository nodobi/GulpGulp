package com.dohyeok.gulpgulp.view.calendar.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.CalendarDetailItemBinding

class CalendarDetailViewHolder(private val binding: CalendarDetailItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(position: Int) {
        binding.imageDrink.setImageResource(R.drawable.ic_bottle_24dp)
        binding.textDrink.text = "물"
        binding.textDrinkAmount.text = "1500ml"
        binding.textDrinkTime.text = "15:15"
    }
}
