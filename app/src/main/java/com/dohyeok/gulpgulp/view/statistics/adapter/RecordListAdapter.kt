package com.dohyeok.gulpgulp.view.statistics.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.databinding.ItemStatisticsRecordBinding
import com.dohyeok.gulpgulp.util.swap
import javax.inject.Inject

class RecordListAdapter @Inject constructor(private val context: Context) :
    RecyclerView.Adapter<RecordListViewHolder>(), RecordListAdapterContract.View,
    RecordListAdapterContract.Model {
    private val recordData: MutableList<DrinkRecord> = mutableListOf()
    private val iconData: HashMap<String, Int> = HashMap()
    override lateinit var onClickRecord: (DrinkRecord) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordListViewHolder {
        return RecordListViewHolder(
            context,
            ItemStatisticsRecordBinding.inflate(LayoutInflater.from(context), parent, false)
        ).apply {
            itemView.setOnClickListener { onClickRecord(drinkRecord) }
        }
    }

    override fun onBindViewHolder(holder: RecordListViewHolder, position: Int) {
        holder.onBind(recordData[position], iconData[recordData[position].drink.icon])
    }

    override fun getItemCount(): Int {
        return recordData.size
    }

    override fun notifyRecordRemoved(position: Int) {
        notifyItemRemoved(position)
    }

    override fun notifyDataChanged() {
        notifyDataSetChanged()
    }

    override fun updateRecordsData(data: List<DrinkRecord>) {
        recordData.clear()
        recordData.addAll(data)
    }

    override fun updateIcons(iconMap: Map<String, Int>) {
        iconData.clear()
        iconData.putAll(iconMap)
    }

    override fun addNewData(newRecord: DrinkRecord, icon: Icon) {
        iconData[icon.name] = icon.resId

        for(i in recordData.indices) {
            if(recordData[i].recordTime >= newRecord.recordTime){
                recordData.add(i, newRecord)
                return
            }
        }
        recordData.add(newRecord)
    }

    override fun removeData(holderPosition: Int) {
        recordData.removeAt(holderPosition)
    }

    override fun updateData(updatedRecord: DrinkRecord, icon: Icon) {
        var oldIdx = 0;
        var newIdx = recordData.lastIndex;

        iconData[icon.name] = icon.resId

        for(i in recordData.indices) {
            if(updatedRecord.recordId == recordData[i].recordId) {
                recordData[i].drink = updatedRecord.drink
                recordData[i].recordTime = updatedRecord.recordTime
                oldIdx = i
            }
            if(recordData[i].recordTime >= updatedRecord.recordTime){
                newIdx = i
            }
        }

        recordData.swap(oldIdx, newIdx)
    }
}