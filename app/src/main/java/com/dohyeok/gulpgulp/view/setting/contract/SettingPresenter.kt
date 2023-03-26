package com.dohyeok.gulpgulp.view.setting.contract

import com.dohyeok.gulpgulp.util.SPUtils

class SettingPresenter constructor(
    override var view: SettingContract.View,
    private var spUtils: SPUtils
) : SettingContract.Presenter {
    override var goalNumberPickerNumberMultiply: Int = 50

    override var onAlertChanged: (Boolean) -> Unit = { onAlertChangeListener(it) }
    override var onGoalCommitBtnClick: (Int) -> Unit = { onGoalCommitBtnClickListener(it) }

    override fun getDrinkGoal(): Int =
        spUtils.getInt(SPUtils.PREFERENCE_KEY_GOAL, 1000)


    override fun getAlarmEnabled(): Boolean =
        spUtils.getBoolean(SPUtils.PREFERENCE_KEY_ALERT, false)


    private fun onAlertChangeListener(isChecked: Boolean) {
        spUtils.put(SPUtils.PREFERENCE_KEY_ALERT, isChecked, false)

        view.changeAlertVisibility(isChecked)
    }

    private fun onGoalCommitBtnClickListener(amount: Int) {
        view.changeDisplayDrinkGoal(amount * goalNumberPickerNumberMultiply)
        spUtils.put(SPUtils.PREFERENCE_KEY_GOAL, amount * goalNumberPickerNumberMultiply, false)
    }
}