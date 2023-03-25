package com.dohyeok.gulpgulp.view.setting.contract

import android.content.SharedPreferences
import com.dohyeok.gulpgulp.view.setting.SettingFragment

class SettingPresenter constructor(
    override var view: SettingContract.View,
    private var preferences: SharedPreferences
) : SettingContract.Presenter {
    override var goalNumberPickerNumberMultiply: Int = 50

    override var onAlertChanged: (Boolean) -> Unit = { onAlertChangeListener(it) }
    override var onGoalCommitBtnClick: (Int) -> Unit = { onGoalCommitBtnClickListener(it) }

    override fun getDrinkGoal(): Int {
        return getPreferenceValue(SettingFragment.PREFERENCE_KEY_GOAL) as Int
    }

    private fun onAlertChangeListener(isChecked: Boolean) {
        putPreferenceValue(SettingFragment.PREFERENCE_KEY_ALERT, isChecked)
        view.changeAlertVisibility(isChecked)
    }

    private fun onGoalCommitBtnClickListener(amount: Int) {
        view.changeDisplayDrinkGoal(amount * goalNumberPickerNumberMultiply)
        putPreferenceValue(
            SettingFragment.PREFERENCE_KEY_GOAL, amount * goalNumberPickerNumberMultiply
        )
    }

    override fun putPreferenceValue(key: String, value: Any) {
        val editor = preferences.edit()
        when (value) {
            is Boolean -> {
                editor.putBoolean(key, value)
            }
            is Int -> {
                editor.putInt(key, value)
            }
            is String -> {
                editor.putString(key, value)
            }
        }
        editor.apply()
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