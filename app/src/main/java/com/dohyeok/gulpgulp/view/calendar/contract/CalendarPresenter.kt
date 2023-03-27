package com.dohyeok.gulpgulp.view.calendar.contract

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.util.CalendarUtil
import com.dohyeok.gulpgulp.util.SPUtils
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
    override var drinkRepository: DrinkRepository,
    private var spUtils: SPUtils
) : CalendarContract.Presenter {
    init {
        adapterModel.onDateClicked = { onDateClickListener(it) }
    }

    override var date: LocalDate = LocalDate.now()

    override fun updateAdapterData() {
        adapterModel.updateSize(7, CalendarUtil.getCalendarWeekCnt(date))
        adapterModel.updateData(CalendarUtil.getDateList(date))

        view.attachItemTouchHelper(object : ItemTouchCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos: Int = viewHolder.adapterPosition
                val item: DrinkRecord = detailAdapterModel.recordData[pos]
                detailAdapterView.notifyItemDraw(pos)

                view.showDialog(
                    onPositive = {
                        detailAdapterModel.removeItem(pos)
                        CoroutineScope(Dispatchers.Main).launch {
                            drinkRepository.deleteDrinkRecord(item)
                            updateDetails()
                        }
                    },
                    onDismiss = {

                    }
                )
            }
        })
    }

    override fun updateDate() {
        view.updateCalendarDates(date)
    }

    override fun updateDetailAdapterData() {
        CoroutineScope(Dispatchers.Main).launch {
            detailAdapterModel.updateDrinkData(drinkRepository.loadDrinkRecords(date))
            detailAdapterView.notifyDataChanged()
        }
    }

    override fun updateProgress() {
        CoroutineScope(Dispatchers.Main).launch {
            view.changeProgressPercent(
                (drinkRepository.loadDrinkAmount()
                    .toFloat() / spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000) * 100).toInt()
            )
        }
    }

    override fun updateDetails() {
        updateDetailProgresses()
        updateDetailDrinkAmount()
    }

    private fun updateDetailProgresses() {
        CoroutineScope(Dispatchers.Main).launch {
            val goal = drinkRepository.loadDrinkGoal(date)?.amount
                ?: spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000)
            view.changeDetailProgressPercent(
                (drinkRepository.loadDrinkAmount(date) * 100f / goal).toInt()
            )
        }
    }

    private fun updateDetailDrinkAmount() {
        CoroutineScope(Dispatchers.Main).launch {
            view.changeDetailDrinkAmount(
                drinkRepository.loadDrinkAmount(date)
            )
        }
    }

    private fun onDateClickListener(date: LocalDate) {
        this.date = date
        updateDate()
        updateDetailAdapterData()
        updateDetails()
    }
}