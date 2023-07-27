package com.dohyeok.gulpgulp.view.calendar.contract

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.util.SPUtils
import com.dohyeok.gulpgulp.view.calendar.ItemTouchCallback
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDayBinderContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapterContract
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarHeaderBinderContract
import com.kizitonwose.calendar.core.CalendarMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarPresenter constructor(
    override var view: CalendarContract.View,
    override var calendarDayBinder: CalendarDayBinderContract,
    override var calendarHeaderBinder: CalendarHeaderBinderContract,
    override var detailAdapterView: CalendarDetailAdapterContract.View,
    override var detailAdapterModel: CalendarDetailAdapterContract.Model,
    override var drinkRepository: DrinkRepository,
    private var spUtils: SPUtils
) : CalendarContract.Presenter {

    // Displaying Calendar Date
    private var calendarDate: LocalDate = LocalDate.now()
    private var calendarDetailDate: LocalDate = LocalDate.now()


    init {
        calendarDayBinder.apply {
            onClickDayView = { newDate, oldDate ->
                onDateClickListener(newDate, oldDate)
            }
        }

    }

    override var onCalendarScroll: (CalendarMonth) -> Unit = { calendarMonth ->
        calendarDate = calendarMonth.yearMonth.atDay(1)
        view.updateCalendarDate(calendarDate)
        calendarDayBinder.selectedDate?.let {
            view.notifyCalendarDateChanged(it)
            calendarDayBinder.selectedDate = null
        }

        updateProgress()
        CoroutineScope(Dispatchers.IO).launch {
            val drinkResultMap = mutableMapOf<LocalDate, Boolean>()
            calendarMonth.weekDays.forEach {weeks ->
                weeks.forEach {calendarDay ->
                    drinkRepository.loadDrinkGoal(calendarDay.date)?.let { drinkGoal ->
                        drinkResultMap.put(drinkGoal.date, drinkGoal.isComplete)
                    }
                }
            }
            calendarDayBinder.drinkResultMap = drinkResultMap.toMap()
            view.notifyCalendarMonthChanged(calendarMonth.yearMonth)
        }
    }

    override fun updateDate() {
        view.updateCalendarDate(calendarDate)
        view.updateCalendarDetailDate(calendarDetailDate)
    }

    override fun updateDetailAdapterData() {
        CoroutineScope(Dispatchers.Main).launch {
            detailAdapterModel.updateDrinkData(drinkRepository.loadDrinkRecords(calendarDetailDate))
            detailAdapterView.notifyDataChanged()
        }
    }

    override fun updateProgress() {
        CoroutineScope(Dispatchers.Main).launch {
            val calendarMonthDateList = mutableListOf<LocalDate>()
            var completeCnt = 0

            for(i in 1 .. calendarDate.month.maxLength()) {
                calendarMonthDateList.add(calendarDate.withDayOfMonth(i))
            }

            val drinkGoals = drinkRepository.loadDrinkGoals(calendarMonthDateList)
            for(goal in drinkGoals) {
                goal?.let {
                    if(it.isComplete) completeCnt++
                }
            }

            view.changeProgressPercent((completeCnt.toFloat() / calendarDate.month.maxLength() * 100).toInt())

        }
    }

    override fun updateDetails() {
        updateDetailProgresses()
        updateDetailDrinkAmount()
    }

    override fun setAdapterEvents() {
        view.setCalendarEvents( onPrev = {
            calendarDate = calendarDate.minusMonths(1)
            view.moveCalendar(calendarDate)
        }) {
            calendarDate = calendarDate.plusMonths(1)
            view.moveCalendar(calendarDate)
        }

        view.attachItemTouchHelper(object : ItemTouchCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos: Int = viewHolder.adapterPosition
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
            val goal = drinkRepository.loadDrinkGoal(calendarDetailDate)?.amount
                ?: spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000)
            view.changeDetailProgressPercent(
                (drinkRepository.loadDrinkAmount(calendarDetailDate) * 100f / goal).toInt()
            )
        }
    }

    private fun updateDetailDrinkAmount() {
        CoroutineScope(Dispatchers.Main).launch {
            view.changeDetailDrinkAmount(
                drinkRepository.loadDrinkAmount(calendarDetailDate)
            )
        }
    }


    private fun onDateClickListener(newDate: LocalDate, oldDate: LocalDate?) {
        if(newDate != oldDate) {
            calendarDetailDate = newDate
            view.notifyCalendarDateChanged(newDate)
            oldDate?.let { view.notifyCalendarDateChanged(oldDate) }

            view.updateCalendarDetailDate(calendarDetailDate)
            updateDetailAdapterData()
            updateDetails()
        }
    }
}