package com.dohyeok.gulpgulp.view.calendar.contract

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.util.CalendarUtil
import com.dohyeok.gulpgulp.view.calendar.ItemTouchCallback
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarAdapterContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapterContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarPresenter constructor(
    override var view: CalendarContract.View,
    override var adapterView: CalendarAdapterContract.View,
    override var adapterModel: CalendarAdapterContract.Model,
    override var detailAdapterView: CalendarDetailAdapterContract.View,
    override var detailAdapterModel: CalendarDetailAdapterContract.Model,
    override var drinkRepository: DrinkRepository
) : CalendarContract.Presenter {

    init {
        adapterModel.onDateClicked = { onDateClickListener(it) }
    }

    override var date: LocalDate = LocalDate.now()

    fun updateAdapterData() {
        adapterModel.updateSize(7, CalendarUtil.getCalendarWeekCnt(date))
        adapterModel.updateData(CalendarUtil.getDateList(date))

        view.attachItemTouchHelper(object : ItemTouchCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos: Int = viewHolder.adapterPosition
                val item: DrinkRecord = detailAdapterModel.recordData[pos]
                detailAdapterView.notifyItemDraw(pos)
                val onPositive: (Unit) -> Unit = {
                    detailAdapterModel.removeItem(pos)
                    CoroutineScope(Dispatchers.IO).launch {
                        drinkRepository.deleteDrinkRecord(item)
                    }
                }
                val onNegative: (Unit) -> Unit = {

                }

                view.showDialog(onPositive, onNegative)
            }
        })
    }

    override fun updateDate() {
        view.updateCalendarDates(date)
    }

    override fun updateDetailData() {
        CoroutineScope(Dispatchers.Main).launch {
            detailAdapterModel.updateDrinkData(drinkRepository.loadDrinkRecords(date))
            detailAdapterView.notifyDataChanged()
        }
    }

    private fun onDateClickListener(date: LocalDate) {
        this.date = date
        updateDate()
        updateDetailData()
    }
}