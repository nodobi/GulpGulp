package com.dohyeok.gulpgulp.view.editdrinkdetail.contract

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.editdrinkdetail.adapter.ExistDrinkAdapterContract

interface EditDrinkContract {
    interface View: BaseContract.View {
        var presenter: EditDrinkPresenter

        fun updateBottomSheetIcon(resId: Int)
    }
    interface Presenter: BaseContract.Presenter<View> {
        var adapterView: ExistDrinkAdapterContract.View
        var adapterModel: ExistDrinkAdapterContract.Model
        var drinkRepository: DrinkRepository
        var onDrinkAddBtnClick: (Drink) -> Unit
        var onIconSelected: (Int) -> Unit

        fun updateDrinkData()
    }
}