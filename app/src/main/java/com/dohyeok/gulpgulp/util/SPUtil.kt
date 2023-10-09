package com.dohyeok.gulpgulp.util

import android.content.Context

class SPUtil(private val context: Context) {
    private val prefName = "pref_goal"
    private val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val editor get() = pref.edit()

    fun putInt(key: String, value: Int) {
        editor.putInt(KEY_GOAL_AMOUNT, value).commit()
    }

    fun getInt(key: String): Int {
        return pref.getInt(key, -1)
    }

    companion object {
        const val KEY_GOAL_AMOUNT = "goal_amount"
    }
}