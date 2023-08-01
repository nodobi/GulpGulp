package com.dohyeok.gulpgulp.view.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.databinding.CalendarDetailItemBinding

class CalendarDetailAdapter(private val context: Context) :
    RecyclerView.Adapter<CalendarDetailViewHolder>(), CalendarDetailAdapterContract.View,
    CalendarDetailAdapterContract.Model {

    private var recordData: ArrayList<Pair<DrinkRecord, Int>> = arrayListOf()
    override lateinit var onDrinkDetailClick: (DrinkRecord, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDetailViewHolder {
        return CalendarDetailViewHolder(
            CalendarDetailItemBinding.inflate(
                LayoutInflater.from(
                    context
                ), parent, false
            )
        ).apply {
            itemView.setOnClickListener {
                onDrinkDetailClick.invoke(
                    recordData[bindingAdapterPosition].first,
                    recordData[bindingAdapterPosition].second
                )
            }
        }
    }

    override fun onBindViewHolder(holder: CalendarDetailViewHolder, position: Int) {
        holder.onBind(recordData[position].first, recordData[position].second)
    }

    override fun getItemCount(): Int {
        return recordData.size
    }

    override fun updateDrinkData(loadDrinks: List<DrinkRecord>) {
        recordData = ArrayList<Pair<DrinkRecord, Int>>(loadDrinks.size).apply {
            loadDrinks.forEach {
                add(Pair(it, iconNameToIconResId(it.drink.iconResName)))
            }
        }
    }

    override fun notifyDataChanged() {
        notifyDataSetChanged()
    }

    override fun notifyItemDraw(position: Int) {
        notifyItemChanged(position)
    }

    override fun removeItem(position: Int) {
        recordData.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun restoreItem(item: DrinkRecord, position: Int) {
        recordData.add(position, Pair(item, iconNameToIconResId(item.drink.iconResName)))
        notifyItemInserted(position)
    }

    override fun getRecordData(pos: Int): DrinkRecord {
        return recordData[pos].first
    }

    override fun updateItem(newRecord: DrinkRecord): Int {
        recordData.forEachIndexed { index, data ->
            if(data.first.id == newRecord.id) {
                recordData[index] = Pair(newRecord, iconNameToIconResId(newRecord.drink.iconResName))
                return index
            }
        }
        return -1
    }

    private fun iconNameToIconResId(resName: String): Int =
        context.resources.getIdentifier(resName, "drawable", context.packageName)

}