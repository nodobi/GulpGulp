package com.dohyeok.gulpgulp.view.setting.contract

import com.dohyeok.gulpgulp.view.base.BaseContract

interface SettingContract {
    interface View : BaseContract.View {
        var presenter: SettingPresenter

        fun changeAlertVisibility(isVisible: Boolean)
        fun changeDisplayDrinkGoal(goal: Int)
    }

    interface Presenter : BaseContract.Presenter<View> {
        var goalNumberPickerNumberMultiply: Int

        var onAlertChanged: (Boolean) -> Unit
        var onGoalCommitBtnClick: (Int) -> Unit

        fun getDrinkGoal(): Int
        fun putPreferenceValue(key: String, value: Any)
        fun getPreferenceValue(key: String): Any?
    }
}