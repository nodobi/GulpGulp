package com.dohyeok.gulpgulp.view.setting.contract

import com.dohyeok.gulpgulp.view.base.BaseContract

interface SettingContract {
    interface View: BaseContract.View {
        fun updateGoalAmount(goal: Int)

    }
    interface Presenter: BaseContract.Presenter<View> {
        var onGoalPickerCommit: (Int) -> Unit

        fun loadGoalAmount()
    }
}