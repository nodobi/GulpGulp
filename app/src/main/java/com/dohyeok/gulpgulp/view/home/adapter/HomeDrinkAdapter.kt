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
    var drinkData: List<Pair<Drink, Int>> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDrinkViewHolder {
        return HomeDrinkViewHolder(
            HomeDrinkItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                onDrinkClicked.invoke(drink)
            }
        }
    }

    override fun onBindViewHolder(holder: HomeDrinkViewHolder, position: Int) {
        holder.onBind(drinkData[position].first, drinkData[position].second)
    }

    override fun getItemCount(): Int {
        return drinkData.size
    }

    override fun updateDrinkList(drinkList: List<Drink>) {
        drinkData = mutableListOf<Pair<Drink, Int>>().apply {
            drinkList.forEach {
                add(Pair(it, iconNameToIconResId(it.iconResName)))
            }
        }
    }

    override fun notifyDataUpdate() {
        notifyItemRangeInserted(0, drinkData.size)
    }

    private fun iconNameToIconResId(iconName: String): Int =
        context.resources.getIdentifier(iconName, "drawable", context.packageName)
}