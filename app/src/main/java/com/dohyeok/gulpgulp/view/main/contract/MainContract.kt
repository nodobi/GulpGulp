package com.dohyeok.gulpgulp.view.main.contract

import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.main.adapter.DrinkListContract

interface MainContract {
    interface View: BaseContract.View {
        fun updateDrinkAmount(amount: Int)
        fun updateDrinkGoal(amount: Int)
        fun updateDrinkProgressbar(max: Int, progress: Int)
    }

    interface Presenter: BaseContract.Presenter<View> {
        var drinkListView: DrinkListContract.View
        var drinkListModel: DrinkListContract.Model

        fun loadDrinkItemData()
        fun loadTodayGoalData()
    }
}