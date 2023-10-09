package com.dohyeok.gulpgulp.view.statistics.contract

import android.util.Log
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.goal.GoalRepository
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.view.statistics.adapter.CalendarDayBinderContract
import com.dohyeok.gulpgulp.view.statistics.adapter.RecordListAdapterContract
import com.dohyeok.gulpgulp.view.statistics.adapter.RecordListViewHolder
import com.kizitonwose.calendar.core.CalendarMonth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import javax.inject.Inject

class StatisticsPresenter @Inject constructor(
    override var recordListAdapterView: RecordListAdapterContract.View,
    override var recordListAdapterModel: RecordListAdapterContract.Model,
    override var calendarDayBinder: CalendarDayBinderContract,
    private var iconRepository: IconRepository,
    private var goalRepository: GoalRepository,
    private var uiContext: CoroutineDispatcher = Dispatchers.Main,
) : StatisticsContract.Presenter {
    init {
        calendarDayBinder.onClickCalendarDate = { onClickCalendarDateListener(it) }
        recordListAdapterView.onClickRecord = { onClickRecordItemListener(it) }

    }

    override var _view: StatisticsContract.View? = null

    override var onItemSwiped: (RecordListViewHolder, Int) -> Unit =
        { holder, position -> onSwipeItemListener(holder, position) }

    override var onCommitAddDialog: (DrinkItem, LocalTime) -> Unit =
        { item, time -> onCommitAddDialogListener(item, time) }
    override var onCommitEditDialog: (DrinkRecord) -> Unit =
        { updatedRecord -> onCommitEditDialogListener(updatedRecord) }

    // 캘린더에서 스크롤 할 때 마다 갱신되는 변수
    private lateinit var calendarYearMonth: CalendarMonth

    private val calendarDays: List<LocalDate>
        get() {
            return mutableListOf<LocalDate>().apply {
                calendarYearMonth.weekDays.forEach { it ->
                    this.addAll(it.map {
                        it.date
                    })
                }
            }
        }


    override fun onScrollCalendar(scrolledData: CalendarMonth) {
        calendarYearMonth = scrolledData
        view.updateCalendarHeader(calendarYearMonth.yearMonth)

        loadGoalData(scrolledData.yearMonth)
    }

    override fun loadData() {
        loadGoalData(YearMonth.now())
        loadRecordListData(LocalDate.now())
    }

    /* 현재 보여지고 있는 달력의 Goals 들을 불러오는 함수
         *
         */
    override fun loadGoalData(yearMonth: YearMonth) {
        CoroutineScope(uiContext).launch {
            val data = goalRepository.getMonthGoals(yearMonth.year, yearMonth.monthValue)
                .associateBy { it.date }
            calendarDayBinder.updateGoalsData(data)
            data.keys.forEach {
                view.notifyCalendarDateChanged(it)
            }
        }
    }

    /*
     * 현재 선택된 날짜의 Records 와 아이콘들을 불러오는 함수
     */
    override fun loadRecordListData(date: LocalDate) {
        view.updateRecordHeader(date)
        CoroutineScope(uiContext).launch {
            recordListAdapterModel.updateRecordsData(goalRepository.getRecordsWithDate(date))
            recordListAdapterModel.updateIcons(iconRepository.getAllIconsResId())
            recordListAdapterView.notifyDataChanged()
        }
    }

    private fun onClickCalendarDateListener(date: LocalDate) {
        val oldDate = calendarDayBinder.selectedDate
        calendarDayBinder.selectedDate = date
        view.notifyCalendarDateChanged(date)
        oldDate.let {
            view.notifyCalendarDateChanged(it)
        }

        loadRecordListData(date)
    }

    private fun onCommitAddDialogListener(drinkItem: DrinkItem, time: LocalTime) {
        Log.d("DHK", "onCommitAddDialogListener")
        CoroutineScope(uiContext).launch {
            val newRecord = DrinkRecord(calendarDayBinder.selectedDate, time, drinkItem)
            goalRepository.saveRecord(newRecord)

            calendarDayBinder.updateGoalData(
                goalRepository.getGoal(newRecord.recordDate)!!
            )

            iconRepository.addIcon(newRecord.drink.icon)
            val icon = iconRepository.getIcon(newRecord.drink.icon)

            recordListAdapterModel.addNewData(newRecord, icon)
            recordListAdapterView.notifyDataChanged()
        }
    }

    private fun onCommitEditDialogListener(updatedRecord: DrinkRecord) {
        Log.d("DHK", "onCommitEditDialogListener")
        CoroutineScope(uiContext).launch {
            goalRepository.updateRecordDrink(
                updatedRecord
            )
            iconRepository.addIcon(updatedRecord.drink.icon)
            recordListAdapterModel.updateData(
                updatedRecord = updatedRecord,
                icon = iconRepository.getIcon(updatedRecord.drink.icon)
            )
        }
    }

    private fun onSwipeItemListener(holder: RecordListViewHolder, position: Int) {
        recordListAdapterModel.removeData(position)
        recordListAdapterView.notifyRecordRemoved(position)
        CoroutineScope(uiContext).launch {
            goalRepository.deleteRecord(holder.drinkRecord)
            goalRepository.getGoal(calendarDayBinder.selectedDate)?.let {
                calendarDayBinder.updateGoalData(it)
                view.notifyCalendarDateChanged(calendarDayBinder.selectedDate)
            }
        }
    }

    private fun onClickRecordItemListener(item: DrinkRecord) {
        view.showEditRecordDialog(item)
    }
}