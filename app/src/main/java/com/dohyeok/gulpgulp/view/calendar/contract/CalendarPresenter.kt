package com.dohyeok.gulpgulp.view.calendar.contract

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.util.SPUtils
import com.dohyeok.gulpgulp.view.calendar.ItemTouchCallback
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapterContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarPagerAdapterContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarPresenter constructor(
    override var view: CalendarContract.View,
    override var detailAdapterView: CalendarDetailAdapterContract.View,
    override var detailAdapterModel: CalendarDetailAdapterContract.Model,
    override var pagerAdapterView: CalendarPagerAdapterContract.View,
    override var pagerAdapterModel: CalendarPagerAdapterContract.Model,
    override var drinkRepository: DrinkRepository,
    private var spUtils: SPUtils
) : CalendarContract.Presenter {
    init {
        pagerAdapterModel.apply {
            onCalendarPageChanged = {
                onCalendarPageChangeListener(it)
            }
            onDateClicked = {
                onDateClickListener(it)
            }
        }
        detailAdapterModel.currentDate = LocalDate.now()
    }

    override fun updateDate() {
        view.updateCalendarDate(pagerAdapterModel.date)
        view.updateCalendarDetailDate(detailAdapterModel.currentDate)
    }

    override fun updateDetailAdapterData() {
        CoroutineScope(Dispatchers.Main).launch {
            detailAdapterModel.updateDrinkData(drinkRepository.loadDrinkRecords(detailAdapterModel.currentDate))
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

    override fun setAdapterEvents() {
        view.attachItemTouchHelper(object : ItemTouchCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos: Int = viewHolder.bindingAdapterPosition
                val item: DrinkRecord = detailAdapterModel.recordData[pos]
                detailAdapterView.notifyItemDraw(pos)

                view.showDialog(onPositive = {
                    detailAdapterModel.removeItem(pos)
                    CoroutineScope(Dispatchers.Main).launch {
                        drinkRepository.deleteDrinkRecord(item)
                        updateDetails()
                    }
                }, onDismiss = {

                })
            }
        })
    }

    private fun updateDetailProgresses() {
        CoroutineScope(Dispatchers.Main).launch {
            val goal = drinkRepository.loadDrinkGoal(detailAdapterModel.currentDate)?.amount
                ?: spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000)
            view.changeDetailProgressPercent(
                (drinkRepository.loadDrinkAmount(detailAdapterModel.currentDate) * 100f / goal).toInt()
            )
        }
    }

    private fun updateDetailDrinkAmount() {
        CoroutineScope(Dispatchers.Main).launch {
            view.changeDetailDrinkAmount(
                drinkRepository.loadDrinkAmount(detailAdapterModel.currentDate)
            )
        }
    }

    private fun onDateClickListener(date: LocalDate) {
        detailAdapterModel.currentDate = date
        view.updateCalendarDetailDate(date)
        updateDetailAdapterData()
        updateDetails()
    }

    private fun onCalendarPageChangeListener(date: LocalDate) {
        view.updateCalendarDate(date)
    }
}