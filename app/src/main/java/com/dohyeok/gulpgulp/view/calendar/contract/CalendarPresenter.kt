package com.dohyeok.gulpgulp.view.calendar.contract

import com.dohyeok.gulpgulp.util.CalendarUtil
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarAdapterContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapterContract
import java.time.LocalDate

class CalendarPresenter: CalendarContract.Presenter {
    override lateinit var view: CalendarContract.View
    override lateinit var adapterView: CalendarAdapterContract.View
    override lateinit var adapterModel: CalendarAdapterContract.Model
    override lateinit var detailAdapterView: CalendarDetailAdapterContract.View
    override lateinit var detailAdapterModel: CalendarDetailAdapterContract.Model
    override var date: LocalDate = LocalDate.now()

    fun updateAdapterData() {
        adapterModel.updateSize(7, CalendarUtil.getCalendarWeekCnt(date))
        adapterModel.updateData(CalendarUtil.getDateList(date))
    }

    override fun updateDate() {
        view.updateCalendarDates(date)
    }
}