package com.dohyeok.gulpgulp.view.editdrinkdetail.adapter

import com.dohyeok.gulpgulp.data.Drink

interface ExistDrinkAdapterContract {
    interface View {
        fun notifyDataInited()

    }
    interface Model {
        fun updateDrinkList(drinkList: List<Drink>)
        fun addDrink(drink: Drink) : Int
    }
}