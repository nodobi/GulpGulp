package com.dohyeok.gulpgulp.view.dialog.goalpicker.contract

import android.widget.NumberPicker
import com.dohyeok.gulpgulp.view.base.BaseContract

interface GoalPickerDialogContract {
    interface View: BaseContract.View {
        fun setPickerValues(min: Int, max: Int, current: Int, formatter: NumberPicker.Formatter)
    }
    interface Presenter: BaseContract.Presenter<View> {
        fun loadPickerValues()
        fun toFormatValue(pickerValue: Int): Int
        fun saveGoalValue(pickerValue: Int)
    }
}