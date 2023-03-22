package com.dohyeok.gulpgulp.view.setting.contract

import android.content.SharedPreferences
import com.dohyeok.gulpgulp.view.setting.SettingFragment

class SettingPresenter constructor(
    override var view: SettingContract.View, private var preferences: SharedPreferences
) : SettingContract.Presenter {
    override var onAlertChanged: (Boolean) -> Unit = { onAlertChangeListener(it) }

    private fun onAlertChangeListener(isChecked: Boolean) {
        putPreferenceValue(SettingFragment.PREFERENCE_KEY_ALERT, isChecked)
        view.changeAlertVisibility(isChecked)
    }

    private fun putPreferenceValue(key: String, value: Any) {
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
}