package com.dohyeok.gulpgulp.view.editdrinkdetail.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.EditdrinkExistDrinkItemBinding

class ExistDrinkAdapter(private val context: Context) :
    RecyclerView.Adapter<ExistDrinkViewHolder>(),
    ExistDrinkAdapterContract.View, ExistDrinkAdapterContract.Model {
    override lateinit var onDrinkClick: (Drink) -> Unit
    var drinkList: ArrayList<Pair<Drink, Int>> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistDrinkViewHolder {
        return ExistDrinkViewHolder(
            EditdrinkExistDrinkItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        ).apply {
            this.itemView.setOnClickListener {
                onDrinkClick(this.drink)
            }
        }
    }

    override fun notifyDataInited() {
        notifyItemRangeChanged(0, drinkList.size)
    }

    override fun onBindViewHolder(holder: ExistDrinkViewHolder, position: Int) {
        holder.onBind(drinkList[position].first, drinkList[position].second)
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    override fun updateDrinkList(drinkList: List<Drink>) {
        this.drinkList = ArrayList()

        drinkList.forEach {
            this.drinkList.add(Pair(it, iconNameToIconResId(it.iconResName)))
        }
    }

    override fun addDrink(drink: Drink): Int {
        drinkList.add(Pair(drink, iconNameToIconResId(drink.iconResName)))
        notifyItemInserted(drinkList.size)
        return drink.order
    }

    override fun removeDrink(drink: Drink) {
        val idx = drinkList.indexOf(Pair(drink, iconNameToIconResId(drink.iconResName)))
        drinkList.removeAt(idx)
        notifyItemRemoved(idx)
    }

    @SuppressLint("DiscouragedApi")
    private fun iconNameToIconResId(iconName: String): Int =
        context.resources.getIdentifier(iconName, "drawable", context.packageName)

}