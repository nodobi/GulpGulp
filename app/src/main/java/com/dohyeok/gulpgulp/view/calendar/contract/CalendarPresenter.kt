package com.dohyeok.gulpgulp.view.calendar.contract

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.Drink
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
import java.time.LocalTime

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

    override lateinit var onCalendarScroll: (CalendarMonth) -> Unit
    override lateinit var onDrinkRecordEdit: (DrinkRecord, Drink) -> Unit

    override lateinit var onAddMissingDrinkRecordBtnClick: (Drink) -> Unit

    init {
        calendarDayBinder.apply {
            onClickDayView = { newDate, oldDate ->
                onDateClickListener(newDate, oldDate)
            }
        }
        detailAdapterModel.onDrinkDetailClick =
            { DrinkRecord, resId -> onDrinkDetailClickListener(DrinkRecord, resId) }

        onCalendarScroll = { calendarMonth -> onCalendarScrollListener(calendarMonth) }
        onDrinkRecordEdit =
            { drinkRecord, editedDrink -> onDrinkEditedListener(drinkRecord, editedDrink) }
        onAddMissingDrinkRecordBtnClick = { drink -> onAddMissingDrinkBtnClickListener(drink) }
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

            for (i in 1..calendarDate.month.maxLength()) {
                calendarMonthDateList.add(calendarDate.withDayOfMonth(i))
            }

            val drinkGoals = drinkRepository.loadDrinkGoals(calendarMonthDateList)
            for (goal in drinkGoals) {
                goal?.let {
                    if (it.isComplete) completeCnt++
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
        view.setCalendarEvents(onPrev = {
            calendarDate = calendarDate.minusMonths(1)
            view.moveCalendar(calendarDate)
        }) {
            calendarDate = calendarDate.plusMonths(1)
            view.moveCalendar(calendarDate)
        }

        view.attachItemTouchHelper(object : ItemTouchCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos: Int = viewHolder.adapterPosition
                val item: DrinkRecord = detailAdapterModel.getRecordData(pos)
                detailAdapterView.notifyItemDraw(pos)

                view.showDialog(onPositive = {
                    detailAdapterModel.removeItem(pos)
                    CoroutineScope(Dispatchers.Main).launch {
                        drinkRepository.deleteDrinkRecord(item)
                        updateDetails()
                    }
                }, onNegative = {

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

    private fun onCalendarScrollListener(calendarMonth: CalendarMonth) {
        calendarDate = calendarMonth.yearMonth.atDay(1)
        view.updateCalendarDate(calendarDate)
        calendarDayBinder.selectedDate?.let {
            view.notifyCalendarDateChanged(it)
            calendarDayBinder.selectedDate = null
        }

        updateProgress()
        CoroutineScope(Dispatchers.IO).launch {
            val drinkResultMap = mutableMapOf<LocalDate, Boolean>()
            calendarMonth.weekDays.forEach { weeks ->
                weeks.forEach { calendarDay ->
                    drinkRepository.loadDrinkGoal(calendarDay.date)?.let { drinkGoal ->
                        drinkResultMap.put(drinkGoal.date, drinkGoal.isComplete)
                    }
                }
            }
            calendarDayBinder.drinkResultMap = drinkResultMap
            view.notifyCalendarMonthChanged(calendarMonth.yearMonth)
        }
    }

    private fun onDateClickListener(newDate: LocalDate, oldDate: LocalDate?) {
        if (newDate != oldDate) {
            calendarDetailDate = newDate
            view.notifyCalendarDateChanged(newDate)
            oldDate?.let { view.notifyCalendarDateChanged(oldDate) }

            view.updateCalendarDetailDate(calendarDetailDate)
            updateDetailAdapterData()
            updateDetails()
        }
    }

    private fun onDrinkDetailClickListener(drinkRecord: DrinkRecord, resId: Int) {
        view.showEditDrinkRecordDialog(drinkRecord, resId)
    }

    private fun onDrinkEditedListener(drinkRecord: DrinkRecord, editedDrink: Drink) {
        CoroutineScope(Dispatchers.Main).launch {
            val isUpdateAmount = drinkRecord.drink.amount != editedDrink.amount
            val idx = detailAdapterModel.updateItem(drinkRecord.copy(drink = editedDrink).apply {
                id = drinkRecord.id
            })

            detailAdapterView.notifyItemChanged(idx)

            drinkRepository.updateDrinkRecord(drinkRecord, editedDrink)
            updateDetails()

            if (isUpdateAmount) {
                val goal = spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000)
                val isComplete = drinkRepository.loadDrinkAmount(drinkRecord.date) >= goal
                calendarDayBinder.drinkResultMap?.set(drinkRecord.date, isComplete)
                view.notifyCalendarDateChanged(drinkRecord.date)


                drinkRepository.upsertDrinkGoal(drinkRecord.date, goal, isComplete)
                updateProgress()
            }


        }
    }

    private fun onAddMissingDrinkBtnClickListener(drink: Drink) {
        CoroutineScope(Dispatchers.Main).launch {
            val drinkRecord = DrinkRecord(drink, calendarDetailDate, LocalTime.now())
            drinkRepository.insertDrinkRecord(drinkRecord)
            updateDetails()
            updateDetailAdapterData()
            val goal = spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000)
            val isComplete = drinkRepository.loadDrinkAmount(calendarDetailDate) >= goal
            calendarDayBinder.drinkResultMap?.set(drinkRecord.date, isComplete)
            view.notifyCalendarDateChanged(drinkRecord.date)

            drinkRepository.upsertDrinkGoal(calendarDetailDate, goal, isComplete)
            updateProgress()
        }
    }
}