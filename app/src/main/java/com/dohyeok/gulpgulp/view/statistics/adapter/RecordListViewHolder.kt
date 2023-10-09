package com.dohyeok.gulpgulp.view.statistics.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.databinding.ItemStatisticsRecordBinding

class RecordListViewHolder(
    private val context: Context,
    private val binding: ItemStatisticsRecordBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var drinkRecord: DrinkRecord
    fun onBind(record: DrinkRecord, resId: Int?) {
        drinkRecord = record
        binding.apply {
            resId?.let {
                imageRecordDrink.setImageResource(it)
            }
            textRecordDrinkName.text = drinkRecord.drink.name
            textRecordDrinkAmount.text = context.resources.getString(R.string.main_drink_amount_unit, drinkRecord.drink.amount)
            textRecordTime.text = drinkRecord.recordTime.toString()
        }
    }
}