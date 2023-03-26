package com.dohyeok.gulpgulp.view.home.contract

import android.content.SharedPreferences
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.view.home.adapter.HomeDrinkAdapterContract
import com.dohyeok.gulpgulp.view.setting.SettingFragment
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
    private var preferences: SharedPreferences
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
            view.changeTodayDrinkAmount(drinkRepository.loadTodayDrinkAmount())
        }
    }

    override fun updateProgress() {
        CoroutineScope(Dispatchers.Main).launch {
            view.changeProgressPercent(
                (drinkRepository.loadTodayDrinkAmount().toFloat() * 100 / getPreferenceValue(
                    SettingFragment.PREFERENCE_KEY_GOAL
                ) as Int).toInt()
            )
        }
    }

    override fun getPreferenceValue(key: String): Any? = when (key) {
        SettingFragment.PREFERENCE_KEY_ALERT -> {
            preferences.getBoolean(key, false)
        }
        SettingFragment.PREFERENCE_KEY_GOAL -> {
            preferences.getInt(key, 1000)
        }
        else -> {
            null
        }
    }

}