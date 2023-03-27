package com.dohyeok.gulpgulp.view.home.contract

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.util.SPUtils
import com.dohyeok.gulpgulp.view.home.adapter.HomeDrinkAdapterContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class HomePresenter constructor(
    override var view: HomeContract.View,
    override var adapterView: HomeDrinkAdapterContract.View,
    override var adapterModel: HomeDrinkAdapterContract.Model,
    override var drinkRepository: DrinkRepository,
    private var spUtils: SPUtils
) : HomeContract.Presenter {
    init {
        view.presenter = this
        adapterModel.onDrinkClicked = { onDrinkClickListener(it) }
    }

    private fun onDrinkClickListener(drink: Drink) {
        CoroutineScope(Dispatchers.Main).launch {
            drinkRepository.insertDrinkRecord(DrinkRecord(drink, LocalDate.now(), LocalTime.now()))
            updateTodayDrinkAmount()
            updateProgress()

            drinkRepository.apply {
                upsertDrinkGoal(
                    LocalDate.now(),
                    spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000),
                    loadDrinkAmount() >= spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000)
                )
            }
        }
    }

    override fun updateDrinkData() {
        CoroutineScope(Dispatchers.Main).launch {
            adapterModel.updateDrinkList(drinkRepository.loadDrinks())
            adapterView.notifyDataUpdate()
        }

    }

    override fun updateTodayDrinkAmount() {
        CoroutineScope(Dispatchers.Main).launch {
            view.changeTodayDrinkAmount(drinkRepository.loadDrinkAmount())
        }
    }

    override fun updateProgress() {
        CoroutineScope(Dispatchers.Main).launch {
            view.changeProgressPercent(
                (drinkRepository.loadDrinkAmount()
                    .toFloat() * 100 / spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000)).toInt()
            )
        }
    }

}