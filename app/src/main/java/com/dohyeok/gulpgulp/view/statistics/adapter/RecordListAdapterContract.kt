package com.dohyeok.gulpgulp.view.statistics.adapter

import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.data.record.DrinkRecord

interface RecordListAdapterContract {
    interface View {
        var onClickRecord: (DrinkRecord) -> Unit

        fun notifyDataChanged()
        fun notifyRecordRemoved(position: Int)
    }
    interface Model {
        fun updateRecordsData(data: List<DrinkRecord>)
        fun updateIcons(iconMap: Map<String, Int>)
        fun addNewData(newRecord: DrinkRecord, icon: Icon)
        fun removeData(holderPosition: Int)
        fun updateData(updatedRecord: DrinkRecord, icon: Icon)
    }
}