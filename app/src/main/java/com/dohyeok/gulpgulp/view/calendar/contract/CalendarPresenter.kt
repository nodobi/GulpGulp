package com.dohyeok.gulpgulp.view.calendar.contract

import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.util.CalendarUtil
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
    }

    override fun updateDate() {
        view.updateCalendarDates(date)
    }

    override fun updateDetailData() {
        CoroutineScope(Dispatchers.Main).launch {
            detailAdapterModel.updateDrinkData(drinkRepository.loadDrinks(date))
            detailAdapterView.notifyDataChanged()
        }
    }

    private fun onDateClickListener(date: LocalDate) {
        this.date = date
        updateDate()
        updateDetailData()
    }
}