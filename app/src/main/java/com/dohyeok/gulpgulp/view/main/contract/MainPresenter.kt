package com.dohyeok.gulpgulp.view.main.contract

import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.goal.GoalRepository
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.view.main.adapter.DrinkListContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override var drinkListView: DrinkListContract.View,
    override var drinkListModel: DrinkListContract.Model,
    private var drinkRepository: DrinkRepository,
    private var iconRepository: IconRepository,
    private var goalRepository: GoalRepository,
    private var uiContext: CoroutineDispatcher,
) : MainContract.Presenter {
    init {
        drinkListView.onClickDrinkItem = { onClickDrinkItemListener(it) }
    }

    // TODO("View 를 null로 만들어주지 않아도 가비지 컬랙터가 가져가나?")
    override var _view: MainContract.View? = null

    override fun loadDrinkItemData() {
        CoroutineScope(uiContext).launch {
            val items = drinkRepository.getAllDrinkItems()
            val iconMap = iconRepository.getAllIconsResId()
            drinkListModel.refreshDrinkItems(items, iconMap)
            drinkListView.notifyItemsChanged()
        }
    }

    override fun loadTodayGoalData() {
        CoroutineScope(uiContext).launch {
            val today = LocalDate.now()
            val drinkAmount = goalRepository.getDrinkAmountSum(today)
            val todayGoalAmount = goalRepository.getGoal(today, true)!!.amount

            view.updateDrinkAmount(drinkAmount)
            view.updateDrinkProgressbar(todayGoalAmount, drinkAmount)
        }
    }

    private fun onClickDrinkItemListener(item: DrinkItem) {
        CoroutineScope(uiContext).launch {
            goalRepository.saveRecord(
                DrinkRecord(
                    LocalDate.now(),
                    LocalTime.now(),
                    item
                )
            )

            val today = LocalDate.now()
            val drinkAmount = goalRepository.getDrinkAmountSum(today)
            val todayGoalAmount = goalRepository.getGoal(today, true)!!.amount

            view.updateDrinkAmount(drinkAmount)
            view.updateDrinkProgressbar(todayGoalAmount, drinkAmount)
        }
    }

}
