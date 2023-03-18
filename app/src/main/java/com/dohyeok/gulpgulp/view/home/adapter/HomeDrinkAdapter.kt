package com.dohyeok.gulpgulp.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.HomeDrinkItemBinding

class HomeDrinkAdapter(private val context: Context) : RecyclerView.Adapter<HomeDrinkViewHolder>(),
    HomeDrinkAdapterContract.View, HomeDrinkAdapterContract.Model {
    override lateinit var onDrinkClicked: (Drink) -> Unit
    var drinkData: List<Drink> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDrinkViewHolder {
        return HomeDrinkViewHolder(
            HomeDrinkItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeDrinkViewHolder, position: Int) {
        holder.onBind(drinkData[position], onDrinkClicked)
    }

    override fun getItemCount(): Int {
        return drinkData.size
    }

    override fun updateDrinkList(drinkList: List<Drink>) {
        drinkData = drinkList
    }

    override fun notifyDataUpdate() {
        notifyItemRangeInserted(0, drinkData.size)
    }
}