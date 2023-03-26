package com.dohyeok.gulpgulp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SPUtils constructor(
    context: Context,
    spName: String = PREFERENCE_NAME,
    mode: Int = Context.MODE_PRIVATE
) {
    private var sp: SharedPreferences

    init {
        sp = context.getSharedPreferences(spName, mode)
    }

    @SuppressLint("ApplySharedPref")
    fun put(key: String, value: Any, isCommit: Boolean) {
        val editor = sp.edit()
        when (value) {
            is Int -> {
                editor.putInt(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
            is String -> {
                editor.putString(key, value)
            }
        }
        if (isCommit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }

    fun getInt(key: String, defValue: Int = -1): Int =
        sp.getInt(key, defValue)

    fun getFloat(key: String, defValue: Float = -1f): Float =
        sp.getFloat(key, defValue)

    fun getBoolean(key: String, defValue: Boolean = false): Boolean =
        sp.getBoolean(key, defValue)

    fun getString(key: String, defValue: String = ""): String =
        sp.getString(key, defValue) ?: defValue

    companion object {
        const val PREFERENCE_NAME = "PREFERENCE_NAME"
        const val PREFERENCE_KEY_ALERT = "PREFERENCE_KEY_ALERT"
        const val PREFERENCE_KEY_GOAL = "PREFERENCE_KEY_GOAL"
    }

}