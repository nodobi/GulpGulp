package com.dohyeok.gulpgulp.view.dialog.goalpicker.contract

import com.dohyeok.gulpgulp.di.module.UIDispatcher
import com.dohyeok.gulpgulp.util.SPUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoalPickerDialogPresenter @Inject constructor(
    private var spUtil: SPUtil,
    @UIDispatcher private var uiDispatcher: CoroutineDispatcher
): GoalPickerDialogContract.Presenter {
    override var _view: GoalPickerDialogContract.View? = null

    private val formatValue = 50
    private val formatter: (Int) -> String = { (it*formatValue).toString() }

    override fun loadPickerValues() {
        CoroutineScope(uiDispatcher).launch {
            val goalValue = spUtil.getInt(SPUtil.KEY_GOAL_AMOUNT)
            view.setPickerValues(10, 100, goalValue / formatValue, formatter)
        }
    }

    override fun toFormatValue(pickerValue: Int): Int {
        return pickerValue * formatValue
    }

    override fun saveGoalValue(pickerValue: Int) {
        CoroutineScope(uiDispatcher).launch {
            spUtil.putInt(SPUtil.KEY_GOAL_AMOUNT, pickerValue * formatValue)
        }
    }
}