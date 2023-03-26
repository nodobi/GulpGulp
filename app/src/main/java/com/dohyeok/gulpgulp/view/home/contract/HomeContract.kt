package com.dohyeok.gulpgulp.view.home.contract

import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.home.adapter.HomeDrinkAdapterContract

interface HomeContract {
    interface View : BaseContract.View {
        var presenter: Presenter

        fun changeTodayDrinkAmount(amount: Int)
        fun changeProgressPercent(percent: Int)
    }

    interface Presenter : BaseContract.Presenter<View> {
        var adapterView: HomeDrinkAdapterContract.View
        var adapterModel: HomeDrinkAdapterContract.Model
        var drinkRepository: DrinkRepository
        fun updateDrinkData()
        fun updateTodayDrinkAmount()
        fun updateProgress()
        fun getPreferenceValue(key: String): Any?
    }
}