package com.dohyeok.gulpgulp.view.editdrinkdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.databinding.EditdrinkExistDrinkItemBinding

class ExistDrinkAdapter(private val context: Context) : RecyclerView.Adapter<ExistDrinkViewHolder>(),
    ExistDrinkAdapterContract.View, ExistDrinkAdapterContract.Model {
    var drinkList : ArrayList<Drink> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistDrinkViewHolder {
        return ExistDrinkViewHolder(
            EditdrinkExistDrinkItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun notifyDataInited() {
        notifyItemRangeChanged(0, drinkList.size)
    }

    override fun onBindViewHolder(holder: ExistDrinkViewHolder, position: Int) {
        holder.onBind(drinkList[position])
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    override fun updateDrinkList(drinkList: List<Drink>) {
        this.drinkList = ArrayList(drinkList)
    }

    override fun addDrink(drink: Drink): Int {
        drinkList.add(drink)
        notifyItemInserted(drinkList.size)
        return drinkList.size
    }
}