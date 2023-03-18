package com.dohyeok.gulpgulp.view.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.CalendarDetailItemBinding

class CalendarDetailAdapter(private val context: Context) :
    RecyclerView.Adapter<CalendarDetailViewHolder>(), CalendarDetailAdapterContract.View,
    CalendarDetailAdapterContract.Model {
    var drinkData: List<Drink> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDetailViewHolder {
        return CalendarDetailViewHolder(
            CalendarDetailItemBinding.inflate(
                LayoutInflater.from(
                    context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CalendarDetailViewHolder, position: Int) {
        holder.onBind(drinkData[position])
    }

    override fun getItemCount(): Int {
        return drinkData.size
    }

    override fun updateDrinkData(loadDrinks: List<Drink>) {
        drinkData = loadDrinks
    }

    override fun notifyDataChanged() {
        notifyDataSetChanged()
    }
}