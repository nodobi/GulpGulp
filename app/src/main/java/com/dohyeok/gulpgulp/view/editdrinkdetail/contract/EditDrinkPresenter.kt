package com.dohyeok.gulpgulp.view.editdrinkdetail.contract

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.view.editdrinkdetail.adapter.ExistDrinkAdapterContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDrinkPresenter constructor(
    override var view: EditDrinkContract.View,
    override var adapterView: ExistDrinkAdapterContract.View,
    override var adapterModel: ExistDrinkAdapterContract.Model,
    override var drinkRepository: DrinkRepository
): EditDrinkContract.Presenter {
    init {
        adapterView.onDrinkClick = { drink -> onDrinkClickListener(drink) }
    }

    override var onDrinkAddBtnClick: (Drink) -> Unit = { onDrinkAddBtnClickListener(it) }
    override var onIconSelected: (Int) -> Unit = { resId -> onIconSelectListener(resId) }

    override fun updateDrinkData() {
        CoroutineScope(Dispatchers.Main).launch {
            val drinks = drinkRepository.loadDrinks()
            adapterModel.updateDrinkList(drinks)
            adapterView.notifyDataInited()
        }
    }

    private fun onDrinkAddBtnClickListener(drink: Drink) {
        CoroutineScope(Dispatchers.Main).launch {
            drink.order = drinkRepository.getLastDrinkOrder() + 1
            adapterModel.addDrink(drink)
            drinkRepository.insertDrink(drink)
        }
    }

    private fun onIconSelectListener(resId: Int) {
        view.updateBottomSheetIcon(resId)
    }

    private fun onDrinkClickListener(drink: Drink) {
        CoroutineScope(Dispatchers.Main).launch {
            adapterModel.removeDrink(drink)
            drinkRepository.deleteDrink(drink)
        }
    }
}