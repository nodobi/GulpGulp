package com.dohyeok.gulpgulp.view.home.contract

import android.annotation.SuppressLint
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
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
    override var drinkRepository: DrinkRepository
) : HomeContract.Presenter {
    init {
        view.presenter = this
        adapterModel.onDrinkClicked = { onDrinkClickListener(it) }
    }

    private fun onDrinkClickListener(drink: Drink) {
        CoroutineScope(Dispatchers.Main).launch {
            drinkRepository.insertDrinkRecord(DrinkRecord(drink, LocalDate.now(), LocalTime.now()))
        }
    }

    @SuppressLint("ResourceType")
    override fun updateDrinkData() {
        val tempDrinkData: ArrayList<Drink> = arrayListOf()
        tempDrinkData.add(
            Drink(
                R.drawable.ic_bottle_24dp,
                "물",
                250
            )
        )
        tempDrinkData.add(
            Drink(
                R.drawable.ic_bottle_24dp,
                "커피",
                250
            )
        )
        tempDrinkData.add(
            Drink(
                R.drawable.ic_bottle_24dp,
                "녹차",
                250
            )
        )

        adapterModel.updateDrinkList(tempDrinkData.toList())
        adapterView.notifyDataUpdate()
    }

}