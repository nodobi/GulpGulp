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
    override var drinkData: ArrayList<Drink> = arrayListOf()

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
        drinkData = ArrayList(loadDrinks)
    }

    override fun notifyDataChanged() {
        notifyDataSetChanged()
    }

    override fun notifyItemDraw(position: Int) {
        notifyItemChanged(position)
    }

    override fun removeItem(position: Int) {
        drinkData.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun restoreItem(item: Drink, position: Int) {
        drinkData.add(position, item)
        notifyItemInserted(position)
    }

}